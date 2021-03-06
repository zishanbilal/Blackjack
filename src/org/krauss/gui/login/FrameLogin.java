package org.krauss.gui.login;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.krauss.entity.Player;
import org.krauss.game.DatabaseHandler;
import org.krauss.gui.admin.FrameAdmin;
import org.krauss.gui.game.FrameGame;


@SuppressWarnings("serial")
public class FrameLogin extends JFrame {

	// Container components
	private PanelLogin panelLogin;

	// Game Objects
	private Player player;
	private Player admin;
	private DatabaseHandler dbHandler;
	private Timer gameTimer;
	private Timer adminTimer;
	private String playerLoginMsg = "<html><b><font color=\"#00FF22\"><br>Well done mate!</font></b></html>";
	private String adminLoginMsg = "<html><b><font color=\"#22BBFF\"><br>Welcome back mate!</font></b></html>";

	public FrameLogin() {
		
		this.setTitle("\u2663 \u2665    The BlackJack Game   \u2660 \u2666");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(550, 600));
		this.setSize(550, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		createJPanelLogin();

		this.setVisible(true);
	}

	private void createJPanelLogin() {

		// Creates de Login panel using the BJPanelLogin class
		panelLogin = new PanelLogin();
		// Adds the login action to the button
		
		panelLogin.getJb_login().addActionListener(new ACLogin());
		this.add(panelLogin);

		// Initialize the Game timer. Just to give an impression of being loading.
		gameTimer = new Timer(1500, null);
		gameTimer.setRepeats(false);
		gameTimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createJPanelGame();
			}
		});

		// Initialize the Game timer. Just to give an impression of being loading.
		adminTimer = new Timer(1500, null);
		adminTimer.setRepeats(false);
		adminTimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createPanelAdmin();
			}
		});

	}
	
	//Creates the Game frame after successful login
	private void createJPanelGame() {
		new FrameGame(player);
		this.setVisible(false);
		gameTimer.stop();

	}
	
	//Creates the Admin Frame after successful login
	private void createPanelAdmin() {
		new FrameAdmin(admin);
		this.setVisible(false);
		adminTimer.stop();

	}

	private class ACLogin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//If it's admin login
			if (panelLogin.getJt_login().getText().equalsIgnoreCase("admin")) {
				
				if ((panelLogin.getJt_login().getText().trim().length() != 0)
						&& (panelLogin.getJt_password().getPassword().length != 0)) {
					try {

						dbHandler = DatabaseHandler.getDbHandler();
						admin = dbHandler.authenticateAdmin(panelLogin.getJt_login().getText(),
								panelLogin.getJt_password().getPassword());

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (admin != null) {

						panelLogin.getJl_login_error().setText(adminLoginMsg);
						panelLogin.getJb_login().setText("OK");
						panelLogin.getJb_login().setEnabled(false);
						adminTimer.start();

					} else {
						panelLogin.getJl_login_error()
								.setText("<html>&#x26D4; <b>Mate, your password's wrong!</b></html>");
					}

				}
			
			//If it's a normal player login
			} else {

				if (panelLogin.getJc_createPlayer().isSelected()) {
					dbHandler = DatabaseHandler.getDbHandler();
					
					//Create the object Player first
					player = new Player(panelLogin.getJt_login().getText());
					
					//Insert it into the database
					dbHandler.insertNewUser(player, panelLogin.getJt_password().getPassword());

					
					panelLogin.getJl_login_error().setText(playerLoginMsg);
					panelLogin.getJb_login().setText("OK");
					panelLogin.getJb_login().setEnabled(false);
					// It executes the actionPerformed method from the
					// ActionListener previously defined
					gameTimer.start();
					panelLogin.removeCreationPanel();

				} else if ((panelLogin.getJt_login().getText().trim().length() != 0)
						&& (panelLogin.getJt_password().getPassword().length != 0)) {
					try {

						dbHandler = DatabaseHandler.getDbHandler();
						player = dbHandler.authenticate(panelLogin.getJt_login().getText(),
								panelLogin.getJt_password().getPassword());

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (player != null) {

						panelLogin.getJl_login_error().setText(playerLoginMsg);
						panelLogin.getJb_login().setText("OK");
						panelLogin.getJb_login().setEnabled(false);
						gameTimer.start();

					} else {
						panelLogin.getJl_login_error().setText("<html>&#x26D4; <b>Mate, your password's wrong!</b></html>");
					}

				}
			}
		}

	}
}
