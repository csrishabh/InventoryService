package com.mongo.demo.repo;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongo.demo.document.Manifest;

public interface ManifestRepoCustom {
	
	public List<Document> searchManifest(Map<String, Object> filters);
	
	public void deleteManifest(String refId);
	
    public void deleteConsignmentFromManifest(String refId,String biltyNo);
    
    public Manifest getManifest(String manifestId);

}
