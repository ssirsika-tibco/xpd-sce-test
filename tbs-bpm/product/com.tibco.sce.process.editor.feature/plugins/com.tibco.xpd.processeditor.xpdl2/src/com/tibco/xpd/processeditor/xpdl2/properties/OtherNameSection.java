/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * @author NWilson
 * 
 */
public class OtherNameSection extends NamedElementSection {
    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        boolean select = super.select(toTest);
        Object item = getBaseSelectObject(toTest);
        if (item instanceof ProcessRelevantData || item instanceof Process
                || item instanceof Participant
                || item instanceof ProcessInterface
                || item instanceof InterfaceMethod
                || item instanceof TypeDeclaration || isEvent(item)
                || item instanceof Artifact || isGateway(item) || isTask(item)
                || isBlockActivity(item) || item instanceof Transition) {
            select = false;
        }
        return select;
    }

    private boolean isGateway(Object item) {
        boolean isGateway = false;
        if (item instanceof Activity) {
            Activity activity = (Activity) item;
            if (activity.getRoute() != null) {
                isGateway = true;
            }
        }
        return isGateway;
    }

    private boolean isEvent(Object item) {
        boolean isEvent = false;
        if (item instanceof Activity) {
            Activity activity = (Activity) item;
            if (activity.getEvent() != null) {
                isEvent = true;
            }
        }
        return isEvent;
    }

    private boolean isTask(Object item) {
        boolean isTask = false;
        if (item instanceof Activity) {
            Activity activity = (Activity) item;
            if (activity.getImplementation() != null) {
                isTask = true;
            }
        }
        return isTask;
    }

    private boolean isBlockActivity(Object item) {
        boolean isBlockActivity = false;
        if (item instanceof Activity) {
            Activity activity = (Activity) item;
            if (activity.getBlockActivity() != null) {
                isBlockActivity = true;
            }
        }
        return isBlockActivity;
    }

}
