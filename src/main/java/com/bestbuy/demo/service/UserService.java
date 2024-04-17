package com.bestbuy.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.demo.Repository.UserRepository;
import com.bestbuy.demo.dto.CartRequest;
import com.bestbuy.demo.model.CartItem;
import com.bestbuy.demo.model.Product;
import com.bestbuy.demo.model.User;
import com.bestbuy.demo.utils.AzureStorageUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AzureStorageUtil azureStorageUtil;

    public String updateCart(CartRequest cartRequest) {
        User user = userRepository.findByAuthID(cartRequest.getAuthID())
                .orElseThrow(() -> new NoSuchElementException("No user found"));
        int index = -1;
        List<CartItem> carts = user.getCarts();
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getProduct().getId().equals(cartRequest.getProduct().getId())) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            carts.get(index).getProduct().setQuantity(cartRequest.getProduct().getQuantity());
        } else {
            CartItem cart = new CartItem(cartRequest.getProduct().getQuantity(), cartRequest.getProduct());
            carts.add(cart);
        }
        userRepository.save(user);
        return "Update cart successfully";
    }

    public List<CartItem> getCart(String authID) {
        User user = userRepository.findByAuthID(authID).orElseThrow(() -> new NoSuchElementException("No user found"));
        List<CartItem> carts = user.getCarts().stream().map(cart -> {
            Product product = cart.getProduct();
            product.setImage(product.getImage() + azureStorageUtil.getSasToken());
            return new CartItem(cart.getQuantity(), product);
        }).collect(Collectors.toList());
        return carts;
    }
}
