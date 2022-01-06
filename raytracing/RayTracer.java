/**
 * @author Aidan St. George
 * Class that houses ray tracing methods. Includes separate Phong Lighting, Shadow checking, Reflection methods, as
 * well as others.
 */

package raytracing;

import java.util.ArrayList;

import graphicsMath.*;

public class RayTracer {

	private Screen screen; 
	private ArrayList<Solid> solids;
	private ArrayList<Light> lights;
	private ArrayList<Light> transformedLights;
	private Transformation eyeView;

	private Vec3 ambientLight = new Vec3(0.4, 0.4, 0.4);

	final static Vec4 EYE = new Vec4(0, 0, 0, 1);
	final static double BACK = 50;
	final static double FRONT = 0.5;
	final static int REFLECT_DEPTH = 5;
	
	final static boolean DO_SHADOWS = true;
	
	
	// TODO remove
	public static boolean tracker = false;
	
	public RayTracer (Screen s) {
		screen = s;
		solids = new ArrayList<Solid>();
		lights = new ArrayList<Light>();
		transformedLights = new ArrayList<Light>();
		eyeView = new Transformation();
	}
	
	public void rayTrace(double width, double height, double scale, double step) {
		setTransformedLights();
		
		
		// MAIN RAY TRACING LOOPS
		Intersection intersect = null;
		Intersection closest;		
		double closestDist = BACK;
		double distance;
		Vec4 direction;
		
		for (double r = 0; r + step <= height; r += step) {
			for (double c = 0; c + step <= width; c += step) {
				
				// TODO remove
				if (c == 400 && r == 270) {
					tracker = true;
				}
				
				// Calculate Ray
				direction = new Vec4(width / scale * (2 * c / width - 1), -height / scale * (2 * r / height - 1), -1, 0);
				
				// Find closest intersection
				closest = null;
				closestDist = BACK;
				Ray ray = new Ray(EYE, direction);
				
				for (Solid obj : solids) {
					intersect = obj.intersects(eyeView.transform(ray));  // Check if ray intersects object (in eye space)
					if (intersect == null) continue;
					
					intersect.transform(eyeView.getInvTransformation()); // Transform back to eyeSpace
					
					distance = intersect.getPos().getMagnitude3();
					if (intersect.getPos().getZ() < 0 && distance < closestDist && distance > FRONT) {
						closest = intersect; // transform back to model
						closestDist = distance;
					}
				}
				
				if (closest == null) {
					continue; // No intersection, don't get color
				}
				
				// Get color and display
				Vec3 color = getColor(EYE, closest, REFLECT_DEPTH);
				screen.setPixel((int) r, (int) c, color, step);
			}
		}
		
		// Done tracing!
		
	}
	
	
	/**
	 * Method that gets a color at the point of intersection given an intersection from the ray
	 * tracer. Returns color at that point.
	 * 
	 * Currently supports:
	 *  - Phong Lighting: Ambient, Diffuse, and Specular Components
	 *  - Shadows
	 *  - Reflection
	 * 
	 * Upcoming Features:
	 * 
	 * @param intersect
	 * @return
	 */
	private Vec3 getColor(Vec4 eye, Intersection intersect, int depth) {
		
		if (depth == 0) {
			return new Vec3(0, 0, 0); // prevent recursive loops
		}
		
		Vec3 color = intersect.getSolid().getColor().toVec3().mult(ambientLight);
		
		Vec3 N = intersect.getNorm().toVec3().normalize();  // normal
		Vec3 V = eye.subtract(intersect.getPos()).toVec3().normalize();  // to eye
		
		for (Light light : transformedLights) {
			Vec3 L = light.getPos().subtract(intersect.getPos().toVec3()); // don't normalize, checkShadow needs un-normalized
			
			boolean checkShadow = false;
			for (Solid solid : solids) {
				if (!DO_SHADOWS) break;
				
				if (intersect.getSolid() != solid && checkShadow(intersect, solid, L, V)) {
					checkShadow = true;
					solids.remove(solid);
					solids.add(0, solid);
					break;
				}
			}
			
			L = L.normalize(); // now normalize
			
			if (checkShadow) continue;

			Vec3 R = L.mult(-1).reflect(N).normalize();  // reflected from light
			
			if (intersect.getSolid().getReflectivity() > 0) {
				color = color.add(getReflection(intersect, new Vec4(V.reflect(N), 0), depth - 1)
									.mult(intersect.getSolid().getReflectivity()));
			}
			
			color = color.add(getPhongLighting(intersect, light, L, N, R, V));
		}
			
		return color;
	}
	
	
	private Vec3 getReflection(Intersection origin, Vec4 direction, int depth) {

		// TODO remove
		if (tracker) {
			tracker = false;
		}
		
		Intersection intersect;
		Intersection closest = null;
		double closestDist = BACK;
		Vec4 reflectionVec;  // For distance and direction checking
		
		Ray ray = new Ray(origin.getPos(), direction);
		
		for (Solid obj : solids) {
			if (origin.getSolid() == obj) continue;  // No self-reflection
			
			intersect = obj.intersects(eyeView.transform(ray));  // Check if ray intersects object (in eye space)
			
			if (intersect == null) continue;
			
			intersect.transform(eyeView.getInvTransformation()); // Transform back to eyeSpace
			reflectionVec = intersect.getPos().subtract(origin.getPos());
			if (reflectionVec.dot(direction) < 0 && reflectionVec.getMagnitude3() < closestDist) {
				closest = intersect; // transform back to model
				closestDist = reflectionVec.getMagnitude3();
			}
		}
		
		if (closest == null) {
			return new Vec3(0, 0, 0); // No intersection, don't get color
		}
		
		// Get color and display
		return getColor(origin.getPos(), closest, depth);
	}
	
		
	/**
	 * 
	 * @param intersect
	 * @param solid
	 * @param L MUST not be normalized, magnitude should be distance from surface to light
	 * @return
	 */
	private boolean checkShadow(Intersection intersect, Solid solid, Vec3 L, Vec3 V) {
		Ray ray = new Ray( intersect.getPos().add(new Vec4(V.mult(0.0001), 0)) , new Vec4(L, 0) );
		
		return solid.hits(eyeView.transform(ray));
	}
	
	
	private Vec3 getPhongLighting(Intersection intersect, Light light, Vec3 L, Vec3 N, Vec3 R, Vec3 V) {
		Vec4 specularColor = intersect.getSolid().getSpecularColor();
		double specularExp = intersect.getSolid().getSpecularExp();
		
		Vec3 diffuse = intersect.getSolid().getColor().mult(Math.max(L.dot(N), 0))
				.toVec3().mult(light.getColor()).mult(intersect.getSolid().getDiffuseConst());
		
		Vec3 specular = new Vec3(0, 0, 0);
		if (specularExp > 0) {
			specular = specularColor.mult(Math.pow(Math.max(R.dot(V), 0), specularExp))
				.toVec3().mult(light.getColor());
		}	
		
		if (L.dot(N) < 0) {
			specular = new Vec3(0, 0, 0);
		}
			
		return diffuse.add(specular);
	}
	
	
	/**
	 * Pre-transforms lights into eye space, so transformation only happens once
	 */
	private void setTransformedLights() {
		for (Light light : lights) {
			transformedLights.add(eyeView.invTransform(light));
		}
	}
	
	
	public void addSolid(Solid s) {
		solids.add(s);
	}
	
	public void addLight(Light l) {
		lights.add(l);
	}
	
	public void setAmbient(Vec3 color) {
		ambientLight = color;
	}
	
	public Transformation getEyeView() {
		return eyeView;
	}
	
}
