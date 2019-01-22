/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat.ISO8601DateFormatType;

/**
 * DateTimeUtil method provider.
 * 
 * @author nwilson
 * @since 3 Jun 2015
 */
public class DateTimeUtil {
    public XMLGregorianCalendar createDate(String date) {
        XMLGregorianCalendar cal = null;
        try {
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        return cal;
    }

    public XMLGregorianCalendar createTime(String date) {
        XMLGregorianCalendar cal = null;
        try {
            ISO8601DateFormat df =
                    new ISO8601DateFormat(ISO8601DateFormatType.TIME);
            Date parsed = df.parse(date);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(parsed);
            cal =
                    DatatypeFactory
                            .newInstance()
                            .newXMLGregorianCalendarTime(gc.get(Calendar.HOUR_OF_DAY),
                                    gc.get(Calendar.MINUTE),
                                    gc.get(Calendar.SECOND),
                                    gc.get(Calendar.MILLISECOND),
                                    DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException | ParseException e) {
            fail(e.getMessage());
        }
        return cal;
    }

    public XMLGregorianCalendar createDatetimetz(String date) {
        XMLGregorianCalendar cal = null;
        try {
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        return cal;
    }

    public XMLGregorianCalendar createDatetimetz() {
        XMLGregorianCalendar cal = null;
        try {
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        } catch (DatatypeConfigurationException e) {
            fail(e.getMessage());
        }
        return cal;
    }
}
