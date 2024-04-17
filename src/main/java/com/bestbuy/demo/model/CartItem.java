package com.bestbuy.demo.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class CartItem {
    private int price;
    private int quantity;
    @DBRef
    private Product product;

    public CartItem(){}

    public CartItem(int quantity, Product product){
        this.quantity = quantity;
        this.product = product;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
