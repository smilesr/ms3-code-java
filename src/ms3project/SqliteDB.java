package ms3project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteDB {
	Connection c = null;
	Statement stmt = null;
	
	SqliteDB(){
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ms3interviews.db");
			System.out.println("Connected to DB");
		} catch (Exception e) {
			System.out.println("Error1: " + e.getMessage());
		}
	}


public void getRecords() {
	try {
		this.stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM test");
		while(rs.next()) {
			int id = rs.getInt("primary_key");
			String firstName = rs.getString("name");
			int personAge = rs.getInt("age");
			System.out.println(id + "    " + firstName + "  " + personAge);
		}

		
	} catch(Exception e) {
		System.out.println("Error2: " + e.getMessage());
	}
	
}

public void insertRecords(String query ) {
	try {
		this.stmt = c.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
	} catch (Exception e) {
		System.out.println("Error3: " + e.getMessage());
	}
}

public void closeConnection() {
	try {
		c.close();
	} catch (Exception e) {
		System.out.println("Error4: " + e.getMessage());
	}
}
}