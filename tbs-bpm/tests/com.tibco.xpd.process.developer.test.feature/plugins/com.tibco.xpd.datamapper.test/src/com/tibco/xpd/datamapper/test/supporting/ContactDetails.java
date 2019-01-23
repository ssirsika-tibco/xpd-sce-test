/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * ContactDetails test class.
 * 
 * @author nwilson
 * @since 23 Jun 2015
 */
public class ContactDetails {
    private String type;

    protected EList<String> email;

    protected EList<Phone> phone;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the email
     */
    public EList<String> getEmail() {
        if (email == null) {
            email = new BasicEList<String>();
        }
        return email;
    }

    /**
     * @return the phone
     */
    public EList<Phone> getPhone() {
        if (phone == null) {
            phone = new BasicEList<Phone>();
        }
        return phone;
    }

}
