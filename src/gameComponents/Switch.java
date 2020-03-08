package gameComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import frame.JPanelNLM;

public abstract class Switch extends JPanelNLM implements MouseListener{
	protected boolean on = true;
	private int length;
	public static int width = Card.smallWidth*2;
	public static int height = Card.smallHeight/2;
	private Timer t;
	
	public Switch() {
		length = width/2;
		int delay = 1; // milliseconds
		ActionListener taskPerformer1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (!on && length>0)
					length--;
				else if (on && length<width/2)
					length++;
				else
					t.stop();
			}
		};
		t = new Timer(delay, taskPerformer1);
		t.start();
		t.stop();
		length = width/2;
		this.setSize(width,height+2);
		this.addMouseListener(this);
		this.setBackground(new Color(0,0,0,0));
	}
	
	public void switchState() {
		//length = (on?width/2:0);
		t.restart();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(width/4, height/4+1, width/2, height/2, height/2, height/2);
		g.setColor(Color.GREEN);
		g.fillRoundRect(width/4, height/4+1, length, height/2, height/2, height/2);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(width/4, height/4+1, width/2, height/2, height/2, height/2);
		g.setColor(Color.WHITE);
		g.fillOval(length, (height-width/2-1)/2+1,  width/2-1, width/2-1);
		g.setColor(Color.DARK_GRAY);
		g.drawOval(length, (height-width/2-1)/2+1,  width/2-1, width/2-1);
	}

	@Override
	public abstract void mouseClicked(MouseEvent arg0);

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
}
