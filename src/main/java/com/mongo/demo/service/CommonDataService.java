package com.mongo.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.CommonData;
import com.mongo.demo.repo.CommonDataRepo;
import com.mongo.utility.StringConstant;

@Service
public class CommonDataService {

	@Autowired
	private CommonDataRepo repo;
	
	
	public AppResponse<Void> updateStatus(CommonData data) {
		
		AppResponse<Void> res = new AppResponse<>();
		if(data.getId()!=null) {
			try {
			CommonData commonData = repo.findById(data.getId()).get();
			commonData.setDisabled(data.isDisabled());
			res.setSuccess(true);
			res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			}
			catch(Exception e) {
				res.setSuccess(false);
				res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			}
		}
		else {
			res.setSuccess(false);
			res.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
		}
		return res;
	}
}
