package com.saunderstheaterproperties.utils.errors;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.saunderstheaterproperties.utils.AudioHandler;


public class GenericErrorDisplay {

	/**
	 * The types of errors that are available to the GenericErrorDisplay class
	 * @author matth
	 *
	 */
	public enum GenericErrorSettings{
		FATAL, FATAL_RECOVER, RECOVER, ERROR_NO_RESPONSE
	}
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	private static volatile GenericErrorDisplay display;
	
	public CountDownLatch LATCH;
	
	public static boolean isGenericErrorDisplaySet() {
		return (display != null);
	}
	
	public static GenericErrorDisplay getGenericErrorDisplay() throws NullPointerException{
		
		if(display != null)
			return display;
		
		throw new NullPointerException("No Generic Error Display is available. Please create a new one.");
		
	}
	
	GenericErrorDisplay countDown() throws NullPointerException{
		getGenericErrorDisplay().LATCH.countDown();
		return getGenericErrorDisplay();
	}
	
	/**
	 * Creates a new GenericErrorDispla with the descriptions. use .LATCH.await() to wait for user to acknowledge
	 * @param shortText the title of the window
	 * @param longText the content of the window
	 * @param type the type of error, {@link GenericErrorDisplay.GenericErrorSettings}
	 * @return
	 */
	public static GenericErrorDisplay getGenericErrorDisplay(String shortText, String longText, GenericErrorSettings type) {
		
		if(display != null)
			return display;
		display = new GenericErrorDisplay(shortText, longText, type, new CountDownLatch(1));
		return display;
		
	}
	
	public static GenericErrorDisplay getGenericErrorDisplay(String shortText, String longText, GenericErrorSettings type, CountDownLatch LATCH) {
		
		if(display != null)
			return display;
		display = new GenericErrorDisplay(shortText, longText, type,LATCH);
		return display;
		
	}
		
	JFrame mainApplicationFrame;
	
	JPanel 	content, 
			errorMessage,
			buttonPanel;

	JLabel errorMessageLabel;
	
	String shortMessage,longMessage;

	JButton errorAckClose, errorIgnoreClose;
	
	private GenericErrorDisplay(String shortText, String longText, GenericErrorSettings type, CountDownLatch LATCH) {
		
		this.LATCH = LATCH;
		
		// open the JFrame object
		mainApplicationFrame = new JFrame(shortText);
		mainApplicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainApplicationFrame.setPreferredSize(new Dimension(720,720));
		mainApplicationFrame.getContentPane().setLayout(new GridLayout());		
		
		// play the notification sound
		AudioHandler.playAudioFile("/sound/bell.wav");
		
		// sets the IconImage
		setIconImage("/images/x.png");
		
		// create all of the jpanels and add them to the JFrame
		content = new JPanel();
		errorMessage = new JPanel();
		buttonPanel = new JPanel();
		content.add(errorMessage);
		content.add(buttonPanel);
		mainApplicationFrame.add(content);
		//content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		
		// create the message label and add
		errorMessageLabel = new JLabel(longText);
		errorMessage.add(errorMessageLabel);
		
		// create the buttons
		switch(type) {
		case FATAL:
			errorAckClose = new JButton(new ErrorClose("Close the application","This will close the application because the error is fatal", shortText));
			errorAckClose.setFont(font);
			buttonPanel.add(errorAckClose);
			break;
		case FATAL_RECOVER:
			errorAckClose = new JButton(new ErrorClose("Close application","This will close the application because the error is fatal", shortText));
			errorIgnoreClose = new JButton(new ErrorContinue("Ignore Error", "Ignore the error. This may result in unexpected behavior as the error is fatal.", shortText));
			errorAckClose.setFont(font);
			errorIgnoreClose.setFont(font);
			buttonPanel.add(errorAckClose);
			buttonPanel.add(errorIgnoreClose);
			break;
		case RECOVER:
			errorIgnoreClose = new JButton(new ErrorContinue("Ignore Error", "Ignore the error. There may be wierd things that happen", shortText));
			errorIgnoreClose.setFont(font);
			buttonPanel.add(errorIgnoreClose);
			break;
		case ERROR_NO_RESPONSE:
			System.exit(-1);
			break;
		default:
			// cause a crash
			System.exit(-1);
		}
		

		content.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		content.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		errorMessage.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		errorMessage.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		
		
		mainApplicationFrame.pack();
		mainApplicationFrame.validate();
		mainApplicationFrame.repaint();
		
		mainApplicationFrame.setAlwaysOnTop(true);
		mainApplicationFrame.setVisible(true);
		
	}
	
	/**
	 * sets the image icon
	 * @param path
	 */
	private void setIconImage(String path) {
		try {
			java.net.URL url = getClass().getResource(path);
			ImageIcon icon = new ImageIcon(url);
			mainApplicationFrame.setIconImage(icon.getImage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		
		mainApplicationFrame.setVisible(false);
		mainApplicationFrame.dispose();
		display = null;
		System.gc();
		
	}

}

class ErrorContinue extends AbstractAction{

	static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8102634505025223067L;

	public ErrorContinue(String text, String description,String purpose) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
		putValue("PURPOSE", purpose);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		LOGGER.severe("Closing error window. Log caused by: " + getValue("PURPOSE"));
		try {
			GenericErrorDisplay.getGenericErrorDisplay().countDown().finalize();
		}catch(Exception e1) {
			LOGGER.log(Level.SEVERE, "Error", e1);
			System.exit(-1);
		}
		
	}
	
}

class ErrorClose extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4408258707142790345L;
	
	static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public ErrorClose(String text, String description, String purpose) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
		putValue("PURPOSE", purpose);
	}

	
	public void actionPerformed(ActionEvent e) {
		LOGGER.severe("Closing program because of " + getValue("PURPOSE"));
		System.exit(1);
		
	}
	
}
