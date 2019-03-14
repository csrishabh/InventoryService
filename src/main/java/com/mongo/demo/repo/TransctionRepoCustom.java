package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;

import com.mongo.demo.document.Product;
import com.mongo.demo.document.Transction;

public interface TransctionRepoCustom {
	
	public List<Transction> getAuditPandingTransction(Date startDate, Date endDate);
	
	Transction updateTransction(Transction t);
	
	Transction deleteTransction(Transction t);

}
