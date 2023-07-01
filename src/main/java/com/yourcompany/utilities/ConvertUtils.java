package com.yourcompany.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertUtils {
	private static Logger logger = LogManager.getLogger();

	/***
	 * Return the o Object if o not null. Otherwise return defaultVal
	 * @param o The object is passed to this method
	 * @param defaultVal The default object that you expect if o is null
	 * @return return a object
	 */
	
	public static Object getObject(Object o, Object defaultVal){
		if(o != null) return o;
		return defaultVal;
	}
	
	/***
	 * Return the Float if the input object not null and parsed successfully. Otherwise defaultVal
	 * @param input The input Object passed to this function
	 * @param defaultVal The default value that you expect if any error occur
	 * @return Return the float object
	 */
	public static Float getFloat(Object input, Float defaultVal){
		if(input == null) return defaultVal;
		Float result;
		try{
			result = Float.parseFloat(input.toString());
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result = defaultVal;
		}
		return result;
	}
	
	/***
	 * Return a string if input object not null. Otherwise return defaultVal
	 * @param input The input Object passed to this function
	 * @param defaultVal The default value that you expect if any error occur
	 * @return return a string
	 */
	
	public static String getString(Object input, String defaultVal){
		if(input == null) return defaultVal;
		return input.toString();
	}
	
	/***
	 * Return a integer if input object not null and parsed successfully. Otherwise return defaultVal
	 * @param input The input Object that passed to this function
	 * @param defaultVal The default value that you expect if any error occur
	 * @return Return integer object
	 */
	public static Integer getInt(Object input, Integer defaultVal){
		
		if(input == null) return defaultVal;
		Integer result = null;
		try{
			result = Integer.parseInt(input.toString());
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result = defaultVal;
		}
		return result;
	}
	
	/***
	 * Return a double object if input object not null and parsed successfully. Otherwise defaultVal
	 * @param input The input object that passed to this function
	 * @param defaultVal The default value that you expect if any error occur
	 * @return Return a double object
	 */
	public static Double getDouble(Object input, Double defaultVal){
		
		if(input == null) return defaultVal;
		Double result;
		try{
			result = Double.parseDouble(input.toString()) ;
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result = defaultVal;
		}
		return result;
	}
	
	/***
	 * Return a boolean if input object is instance of Boolean
	 *<br>Return true if input object in ("true","yes","1")
	 *<br>Return false if input object in ("false","no","0")
	 *<br>Otherwise return defaultVal
	 * @param input The input object that you pass to this function
	 * @param defaultVal The default value you expect if any error occur
	 * @return Return a boolean
	 */
	public static Boolean getBoolean(Object input, Boolean defaultVal){
		if(input == null) return defaultVal;
		if(input instanceof Boolean) return (Boolean)input;
		
		Boolean result;
		try{
			String valCase = input.toString().toLowerCase();
			switch(valCase){
			case "true" : return true;
			case "fasle" : return false;
			case "yes": return true;
			case "no" : return false;
			case "1" : return true;
			case "0" : return false;
			default:
				return defaultVal;
			}
		}catch(Exception e){
			result = defaultVal;
			logger.info(e.getMessage(), e);
		}
		return result;
	}
	
	/***
	 * The method returns a long value if input object not null and parse successfully. Otherwise defaultVal
	 * @param input The input object is passed
	 * @param defaultVal The default value that you expect if any error occur
	 * @return Return a long object
	 */
	public static Long getLong(Object input, Long defaultVal){
		if(input == null) return (Long)defaultVal;
		Long result;
		try{
			result = Long.parseLong(input.toString().trim());
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result = defaultVal;
		}
		return result;
	}
	
	/***
	 * This method return a date object provided by inputDate and formatPattern
	 * @param inputDate the String of date
	 * @param formatPattern the format pattern that you would like to parse date
	 * @param defaultVal The default date that you expect if any error occur
	 * @return Return a date object
	 */
	public static Date getDateTime(String inputDate,String formatPattern, Date defaultVal){
		
		if(inputDate == null  || "".equals(inputDate)) return defaultVal;
		
		Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
			date = sdf.parse(inputDate);
		}catch(Exception e){
			date = defaultVal;
			logger.info(e.getMessage(),e);
		}
		return date;
	}
	/***
	 * This method return a formatted String provided by date object and format pattern
	 * @param date The date will be format to string
	 * @param formatPattern The format pattern that you would like to format the date
	 * @param defaultVal The default value will be return if any error occur
	 * @return Return string object
	 */
	public static String getDateAsTring(Date date, String formatPattern,String defaultVal){
		
		String result;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
			result = sdf.format(date);
		}catch(Exception e){
			result = defaultVal;
			logger.info(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * Format a given number to Dollar. Example 2 -> $2.00
	 * @param amount Number
	 * @return
	 */
    public static String formatDollarAmount(Number amount){
        if(amount.toString().isEmpty() || amount == null ){amount = 0;};
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        logger.info("        Number: " + amount + " is converted to " + formatter.format(amount).toString());
        return formatter.format(amount).toString();
    }
	
	public static void decompressGzipFile(String gzipFile, String location) {
		try {
            FileInputStream fis = new FileInputStream(location+gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            String outFile = gzipFile.substring(0, gzipFile.length()-3);
            FileOutputStream fos = new FileOutputStream(location+outFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static Date convertStringToDate(String inputDate,String formatPattern){		
		Date date = null;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
			date = sdf.parse(inputDate);
		}catch(Exception e){			
			logger.info(e.getMessage(),e);
		}
		
		return date;
	}

	public static String convertNull(String data){
		return ((data == null || (data != null && data.trim().length() == 0)) ? "" : data);
	}
	
	public static String convertNumeric(String val){
		return  val.replaceAll("[^\\d^*]", "").replace("*", "%");
	}
	
	public static String convertNull(Object data) {
		return data == null ? "" : data.toString();
	}
	
	public static int convertStringToInt(String val){
		int result = 0;
		try {
			DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
			result = decimalFormat.parse(val).intValue();
		} catch (Exception e) {
			result = 0;
			logger.info(e.getMessage(), e);
		}
		return result;	
	}
	
	public static List<String> convertFromObjectListToStringListWithSort(List<?> list) {
		List<String> listConvert = new ArrayList<>();
		for (Object obj : list) {
			listConvert.add(obj.toString());
		}
		Collections.sort(listConvert);
		return listConvert;
	}

	public static java.sql.Date convertUtilDateToSqlDate(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sqlDate = dateFormat.format(date);
		return java.sql.Date.valueOf(sqlDate);
	}
	
	/***
	 * The test method for all functions in this class
	 * @param args 
	 */
	public static void main(String[] args){
		formatDollarAmount(2.555d);
	}
	
	public static int convertBoolToInt(Boolean value) {
		return value == true ? 1 : 0;
	}
	
	public static BigDecimal roundBigDecimal(BigDecimal input, int scale) {
		return input.setScale(scale, RoundingMode.HALF_EVEN);
	}
	
	public static String convertObjectAsUtilDateToString(Object value) {
		try {
			if (value instanceof java.util.Date) {
				return new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)value);
			}
		} catch(Exception e) {}
		
		return null;
	}
}
