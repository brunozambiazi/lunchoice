
package com.brunozambiazi.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class CalendarUtil {

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String HOUR_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String WEEK_FORMAT = "ww/yyyy";
	
	
	private CalendarUtil() {
	}
	

	public static boolean equalsDate(Calendar cal1, Calendar cal2) {
		return toString(cal1, DATE_FORMAT).equals(toString(cal2, DATE_FORMAT));
	}
	
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
			return null;
		}
	}
	
	public static String toString(Calendar calendar, String pattern) {
		if (calendar == null) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}
	
}
