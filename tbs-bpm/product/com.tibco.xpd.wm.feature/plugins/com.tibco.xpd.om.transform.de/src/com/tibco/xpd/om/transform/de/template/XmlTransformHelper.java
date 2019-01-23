/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.template;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;

/**
 * Utility class for XML model transformation.
 * <p>
 * <i>Created: 3 Mar 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class XmlTransformHelper {

    private static final SimpleDateFormat FORMAT_ISO_8601 =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //$NON-NLS-1$

    /**
     * Converts date to to String using ISO-8601 XML-Schema format.
     * 
     * @param date
     *            date to format.
     * @return String with a formated date (or <code>null</code> if the argument
     *         is <code>null</code>).
     */
    public static String eDateToString(Date date) {
        if (date != null) {
            return XMLTypeFactory.eINSTANCE.createDateTime(FORMAT_ISO_8601
                    .format(date)).normalize().toXMLFormat();
        }
        return null;
    }

    /**
     * Utility method to print object from within transformation.
     * 
     * @param o
     *            object to trace;
     * @return the same object as passed as argument. (Allow to trace object
     *         returned from a function. For example in case of create
     *         expression : ... ->traceMe(this); )
     */
    public static Object traceMe(final Object o) {
        System.out.println(o);
        return o;
    }
}
