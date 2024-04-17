package com.bestbuy.demo.dto;

import com.bestbuy.demo.model.Product;

public class CartRequest {
    private String authID;
    private Product product;

    public CartRequest(){}

    public CartRequest(String authID, Product product) {
        this.authID = authID;
        this.product = product;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
