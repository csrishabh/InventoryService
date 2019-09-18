package com.mongo.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.demo.document.Product;

public interface ProductRepo extends MongoRepository<Product, String>, ProductRepoCustom{
	
	List<Product> findByNameIgnoreCase(String name);	
}
