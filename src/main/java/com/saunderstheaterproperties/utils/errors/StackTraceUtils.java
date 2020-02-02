package com.saunderstheaterproperties.utils.errors;

public class StackTraceUtils {

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
