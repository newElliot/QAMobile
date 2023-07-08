package com.yourcompany.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandPrompt {
	public static Logger logger = LogManager.getLogger(CommandPrompt.class);
	private static Process p;
	private static ProcessBuilder builder;

	/**
	 * This method run command on windows and mac
	 * 
	 * @param command to run
	 */
	public static String runCommand(String command) throws InterruptedException, IOException {
		String os = System.getProperty("os.name");

		// build cmd proccess according to os
		if (os.contains("Windows")) {
			builder = new ProcessBuilder("cmd.exe", "/c", command);
			builder.redirectErrorStream(true);
			Thread.sleep(1000);
			p = builder.start();
		} else { // If Mac
			p = Runtime.getRuntime().exec(command);
		}

		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		String allLine = "";
		while ((line = r.readLine()) != null) {
			// System.out.println(i+". "+line);
			allLine = allLine + "" + line + "\n";
			if (line.contains("Console LogLevel: debug"))
				break;
		}
		return allLine;
	}

	public static void main(String[] args) throws Exception {
		CommandPrompt.runCommand("dir");
	}
}
