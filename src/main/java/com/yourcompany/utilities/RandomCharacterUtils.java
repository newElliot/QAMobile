package com.yourcompany.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomCharacterUtils {
private static Logger logger = LogManager.getLogger();
	
	/**
	 * Get random alphabetic characters
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String getRandomAlphaString(int length) throws Exception {		
		String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";		
		charset = RandomStringUtils.random(length, charset.toCharArray());
		logger.info("        Returned Random Alpha String: " + charset);
		return charset;		
	}
	
	/**
	 * Get random numeric characters
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String getRandomNumericString(int length) throws Exception {		
		String charset = "1234567890";		
		charset = RandomStringUtils.random(length, charset.toCharArray());
		logger.info("        Returned Random Numeric String: " + charset);
		return charset;		
	}
	
	/**
	 * Get random alphanumeric characters
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String getRandomAlphaNumericString(int length) throws Exception {		
		String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";		
		charset = RandomStringUtils.random(length, charset.toCharArray());
		logger.info("        Returned Random AlphaNumeric String: " + charset);
		return charset;		
	}

	/**
	 * Get a random Non-Zero numeric characters
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String getNonZeroRandomNumericString(int length) throws Exception {		
		String charset = "123456789";		
		charset = RandomStringUtils.random(length, charset.toCharArray());
		logger.info("        Returned Non-Zero Random Numeric String: " + charset);
		return charset;		
	}
	
	/**
	 * Get random alphabetic and special characters
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String getRandomAlphaStringwithSpecialCharacters(int length) throws Exception {
		String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ>&\"'";
		charset = RandomStringUtils.random(length, charset.toCharArray());
		logger.info("        Returned Random AlphaNumeric String: " + charset);
		return charset;
	}

	/**
	 * Get random password
	 * @param length
	 * @return Random String
	 * @throws Exception
	 */
	public static String generateRandomPassword(int length) {
		String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		charset = RandomStringUtils.random(length, charset.toCharArray());
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%&])(?=\\S+$).{8,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(charset);

		if (matcher.matches()) {
			logger.info("        Returned Random password: " + charset);
			return charset;
		} else {
			return generateRandomPassword(length);
		}
	}
}
