package hr.fer.ooup.lab3;

public class DeleteAfterAction implements EditAction{
	private TextEditorModel model;
	
	private char c;
	private Location charLocation;
	private Location afterCharLocation;
	
	public DeleteAfterAction(TextEditorModel model) {
		this.model = model;
	}
	
	@Override
	public void execute_do() {
		if(charLocation == null) {
			charLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());	
		}
		model.setCursorLocation(charLocation.getX(), charLocation.getY());
		c = model.deleteAfter();
		if(afterCharLocation == null) {
			afterCharLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
		}
		
	}

	@Override
	public void execute_undo() {
		if(c != '\0') {
			model.setCursorLocation(charLocation.getX(), charLocation.getY());
			model.insert(c);
			model.moveCursorLeft();	
		}
	}
	
}