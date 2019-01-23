/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Utility class that can be used to serialize date/time using the ISO 8601
 * standardized format. The time will also be normalized to UTC to avoid any
 * confusion of time zones/daylight saving time.
 * <p>
 * This format of date is used for example in the Organization Model.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class DateUtil {

    private static final Logger LOG = XpdResourcesUIActivator.getDefault()
            .getLogger();

    private static final String ISO8601_DATETIME = "yyyy-MM-dd'T'HH:mm:ssZ"; //$NON-NLS-1$

    private static final String ISO8601_DATE = "yyyy-MM-dd"; //$NON-NLS-1$

    private static final String ISO8601_TIME = "HH:mm:ssZ"; //$NON-NLS-1$

    private static final String UTC_TIME_ZONE = "UTC"; //$NON-NLS-1$

    /**
     * Format the date into a ISO 8601 standard string. This will also normalize
     * the time to UTC.
     * 
     * @param date
     *            date to format
     * @param type
     *            format type - i.e. Date, DateTime or Time.
     * @return ISO 8601 string or <code>null</code> if date passed in is
     *         <code>null</code>.
     */
    public static String format(Date date, DateType type) {
        String dateStr = null;

        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(getPattern(type));
            formatter.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
            dateStr = formatter.format(date);
        }

        return dateStr;
    }

    /**
     * Parse the given UTC ISO 8601 date string into a {@link Date} (string
     * resulting from call to {@link #format(Date, DateType)}.
     * 
     * @param dateStr
     *            ISO 8601 date/time string
     * @param type
     *            format type - i.e. Date, DateTime or Time.
     * @return {@link Date} or <code>null</code> if string passed in is
     *         <code>null</code>.
     * @throws ParseException
     */
    public static Date parse(String dateStr, DateType type)
            throws ParseException {
        Date dt = null;
        if (dateStr != null) {
            try {
                SimpleDateFormat formatter =
                        new SimpleDateFormat(getPattern(type));
                formatter.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
                dt = formatter.parse(dateStr);
            } catch (ParseException e) {
                /*
                 * If the date is not in ISO 8601 format that it may be in the
                 * locale-specific format so try to parse. This would be the
                 * case with earlier models before the date/time format was
                 * changed.
                 */
                DateFormat formatter = getFormatter(type);
                dt = formatter.parse(dateStr);
            }
        }
        return dt;
    }

    /**
     * Get the current locale format string representation of the date.
     * 
     * @param date
     * @param type
     *            format type - i.e. Date, DateTime or Time.
     * @return current locale string
     */
    public static String getLocalizedString(Date date, DateType type) {
        String dateStr = null;

        if (date != null) {
            switch (type) {
            case DATE:
                dateStr =
                        SimpleDateFormat.getDateInstance(DateFormat.SHORT)
                                .format(date);
                break;
            case DATETIME:
                dateStr =
                        SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,
                                DateFormat.SHORT).format(date);
                break;
            case TIME:
                dateStr =
                        SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
                                .format(date);
                break;
            }
        }

        return dateStr;
    }

    /**
     * Convert the ISO 8601 standard date/time string to a current locale format
     * string for display.
     * 
     * @param dateStr
     *            ISO 8601 standard string
     * @param type
     *            format type - i.e. Date, DateTime or Time.
     * @return current locale format date/time string
     * @throws ParseException
     */
    public static String getLocalizedStringFromISO8601String(String dateStr,
            DateType type) throws ParseException {
        if (dateStr != null) {
            Date dt = parse(dateStr, type);
            if (dt != null) {
                dateStr = getLocalizedString(dt, type);
            }
        }
        return dateStr;
    }

    /**
     * Converts a string date in ISO 8601 format ("HH:mm:ssZ", "yyyy-MM-dd",
     * "yyyy-MM-dd'T'HH:mm:ssZ") to corresponding XMLGregorianCalendar instance.
     * 
     * @param date
     *            string date in ISO 8601 time format to convert.
     * @param dateType
     *            the type of the date {@link DateType}
     * @return XMLGregorianCalendar instance representing the date.
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(String date,
            DateType dateType) {
        try {
            int UTC = 0;
            Date timeMilis = DateUtil.parse(date, dateType);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(timeMilis);
            XMLGregorianCalendar xgc =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            switch (dateType) {
            case TIME:
                return DatatypeFactory.newInstance()
                        .newXMLGregorianCalendarTime(xgc.getHour(),
                                xgc.getMinute(),
                                xgc.getSecond(),
                                UTC);
            case DATE:
                return DatatypeFactory.newInstance()
                        .newXMLGregorianCalendarDate(xgc.getYear(),
                                xgc.getMonth(),
                                xgc.getDay(),
                                DatatypeConstants.FIELD_UNDEFINED);
            case DATETIME:
                return DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(xgc.getYear(),
                                xgc.getMonth(),
                                xgc.getDay(),
                                xgc.getHour(),
                                xgc.getMinute(),
                                xgc.getSecond(),
                                DatatypeConstants.FIELD_UNDEFINED,
                                UTC);
            default:
                return DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(xgc.getYear(),
                                xgc.getMonth(),
                                xgc.getDay(),
                                xgc.getHour(),
                                xgc.getMinute(),
                                xgc.getSecond(),
                                DatatypeConstants.FIELD_UNDEFINED,
                                UTC);
            }
        } catch (ParseException e) {
            LOG.error(e);
            return null;
        } catch (DatatypeConfigurationException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * Get the appropriate ISO 8601 format pattern.
     * 
     * @param type
     * @return
     */
    private static String getPattern(DateType type) {
        switch (type) {
        case DATE:
            return ISO8601_DATE;
        case TIME:
            return ISO8601_TIME;
        }

        return ISO8601_DATETIME;
    }

    /**
     * Get the locale-specific date formatter for the given date/time type.
     * 
     * @param type
     * @return
     */
    private static DateFormat getFormatter(DateType type) {
        if (type != null) {
            switch (type) {
            case DATE:
                return SimpleDateFormat.getDateInstance();
            case TIME:
                return SimpleDateFormat.getTimeInstance();
            }
        }
        return SimpleDateFormat.getDateTimeInstance();
    }
}
