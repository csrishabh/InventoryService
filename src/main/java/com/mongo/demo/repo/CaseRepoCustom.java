package com.mongo.demo.repo;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongo.demo.document.CaseSearchResult;

public interface CaseRepoCustom {
	
	public List<CaseSearchResult> findAllLatestCase(Map<String, Object> filters);
	
	public List<CaseSearchResult> findAllLatestCaseByUser(String user,Map<String, Object> filters);
	
	public List<CaseSearchResult> findLatestCase(String opdNo);
	
	public List<Document> findLateCaseCount();
	
	public List<CaseSearchResult> findAllLateCase() ;

}
