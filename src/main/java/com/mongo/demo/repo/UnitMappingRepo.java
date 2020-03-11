package com.mongo.demo.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.Unit;
import com.mongo.demo.document.UnitMapping;

@Repository
public interface UnitMappingRepo extends MongoRepository<UnitMapping, String>, UnitMappingRepoCustom {

	@Query("{'unit' : ?0, 'company.$id': ?1, 'version' : ?2 }")
	public UnitMapping getUnitMappingByTypeAndCompany(Unit unit, ObjectId compamyId, int version);
}
