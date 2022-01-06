package graphicsMath;

public class Mat4 {
	
	private double mat[][] = new double[4][4];

	
	// CONSTRUCTORS
	
	public Mat4(double[] m) {
		if (m.length < 16) {
			throw new IllegalArgumentException("Mat4 must be initialized with list of size 16!");
		}
		
		mat[0][0] = m[0];
		mat[0][1] = m[1];
		mat[0][2] = m[2];
		mat[0][3] = m[3];
		
		mat[1][0] = m[4];
		mat[1][1] = m[5];
		mat[1][2] = m[6];
		mat[1][3] = m[7];
		
		mat[2][0] = m[8];
		mat[2][1] = m[9];
		mat[2][2] = m[10];
		mat[2][3] = m[11];
		
		mat[3][0] = m[12];
		mat[3][1] = m[13];
		mat[3][2] = m[14];
		mat[3][3] = m[15];
	}
	
	public Mat4(double x1, double x2, double x3, double x4,
				double y1, double y2, double y3, double y4,
				double z1, double z2, double z3, double z4,
				double w1, double w2, double w3, double w4) {
			
		mat[0][0] = x1;
		mat[0][1] = x2;
		mat[0][2] = x3;
		mat[0][3] = x4;
		
		mat[1][0] = y1;
		mat[1][1] = y2;
		mat[1][2] = y3;
		mat[1][3] = y4;
		
		mat[2][0] = z1;
		mat[2][1] =	z2;
		mat[2][2] = z3;
		mat[2][3] = z4;
		
		mat[3][0] = w1;
		mat[3][1] = w2;
		mat[3][2] = w3;
		mat[3][3] = w4;
	}
	
	public Mat4(Vec4 a, Vec4 b, Vec4 c, Vec4 d) {
		for (int i = 0; i < 4; i++) {
			mat[0][i] = a.getElement(i);
		}
		
		for (int i = 0; i < 4; i++) {
			mat[1][i] = b.getElement(i);
		}
		
		for (int i = 0; i < 4; i++) {
			mat[2][i] = c.getElement(i);
		}
		
		for (int i = 0; i < 4; i++) {
			mat[3][i] = d.getElement(i);
		}
	}
	
	/**
	 * Initialize Matrix to 4x4 identity matrix
	 */
	public Mat4() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == j) {
					mat[i][j] = 1;
				} else {
					mat[i][j] = 0;
				}
			}
		}
		
	}
	
	public Mat4(Mat4 m) {
		copy(m);
	}

	
	// GETTER
	
	public double get(int i, int j) {
		return mat[i][j];
	}
	
	
	// HELPER METHODS
	
	public void copy(Mat4 m) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				mat[i][j] = m.get(i, j);
			}
		}
	}
	
	
	// MATRIX MATH METHODS
	
	public Vec4 mult(Vec4 vec) {
		double x = vec.getX();
		double y = vec.getY();
		double z = vec.getZ();
		double w = vec.getW();
		
		Vec4 newVec = new Vec4(x * mat[0][0] + y * mat[0][1] + z * mat[0][2] + w * mat[0][3],
				x * mat[1][0] + y * mat[1][1] + z * mat[1][2] + w * mat[1][3],
				x * mat[2][0] + y * mat[2][1] + z * mat[2][2] + w * mat[2][3],
				x * mat[3][0] + y * mat[3][1] + z * mat[3][2] + w * mat[3][3]);
		
		return newVec;
	}

	public Vec4 mult(Vec3 pos, double w) {
		return mult(new Vec4(pos, w));
	}
	
	public Ray mult(Ray ray) {
		return new Ray( mult(ray.getLoc()) , mult(ray.getDir()) );
	}
	
	public Mat4 mult(Mat4 m) {
		int index = 0;
		double[] temp = new double[16];
		double cell = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					cell += mat[i][k] * m.get(k, j);
				}
				temp[index++] = cell;
				cell = 0;
			}
		}
		
		return new Mat4(temp);
	}
	
	
	public Mat4 transpose() {
		int index = 0;
		double[] temp = new double[16];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[index++] = mat[j][i];
			}
		}
		
		return new Mat4(temp);
	}
	
	
	// STATIC MATRIX MATH METHODS
	
	// Translate
	public static Mat4 translate(double x, double y, double z) {
		return new Mat4(1, 0, 0, x,
		                0, 1, 0, y, 
		                0, 0, 1, z,
		                0, 0, 0, 1);
	}
	
	public static Mat4 translate(Vec3 v) {
		return new Mat4(1, 0, 0, v.getX(),
		                0, 1, 0, v.getY(), 
		                0, 0, 1, v.getZ(),
		                0, 0, 0, 1);
	}
	
	
	// Scale
	public static Mat4 scale(double x, double y, double z) {
		return new Mat4(x, 0, 0, 0,
						0, y, 0, 0,
						0, 0, z, 0,
						0, 0, 0, 1);
	}
	
	public static Mat4 scale(Vec3 v) {
		return new Mat4(v.getX(), 0, 0, 0,
						0, v.getY(), 0, 0,
						0, 0, v.getZ(), 0,
						0, 0, 0, 1);
	}
	
	public static Mat4 scale(double factor) {
		return new Mat4(factor, 0, 0, 0,
						0, factor, 0, 0,
						0, 0, factor, 0,
						0, 0, 0, 1); 
	}
	
	
	// Rotate
	public static Mat4 rotateX(double radians) {
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		
		return new Mat4(1, 0,  0, 0,
						0, c, -s, 0,
						0, s,  c, 0,
						0, 0,  0, 1);
	}
	
	public static Mat4 rotateY(double radians) {
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		
		return new Mat4(c, 0, -s, 0,
						0, 1,  0, 0,
						s, 0,  c, 0,
						0, 0,  0, 1);
	}
	
	public static Mat4 rotateZ(double radians) {
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		
		return new Mat4(c, -s, 0, 0,
						s,  c, 0, 0,
						0,  0, 1, 0,
						0,  0, 0, 1);
	}
	
	public static Mat4 rotate(double radians, Vec3 axis) {
		double x = axis.getX();
		double y = axis.getY();
		double z = axis.getZ();
		
		double c = Math.cos(radians);
	    double omc = 1 - c;
	    double s = Math.sin(radians);

	    return new Mat4(x*x*omc + c,   x*y*omc - z*s, x*z*omc + y*s, 0,
	    				x*y*omc + z*s, y*y*omc + c,   y*z*omc - x*s, 0,
	        			x*z*omc - y*s, y*z*omc + x*s, z*z*omc + c,   0,
	        			0, 0, 0, 1);
	}
	
	
	// DEBUGGING
	
	public String toString() {
		String ret = "[[ ";
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double val = Math.abs(mat[i][j]) < 0.0000000001 ? 0 : mat[i][j]; // remove unnecessary incorrect precision
				ret += val + " ";
				if (j != 3) {
					ret += "\t";
				}
			}
			if (i != 3) {
				ret += "]\n [ ";
			}
		}
		
		return ret + "]]";
	}
	
}
