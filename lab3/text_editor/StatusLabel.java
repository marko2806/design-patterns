package hr.fer.ooup.lab3;

import java.util.Iterator;

import javax.swing.JLabel;

public class StatusLabel extends JLabel implements CursorObserver, TextObserver{
	private TextEditorModel model;
	private int currentRow;
	private int currentCol;
	private int numRows = 0;
	
	public StatusLabel(TextEditorModel model) {
		this.model = model;
	}
	
	@Override
	public void updateCursorLocation(Location loc) {
		currentRow = loc.getX() + 1;
		currentCol = loc.getY() + 1;
		this.setText(currentRow + ":" + currentCol + ", (" + numRows + " rows)");
	}

	@Override
	public void updateText() {
		numRows = 0;
		Iterator<String> iterator = model.allLines();
		while(iterator.hasNext()) {
			numRows += 1;
			iterator.next();
		}
		this.setText(currentRow + ":" + currentCol + ", (" + numRows + " rows)");
	}
}
