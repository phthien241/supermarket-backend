package com.bestbuy.demo.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bestbuy.demo.model.Product;
import com.bestbuy.demo.dto.ProductDTO;
import com.bestbuy.demo.service.ProductService;
import com.bestbuy.demo.utils.AzureStorageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AzureStorageUtil azureStorageUtil;

    @PostMapping
    ResponseEntity<?> fetchedProduct(@RequestBody Map<String,String> data) {
        String filter = data.get("filter");
        try {
            List<Product> products = productService.getProductsByFilter(filter);
            List<ProductDTO> productWithSAS = products.stream()
                    .map(product -> new ProductDTO(product.getId().toString(), product.getName(),
                            product.getDescription(), product.getPrice(),
                            product.getQuantity(), product.getBrand(), product.getType(),
                            product.getImage() + azureStorageUtil.getSasToken()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productWithSAS);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/update-product")
    public ResponseEntity<Map<String, String>> updateProduct(
            @RequestParam("product") String productJson,
            @RequestPart(required = false) MultipartFile file) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            ProductDTO product = mapper.readValue(productJson, ProductDTO.class);
            if (file != null && !file.isEmpty()) {
                String oldImageName = product.getImage() != null
                        ? azureStorageUtil.getFileNameFromURL(product.getImage())
                        : null;
                String imageUrl = azureStorageUtil.uploadFileToAzureBlob(
                        product.getName(), oldImageName, file.getInputStream(), file.getSize());
                product.setImage(imageUrl);
                productService.updateProduct(product, true);
            } else {
                productService.updateProduct(product, false);
            }

            return ResponseEntity.ok(Collections.singletonMap("message", "Add or update product successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product-info/{id}")
    public ResponseEntity<?> getProductInfo(@PathVariable String id) {
        try {
            Product product = productService.getProductInfo(new ObjectId(id)).get();
            ProductDTO productDTO = new ProductDTO(product.getId().toString(), product.getName(),
                    product.getDescription(), product.getPrice(),
                    product.getQuantity(), product.getBrand(), product.getType(),
                    product.getImage() + azureStorageUtil.getSasToken());
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestBody Map<String,String> data) {
        ObjectId id = new ObjectId(data.get("id"));
        productService.deleteProduct(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "deleted product successfully"));
    }
}
