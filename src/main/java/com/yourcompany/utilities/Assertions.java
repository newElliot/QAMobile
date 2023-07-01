package com.yourcompany.utilities;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;


public class Assertions {
	protected static final Logger logger = LogManager.getLogger();
	private static final Float FLOAT_DEFAULT_VALUE = 0F;
	private static final Double DOUBLE_DEFAULT_VALUE = 0D;
	
	/**
	 * Verify object with object which both has the same data type
	 * @param actual Object
	 * @param expected Object
	 * @param message string
	 */
	protected void verifyEquals(Object actual, Object expected, String message) {
		String act = ConvertUtils.convertNull(actual);
		String exp = ConvertUtils.convertNull(expected);
		
		logger.info("        Actual  : " + act + "[Length: " + act.length() + "]");
		logger.info("        Expected: " + exp + "[Length: " + exp.length() + "]");
		Assert.assertEquals(act, exp, message);
	}
	
	/**
	 * Verify string with string
	 * @param actual string
	 * @param expected string
	 * @param message string
	 */
	protected void verifyStringWithString(String actual, String expected, String message) {
		actual = ConvertUtils.convertNull(actual).trim();
		logger.info("        Actual  : " + actual + "[Length: " + actual.length() + "]");
		
		expected = ConvertUtils.convertNull(expected).trim();
		logger.info("        Expected: " + expected + "[Length: " + expected.length() + "]");
		
		Assert.assertEquals(actual, expected, message);
	}
	
	/**
	 * Verify string with int or in reverse order
	 * @param actual String or int
	 * @param expected String or int
	 * @param message String
	 */
	protected void verifyStringWithIntOrReverse(Object actual, Object expected, String message) {
		String act = ConvertUtils.convertNull(actual);
		logger.info("        Actual  : " + act + "[Length: " + act.length() + "]");
		
		String exp = ConvertUtils.convertNull(expected);
		logger.info("        Expected: " + exp + "[Length: " + exp.length() + "]");
		
		Assert.assertEquals(act, exp, message);
	}
	
	/**
	 * Verify date with date, no matter type of the date.
	 * @param actual date
	 * @param expected date
	 * @param message string
	 */
	protected void verifyDateWithDate(java.util.Date actual, java.util.Date expected, String message) {
		String act = StringUtils.EMPTY;
		String exp = StringUtils.EMPTY;
		
		act = ConvertUtils.convertObjectAsUtilDateToString(actual);
		logger.info("        Actual  : " + act);
		
		exp = ConvertUtils.convertObjectAsUtilDateToString(expected);
		logger.info("        Expected: " + exp);
		
		Assert.assertEquals(act, exp, message);
	}
	
	/**
	 * Verify number with number, round 2 digits and no matter type of the date. 
	 * If actual or expected value is null, take the default value is 0.0
	 * @param actual number
	 * @param expected number
	 * @param message string
	 */
	protected void verifyNumWithNum(Number actual, Number expected, String message) {
		Double act = Math.floor(ConvertUtils.getFloat(actual, FLOAT_DEFAULT_VALUE) * 100) / 100;
		logger.info("        Actual  : " + act);
		
		Double exp = Math.floor(ConvertUtils.getFloat(expected, FLOAT_DEFAULT_VALUE) * 100) / 100;
		logger.info("        Expected: " + exp);
		
		Assert.assertEquals(act, exp, message);
	}
	
	/**
	 * Verify number with number, round up 2 digits and no matter type of the data. 
	 * If actual or expected value is null, take the default value is 0.00
	 * @param actual number
	 * @param expected number
	 * @param message string
	 */
	protected void verifyNumWithNumRound(Number actual, Number expected, String message) {
		BigDecimal act = ConvertUtils.roundBigDecimal(BigDecimal.valueOf(ConvertUtils.getDouble(actual, DOUBLE_DEFAULT_VALUE)), 2);
		logger.info("        Actual  : " + act);
		
		BigDecimal exp = ConvertUtils.roundBigDecimal(BigDecimal.valueOf(ConvertUtils.getDouble(expected, DOUBLE_DEFAULT_VALUE)), 2);
		logger.info("        Expected: " + exp);
		
		Assert.assertEquals(act, exp, message);
	}

	/**
	 * Verify the given value. Failed if the value is false
	 * @param value boolean
	 * @param message string
	 */
	protected void verifyTrue(boolean value, String message) {
		Assert.assertTrue(value, message);
	}
	
	/**
	 * Verify the given value. Failed if the value is true
	 * @param value boolean
	 * @param message string
	 */
	protected void verifyFalse(boolean value, String message) {
		Assert.assertFalse(value, message);
	}
	
	/**
	 * Verify the given object. Failed if object is not null
	 * @param value
	 * @param message string
	 */
	protected void verifyNull(Object value, String message) {
		Assert.assertNull(value, message);
	}
	
	/**
	 * Verify the given object. Failed if object is null
	 * @param value object
	 * @param message string
	 */
	protected void verifyNotNull(Object value, String message) {
		Assert.assertNotNull(value, message);
	}
}
