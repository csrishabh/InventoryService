package com.mongo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.CommonData;
import com.mongo.demo.repo.CommonDataRepo;

@RestController
public class CommonDataController {
	
	@Autowired
	CommonDataRepo commonDataRepo;
	
	@GetMapping("/commmonData/{typeId}/{name}")
	public List<CommonData> getPersonByNameAndType(@PathVariable("name") String name, @PathVariable("typeId") int typeId) {
		if(name.trim().equals("")) {
			return new ArrayList<>();
		}
		List<CommonData> data = commonDataRepo.findByNameStartingWithAndType(name.trim(),typeId, false);
		return data;
	}
	
	/*@PostMapping("update/commmonData/status")
	public AppResponse<Void> updateStatus(@RequestBody CommonData data) {
		
	}*/

}
