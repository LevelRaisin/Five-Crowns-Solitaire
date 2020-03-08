package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import frame.Button;
import frame.JPanelNLM;

public class ClearPanel extends JPanelNLM{
	ArrayList<Card> groups;
	Button[] buttons = new Button[2];
	boolean noButton = true;
	MouseListener cardReturn = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Card temp = ((Card)arg0.getComponent());
			temp.setHighlight(false);
			removeCard(temp, false);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			((Card)arg0.getComponent()).setHighlight(true);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			((Card)arg0.getComponent()).setHighlight(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public ClearPanel() {
		super();
		groups = new ArrayList<Card>();
		this.setSize(DiscardPile.width, DiscardPile.height);
		buttons[0] = new Button(DiscardPile.width-Card.smallWidth*3-5, 2, Card.smallWidth*3, Card.smallHeight, "Cancel", 15, true) {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (Card c : groups) {
					GameLogic.p[GameLogic.groupCurClearing].addCard(c);
				}
				GameLogic.p[GameLogic.groupCurClearing].resetCards();
				GameLogic.instructions.setText("You may now do two things. Either select a card to discard, or press the number if you wish to try and clear the hand.");
			}
			
		};
		buttons[0].setLocation(buttons[0].x, buttons[0].y);
		this.add(buttons[0]);
		
		buttons[1] = new Button(DiscardPile.width-Card.smallWidth*3-5, 4 + Card.smallHeight, Card.smallWidth*3, Card.smallHeight, "Clear", 15, true) {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//working = true;
				super.mouseExited(arg0);
				arg0.getComponent().getParent().remove(buttons[1]);
				noButton = true;
				for (int i = groups.size()-1; i>=0; i--) {
					groups.get(i).invalid = true;
					removeCard(groups.get(i), true);
				}
				if (GameLogic.p[GameLogic.groupCurClearing].isFinished()) {
					GameLogic.p[GameLogic.groupCurClearing].setInvalid();
					GameLogic.instructions.setText("Now, select a hand to which you wish to add the large card.");
				}
				//working = false;
			}
			
		};
		buttons[1].setLocation(buttons[1].x, buttons[1].y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//if (!working)
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, DiscardPile.width-1, DiscardPile.height-1);
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, DiscardPile.width-2, DiscardPile.height-2);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Algerian", Font.PLAIN, 35));
		g.drawString("Clear Cards", 1, Card.smallHeight-g.getFontMetrics().getDescent());
	}
	
	public void addCard(Card c) {
		for (MouseListener ml : c.getMouseListeners()) {
			c.removeMouseListener(ml);
		}
		if (!c.invalid)
			c.addMouseListener(cardReturn);
		this.add(c);
		groups.add(c);
		c.setLocation((groups.size()-1)%14 + Card.smallWidth*((groups.size()-1)%14)+1, Card.smallHeight*2-1);
		if (ComboChecker.checkCombo(groups, GameLogic.groupCurClearing+3)) {
			if (noButton)
				this.add(buttons[1]);
			noButton = false;
		}
		else {
			noButton = true;
			this.remove(buttons[1]);
		}
	}
	
	private void redrawCards() {
		int i = 0;
		for (Card c : groups) {
			c.setLocation((i)%14 + Card.smallWidth*((i++)%14)+1, c.getY());
		}
	}
	
	public void removeCard(Card c, boolean cleared) {
		c.removeMouseListener(cardReturn);
		groups.remove(c);
		this.remove(c);
		if (!c.invalid)
			c.addMouseListener(GameLogic.p[GameLogic.groupCurClearing].clearListener);
		if (cleared)
			GameLogic.p[GameLogic.groupCurClearing].addInvalidCard(c);
		else {
			redrawCards();
			GameLogic.p[GameLogic.groupCurClearing].addCard(c);
			if (ComboChecker.checkCombo(groups, GameLogic.groupCurClearing+3)) {
				if (noButton)
					this.add(buttons[1]);
				noButton = false;
			}
			else {
				noButton = true;
				this.remove(buttons[1]);
			}
		}
	}
}
