package hr.fer.ooup.lab3.plugins;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import hr.fer.ooup.lab3.ClipboardStack;
import hr.fer.ooup.lab3.Location;
import hr.fer.ooup.lab3.LocationRange;
import hr.fer.ooup.lab3.TextEditorModel;
import hr.fer.ooup.lab3.UndoManager;
import hr.fer.ooup.lab3.Plugin;

public class CapitalizePlugin implements Plugin{

	@Override
	public String getName() {
		return "Capitalize";
	}

	@Override
	public String getDescription() {
		return "Capitalizes all words in text";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		Iterator<String> linesIterator = model.allLines();
		List<String> rows = new LinkedList<String>();
		String text = "";
		int rowCounter = 0;
		while(linesIterator.hasNext()) {
			String row = linesIterator.next();
			text += row;
			text += "\n";
			rows.add(row);
			rowCounter++;
		}
		text = text.substring(0, text.length() - 1);
	    String regex = "\\b(.)(.*?)\\b";
	    String result = Pattern.compile(regex).matcher(text).replaceAll(
	            matche -> matche.group(1).toUpperCase() + matche.group(2)
	    );
	    int endY = rows.size() - 1;
	    int endX = rows.get(rows.size() - 1).length();
	    model.deleteRange(new LocationRange(
	    		new Location(0,0), 
	    		new Location(endX, endY)));
	    model.setCursorLocation(0, 0);
	    model.insert(result);
	}

}
