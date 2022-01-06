package graphicsMath;

public class Vec3 {
	
	private double x, y, z;
	
	
	// CONSTRUCTORS
	
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(double a) {
		this.z = a;
		this.y = a;
		this.z = a;
	}
	
	
	// GETTERS
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getElement(int i) {
		if (i == 0) {
			return x;
		} else if (i == 1) {
			return y;
		} else if (i == 2) {
			return z;
		} else {
			throw new IndexOutOfBoundsException("Index out of bounds in for vec3 element in 'getElement': Index " + i);
		}
	}
	
	
	// VECTOR GRAPHICS MATH
	
	public double getMagnitude() {
		return Math.sqrt(this.dot(this));
	}
	
	public Vec3 normalize() {
		double magnitude = getMagnitude();
		
		if (magnitude == 0) {
			throw new ArithmeticException("Cannot compute magnitude for vector of 0 length!");
		}
		
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		
		return this;
	}

	public Vec3 subtract(Vec3 vec) {
		return new Vec3(x - vec.getX(), y - vec.getY(), z - vec.getZ());
	}
	
	public Vec3 add(Vec3 vec) {
		return new Vec3(x + vec.getX(), y + vec.getY(), z + vec.getZ());
	}
	
	public Vec3 mult(double a) {
		return new Vec3(a * getX(), a * getY(), a * getZ());
	}
	
	public Vec3 mult(Vec3 vec) {
		return new Vec3(x * vec.getX(), y * vec.getY(), z * vec.getZ());
	}
	
	public double getAngle(Vec3 vec) {
		return Math.acos(dot(vec) / (getMagnitude() * vec.getMagnitude()));
	}
	
	public Vec3 cross(Vec3 b) {
		return new Vec3(y * b.getZ() - z * b.getY(),
				        z * b.getX() - x * b.getZ(),
				        x * b.getY() - y * b.getX());
	}
	
	public double dot(Vec3 b) {
		return x * b.getX() + y * b.getY() + z * b.getZ();
	}
	
	public Vec3 reflect(Vec3 axis) {
		Vec3 diff = axis.mult(dot(axis));
		return subtract(diff.mult(2));
	}
	
	
	// STATIC VECTOR MATH METHODS
	
	public static Vec3 normalize(Vec3 a) {
		double magnitude = a.getMagnitude();
		return new Vec3(a.getX() / magnitude, a.getY() / magnitude, a.getZ() / magnitude );
	}
	
	
	// Improve before implementing or remove
	/*public boolean isSameDirectionAs(Vec3 vec) { // Only works for parallel vectors 
		boolean skip = false;
		int i = 0;
		while (!skip && i < 3) {
			double diff = vec.getElement(i);
			if (diff != 0) {				
				if (diff > 0 && getElement(i) > 0 || diff < 0 && getElement(i) < 0) {
					return true;
				}
				skip = true;
			} else {
				i++;
			}
		}
		return false;
	}*/
	
	
	// DEBUGGING
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
