package com.mongo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.Product;
import com.mongo.demo.repo.ProductRepo;

@RestController
public class ProductController {

	@Autowired
	ProductRepo repo;

	@GetMapping("/test")
	public String test() {

		return "This is Mongo API";
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product item) {
		try {
		item = repo.save(item);
		return new ResponseEntity<Product>(item, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		List<Product> items = repo.findAll();
		return items;
	}
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable("id") String id) {
		Product product = repo.findById(id).get();
		return product;
	}
	
	@GetMapping("/products/{name}")
	public List<Product> getProductByName(@PathVariable("name") String name) {
		if(name.trim().equals("")) {
			return new ArrayList<>();
		}
		List<Product> products = repo.findByNameStartingWith(name.trim());
		return products;
	}
}
