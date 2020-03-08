package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import frame.JPanelNLM;

public class SwitchPanel extends JPanelNLM{
	private Switch[] switches = new Switch[2];
	public SwitchPanel() {
		this.add(switches[0] =new Switch() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				on = !on;
				GameLogic.sortByValue = on;
				GameLogic.sortAll();
				switchState();
			}});
		switches[0].setLocation(CardHand.width-Switch.width-5, CardHand.height/3-Switch.height/2);
		this.add(switches[1] =new Switch() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				on = !on;
				GameLogic.ascending = on;
				GameLogic.sortAll();
				switchState();
			}});
		switches[1].setLocation(CardHand.width-Switch.width-5, 2*CardHand.height/3-Switch.height/2);
		this.setSize(CardHand.width-1, CardHand.height-1);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, CardHand.width-2, CardHand.height-2);
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, CardHand.width-2, CardHand.height-2);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		g.drawString("Sort by Value", CardHand.width-Switch.width-5 - g.getFontMetrics().stringWidth("Sort by Value")-5, CardHand.height/3-Switch.height/2+Switch.height-g.getFontMetrics().getAscent()/2);
		g.drawString("Ascending Order", CardHand.width-Switch.width-5- g.getFontMetrics().stringWidth("Ascending Order")-5, 2*CardHand.height/3-Switch.height/2+Switch.height-g.getFontMetrics().getAscent()/2);
	}
}
