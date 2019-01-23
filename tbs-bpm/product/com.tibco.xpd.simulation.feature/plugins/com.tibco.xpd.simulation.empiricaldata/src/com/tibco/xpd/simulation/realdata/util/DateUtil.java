/* 
 ** 
 **  MODULE:             $RCSfile: DateUtil.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-04-04 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.realdata.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities class for date conversion.
 * 
 * @author jarciuch
 */
public class DateUtil {

    public static DateFormat[] DATETIME_FORMATS = new DateFormat[] {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"), //$NON-NLS-1$
            new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"), //$NON-NLS-1$
            new SimpleDateFormat("yyyy-MM-dd") }; //$NON-NLS-1$

    /**
     * This method ignore time zone while convertig and trying all
     * DATETIME_FORMATS in sequence if previous fails.
     * 
     * @param stringDate
     *            string to convert.
     * @return
     */
    public static Date parseDate(String stringDate) {
        for (int i = 0; i < DATETIME_FORMATS.length; i++) {
            try {
                return DATETIME_FORMATS[i].parse(stringDate);
            } catch (ParseException e) {
                // Ignore, exception will be thrown.
            }
        }
        throw new RuntimeException("Could not parse date! [" + stringDate + "]"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
