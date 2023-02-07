package hr.fer.zemris.ooup.lab4;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;
import hr.fer.zemris.ooup.lab4.shapes.LineSegment;
import hr.fer.zemris.ooup.lab4.shapes.Oval;

public class MainClass {
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				List<GraphicalObject> objects = new ArrayList<>();
				
				objects.add(new LineSegment());
				objects.add(new Oval());
				
				GUI gui = new GUI(objects);
				gui.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
