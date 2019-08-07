package com.mongo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mongo.demo.document.Product;
import com.mongo.demo.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	
	public Page<Product> SearchProducts(int pageNo, boolean reverseOrder, String orderBy,String name) {
		Pageable p;
		if(orderBy!=null && !orderBy.equals("")) {
			if(reverseOrder) {
				p = PageRequest.of(pageNo, 10, Direction.DESC,orderBy.trim());
			}
			else {
				p = PageRequest.of(pageNo, 10, Direction.ASC,orderBy.trim());
			}
		}
		else {
			p = PageRequest.of(pageNo, 10);
		}
		if(name!=null && !name.equals("")) {
		Product filter = new Product();
		filter.setName(name);
		ExampleMatcher matcher =  ExampleMatcher.matchingAll()
				.withMatcher("name", match -> match.ignoreCase().contains()).withIgnorePaths("alertBack", "unit","lastPriceBack","qtyAblBack","lstAddBy").withIgnoreNullValues();
		Example<Product> example = Example.of(filter,matcher);
		return repo.findAll(example,p);
		}
		else {
			return repo.findAll(p);
		}
	}

}
