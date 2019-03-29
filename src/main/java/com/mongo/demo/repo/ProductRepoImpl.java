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

import com.mongo.demo.document.Cart;
import com.mongo.demo.document.Carted;
import com.mongo.demo.document.Product;
import com.mongo.demo.document.TransctionType;
import com.mongo.utility.Config;
import com.mongodb.BasicDBObject;

@Repository
public class ProductRepoImpl implements ProductRepoCustom {
	
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
				update.inc("qtyAblBack", (long)(qty*Config.QTY_FORMATTER));
				update.set("lstAdtBy", product.getLstAdtBy());
				update.set("lstAdtDate", new Date());
				update.set("lastPriceBack", product.getLastPriceBack());
			}
			else if(type == TransctionType.DISPATCH){
				query.addCriteria(Criteria.where("id").is(product.getId()).and("qtyAblBack").gte((long)(qty*Config.QTY_FORMATTER)));
				update.inc("qtyAblBack", (long	)(-qty*Config.QTY_FORMATTER));
			}
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			return mongoTemplate.findAndModify(query, update, options,Product.class);
	 }
	 
	 public Product addCarted(Product product, TransctionType type , double qty, Cart cart){
		 
		 	Query query = new Query();
			Update update = new Update();
			
			if(type == TransctionType.DISPATCH){
				query.addCriteria(Criteria.where("id").is(product.getId()).and("qtyAblBack").gte((long)(qty*Config.QTY_FORMATTER)));
				update.inc("qtyAblBack", (long	)(-qty*Config.QTY_FORMATTER));
				Carted c = new Carted();
				c.setCartId(cart.getId());
				c.setQtyBack((long)(qty*Config.QTY_FORMATTER));
				c.setTimeStamp(new Date());
				update.push("carted", c);
			}
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			return mongoTemplate.findAndModify(query, update, options,Product.class);
	 }

	@Override
	public Product editCarted(Product product, TransctionType type, double qty, Cart cart) {
		
		Query query = new Query();
		Update update = new Update();
		
		if(type == TransctionType.DISPATCH){
			query.addCriteria(Criteria.where("id").is(product.getId()).and("qtyAblBack").gte((long)(qty*Config.QTY_FORMATTER)).and("carted.cartId").is(cart.getId()));
			update.inc("qtyAblBack", (long	)(-qty*Config.QTY_FORMATTER));
			update.inc("carted.$.qtyBack", (long)(qty*Config.QTY_FORMATTER));
			update.set("carted.$.timeStamp", new Date());
			
		}
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		return mongoTemplate.findAndModify(query, update, options,Product.class);
	}


	@Override
	public void deleteCarted(String productId, String cartId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(productId));
		Update update = new Update();
		BasicDBObject carted = new BasicDBObject("cartId",cartId);
		update.pull("carted", carted);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		mongoTemplate.upsert(query, update, Product.class);
	}

}
