package com.yourcompany.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PropertyReader {
	public static Logger logger = LogManager.getLogger();
	
	public Configuration loadConfigurationByPropertyFile(String fileName) throws Exception {
		Configuration configuration = new Configuration();
		Properties prop = new Properties();
		File file = null;
		BufferedReader bf = null;

		try {
			file = new File(fileName);
			bf = new BufferedReader(new FileReader(file));
			prop.load(bf);
			configuration.setApp(prop.getProperty("app.apk"));
			configuration.setDevice(prop.getProperty("app.device.emulator"));
		} catch (Exception e) {
			throw new Exception("Fail to load properties file, filename=" + fileName);
		} finally {
			if (bf != null) {
				bf.close();
			}
		}

		return configuration;
	}
}
