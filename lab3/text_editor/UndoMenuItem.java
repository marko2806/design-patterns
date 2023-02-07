package hr.fer.ooup.lab3;


import javax.swing.Action;
import javax.swing.JMenuItem;

public class UndoMenuItem extends JMenuItem implements UndoObserver{

	UndoMenuItem(Action action){
		super(action);
	}
	
	@Override
	public void update(UndoManager undo) {
		setEnabled(!undo.isUndoStackEmpty());				
	}

}
