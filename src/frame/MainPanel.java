package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gameComponents.*;

@SuppressWarnings("serial")
public class MainPanel extends JPanelNLM{
	public static GameLogic g;
	public static JPanelNLM endPanel;
	private static boolean win;
	
	public MainPanel() {
		this.setSize(GameLogic.width, GameLogic.height);
		g = new GameLogic();
		this.add(g);
		g.setLocation(0, 0);
		
		endPanel = new JPanelNLM() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.CYAN);
				g.setFont(new Font("Elephant", Font.PLAIN, 75));
				String temp = "You " + (win?"WIN":"LOSE")+"!"; 
				g.drawString(temp, (GameLogic.width-g.getFontMetrics().stringWidth(temp))/2, GameLogic.height/2+g.getFontMetrics().getDescent());
			}
		};
		endPanel.setBackground(Color.PINK);
		endPanel.setSize(GameLogic.width, GameLogic.height);
		endPanel.setLocation(0,0);
	}
	
	public void endGame (boolean win) {
		MainPanel.win = win;
		this.remove(g);
		this.add(endPanel);
	}
}
