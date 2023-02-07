package hr.fer.ooup.lab3;

public class DeleteBeforeAction implements EditAction{
	private TextEditorModel model;
	
	private char c;
	private Location charLocation;
	private Location afterCharLocation;
	
	public DeleteBeforeAction(TextEditorModel model) {
		this.model = model;
	}
	
	@Override
	public void execute_do() {
		if(afterCharLocation == null) {
			afterCharLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
		}
		model.setCursorLocation(afterCharLocation.getX(), afterCharLocation.getY());
		c = model.deleteBefore();
		if(charLocation == null) {
			charLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());	
		}
		
	}

	@Override
	public void execute_undo() {
		if(c != '\0') {
			model.setCursorLocation(charLocation.getX(), charLocation.getY());
			model.insert(c);	
		}
	}
	
}
