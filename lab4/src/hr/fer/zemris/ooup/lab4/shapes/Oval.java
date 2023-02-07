package hr.fer.zemris.ooup.lab4.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.geometry.GeometryUtil;
import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;

public class Oval extends AbstractGraphicalObject{
	private Point center;
	
	public Oval(Point p1, Point p2) {
		super(p1,p2);
		this.center = new Point(p1.getX(), p2.getY());
	}
	
	public Oval() {
		super(new Point(10,0), new Point(0,20));
		Point p1 = new Point(10,0);
		Point p2 = new Point(0,20);
		this.center = new Point(p1.getX(), p2.getY());
	}


	@Override
	public Rectangle getBoundingBox() {
		int width = Math.abs(this.getHotPoint(0).getX() - this.getHotPoint(1).getX()) * 2;
		int height = Math.abs(this.getHotPoint(0).getY() - this.getHotPoint(1).getY()) * 2;
		return new Rectangle(center.getX(), center.getY(), width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
	    int a = getHotPoint(0).getX() - center.getX();
	    int b = getHotPoint(1).getY() - center.getY();
	    return GeometryUtil.distanceFromPoint(mousePoint, new Point(center.getX(), center.getY())) - Math.max(a, b);
	}

	@Override
	public void render(Renderer r) {
		Point[] hotPoints = this.getAllHotPoints();
		this.center = new Point(hotPoints[1].getX(), hotPoints[0].getY());
		double a = GeometryUtil.distanceFromPoint(center, hotPoints[0]);
		double b = GeometryUtil.distanceFromPoint(center, hotPoints[1]);
		int x0 = (int)hotPoints[0].getX();
		int y0 = (int)hotPoints[0].getY();
		List<Point> points = new ArrayList<>();
		points.add(new Point(x0,y0));
		for(int i = 0; i <= 180; i++) {
			double kut = 2 * Math.PI * i / 180;
			int x1 = (int)(center.getX() + a * Math.cos(kut));
			int y1 = (int)(center.getY() + b * Math.sin(kut));
			if(i != 0) {
				r.drawLine(new Point(x0,y0), new Point(x1,y1));	
			}
			x0 = x1;
			y0 = y1;
			points.add(new Point(x0,y0));
		}
		Point[] pointsArr = new Point[points.size()];
		points.toArray(pointsArr);
		r.fillPoygon(pointsArr);
	}

	@Override
	public String getShapeName() {
		return "Oval";
	}

	@Override
	public GraphicalObject duplicate() {
		return new Oval(this.getHotPoint(0), this.getHotPoint(1));
	}

	@Override
	public void save(List<String> rows) {}

	@Override
	public String getShapeID() {
		return null;
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {}
	
}
