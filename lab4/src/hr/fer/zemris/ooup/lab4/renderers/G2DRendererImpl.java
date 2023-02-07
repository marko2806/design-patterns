package hr.fer.zemris.ooup.lab4.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import hr.fer.zemris.ooup.lab4.geometry.Point;

public class G2DRendererImpl implements Renderer{

	private Graphics2D g2d;
	
	public G2DRendererImpl(Graphics2D graphics) {
		this.g2d = graphics;
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		g2d.setColor(Color.BLUE);
		g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
	}

	@Override
	public void fillPoygon(Point[] points) {
		g2d.setColor(Color.blue);
		int[] xpoints = new int[points.length];
		int[] ypoints = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			xpoints[i] = points[i].getX();
			ypoints[i] = points[i].getY();
		}
		g2d.fillPolygon(new Polygon(xpoints,ypoints, points.length));
		g2d.setColor(Color.RED);
		g2d.drawPolygon(new Polygon(xpoints,ypoints, points.length));
	}

}
