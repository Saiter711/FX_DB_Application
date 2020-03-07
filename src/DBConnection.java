package application;

import java.sql.*;
import javafx.scene.control.Alert;


public class DBConnection {
	private static Connection conn;
	static {
	    try {
	        Class.forName ("oracle.jdbc.OracleDriver");
	    } 
	    catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	public static Connection getConnection(String login, String password) {
		String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:$$$$:####";
		String DB_USER = login;
		String DB_PASS = password;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} 
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error to database connection");
			alert.setContentText("Details: "+ exc.getMessage());
			alert.show();
		}
		return conn;
	}
}
