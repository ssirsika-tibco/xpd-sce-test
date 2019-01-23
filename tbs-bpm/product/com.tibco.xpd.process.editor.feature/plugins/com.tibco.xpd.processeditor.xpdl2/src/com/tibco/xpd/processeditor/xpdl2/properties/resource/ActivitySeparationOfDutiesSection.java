/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author bharge
 *
 */
public class ActivitySeparationOfDutiesSection extends
        AbstractSeparationOfDutiesSection {

    public ActivitySeparationOfDutiesSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param toTest
     * @return
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        boolean ok = false;
        if (toTest instanceof Activity) {
            Activity activity = (Activity) toTest;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null || task.getTaskManual() != null) {
                    ok = true;
                }
            }
        } 
        return ok;
    }

}
