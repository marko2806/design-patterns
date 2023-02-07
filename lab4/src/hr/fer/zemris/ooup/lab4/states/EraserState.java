package hr.fer.zemris.ooup.lab4.states;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ooup.lab4.DocumentModel;
import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;
import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;
import hr.fer.zemris.ooup.lab4.shapes.LineSegment;

public class EraserState implements State{

	private DocumentModel model;
	private List<Point> drawnPoints = new ArrayList<>();
	private List<GraphicalObject> drawnObjects = new ArrayList<>();
	private Set<GraphicalObject> selectedObjects= new HashSet<GraphicalObject>();
	
	public EraserState(DocumentModel model) {
		this.model = model;
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		drawnPoints.clear();
		drawnPoints.add(mousePoint);
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		Iterator<GraphicalObject> drawnIter = drawnObjects.iterator();
		
		while (drawnIter.hasNext()) {
		    GraphicalObject obj = drawnIter.next();
		    model.removeGraphicalObject(obj);
		}
		Iterator<GraphicalObject> objectsIter = selectedObjects.iterator();
		
		while (objectsIter.hasNext()) {
		    GraphicalObject obj = objectsIter.next();
		    model.removeGraphicalObject(obj);
		}
		
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		GraphicalObject obj = new LineSegment(drawnPoints.get(drawnPoints.size() - 1), mousePoint);
		model.addGraphicalObject(obj);
		GraphicalObject objectForDeletion = model.findSelectedGraphicalObject(mousePoint);
		if(objectForDeletion != null) {
			selectedObjects.add(objectForDeletion);
		}
		drawnObjects.add(obj);
		drawnPoints.add(mousePoint);
	}

	@Override
	public void keyPressed(int keyCode) {}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {}

	@Override
	public void afterDraw(Renderer r) {}

	@Override
	public void onLeaving() {}

}
