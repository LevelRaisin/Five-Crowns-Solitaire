package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public abstract class Button extends JPanelNLM implements MouseListener, MouseMotionListener{
	public int x, y, w, h, textSize;
	Color reg=Color.BLACK, hov=Color.GRAY, pre=Color.RED, cur, bck=Color.WHITE;
	String text;
	public boolean state;
	public Button(int x, int y, int w, int h, String text, int textSize, boolean state) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		cur = reg;
		this.text = text;
		this.textSize = textSize;
		this.state = state;
		this.setSize(w, h);
		this.addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(cur);
		g.fillRect(0, 0, w, h);
		if (state)
			g.setColor(bck);
		else
			g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10, w-20, h-20);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, textSize));
		g.setColor(Color.BLUE);
		g.drawString(text, (w - g.getFontMetrics().stringWidth(text)) / 2, h-(h-g.getFontMetrics().getHeight())/2-textSize/2+5);
	}

	@Override
	public abstract void mouseClicked(MouseEvent arg0);

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (state)
			cur = hov;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (state)
			cur = reg;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (state)
			cur = pre;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (state && cur.equals(pre))
			cur = hov;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setState(boolean on) {
		state = on;
	}
	
	/*public boolean analyze(int mx, int my) {
		if(state)
			cur = reg;
		if (x<=mx && mx<=x+w && y<=my && my<=y+h) {
			if(state)
				cur = hov;
			return true;
		}
		return false;
	}
	
	public void pressed() {
		if(state)
			cur = pre;
	}
	
	public void released() {
		if (state)
			cur = hov;
	}
	
	public void unpressed() {
		if (state)
			cur = reg;
	}*/

}
