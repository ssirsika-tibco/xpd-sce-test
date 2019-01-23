/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * WebContact test class.
 * 
 * @author nwilson
 * @since 23 Jun 2015
 */
public class WebContact {
    private String name;

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
     * @return the details
     */
    public EList<ContactDetails> getDetails() {
        if (details == null) {
            details = new BasicEList<ContactDetails>();
        }
        return details;
    }

}
