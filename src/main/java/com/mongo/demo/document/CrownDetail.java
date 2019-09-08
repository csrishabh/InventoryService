package com.mongo.demo.document;

public class CrownDetail {
	
	private CROWNTYPE type;
	private String shade;
	private String crownNo;
	public CROWNTYPE getType() {
		return type;
	}
	public void setType(CROWNTYPE type) {
		this.type = type;
	}
	public String getShade() {
		return shade;
	}
	public void setShade(String shade) {
		this.shade = shade;
	}
	public String getCrownNo() {
		return crownNo;
	}
	public void setCrownNo(String crownNo) {
		this.crownNo = crownNo;
	}
	@Override
	public String toString() {
		return "[type=" + type.getName() + ", crownNo=" + crownNo + "]";
	}
	
}
