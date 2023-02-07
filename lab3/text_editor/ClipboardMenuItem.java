package hr.fer.ooup.lab3;

import javax.swing.JMenuItem;

public class ClipboardMenuItem extends JMenuItem implements ClipboardObserver{

	@Override
	public void update(boolean isEmpty) {
		this.setEnabled(!isEmpty);
	}

}