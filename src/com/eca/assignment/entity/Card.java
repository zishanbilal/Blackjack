package com.eca.assignment.entity;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.eca.assignment.interfaces.ICard;

/**
 * 
 *@author <b> jrkrauss </b><br><br>
 *
 * Definition entity that represents the object 'Card' in the game
 *
 *
 */
@SuppressWarnings("serial")
public abstract class Card extends JLabel implements ICard{
	
	private String number;
	private String suit;
	private int value;
	private boolean upsideDown = false;
		
	
	/**
	 *  
	 * 
	 * @param n = randomly generated number between 1-13
	 * @param s = the suit might be one of these four: SPADES, HEARTS, DIAMONDS, CLUBS;
	 */
	public Card(int n, String s) {
		this.suit = s;
		this.setBackground(Color.WHITE);
		this.setOpaque(true);		
		this.setBorder(BorderFactory.createMatteBorder(-2, -2, -2, -2, Color.WHITE));
		this.setPreferredSize(new Dimension(65, 80));
		setValueAndNumber(n);
	}
	
	
	/**
	 * 
	 * @return the card number
	 * 
	 */
	@Override
	public String getNumber() {
		return this.number;
	}

	
	/**
	 * 
	 * @return the card suit
	 * 
	 */
	@Override
	public String getSuit() {
		return this.suit;
	}
	
	
	/**
	 * 
	 * @return the card value in equivalent points
	 * 
	 */
	@Override
	public int getValue() {
		return value;
	}
	
	/**
	 * 
	 * set the card value
	 * 
	 */
	@Override
	public void setValue(int i) {
		value = i;
	}
	
	
	/**
	 * 
	 * String representation of the object in memory
	 */
	@Override
	public String toString(){
		return getClass() + " nro: " + number + " of " + suit;
	}
	
		
	/**
	 * Assigns the points that each card has
	 * 
	 */
	private void setValueAndNumber(int n){
		switch(n){
		case 1:
			this.value = 1;
			this.number = "A";
			break;
		case 11:
			this.value = 10;
			this.number = "J";
			break;
		case 12:
			this.value = 10;
			this.number = "Q";
			break;
		case 13:
			this.value = 10;
			this.number = "K";
			break;
		default:
			this.number = n+"";	
			this.value = Integer.valueOf(number);
			break;
		}		
	}


	public boolean isUpsideDown() {
		return upsideDown;
	}
	
	
	public void setBackImage(boolean s) {}

}