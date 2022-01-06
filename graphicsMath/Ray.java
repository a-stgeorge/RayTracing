package graphicsMath;

public class Ray {

	private Vec4 location, direction;
	
	/** Instance variable for intersection calculation optimization, specifically for cubes */
	private Vec3 sign;
	private Vec3 inverseSign;
	
	/** Instance variable to minimize divisions */
	private Vec3 inverseDir;
	
	
	// CONSTRUCTOR
	
	public Ray(Vec4 loc, Vec4 dir) {
		location = loc;
		direction = dir;
		
		inverseDir = new Vec3(1 / direction.getX(), 1 / direction.getY(), 1 / direction.getZ());
		sign = new Vec3(GMath.sign(inverseDir.getX()), GMath.sign(inverseDir.getY()), GMath.sign(inverseDir.getZ()));
		inverseSign = new Vec3(GMath.inverseSign(inverseDir.getX()), 
							GMath.inverseSign(inverseDir.getY()), 
							GMath.inverseSign(inverseDir.getZ()));
	}
	
	
	// GETTERS
	
	public Vec4 getLoc() {
		return location;
	}
	
	public Vec4 getDir() {
		return direction;
	}
	
	/**
	 * Returns Vec3 of computed sign values using GMath sign funciton
	 * @return
	 */
	public Vec3 getSign() {
		return sign;
	}
	
	public Vec3 getInvSign() {
		return inverseSign;
	}
	
	public Vec3 getInverseDir() {
		return inverseDir;
	}
	
	
	// RAY GRAPHICS CALCULATIONS
	
	public Vec4 getPositionAlongRay(double t) {
		return location.add(direction.mult(t));
	}
	
	public Ray transform(Mat4 mat) {
		return new Ray(mat.mult(location), mat.mult(direction));
	}
	
	
	// DEBUGGING
	
	public String toString() {
		return "Pos: " + location.toString() + "\t Dir: " + direction.toString();
	}
	
}
