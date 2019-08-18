package com.eca.assignment.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.eca.assignment.entity.Player;

/**
 * 
 * @author jrkrauss
 *
 *         Class responsible for the database connection, user authentication
 *         and update scores
 *
 */
public class DBConnection {

	private Connection conn;
	private Statement statement;
	private ResultSet result;

	
	public DBConnection() {

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:blackjack.db");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Player authenticate(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);

		statement = conn.createStatement();

		result = statement.executeQuery("select * from Login where username = '" + user + "';");

		if (result.next()) {
			if (result.getString("password").equalsIgnoreCase(password.hashCode()+"")) {
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));
				p.setName(result.getString("name"));
			}
		}

		conn.close();
		return p;

	}

	public void refreshPlayerData(Player p) {
		try {
			statement = conn.createStatement();
			result = statement.executeQuery("select * from Login where username = '" + p.getUserName() + "';");

			if (result.next()) {
				p.setScore(result.getInt("score"));
				p.setName(result.getString("name"));
			}

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	// Increase the user's score to plus 100
	public void setPlayerScore(Player p) {

		try {

			statement = conn.createStatement();
			statement.executeUpdate("update Login set score = score + 50 where username = '" + p.getUserName() + "';");

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public boolean checkExistingUser(String userName){
		boolean result = false;
		
		try {
			
			statement = conn.createStatement();
			ResultSet r = statement.executeQuery("Select username from Login where username = '"+userName+"';");
			
			if(r.next()){
				if(r.getString("username").equalsIgnoreCase(userName)){				
					result = true;
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result; 
	}
	
	public void insertNewUser(String userName, char[] userPassword, String name){
		String userPass = "";
		int nextValidId = 0;
		
		for (char c : userPassword) {
			userPass += c;
		}
		
		try {
			
			statement = conn.createStatement();	
			
			nextValidId = statement.executeQuery("select max(idLogin)+2 as maxId from Login;").getInt("maxId");
			
			statement = conn.createStatement();
			
			statement.execute("insert into Login values ('"+nextValidId+"', '"+userName+"', '"+userPass.hashCode()+"', '"+name+"', 0);"); 
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}