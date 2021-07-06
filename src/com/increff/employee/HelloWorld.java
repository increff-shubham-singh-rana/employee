package com.increff.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class HelloWorld {

	private static final Logger logger=Logger.getLogger(HelloWorld.class);

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
//		PropertyConfigurator.configure("log4j.properties");
//		BasicConfigurator.configure();
		Properties props = new Properties();
		InputStream inStream = new FileInputStream("employee.properties");
		props.load(inStream);
		Class.forName(props.getProperty("jdbc.driver"));
		Connection con = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"),
				props.getProperty("jdbc.password"));
		insert(con);
		select(con);
		delete(con);
		select(con);
		con.close();

	}

	private static void insert(Connection con) throws SQLException {
		logger.info("inserting employees");
		Statement stmt = con.createStatement();
		for (int i = 0; i < 3; i++) {
			int id = i;
			int age = 30 + i;
			String name = "name" + i;
			String sql = "insert into employee values (" + id + ", '" + name + "'," + age + ")";
			logger.info(sql);
			stmt.executeUpdate(sql);
		}
		stmt.close();
	}

	private static void select(Connection con) throws SQLException {
		logger.info("selecting employees");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from employee");
		while (rs.next()) {
			logger.info(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
		}
		stmt.close();
	}

	private static void delete(Connection con) throws SQLException {
		logger.info("deleting all employees ");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(" select * from employee ");
		List<Integer> idList = new ArrayList<Integer>();
		while (rs.next()) {
			idList.add(rs.getInt(1));
		}
		for (int i = 0; i < idList.size(); i++) {
			stmt.executeUpdate(" delete from employee where id = " + idList.get(i));
		}
		stmt.close();
	}

}
