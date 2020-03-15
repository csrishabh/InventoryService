package com.mongo.demo.repo;

import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface ManifestRepoCustom {
	
	public List<Document> searchManifest(Map<String, Object> filters);

}
