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

	
	public Map<String, Object> exportPaymentReport() {

		
		Map<String, Object> fileData = new HashMap<>();
		fileData.put("products", productRepo.findAll());
		return fileData;

	}
}
