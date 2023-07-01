package com.yourcompany.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeStampUtils {
private static Logger logger = LogManager.getLogger();
	
	public static String getTimeStamp() throws Exception {
		// format timestamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");

		// get current date and time
		Date date = new Date();
		logger.info(sdf.format(date));

		return sdf.format(date);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	// Return current date as string in MM/dd/yyyy format.
	public static String getCurrentDate() throws Exception {

		String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		logger.info("        Returned Current Date: " + currentDate);
		return currentDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getCurrentDate(String format) throws Exception {

		String currentDate = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
		logger.info("        Returned Current Date: " + currentDate);
		return currentDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getPreviousDate(String format, int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		String previousDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned Previous Date: " + previousDate);
		return previousDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getFutureDate(String format, int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		String futureDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned Future Date: " + futureDate);
		return futureDate;
	}

	public static java.sql.Date getCurrentDateAsSqlDate() {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		logger.info("        Returned Current Date: " + currentDate);
		return currentDate;
	}

	public static java.sql.Date getFutureDateAsSqlDate(int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		java.sql.Date futureDate = new java.sql.Date(cal.getTimeInMillis());
		logger.info("        Returned Future Date: " + futureDate);
		return futureDate;
	}

	public static java.sql.Date getPreviousDateAsSqlDate(int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		java.sql.Date previousDate = new java.sql.Date(cal.getTimeInMillis());
		logger.info("        Returned Previous Date: " + previousDate);
		return previousDate;
	}

	public static java.sql.Date getFirstDateOfMonthAsSqlDate(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date firstDate = new java.sql.Date(cal.getTimeInMillis());
		logger.info("        Returned the First Date of Month: " + firstDate);

		return firstDate;
	}

	public static java.sql.Date getLastDateOfMonthAsSqlDate(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date lastDate = new java.sql.Date(cal.getTimeInMillis());
		logger.info("        Returned the Last Date of Month: " + lastDate);
		return lastDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	// Parses date of specified format and converts date to new specified format
	public static String getConvertedDate(String oldFormat, String newFormat, String oldDate) throws Exception {
		Date date = new SimpleDateFormat(oldFormat).parse(oldDate);
		String newDate = new SimpleDateFormat(newFormat).format(date);
		newDate = newDate.toUpperCase();
		logger.info("        Returned New Format Date: " + newDate);
		return newDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getFirstDateOfMonth(String format, Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned the First Date of Month: " + firstDate);
		return firstDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getLastDateOfMonth(String format, Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String firstDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned the Last Date of Month: " + firstDate);
		return firstDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getPreviousMonth(String format, Date date, int numOfMonth) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -numOfMonth);
		String previousDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned Previous Month Date: " + previousDate);
		return previousDate;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getNextDay(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date myDate = dateFormat.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(myDate);
		calendar.add(Calendar.DAY_OF_MONTH, +1);

		// Use the date formatter to produce a formatted date string
		Date previousDate = calendar.getTime();
		String result = dateFormat.format(previousDate);
		return result;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getNextDay(String format, Date date) throws ParseException {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);

		// Use the date formatter to produce a formatted date string
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date previousDate = calendar.getTime();
		String result = dateFormat.format(previousDate);
		return result;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	public static String getDayOfPrevMonth(String format, Date date, int numOfMonth, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -numOfMonth);
		cal.set(Calendar.DAY_OF_MONTH, day);
		String prevDate = new SimpleDateFormat(format).format(cal.getTime());
		logger.info("        Returned the Date: " + prevDate);
		return prevDate;
	}

	public static Date getRandomDate(int startYear, int endYear) {
		long start = Timestamp.valueOf((startYear + 1) + "-1-1 0:0:0").getTime();
		long end = Timestamp.valueOf(endYear + "-1-1 0:0:0").getTime();
		long ms = (long) ((end - start) * Math.random() + start); // The qualified number of 13-bit milliseconds is
																	// obtained.
		Date randomdate = new Date(ms);
		return randomdate;
	}
}
