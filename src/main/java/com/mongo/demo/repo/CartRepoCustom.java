package com.mongo.demo.repo;

import com.mongo.demo.document.Cart;

public interface CartRepoCustom {
	
	
	Cart getCartByUserId(String userId);

}
