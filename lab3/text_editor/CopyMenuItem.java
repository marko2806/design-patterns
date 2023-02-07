package hr.fer.ooup.lab3;

import javax.swing.Action;
import javax.swing.JMenuItem;

public class CopyMenuItem extends JMenuItem implements SelectionObserver{

	public CopyMenuItem(Action action) {
		super(action);
	}
	
	@Override
	public void update(TextEditorModel model) {
		LocationRange range = model.getSelectionRange();
		if(range != null) {
			int startX = range.getStart().getX();
			int startY = range.getStart().getY();
			int endX = range.getEnd().getX();
			int endY = range.getEnd().getY();
			
			setEnabled(startX < endX || startY < endY );			
		}else {
			setEnabled(false);
		}
	}

}
