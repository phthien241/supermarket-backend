package com.bestbuy.demo.Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestbuy.demo.model.User;


public interface UserRepository extends MongoRepository<User, ObjectId>{

    Optional<User> findByAuthID(String authID);
    
}
