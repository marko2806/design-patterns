package hr.fer.zemris.ooup.lab4.shapes;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;

public class CompositeShape extends AbstractGraphicalObject{
	private List<GraphicalObject> shapes;
	
	public CompositeShape(List<GraphicalObject> shapes) {
		this.shapes = shapes;
	}

	@Override
	public Rectangle getBoundingBox() {
		Rectangle bb = null; 
		for(GraphicalObject obj: shapes) {
			if(bb == null) {
				bb = obj.getBoundingBox();
			}else {
				bb = bb.union(obj.getBoundingBox());
			}
		}
		return bb;
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Double res = null;
		for(GraphicalObject obj: shapes) {
			if(res == null) {
				res = obj.selectionDistance(mousePoint);
			}else {
				if(res > obj.selectionDistance(mousePoint)) {
					res = obj.selectionDistance(mousePoint);
				}
			}
		}
		return res;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifySelectionListeners();
	}
	
	@Override
	public void render(Renderer r) {
		shapes.stream().forEach(g -> g.render(r));
	}

	@Override
	public String getShapeName() {
		return "Composite";
	}

	@Override
	public GraphicalObject duplicate() {
		return new CompositeShape(List.copyOf(shapes));
	}

	@Override
	public String getShapeID() {
		return null;
	}
	@Override
	public void translate(Point p) {
		for(GraphicalObject go: shapes) {
			go.translate(p);
		}
		notifyListeners();
	}
	
	@Override
	public void load(Stack<GraphicalObject> stack, String data) {}

	@Override
	public void save(List<String> rows) {}

	public List<GraphicalObject> getShapes() {
		return Collections.unmodifiableList(this.shapes);		
	}
}
