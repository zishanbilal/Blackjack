package org.krauss.game;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.krauss.entity.Player;

/**
 * 
 * @author jrkrauss
 *
 *         Class responsible for the database connection, user authentication
 *         and update scores
 *
 */
public class DatabaseHandler {

	private Connection conn;
	private Statement statement;
	private ResultSet result;
	private static DatabaseHandler dbHandler;

	private DatabaseHandler() {

		try {
			
			DriverManager.registerDriver(new org.sqlite.JDBC());
			URL pathToDB = getClass().getResource("/database/blackjack.db");			
			if (pathToDB != null) {
				
				conn = DriverManager.getConnection("jdbc:sqlite::resource:"+pathToDB);
				
		    } 		

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//Singleton implementation
	public static DatabaseHandler getDbHandler() {
		
		if (dbHandler == null ) {
			
			dbHandler = new DatabaseHandler();
			
		}
		
		return dbHandler;
		
	}
	

	public Player authenticateAdmin(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);

		statement = conn.createStatement();
		result = statement.executeQuery("select * from Login where username = '" + user + "';");
		if (result.next()) {
			
			if (result.getString("password").equalsIgnoreCase(password)) {
				
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));
				updateLastLogin(statement, p);
				
			}
		}

		return p;

	}

	public Player authenticate(String user, char[] pass) throws SQLException {
		Player p = null;
		String password = String.copyValueOf(pass);

		statement = conn.createStatement();
		result = statement.executeQuery("select * from Login where username = '" + user + "';");
		if (result.next()) {
			
			if (result.getString("password").equalsIgnoreCase(password.hashCode() + "")) {
				
				p = new Player(result.getString("username"));
				p.setScore(result.getInt("score"));
				updateLastLogin(statement, p);
				
			}
			
		}
		
		return p;

	}

	private void updateLastLogin(Statement statement, Player user) {

		try {
			
			statement.executeUpdate("update Login set lastLogin = '" + getDate() + "' where username = '" + user.getPlayerName() + "';");
			user.setLastLogin(getDate());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void refreshPlayerData(Player p) {
		try {
			
			statement = conn.createStatement();
			result = statement.executeQuery("select * from Login where username = '" + p.getPlayerName() + "';");
			if (result.next()) {
				p.setScore(result.getInt("score"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Increase the user's score to plus 50
	public void setPlayerScore(Player p) {

		try {

			statement = conn.createStatement();
			statement.executeUpdate("update Login set score = score + 50 where username = '" + p.getPlayerName() + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// Increase the user's score to plus 50
		public void updatePlayerPassword(String playername, char[] newPass) {
			String password = String.valueOf(newPass);

			try {

				statement = conn.createStatement();
				statement.executeUpdate("update Login set password = '"+password.hashCode()+"' where username = '" + playername + "';");

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	public boolean checkExistingUser(String userName) {
		boolean result = false;

		try {

			statement = conn.createStatement();
			ResultSet r = statement.executeQuery("Select username from Login where username = '" + userName + "';");
			if (r.next()) {
				if (r.getString("username").equalsIgnoreCase(userName)) {
					result = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void insertNewUser(Player p, char[] pass) {

		String userPass = String.copyValueOf(pass);
		int nextValidId = 0;

		try {

			statement = conn.createStatement();
			nextValidId = statement.executeQuery("select max(idLogin)+2 as maxId from Login;").getInt("maxId");
			statement = conn.createStatement();
			statement.execute("insert into Login values ('" + nextValidId + "', '" + p.getPlayerName() + "', '"
					+ userPass.hashCode() + "', '" + p.getPlayerName() + "', 0, '" + getDate() + "');");

			// Update the lastLogin field on the Player object
			p.setLastLogin(getDate());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Player> getDBData() {

		ArrayList<Player> players = new ArrayList<Player>();

		try {

			statement = conn.createStatement();
			ResultSet r = statement.executeQuery("Select username, score, lastlogin from Login;");
			while (r.next()) {
				
				players.add(new Player(r.getString("username"), r.getInt("score"), r.getString("lastLogin")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

	
	public void deletePlayer(String playername) {
		
		try {
			
			statement = conn.createStatement();			
			statement.executeUpdate("Delete from Login where username = '" + playername + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getDate() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm"));
		return date;
	}
}
