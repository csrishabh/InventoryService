package com.mongo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Unit;
import com.mongo.demo.document.UnitMapping;
import com.mongo.demo.service.UnitMappingService;

@RestController
public class UnitMappingController {

	@Autowired
	UnitMappingService unitMappingService;
	
	@PreAuthorize ("hasAuthority('USER_CARGO')")
	@GetMapping("/unit/{companyId}")
	public AppResponse<List<UnitMapping>> getCrownMappingByVendor(@PathVariable("companyId") String companyId){
		return unitMappingService.getUnitMappingByVendor(companyId);
	}
	
	@PreAuthorize ("hasAuthority('ADMIN_CARGO')")
	@PostMapping("/unit/save/{companyId}/{type}")
	public AppResponse<Void> saveCrownMapping(@PathVariable("companyId") String companyId, @PathVariable("type") Unit type , @RequestBody long price){
		return unitMappingService.saveUnitMapping(companyId, type, price);
	}
	
	@PreAuthorize ("hasAuthority('ADMIN_CARGO')")
	@GetMapping("/unit/price/{companyId}/{type}")
	public AppResponse<Double> getLatestPrice(@PathVariable("companyId") String companyId, @PathVariable("type") Unit type){
		return unitMappingService.getLatestUnitPriceByVendor(companyId, type);
	}
}
