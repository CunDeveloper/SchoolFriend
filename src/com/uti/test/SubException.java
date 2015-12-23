package com.uti.test;

public class SubException extends MyException {
	
	@Override
	public void add()  {
		 
	}
	
	public static void main(String[] args){
		SubException e = new SubException();
		e.add();
	}

}
