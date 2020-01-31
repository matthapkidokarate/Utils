package com.saunderstheaterproperties.utils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

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
			errorAckClose = new JButton(new ErrorClose("Close the application","This will close the application because the error is fatal"));
			errorIgnoreClose = new JButton(new ErrorContinue("Ignore Error", "Ignore the error. This may result in unexpected behavior as the error is fatal."));
		default:
			System.exit(-1);
		}
		
		
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

}

class ErrorContinue extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8102634505025223067L;

	public ErrorContinue(String text, String description) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
}

class ErrorClose extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4408258707142790345L;

	public ErrorClose(String text, String description) {
		super(text);
		putValue(SHORT_DESCRIPTION, description);
	}

	
	public void actionPerformed(ActionEvent e) {
		
		System.exit(1);
		
	}
	
}
