package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author glewis
 *
 */
public class DateUtils {
    
    /**
     * Returns the date in string format for the current time zone.
     * @return
     */
    public static String getCurrentLocaleDate(){
        Calendar cal = new GregorianCalendar(TimeZone.getDefault()); 
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); //$NON-NLS-1$
        String strDate = sdf.format(cal.getTime());
        return strDate;
    }   	
}
