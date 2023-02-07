package hr.fer.ooup.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {
	private Stack<String> texts;
	private List<ClipboardObserver> observers;
	
	public ClipboardStack() {
		texts = new Stack<String>();
		observers = new ArrayList<>();
	}
	
	public void putTextToStack(String text) {
		texts.push(text);
		notifyObservers();
	}
	public String popTextFromStack() {
		String text = texts.pop();
		notifyObservers();
		return text;
		
	}
	public String readTextFromTopOfTheStack() {
		return texts.lastElement();
	}
	public boolean hasTextInStack() {
		return !texts.isEmpty();
	}
	public void deleteStack() {
		texts.removeAllElements();
		notifyObservers();
	}
	
	public void notifyObservers() {
		for(ClipboardObserver observer:observers) {
			observer.update(!hasTextInStack());
		}
	}
	
	public void attachObserver(ClipboardObserver observer) {
		observers.add(observer);
	}
	public void dettachObserver(ClipboardObserver observer) {
		observers.remove(observer);
	}
}
