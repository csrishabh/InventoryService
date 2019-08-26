package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mongo.demo.document.Transction;

public interface TransctionRepoCustom {
	
	public List<Transction> getAuditPandingTransction( Map<String, Object> map);
	
	List<Transction> getTransctionByUser(Date startDate, Date endDate,String userID);
	
	List<Transction> getAllTransction(Date startDate, Date endDate);
	
	Transction updateTransction(Transction t);
	
	Transction deleteTransction(Transction t);

}
