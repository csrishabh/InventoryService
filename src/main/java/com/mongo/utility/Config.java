package com.mongo.utility;

public class Config {
	
	
	public static final double QTY_FORMATTER = 1000.0;
	public static final double PRICE_FORMATTER = 100.0;
	
	
	public static double format(long value , double formetter) {
		double v = value / formetter;
		v = Math.round(v * formetter) / formetter;
		return v;
	}

}
