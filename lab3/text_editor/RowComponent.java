package hr.fer.ooup.lab3;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

public class RowComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	private int rownumber;
	private int padding = 0;
	private String content;
	private static final Font font = new Font("Monospaced", Font.PLAIN, 14);
	
	public RowComponent(int rownumber) {
		this.rownumber = rownumber;
	}
	public RowComponent(int rownumber, String content) {
		this.rownumber = rownumber;
		this.content = content;
		setMaximumSize(new Dimension(Integer.MAX_VALUE,16));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    g.setFont(font);
	    g.drawString(content, 10, 10);
	}

	public int getRownumber() {
		return rownumber;
	}

	public void setRownumber(int rownumber) {
		this.rownumber = rownumber;
	}
	
	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
}
