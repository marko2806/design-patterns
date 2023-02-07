package hr.fer.ooup.lab3;

import javax.swing.Action;
import javax.swing.JMenuItem;

public class RedoMenuItem extends JMenuItem implements UndoObserver{

	RedoMenuItem(Action action){
		super(action);
	}
	
	@Override
	public void update(UndoManager undo) {
		setEnabled(!undo.isRedoStackEmpty());				
	}

}