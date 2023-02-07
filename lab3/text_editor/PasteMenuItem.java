package hr.fer.ooup.lab3;

import javax.swing.Action;
import javax.swing.JMenuItem;

public class PasteMenuItem extends JMenuItem implements ClipboardObserver{

	public PasteMenuItem(Action action) {
		super(action);
	}
	
	@Override
	public void update(boolean isEmpty) {
		setEnabled(!isEmpty);
	}

}
