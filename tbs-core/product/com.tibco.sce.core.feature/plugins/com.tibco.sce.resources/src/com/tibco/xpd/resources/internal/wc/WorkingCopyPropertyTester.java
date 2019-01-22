/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.internal.wc;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * <code>WorkingCopy</code> property tester. This implements the following
 * tests:
 * <ul>
 * <li><b>hasWorkingCopy</b> - Check if the given IResource has an associated
 * WorkingCopy.</li>
 * <li><b>isWorkingCopyDirty</b> - Check if the given IResource has an
 * associated WorkingCopy and is dirty.</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public class WorkingCopyPropertyTester extends PropertyTester {

    private static final String PROP_HASWC = "hasWorkingCopy"; //$NON-NLS-1$

    private static final String PROP_WCDIRTY = "isWorkingCopyDirty"; //$NON-NLS-1$

    /**
     * <code>WorkingCopy</code> property tester.
     */
    public WorkingCopyPropertyTester() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        boolean result = false;

        if (receiver instanceof IResource) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    (IResource) receiver);

            if (wc != null) {
                if (property.equals(PROP_HASWC)) {
                    // Has working copy
                    result = true;
                } else if (property.equals(PROP_WCDIRTY)) {
                    // Check if working copy is dirty
                    result = wc.isWorkingCopyDirty();
                }
            }
        }

        return result;
    }

}
