package com.mongo.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.Manifest;
import com.mongo.demo.repo.ConsignmentRepo;
import com.mongo.demo.repo.ManifestRepo;
import com.mongo.utility.StringConstant;

@Service
public class ManifestService {

	
	@Autowired
	private ManifestRepo manifestRepo;
	
	@Autowired
	private ConsignmentRepo consignmentRepo;
	
	
	public AppResponse<String> createManifest(Manifest manifest){
		AppResponse<String> res = new AppResponse<>();
		try {
		if(!isValidate(manifest)) {
			res.setSuccess(false);
			res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			return res;
		}
		
		boolean isAnyConProcessed = consignmentRepo.isAnyConsignmentProcessed(manifest.getConsignments());
		
		if(isAnyConProcessed) {
			res.setSuccess(false);
			res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
		}
		else {
			manifest = manifestRepo.save(manifest);
			consignmentRepo.setConsignmentDeliverd(manifest.getConsignments(), manifest.getDes());
			res.setSuccess(true);
			res.setMsg(Arrays.asList(StringConstant.MANIFEST_CREATED_SUCCESS));
			res.setData(String.valueOf(manifest.getRefId()));
		}
		
		}
		catch (Exception e) {
			res.setSuccess(false);
			res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
		}
		
		return res;
	}
	
	private boolean isValidate(Manifest manifest) {

		if (null == manifest.getConsignments() || 0 == manifest.getConsignments().size() || null == manifest.getCompany()
				|| null == manifest.getDes()) {
			return false;
		}
		return true;
	}
}
