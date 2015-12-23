package com.uti.test;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.nju.dao.impl.CommentDaoImpl;
import com.nju.util.C3PODataSource;
import com.nju.util.Validate;
 
 

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		 
//		 byte[] buffer = {'#','1','2'};
//		 int length = buffer.length;
//		 byte[] finByte = new byte[length+1];
//		 System.arraycopy(buffer, 0, finByte, 0, length);
//		 finByte[length]='?';
//		 for(byte c:finByte){
//			 System.out.println(c);
//		 }
		 String str= "";
		 String str1 = "0";
		 String str2 ="0.1";
		 String str3 = "3";
		 System.out.println(Validate.isInteger(str));
		 System.out.println(Validate.isInteger(str1));
		 System.out.println(Validate.isInteger(str2));
		 System.out.println(Validate.isInteger(str3));
		 CommentDaoImpl dao = new CommentDaoImpl();
		 
	}

}
