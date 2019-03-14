package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.Product;
import com.mongo.demo.document.TransctionType;

@Repository
public class ProductRepoImpl implements ProductRepoCustom{
	
	 @Autowired 
	 MongoTemplate  mongoTemplate;
	 
	 public List<Product> findByNameStartingWith(String regexp){
		 
		 Criteria regex = Criteria.where("name").regex(regexp, "i");
		 return mongoTemplate.find(new Query().addCriteria(regex), Product.class);
	 }
	 
	 
	 public Product updateProduct(Product product, TransctionType type , double qty){
		 
		 	Query query = new Query();
			Update update = new Update();
			
			if(type == TransctionType.ADD) {
			query.addCriteria(Criteria.where("id").is(product.getId()));	
			update.set("lstAddBy", product.getLstAddBy());
			update.set("lstAddDate", product.getLstAddDate());
			}
			else if(type == TransctionType.AUDIT) {
				query.addCriteria(Criteria.where("id").is(product.getId()));	
				update.inc("qtyAbl", qty);
				update.set("lstAdtBy", product.getLstAdtBy());
				update.set("lstAdtDate", new Date());
				update.set("lastPrice", product.getLastPrice());
			}
			else if(type == TransctionType.DISPATCH){
				query.addCriteria(Criteria.where("id").is(product.getId()).and("qtyAbl").gte(qty));
				update.inc("qtyAbl", -qty);
			}
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			return mongoTemplate.findAndModify(query, update, options,Product.class);
	 }

}
