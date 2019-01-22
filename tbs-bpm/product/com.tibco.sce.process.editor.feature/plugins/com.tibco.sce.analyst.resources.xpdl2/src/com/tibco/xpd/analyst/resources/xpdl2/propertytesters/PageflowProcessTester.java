/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class PageflowProcessTester extends PropertyTester {

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        boolean isPageflow = false;
        if (receiver instanceof Process) {
            isPageflow = Xpdl2ModelUtil.isPageflow((Process) receiver);
        }
        return isPageflow;
    }

}
