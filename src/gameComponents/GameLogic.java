package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import frame.Button;
import frame.JPanelNLM;
import frame.MainFrame;
import frame.MainPanel;

@SuppressWarnings("serial")
public class GameLogic extends JPanelNLM implements MouseListener{
	private static Deck d;
	static CardHand[] p;
	public static int width = 6+CardHand.width*4;
	public static int height = 7+CardHand.height*3+Card.bigHeight;
	private static Card curCard;
	private static SwitchPanel sp;
	public static boolean drawCard = true;
	public static boolean sortByValue = true;
	public static boolean ascending = true;
	public static int groupCurClearing;
	public static ClearPanel cp = new ClearPanel();
	public static JPanelNLM emptyCard = new JPanelNLM() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Card.bigWidth-1, (Card.bigHeight-1));
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, Card.bigWidth-1, (Card.bigHeight-1));
		}
	};
	public static JTextArea instructions;
	
	public GameLogic() {
		super();
		
		this.setSize(width, height);
		this.setBackground(Color.PINK);
		this.addMouseListener(this);
		
		d = new Deck();
		p = new CardHand[11];
		
		for (int i = 0; i<11; i++) {
			p[i] = new CardHand(i+3);
			for (int j = 0; j<i+3; j++) {
				p[i].addCard(d.getCard());
			}
			this.add(p[i]);
			p[i].setLocation((i)%4+1+(i%4)*CardHand.width, 3+(i)/4+Card.bigHeight+(i/4)*CardHand.height);
			/*if (i<4) {
				p[i].setLocation(2*(i+1)+CardHand.width*i, 2+Card.bigHeight+4);
			} else if (i<7) {
				p[i].setLocation(2*(i-4)+CardHand.width*(i-4)+(width-CardHand.width*3+2)/2, 4+CardHand.height+Card.bigHeight+4);
			} else {
				p[i].setLocation(2*(i-6)+CardHand.width*(i-7), 6+CardHand.height*2+Card.bigHeight+4);
			}*/
		}
		emptyCard.setSize(Card.bigWidth, Card.bigHeight);
		emptyCard.setLocation(3+Card.bigWidth, 2);
		this.add(d.deckPicture);
		d.deckPicture.setLocation(2, 2);
		curCard = d.getCard();
		curCard.setSize(false);
		curCard.setLocation(3+Card.bigWidth, 2);
		this.add(curCard);
		this.add(DiscardPile.discardPic);
		DiscardPile.discardPic.setLocation(4+Card.bigWidth*2, 2);
		sp = new SwitchPanel();
		this.add(sp);
		sp.setLocation((11)/3+2+(11/3)*CardHand.width, 4+(11)%3+Card.bigHeight+(11%3)*CardHand.height);
		
		instructions = new JTextArea();
		instructions.setSize(GameLogic.width-(6+Card.bigWidth*2+DiscardPile.width)-2, Card.bigHeight-2);
		instructions.setLocation(6+Card.bigWidth*2+DiscardPile.width, 2);
		instructions.setEditable(false);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setFont(new Font("Arial", Font.PLAIN, 12));
		instructions.setText("Welcome to Five Crowns Solitaire. Your goal is to clear every single one of the 11 hands before there are no cards remaining in the deck. To begin, select a hand to which you wish to add the large card.");
		this.add(instructions);
		/*Card c = new Card();
		c.setSize(false);
		this.add(c);
		c.setLocation(2, 2);*/
		
		/*PlayArea pa = new PlayArea(13);
		for (int i = 0; i<pa.getNumCards(); i++) {
			pa.addCard(new Card(i%11+3,i%5));
		}
		this.add(pa);
		pa.setLocation(20,20);*/
		
		/*Card c = new Card();
		this.add(c);
		c.setLocation(20, 20);*/
	}
	
	public static void clearHand(int num) {
		groupCurClearing = num;
		cp = new ClearPanel();
		MainPanel.g.remove(DiscardPile.discardPic);
		MainPanel.g.add(cp);
		cp.setLocation(4+Card.bigWidth*2, 2);
		p[num].initiateClear();
	}
	
	public static void sortAll() {
		for (CardHand ch : p) {
			ch.redrawCards();
			ch.sort();
			ch.drawCards();
		}
	}
	
	public static Card getCurCard() {
		if (drawCard) {
			Card out = curCard;
			MainPanel.g.remove(curCard);
			MainPanel.g.add(emptyCard);
			drawCard = false;
			return out;
		}
		return null;
	}
	
	public static void newCard() {
		if (d.curCard < 58*2) {
			curCard = d.getCard();
			curCard.setSize(false);
			curCard.setLocation(3+Card.bigWidth, 2);
			MainPanel.g.remove(emptyCard);
			MainPanel.g.add(curCard);
			MainPanel.g.remove(cp);
			MainPanel.g.add(DiscardPile.discardPic);
			drawCard = true;
		} else {
			MainFrame.mp.endGame(false);
		}
	}
	
	/*public void analyzeClick(int x, int y) {
		if (x<0 || x>=4*PlayArea.width || y<0 || y>=3*PlayArea.height)
			return;
		else {
			int remx = (int)Math.IEEEremainder(x, PlayArea.width);
			int remy = (int)Math.IEEEremainder(y, PlayArea.height);
			if (remx < 0)
				remx = PlayArea.width+remx;
			if (remy<0)
				remy = PlayArea.height+remy;
			p[x/PlayArea.width+(y/PlayArea.height)*4].analyzeClick(remx, remy);
		}
		switch (((DeckPlayArea)p[11]).getChange()) {
		case 0:
			break;
		case 1:
			for (int i = 0; i<11; i++) {
				p[i].setSortType(p[11].sortByValue);
			}
			break;
		case 2:
			for (int i = 0; i<11; i++) {
				p[i].setAscending(p[11].ascending);
			}
			break;
		}
	}*/
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*g.setColor(Color.RED);
		g.fillRect(0, 0, width-1, height-1);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);*/
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static boolean checkWinCondition() {
		boolean win = true;
		for (CardHand ch : p) {
			win = (win && ch.invalid);
		}
		if (win) {
			MainFrame.mp.endGame(true);
		}
		return win;
	}
}
