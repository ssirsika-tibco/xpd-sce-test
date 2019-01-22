/**
 * Copyright 2006 TIBCO Software Inc.
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * Static utility method used in this package. This is internal to xpdl2.impl
 * and should not be used outslide this package.
 * 
 * @author Wojciech Zurek (wzurek@tibco.com)
 */
/* package */class Xpdl2InternalUtil {

    /**
     * Find list of start activities in the flowContainer
     * 
     * @param flowContainer
     * @return
     */
    /* package */static EList findStartActivity(FlowContainer flowContainer) {
        List res = new ArrayList();
        for (Iterator iter = flowContainer.getActivities().iterator(); iter
                .hasNext();) {
            Activity act = (Activity) iter.next();
            // find transition that ends in the activity
            Object obj = EMFSearchUtil.findInList(flowContainer
                    .getTransitions(), Xpdl2Package.eINSTANCE
                    .getTransition_To(), act.getId());
            if (obj == null) {
                // if there is no such transition, it is start activity
                // (one of them)!
                res.add(act);
            }
        }
        return new BasicEList.UnmodifiableEList(res.size(), res.toArray());
    }
}
