package com.mongo.demo.repo;

import java.util.List;

import com.mongo.demo.document.CROWNTYPE;
import com.mongo.demo.document.CrownMapping;

public interface CrownMappingRepoCustom {

	public List<CrownMapping> getCurrentCrownTypeByVendor(String vendorId , CROWNTYPE... type);
	
	public CrownMapping getLatestCrownMappingByVendor(String vendorId, CROWNTYPE type);
	
	
}
