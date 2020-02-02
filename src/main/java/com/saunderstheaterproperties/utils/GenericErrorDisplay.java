package com.saunderstheaterproperties.utils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GenericErrorDisplay {

	public enum GenericErrorSettings{
		FATAL, FATAL_RECOVER, RECOVER, ERROR_NO_RESPONSE
	}
	
	private static volatile GenericErrorDisplay display;
	
	public static GenericErrorDisplay getGenericErrorDisplay() throws NullPointerException{
		
		if(display != null)
			return display;
		
		throw new NullPointerException("No Generic Error Display is available. Please create a new one.");
		
	}
	
	public static GenericErrorDisplay getGenericErrorDisplay(String shortText, String longText, GenericErrorSettings type) {
		
		if(display != null)
			return display;
		return new GenericErrorDisplay(shortText, longText, type);
		
	}
		
	JFrame mainApplicationFrame;
	
	JPanel 	content, 
			errorMessage;

	JLabel errorMessageLabel;
	
	String shortMessage,longMessage;

	JButton errorAckClose, errorIgnoreClose;
	
	private GenericErrorDisplay(String shortText, String longText, GenericErrorSettings type) {
		
		
		// open the JFrame object
		mainApplicationFrame = new JFrame(shortText);
		mainApplicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainApplicationFrame.setPreferredSize(new Dimension(720,1280));
		mainApplicationFrame.getContentPane().setLayout(new GridLayout());
		
		// play the notification sound
		AudioHandler.playAudioFile("/sound/bell.wav");
		
		// sets the IconImage
		setIconImage("/images/x.png");
		
		// create all of the jpanels and add them to the JFrame
		content = new JPanel();
		errorMessage = new JPanel();
		content.add(errorMessage);
		
		// create the message label and add
		errorMessageLabel = new JLabel(longText);
		errorMessage.add(errorMessageLabel);
		
		// create the buttons
		switch(type) {
		case FATAL:
			errorAckClose = new JButton(new ErrorClose("Close the application","This will close the application because the error is fatal"));
			content.add(errorAckClose);
			break;
		case FATAL_RECOVER:
			errorAckClose = new JButton(new ErrorClose("Close application","This will close the application because the error is fatal"));
			errorIgnoreClose = new JButton(new ErrorContinue("Ignore Error", "Ignore the error. This may result in unexpected behavior as the error is fatal."));
			break;
		case RECOVER:
			errorIgnoreClose = new JButton(new ErrorContinue("Ignore Error", "Ignore the error. There may be wierd things that happen"));
		default:
			// cause a crash
			System.exit(-1);
		}
		
		
		mainApplicationFrame.pack();
		
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
		System.gc();
		
	}

}

class ErrorContinue extends AbstractAction{

	static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8102634505025223067L;

	public ErrorContinue(String text, String description) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		LOGGER.severe("Closing error window. Log caused by: " + NAME);
		
		
	}
	
}

class ErrorClose extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4408258707142790345L;
	
	static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public ErrorClose(String text, String description) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
	}

	
	public void actionPerformed(ActionEvent e) {
		LOGGER.severe("Closing program because of " + NAME);
		System.exit(1);
		
	}
	
}
