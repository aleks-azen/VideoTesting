import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


class MainUI extends JFrame
{
//	Scalar hsv_min = new Scalar(50, 90, 50);
//	Scalar hsv_max = new Scalar(96, 255, 255);
	//default sstarting values
	public static short minH =50;
	public static short maxH =96;
	public static short minS = 90;
	public static short maxS = 255;
	public static short minV = 50;
	public static short maxV = 255;
    ArrayList<JLabel> labelList = new ArrayList<JLabel>();

   public MainUI()
   {
      setTitle("SliderTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      sliderPanel = new JPanel();
      sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

      //labels for  
      JLabel lHMin = new JLabel();
      lHMin.setName("labHMin");
      JLabel lHMax = new JLabel();
      lHMax.setName("labHMax");
      JLabel lSMin = new JLabel();
      lSMin.setName("labSMin");
      JLabel lSMax = new JLabel();
      lSMax.setName("labSMax");
      JLabel lBMin = new JLabel();
      lBMin.setName("labBMin");
      JLabel lBMax = new JLabel();
      lBMax.setName("labBMax");
      // common listener for all sliders  
      listener = new ChangeListener()
         {
    	  
    	  //determines which ui element was changed and updates the color profile being detected
            public void stateChanged(ChangeEvent event)
            {
               // update text field when the slider value changes
            	if(event.getSource().getClass().equals(new JButton().getClass())){
            	}
            	else{
	               JSlider source = (JSlider) event.getSource();
	               if(source.getName().equals("Hue Minimum")){
	            	   minH=(short) source.getValue();
	            	  ObjectDetector.minH=(short) source.getValue();
	            	  lHMin.setText(Integer.toString(minH));
	               }
	               if(source.getName().equals("Hue Maximum")){
	            	   maxH=(short) source.getValue();
	            	  ObjectDetector.maxH=(short) source.getValue();
	            	  lHMax.setText(Integer.toString(maxH));

	               }
	               if(source.getName().equals("Saturation Minimum")){
	            	   minS=(short) source.getValue();
	            	  ObjectDetector.minS=(short) source.getValue();
	            	  lSMin.setText(Integer.toString(minS));
	               }
	               if(source.getName().equals("Saturation Maximum")){
	            	   maxS=(short) source.getValue();
	            	  ObjectDetector.maxS=(short) source.getValue();
	            	  lSMax.setText(Integer.toString(maxS));
	               }
	               if(source.getName().equals("Brightness Minimum")){
	            	   minV=(short) source.getValue();
	            	  ObjectDetector.minV=(short) source.getValue();
	            	  lBMin.setText(Integer.toString(minV));
	               }
	               if(source.getName().equals("Brightness Maximum")){
	            	   maxV=(short) source.getValue();
	            	  ObjectDetector.maxV=(short) source.getValue();
	            	  lBMax.setText(Integer.toString(maxV));
	               }
//	               textField.setText("" + source.getValue());
	            }
            }
         };

         
      //ui creation
      JSlider slider = new JSlider();
      JButton button = new JButton();
      
      
      //creates buttons for preset values
      button.setText("Light Blue");
      button.setName("LBBut");
      addButton(button,"LB");
      
      button.setText("Dark Blue");
      button.setName("DBBut");
      addButton(button,"DB");
      
      button.setText("Green");
      button.setName("GBut");
      addButton(button,"G");
      
      button.setText("Purple");
      button.setName("PBut");
      addButton(button,"P");
      
      
      //creates sliders for on the fly adjustment
      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(minH);
      slider.setName("Hue Minimum");
      addSlider(slider, "Hue Minimum");
      lHMin.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lHMin);
	  // add a slider that
      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(maxH);
      slider.setName("Hue Maximum");
      addSlider(slider, "Hue Maximum");
      lHMax.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lHMax);

      // add a slider with no track
      
      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(minS);
      slider.setName("Saturation Minimum");
      addSlider(slider, "Saturation Minimum");
      lSMin.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lSMin);

      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(maxS);
      slider.setName("Saturation Maximum");
      addSlider(slider, "Saturation Maximum");
      lSMax.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lSMax);
      // add a slider that snaps to ticks

      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(minV);
      slider.setName("Brightness Minimum");
      addSlider(slider, "Brightness Minimum");
      lBMin.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lBMin);

      // add a slider with no track

      slider = new JSlider(0,255);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(50);
      slider.setMinorTickSpacing(5);
      slider.setValue(maxV);
      slider.setName("Brightness Maximum");
      addSlider(slider, "Brightness Maximum");
      lBMax.setText(Integer.toString(slider.getValue()));
      sliderPanel.add(lBMax);
      // add the text field that displays the slider value
