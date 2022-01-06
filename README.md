# RayTracing

This is a basic ray tracing program written from scratch in Java, written for a final project in computer graphics. This is not designed for effeciency or speed.

### Features
- Object Detection for Spheres and Cubes
- Transformations from generic spheres and cubes
- Multiple rays per pixel (anti-aliasing)
- Basic Phong Lighting
- Shadows
- Reflections

### Potential Future Additions
- Texture mapping
- Refraction

## Example

4 Colored Marbles on a Checkerboard:

![Ray Tracing output example](https://github.com/ais97696/RayTracing/blob/790e7717dfb5845c6476075433160fdfd5d1467a/OutputExample.png)

## About

This project is originally an Eclipse project. It includes two packages; supporting math, data types, and classes are in the graphicsMath package, the Ray Tracing methods and Main Method are in the raytracing package.

To run the ray tracer, run the Driver class. This class includes several pre-built scenes that can be ran by switching the method call in the main method. If the scene is rendering slowly, a quick fix for a preview scene would be to switch the step variable in the scene method to a larger number, such as 2.

