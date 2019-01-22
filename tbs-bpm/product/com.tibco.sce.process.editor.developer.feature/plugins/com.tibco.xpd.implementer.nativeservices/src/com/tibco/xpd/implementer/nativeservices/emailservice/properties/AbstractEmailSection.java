/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Abstract class that defines some commonly used methods for the email sections
 * - both in the general tab and the email tab.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractEmailSection extends
        AbstractFilteredTransactionalSection {

    protected static final int TEXT_WIDTH_HINT = NativeServicesConsts.TEXT_WIDTH_HINT;

    /**
     * Abstract email section class that defines common methods for the general
     * and email tabs
     * 
     * @param eclass
     */
    public AbstractEmailSection(EClass eclass) {
        super(eclass);

    }

    /**
     * Get the <code>TaskService</code> input
     * 
     * @return
     */
    protected TaskService getTaskServiceInput() {
        TaskService service = null;
        EObject input = getInput();

        if (input != null && input instanceof Activity) {
            Activity activity = (Activity) input;

            Task task = (Task) activity.getImplementation();

            if (task != null) {
                service = task.getTaskService();
            }
        }

        return service;
    }

    /**
     * Get the <code>Activity</code> input
     * 
     * @return
     */
    protected Activity getActivityInput() {
        EObject input = getInput();

        if (input != null && input instanceof Activity) {
            return (Activity) input;
        }

        return null;
    }

}
