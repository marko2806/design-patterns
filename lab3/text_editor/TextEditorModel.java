package hr.fer.ooup.lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TextEditorModel{
	private List<String> lines;
	private LocationRange selectionRange;
	private Location cursorLocation; 
	private List<CursorObserver> cursorObservers;
	private List<TextObserver> textObservers;
	private List<SelectionObserver> selectionObservers;
	
	public TextEditorModel(String text) {
		this.lines = new LinkedList<String>(Arrays.asList(text.split("\n")));
		this.textObservers = new ArrayList<>();
		this.cursorObservers = new ArrayList<>();
		this.cursorLocation = new Location(0,0);
		this.selectionObservers = new ArrayList<>();
	}
	
	public Iterator<String> allLines() {
		return lines.iterator();
	}
	
	public Iterator<String> linesRange(int index1, int index2){
		return new RangeIterator(index1, index2);
	}

	
	public void attachCursorObserver(CursorObserver observer) {
		cursorObservers.add(observer);
	}
	public void dettachCursorObserver(CursorObserver observer) {
		cursorObservers.remove(observer);
	}
	
	public void attachTextObserver(TextObserver observer) {
		textObservers.add(observer);
	}
	public void dettachTextObserver(TextObserver observer) {
		textObservers.remove(observer);
	}
	
	public void attachSelectionObserver(SelectionObserver copyItem) {
		selectionObservers.add(copyItem);
	}

	public void dettachSelectionObserver(SelectionObserver copyItem) {
		selectionObservers.remove(copyItem);
	}
	
	public void moveCursorUp() {
		int currentY = cursorLocation.getY();
		if (currentY > 0) {
			String nextLine = lines.get(currentY - 1);
			cursorLocation.setY(currentY - 1);
			if(cursorLocation.getX() > nextLine.length()) {
				cursorLocation.setX(nextLine.length());	
			}
			notifyCursorChange();	
		}
	}
	public void moveCursorDown() {
		int currentY = cursorLocation.getY();
		if (currentY < (lines.size() - 1)) {
			String nextLine = lines.get(currentY + 1);
			cursorLocation.setY(currentY + 1);
			if(cursorLocation.getX() > nextLine.length()) {
				cursorLocation.setX(nextLine.length());	
			}
			notifyCursorChange();
		}

	}
	public void moveCursorLeft() {
		int currentX = cursorLocation.getX();
		if(currentX > 0)
			cursorLocation.setX(currentX - 1);
			notifyCursorChange();
	}
	public void moveCursorRight() {
		int currentX = cursorLocation.getX();
		int currentY = cursorLocation.getY();
		if (currentX < lines.get(currentY).length())
			cursorLocation.setX(currentX + 1);
			notifyCursorChange();
	}
	
	public void notifyCursorChange() {
		for(CursorObserver observer: cursorObservers) {
			observer.updateCursorLocation(cursorLocation);
		}
	}
	public void notifyTextChange() {
		for(TextObserver observer: textObservers) {
			observer.updateText();
		}
	}
	public void notifySelectionChange() {
		for(SelectionObserver observer: selectionObservers) {
			observer.update(this);
		}
	}
	
	public char deleteBefore() {
		String line = lines.get(cursorLocation.getY());
		StringBuilder builder = new StringBuilder(line);
		char returnValue = '\0';
		if(cursorLocation.getX() > 0) {
			returnValue = line.charAt(cursorLocation.getX() - 1);
			builder.deleteCharAt(cursorLocation.getX() - 1);
			line = builder.toString();
			lines.set(cursorLocation.getY(), line);
			cursorLocation.setX(cursorLocation.getX() - 1);
		}else if(cursorLocation.getX() == 0 && cursorLocation.getY() > 0) {
			int index = cursorLocation.getY();
			if((index - 1) >= 0) {
				String previousLine = lines.get(index - 1);
				lines.set(index - 1, previousLine + line);
				lines.remove(index);
				returnValue = '\n';
				cursorLocation.setY(cursorLocation.getY() - 1);
				cursorLocation.setX(previousLine.length());
			}
		}
		notifyTextChange();
		notifyCursorChange();
		return returnValue;
	}
	
	public char deleteAfter() {
		int yValue = cursorLocation.getY();
		char returnValue = '\0';
		if(yValue < lines.size()) {
			int xValue = cursorLocation.getX();
			String line = lines.get(yValue);
			StringBuilder builder = new StringBuilder(line);
			if(line.equals("")) {
				lines.remove(yValue);
				returnValue = '\n';
			}else if(xValue < line.length()) {
				returnValue = lines.get(yValue).charAt(xValue);
				builder.deleteCharAt(xValue);	
				line = builder.toString();
				lines.set(yValue, line);
			}else if(xValue == line.length()) {
				if((yValue + 1) < lines.size()) {
					String nextLine = lines.get(yValue + 1);
					lines.set(yValue, line + nextLine);
					lines.remove(yValue + 1);
					returnValue = '\n';
				}
			}
			notifyTextChange();	
			notifyCursorChange();
		}
		return returnValue;
	}
	
	public String deleteRange(LocationRange r) {
		int startX = r.getStart().getX();
		int startY = r.getStart().getY();
		int endX = r.getEnd().getX();
		int endY = r.getEnd().getY();
		String returnValue = getTextFromSelection(r);		
		String firstPart = lines.get(startY).substring(0, startX);
		String secondPart = lines.get(endY).substring(endX);
		for(int i = startY; i <= endY; i++) {
			lines.remove(startY);
		}
		lines.add(startY, firstPart + secondPart);
		resetSelectionRange();
		notifyTextChange();	
		notifyCursorChange();
		return returnValue;
	}

	public LocationRange getSelectionRange() {
		if(selectionRange == null) {
			selectionRange = new LocationRange(null, null);
			selectionRange.setStart(new Location(cursorLocation.getX(), cursorLocation.getY()));
			selectionRange.setEnd(new Location(cursorLocation.getX(), cursorLocation.getY()));
		}
		return selectionRange;
	}

	public void setSelectionRange(LocationRange range) {
		if(range.getEnd().getY() >= lines.size()) {
			range.getEnd().setY(lines.size() - 1);
		}
		if(range.getEnd().getX() >= lines.get(range.getEnd().getY()).length()) {
			range.getEnd().setX(lines.get(range.getEnd().getY()).length());
		}
		selectionRange = range;
		notifySelectionChange();
	}
	
	public void resetSelectionRange() {
		selectionRange = null;
		notifySelectionChange();
	}
	
	public void insert(char c) {
		if(lines.size() == 0) {
			lines.add("");
			cursorLocation.setY(0);
			cursorLocation.setX(0);
		}
		if(c == '\n') {
			String currentLine = lines.get(cursorLocation.getY());
			String newLine = currentLine.substring(cursorLocation.getX());
			currentLine = currentLine.substring(0, cursorLocation.getX());
			lines.set(cursorLocation.getY(), currentLine);
			cursorLocation.setY(cursorLocation.getY() + 1);
			lines.add(cursorLocation.getY(), newLine);
			cursorLocation.setX(0);
		}else {
			String line = lines.get(cursorLocation.getY());
			StringBuilder builder = new StringBuilder(line);
			builder.insert(cursorLocation.getX(), c);
			line = builder.toString();
			lines.set(cursorLocation.getY(), line);
			cursorLocation.setX(cursorLocation.getX() + 1);	
		}
		notifyTextChange();
		notifyCursorChange();
	}
	
	public void insert(String text) {
		String[] rows = text.split("\n");
		if(lines.size() == 0) {
			lines.add("");
			cursorLocation.setY(0);
			cursorLocation.setX(0);
		}
		String firstPart = "";
		String secondPart = "";
		for(int i = 0; i < rows.length; i++) {
			if(i == 0) {
				String line = lines.get(cursorLocation.getY() + i);
				firstPart = line.substring(0, cursorLocation.getX());
				secondPart = line.substring(cursorLocation.getX());
				if(rows.length == 1) {
					lines.set(cursorLocation.getY(), firstPart + rows[i] + secondPart);				
				}else {
					lines.set(cursorLocation.getY(), firstPart + rows[i]);					
				}
			}else if(i == (rows.length - 1)) {
				if((cursorLocation.getY() + i) < lines.size()) {
					lines.add(cursorLocation.getY() + i, rows[i] + secondPart);	
				}else {
					lines.add(rows[i] + secondPart);
				}
			}else {
				lines.add(cursorLocation.getY() + i, rows[i]);
			}
		}
		if(rows.length == 1) {
			cursorLocation.setX(cursorLocation.getX() + rows[rows.length - 1].length());	
		}else {
			cursorLocation.setX(rows[rows.length - 1].length());
		}
		cursorLocation.setY(cursorLocation.getY() + rows.length - 1);
		notifyTextChange();
		notifyCursorChange();
	}
	
	public String getTextFromSelection(LocationRange range) {
		if(range != null) {
			StringBuilder sb = new StringBuilder();
			if(range.getStart() != null && range.getEnd() != null) {
				int startX = range.getStart().getX();
				int startY = range.getStart().getY();
				int endX = range.getEnd().getX();
				int endY = range.getEnd().getY();
				
				for(int i = startY; i <= endY; i++) {
					if(i == startY && i == endY) {
						sb.append(lines.get(i).substring(startX,endX));
					}else if(i == startY && i != endY) {
						sb.append(lines.get(i).substring(startX));
						sb.append("\n");
					}else if(i != startY && i == endY) {
						sb.append(lines.get(i).substring(0, endX));
					}else if(i != startY && i != endY) {
						sb.append(lines.get(i));
						sb.append("\n");
					}
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	public Location getCursorLocation() {
		return this.cursorLocation;
	}
	
	public void setCursorLocation(int x, int y) {
		cursorLocation.setX(x);
		cursorLocation.setY(y);
		notifyCursorChange();
	}
	
	public void setCursorToEnd() {
		setCursorLocation(lines.get(lines.size() - 1).length(), lines.size() - 1);
		notifyCursorChange();
	}
	
	private class RangeIterator implements Iterator<String>{
		private int end;
		private int current;
		
		public RangeIterator(int start, int end) {
			this.end = end;
			this.current = start;
		}
		
		@Override
		public boolean hasNext() {
			return current < end && current < lines.size() && current > 0;
		}

		@Override
		public String next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return lines.get(current++);
		}
		
	}
}
