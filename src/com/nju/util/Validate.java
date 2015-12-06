package com.nju.util;

public class Validate {

	public static boolean isNumber(String str) {
		char[] chars = str.toCharArray();
		boolean result = true;
		for(char ch:chars){
			if(!Character.isDigit(ch)){
				result = false;
			}
		}
		return result;
	}
}
