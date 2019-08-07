package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.Person;

@Repository
public interface PersonRepo extends MongoRepository<Person, String>, PersonRepoCustom {

	
}
