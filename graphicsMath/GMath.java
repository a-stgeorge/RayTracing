package graphicsMath;

import java.lang.Math;

public class GMath {

	public static double toRadians(double degrees) {
		return degrees / 180 * Math.PI;
	}
	
	public static double toDegrees(double radians) {
		return radians / Math.PI * 180;
	}
	
	/**
	 * Returns 1 if val is negative. 0 otherwise.
	 * @param val
	 */
	public static int sign(double val) {
		if (val < 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static int inverseSign(double val) {
		if (val < 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static int inverSign(int val) {
		if (val == 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
