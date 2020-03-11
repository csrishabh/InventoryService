package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.demo.document.Consignment;

public interface ConsignmentRepo extends MongoRepository<Consignment, String> , ConsignmentRepoCustom {

	
}
