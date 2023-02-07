package hr.fer.ooup.lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

public class CursorComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public CursorComponent(int x, int y) {
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.red);
		g.drawLine(x * 8 + 10, y * 16, x*8 + 10, y*16 + 16);
	    
	}
	
	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
		paintComponent(getGraphics());
	}
}
