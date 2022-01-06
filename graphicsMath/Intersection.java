package graphicsMath;

/**
 * Helper class to store ray intersection data for use in finding color
 * @author Aidan St. George
 */
public class Intersection {
	
	private Vec4 position;
	private Vec4 normal;
	private Solid solid;
	
	
	// CONSTRUCTOR
	
	public Intersection(Vec4 pos, Vec4 norm, Solid s) {
		position = pos;
		normal = norm;
		solid = s;
	}
	
	
	// GETTERS
	
	public Vec4 getPos() {
		return position;
	}
	
	public Vec4 getNorm() {
		return normal;
	}
	
	public Solid getSolid() {
		return solid;
	}
	
	
	// CALCULATIONS
	
	public void transform(Mat4 mat) {
		position = mat.mult(position);
		normal = mat.mult(normal);
	}
	
}
