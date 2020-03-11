package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.Manifest;

@Repository
public interface ManifestRepo extends MongoRepository<Manifest, String> , ManifestRepoCustom{

}
