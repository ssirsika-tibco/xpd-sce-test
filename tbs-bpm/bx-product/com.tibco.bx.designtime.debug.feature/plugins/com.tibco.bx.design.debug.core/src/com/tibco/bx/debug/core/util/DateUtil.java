package com.tibco.bx.debug.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.bx.debug.core.DebugCoreActivator;

public class DateUtil {

	public static Date parse(String iDate) {
		Date date = null;
		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(iDate);
			if (calendar != null) {
				date = calendar.toGregorianCalendar().getTime();
				return date;
			}
		} catch (DatatypeConfigurationException e) {
			DebugCoreActivator.log(e);
		}
		return date;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar() {
		XMLGregorianCalendar calendar = null;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		} catch (DatatypeConfigurationException e) {
			DebugCoreActivator.log(e);
		}
		return calendar;
	}

	public static Duration createDuration() {
		Duration duration = null;
		try {
			duration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return duration;
	}

	public static Duration createDuration(String currrentDate) {
		Duration duration = null;
		try {
			duration = DatatypeFactory.newInstance().newDuration(currrentDate);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return duration;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar(String iDate) {
		XMLGregorianCalendar calendar = null;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(iDate);
		} catch (DatatypeConfigurationException e) {
			DebugCoreActivator.log(e);
		}
		return calendar;
	}

	private static final String ISO8601_DATETIME = "yyyy-MM-dd'T'HH:mm:ssZ"; //$NON-NLS-1$
	private static final String ISO8601_DATE = "yyyy-MM-ddZ"; //$NON-NLS-1$
	private static final String ISO8601_TIME = "HH:mm:ssZ"; //$NON-NLS-1$
	private static GregorianCalendar calendar = new GregorianCalendar();

	public static String formatDate(Date date) {
		String str = new SimpleDateFormat(ISO8601_DATE).format(date);
		return str.substring(0, str.length() - 2) + ":" + str.substring(str.length() - 2, str.length()); //$NON-NLS-1$
	}

	public static String formatDateTime(Date date) {
		String str = new SimpleDateFormat(ISO8601_DATETIME).format(date);
		return str.substring(0, str.length() - 2) + ":" + str.substring(str.length() - 2, str.length()); //$NON-NLS-1$
	}

	public static String formatTime(Date date) {
		String str = new SimpleDateFormat(ISO8601_TIME).format(date);
		return str.substring(0, str.length() - 2) + ":" + str.substring(str.length() - 2, str.length()); //$NON-NLS-1$
	}

	public static String formatLocaleDate(Date date) {
		return SimpleDateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
	}

	public static String formatLocaleDateTime(Date date) {
		return SimpleDateFormat.getDateTimeInstance(DateFormat.DATE_FIELD, DateFormat.DATE_FIELD).format(date);
	}

	public static String formatLocaleDateTimeZone(Date date, TimeZone timeZone) {
		DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DATE_FIELD, DateFormat.FULL);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(date);
	}

	public static String formatLocaleDuration(Duration duration) {
		return formatDuration(duration.toString());
	}

	public static String formatLocaleTime(Date date) {
		return SimpleDateFormat.getTimeInstance(DateFormat.DATE_FIELD).format(date);
	}

	public static String formatTimeScript(Date date) {
		calendar.setTime(date);
		return String.format("DateTimeUtil.createTime(%d,%d,%d)", //$NON-NLS-1$
				new Object[] { calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0 });
	}

	public static String formatDateScript(Date date) {
		calendar.setTime(date);
		return String.format("DateTimeUtil.createDate(%d,%d,%d)", //$NON-NLS-1$
				new Object[] { calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) });
	}

	public static String formatDateTimeScript(Date date) {
		calendar.setTime(date);
		return String.format("DateTimeUtil.createDatetime(%d,%d,%d,%d,%d,%d)", //$NON-NLS-1$
				new Object[] { calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0 });
	}

	public static String formatDateTimeZoneScript(Date date, int timeZone) {
		calendar.setTime(date);
		return String.format("DateTimeUtil.createDatetimetz(%d,%d,%d,%d,%d,%d,%d,%d)", //$NON-NLS-1$
				new Object[] { calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0, 0, timeZone });
	}

	public static String formatDurationScript(Duration duration) {
		return String.format("DateTimeUtil.createDuration(%b,%d,%d,%d,%d,%d,%d)", //$NON-NLS-1$
				new Object[] { duration.getSign() > 0 ? true : false, duration.getYears(), duration.getMonths(), duration.getDays(),
						duration.getHours(), duration.getMinutes(), duration.getSeconds() });
	}

	/**
	 * Get the ISO 8601 standard date/time string.
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getISO8601String(XMLGregorianCalendar calendar) {
		return calendar.toString();
	}

	private static String formatDuration(String duration) {
		String result = ""; //$NON-NLS-1$
		int index = duration.indexOf("T"); //$NON-NLS-1$
		if (index > 0) {
			result = durationFilter(duration.substring(0, index)) + durationFilter(duration.substring(index, duration.length()));
			if (result.endsWith("T")) { //$NON-NLS-1$
				result = result.replace("T", ""); //$NON-NLS-1$
			}
		} else {
			result = duration;
		}
		return result;
	}

	private static String durationFilter(String duration) {
		int size = duration.length();
		for (int i = 0; i < size; i++) {
			char ch = duration.charAt(i);
			if (String.valueOf(ch).equals("0")) { //$NON-NLS-1$
				char temp = duration.charAt(i - 1);
				if (!(temp >= '0' && temp <= '9')) {
					String sub = duration.substring(i, i + 2);
					duration = duration.replaceFirst(sub, ""); //$NON-NLS-1$
					size = size - sub.length();
					i--;
				}
			}
		}
		return duration;
	}

	private static final String LOCAL_TIME = "hh:mm aa"; //$NON-NLS-1$
	private static final String LOCAL_DATE = "MM/dd/yy"; //$NON-NLS-1$
	private static final String LOCAL_DATETIME = "MM/dd/yy hh:mm aa"; //$NON-NLS-1$
	private static final String LCOAL_DATETIMEZONE = "MM/dd/yy hh:mm:ss aa"; //$NON-NLS-1$
	private static final String LOCAL_TIME_FORMATTER = "HH:mm:ss.SSS"; //$NON-NLS-1$
	private static final String LOCAL_DT_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSS"; //$NON-NLS-1$
	private static final String LOCAL_SIMPLEDATE = "yyyy-MM-dd"; //$NON-NLS-1$
	private static final String TZONE = "GMT"; //$NON-NLS-1$

	public static String formatGregorianCalendarTime(String expression) {
		SimpleDateFormat base = new SimpleDateFormat(LOCAL_TIME);
		SimpleDateFormat df = new SimpleDateFormat(LOCAL_TIME_FORMATTER);
		try {
			Date date = (Date) base.parse(expression);
			expression = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return expression;
	}

	public static String formatGregorianCalendarDate(String expression) {
		SimpleDateFormat base = new SimpleDateFormat(LOCAL_DATE);
		SimpleDateFormat df = new SimpleDateFormat(LOCAL_SIMPLEDATE);
		try {
			Date date = (Date) base.parse(expression);
			expression = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return expression;
	}

	public static String formatGregorianCalendarDateTime(String expression) {
		SimpleDateFormat base = new SimpleDateFormat(LOCAL_DATETIME);
		SimpleDateFormat df = new SimpleDateFormat(LOCAL_DT_FORMATTER);
		try {
			Date date = (Date) base.parse(expression);
			expression = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return expression;
	}

	public static String formatGregorianCalendarDateTimeZone(String expression) {
		String dTimeZone = null;
		String dtPart = expression.substring(0, expression.indexOf(TZONE)).trim();
		String zonePart = expression.substring(expression.indexOf(TZONE) + 3, expression.length());
		SimpleDateFormat base = new SimpleDateFormat(LCOAL_DATETIMEZONE);
		SimpleDateFormat df = new SimpleDateFormat(LOCAL_DT_FORMATTER);
		try {
			Date date = (Date) base.parse(dtPart);
			dTimeZone = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dTimeZone + zonePart;
	}

	public static boolean isValidZone(String expression) {
		boolean isValid = true;
		String zonePart = expression.substring(expression.indexOf(TZONE) + 4, expression.length());
		int zone = Integer.valueOf(zonePart.substring(0, 2));
		if (zone > 12) {
			isValid = false;
		}
		return isValid;
	}
	
	public static XMLGregorianCalendar createXMLGregorianCalendar4Date(Date iDate) {
		SimpleDateFormat df = new SimpleDateFormat(LOCAL_DT_FORMATTER);
		String expression = df.format(iDate);
		return createXMLGregorianCalendar(expression);
	}

}
