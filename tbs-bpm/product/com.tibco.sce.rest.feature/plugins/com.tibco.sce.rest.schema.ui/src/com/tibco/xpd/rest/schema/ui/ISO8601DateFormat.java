/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date formatter utility used for parsing of ISO 8601 format dates and times.
 * By default the parser will expect both a Date and Time separated by a 'T' or
 * space, although milliseconds and time zone are optional and will default to
 * zero and the local time zone if not present.
 * 
 * If the class is contructed with the ISO8601DateFormatType DATE constant then
 * it will expect only a Date. Parsing will fail if a time component is
 * provided.
 * 
 * Using ISO8601DateFormatType TIME the parser will expect only a time. Parsing
 * will fail if a Date component is provided. Again milliseconds and time zone
 * are optional.
 * 
 * @author nwilson
 * @since 23 Jan 2015
 */
public class ISO8601DateFormat extends DateFormat {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 1L;

    public enum ISO8601DateFormatType {
        DATE_TIME, DATE, TIME
    }

    private ISO8601DateFormatType type;

    /**
     * ISO 8601 date format constructor for a DATE_TIME format type.
     */
    public ISO8601DateFormat() {
        this(ISO8601DateFormatType.DATE_TIME);
    }

    /**
     * ISO 8601 date format constructor for a specific format type.
     * 
     * @param type
     *            The format type.
     */
    public ISO8601DateFormat(ISO8601DateFormatType type) {
        this.type = type;
    }

    /**
     * @see java.text.DateFormat#format(java.util.Date, java.lang.StringBuffer,
     *      java.text.FieldPosition)
     * 
     * @param date
     *            The date to format.
     * @param toAppendTo
     *            The string buffer to append to.
     * @param fieldPosition
     *            formatter field identification.
     * @return The modified string buffer.
     */
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
            FieldPosition fieldPosition) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.text.DateFormat#parse(java.lang.String,
     *      java.text.ParsePosition)
     * 
     * @param source
     *            The source string.
     * @param pos
     *            The starting position in the string.
     * @return The parsed date, or null if it could not be parsed.
     */
    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;
        int offset = pos.getIndex();
        int separator;
        GregorianCalendar cal = new GregorianCalendar();
        try {
            if (!ISO8601DateFormatType.TIME.equals(type)) {
                separator = source.indexOf('-', offset);
                String yearString = source.substring(offset, separator);
                int year = Integer.parseInt(yearString);
                cal.set(Calendar.YEAR, year);
                offset = separator + 1;

                separator = source.indexOf('-', offset);
                String monthString = source.substring(offset, separator);
                int month = Integer.parseInt(monthString) - 1;
                cal.set(Calendar.MONTH, month);
                offset = separator + 1;

                if (ISO8601DateFormatType.DATE_TIME.equals(type)) {
                    separator = source.indexOf('T', offset);
                    if (separator == -1) {
                        separator = source.indexOf(' ', offset);
                    }
                } else {
                    separator = source.length();
                }
                String dayString = source.substring(offset, separator);
                int day = Integer.parseInt(dayString);
                cal.set(Calendar.DAY_OF_MONTH, day);
                offset = separator + 1;
            }
            if (!ISO8601DateFormatType.DATE.equals(type)) {
                separator = source.indexOf(':', offset);
                String hourString = source.substring(offset, separator);
                int hour = Integer.parseInt(hourString);
                cal.set(Calendar.HOUR_OF_DAY, hour);
                offset = separator + 1;

                separator = source.indexOf(':', offset);
                String minuteString = source.substring(offset, separator);
                int minute = Integer.parseInt(minuteString);
                cal.set(Calendar.MINUTE, minute);
                offset = separator + 1;

                boolean expectMillis = false;
                separator = source.indexOf('.', offset);
                if (separator == -1) {
                    separator = source.indexOf('+', offset);
                } else {
                    expectMillis = true;
                }
                if (separator == -1) {
                    separator = source.indexOf('Z', offset);
                }
                if (separator == -1) {
                    separator = source.length();
                }
                String secondString = source.substring(offset, separator);
                int second = Integer.parseInt(secondString);
                cal.set(Calendar.SECOND, second);
                offset = separator + 1;

                if (expectMillis) {
                    separator = source.indexOf('+', offset);
                    if (separator == -1) {
                        separator = source.indexOf('Z', offset);
                    }
                    if (separator == -1) {
                        separator = source.length();
                    }
                    if (separator > offset) {
                        String millisString =
                                source.substring(offset, separator);
                        int millis = Integer.parseInt(millisString);
                        cal.set(Calendar.MILLISECOND, millis);
                        offset = separator + 1;
                    }
                }
            }
            pos.setIndex(offset);
            date = cal.getTime();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
        }
        return date;
    }

}
