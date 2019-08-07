package com.mongo.demo.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import com.mongo.demo.document.Case;
import com.mongo.demo.document.CaseSearchResult;
import com.mongo.demo.document.CaseStatus;
import com.mongo.utility.Config;
import com.mongodb.BasicDBObject;



public class CaseRepoCustomImpl implements CaseRepoCustom {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public List<CaseSearchResult> findAllLatestCase(Map<String, Object> filters) {
		
		
		SortOperation sortByVersionAsc = Aggregation.sort(new Sort(Direction.DESC, "version"));	
		GroupOperation getCaseWithMaxVersion = Aggregation.group("opdNo").first(Aggregation.ROOT).as("Case");
		SortOperation sortByBookingDate = Aggregation.sort(new Sort(Direction.DESC, "Case.bookingDate"));	
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(applyFilter(filters)),sortByVersionAsc,getCaseWithMaxVersion,sortByBookingDate);
		AggregationResults<CaseSearchResult> result = mongoTemplate.aggregate(aggregation, "case",CaseSearchResult.class);
		return result.getMappedResults();	
		
	}
	
	 public List<CaseSearchResult> findLatestCase(String opdNo) {
		
		
		SortOperation sortByVersionAsc = Aggregation.sort(new Sort(Direction.DESC, "version"));	
		GroupOperation getCaseWithMaxVersion = Aggregation.group("opdNo").first(Aggregation.ROOT).as("Case");	
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("opdNo").is(opdNo)),sortByVersionAsc,getCaseWithMaxVersion);
		AggregationResults<CaseSearchResult> result = mongoTemplate.aggregate(aggregation, "case",CaseSearchResult.class);
		return result.getMappedResults();	
		
	}
	 
	 public List<Document> findLateCaseCount() {
			
			
			SortOperation sortByVersionAsc = Aggregation.sort(new Sort(Direction.DESC, "version"));	
			GroupOperation getCaseWithMaxVersion = Aggregation.group("opdNo").first("status").as("status").first("deliveredDate").as("deliveredDate");	
			Aggregation aggregation = Aggregation.newAggregation(sortByVersionAsc,getCaseWithMaxVersion,
					Aggregation.match(Criteria.where("status").is(CaseStatus.INPROCESS).and("deliveredDate").lt(Config.fomatDate(new Date()))),Aggregation.count().as("count"));
			AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "case",Document.class);
			return result.getMappedResults();	
	}
	 
	public List<CaseSearchResult> findAllLateCase() {
			
			SortOperation sortByVersionAsc = Aggregation.sort(new Sort(Direction.DESC, "version"));	
			GroupOperation getCaseWithMaxVersion = Aggregation.group("opdNo").first(Aggregation.ROOT).as("Case");	
			Aggregation aggregation = Aggregation.newAggregation(sortByVersionAsc,getCaseWithMaxVersion,
					Aggregation.match(Criteria.where("Case.status").is(CaseStatus.INPROCESS).and("Case.deliveredDate").lt(Config.fomatDate(new Date()))));
			AggregationResults<CaseSearchResult> result = mongoTemplate.aggregate(aggregation, "case",CaseSearchResult.class);
			return result.getMappedResults();	
			
	}	


	@Override
	public List<CaseSearchResult> findAllLatestCaseByUser(String user,Map<String, Object> filters) {
		MatchOperation filterUsers = Aggregation.match(new Criteria("createdBy").is(user).andOperator(applyFilter(filters)));
		SortOperation sortByVersionAsc = Aggregation.sort(new Sort(Direction.DESC, "version"));	
		GroupOperation getCaseWithMaxVersion = Aggregation.group("opdNo").first(Aggregation.ROOT).as("Case");
		SortOperation sortByBookingDate = Aggregation.sort(new Sort(Direction.DESC, "Case.bookingDate"));
		Aggregation aggregation = Aggregation.newAggregation(filterUsers,sortByVersionAsc,getCaseWithMaxVersion,sortByBookingDate);
		AggregationResults<CaseSearchResult> result = mongoTemplate.aggregate(aggregation, "case", CaseSearchResult.class);
		return result.getMappedResults();
	}
	
	
	private Criteria applyFilter(Map<String, Object> filters) {
		Criteria criteria = new Criteria();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		filters.forEach((k,v)->{
			switch(k) {
			case "status": {
				if(!StringUtils.isEmpty(v))
				criteria.and(k).is(v);
				break;
			}
			case "createdBy": {
				if(!StringUtils.isEmpty(v))
				criteria.and(k).regex((String)v,"i");
				break;
			}
			case "opdNo": {
				if(!StringUtils.isEmpty(v))
					criteria.and(k).regex((String)v,"i");
				break;
			}
			case "bookingDate1": {
				if(!StringUtils.isEmpty(v) && !StringUtils.isEmpty(filters.get("bookingDate2")))
					try {
						System.out.println(format.parse((String) v));
						criteria.andOperator(Criteria.where("bookingDate").gte(format.parse((String) v)),Criteria.where("bookingDate").lte(format.parse((String) filters.get("bookingDate2"))));
					} catch (ParseException e) {
						
					}
				break;
			}
			}
		});
		return criteria;
	}
	

}
