package hr.fer.zemris.ooup.lab4.geometry;

public class Rectangle {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	};
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rectangle union(Rectangle r) {
		int mostLeft = Math.min(this.getX() - this.getWidth()/2, 
				r.getX() - r.getWidth() / 2);
		int mostRight = Math.max(this.getX() + this.getWidth()/2, 
				r.getX() + r.getWidth() / 2);
		int top = Math.max(this.getY() + this.getHeight()/2, 
				r.getY() + r.getHeight() / 2);
		int bottom = Math.min(this.getY() - this.getHeight()/2, 
				r.getY() - r.getHeight() / 2);
		
		return new Rectangle((mostRight + mostLeft) / 2, 
				(top + bottom) / 2, 
				(mostRight - mostLeft), 
				(top - bottom));
	}
}