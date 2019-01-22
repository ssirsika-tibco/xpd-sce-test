/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.preprocessors;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Analyses REST activities.
 * 
 * @author nwilson
 * @since 21 May 2015
 */
public class RestPreProcessor implements IPreProcessor {

    boolean hasRestInvocation = false;

    /**
     * @param pckg
     *            The package to analyse.
     */
    public RestPreProcessor(Process process) {
        analysePackage(process);
    }

    /**
     * @param pckg
     */
    private void analysePackage(Process process) {
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            analyseActivity(activity);
        }
    }

    /**
     * @param activity
     */
    private void analyseActivity(Activity activity) {
        if (!hasRestInvocation) {
            OtherAttributesContainer oac = null;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                TaskService service = task.getTaskService();
                if (service != null) {
                    oac = service;
                }
                TaskSend send = task.getTaskSend();
                if (send != null) {
                    oac = send;
                }
            }
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                oac = event;
            }
            if (oac != null) {
                Object oa =
                        Xpdl2ModelUtil.getOtherAttribute(oac,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
                if ("RESTService".equals(oa)) { //$NON-NLS-1$
                    hasRestInvocation = true;
                }
            }
        }
    }

    /**
     * @return
     */
    public boolean hasRestServiceInvocation() {
        return hasRestInvocation;
    }

}
