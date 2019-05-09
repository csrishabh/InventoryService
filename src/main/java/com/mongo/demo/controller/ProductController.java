package com.mongo.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.Product;
import com.mongo.demo.repo.ProductRepo;
import com.mongo.demo.service.ProductService;
import com.mongo.utility.Config;

@RestController
public class ProductController {

	@Autowired
	ProductRepo repo;
	
	@Autowired
	ProductService productService;

	@GetMapping("/test")
	public String test() {

		return "This is Mongo API";
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product item) {
		try {	
		item.setAlertBack((long)(item.getAlert()*Config.QTY_FORMATTER));
		item = repo.save(item);
		return new ResponseEntity<Product>(item, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		List<Product> items = formatProducts(repo.findAll());
		return items;
	}
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable("id") String id) {
		Product product = formatProducts(Arrays.asList(repo.findById(id).get())).get(0);
		return product;
	}
	
	@GetMapping("/products/{name}")
	public List<Product> getProductByName(@PathVariable("name") String name) {
		if(name.trim().equals("")) {
			return new ArrayList<>();
		}
		List<Product> products = formatProducts(repo.findByNameStartingWith(name.trim()));
		return products;
	}
	
	
	private List<Product> formatProducts(List<Product> products){
		
		products.stream().forEach(product ->{
			product.setQtyAbl(Config.format(product.getQtyAblBack(),Config.QTY_FORMATTER));
			product.setAlert(Config.format(product.getAlertBack(),Config.QTY_FORMATTER));
			product.setLastPrice(Config.format(product.getLastPriceBack(),Config.PRICE_FORMATTER));
		});
		return products;
	}
	
	@GetMapping("/searchProducts/{pageNo}")
	public Page<Product> getProducts(@PathVariable("pageNo") int pageNo, @RequestParam(value = "reverseSort", required=false) final boolean reverseSort,
			@RequestParam(value = "orderBy", required=false) final String orderByField) {
		Page<Product> products = productService.SearchProducts(pageNo,reverseSort,orderByField);
		formatProducts(products.getContent());
		return products;
	}
	
}
