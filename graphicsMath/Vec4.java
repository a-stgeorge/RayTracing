package graphicsMath;

public class Vec4 {
	
	private double x, y, z, w;
	
	
	// CONSTRUCTORS
	
	public Vec4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(double a) {
		this.z = a;
		this.y = a;
		this.z = a;
		this.w = a;
	}
	
	public Vec4(Vec3 v, double w) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		this.w = w;
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
	
	public double getW() {
		return w;
	}
	
	public double getElement(int i) {
		if (i == 0) {
			return x;
		} else if (i == 1) {
			return y;
		} else if (i == 2) {
			return z;
		} else if (i == 3) {
			return w;
		} else {
			throw new IndexOutOfBoundsException("Index out of bounds for vec4 element in 'getElement': Index " + i);
		}
	}
	
	
	// VECTOR MATH
	
	public Vec4 normalize() {
		double magnitude = getMagnitude();
		
		if (magnitude == 0) {
			throw new ArithmeticException("Cannot compute magnitude for vector of 0 length!");
		}
		
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		w /= magnitude;
		
		return this;
	}
	
	public double getMagnitude() {
		return Math.sqrt(this.dot(this));
	}
	
	public double getMagnitude3() { // don't factor w-coordinate into the magnitude
		return Math.sqrt(toVec3().dot(toVec3()));
	}

	public Vec4 subtract(Vec4 v) {
		return new Vec4(x - v.getX(), y - v.getY(), z - v.getZ(), w - v.getW());
	}
	
	public Vec4 subtract(double a) {
		return new Vec4(x - a, y - a, z - a, w - a);
	}
	
	public Vec4 add(Vec4 v) {
		return new Vec4(x + v.getX(), y + v.getY(), z + v.getZ(), w + v.getW());
	}
	
	public Vec4 add(double a) {
		return new Vec4(x + a, y + a, z + a, w + a);
	}
	
	public Vec4 mult(double a) {
		return new Vec4(a * x, a * y, a * z, a * w);
	}
	
	public Vec4 mult(Vec4 v) {
		return new Vec4(x * v.getX(), y * v.getY(), z * v.getZ(), w * v.getW());
	}
	
	public Vec4 cross(Vec4 v) {
		return new Vec4(y * v.getZ() - z * v.getY(),
				        z * v.getX() - x * v.getZ(),
				        x * v.getY() - y * v.getX(),
						0.0);
	}
	
	public double dot(Vec4 v) {
		return x * v.getX() + y * v.getY() + z * v.getZ() + w * v.getW();
	}
	
	public Vec4 reflect(Vec4 axis) {
		Vec4 diff = subtract(axis);
		return add(diff).add(diff);
	}
	
	// HELPER METHODS
	
	public Vec3 toVec3() {
		return new Vec3(x, y, z);
	}
	
	public Vec4 maximize(double compare) {
		return new Vec4(Math.max(compare, x), Math.max(compare, y),
						Math.max(compare, z), Math.max(compare, z));
	}
	
	public Vec4 vector() {
		return new Vec4(x, y, z, 0);
	}
	
	
	// DEBUGGING
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
	
}
