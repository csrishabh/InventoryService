package com.mongo.demo.repo;

import java.util.List;

import com.mongo.demo.document.Product;
import com.mongo.demo.document.TransctionType;

public interface ProductRepoCustom{

	 List<Product> findByNameStartingWith(String regexp);
	 
	 Product updateProduct(Product product, TransctionType type , double qty);
}
