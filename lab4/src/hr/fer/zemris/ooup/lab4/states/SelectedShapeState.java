package hr.fer.zemris.ooup.lab4.states;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.ooup.lab4.DocumentModel;
import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;
import hr.fer.zemris.ooup.lab4.shapes.CompositeShape;
import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;

public class SelectedShapeState implements State{

	private DocumentModel model;
	
	public SelectedShapeState(DocumentModel model) {
		this.model = model;
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		List<GraphicalObject> objects = model.list();
		GraphicalObject object = model.findSelectedGraphicalObject(mousePoint);
		if(object != null) {
			if(ctrlDown){
				object.setSelected(!object.isSelected());
			}else {
				for(GraphicalObject go: objects) {
					go.setSelected(false);
				}
				object.setSelected(true);
			}
		}else if(object == null) {
			for(GraphicalObject obj: List.copyOf(model.getSelectedObjects())) {
				obj.setSelected(false);
			}
		}
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {}

	@Override
	public void mouseDragged(Point mousePoint) {
		GraphicalObject object = model.findSelectedGraphicalObject(mousePoint);
		if(object != null) {
			int index = model.findSelectedHotPoint(object, mousePoint);
			if(index != -1) {
				object.setHotPoint(index, mousePoint);
				object.setHotPointSelected(index, true);	
			}
			
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		for(GraphicalObject o: model.getSelectedObjects()) {
			if(keyCode == KeyEvent.VK_LEFT) {
				o.translate(new Point(-1,0));
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				o.translate(new Point(1,0));
			}else if(keyCode == KeyEvent.VK_UP) {
				o.translate(new Point(0,-1));
			}else if(keyCode == KeyEvent.VK_DOWN) {
				o.translate(new Point(0,1));
			}
		}
		
		for(GraphicalObject obj: model.list()) {
			if(obj.isSelected()) {
				if(keyCode == KeyEvent.VK_PLUS) {
					model.increaseZ(obj);
				}else if(keyCode == KeyEvent.VK_MINUS) {
					model.decreaseZ(obj);
				}
				
			}
		}
		if(keyCode == KeyEvent.VK_G) {
			List<GraphicalObject> objects = List.copyOf(model.getSelectedObjects());
			for(GraphicalObject obj: objects) {
				model.removeGraphicalObject(obj);
			}
			GraphicalObject shape = new CompositeShape(objects);
			model.addGraphicalObject(shape);
			shape.setSelected(true);
		}else if(keyCode == KeyEvent.VK_U) {
			List<GraphicalObject> objects = model.getSelectedObjects();
			if(objects.size() == 1 
					&& objects.get(0).getShapeName().equals("Composite")) {
				CompositeShape shape = (CompositeShape)objects.get(0);
				List<GraphicalObject> shapes =shape.getShapes();
				model.removeGraphicalObject(shape);
				for(GraphicalObject obj: shapes) {
					model.addGraphicalObject(obj);
					obj.setSelected(true);
				}
			}
		}
		
	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
		if(go.isSelected()) {
			Rectangle bb = go.getBoundingBox();
			Point[] points = new Point[4];
			points[0] = new Point(bb.getX() - bb.getWidth()/2, 
					bb.getY() - bb.getHeight()/2);
			points[1] = new Point(bb.getX() + bb.getWidth()/2, 
					bb.getY() - bb.getHeight()/2);
			points[2] = new Point(bb.getX() - bb.getWidth()/2, 
					bb.getY() + bb.getHeight()/2);
			points[3] = new Point(bb.getX() + bb.getWidth()/2, 
					bb.getY() + bb.getHeight()/2);
			r.drawLine(points[0], points[1]);
			r.drawLine(points[1], points[3]);
			r.drawLine(points[3], points[2]);
			r.drawLine(points[2], points[0]);
			for(int i = 0; i < go.getNumberOfHotPoints(); i++) {
				Point hotPoint = go.getHotPoint(i);
				Point[] hotPointRect = new Point[4];
				hotPointRect[0] = new Point(hotPoint.getX() - 3, hotPoint.getY() - 3);
				hotPointRect[1] = new Point(hotPoint.getX() + 3, hotPoint.getY() - 3);
				hotPointRect[2] = new Point(hotPoint.getX() - 3, hotPoint.getY() + 3);
				hotPointRect[3] = new Point(hotPoint.getX() + 3, hotPoint.getY() + 3);
				r.drawLine(hotPointRect[0], hotPointRect[1]);
				r.drawLine(hotPointRect[1], hotPointRect[3]);
				r.drawLine(hotPointRect[3], hotPointRect[2]);
				r.drawLine(hotPointRect[2], hotPointRect[0]);				
			}
		}
	}

	@Override
	public void afterDraw(Renderer r) {}

	@Override
	public void onLeaving() {
		Iterator<GraphicalObject> iter = List.copyOf(model.getSelectedObjects()).iterator();
		while(iter.hasNext()) {
			GraphicalObject obj = iter.next();
			obj.setSelected(false);
		}
	}
}
