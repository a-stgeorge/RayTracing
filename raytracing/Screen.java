/**
 * @author Aidan St. George
 * Class that handles display window
 */

package raytracing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphicsMath.Vec3;

public class Screen {

	final static int JFRAME_WIDTH_BUFFER = 16;
	final static int JFRAME_HEIGHT_BUFFER = 39;
	
	private SceneImage image;
	/** Pixel Array used for ray multi-casting */
	private Vec3[][] pixels;
	
	public Screen(int w, int h) {		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w + JFRAME_WIDTH_BUFFER, h + JFRAME_HEIGHT_BUFFER);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Ray Tracer by Aidan St. George");
		frame.setResizable(false);
		image = new SceneImage(w, h);
		frame.add(image);
		frame.setVisible(true);
		
		pixels = new Vec3[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				pixels[i][j] = new Vec3(0, 0, 0);
			}
		}
	}
	
	public void setPixel(int r, int c, Vec3 color) {
		
		image.drawPoint(r, c, vec3ToColor(color));
	}
	
	public void setPixel(int r, int c, Vec3 color, double width) {
		if (width < 1) { // cast multiple rays per pixel
			color = pixels[r][c].add(color.mult(width * width));
			image.drawPoint(r, c, vec3ToColor(color) );
			pixels[r][c] = color;
		} else {
			image.drawPoint(r, c, vec3ToColor(color), (int) width);
		}
	}
	
	private Color vec3ToColor(Vec3 color) {
		color = color.mult(255);
		int red = (int) color.getX();
		int green = (int) color.getY();
		int blue = (int) color.getZ();
		
		if (red > 255) {
			red = 255;
		} if (green > 255) {
			green = 255;
		} if (blue > 255) {
			blue = 255;
		}
		
		if (red < 0) {
			red = 0;
		} if (green < 0) {
			green = 0;
		} if (blue < 0) {
			blue = 0;
		}
		
		return new Color(red, green, blue);
		
	}
	
	
	/**
	 * Getter for output image
	 *
	 */
	public JPanel getScreenImage() {
		return image;
	}
	
	
	private class SceneImage extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private final int width, height;
		
		// Pixel array for painting screen
		private Color[][] pixels;
		
		public SceneImage(int w, int h) {
			width = w; height = h;
			
			setSize(width, height);
			setBackground(Color.BLACK);
			setFocusable(true);
			setDoubleBuffered(true);
			
			pixels = new Color[height][width];
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Draw pixel array
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					if (pixels[r][c] != null) {
						g.setColor(pixels[r][c]);
						g.drawLine(c, r, c, r);;
					}
				}
			}
			
		}
		
		private void drawPoint(int r, int c, Color color) {
			pixels[r][c] = color;
			repaint();
		}
		
		private void drawPoint(int r, int c, Color color, int width) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					pixels[r + i][c + j] = color;
				}
			}	
			repaint();
		}
		
	}
	
}
