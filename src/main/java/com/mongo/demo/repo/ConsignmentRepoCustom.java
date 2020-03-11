package com.mongo.demo.repo;

import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface ConsignmentRepoCustom {
	
	public List<Document> searchConsignment(Map<String, Object> filters);
	
	public boolean isAnyConsignmentProcessed(List<String> consignments);
	
	public boolean isDuplicateConsignment(String biltyNo);
	
	public void setConsignmentDeliverd(List<String> consignments , String des);

}
