/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * Utility class that can be used to serialize date/time in the Organization
 * Model using the ISO 8601 standardized format. The time will also be
 * normalized to UTC to avoid any confusion of time zones/daylight saving time.
 * 
 * @author njpatel
 * 
 */
public final class BOMDateUtil {

    private static final String ISO8601_DATETIME = "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$
    private static final String ISO8601_DATE = "yyyy-MM-dd"; //$NON-NLS-1$
    private static final String ISO8601_TIME = "HH:mm:ss"; //$NON-NLS-1$

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
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar parse(String dateStr, DateType type)
            throws DatatypeConfigurationException {
        if (dateStr != null) {
            XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(dateStr);
            return xmlCalendar;
        }
        return null;
    }

    /**
     * Get the current locale format string representation of the calendar date.
     * 
     * @param calendar
     * @param type
     *            format type - i.e. Date, DateTime, Time or DateTime and
     *            TimeZone.
     * @return current locale string
     */
    public static String getLocalizedString(XMLGregorianCalendar calendar,
            DateType type) {
        String dateStr = null;

        if (calendar != null) {
            XMLGregorianCalendar temp = (XMLGregorianCalendar) calendar.clone();
            // Clear the timezone as we want the time as-is, not converted to
            // local time
            temp.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            Date date = temp.toGregorianCalendar().getTime();
            switch (type) {
            case DATE:
                dateStr = SimpleDateFormat.getDateInstance(DateFormat.SHORT)
                        .format(date);
                break;
            case DATETIME:
            case DATETIMETZ:
                dateStr = SimpleDateFormat.getDateTimeInstance(
                        DateFormat.SHORT, DateFormat.SHORT).format(date);
                int offset = calendar.getTimezone();
                if (type == DateType.DATETIMETZ
                        && offset != DatatypeConstants.FIELD_UNDEFINED) {
                    // Convert offset into hours
                    Double value = (Double) (offset / 60.0);
                    // Add the timezone
                    if (value == 0) {
                        dateStr += " (GMT)"; //$NON-NLS-1$
                    } else {
                        String str;
                        if (value == value.intValue()) {
                            str = String.valueOf(value.intValue());
                        } else {
                            str = String.valueOf(value);
                        }
                        dateStr += " (GMT" + (value > 0 ? "+" : "") + str + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    }
                }
                break;
            case TIME:
                dateStr = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
                        .format(date);
                break;
            }
        }

        return dateStr;
    }

    /**
     * Get the ISO 8601 standard date/time string.
     * 
     * @param calendar
     * @param type
     * @return
     */
    public static String getISO8601String(XMLGregorianCalendar calendar,
            DateType type) {
        String str = null;

        if (type == DateType.DATETIMETZ) {
            str = calendar.toXMLFormat();
        } else {
            Date date = calendar.toGregorianCalendar().getTime();
            String datePattern;
            switch (type) {
            case DATE:
                datePattern = ISO8601_DATE;
                break;
            case TIME:
                datePattern = ISO8601_TIME;
                break;
            default:
                datePattern = ISO8601_DATETIME;
                break;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            str = formatter.format(date);
        }

        return str;
    }

}
