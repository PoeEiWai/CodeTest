package com.demo.data;

import java.sql.Connection;
import java.sql.DriverManager;


public class conManager {

	public static Connection getConnection() {
		Connection conn=null;
		try {
		//	Class.forName("com.mysql.jdbc.Driver");
//			conn=DriverManager.getConnection(" jdbc:mysql://localhost:3306/payment", "root",
//					"demo");
			Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 2: Establish a connection using the JDBC URL
            String url = "jdbc:mysql://localhost:3306/payment";
            String username = "root";
            String password = "demo";
            conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}

}
