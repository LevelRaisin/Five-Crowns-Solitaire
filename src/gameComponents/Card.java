package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import frame.JPanelNLM;

@SuppressWarnings("serial")
public class Card extends JPanelNLM{
	private String name;
	private Color suit;
	private int value;
	private int suitNum;
	
	public static int smallWidth = 24;
	public static int smallHeight = 44;
	public static int bigWidth = smallWidth*3;
	public static int bigHeight = smallHeight*3;
	
	private boolean highlight;
	private boolean small;
	private boolean removable = false;
	
	public boolean joker;
	
	public boolean invalid = false;
	
	public Card() {
		this(15, 10, true);
	}
	
	public Card(Card c) {
		this.value = c.value;
		name = valueToName(value);
		suitNum = c.suitNum;
		this.suit = suitNumToName(suitNum);
		//this.addMouseListener(this);
		small=true;
		this.setSize(smallWidth, smallHeight);
		this.setBackground(new Color(0,0,0,0));
		this.joker = c.joker;
		this.invalid = c.invalid;
		for (MouseListener ml : c.getMouseListeners())
			this.addMouseListener(ml);
	}
	
	public Card (int value, int suit) throws IllegalArgumentException{
		this(value, suit, false);
	}
	
	private Card(int value, int suit, boolean joker) {
		this.value = value;
		name = valueToName(value);
		suitNum = suit;
		this.suit = suitNumToName(suit);
		//this.addMouseListener(this);
		small=true;
		this.setSize(smallWidth, smallHeight);
		this.setBackground(new Color(0,0,0,0));
		this.joker = joker;
	}
	
	public void setJoker() {
		if(!joker) {
			joker = true;
			suitNum+=5;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Color getSuit() {
		return suit;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getSuitNum() {
		return suitNum;
	}
	
	public String toString() {
		return name + " of " + suit;
	}
	
	public int hashCode() {
		return value*100+suitNum;
	}
	
	public boolean getHighlight() {
		return highlight;
	}
	
	public void setHighlight(boolean val) {
		highlight = val;
	}
	
	public boolean getRemovable() {
		return removable;
	}
	
	public void setRemovable (boolean b) {
		removable = b;
	}
	
	public void setSize(boolean s) {
		small = s;
		if (small)
			this.setSize(smallWidth, smallHeight);
		else
			this.setSize(bigWidth, bigHeight);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (small) {
			g.setFont(new Font("Monospace", Font.BOLD, 10));
			g.setColor(Color.WHITE);
			g.fillRect(2, 2, smallWidth-5, (smallHeight-5));
			g.setColor(suit);
			g.fillRect(2, 2, smallWidth-5, (smallHeight-5)/3);
			g.fillRect(2, smallHeight-2-(smallHeight-5)/3, smallWidth-5, (smallHeight-5)/3);
			if (joker) {
				g.setColor(Color.WHITE);
				g.drawString("&", (smallWidth - g.getFontMetrics().stringWidth("&"))/2, (smallHeight-5)/3-2);
			}
			g.setColor(Color.BLACK);
			g.drawRect(2, 2, smallWidth-5, smallHeight-5);
			//g.setColor(Color.BLACK/*new Color(255-suit.getRed(),255-suit.getGreen(),255-suit.getBlue())*/);
			g.setFont(new Font("Monospace", Font.BOLD, (smallHeight-4)/3));
			g.drawString(name, (smallWidth-10)/2, ((smallHeight-4)/4)*3-2);
			if (highlight) {
				g.setColor(Color.RED);
				g.drawRect(0, 0, smallWidth-1, smallHeight-1);
				g.drawRect(1, 1, smallWidth-3, smallHeight-3);
			}
			if (invalid) {
				g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
				g.fillRect(2, 2, smallWidth-5, (smallHeight-5));
			}
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, bigWidth-1, (bigHeight-1));
			g.setColor(suit);
			g.fillRect(0, 0, bigWidth-1, (bigHeight-1)/3);
			g.fillRect(0, bigHeight-(bigHeight-1)/3, bigWidth-1, (bigHeight-1)/3);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, bigWidth-1, (bigHeight-1));
			//g.setColor(Color.BLACK/*new Color(255-suit.getRed(),255-suit.getGreen(),255-suit.getBlue())*/);
			g.setFont(new Font("Monospace", Font.BOLD, (bigHeight-4)/3));
			g.drawString(name, (bigWidth-g.getFontMetrics().stringWidth(name))/2, (bigHeight+g.getFontMetrics().getAscent())/2);
			/*if (highlight) {
				g.setColor(Color.RED);
				g.drawRect(0, 0, bigWidth-1, bigHeight-1);
				g.drawRect(1, 1, bigWidth-3, bigHeight-3);
			}*/
						
		}
	}
	
	public static String valueToName(int value) throws IllegalArgumentException {
		switch(value) {
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return "" + value;
		case 10:
			return "T";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13: 
			return "K";
		case 15:
			return "&";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public static Color suitNumToName(int suit) throws IllegalArgumentException{
		switch (suit) {
		case 0:
		case 5:
			return Color.BLACK;
		case 1:
		case 6:
			return Color.GREEN;
		case 2:
		case 7:
			return Color.BLUE;
		case 3:
		case 8:
			return Color.RED;
		case 4:
		case 9:
			return Color.ORANGE;
		case 10:
			return Color.MAGENTA;
		default:
			throw new IllegalArgumentException();	
		}
	}
	
	public int compareWith(Card c, boolean valueFirst){
		int tempValueC = (c.joker?14:c.value);
		int tempValueCur = (joker?14:this.value);
		if (c.suit == this.suit && c.value == this.value) {
			return 0;
		}
		else if (valueFirst) {
			if (tempValueC == tempValueCur) {
				return c.suitNum - this.suitNum;
			} else {
				return tempValueC-tempValueCur;
			}
		} else {
			if (c.suitNum == this.suitNum) {
				return tempValueC - tempValueCur;
			} else {
				return c.suitNum-this.suitNum;
			}
		}
	}
}
