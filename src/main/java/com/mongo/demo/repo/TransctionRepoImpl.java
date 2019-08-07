package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongo.demo.document.Transction;
import com.mongo.demo.document.TransctionType;

public class TransctionRepoImpl implements TransctionRepoCustom {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Transction> getAuditPandingTransction(Date startDate, Date endDate) {

		Criteria regex = Criteria.where("isAdtable").is(true).andOperator(Criteria.where("isAdtDone").is(false),Criteria.where("isDeleted").ne(true),Criteria.where("date").gte(startDate)
			    ,Criteria.where("date").lte(endDate));
		return mongoTemplate.find(new Query().addCriteria(regex), Transction.class);
	}

	@Override
	public Transction updateTransction(Transction t) {
		
		Query query = new Query();
		Update update = new Update();
		
		if(t.getType() == TransctionType.AUDIT){
			query.addCriteria(Criteria.where("id").is(t.getId()).and("isAdtDone").is(false));
			update.set("quantityBack", t.getQuantityBack());
			update.set("amountBack", t.getAmountBack());
			update.set("isAdtDone", true);
			update.set("adtDate", new Date());
			update.set("adtBy", t.getAdtBy());
		}
		
		return mongoTemplate.findAndModify(query, update, Transction.class);
		
	}

	@Override
	public Transction deleteTransction(Transction t) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(t.getId()).and("isAdtDone").is(false));
		return mongoTemplate.findAndRemove(query, Transction.class);
	}

	@Override
	public List<Transction> getTransctionByUser(Date startDate, Date endDate, String userID) {
		Criteria regex = Criteria.where("addBy").is(userID).andOperator(Criteria.where("isDeleted").ne(true),Criteria.where("date").gte(startDate)
			    ,Criteria.where("date").lte(endDate));
		return mongoTemplate.find(new Query().addCriteria(regex), Transction.class);
	}

	@Override
	public List<Transction> getAllTransction(Date startDate, Date endDate) {
		Criteria regex = Criteria.where("isDeleted").ne(true).andOperator(Criteria.where("date").gte(startDate)
			    ,Criteria.where("date").lte(endDate));
		return mongoTemplate.find(new Query().addCriteria(regex), Transction.class);
	}

}
