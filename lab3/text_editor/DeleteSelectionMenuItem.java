package hr.fer.ooup.lab3;

import javax.swing.Action;
import javax.swing.JMenuItem;

public class DeleteSelectionMenuItem extends JMenuItem implements SelectionObserver{

	DeleteSelectionMenuItem(Action action){
		super(action);
	}
	
	@Override
	public void update(TextEditorModel model) {
		LocationRange range = model.getSelectionRange();
		int startX = range.getStart().getX();
		int startY = range.getStart().getY();
		int endX = range.getEnd().getX();
		int endY = range.getEnd().getY();
		
		setEnabled(startX < endX || startY < endY);
				
	}

}
