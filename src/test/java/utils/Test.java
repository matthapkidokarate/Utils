package utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.saunderstheaterproperties.utils.GenericErrorDisplay;
import com.saunderstheaterproperties.utils.logging.LoggerSetup;

public class Test {
	
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		
		try {
			LoggerSetup.setup(System.getProperty("user.home")+"/Documents/Logger.html", System.getProperty("user.home")+"/Documents/Logger.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        // set the LogLevel to Severe, only severe Messages will be written
        LOGGER.setLevel(Level.SEVERE);
        LOGGER.severe("Info Log");
        LOGGER.warning("Info Log");
        LOGGER.info("Info Log");
        LOGGER.finest("Really not important");
        
        GenericErrorDisplay.getGenericErrorDisplay("Error", "I am a pissed off error", GenericErrorDisplay.GenericErrorSettings.RECOVER);

        // set the LogLevel to Info, severe, warning and info will be written
        // finest is still not written
        LOGGER.setLevel(Level.INFO);
        LOGGER.severe("Info Log");
        LOGGER.warning("Info Log");
        LOGGER.info("Info Log");
        LOGGER.finest("Really not important");

	}

}
