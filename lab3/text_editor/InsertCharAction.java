package hr.fer.ooup.lab3;

public class InsertCharAction implements EditAction{
	private TextEditorModel model;
	
	private char c;
	private Location charLocation;
	private Location afterCharLocation;
	
	public InsertCharAction(TextEditorModel model, char c) {
		this.c = c;
		this.model = model;
	}
	
	@Override
	public void execute_do() {
		if(charLocation == null) {
			charLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());	
		}
		model.setCursorLocation(charLocation.getX(), charLocation.getY());
		model.insert(c);
		if(afterCharLocation == null) {
			afterCharLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());	
		}
		
	}

	@Override
	public void execute_undo() {
		LocationRange range = new LocationRange(charLocation, afterCharLocation);
		model.deleteRange(range);
		model.setCursorLocation(charLocation.getX(), charLocation.getY());
	}
	
}
