/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.Enumerator;

/**
 * Date enumeration test class.
 * 
 * @author nwilson
 * @since 19 Jun 2015
 */
public enum Dates implements Enumerator {
    D1("2015-06-22"), //$NON-NLS-1$
    D2("2015-06-23"); //$NON-NLS-1$

    private String value;

    Dates(String value) {
        this.value = value;
    }

    /**
     * @see org.eclipse.emf.common.util.Enumerator#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * @see org.eclipse.emf.common.util.Enumerator#getValue()
     * 
     * @return
     */
    @Override
    public int getValue() {
        return ordinal();
    }

    /**
     * @see org.eclipse.emf.common.util.Enumerator#getLiteral()
     * 
     * @return
     */
    @Override
    public String getLiteral() {
        return value;
    }

    /**
     * @see java.lang.Enum#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getLiteral();
    }

}
