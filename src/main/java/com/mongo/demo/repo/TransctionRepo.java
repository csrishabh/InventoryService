package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.Transction;

@Repository
public interface TransctionRepo extends MongoRepository<Transction, String> ,TransctionRepoCustom {
}
