import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


class Image {
	public static BufferedImage img = null;
	public static JFrame frame = new JFrame();
	public static JLabel lbl = new JLabel();
	public static int counter = 0;

	//displays the image in a frame
	public static void UpdateImage() throws IOException {
		BufferedImage img = ImageIO.read(new File(
				MainVid.directory+"cap1.jpg"));
		ImageIcon icon = new ImageIcon(img);
		frame.setLayout(new FlowLayout());
		frame.setSize(640, 480);
		lbl.setIcon(icon);
		if (counter == 0) {
			counter++;
			frame.add(lbl);
		}
		frame.setVisible(true);
	}
}