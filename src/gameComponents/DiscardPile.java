package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import frame.JPanelNLM;

public class DiscardPile{
	public static int width = 15+Card.smallWidth*14;
	public static int height = Card.smallHeight*3;
	private static int numCards = 0;
	public static JPanelNLM discardPic = new JPanelNLM() {
		@Override
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, width-1, height-1);
			g.setColor(Color.WHITE);
			g.fillRect(1, 1, width-2, height-2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Algerian", Font.PLAIN, 35));
			g.drawString("DISCARD", 1, Card.smallHeight-g.getFontMetrics().getDescent());
		}
	};
	
	public static void initialize() {
		discardPic.setSize(width, height);
	}

	public static void addCard(Card c) {
		for (MouseListener ml : c.getMouseListeners()) {
			c.removeMouseListener(ml);
		}
		discardPic.add(c);
		c.setLocation(numCards%14 + Card.smallWidth*(numCards%14)+1, Card.smallHeight*(numCards/14+1)-1);
		numCards++;
	}
	
	private DiscardPile() {
	}
}
