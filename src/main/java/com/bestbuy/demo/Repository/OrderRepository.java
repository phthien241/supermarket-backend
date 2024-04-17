package com.bestbuy.demo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestbuy.demo.model.Order;

public interface OrderRepository extends MongoRepository<Order, ObjectId>{
    
}
