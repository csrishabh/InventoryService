package com.mongo.demo.repo;

import java.util.List;

import com.mongo.demo.document.Unit;
import com.mongo.demo.document.UnitMapping;

public interface UnitMappingRepoCustom {

	public List<UnitMapping> getCurrentUnitByCompany(String compamyId, Unit... type);

	public UnitMapping getLatestUnitMappingByCompany(String companyId, Unit type);

}
