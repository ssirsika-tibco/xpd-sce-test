/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import org.eclipse.emf.common.util.Enumerator;

/**
 * Text enumeration test class.
 * 
 * @author nwilson
 * @since 19 Jun 2015
 */
public enum Names implements Enumerator {
    ABC(0),
    DEF(1);

    private int value;

    Names(int value) {
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
        return value;
    }

    /**
     * @see org.eclipse.emf.common.util.Enumerator#getLiteral()
     * 
     * @return
     */
    @Override
    public String getLiteral() {
        return name();
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
