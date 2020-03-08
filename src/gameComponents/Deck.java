package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import frame.JPanelNLM;

public class Deck{
	public Card[] cards;
	private int numCards = 58*2;
	public int curCard = 0;
	public JPanelNLM deckPicture;
	
	public Deck() {
		deckPicture = new JPanelNLM() {
			@Override
			public void paintComponent (Graphics g) {
				g.setColor(Color.CYAN);
				g.fillRect(0,0,Card.bigWidth-1,Card.bigHeight-1);
				g.setColor(Color.BLACK);
				g.drawRect(0,0,Card.bigWidth-1,Card.bigHeight-1);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				g.setColor(Color.WHITE);
				String text = ""+(numCards-curCard);
				String adText = "LEFT";
				int width = g.getFontMetrics().stringWidth(text), height = g.getFontMetrics().getAscent();
				g.fillRect((Card.bigWidth-g.getFontMetrics().stringWidth(adText))/2, (Card.bigHeight)/2,g.getFontMetrics().stringWidth(adText)+1,g.getFontMetrics().getHeight()+1);
				g.fillRect((Card.bigWidth-width)/2, (Card.bigHeight)/2-height, width+1, g.getFontMetrics().getHeight()+1);
				g.setColor(Color.BLACK);
				g.drawString(adText, (Card.bigWidth-g.getFontMetrics().stringWidth(adText))/2, (Card.bigHeight)/2+height);
				g.drawString(text, (Card.bigWidth-width)/2, (Card.bigHeight)/2-g.getFontMetrics().getDescent()/2);
			}
		};
		
		deckPicture.setSize(Card.bigWidth, Card.bigHeight);
		
		int curCard = 0;
		cards = new Card[numCards];
		for (int k = 0; k<2; k++) {
			for (int i = 0; i<5; i++) {
				for (int j = 3; j<=13; j++) {
					try {
						cards[curCard++] = new Card(j,i);
					}catch (IllegalArgumentException e) {
						System.out.println("aaaah");
					}
				}
			}
			for (int i = 0; i<3; i++) {
				try {
					cards[curCard++] = new Card();
				}catch (IllegalArgumentException e) {
					System.out.println("aaaah");
				}
			}
		}
		shuffle();
		shuffle();
	}
	
	private void shuffle() {
		Card temp;
		int rand;
		for (int i = 0; i<numCards; i++) {
			rand = (int)(Math.random()*numCards);
			temp = cards[i];
			cards[i] = cards[rand];
			cards[rand] = temp;
		}
	}
	
	public void seeDeck() {
		for (Card c : cards) {
			System.out.println(c);
		}
	}
	
	public Card getCard() {
		return cards[curCard++];
	}
}
