/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.commands;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;

/**
 * @author aallway
 *
 */
public class ProcessWidgetCommandUtil {

    /**
     * Given a model object return the associated process widget adapter.
     * @param modelObject
     * @return
     */
    private static BaseProcessAdapter getProcessWidgetAdapter(EObject modelObject) {
        EList adps = modelObject.eAdapters();
        for (Iterator iterator = adps.iterator(); iterator.hasNext();) {
            Object adp = (Object) iterator.next();
            
            if (adp instanceof BaseProcessAdapter) {
                return (BaseProcessAdapter)adp;
            }
        }

        throw new IllegalArgumentException(modelObject+": modelObject does not have a Process Widget Adapter."); //$NON-NLS-1$
        
    }

    
    public static SequenceFlowAdapter getSequenceFlowAdapter(EObject modelObject) {
        Adapter adp = getProcessWidgetAdapter(modelObject);

        if ((adp instanceof SequenceFlowAdapter)) {
            return (SequenceFlowAdapter)adp;
        }
        return null;
    }

    public static EventAdapter getEventAdapter(EObject modelObject) {
        Adapter adp = getProcessWidgetAdapter(modelObject);

        if ((adp instanceof EventAdapter)) {
            return (EventAdapter)adp;
        }
        return null;
    }

    public static TaskAdapter getTaskAdapter(EObject modelObject) {
        Adapter adp = getProcessWidgetAdapter(modelObject);

        if ((adp instanceof TaskAdapter)) {
            return (TaskAdapter)adp;
        }
        return null;
    }
    
    public static GatewayAdapter getGatewayAdapter(EObject modelObject) {
        Adapter adp = getProcessWidgetAdapter(modelObject);

        if ((adp instanceof GatewayAdapter)) {
            return (GatewayAdapter)adp;
        }
        return null;
    }
}
