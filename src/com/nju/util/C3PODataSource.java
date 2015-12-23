package com.nju.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3PODataSource {
	private static ComboPooledDataSource cpds = null;
	private C3PODataSource(){
		 cpds= new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "com.mysql.jdbc.Driver" );
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//loads the jdbc driver 
		cpds.setJdbcUrl( "jdbc:mysql://114.215.105.94:3306/school_friend?useUnicode=true&characterEncoding=utf-8" ); 
		cpds.setUser("root"); 
		cpds.setPassword("1234");
		//turn on PreparedStatement pooling
		cpds.setMaxStatements(180);
		cpds.setMaxStatementsPerConnection(20);
		
	}
	
	public static void init(){
		if (cpds == null){
			new C3PODataSource();
		}
	}
	public static Connection getConn() throws SQLException{
		 init();
		return cpds.getConnection();
	}
	public static void close(){
		cpds.close();
	}
	
}
