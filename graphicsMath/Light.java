package graphicsMath;

public class Light {
	
	private Vec3 position;
	private Vec3 color;  // No alpha channel for lights
	
	public Light(Vec3 pos, Vec3 col) {
		position = pos;
		color = col;
	}
	
	public Vec3 getPos() {
		return position;
	}
	
	public Vec3 getColor() {
		return color;
	}
	
}
