package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

import gameComponents.Card;
import gameComponents.ClearPanel;
import gameComponents.DiscardPile;
import gameComponents.GameLogic;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	public static MainPanel mp;
	static int trueWidth = GameLogic.width;
	static int trueHeight = GameLogic.height;
	static int width;
	static int height;
	
	private MainFrame() {
		super("Five Crowns Solitaire");
		DiscardPile.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(false);
		BufferedImage icon = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics g = icon.getGraphics();
		for (int i = 0; i<5; i++) {
			g.setColor(Card.suitNumToName(i));
			g.fillArc(0, 0, 499, 499, i*(360/6), 60);
		}
		g.setColor(Card.suitNumToName(10));
		g.fillArc(0, 0, 499, 499, 5*(360/6), 60);
		this.setIconImage(icon);
		this.add(mp = new MainPanel());
		this.pack();
		this.setLocation(100, 100);
		this.setSize(trueWidth, trueHeight);
		width = trueWidth + this.getPreferredSize().width - this.getContentPane().getPreferredSize().width;
		height = trueHeight + this.getPreferredSize().height - this.getContentPane().getPreferredSize().height;
		this.setSize(width, height);
		this.setVisible(true);
		
		//20-Frames per second
		int delay = 50; // milliseconds
		ActionListener taskPerformer1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};
		new Timer(delay, taskPerformer1).start();
	}
	
	public static void main(String[] args) {
		new ClearPanel();
		new MainFrame();
	}
}
