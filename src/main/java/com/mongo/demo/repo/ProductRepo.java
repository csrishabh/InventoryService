package com.mongo.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mongo.demo.document.Product;

public interface ProductRepo extends MongoRepository<Product, String>, ProductRepoCustom{
	
	List<Product> findByNameIgnoreCase(String name);	
	
	@Query(value="{'name' : {$regex : ?0 , $options: 'i'}}")
    List<Product> findByNameStartingWith(String regexp);
}
