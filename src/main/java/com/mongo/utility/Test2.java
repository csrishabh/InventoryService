package com.mongo.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {
	
	public static void main(String[] args) throws Exception {
		
		String patternString1 = "(\\d{4}) (is your One-Time Password)"; 
		Pattern pattern = Pattern.compile(patternString1);
		Matcher matcher = pattern.matcher("4607 is your One-Time Password. It is valid for 5 minutes for login in PVI Application");
		if (matcher.find()) {
			String otp = matcher.group(1);
			System.out.println(otp);
		} else {
			throw new Exception("Otp Not found");
		} 
		
		
	}

}
