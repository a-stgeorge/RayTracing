/**
 * @author Aidan St. George
 * Run this class to start the Ray Tracer. Different scenes are already built in the various methods below.
 */

package raytracing;

import graphicsMath.*;

public class Driver {
	
	public static boolean CUBES = true;
	public static boolean SPHERES = true;
	public static boolean LIGHT2 = true;
	
	
	// Colors
	public static Vec4 brown = new Vec4(0.2941, 0.1608, 0.1059, 1);
	public static Vec4 lessBrown = new Vec4(0.7451, 0.6392, 0.5647, 1);
			
	
	public static void main(String args[]) {
		mirrors();
	}
	
	
	public static void mirrors() {
		int width = 800;
		int height = 500;
		int scale = 1200;
		double step = 0.5;
		String filename = "Mirrors.png";
		
		Screen screen = new Screen(width, height);
		RayTracer rayTracer = new RayTracer(screen);
		
		
		// EYE TRANSFORMATIONS
//		rayTracer.getEyeView().pushRotateX(-Math.PI / 10);
		rayTracer.getEyeView().pushTranslation(new Vec3(0, 2, 8));
		
		
		// LIGHTS
		rayTracer.setAmbient(new Vec3(0.3, 0.3, 0.3));
		
		Light l1 = new Light(new Vec3(0, 50, 0), new Vec3(1, 1, 1));
		rayTracer.addLight(l1);
		
		
		// Marbles
		Sphere sph1 = new Sphere(new Vec3(0, 2, -2), 1);
		sph1.setColoring(new Vec4(0.4, 0.4, 0.4, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
		rayTracer.addSolid(sph1);
		
		Sphere sph2 = new Sphere(new Vec3(-2, 5, -2), 0.7);
		sph2.setColoring(new Vec4(0.6, 0.0, 0.0, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
		rayTracer.addSolid(sph2);

		Sphere sph3 = new Sphere(new Vec3(2, 3, -2), 0.5);
		sph3.setColoring(new Vec4(0.0, 0.0, 0.6, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
		rayTracer.addSolid(sph3);
		
		Sphere sph4 = new Sphere(new Vec3(-2, 0, 2), 1);
		sph4.setColoring(new Vec4(0.6, 0.0, 0.6, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
		rayTracer.addSolid(sph4);
		
		Sphere sph5 = new Sphere(new Vec3(2, 4, 2), 0.7);
		sph5.setColoring(new Vec4(0.0, 0.6, 0.0, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
		rayTracer.addSolid(sph5);
		
		// Checkerboard
		Cube square;
		for (int i = -4; i < 4; i++) {
			for (int j = -4; j < 4; j++) {
				square = new Cube(i, -0.1, j, i + 1, 0, j + 1);
				square.setSpecularExp(0);
				if ((i + j) % 2 == 0) square.setColor(new Vec4(0.1, 0.1, 0.1, 1));
				else square.setColor(new Vec4(0.9, 0.9, 0.9, 1));
				rayTracer.addSolid(square);
			}
		}
		
		// Walls
		Cube wall1 = new Cube(-4.1, 0, -4, -4, 10, 4);
		wall1.setColoring(new Vec4(0.4, 0.4, 0.4, 1), new Vec4(1, 1, 1, 1), 20, 0.2, 0.5);
		rayTracer.addSolid(wall1);
		
		Cube wall2 = new Cube(4, 0, -4, 4.1, 10, 4);
		wall2.setColoring(new Vec4(0.4, 0.4, 0.4, 1), new Vec4(1, 1, 1, 1), 20, 0.2, 0.5);
		rayTracer.addSolid(wall2);
		
		Cube wall3 = new Cube(-5, 0, -4.1, 5, 10, -4);
		wall3.setColor(new Vec4(0.4, 0.4, 0.8, 1));
		rayTracer.addSolid(wall3);
		
		rayTracer.rayTrace(width, height, scale, step);
		IO.saveImage(screen.getScreenImage(), filename);
	}
	
	
	public static void podiums() {
		int width = 1600;
		int height = 1000;
		int scale = 2400;
		double step = 0.25;
		String filename = "Podiums.png";
		
		Screen screen = new Screen(width, height);
		RayTracer rayTracer = new RayTracer(screen);
		
		
		// EYE TRANSFORMATIONS
		rayTracer.getEyeView().pushRotateX(-Math.PI / 8);
		rayTracer.getEyeView().pushTranslation(new Vec3(0, 8, 10));
		
		
		// LIGHTS
		rayTracer.setAmbient(new Vec3(0.3, 0.3, 0.3));
		
		Light l1 = new Light(new Vec3(0, 50, 0), new Vec3(0.4, 0.4, 0.4));
		rayTracer.addLight(l1);
		
		Light l2 = new Light(new Vec3(0, 20, 10), new Vec3(0.4, 0.4, 0.4));
		rayTracer.addLight(l2);
		
		Light l3 = new Light(new Vec3(10, 20, 10), new Vec3(0.2, 0.2, 0.2));
		rayTracer.addLight(l3);
		
		Light l4 = new Light(new Vec3(-10, 20, 10), new Vec3(0.2, 0.2, 0.2));
		rayTracer.addLight(l4);
		
		
		// Marble
		Sphere sph1 = new Sphere(new Vec3(0, 6, 0), 1);
		sph1.setColoring(new Vec4(0.8314, 0.6863, 0.2157, 1), new Vec4(0.797357, 0.723991, 0.208006, 1), 40, 0.05, 0.1);
		rayTracer.addSolid(sph1);

		Sphere sph2 = new Sphere(new Vec3(-3, 5, 0), 1);
		sph2.setColoring(new Vec4(0.7, 0.7, 0.7, 1), new Vec4(0.773911, 0.773911, 0.773911, 1), 40, 0.05, 0.1);
		rayTracer.addSolid(sph2);

		Sphere sph3 = new Sphere(new Vec3(3, 4, 0), 1);
		sph3.setColoring(new Vec4(0.6902, 0.5529, 0.3412, 1), new Vec4(0.774597, 0.458561, 0.200621, 1), 80, 0.05, 0.1);
		rayTracer.addSolid(sph3);
		
		
		// PODIUMS
		
		Cube rect1 = new Cube(-1.2, 0, -1.2, 1.2, 5, 1.2);
		rect1.setSpecularExp(0);
		rect1.setColor(new Vec4(0.6, 0.6, 0.8, 1));
		rayTracer.addSolid(rect1);
		
		Cube rect2 = new Cube(-4.2, 0, -1.2, -1.8, 4, 1.2);
		rect2.setSpecularExp(0);
		rect2.setColor(new Vec4(0.8, 0.6, 0.6, 1));
		rayTracer.addSolid(rect2);

		Cube rect3 = new Cube(4.2, 0, -1.2, 1.8, 3, 1.2);
		rect3.setSpecularExp(0);
		rect3.setColor(new Vec4(0.6, 0.8, 0.6, 1));
		rayTracer.addSolid(rect3);
		
		
		// RAY TRACE
		
		rayTracer.rayTrace(width, height, scale, step);
		IO.saveImage(screen.getScreenImage(), filename);
	}
	
	
	public static void checkerboardMarble() {
		int width = 1600;
		int height = 1000;
		int scale = 2400;
		double step = 1;
		String filename = "1Marble2.0.png";
		
		Screen screen = new Screen(width, height);
		RayTracer rayTracer = new RayTracer(screen);
		
		
		// EYE TRANSFORMATIONS
		rayTracer.getEyeView().pushRotateX(-Math.PI / 16);
		rayTracer.getEyeView().pushTranslation(new Vec3(0, 2, 8));
		
		
		// LIGHTS
		rayTracer.setAmbient(new Vec3(0.3, 0.3, 0.3));
		
		Light l1 = new Light(new Vec3(0, 50, 0), new Vec3(1, 1, 1));
		rayTracer.addLight(l1);
		
		
		// Marble
		Sphere sph1 = new Sphere(new Vec3(0, 0.5, -2), 0.5);
		sph1.setColoring(new Vec4(0.4, 0.4, 0.4, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.0);
		rayTracer.addSolid(sph1);
		
//		Sphere sph2 = new Sphere(new Vec3(-2, 0.5, -2), 0.5);
//		sph2.setColoring(new Vec4(0.6, 0.0, 0.0, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
//		rayTracer.addSolid(sph2);
//		
//		Sphere sph3 = new Sphere(new Vec3(2, 0.5, -2), 0.5);
//		sph3.setColoring(new Vec4(0.0, 0.0, 0.6, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
//		rayTracer.addSolid(sph3);
//		
//		Sphere sph4 = new Sphere(new Vec3(-2, 0.5, 2), 0.5);
//		sph4.setColoring(new Vec4(0.6, 0.0, 0.6, 1), new Vec4(1, 1, 1, 1), 10, 0.2, 0.9);
//		rayTracer.addSolid(sph4);
		
		// Checkerboard
		Cube square;
		for (int i = -4; i < 4; i++) {
			for (int j = -4; j < 4; j++) {
				square = new Cube(i, -0.1, j, i + 1, 0, j + 1);
				square.setSpecularExp(0);
				if ((i + j) % 2 == 0) square.setColor(new Vec4(0.1, 0.1, 0.1, 1));
				else square.setColor(new Vec4(0.9, 0.9, 0.9, 1));
				rayTracer.addSolid(square);
			}
		}
		
		rayTracer.rayTrace(width, height, scale, step);
		
		IO.saveImage(screen.getScreenImage(), filename);
	}
	
		
	public static void scene1() {
		int width = 800;
		int height = 800;
		
		Screen screen = new Screen(width, height);
		RayTracer rayTracer = new RayTracer(screen);
				
		
		// EYE TRANSFORMATIONS
		//rayTracer.getEyeView().pushRotateX(-Math.PI / 4);
		rayTracer.getEyeView().pushTranslation(new Vec3(0, 0, 0));
		
		
		// LIGHTS
		
		rayTracer.setAmbient(new Vec3(0.4, 0.4, 0.4));
		
		Light l1 = new Light(new Vec3(0, 10, 0), new Vec3(1, 1, 1));
		rayTracer.addLight(l1);
		
		if (LIGHT2) {
			Light l2 = new Light(new Vec3(0, 0, 10), new Vec3(0.1, 0.4, 0.3));
			rayTracer.addLight(l2);
		}
		
		
		// SOLIDS
		
		if (SPHERES) {
			Sphere s1 = new Sphere(new Vec3(0, 0, -8), 1);
			s1.setColor(new Vec4(1, 0, 0, 1));
			rayTracer.addSolid(s1);
			
			Sphere s2 = new Sphere(new Vec3(3, 0, -8), 1);
			s2.setColor(new Vec4(1, 1, 0, 1));
			rayTracer.addSolid(s2);
			
			Sphere s3 = new Sphere(new Vec3(-3, 0, -8), 1);
			s3.setColor(new Vec4(0, 1, 1, 1));
			rayTracer.addSolid(s3);
			
			Sphere s4 = new Sphere(new Vec3(0, 3, -8), 1);
			s4.setColor(new Vec4(0, 0, 1, 1));
			rayTracer.addSolid(s4);
			
			Sphere s5 = new Sphere(new Vec3(0, -3, -8), 1);
			s5.setColor(new Vec4(1, 0, 1, 1));
			rayTracer.addSolid(s5);
		}
		
		if (CUBES) {
			Cube c1 = new Cube(new Vec3(-0.5, -0.5, -4.5));
			c1.setColor(new Vec4(0.2, 0.2, 0.2, 1));
			rayTracer.addSolid(c1);
			
			Cube c2 = new Cube(new Vec3(-2.5, -2.5, -4.5));
			c2.setColor(new Vec4(0.5, 0, 0, 1));
			rayTracer.addSolid(c2);
			
			Cube c3 = new Cube(new Vec3(-2.5, -0.5, -4.5));
			c3.setColor(new Vec4(0.5, 0.5, 0, 1));
			rayTracer.addSolid(c3);
			
			Cube c4 = new Cube(new Vec3(-2.5, 1.5, -4.5));
			c4.setColor(new Vec4(0, 0.5, 0, 1));
			rayTracer.addSolid(c4);
			
			Cube c5 = new Cube(new Vec3(-0.5, 1.5, -4.5));
			c5.setColor(new Vec4(0, 0.5, 0.5, 1));
			rayTracer.addSolid(c5);
			
			Cube c6 = new Cube(new Vec3(1.5, 1.5, -4.5));
			c6.setColor(new Vec4(0, 0, 0.5, 1));
			rayTracer.addSolid(c6);
			
			Cube c7 = new Cube(new Vec3(1.5, -0.5, -4.5));
			c7.setColor(new Vec4(0.5, 0, 0.5, 1));
			rayTracer.addSolid(c7);
			
			Cube c8 = new Cube(new Vec3(1.5, -2.5, -4.5));
			c8.setColor(new Vec4(0.5, 0, 0, 1));
			rayTracer.addSolid(c8);
			
			Cube c9 = new Cube(new Vec3(-0.5, -2.5, -4.5));
			c9.setColor(new Vec4(0.5, 0.5, 0.5, 1));
			rayTracer.addSolid(c9);
		}
		
		rayTracer.rayTrace(width, height, 1024, 0.25);	
		IO.saveImage(screen.getScreenImage(), "Scene1.0");
	}
	
	
	public static void scene2() {
		int width = 800;
		int height = 800;
		
		Screen screen = new Screen(width, height);
		RayTracer rayTracer = new RayTracer(screen);
		
		
		rayTracer.getEyeView().pushTranslation(new Vec3(0, 0, 0));
		rayTracer.getEyeView().pushRotateX(-Math.PI / 6);
		
		rayTracer.setAmbient(new Vec3(0.4, 0.4, 0.4));
		
		
		Light l1 = new Light(new Vec3(0, 10, -10), new Vec3(1, 1, 1));
		rayTracer.addLight(l1);
		
		Sphere s1 = new Sphere(new Vec3(0, 0, -10), 1);
		s1.setColor(new Vec4(0.8, 0.2, 0.8, 1));
		rayTracer.addSolid(s1);
		
		rayTracer.rayTrace(width, height, 1024, 1);
	}
	
	public static void print(Object obj) {
		System.out.println(obj);
	}
	
}
