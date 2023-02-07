package hr.fer.ooup.lab3;

public class DeleteRangeAction implements EditAction{
	private TextEditorModel model;
	private String text;
	private Location textLocation;
	private Location afterTextLocation;
	
	public DeleteRangeAction(TextEditorModel model) {
		this.model = model;
	}
	
	@Override
	public void execute_do() {
		if(textLocation == null) {
			textLocation = new Location(model.getSelectionRange().getStart().getX(), model.getSelectionRange().getStart().getY());	
		}
		if(afterTextLocation == null) {
			afterTextLocation = new Location(model.getSelectionRange().getEnd().getX(), model.getSelectionRange().getEnd().getY());
		}
		model.setCursorLocation(textLocation.getX(), textLocation.getY());
		text = model.deleteRange(new LocationRange(textLocation, afterTextLocation));
	}

	@Override
	public void execute_undo() {
		model.setCursorLocation(textLocation.getX(), textLocation.getY());
		model.insert(text);
	}
	
}