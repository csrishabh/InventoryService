package com.mongo.demo.repo;

import java.util.List;

import com.mongo.demo.document.Cart;
import com.mongo.demo.document.Product;
import com.mongo.demo.document.TransctionType;

public interface ProductRepoCustom{

	 List<Product> findByNameStartingWith(String regexp);
	 
	 Product updateProduct(Product product, TransctionType type , double qty);
	 
	 Product addCarted(Product product, TransctionType type , double qty ,Cart cart);
	 
	 void deleteCarted(String productId, String cartId);
	 	 
	 Product editCarted(Product product, TransctionType type , double qty ,Cart cart);
}
