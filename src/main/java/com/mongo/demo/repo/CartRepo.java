package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.demo.document.Cart;

public interface CartRepo extends MongoRepository<Cart, String> , CartRepoCustom{

}
