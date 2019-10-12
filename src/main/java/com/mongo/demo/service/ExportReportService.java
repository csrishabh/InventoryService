package com.mongo.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.demo.repo.ProductRepo;

@Service
public class ExportReportService {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CaseService caseService;

	
	public Map<String, Object> exportPaymentReport() {

		
		Map<String, Object> fileData = new HashMap<>();
		fileData.put("products", productRepo.findAll());
		return fileData;

	}
	
	public Map<String, Object> exportVendorReport(Map<String, Object> filters) {
		Map<String, Object> fileData = new HashMap<>();
		fileData.put("report", caseService.getVendorPayment(filters));
		return fileData;

	}
}
