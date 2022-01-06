/**
 * @author Aidan St. George
 * Class that handles working with the file system. Currently only saves images to png format, but in future might
 * read in a scene file.
 */

package raytracing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class IO {

	public static void saveImage(JPanel panel, String fileName) {
		BufferedImage bImg = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D cg = bImg.createGraphics();
		panel.paintAll(cg);
		try {
			if (ImageIO.write(bImg, "png", new File(fileName))) {
				System.out.println("Image Saved.");
			}
		} catch (IOException e) {
            e.printStackTrace();
		}
	}
	
}
