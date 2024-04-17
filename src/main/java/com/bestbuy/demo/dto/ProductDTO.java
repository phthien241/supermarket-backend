package com.bestbuy.demo.dto;

public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String brand;
    private String type;
    private String image;

    public ProductDTO() {
    }

    public ProductDTO(String id,String name, String description, double price, int quantity, String brand, String type,
            String image) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.type = type;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
