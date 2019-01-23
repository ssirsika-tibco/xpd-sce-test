package com.tibco.xpd.resources.ui.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * Util class to change dates to current time zone. Logic is in place for
 * parsing errors so we at least bring something back for user display.
 * 
 * @author glewis
 */
public class LocaleUtils {

    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); //$NON-NLS-1$

    private static final SimpleDateFormat localFormatter =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); //$NON-NLS-1$

    private static final String[] availableTimeZones =
            TimeZone.getAvailableIDs();

    private static final String iso8601DateTimePattern =
            "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$ 

    private static final String iso8601DatePattern = "yyyy-MM-dd"; //$NON-NLS-1$ 

    private static final String iso8601TimePattern = "HH:mm:ss"; //$NON-NLS-1$ 

    private static final int MIN_ASCII_CHAR_NUM = 33;

    private static final int MAX_ASCII_CHAR_NUM = 127;

    /**
     * Tries to find timezone that package was originally created with so we can
     * then convert it back to our default time zone date.
     * 
     * @param strDate
     * @return
     */
    private static TimeZone findTimeZoneInDate(String strDate) {
        Date today = new Date();

        for (int i = 0; i < availableTimeZones.length; i++) {
            String tempTimeZone = availableTimeZones[i];

            TimeZone tz = TimeZone.getTimeZone(tempTimeZone);
            tempTimeZone =
                    tz.getDisplayName(tz.inDaylightTime(today), TimeZone.SHORT);

            if (strDate.indexOf(tempTimeZone) != -1) {
                return tz;
            }
        }
        return TimeZone.getDefault();
    }

    /**
     * Changes any package created date into current timezone - if a parsing
     * error occurs then we take the last modified date in current timezone. If
     * no modifications then todays date is used as last resort.
     */
    public static String globaliseCreatedDate(long modified, String currentDate) {
        Date packageDate = null;

        TimeZone packageTimeZone = findTimeZoneInDate(currentDate);
        Calendar cal = new GregorianCalendar(packageTimeZone);

        try {
            packageDate = sdf.parse(currentDate);
            cal.setTime(packageDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (packageDate == null) {
            if (modified != -1) {
                packageDate = new Date(modified);
            } else {
                packageDate = new Date();
            }
            cal.setTime(packageDate);
        }

        Calendar localCal = new GregorianCalendar();
        localCal.setTimeInMillis(cal.getTimeInMillis());

        String userTimeZoneDate = localFormatter.format(localCal.getTime());

        return userTimeZoneDate;
    }

    /**
     * Returns the currency symbol for a given locale - defaults to USA $ if not
     * found
     */
    public static String getCurrencySymbol(Locale locale) {
        String symbol = "$"; //$NON-NLS-1$
        Currency currency =
                NumberFormat.getCurrencyInstance(locale).getCurrency();
        if (currency != null) {
            symbol = currency.getSymbol();
        }
        return symbol;
    }

    /**
     * Returns the currency code for a given locale - defaults to USA USD if not
     * found
     */
    public static String getCurrencyCode(Locale locale) {
        String code = "USD"; //$NON-NLS-1$
        Currency currency =
                NumberFormat.getCurrencyInstance(locale).getCurrency();
        if (currency != null) {
            code = currency.getCurrencyCode();
        }
        return code;
    }

    /**
     * Returns the currency codes for all locales - sorts them alphabetically
     * also!
     */
    public static String[] getCurrencyCodes() {
        Locale[] localeArray = Locale.getAvailableLocales();
        String[] currencyCodesArray = new String[localeArray.length];
        TreeSet<String> currencyCodesSet = new TreeSet<String>();

        for (Locale tempLocale : localeArray) {
            Currency currency =
                    NumberFormat.getCurrencyInstance(tempLocale).getCurrency();
            if (currency != null) {
                String code = currency.getCurrencyCode();
                currencyCodesSet.add(code);
            }
        }
        return (String[]) currencyCodesSet.toArray(currencyCodesArray);
    }

    /**
     * Returns the iso codes for all locales - sorts them alphabetically also!
     */
    public static String[] getISOCodes() {
        Locale[] localeArray = Locale.getAvailableLocales();
        String[] isoCodesArray = new String[localeArray.length];
        ArrayList<String> isoCodesArrayList = new ArrayList<String>();

        for (Locale tempLocale : localeArray) {
            String displayName = tempLocale.getDisplayName();
            isoCodesArrayList.add(displayName);
        }

        Collections.sort(isoCodesArrayList);
        return (String[]) isoCodesArrayList.toArray(isoCodesArray);
    }

    /**
     * Returns the suitable storage locale iso code for passed display name
     */
    public static String getLocaleISOFromDisplayName(String displayName) {
        Locale[] localeArray = Locale.getAvailableLocales();
        for (Locale tempLocale : localeArray) {
            if (tempLocale.getDisplayName().equals(displayName)) {
                return tempLocale.toString();
            }
        }

        return ""; //$NON-NLS-1$";
    }

    /**
     * Returns the suitable display name for passed iso code
     */
    public static String getDisplayNameFromISOCode(String isoCode) {
        Locale[] localeArray = Locale.getAvailableLocales();
        for (Locale tempLocale : localeArray) {
            if (tempLocale.toString().equals(isoCode)) {
                return tempLocale.getDisplayName();
            }
        }

        return ""; //$NON-NLS-1$";
    }

    /**
     * Method to get a localised (set in local system) date string from an iso
     * 8601 pattern date
     * 
     * @param isoDate
     *            ISO8601 patterned date String
     * @return
     */
    public static String getLocalisedDateTime(String isoDate,
            int dateFormatMode, int timeFormatMode) {
        String localisedDate = isoDate;
        if (isoDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(iso8601DateTimePattern);
            try {
                Date tempDate = simpleDateFormat.parse(isoDate);
                DateFormat dateFormat =
                        DateFormat.getDateInstance(dateFormatMode);
                DateFormat timeFormat =
                        DateFormat.getTimeInstance(timeFormatMode);
                localisedDate =
                        dateFormat.format(tempDate)
                                + " " + timeFormat.format(tempDate); //$NON-NLS-1$
            } catch (ParseException e) {
            }
        }
        return localisedDate;
    }

    /**
     * Method to get a localised (set in local system) date string from an iso
     * 8601 pattern date
     * 
     * @param isoDate
     *            ISO8601 patterned date String
     * @return
     */
    public static String getLocalisedDate(String isoDate, int dateFormatMode) {
        String localisedDate = isoDate;
        if (isoDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(iso8601DatePattern);
            try {
                Date tempDate = simpleDateFormat.parse(isoDate);
                DateFormat dateFormat =
                        DateFormat.getDateInstance(dateFormatMode);
                localisedDate = dateFormat.format(tempDate);
            } catch (ParseException e) {
            }
        }
        return localisedDate;
    }

    /**
     * Method to get a localised (set in local system) date string from an iso
     * 8601 pattern date
     * 
     * @param isoDate
     *            ISO8601 patterned date String
     * @return
     */
    public static String getLocalisedTime(String isoDate, int timeFormatMode) {
        String localisedDate = isoDate;
        if (isoDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(iso8601TimePattern);
            try {
                Date tempDate = simpleDateFormat.parse(isoDate);
                DateFormat timeFormat =
                        DateFormat.getTimeInstance(timeFormatMode);
                localisedDate = timeFormat.format(tempDate);
            } catch (ParseException e) {
            }
        }
        return localisedDate;
    }

    /**
     * Method to get an ISO8601 date time pattern string from a date
     * 
     * @param date
     *            Date to turn into iso8601 pattern string
     * @return
     */
    public static String getISO8601DateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(iso8601DateTimePattern);
        return dateFormat.format(date);

    }

    /**
     * Method to get an ISO8601 date pattern string from a date
     * 
     * @param date
     *            Date to turn into iso8601 pattern string
     * @return
     */
    public static String getISO8601Date(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(iso8601DatePattern);
        return dateFormat.format(date);

    }

    /**
     * Method to get an ISO8601 time pattern string from a date
     * 
     * @param date
     *            Date to turn into iso8601 pattern string
     * @return
     */
    public static String getISO8601Time(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(iso8601TimePattern);
        return dateFormat.format(date);

    }

    /**
     * Detects if the String contains any non ascii chars in it and then
     * converts it to the appropriate unicode string so it can be supported.
     * 
     * @param text
     * @return
     */
    public static String escapeNonAsciiChars(String text) {
        String escapedNonAsciiValue = ""; //$NON-NLS-1$
        for (int i = 0; i < text.length(); i++) {
            char tempChar = text.charAt(i);
            if (((tempChar < MIN_ASCII_CHAR_NUM || tempChar > MAX_ASCII_CHAR_NUM))
                    && (tempChar != ' ')) {
                int codeVal = (int) tempChar;
                String tempHex = Integer.toHexString(codeVal).toUpperCase();
                int length = tempHex.length();
                while (length < 4) {
                    tempHex = "0" + tempHex; //$NON-NLS-1$
                    length = tempHex.length();
                }
                escapedNonAsciiValue = escapedNonAsciiValue + "\\u" + tempHex; //$NON-NLS-1$
            } else {
                escapedNonAsciiValue += tempChar;
            }
        }
        return escapedNonAsciiValue;
    }

    /**
     * @param doubleVal
     * @return
     */
    public static String getLocalisedDoubleValue(String doubleVal) {
        String localisedDoubleValue = doubleVal;
        try {
            double dblVal = Double.parseDouble(doubleVal.trim());
            localisedDoubleValue = DecimalFormat.getInstance().format(dblVal);
        } catch (NumberFormatException e) {
        }
        return localisedDoubleValue;
    }

    /**
     * @param doubleVal
     * @return
     */
    public static String getStandardisedDoubleValue(String doubleVal) {
        String standardiseDoubleValue = doubleVal;
        try {
            Number tempNumber =
                    DecimalFormat.getInstance(Locale.getDefault())
                            .parse(doubleVal);
            doubleVal = String.valueOf(tempNumber.doubleValue());
        } catch (Exception e) {
        }

        try {
            Double.parseDouble(doubleVal);
            standardiseDoubleValue = doubleVal;
        } catch (NumberFormatException e) {
        }

        return standardiseDoubleValue;
    }

    public static String getLocalisedDefaultDoubleValue() {
        double tempVal = Double.parseDouble("1.11"); //$NON-NLS-1$
        String reportFormat = DecimalFormat.getInstance().format(tempVal);
        reportFormat = reportFormat.replace("1", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        return reportFormat;
    }

    /**
     * @param tempChar
     * @return
     */
    private static boolean isInteger(String tempChar) {
        try {
            Integer.parseInt(tempChar);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
