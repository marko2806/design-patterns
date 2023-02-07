package hr.fer.zemris.ooup.lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.listeners.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.listeners.GraphicalObjectListener;
import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;

public class DocumentModel {
	private static DocumentModel instance;
	
	public final static double SELECTION_PROXIMITY = 10;

	private List<GraphicalObject> objects = new ArrayList<>();
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	private List<DocumentModelListener> listeners = new ArrayList<>();
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

		@Override
		public void graphicalObjectChanged(GraphicalObject go) {
			notifyListeners();
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {
			if(go.isSelected() && !selectedObjects.contains(go)) {
				selectedObjects.add(go);
			}else if(!go.isSelected() && selectedObjects.contains(go)) {
				selectedObjects.remove(go);
			}
			notifyListeners();
		}
		
	};
		
	public static DocumentModel getInstance() {
		if(instance == null) {
			instance = new DocumentModel();
		}
		return instance;
	}

	public void clear() {
		for(GraphicalObject object: objects) {
			object.removeGraphicalObjectListener(goListener);
			objects.remove(object);
		}
		notifyListeners();
	}

	public void addGraphicalObject(GraphicalObject obj) {
		objects.add(obj);
		obj.addGraphicalObjectListener(goListener);
		notifyListeners();
	}
	
	public void removeGraphicalObject(GraphicalObject obj) {
		if(selectedObjects.contains(obj)) {
			selectedObjects.remove(obj);
		}
		obj.removeGraphicalObjectListener(goListener);
		objects.remove(obj);
		notifyListeners();
	}

	public List<GraphicalObject> list() {
		return roObjects;
	}

	public void addDocumentModelListener(DocumentModelListener l) {
		listeners.add(l);
	}
	
	public void removeDocumentModelListener(DocumentModelListener l) {
		listeners.remove(l);
	}

	public void notifyListeners() {
		for(DocumentModelListener listener: listeners) {
			listener.documentChange();
		}
	}
	
	public List<GraphicalObject> getSelectedObjects() {
		return roSelectedObjects;
	}

	public void increaseZ(GraphicalObject go) {
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i) == go) {
				if(objects.size() > 1 && i <= (objects.size() - 2)) {
					GraphicalObject temp = objects.get(i + 1);
					objects.set(i + 1, go);
					objects.set(i, temp);
				}
			}
		}
		notifyListeners();
	}
	
	public void decreaseZ(GraphicalObject go) {
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i) == go) {
				if(objects.size() > 1 && i != 0) {
					GraphicalObject temp = objects.get(i - 1);
					objects.set(i - 1, go);
					objects.set(i, temp);
				}
			}
		}
		notifyListeners();
	}
	
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		GraphicalObject closest = null;
		for(GraphicalObject object: objects) {
			double distance = object.selectionDistance(mousePoint);
			if(distance <= SELECTION_PROXIMITY) {
				if(closest == null || 
						closest != null && distance < closest.selectionDistance(mousePoint)) {
					closest = object;
				}
			}
		}
		return closest;
	}

	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		int distance = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < object.getNumberOfHotPoints(); i++) {
			if(object.getHotPointDistance(i, mousePoint) <= SELECTION_PROXIMITY) {
				if(index == -1 ||
						index != -1 && object.getHotPointDistance(i, mousePoint) < distance) {
					distance = (int)object.getHotPointDistance(i, mousePoint);
					index = i;
				}
			}
		}
		return index;
	}
	
}
