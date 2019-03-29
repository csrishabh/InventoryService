package com.mongo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mongo.demo.document.Product;
import com.mongo.demo.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	
	
	public Page<Product> SearchProducts(int pageNo) {
		
		Pageable p = PageRequest.of(pageNo, 10);
		return repo.findAll(p);
	}

}
