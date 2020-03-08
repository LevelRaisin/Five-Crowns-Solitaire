package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import frame.JPanelNLM;
import frame.MainFrame;
import frame.MainPanel;
import gameComponents.Card;

@SuppressWarnings("serial")
public class CardHand extends JPanelNLM implements MouseListener{
	public static int width = 4+Card.smallWidth*8;
	public static int height = 4+Card.smallHeight*2;
	private ArrayList<Card> cards;
	private int size;
	private boolean highlight = false;
	private boolean wasClicked = false;
	private JPanelNLM number;
	private Color textColor;
	public boolean invalid = false;
	private MouseListener cardListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Card temp = ((Card)arg0.getComponent());
			temp.setHighlight(false);
			temp.joker = false;
			DiscardPile.addCard(temp);
			removeCard(temp);
			number.removeMouseListener(numberListener);
			GameLogic.newCard();
			GameLogic.instructions.setText("Now, select a hand to which you wish to add the large card.");
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
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public MouseListener clearListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Card temp = ((Card)arg0.getComponent());
			temp.setHighlight(false);
			removeCard(temp);
			GameLogic.cp.addCard(temp);
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
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	private MouseListener numberListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			GameLogic.clearHand(size-3);
			number.removeMouseListener(numberListener);
			textColor = Color.BLACK;
			GameLogic.instructions.setText("Clear the hand by making books or runs. Each book or run must contain at least 3 cards. A book is a set of cards of same value. A run is a set of cards with consecutive values and the same colour. A joker or a wild card can take on any value and colour. Wild cards are those that match the number asigned to the hand. Remember, you need one card to remain after you're done clearing.");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			textColor = Color.RED;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			textColor = Color.BLACK;
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
	//private boolean[] highlight;
	
	public CardHand(int size) {
		super();
		this.size = size;
		number = new JPanelNLM() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setFont(new Font("Algerian", Font.PLAIN, 35));
				g.setColor(textColor);
				g.drawString(Card.valueToName(size), 1, Card.smallHeight-1);
			}
		};
		this.setBackground(new Color(0,0,0,0));
		number.setBackground(new Color(0,0,0,0));
		number.setSize(Card.smallWidth, Card.smallHeight*2);
		this.add(number);
		number.setLocation(2, 1);
		this.setSize(width, height);
		cards = new ArrayList<Card>();
		this.addMouseListener(this);
		//highlight = new boolean[14];
	}
	
	public boolean isFinished() {
		int countFalse = 0;
		for (Card c : cards) {
			if (!c.invalid)
				countFalse++;
		}
		return (countFalse==1);
	}
	
	public void setInvalid() {
		invalid = true;
		highlight = false;
		this.removeMouseListener(this);
		if (GameLogic.checkWinCondition())
			return;
		for (Card c : cards) {
			if (!c.invalid) {
				DiscardPile.addCard(c);
				removeCard(c);
				number.removeMouseListener(numberListener);
				GameLogic.newCard();
				break;
			}
		}
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawRect(1, 1, width-2, height-2);
		g.setColor(Color.WHITE);
		g.fillRect(2, 2, width-3, height-3);
		if (highlight) {
			g.setColor(Color.RED);
			g.drawRect(0, 0, width-1, height-1);
		}
		if (invalid) {
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			g.fillRect(1, 1, width-2, height-2);
		}
	}
	
	public int getNumCards() {
		return size;
	}
	
	public void addCard(Card c) {
		redrawCards();
		if (c.getValue() == size)
			c.setJoker();
		cards.add(c);
		drawCards();
	}
	
	public void addInvalidCard (Card c) {
		redrawCards();
		c.invalid = true;
		if (c.getValue() == size)
			c.setJoker();
		cards.add(c);
		drawCards();
	}
	
	public void resetCards() {
		redrawCards();
		for (Card c : cards) {
			c.invalid = false;
			for (MouseListener ml : c.getMouseListeners()) {
				c.removeMouseListener(ml);
			}
			c.addMouseListener(cardListener);
		}
		drawCards();
		number.addMouseListener(numberListener);
		MainPanel.g.remove(GameLogic.cp);
		MainPanel.g.add(DiscardPile.discardPic);
	}
	
	public void drawCards() {
		sort();
		outer: for (int i = 0; i<2; i++) {
			for (int j = 0; j<7; j++) {
				if (i*7+j>=cards.size()) {
					break outer;
				}
				this.add(cards.get(i*7+j));
				cards.get(i*7+j).setLocation(2+Card.smallWidth*(j+1), 2+Card.smallHeight*i);
			}
		}
	}
	
	public void redrawCards()	{
		for (Card c: cards) {
			this.remove(c);
		}
	}
	
	private void removeCard(Card c) {
		this.remove(c);
		for (Card car:cards) {
			car.removeMouseListener(cardListener);
		}
		cards.remove(c);
		redrawCards();
		drawCards();
	}
	
	public void sort() {
		int count;
		int numSame;
		int comp;
		ArrayList<Card> tempVal = new ArrayList<Card>();
		ArrayList<Card> tempInval = new ArrayList<Card>();
		for (Card c : cards) {
			if (c.invalid)
				tempInval.add(c);
			else
				tempVal.add(c);
		}
		for (Card c: tempVal) {
			count = 0;
			numSame = 0;
			for (int i = 0; i<tempVal.size();i++) {
				comp = c.compareWith(tempVal.get(i), GameLogic.sortByValue);
				if (comp == 0) {
					numSame++;
				}else if (compareWithAscending(comp)) {
					count++;
				}
			}
			for (int i = count; i<=count+numSame-1; i++) {
				cards.set(i, new Card(c));
			}
		}
		for (Card c: tempInval) {
			count = 0;
			numSame = 0;
			for (int i = 0; i<tempInval.size();i++) {
				comp = c.compareWith(tempInval.get(i), GameLogic.sortByValue);
				if (comp == 0) {
					numSame++;
				}else if (compareWithAscending(comp)) {
					count++;
				}
			}
			for (int i = count; i<=count+numSame-1; i++) {
				cards.set(i+tempVal.size(), new Card(c));
			}
		}
	}
	
	private boolean compareWithAscending(int comp) {
		if (GameLogic.ascending) {
			return comp < 0;
		} else {
			return comp > 0;
		}
	}
	
	public boolean readClick() {
		if (wasClicked) {
			wasClicked = false;
			return true;
		}
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Card temp = GameLogic.getCurCard();
		if (temp != null) {
			this.addCard(temp);
			for (Card c: cards) {
				if (!c.invalid)
					c.addMouseListener(cardListener);
			}
			number.addMouseListener(numberListener);
			GameLogic.drawCard = false;
			GameLogic.instructions.setText("You may now do two things. Either select a card to discard, or press the number if you wish to try and clear the hand.");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		highlight=true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		highlight=false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void initiateClear() {
		for (Card c : cards) {
			c.removeMouseListener(cardListener);
			if (!c.invalid)
				c.addMouseListener(clearListener);
		}
	}
}
