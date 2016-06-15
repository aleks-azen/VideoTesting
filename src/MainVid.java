import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import java.awt.EventQueue;
import java.io.IOException;

import javax.naming.event.EventContext;
import javax.swing.JFrame;

public class MainVid {
	public static String directory = "C:/Users/aleks/Documents/GitHub/personal/VideoTesting/bin/";
	public static void main(String[] args) {
	
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture camera = new VideoCapture();
		camera.open(0);
//		camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 950);
//		camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 760);
		
		if (!camera.isOpened()) {
			System.out.println("Error");
		} else {
			Mat frame = new Mat();
			// int counter = 0;
	               MainUI frames = new MainUI();
	               frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	               frames.setVisible(true);
	        Serial.run();
//			Size s = new Size(640,480);
//	        VideoWriter vWriter = new VideoWriter();
//			vWriter.open(directory+"video.avi",VideoWriter.fourcc('D', 'I', 'V', '3'),20,s,true);
	       while(!Serial.runRestOfProgram);
			while (true) {
				long startTime = System.nanoTime();
//				if(vWriter.isOpened())System.out.println("Recing");
				
				if (camera.read(frame)) {
					System.out.println("Frame Obtained");
//					vWriter.write(frame);
					Imgcodecs
							.imwrite(
									directory+"cap.jpg",
									frame);
					ObjectDetector.runColorDetection();
					try {
						Image.UpdateImage();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000000;
				System.out.println("Ran for:" + duration);
			}
		}
		camera.release();
	}

}

