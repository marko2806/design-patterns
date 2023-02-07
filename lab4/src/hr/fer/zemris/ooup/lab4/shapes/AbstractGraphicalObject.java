package hr.fer.zemris.ooup.lab4.shapes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab4.geometry.GeometryUtil;
import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.listeners.GraphicalObjectListener;

public abstract class AbstractGraphicalObject implements GraphicalObject{
	private Point[] hotPoints;
	private boolean[] hotPointSelected;
	protected boolean selected = false;
	private List<GraphicalObjectListener> listeners = new ArrayList<>();
	
	protected AbstractGraphicalObject(Point ...points) {
		this.hotPoints = new Point[points.length];
		for(int i=0; i < points.length; i++) {
			this.hotPoints[i] = points[i];	
		}
		this.selected = false;
		this.hotPointSelected = new boolean[points.length];
	}
	
	public Point[] getAllHotPoints() {
		return this.hotPoints;
	}
	
	public Point getHotPoint(int i) {
		return this.hotPoints[i];
	}
	
	public void setHotPoint(int i, Point p) {
		this.hotPoints[i] = p;
		notifyListeners();
	}
	
	public int getNumberOfHotPoints() {
		return this.hotPoints.length;
	}
	
	public double getHotPointDistance(int i, Point p) {
		return GeometryUtil.distanceFromPoint(this.hotPoints[i], p);
	}
	
	public boolean isHotPointSelected(int i) {
		return this.hotPointSelected[i]; 
	}
	
	public void setHotPointSelected(int i, boolean selected) {
		this.hotPointSelected[i] = selected;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifySelectionListeners();
	}
	
	public void translate(Point p) {
		for(int i = 0; i < hotPoints.length; i++) {
			Point translated = hotPoints[i].translate(p);
			hotPoints[i] = translated;
		}
		notifyListeners();
	}
	public void addGraphicalObjectListener(GraphicalObjectListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeGraphicalObjectListener(GraphicalObjectListener listener) {
		this.listeners.remove(listener);
	}
	
	protected void notifyListeners() {
		for(GraphicalObjectListener listener: listeners) {
			listener.graphicalObjectChanged(this);
		}
	}
	protected void notifySelectionListeners() {
		for(GraphicalObjectListener listener: listeners) {
			listener.graphicalObjectSelectionChanged(this);
		}
	}
	
	protected boolean isInsideBoundingBox(Point p) {
		Rectangle rect = getBoundingBox();
		if(p.getX() >= (rect.getX() - rect.getWidth()/2) &&
				p.getX() <= (rect.getX() + rect.getWidth() / 2) &&
				p.getY() >= (rect.getY() - rect.getHeight() / 2) &&
				p.getY() <= (rect.getY() + rect.getHeight() / 2)) {
			return true;
		}
		return false;
	}
}
