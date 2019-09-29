package ms3project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

public void createTable(List<String> columnNames) {
    System.out.println(columnNames);
	try {
	    this.stmt = c.createStatement();
	    System.out.println(columnNames);
	    String sql = "CREATE TABLE Interviews (";
	    String columnHeadings = "";
	    String endStatement = ");";
	    for(String columnName : columnNames) {
	    		System.out.println(columnName);
	    		columnHeadings += " " + columnName + " TEXT,";
	    }
	    sql += columnHeadings;
	    sql = sql.substring(0, sql.length() - 1);
	    sql += endStatement;
	    
	    System.out.println(sql);
	    stmt.executeQuery(sql);

	} catch (Exception e) {
		System.out.println("Error5: " + e.getMessage());
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
		stmt.executeQuery(query);
		
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