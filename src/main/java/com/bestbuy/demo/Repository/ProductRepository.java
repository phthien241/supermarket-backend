package com.bestbuy.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestbuy.demo.model.Product;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    List<Product> findByTypeIn(String type);
}