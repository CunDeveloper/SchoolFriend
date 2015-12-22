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
	
	public static boolean isInteger(final String value){
        boolean status=true;
        if(value.length()<1)
            return false;
        for(int i = 0;i<value.length();i++){
            final char c=value.charAt(i);
            if(Character.isDigit(c)){
            }else{
                status=false;
                break;
            }
        }
        return status;
    }
	
	public static boolean isEmpty(final String str) {
		if (str==null || str.trim().equals("")) {
			return true;
		} else{
			return false;
		}
	}
	
	public static boolean isRight(final String str,final String value) {
		switch(str){
		case Constant.INTEGER:
			if(!isEmpty(value)){
				return isInteger(value);
			}else{
				return false;
			}
		case Constant.STRING:
			return !isEmpty(value);
		}
		return true;
		
		 
			
	}
	 
}
