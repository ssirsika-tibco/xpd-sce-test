/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import java.text.ParseException;

import junit.framework.TestCase;

import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat.ISO8601DateFormatType;

/**
 * Test for ISO 8601 Date parsing code.
 * 
 * @author nwilson
 * @since 23 Jan 2015
 */
public class ISO8601DateFormatUnitTest extends TestCase {

    private ISO8601DateFormat dt;

    private ISO8601DateFormat d;

    private ISO8601DateFormat t;

    @Override
    public void setUp() {
        dt = new ISO8601DateFormat();
        d = new ISO8601DateFormat(ISO8601DateFormatType.DATE);
        t = new ISO8601DateFormat(ISO8601DateFormatType.TIME);
    }

    public void testDateTime1() {
        checkDateTime("2015-01-23T10:30:36+00:00"); //$NON-NLS-1$
    }

    public void testDateTime2() {
        checkDateTime("2015-01-23T10:30:36Z"); //$NON-NLS-1$
    }

    public void testDateTime3() {
        checkDateTime("2015-01-23T10:30:36"); //$NON-NLS-1$
    }

    public void testDateTime4() {
        checkDateTime("2015-01-23 10:30:36"); //$NON-NLS-1$
    }

    public void testDate1() {
        checkDate("2015-01-23"); //$NON-NLS-1$
    }

    public void testTime1() {
        checkTime("10:30:36+00:00"); //$NON-NLS-1$
    }

    public void testTime2() {
        checkTime("10:30:36Z"); //$NON-NLS-1$
    }

    public void testTime3() {
        checkTime("10:30:36"); //$NON-NLS-1$
    }

    public void testFailDateTime1() {
        checkFailDateTime("10:30:36+00:00"); //$NON-NLS-1$
    }

    public void testFailDateTime2() {
        checkFailDateTime("2015-01-23"); //$NON-NLS-1$
    }

    public void testFailDate1() {
        checkFailDate("10:30:36+00:00"); //$NON-NLS-1$
    }

    public void testFailDate2() {
        checkFailDate("2015-01-23T10:30:36"); //$NON-NLS-1$
    }

    public void testFailTime1() {
        checkFailTime("2015-01-23"); //$NON-NLS-1$
    }

    public void testFailTime2() {
        checkFailTime("2015-01-23T10:30:36"); //$NON-NLS-1$
    }

    public void testFailTime3() {
        checkFailTime("10:30:36s123"); //$NON-NLS-1$
    }

    /**
     * @param s
     */
    private void checkDateTime(String s) {
        try {
            assertNotNull(dt.parse(s));
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    /**
     * @param s
     */
    private void checkDate(String s) {
        try {
            assertNotNull(d.parse(s));
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    /**
     * @param s
     */
    private void checkTime(String s) {
        try {
            assertNotNull(t.parse(s));
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    /**
     * @param s
     */
    private void checkFailDateTime(String s) {
        try {
            dt.parse(s);
            fail("Expected ParseException"); //$NON-NLS-1$
        } catch (ParseException e) {
        }
    }

    /**
     * @param s
     */
    private void checkFailDate(String s) {
        try {
            d.parse(s);
            fail("Expected ParseException"); //$NON-NLS-1$
        } catch (ParseException e) {
        }
    }

    /**
     * @param s
     */
    private void checkFailTime(String s) {
        try {
            t.parse(s);
            fail("Expected ParseException"); //$NON-NLS-1$
        } catch (ParseException e) {
        }
    }

}
