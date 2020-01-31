package com.saunderstheaterproperties.utils;

public class Time {

	public static String timeToStore(int minutes, int seconds) {
		String m = Integer.toString(minutes);
		String s = Integer.toString(seconds);
		
		while(m.length()<2) {
			m = "0" + m;
		}
		while(s.length()<2) {
			s = "0" + s;
		}
		
		String result = m+s;
		return result;
		
	}
	
	public static int[] parseTimeFromStorage(String toParse){
		
		try{
			String minutes = toParse.substring(0,2);
		
			String seconds = toParse.substring(2);
			
			int[] result = {
				Integer.parseInt(minutes),
				Integer.parseInt(seconds)
			};
			
			if(result[0] == 0 && result[1] < 5) {
				int[] result1 = {0,50};
				return result1;
			}
			
			return result;
		}catch (Exception|Error e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		int[] result = {0,50};
		return result;
		
	}

}
