package com.mongo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Manifest;
import com.mongo.demo.service.ManifestService;

@RestController
public class MainfestController {

	
	@Autowired
	private ManifestService manifestService;
	
	@PostMapping("/add/manifest")
	public AppResponse<String> createManifest(@RequestBody Manifest manifest){
		
		return manifestService.createManifest(manifest);
	}
}
