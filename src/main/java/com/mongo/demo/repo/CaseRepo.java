package com.mongo.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mongo.demo.document.Case;

public interface CaseRepo extends MongoRepository<Case,String> ,CaseRepoCustom {

	@Query("{'patient.id': ?0}")
	public List<Case> getCaseByPatientName(String name);
	
	public List<Case> getCaseByOpdNo(String opdNo);
	
	@Query("{'opdNo' : ?0, version : ?1 , bookingDate : ?2 }")
	public Case getCaseByOpdAndVersionNo(String opdNo, int versionNo, Date bookingDate);
}
