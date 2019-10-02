package ms3project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
			System.out.println(e.getMessage());
		}
	}

	public void createTable(List<String> columnNames) {
		try {
			this.stmt = c.createStatement();
			String sql = "CREATE TABLE Interviews (";
			String columnHeadings = "";
			String endStatement = ");";
			for(String columnName : columnNames) {
	    			columnHeadings += " " + columnName + " TEXT,";
			}
			sql += columnHeadings;
			sql = sql.substring(0, sql.length() - 1);
			sql += endStatement;
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}

	public void insertRecord(List<String> row) {
		try {
			this.stmt = c.createStatement();
			String sql, cellString;
			String endStatement = ");";
			sql = "INSERT INTO Interviews(A,B,C,D,E,F,G,H,I,J) VALUES(";
			cellString = "";
			for (String cell: row) {
				cell = cell.replaceAll("\"","\\\\\'");
				cell = "\"" + cell + "\"" +  ",";
				cellString += cell;
			}
			cellString = cellString.substring(0, cellString.length() - 1);    		  		cellString = cellString + endStatement;
			sql += cellString;
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}	
	}

	public void closeConnection() {
		try {
			c.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}