//      textField = new JTextField();
      add(sliderPanel, BorderLayout.CENTER);
      
//      add(textField, BorderLayout.SOUTH);
   }

   
   //Updates slider values after a button has been pressed
   public void updateSliders(){
	   for(Component c : sliderPanel.getComponents()){
		   System.out.println(c.getName());
		   try{
			   if(!c.getName().isEmpty()){
		   }
		   if((!c.getName().contains("lab")&&!c.getName().contains("But"))){
	    		  JSlider source = (JSlider)c;
	    		  if(source.getName().equals("Hue Minimum")){
	           	   source.setValue(minH);
	              }
	    		  else if(source.getName().equals("Hue Maximum")){
	            	  source.setValue(maxH);
	              }
	    		  else if(source.getName().equals("Saturation Minimum")){
	            	  source.setValue(minS);
	              }
	    		  else if(source.getName().equals("Saturation Maximum")){
	            	  source.setValue(maxS);
	              }
	    		  else if(source.getName().equals("Brightness Minimum")){
	            	  source.setValue(minV);
	              }
	    		  else if(source.getName().equals("Brightness Maximum")){
	            	  source.setValue(maxV);
	              }
	    	  }
	      }
	   catch(Exception e){
	   }
	   }
   }
   
   //updates color values throughout the program
   public void changeColor(short minH,short maxH,short minS, short maxS, short minV, short maxV){
	   MainUI.minH=minH;
	   MainUI.minS=minS;
	   MainUI.minV=minV;
	   MainUI.maxH=maxH;
	   MainUI.maxS=maxS;
	   MainUI.maxV=maxV;
	   MainUI.minH=minH;
	   MainUI.minS=minS;
	   MainUI.minV=minV;
	   MainUI.maxH=maxH;
	   MainUI.maxS=maxS;
	   MainUI.maxV=maxV;
	   ObjectDetector.minH=minH;
	   ObjectDetector.minS=minS;
	   ObjectDetector.minV=minV;
	   ObjectDetector.maxH=maxH;
	   ObjectDetector.maxS=maxS;
	   ObjectDetector.maxV=maxV;
	   ObjectDetector.minH=minH;
	   ObjectDetector.minS=minS;
	   ObjectDetector.minV=minV;
	   ObjectDetector.maxH=maxH;
	   ObjectDetector.maxV=maxV;
	   updateSliders();
	   
   }
   public void addSlider(JSlider s, String description)
   {
      s.addChangeListener(listener);
      sliderPanel.add(s);
   }
   
   //sets button functionality
   public void addButton(JButton b, String description)
   {
	   JButton b2 = new JButton();
	  if(b.getName().equals("LBBut"))
	  {
		  b2.setName("But");
		  b2.setText("Light Blue");
      b2.addActionListener(new ActionListener(){        
		@Override
		public void actionPerformed(ActionEvent e) {
			changeColor((short)79,(short) 115, (short)50, 
					(short)255, (short)45, (short)255);
		}
      });
	  }
	  else if (b.getName().equals("DBBut")){
		  b2.setName("But");
		  b2.setText("Dark Blue");
		  b2.addActionListener(new ActionListener(){        
				@Override
				public void actionPerformed(ActionEvent e) {
					changeColor((short)77,(short) 116, (short)130, 
							(short)255, (short)0, (short)188);
				}
		      });
	  }
	  else if (b.getName().equals("PBut")){
		  b2.setName("But");
		  b2.setText("Red");
		  b2.addActionListener(new ActionListener(){        
				@Override
				public void actionPerformed(ActionEvent e) {
					changeColor((short)57,(short) 186, (short)140, 
							(short)224, (short)55, (short)184);
				}
		      });
	  }
	  else if (b.getName().equals("GBut")){
		  b2.setName("But");
		  b2.setText("Green");
		  b2.addActionListener(new ActionListener(){        
				@Override
				public void actionPerformed(ActionEvent e) {
					changeColor((short)44,(short) 85, (short)50, 
							(short)255, (short)0, (short)220);
				}
		      });
	  }
      sliderPanel.add(b2);
   }
   public static final int DEFAULT_WIDTH = 375;
   public static final int DEFAULT_HEIGHT = 450;

   private JPanel sliderPanel;
   private JTextField textField;
   private ChangeListener listener;
}