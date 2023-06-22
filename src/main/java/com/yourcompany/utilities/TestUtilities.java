package com.yourcompany.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestUtilities {
	public static Logger logger = LogManager.getLogger(TestUtilities.class.getName());
	
	public static void setDelayTime(int seconds) throws Exception{
		logger.info("    Setting delay time " + seconds + "s");
		Thread.sleep(seconds * 1000);
	}
}
