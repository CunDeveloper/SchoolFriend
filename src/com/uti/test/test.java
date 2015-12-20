package com.uti.test;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.nju.util.C3PODataSource;
 
 

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 byte[] buffer = {'#','1','2'};
		 int length = buffer.length;
		 byte[] finByte = new byte[length+1];
		 System.arraycopy(buffer, 0, finByte, 0, length);
		 finByte[length]='?';
		 for(byte c:finByte){
			 System.out.println(c);
		 }
	}

}
