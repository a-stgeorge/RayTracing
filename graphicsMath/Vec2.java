package graphicsMath;

public class Vec2 {

	private double x, y;
	
	
	// CONSTUCTORS
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2(double a) {
		this.x = a;
		this.y = a;
	}
	
	
	// GETTERS
	
	public double getX() {
		return x;
	}
		
	public double getY() {
		return y;
	}
		
	public double getElement(int i) {
		if (i == 0) {
			return x;
		} else if (i == 1) {
			return y;
		} else {
			throw new IndexOutOfBoundsException("Index out of bounds in for vec2 element in 'getElement': Index " + i);
		}
	}
	
}
