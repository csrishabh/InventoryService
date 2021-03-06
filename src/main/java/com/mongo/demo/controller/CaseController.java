package com.mongo.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Case;
import com.mongo.demo.document.CaseSearchResult;
import com.mongo.demo.document.CaseStatus;
import com.mongo.demo.service.CaseService;

@RestController
public class CaseController {


	@Autowired
	private CaseService caseService;
	
	@PostMapping("/add/case")
	public AppResponse<Case> addCase(@RequestBody Case report) {
		return caseService.saveNewCase(report);
	}
	
	@PostMapping("/update/case")
	public AppResponse<Void> updateCase(@RequestBody Case report) {
		return caseService.updateCase(report);
	}
	
	@GetMapping("/case/{OpdNo}/{date}")
	public AppResponse<Case> getCase(@PathVariable("OpdNo") String OpdNo , @PathVariable("date") String date) {
		return caseService.getLatestCase(OpdNo,date);
	}
	
	@GetMapping("/patient/{OpdNo}")
	public AppResponse<String> getPatientNameByOpdNo(@PathVariable("OpdNo") String OpdNo) {
		return caseService.getPatientNameByOpdNo(OpdNo);
	}
	
	
	@GetMapping("/get/cases")
	public ResponseEntity<List<CaseSearchResult>> getCaseHistory(HttpServletRequest request, @RequestParam Map<String, Object> filters){
		try {
		List<CaseSearchResult> cases = caseService.getCaseHistory(filters);
		return new ResponseEntity<List<CaseSearchResult>>(cases, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<CaseSearchResult>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/case/history/{OpdNo}/{date}")
	public AppResponse<List<CaseSearchResult>> getCaseHistoryByOpdNo(@PathVariable("OpdNo") String OpdNo ,@PathVariable("date") String date){
		return caseService.getCaseHistoryByOpdNo(OpdNo,date);
	}
	
	@GetMapping("/get/count/lateCase")
	public AppResponse<Document> getLateCaseCount(){
		return caseService.getLateCaseCount();
	}
	

	@PostMapping("proceed/cases")
	public AppResponse<Map<String, CaseStatus>> changeCaseStatus(@RequestBody Map<String, CaseStatus> cases){
		return caseService.changeCaseStatus(cases);
	}
	
	@GetMapping("/get/lateCase")
	public AppResponse<List<CaseSearchResult>> getAllLateCase(){
		return caseService.getAllLateCase();
	}
	
	
}
