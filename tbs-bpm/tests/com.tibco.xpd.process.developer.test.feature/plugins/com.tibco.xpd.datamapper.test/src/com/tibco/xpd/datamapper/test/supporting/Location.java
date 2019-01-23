/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.Enumerator;

/**
 * 
 * 
 * @author nwilson
 * @since 23 Jun 2015
 */
public enum Location implements Enumerator {
    LONDON("London"), //$NON-NLS-1$
    EDINBURGH("Edinburgh"), //$NON-NLS-1$
    BRISTOL("Bristol"); //$NON-NLS-1$

    private String literal;

    Location(String literal) {
        this.literal = literal;
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
        return literal;
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
