/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Filter implementation for Local Signal Catch Data Mapper.
 *
 * @author sajain
 * @since Jul 15, 2019
 */
public class LocalSignalCatchDataMapperFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
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
            eo = ((IAdaptable) toTest).getAdapter(EObject.class);
        }

        if (eo != null && eo instanceof Activity) {
            Activity activity = (Activity) eo;

            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil.getEventTriggerType(activity))) {

                if (EventObjectUtil.isLocalSignalEvent(activity)) {
                    return true;
                }
            }
        }

        return false;
    }
}
