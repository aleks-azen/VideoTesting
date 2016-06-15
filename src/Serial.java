import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;


class Serial {
	
	static SerialPort chosenPort;
	public static int lastDirection = 0;
	public static JFrame window = new JFrame();
	public static final JComboBox<String> portList = new JComboBox<String>();
	public static final JButton connectButton = new JButton("Connect");
	public static SerialPort[] portNames = SerialPort.getCommPorts();
	public static int direction =0;
	public static boolean runRestOfProgram = false;
	public static PrintWriter output;
//	static int msb = 8,lsb=8;
	public static void run() {
		JFrame window = new JFrame();
	    window.pack();
	    window.setVisible(true);
	    
	    window.setSize(700, 700);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		// create and configure the window
		JFrame connectwindow = new JFrame();
		connectwindow.setTitle("ISeeU");
		connectwindow.setSize(400, 75);
		connectwindow.setLayout(new BorderLayout());
		connectwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a drop-down box and connect button, then place them at the top of the window
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.add(topPanel, BorderLayout.NORTH);
		window.setVisible(true);
		// populate the drop-down box
		
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		// configure the connect button and use another thread to send data
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (connectButton.getText().equals("Connect")) {
						// attempt to connect to the serial port
						runRestOfProgram = true;
						chosenPort = SerialPort.getCommPort(portList
								.getSelectedItem().toString());
						chosenPort.setComPortTimeouts(
								SerialPort.TIMEOUT_SCANNER, 0, 0);
						if (chosenPort.openPort()) {
							
							connectButton.setText("Disconnect");
							portList.setEnabled(false);
							PrintWriter output = new PrintWriter(
									chosenPort.getOutputStream());
							// create a new thread for sending data to the
							// arduino
							Thread thread = new Thread(){
								@Override public void run() {
									// wait after connecting, so the bootloader can finish
									try {Thread.sleep(100); } catch(Exception e) {}

									// enter an infinite loop that sends text to the arduino
									PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
									while(true) {
//										if(lastDirection!=direction){
								        output.print(direction);
								        output.flush();
								        System.out.println("SERIALING"+direction);
//								        }
//								        lastDirection =direction;
								        try {
								            Thread.sleep(60);                 //1000 milliseconds is one second.
								        } catch(InterruptedException ex) {
								            Thread.currentThread().interrupt();
								        }
//									}
									}
								}
							};
							thread.start();
						}
					} else {
						// disconnect from the serial port
						chosenPort.closePort();
						portList.setEnabled(true);
						connectButton.setText("Connect");
					}
				} catch (Exception e) {

				}
			}
		});
		
		// show the window

	}

}