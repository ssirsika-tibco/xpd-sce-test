/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * WebContact test class.
 * 
 * @author nwilson
 * @since 23 Jun 2015
 */
public class Visitor {
    private String name;

    private Location location;

    private XMLGregorianCalendar visit;

    private EList<ContactDetails> details;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the visit
     */
    public XMLGregorianCalendar getVisit() {
        return visit;
    }

    /**
     * @param visit
     *            the visit to set
     */
    public void setVisit(XMLGregorianCalendar visit) {
        this.visit = visit;
    }

    /**
     * @return the details
     */
    public EList<ContactDetails> getDetails() {
        if (details == null) {
            details = new BasicEList<ContactDetails>();
        }
        return details;
    }

}
