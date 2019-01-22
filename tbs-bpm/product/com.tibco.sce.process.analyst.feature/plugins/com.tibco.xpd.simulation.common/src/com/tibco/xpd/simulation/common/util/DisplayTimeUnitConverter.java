/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tibco.xpd.simulation.TimeDisplayUnitType;

/**
 * Very simple utility converter class.
 * 
 * @author jarciuch
 */
public final class DisplayTimeUnitConverter {

    /** Year conversion factor. */
    private static final long FACTOR_YEAR = 31557600000L;

    /** Month conversion factor. */
    private static final long FACTOR_MONTH = 2629800000L;

    /** Day conversion factor. */
    private static final long FACTOR_DAY = 86400000L;

    /** Hour conversion factor. */
    private static final long FACTOR_HOUR = 3600000L;

    /** Minute conversion factor. */
    private static final long FACTOR_MINUTE = 60000L;

    /** Second conversion factor. */
    private static final long FACTOR_SECOND = 1000L;

    /** Default time unit. */
    public static final TimeDisplayUnitType DEFAULT_TIME_UNIT =
            TimeDisplayUnitType.MINUTE_LITERAL;

    /** Default locale. */
    public static final Locale DEFAULT_LOCALE = Locale.getDefault();

    /** Default time format. */
    public static final String DEFAULT_FORMAT = "#0.############"; //$NON-NLS-1$

    /** Default short format. */
    public static final String DEFAULT_SHORT_FORMAT = "#0.####"; //$NON-NLS-1$

    /** Default percentage format. */
    public static final String DEFAULT_PERCENTAGE_FORMAT = "##0.# %"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private DisplayTimeUnitConverter() {
    }

    /** Conversion factor map. */
    private static Map<TimeDisplayUnitType, Long> factors =
            new HashMap<TimeDisplayUnitType, Long>();
    static {
        // factors.put(TimeDisplayUnitType.MILLISECOND_LITERAL, new Long(1L));
        factors.put(TimeDisplayUnitType.SECOND_LITERAL, new Long(FACTOR_SECOND));
        factors.put(TimeDisplayUnitType.MINUTE_LITERAL, new Long(FACTOR_MINUTE));
        factors.put(TimeDisplayUnitType.HOUR_LITERAL, new Long(FACTOR_HOUR));
        factors.put(TimeDisplayUnitType.DAY_LITERAL, new Long(FACTOR_DAY));
        factors.put(TimeDisplayUnitType.MONTH_LITERAL, new Long(FACTOR_MONTH));
        factors.put(TimeDisplayUnitType.YEAR_LITERAL, new Long(FACTOR_YEAR));
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @return The converted value as a String.
     */
    public static String convertToString(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value) {
        return convertToString(oldUnit, newUnit, value, DEFAULT_LOCALE,
                DEFAULT_FORMAT);
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @param loc The locale to use.
     * @return The converted value as a String.
     */
    public static String convertToString(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value, Locale loc) {
        return convertToString(oldUnit, newUnit, value, loc, DEFAULT_FORMAT);
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @param loc The locale to use.
     * @param format The display format to use.
     * @return The converted value as a String.
     */
    public static String convertToString(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value, Locale loc, String format) {
        double retVal =
                value * ((Long) factors.get(oldUnit)).longValue()
                        / ((Long) factors.get(newUnit)).longValue();
        DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance(loc);
        f.applyPattern(format);
        return f.format(retVal);
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @param format The display format to use.
     * @return The converted value as a String.
     */
    public static String convertToString(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value, String format) {
        return convertToString(oldUnit, newUnit, value, DEFAULT_LOCALE, format);
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @return The converted value.
     */
    public static Double convertToDouble(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value) {
        return new Double(value * ((Long) factors.get(oldUnit)).longValue()
                / ((Long) factors.get(newUnit)).longValue());
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @return The converted value.
     */
    public static Double convertToDouble(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, String value) {
        try {
            DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance(DEFAULT_LOCALE);
            value = value.replace(f.getDecimalFormatSymbols().getDecimalSeparator(), '.');        
            double parsedValue = Double.parseDouble(value);
            
            return new Double(parsedValue
                    * ((Long) factors.get(oldUnit)).longValue()
                    / ((Long) factors.get(newUnit)).longValue());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param oldUnit The old time unit.
     * @param newUnit The new time unit.
     * @param value The value to convert.
     * @return The converted value.
     */
    public static double convert(TimeDisplayUnitType oldUnit,
            TimeDisplayUnitType newUnit, double value) {
        return value * ((Long) factors.get(oldUnit)).longValue()
                / ((Long) factors.get(newUnit)).longValue();
    }
}
