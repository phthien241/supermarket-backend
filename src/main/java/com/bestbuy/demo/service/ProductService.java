package com.bestbuy.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.demo.Repository.ProductRepository;
import com.bestbuy.demo.dto.ProductDTO;
import com.bestbuy.demo.model.Product;
import com.bestbuy.demo.utils.ProductCategoryUtil;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByFilter(String filter) {
        String type = ProductCategoryUtil.FILTER_TO_TYPE_MAP.getOrDefault(filter, Collections.singletonList(null))
                .get(0);
        if (type != null) {
            return productRepository.findByTypeIn(type);
        }
        return productRepository.findAll();
    }

    public void updateProduct(ProductDTO product, boolean upsert) {
        ObjectId id = product.getId() != null ? new ObjectId(product.getId()) : null;
        Product productToUpdate = id != null ? new Product(id, product.getName(), product.getDescription(),
                product.getPrice(), product.getQuantity(), product.getType(), product.getBrand(), product.getImage())
                : new Product(product.getName(), product.getDescription(),
                        product.getPrice(), product.getQuantity(), product.getType(), product.getBrand(),
                        product.getImage());
        if (upsert) {
            productRepository.save(productToUpdate);
        } else {
            productRepository.findById(id).ifPresent(p -> {
                p.setName(product.getName());
                p.setDescription(product.getDescription());
                p.setPrice(product.getPrice());
                p.setQuantity(product.getQuantity());
                p.setType(product.getType());
                p.setBrand(product.getBrand());
                productRepository.save(p);

            });
        }
    }

    public Optional<Product> getProductInfo(ObjectId id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(ObjectId id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
