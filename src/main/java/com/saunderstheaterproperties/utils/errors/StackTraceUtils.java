package com.saunderstheaterproperties.utils.errors;

public class StackTraceUtils {
	
	public static StackTraceElement[] buildBetterStackTraceElementArray(Throwable throwable) {
		
		StackTraceElement[] exceptionStackTrace = throwable.getStackTrace();
		StackTraceElement[] causeStackTrace = null;
		
		int exceptionLength = exceptionStackTrace.length;
		int causeLength = 0;
		
		if(throwable.getCause()!=null) {
			
			causeStackTrace = buildBetterStackTraceElementArray(throwable.getCause());
			causeLength = causeStackTrace.length;
					
		}
        
		StackTraceElement[] result = new StackTraceElement[causeLength + exceptionLength];
        
        System.arraycopy(exceptionStackTrace, 0, result, 0, exceptionLength);
        
        if(causeLength!=0 & causeStackTrace!=null)
        	System.arraycopy(causeStackTrace, 0, result, exceptionLength, causeLength);
        
        return result;
		
	}

	public static String asString(StackTraceElement[] stes) {
		
		String result = "";
		
		for(StackTraceElement ste:stes) {
			result += ste.toString() + "\n";
		}
		
		return result;
		
	}

	public static String asHTML(StackTraceElement[] stes) {
		String result = "<html><h3>Stacktrace:</h3>";
		
		for(StackTraceElement ste:stes) {
			result += "<p>" + ste.toString() + "</p>";
		}
		
		result += "</html>";
		
		return result;
	}
	
}
