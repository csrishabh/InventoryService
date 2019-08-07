package com.mongo.demo.document;

import java.util.HashMap;
import java.util.Map;

public class Crown {
	
	private int count;
	private String shade;
	private Map<CROWNTYPE, String> details = new HashMap<>();
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getShade() {
		return shade;
	}
	public void setShade(String shade) {
		this.shade = shade;
	}
	public Map<CROWNTYPE, String> getDetails() {
		return details;
	}
	public void setDetails(Map<CROWNTYPE, String> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		String s = "Shade: "+shade +"\n";
		s = s + "Details: "+details;
		return s;
		//return "Crown [count=" + count + ", shade=" + shade + ", details=" + details + "]";
	}
	
}
