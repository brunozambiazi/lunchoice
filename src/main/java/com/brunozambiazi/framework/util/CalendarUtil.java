
package com.brunozambiazi.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class CalendarUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarUtil.class);
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String HOUR_FORMAT = "HH:mm";
	public static final String WEEK_FORMAT = "ww/yyyy";
	
	
	private CalendarUtil() {
	}
	

	/**
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Calendar fromString(String dateString, String pattern) {
		if (dateString == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			calendar.setTime(sdf.parse(dateString));
			return calendar;
			
		} catch (ParseException e) {
			LOGGER.warn("String to calendar conversion problem", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param calendar
	 * @param pattern
	 * @return
	 */
	public static String toString(Calendar calendar, String pattern) {
		if (calendar == null) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}
	
}
