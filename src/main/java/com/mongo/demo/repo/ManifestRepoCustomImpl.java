package com.mongo.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ManifestRepoCustomImpl implements ManifestRepoCustom {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
}
