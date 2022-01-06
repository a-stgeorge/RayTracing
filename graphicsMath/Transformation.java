/**
 * @author Aidan St. George
 * Wrapper class for a 3D transformation. Builds forward and inverse matrices at the same time to ease transforming both ways.
 */

package graphicsMath;

public class Transformation {
	
	/** Transformation Matrix */
	private Mat4 transform;
	
	/** Inverse transformation matrix, to be build alongside transform */
	private Mat4 invTransform;
	
	
	// CONSTRUCTORS
	public Transformation() {
		transform = new Mat4();  // initialize to identity
		invTransform = new Mat4();
	}
	
	
	// SET UP TRANSFORMATION
	
	// Translate
	public void pushTranslation(Vec3 offset) {
		transform = Mat4.translate(offset).mult(transform);
		invTransform = invTransform.mult(Mat4.translate(offset.mult(-1)));
	}
	
	// Scale
	public void pushScale(Vec3 factor) {
		transform = Mat4.scale(factor.getX(), factor.getY(), factor.getZ()).mult(transform);
		invTransform = invTransform.mult(
				Mat4.scale(1 / factor.getX(), 1 / factor.getY(), 1 / factor.getZ()));
	}
	
	public void pushScale(double factor) {
		transform = Mat4.scale(factor).mult(transform);
		invTransform = invTransform.mult(Mat4.scale(1 / factor));
	}
	
	// Rotate
	public void pushRotate(double radians, Vec3 axis) {
		transform = Mat4.rotate(radians, axis).mult(transform);
		invTransform = invTransform.mult(Mat4.rotate(-radians, axis));
	}
	
	public void pushRotateX(double radians) {
		transform = Mat4.rotateX(radians).mult(transform);
		invTransform = invTransform.mult(Mat4.rotateX(-radians));
	}
	
	public void pushRotateY(double radians) {
		transform = Mat4.rotateY(radians).mult(transform);
		invTransform = invTransform.mult(Mat4.rotateY(-radians));
	}
	
	public void pushRotateZ(double radians) {
		transform = Mat4.rotateZ(radians).mult(transform);
		invTransform = invTransform.mult(Mat4.rotateZ(-radians));
	}
	
	
	// TRANSFORMATION METHODS
	
	// Forward
	public Vec4 transform(Vec4 vec) {
		return transform.mult(vec);
	}
	
	public Ray transform(Ray ray) {
		return transform.mult(ray);
	}
	
	public Light transform(Light light) {
		return new Light(transform.mult(light.getPos(), 1).toVec3(), light.getColor());
	}
	
	// Inverse
	public Vec4 invTransform(Vec4 vec) {
		return invTransform.mult(vec);
	}
	
	public Ray invTransform(Ray ray) {
		return invTransform.mult(ray);
	}
	
	public Light invTransform(Light light) {
		return new Light(invTransform.mult(light.getPos(), 1).toVec3(), light.getColor());
	}
	
	
	// GETTERS AND SETTERS
	public Mat4 getTransformation() {
		return transform;
	}
	
	public Mat4 getInvTransformation() {
		return invTransform;
	}
	
}
