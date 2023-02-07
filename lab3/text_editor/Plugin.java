package hr.fer.ooup.lab3;

public interface Plugin {
	  String getName();
	  String getDescription();
	  void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);
}