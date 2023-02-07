package hr.fer.ooup.lab3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class SelectionPanel extends JPanel{
	public LocationRange selectionRange;
	public List<RowComponent> rowComponents;
	
	SelectionPanel(LocationRange range, List<RowComponent> rowComponents){
		this.selectionRange = range;
		this.rowComponents = rowComponents;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintRange(selectionRange, g);
		
	}
	
	public void paintRange(LocationRange range, Graphics g) {
		int startX = range.getStart().getX();
		int startY = range.getStart().getY();
		int endX = range.getEnd().getX();
		int endY = range.getEnd().getY();
		g.create();
		g.setColor(new Color(0,0,255, 50));
		
		for(int i = startY; i < rowComponents.size() && i <= endY; i++) {
			if(i == startY && i != endY) {
				g.fillRect(startX * 8 + 10, i * 16, (rowComponents.get(i).getContent().length() - startX) * 8, 16);	
			}else if(i != startY && i != endY) {
				g.fillRect(10, i * 16, (rowComponents.get(i).getContent().length()) * 8, 16);				
			}else if(i != startY && i == endY) {
				g.fillRect(10, i * 16, endX*8, 16);
			}else if(i == startY && i == endY) {
				if(endX < rowComponents.get(i).getContent().length())
					g.fillRect(startX*8 + 10, i*16, (endX - startX)*8, 16);
				else
					g.fillRect(startX*8 + 10, i*16, (rowComponents.get(i).getContent().length() - startX)*8, 16);
			}
		}
		
	}

	public void setSelectionRange(LocationRange range) {
		this.selectionRange = range;
	}
}
