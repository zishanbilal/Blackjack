package com.eca.assignment.gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author jrkrauss <br>
 *         <br>
 *         This class defines the panel of the game. All the components are
 *         setup in this class.
 *
 */
@SuppressWarnings("serial")
public class PanelGame extends JPanel {

	// Dealer components
	private JPanel gamePanelDealer;
	private JPanel dealerCardsPanel;
	private JPanel controlPanelDealer;
	private JPanel replayPanelDealer;
	private JLabel dealerCardslabel;
	private JLabel dealerSum;
	private JLabel dealerHints;
	private JLabel dealerName;
	private JLabel dealerScore;
	private JButton jb_replay;

	// Player components
	private JPanel gamePanelPLayer;
	private JPanel playerCardsPanel;
	private JPanel controlPanelPLayer;
	private JPanel hitStandPanel;
	private JButton jb_hit;
	private JButton jb_stand;
	private JLabel playerCardslabel;
	private JLabel playerSum;
	private JLabel playerName;
	private JLabel playerScore;
	private JLabel winLoseLabel;
	private Font defaultFont = new Font("arial", Font.BOLD, 13);

	// Bet Panel
	private JPanel overlayPanel;
	private JPanel transparentPanel;
	private JPanel finishedGamePanel;

	public PanelGame() {

		this.setLayout(new MigLayout("", "[grow, center]", "[center][center]"));
		this.setBackground(Color.BLACK);

		createPanelGameDealer();
		createPanelGamePlayer();
		createBetPanel();

	}

	

	// The Dealer side of the game
	private void createPanelGameDealer() {
		gamePanelDealer = new JPanel();
		gamePanelDealer.setLayout(new MigLayout("", "[grow]", "[center][center]"));
		gamePanelDealer.setBackground(Color.BLACK);
		this.add(gamePanelDealer, "growx, gap 15 15 15 15, wrap");

		// Creates the panel with the two buttons, HIT and STAND
		dealerCardsPanel = new JPanel();
		dealerCardsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
		dealerCardsPanel.setBackground(new Color(0x03853E));
		dealerCardsPanel.setLayout(new MigLayout("", "5[grow]5", "5[100]5"));

		// Creates the control panel
		controlPanelDealer = new JPanel();
		controlPanelDealer.setBorder(BorderFactory.createEtchedBorder());
		controlPanelDealer.setBackground(Color.BLACK);
		controlPanelDealer.setLayout(new MigLayout("", "5[grow]5", "5[]5"));

		dealerSum = new JLabel("Sum: ");
		dealerHints = new JLabel("Hints:  ");
		dealerName = new JLabel("Name:  Dealer");
		dealerScore = new JLabel();

		dealerSum.setFont(defaultFont);
		dealerSum.setForeground(Color.WHITE);
		dealerHints.setFont(defaultFont);
		dealerHints.setForeground(Color.WHITE);
		dealerName.setFont(defaultFont);
		dealerName.setForeground(Color.WHITE);
		dealerScore.setFont(defaultFont);
		dealerScore.setForeground(Color.WHITE);

		controlPanelDealer.add(dealerName, "wrap");
		controlPanelDealer.add(dealerSum, "wrap");
		controlPanelDealer.add(dealerHints, "wrap");
		controlPanelDealer.add(dealerScore);

		gamePanelDealer.add(controlPanelDealer, "align center, wrap");
		gamePanelDealer.add(dealerCardsPanel, "align center, growx, wrap");

	}

