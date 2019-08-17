package db.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// This is java.sql.Connection. Not com.mysql.jdbc.Connection.
			Connection conn = null;

			// Step 1 Connect to MySQL.
			try {
				System.out.println("Connecting to " + MySQLDBUtil.URL);
				Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
				conn = DriverManager.getConnection(MySQLDBUtil.URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}


			if (conn == null) {
				return;
			}
			
			
			// Step 2 Drop tables in case they exist.
			Statement stmt = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS categories";
			stmt.executeUpdate(sql);
						
			sql = "DROP TABLE IF EXISTS history";
			stmt.executeUpdate(sql);
						
			sql = "DROP TABLE IF EXISTS items";
			stmt.executeUpdate(sql);
						
			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);

			
			// Step 3 Create new tables
			sql = "CREATE TABLE items ("
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "name VARCHAR(255),"
					+ "rating FLOAT,"
					+ "address VARCHAR(255),"
					+ "image_url VARCHAR(255),"
					+ "url VARCHAR(255),"
					+ "distance FLOAT,"
					+ "PRIMARY KEY (item_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE categories ("
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "category VARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (item_id, category),"
					+ "FOREIGN KEY (item_id) REFERENCES items(item_id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "PRIMARY KEY (user_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE history ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "PRIMARY KEY (user_id, item_id),"
					+ "FOREIGN KEY (item_id) REFERENCES items(item_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);
			
			
			// Step 4: insert data
			sql = "INSERT INTO users VALUES (" +
			"'1111', '3229c1097c00d497a0fd282d586be050', 'John', 'Smith')";
			System.out.println("Executing query: " + sql); 
			stmt.executeUpdate(sql);
			 

			System.out.println("Import is done successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
