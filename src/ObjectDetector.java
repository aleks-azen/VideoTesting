import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class ObjectDetector {
	final int MIN_OBJECT_AREA = 1 * 1;
	final int FRAME_WIDTH = 640;
	final int FRAME_HEIGHT = 480;
	
	//starting hsv values
	public static short minH =50;
	public static short maxH =96;
	public static short minS = 90;
	public static short maxS = 255;
	public static short minV = 50;
	public static short maxV = 255;
//	final double MAX_OBJECT_AREA = FRAME_HEIGHT*FRAME_WIDTH / 1.5;
	public static void runColorDetection() {
		System.out.println("\nRunning Color Detection");

		//Selects the image to analyze
		Mat image = Imgcodecs
				.imread(MainVid.directory+"cap.jpg");
		Mat HSV = new Mat();
		Mat threshold = new Mat();
		
		//Sets the color range
		Scalar hsv_min = new Scalar(minH, minS,minV);
		Scalar hsv_max = new Scalar(maxH, maxS, maxV);
		
		//process the image and stores hsv values in the HSV Mat
		Imgproc.cvtColor(image, HSV, Imgproc.COLOR_BGR2HSV);
		
		//filters out all color not matching the the threshold
		Core.inRange(HSV, hsv_min, hsv_max, threshold);
		threshold = morphOps(threshold);
		
		//calls tracking method
		trackFilteredObject(threshold, HSV, image);
		// Save the visualized detection.
		// System.out.println("done");
		String filename = MainVid.directory+"cap1.jpg";
		// System.out.println(String.format("Writing %s", filename));
		//updates displayed image
		Imgcodecs.imwrite(filename, image);
	}

	static void trackFilteredObject( Mat threshold, Mat HSV,
			Mat image) {

		// Vector objects;
		Mat temp = new Mat();
		threshold.copyTo(temp);
		// these two vectors needed for output of findContours
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		// find contours of filtered image using openCV findContours function
		Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP,
				Imgproc.CHAIN_APPROX_SIMPLE);
		// use moments method to find our filtered object
		double refArea = 0;
		boolean objectFound = false;
		if (contours.size() > 0) {
			int numObjects = contours.size();
			
			//makes sure that our filter is working properly and not too many objects are being detected
			if (numObjects < 10) {
				try{
					double h = contours.get(0).height();
					double w = contours.get(0).width();
					double a = Imgproc.contourArea(contours.get(0));
					int j =0;
					
					//checks the area of all objects and only uses the largest detected object matching the color profile
					for(int i = 1;i<contours.size();i++){
						if(Imgproc.contourArea(contours.get(i))>a)
						{
						a = Imgproc.contourArea(contours.get(i));
						j = i;
						}
						
					}
					//makes new array with only 1 element which is the largest object found
					List<MatOfPoint> gContour = new ArrayList<MatOfPoint>();
					gContour.add(contours.get(j));
					
					MatOfPoint2f temp2 = new MatOfPoint2f();
					MatOfPoint2f contour2f = new MatOfPoint2f(gContour.get(0).toArray());
					
					//determines contours of the image
					double dist = Imgproc.arcLength(contour2f, false)*.02;
					Imgproc.approxPolyDP(contour2f,temp2,dist,true);
					MatOfPoint points = new MatOfPoint(temp2.toArray());
					//sets a bounding rectangle around the detected contours
					Rect rect = Imgproc.boundingRect(points);
					//draws the boudning rectangle 
					Imgproc.rectangle(image, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height) , new Scalar(255, 0, 0, 255), 6); 
					//draws blob around correct color profile	
					Imgproc.drawContours(image, gContour, -1, new Scalar(255,0,255),2);
					
					//does point calculations for determining distance
					Point bottomDetected = new Point(rect.x+(rect.width/2-45),rect.y+rect.height/2-45);
					Point topDetected = new Point(rect.x+rect.width/2+45,rect.y+rect.height/2+45);
					Point bottomCenterScreen =new Point(Image.frame.getWidth()/2-35,Image.frame.getHeight()/2-40);
					Point topCenterScreen =new Point(Image.frame.getWidth()/2+35,Image.frame.getHeight()/2+20);
					Point centerOfImageBound = new Point(rect.x+rect.width/2,rect.y+rect.height/2);
					Point centerOfScreen = new Point(Image.frame.getWidth()/2,Image.frame.getHeight()/2);
					
//					System.out.println("xScreenCenter = "+centerOfScreen.x+", yScreenCenter = "+centerOfScreen);
//					System.out.println("xImageCenter = "+centerOfImageBound.x+", yImageCenter = "+centerOfImageBound);
//					
					Imgproc.rectangle(image, bottomDetected,topDetected,new Scalar(255,0,255));
					Imgproc.rectangle(image, bottomCenterScreen,topCenterScreen,new Scalar(255,0,255));
					
//					System.out.println(new Double(((centerOfImageBound.x-centerOfScreen.x)/centerOfScreen.x)*100).intValue());
					
					/*Math for determining what to send to the arduino. negative values get remapped to from -100-0 to 101-200
					 * because the arduino handles negative ints in a strange way
					 * Values that are between -5 and 5 percent off center get disregarded because we decided that this is an acceptable margin of error
					 */
					int horizontalPercent = new Double(((centerOfImageBound.x-centerOfScreen.x)/centerOfScreen.x)*100).intValue();
					if (Math.abs(horizontalPercent)>0 && Math.abs(horizontalPercent)<5)horizontalPercent = 0;
					int verticalPercent = new Double(((centerOfImageBound.y-centerOfScreen.y)/centerOfScreen.y)*100).intValue();
					if (Math.abs(verticalPercent)>0 && Math.abs(verticalPercent)<5)horizontalPercent = 0;
					if(horizontalPercent<0)horizontalPercent = 100-horizontalPercent;
					if(verticalPercent<0)verticalPercent = 100-verticalPercent;
					
					/*bitshifts the horizontal percent so that both the vertical and horizontal values can be sent in one short
					 this helps lessen the number of transmissions to the arduino and reduces lag
					horizontal bits = 8msb, vertical bits = 8lsb
					*/
					short toSend = (short) (horizontalPercent<<8);
					toSend |=verticalPercent;
					
					
//					System.out.println("HORIZPERCENT = " + horizontalPercent);
//					System.out.println("VERTICALPERCENT = " + verticalPercent);

					
					
					Serial.direction = toSend;
					
					

				}catch(Exception e){
					
				}
				
			}
		}
		else Serial.direction = 0;
	}

	//filtering function
	public static Mat morphOps(Mat thresh) {
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
				new Size(3, 3));
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
				new Size(8, 8));
		Imgproc.erode(thresh, thresh, erodeElement);
		Imgproc.erode(thresh, thresh, erodeElement);
		Imgproc.dilate(thresh, thresh, dilateElement);
		Imgproc.dilate(thresh, thresh, dilateElement);
		return thresh;
	}
}
