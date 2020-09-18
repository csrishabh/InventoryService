package com.mongo.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.demo.document.CaseSearchResult;
import com.mongo.demo.document.Transction;
import com.mongo.demo.repo.ProductRepo;
import com.mongo.demo.repo.TransctionRepo;

@Service
public class ExportReportService {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private TransctionRepo transctionRepo;
	
	@Autowired 
	private CustomUserDetailsService userService;

	
	public Map<String, Object> exportPaymentReport() {

		
		Map<String, Object> fileData = new HashMap<>();
		fileData.put("products", productRepo.findAll());
		return fileData;

	}
	
	public List<CaseSearchResult> exportVendorReport(Map<String, Object> filters) {
		
		return caseService.getVendorPayment(filters);

	}
	
	public Map<String, Object> exportTransctionReport(Map<String, Object> filters) {
		Map<String, Object> fileData = new HashMap<>();
		List<Document> res =  transctionRepo.getAllTransction(filters);
		fileData.put("transactions", res);
		return fileData;
	}
}
