package com.mongo.demo.document;

public enum CROWNTYPE {
	
	FULL_METAL_CERAMIC("Full Metal Creamic"),
	CERAMIC_FACING("Creamic Facing"), 
	METAL_FREE("Metal Free"), 
	TEMPORARY("Temporary"),
	REMOVEABLE_PARTIAL_DENTURE("Removeable Partial Denture"),
	COMPLETE_DENTURE_UPPER("Complete Denture Upper"),
	COMPLETE_DENTURE_LOWER("Complete Denture Lower"),
	NIGHT_GUARD("Night Gard");
	
	private String name;

	private CROWNTYPE(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
