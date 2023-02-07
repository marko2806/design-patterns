package hr.fer.ooup.lab3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args) {
		TextEditorModel model = new TextEditorModel("");
		TextEditor frame = new TextEditor(model);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
