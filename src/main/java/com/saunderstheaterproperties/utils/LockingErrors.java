package com.saunderstheaterproperties.utils;

import java.util.concurrent.CountDownLatch;

import com.saunderstheaterproperties.utils.GenericErrorDisplay.GenericErrorSettings;

public class LockingErrors{
	
	public static volatile GenericErrorSettings errorAcknowledged = GenericErrorSettings.ERROR_NO_RESPONSE;
	
	public static CountDownLatch LATCH = new CountDownLatch(1);

	/**
	 * Shows a new error display
	 * @param shortErrorText
	 * @param error
	 * @return
	 */
	public static boolean showNewError(String shortErrorText, String error, GenericErrorSettings errorType) {
		
		LATCH = new CountDownLatch(1);
		
		
		//GenericErrorDisplay errorDisplay = new GenericErrorDisplay(shortErrorText,error,errorType);
		
		try {
			LATCH.await();
		} catch (InterruptedException e) {
		}
		
		boolean result = (errorAcknowledged==GenericErrorSettings.RECOVER) ? true:false;
		errorAcknowledged = GenericErrorSettings.ERROR_NO_RESPONSE;
		return result;
		
	}

}
