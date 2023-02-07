package hr.fer.zemris.ooup.lab4.listeners;

import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;

public interface GraphicalObjectListener {
	void graphicalObjectChanged(GraphicalObject go);
	void graphicalObjectSelectionChanged(GraphicalObject go);
}