	// The Player side of the game
	private void createPanelGamePlayer() {
		// Creates the player back panel
		gamePanelPLayer = new JPanel();
		gamePanelPLayer.setLayout(new MigLayout("", "[grow]", "[center][center]"));
		gamePanelPLayer.setBackground(Color.BLACK);
		this.add(gamePanelPLayer, "gap 15 15 0 15, growx");

		// Creates the panel with the two buttons, HIT and STAND
		playerCardsPanel = new JPanel();
		playerCardsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
		playerCardsPanel.setBackground(new Color(0x03853E));
		playerCardsPanel.setLayout(new MigLayout("", "5[grow]5", "5[]5"));

		// Creates the control panel
		controlPanelPLayer = new JPanel();
		controlPanelPLayer.setBorder(BorderFactory.createEtchedBorder());
		controlPanelPLayer.setBackground(Color.BLACK);
		controlPanelPLayer.setLayout(new MigLayout("", "5[grow]5", "5[]5"));

		playerSum = new JLabel();
		playerName = new JLabel();
		playerScore = new JLabel();

		playerSum.setFont(defaultFont);
		playerSum.setForeground(Color.WHITE);
		playerName.setFont(defaultFont);
		playerName.setForeground(Color.WHITE);
		playerScore.setFont(defaultFont);
		playerScore.setForeground(Color.WHITE);

		controlPanelPLayer.add(playerName, "wrap");
		controlPanelPLayer.add(playerSum, "wrap");
		controlPanelPLayer.add(playerScore);

		// Creates the panel with the two buttons, HIT and STAND
		hitStandPanel = new JPanel();
		hitStandPanel.setBackground(Color.BLACK);
		hitStandPanel.setLayout(new MigLayout("", "5[145]5", "5[30]15[30]5"));

		jb_hit = new JButton("Hit");
		jb_stand = new JButton("Stand");

		hitStandPanel.add(jb_hit, "w 80!, right");
		hitStandPanel.add(jb_stand, "w 80!, left, wrap");

		gamePanelPLayer.add(playerCardsPanel, "growx, align center, wrap");
		gamePanelPLayer.add(controlPanelPLayer, "align center, wrap");
		gamePanelPLayer.add(hitStandPanel, "center");
	}
	
	
	private void createBetPanel() {
		overlayPanel = new JPanel();
		overlayPanel.setSize(750, 690);
		overlayPanel.setPreferredSize(new Dimension(750, 690));
		overlayPanel.setLayout(new OverlayLayout(overlayPanel));

		transparentPanel = new JPanel();
		transparentPanel.setLayout(new MigLayout("", "[750]", "[690]"));
		transparentPanel.setBackground(new Color(0, 0, 0, 90));

		finishedGamePanel = new JPanel();
		finishedGamePanel.setLayout(new MigLayout("", "10[280]10", "10[45]10[45]10"));
		finishedGamePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
		finishedGamePanel.setBackground(Color.DARK_GRAY);

		winLoseLabel = new JLabel();
		winLoseLabel.setFont(new Font("arial", Font.BOLD, 17));
		winLoseLabel.setForeground(Color.RED);

		jb_replay = new JButton(" Replay ");

		finishedGamePanel.add(winLoseLabel, "growx, center, wrap");
		finishedGamePanel.add(jb_replay, "w 100!, center");

		transparentPanel.add(finishedGamePanel, "center");
		overlayPanel.add(transparentPanel, BorderLayout.CENTER);
		overlayPanel.validate();

	}
	
	public JPanel getBetPanel() {

		return overlayPanel;

	}

	public JPanel getGamePanelPLayer() {
		return gamePanelPLayer;
	}

	public JPanel getGamePanelDealer() {
		return gamePanelDealer;
	}

	public JPanel getHitStandPanel() {
		return hitStandPanel;
	}

	public JPanel getPlayerCardsPanel() {
		return playerCardsPanel;
	}

	public JPanel getDealerCardsPanel() {
		return dealerCardsPanel;
	}

	public JPanel getControlPanelPLayer() {
		return controlPanelPLayer;
	}

	public JButton getJb_hit() {
		return jb_hit;
	}

	public JButton getJb_stand() {
		return jb_stand;
	}

	public JLabel getPlayerCardslabel() {
		return playerCardslabel;
	}

	public JLabel getWinLoseLabel() {
		return winLoseLabel;
	}

	public JLabel getPlayerName() {
		return playerName;
	}

	public JLabel getPlayerScore() {
		return playerScore;
	}

	public JLabel getPlayerSum() {
		return playerSum;
	}

	public JPanel getControlPanelDealer() {
		return controlPanelDealer;
	}

	public JLabel getDealerCardslabel() {
		return dealerCardslabel;
	}

	public JLabel getDealerSum() {
		return dealerSum;
	}

	public JLabel getDealerName() {
		return dealerName;
	}

	public JLabel getDealerScore() {
		return dealerScore;
	}

	public JLabel getDealerHints() {
		return dealerHints;
	}

	public JPanel getReplayPanelDealer() {
		return replayPanelDealer;
	}

	public JButton getJb_replay() {
		return jb_replay;
	}

	public JPanel getOverlayPanel() {
		return overlayPanel;
	}

}