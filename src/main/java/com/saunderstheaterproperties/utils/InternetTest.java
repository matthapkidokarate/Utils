package com.saunderstheaterproperties.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.saunderstheaterproperties.utils.errors.GenericErrorDisplay;
import com.saunderstheaterproperties.utils.errors.GenericErrorDisplay.GenericErrorSettings;

public class InternetTest {
	
	
	public static boolean performInternetTest() {
		
		boolean isConnected = isConnectedToInternet();
		
		if(!isConnected) {
			try {
				GenericErrorDisplay.getGenericErrorDisplay("No Internet Connection", "<html><body><h1>There is no internet connection available.</h1><br />"
						+ "<p>While internet is required for this application, clicking ignore will attempt to continue running<br />"
						+ "the program; however, the program will still not function correctly without internet.</p></body></html>", 
						GenericErrorSettings.FATAL_RECOVER).LATCH.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		return isConnected;
		
	}

	
	public static boolean isConnectedToInternet() {
		try {
	         URL url = new URL("http://www.google.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	         return true;
	      } catch (IOException e) {
	    	  return false;
	    } 
	}
}
