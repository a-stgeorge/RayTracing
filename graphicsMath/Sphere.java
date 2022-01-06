package graphicsMath;

public class Sphere extends Solid {
	
	
	public Sphere(Vec3 center, double radius) {

		// Scale first, then translate
		getTransformation().pushScale(radius);
		getTransformation().pushTranslation(center);
	}
	
	
	// RAY INTERSECTION CHECKING
	
	/**
	 * Generic sphere is centered at (0,0,0) with radius 1.
	 * Math taken from Computer Graphics using OpenGL (Hill) textbook. Direction normalization optimization was
	 * not taken from textbook.
	 * @param ray Ray to check for intersection with. Must be in model space.
	 * @return Intersection where ray hits sphere
	 */
	@Override
	public Intersection intersectsModel(Ray ray) {
		ray.getDir().normalize();
		double b = ray.getLoc().dot(ray.getDir()); // a = 1
		double c = ray.getLoc().getMagnitude3() * ray.getLoc().getMagnitude3() - 1;
		double discriminant = b * b - c;
		
		if (discriminant < 0) {
			return null;
		}
		
		double t1 = -b - discriminant;
		
		if (t1 < 0) {  // One side is behind ray starting point
			double t2 = -b + discriminant;
			Vec4 pos2 = ray.getPositionAlongRay(t2);
			return new Intersection(pos2, pos2.vector(), this);
		}
		
		Vec4 pos1 = ray.getPositionAlongRay(t1);		
		return new Intersection(pos1, pos1.vector(), this);
	}
	
	
	public boolean hitsModel(Ray ray) {
		double a = ray.getDir().getMagnitude3() * ray.getDir().getMagnitude3();
		double b = ray.getLoc().dot(ray.getDir());
		double c = ray.getLoc().getMagnitude3() * ray.getLoc().getMagnitude3() - 1;
		double discriminant = b * b - a * c;
		
		if (discriminant < 0) {
			return false;
		}
		
		double t1 = -b - Math.sqrt(discriminant);
		
		if (t1 < 0 || t1 > a) {  // Hit point is not between surface and light
			double t2 = -b + Math.sqrt(discriminant);
			return t2 > 0 && t2 < a;		
		}
				
		return true;
	}
	
}
