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
		 
		Connection connection=null;
		try {
			 connection= C3PODataSource.getConn(); 
			String sql = "select school_name from school";
			PreparedStatement stat = connection.prepareStatement(sql);
			ResultSet set =stat.executeQuery();
			 
			while(set.next()){
				 
			}
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(connection !=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			 
		}
	}

}
