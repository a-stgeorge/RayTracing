package graphicsMath;

public class Cube extends Solid {
	
	public Cube(double x1, double y1, double z1, double x2, double y2, double z2) {
		getTransformation().pushScale(new Vec3(x2 - x1, y2 - y1, z2 - z1));
		getTransformation().pushTranslation(new Vec3(x1, y1, z1));
	}
	
	public Cube(Vec3 min, Vec3 max) {
		getTransformation().pushScale(max.subtract(min));
		getTransformation().pushTranslation(min);
	}
	
	public Cube(double x, double y, double z) {
		getTransformation().pushTranslation(new Vec3(x, y, z));
	}
	
	public Cube(Vec3 pos) {
		getTransformation().pushTranslation(pos);
	}
	
	public Cube(Vec3 pos, double size) {
		getTransformation().pushScale(2);
		getTransformation().pushTranslation(pos);
	}
	
	
	// RAY INTERSECTION CHECKING
	
	/**
	 * In model space, cube is bounded by axis planes, as well as x = 1, y = 1, and z = -1.
	 * Inspired by https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection.
	 * Normals, actual ray intersection point calculation, BUFFER, and others were my own modifications.
	 * 
	 * Ray's sign methods use GMath's sign methods, which are built specifically for this intersection check
	 * 
	 * @param ray Ray to check for intersection. Must be in model space.
	 * @return Intersection class with necessary variables
	 */
	@Override
	public Intersection intersectsModel(Ray ray) {
		// Use BUFFER here to fix edge case NaN issue
		double tMin = (ray.getInvSign().getX() - ray.getLoc().getX() + BUFFER) * ray.getInverseDir().getX();
		double tMax = (ray.getSign().getX() - ray.getLoc().getX() + BUFFER) * ray.getInverseDir().getX();
		double tYMin = (ray.getInvSign().getY() - ray.getLoc().getY() + BUFFER) * ray.getInverseDir().getY();
		double tYMax = (ray.getSign().getY() - ray.getLoc().getY() + BUFFER) * ray.getInverseDir().getY();
		
		Vec4 normal = new Vec4(1, 0, 0, 0);
		if (ray.getSign().getX() == 1) {
			normal = new Vec4(-1, 0, 0, 0);
		}
		
		if ((tMin > tYMax) || (tYMin > tMax)) {
			return null;
		} if (tYMin > tMin) {
			normal = new Vec4(0, 1, 0, 0);
			if (ray.getSign().getY() == 1) {
				normal = new Vec4(0, -1, 0, 0);
			}
			tMin = tYMin;
		} if (tYMax < tMax) {
			tMax = tYMax;
		}
		
		double tZMin = (ray.getInvSign().getZ() - ray.getLoc().getZ() + BUFFER) * ray.getInverseDir().getZ();
		double tZMax = (ray.getSign().getZ() - ray.getLoc().getZ() + BUFFER) * ray.getInverseDir().getZ();
		
		if ((tMin > tZMax) || (tZMin > tMax)) {
			return null;
		} if (tZMin > tMin) {
			normal = new Vec4(0, 0, 1, 0);
			if (ray.getSign().getZ() == 1) {
				normal = new Vec4(0, 0, -1, 0);
			}
			tMin = tZMin;
		} if (tZMax < tMax) {
			tMax = tZMax;
		}
		
		return new Intersection(ray.getPositionAlongRay(tMin), normal, this);
	}
	
	public boolean hitsModel(Ray ray) {
		double tMin = (ray.getInvSign().getX() - ray.getLoc().getX() + BUFFER) * ray.getInverseDir().getX();
		double tMax = (ray.getSign().getX() - ray.getLoc().getX() + BUFFER) * ray.getInverseDir().getX();
		double tYMin = (ray.getInvSign().getY() - ray.getLoc().getY() + BUFFER) * ray.getInverseDir().getY();
		double tYMax = (ray.getSign().getY() - ray.getLoc().getY() + BUFFER) * ray.getInverseDir().getY();
				
		if ((tMin > tYMax) || (tYMin > tMax)) {
			return false;
		} if (tYMin > tMin) {
			tMin = tYMin;
		} if (tYMax < tMax) {
			tMax = tYMax;
		}
		
		double tZMin = (ray.getInvSign().getZ() - ray.getLoc().getZ() + BUFFER) * ray.getInverseDir().getZ();
		double tZMax = (ray.getSign().getZ() - ray.getLoc().getZ() + BUFFER) * ray.getInverseDir().getZ();
		
		if ((tMin > tZMax) || (tZMin > tMax)) {
			return false;
		} if (tZMin > tMin) {
			tMin = tZMin;
		} if (tZMax < tMax) {
			tMax = tZMax;
		}
		
		return tMin > BUFFER && tMin < 1 - BUFFER;  // Fix round off error
	}
	
	
}
