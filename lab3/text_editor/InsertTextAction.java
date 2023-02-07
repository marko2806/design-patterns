package hr.fer.ooup.lab3;

public class InsertTextAction implements EditAction{
	private TextEditorModel model;
	private String text;
	private Location textLocation;
	
	public InsertTextAction(TextEditorModel model, String text) {
		this.model = model;
		this.text = text;
	}
	
	@Override
	public void execute_do() {
		if(textLocation == null) {
			textLocation = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());	
		}
		model.setCursorLocation(textLocation.getX(), textLocation.getY());
		model.insert(text);
	}

	@Override
	public void execute_undo() {
		String rows[] = text.split("\n");
		if(rows.length > 1) {
			Location endLocation = new Location(rows[rows.length - 1].length(), textLocation.getY() + rows.length - 1);
			model.deleteRange(new LocationRange(textLocation, endLocation));
		}else {
			LocationRange range = new LocationRange(textLocation, new Location(textLocation.getX() + rows[0].length(), textLocation.getY()));
			model.deleteRange(range);	
		}
		model.setCursorLocation(textLocation.getX(), textLocation.getY());
	}
	
}
