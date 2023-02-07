package hr.fer.zemris.ooup.lab4.geometry;

public class GeometryUtil {

	public static double distanceFromPoint(Point point1, Point point2) {
		double dx = Math.pow(point1.getX() - point2.getX(), 2);
		double dy = Math.pow(point1.getY() - point2.getY(), 2);
		return Math.sqrt(dx + dy);
	}
	
	public static double distanceFromLineSegment(Point s, Point e, Point p){
		if(s.getX() <= p.getX() && p.getX() <= e.getX()) {
			int a = s.getY() - e.getY();
			int b = e.getX() - s.getX();
			int c = s.getX() * e.getY() - e.getX() * s.getY();
			
			double nom = Math.abs(a * p.getX() + b * p.getY() + c);
			double den = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
			return nom / den;	
		}else if(p.getX() < s.getX()){
			return distanceFromPoint(p, s);
		}else if(p.getX() > e.getX()) {
			return distanceFromPoint(p, e);
		}
		return Double.MAX_VALUE;
	}
	
	public static boolean isInsideRectange(Point p, Rectangle r) {
		if(p.getX() <= (r.getX() + r.getWidth() / 2) &&
				p.getX() >= (r.getX() - r.getWidth() / 2) &&
				p.getY() <= (r.getY() + r.getHeight() / 2) &&
				p.getX() >= (r.getY() - r.getHeight() / 2)) {
			return true;
		}else {
			return false;
		}
	}
}
