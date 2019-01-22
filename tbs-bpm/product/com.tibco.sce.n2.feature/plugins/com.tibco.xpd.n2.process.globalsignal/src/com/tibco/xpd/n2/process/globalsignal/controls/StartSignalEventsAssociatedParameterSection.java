/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.controls;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.event.ActivityAssociatedParameterSection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.StartEvent;

/**
 * Interface Tab Section that allows the selection of Associated parameters.
 * <p>
 * Note: This will be available only on Global Signal Start events.
 * 
 * @author kthombar
 * @since Feb 23, 2015
 */
public class StartSignalEventsAssociatedParameterSection extends
        ActivityAssociatedParameterSection {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.ActivityAssociatedParameterSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {

            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {

            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }

        if (eo != null && eo instanceof Activity) {

            Activity activity = (Activity) eo;

            Event event = activity.getEvent();
            if (event instanceof StartEvent) {
                /*
                 * Show the interface tab only for Start Global Signal Events
                 */
                return GlobalSignalUtil.isGlobalSignalEvent(activity);
            }
        }
        return false;
    }
}
