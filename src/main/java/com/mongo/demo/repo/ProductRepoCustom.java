package com.mongo.demo.repo;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mongo.demo.document.Cart;
import com.mongo.demo.document.Product;
import com.mongo.demo.document.TransctionType;

public interface ProductRepoCustom{

	 List<Product> findByNameStartingWith(String regexp);
	 
	 Product updateProduct(Product product, TransctionType type , double qty);
	 
	 Product addCarted(Product product, TransctionType type , double qty ,Cart cart);
	 
	 void deleteCarted(String productId, String cartId);
	 
	 Product addAssigned(String userid,String productId, double qty);
	 	 
	 Product editCarted(Product product, TransctionType type , double qty ,Cart cart);
	 
	 public List<Product> getAllProduct();
	 
	 public Page<Product> SearchProduct(int pageNo, boolean reverseOrder, String orderBy,String name);
}
