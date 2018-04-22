package com.modofo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
	
	public static void createDerbyDatabase(String path,String dbname) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String protocol = "jdbc:derby:";
		Class.forName(driver).newInstance();
		System.out.println("Loaded the appropriate driver.");

		Connection conn = null;
		Properties props = new Properties();
		props.put("user", "mocar");
		props.put("password", "mocar");

		conn = DriverManager.getConnection(protocol + dbname+";create=true", props);

		System.out.println("Connected to and created database derbyDB");

		conn.setAutoCommit(false);
	}
	
	
}
