package hr.fer.ooup.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager {
	private Stack<EditAction> undoStack;
	private Stack<EditAction> redoStack;
	private List<UndoObserver> observers;
	
	private static UndoManager undoManager;

	private UndoManager() {
		this.undoStack = new Stack<EditAction>();
		this.redoStack = new Stack<EditAction>();
		this.observers = new ArrayList<>();
	}
	public static UndoManager getInstance() {
		if(undoManager == null) {
			undoManager = new UndoManager();
		}
		return undoManager;
	}
	
	public void undo() {
		if(!undoStack.isEmpty()) {
			EditAction action = undoStack.pop();
			redoStack.push(action);
			action.execute_undo();	
			notifyObservers();
		}
	}
	
	public void redo() {
		if(!redoStack.isEmpty()) {
			EditAction action = redoStack.pop();
			action.execute_do();
			undoStack.push(action);	
			notifyObservers();
		}
	}
	
	public void push(EditAction c) {
		redoStack.removeAllElements();
		undoStack.push(c);
		c.execute_do();
		notifyObservers();
	}
	
	public boolean isUndoStackEmpty() {
		return undoStack.isEmpty();
	}
	public boolean isRedoStackEmpty() {
		return redoStack.isEmpty();
	}
	
	public void notifyObservers() {
		for(UndoObserver observer: observers) {
			observer.update(this);
		}
	}
	
	public void attach(UndoObserver observer) {
		observers.add(observer);
	}
	
	public void dettach(UndoObserver observer) {
		observers.remove(observer);
	}
}
