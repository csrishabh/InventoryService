package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.demo.document.Order;

public interface OrderRepo extends MongoRepository<Order, String> {

}
