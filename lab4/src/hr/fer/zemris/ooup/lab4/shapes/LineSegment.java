package hr.fer.zemris.ooup.lab4.shapes;

import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.geometry.GeometryUtil;
import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;

public class LineSegment extends AbstractGraphicalObject{
	
	public LineSegment(Point p1, Point p2) {
		super(p1,p2);
	}
	
	public LineSegment() {
		super(new Point(0,0), new Point(20,20));
	}


	@Override
	public Rectangle getBoundingBox() {
		int x = (getHotPoint(0).getX() + getHotPoint(1).getX()) / 2;
		int y = (getHotPoint(0).getY() + getHotPoint(1).getY()) / 2;
		int width = (getHotPoint(1).getX() - getHotPoint(0).getX());
		int height = (getHotPoint(1).getY() - getHotPoint(0).getY());
		return new Rectangle(x,y,width,height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
	}

	
	@Override
	public String getShapeName() {
		return "Linija";
	}

	@Override
	public GraphicalObject duplicate() {
		return new LineSegment(this.getHotPoint(0), this.getHotPoint(1));
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(this.getHotPoint(0), this.getHotPoint(1));
	}

	@Override
	public String getShapeID() {
		return null;
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {}
	
	@Override
	public void save(List<String> rows) {}
}
