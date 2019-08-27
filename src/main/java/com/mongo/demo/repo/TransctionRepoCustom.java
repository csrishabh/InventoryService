package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mongo.demo.document.Transction;

public interface TransctionRepoCustom {
	
	public List<Transction> getAuditPandingTransction( Map<String, Object> map);
	
	List<Transction> getTransctionByUser(Map<String, Object> map,String userID);
	
	List<Transction> getAllTransction(Map<String, Object> map);
	
	Transction updateTransction(Transction t);
	
	Transction deleteTransction(Transction t);

}
