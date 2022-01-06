/**
 * @author Aidan St. George
 * Parent class for scene geometry
 */

package graphicsMath;

public abstract class Solid {
		
	public final static double BUFFER = 0.00000001;
	
	/** Material properties for an object */
	private Vec4 color;
	private Vec4 specularColor = new Vec4(1, 1, 1, 1);
	private double specularExp = 20;
	private double reflectivity = 0;
	private double diffuseConst = 1;

	/** Transformation to transform point coordinates from model space to world space 
	 *  to send back to ray tracer. */
	private Transformation modelView;
	
	
	// CONSTRUCTORS
	
	public Solid() {
		modelView = new Transformation();
	}
	
	
	// COLOR GETTER AND SETTERS
	
	public Vec4 getColor() {
		if (color == null) {
			return new Vec4(0, 0, 0, 1);
		}
		
		return color;
	}
	
	public Vec4 getSpecularColor() {
		return specularColor;
	}
	
	public void setSpecularColor(Vec4 specColor) {
		specularColor = specColor;
	}
	
	public double getSpecularExp() {
		return specularExp;
	}
	
	public void setSpecularExp(double specExp) {
		specularExp = specExp;
	}
	
	public double getDiffuseConst() {
		return diffuseConst;
	}
	
	public void setDiffuseConst(double diffConst) {
		diffuseConst = diffConst;
	}
	
	public double getReflectivity() {
		return reflectivity;
	}
	
	public void setReflectivity(double reflect) {
		reflectivity = reflect;
	}
	
	public Transformation getTransformation() {
		return modelView;
	}
	
	
	public void setColor(Vec4 c) {
		color = c;
	}
	
	public void setColoring(Vec4 col, Vec4 specColor, double specExp, double diffConst, double reflect) {
		color = col;
		specularColor = specColor;
		specularExp = specExp;
		diffuseConst = diffConst;
		reflectivity = reflect;
	}

	
	// RAY TRACING METHODS
	
	/**
	 * Method that checks if a ray intersects this solid. First converts ray to model space,
	 * calls child class method intersects, then converts result back to world coordinates if 
	 * it is not null
	 * @param ray Ray to check against. Assumes ray is in world space.
	 * @return Method returns z value of intersection for priority calculation. Returns -1 if no intersection.
	 */
	public Intersection intersects(Ray ray) {
		Intersection result = intersectsModel(modelView.invTransform(ray)); // Transform ray to model space
		
		if (result == null) {
			return null;
		}
		
		result.transform(modelView.getTransformation());  // Transform back to world space
		
		return result;
	}
	
	public abstract Intersection intersectsModel(Ray ray); // Implemented for each type of solid
	
	
	/**
	 * Method that checks if a ray intersects this solid. Much quicker method than intersects(), because 
	 * it just returns a boolean once intersection or non intersection is found.
	 * @param ray Ray to check against. Assumes ray is in world space.
	 * @return Returns false if object is not between 0 and 1 indices on the ray, true if so.
	 */
	public boolean hits(Ray ray) {
		return hitsModel(modelView.invTransform(ray)); // Transform ray to model space
	}
	
	public abstract boolean hitsModel(Ray ray); // Implemented in each type of solid
	
}
