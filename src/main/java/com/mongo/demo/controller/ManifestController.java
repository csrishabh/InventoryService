package com.mongo.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Manifest;
import com.mongo.demo.service.ManifestService;

@RestController
public class ManifestController {

	
	@Autowired
	private ManifestService manifestService;
	
	@PostMapping("/add/manifest")
	public AppResponse<String> createManifest(@RequestBody Manifest manifest){
		
		return manifestService.createManifest(manifest);
	}
	
	@GetMapping("/get/manifest")
	public ResponseEntity<List<Document>> getManifestHistory(HttpServletRequest request, @RequestParam Map<String, Object> filters){
		try {
		List<Document> consignments = manifestService.getManifestHistory(filters);
		return new ResponseEntity<List<Document>>(consignments, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Document>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
