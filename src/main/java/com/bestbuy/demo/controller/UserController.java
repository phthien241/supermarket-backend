package com.bestbuy.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bestbuy.demo.dto.CartRequest;
import com.bestbuy.demo.model.CartItem;
import com.bestbuy.demo.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @PostMapping("/cart-update")
    public ResponseEntity<Map<String,String>> updateCart(@RequestBody CartRequest cartRequest){
        try{
            String response = userService.updateCart(cartRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", response));
        }catch(NoSuchElementException e){
            return ResponseEntity.ok(Collections.singletonMap("message", "User not found"));
        }catch(Exception e){
            return ResponseEntity.ok(Collections.singletonMap("message", e.getMessage()));

        }
    }

    @PostMapping("/get-cart")
    public ResponseEntity<?> getCart(@RequestBody Map<String,String> data){
        try{
            List<CartItem> carts = userService.getCart(data.get("authID"));
            return ResponseEntity.ok(carts);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "No user found"));
        }catch(Exception e){
            return ResponseEntity.status(500).body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
