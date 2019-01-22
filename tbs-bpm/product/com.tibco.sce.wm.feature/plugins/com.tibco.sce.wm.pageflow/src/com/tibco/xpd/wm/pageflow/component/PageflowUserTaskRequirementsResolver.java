/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.brm.component.BusinessProcessUserTaskRequirementsResolver;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class PageflowUserTaskRequirementsResolver extends
        BusinessProcessUserTaskRequirementsResolver {

    private static final String UPPER_RANGE = "2.0.0"; //$NON-NLS-1$

    private static final String LOWER_RANGE = "1.0.0"; //$NON-NLS-1$

    private static final String COM_TIBCO_N2_PAGE_ACTIVITY_MODEL =
            "com.tibco.n2.pageactivity.model"; //$NON-NLS-1$

    /** N2 Global destination ID. */
    private static final String N2_GLOBAL_DESTINATION_ID =
            "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

    @Override
    protected Collection<Activity> getManualActivities(Package xpdlPackage) {
        Collection<Activity> manualPageFlowActivities = null;
        EList<Process> processes = xpdlPackage.getProcesses();
        for (Process eachProcess : processes) {
            if (!GlobalDestinationHelper
                    .isGlobalDestinationEnabled(eachProcess,
                            N2_GLOBAL_DESTINATION_ID)) {
                continue;
            }
            if (!Xpdl2ModelUtil.isPageflow(eachProcess)) {
                continue;
            }
            manualPageFlowActivities =
                    BRMUtils.getManualPageFlowActivities(eachProcess);
            if (manualPageFlowActivities != null
                    && !manualPageFlowActivities.isEmpty()) {
                break;
            }
        }
        return manualPageFlowActivities;

    }

    protected String getModelPlugInName() {
        return COM_TIBCO_N2_PAGE_ACTIVITY_MODEL;
    }

    protected String getModelPlugLowerRange() {
        return LOWER_RANGE;
    }

    protected String getModelPlugUpperRange() {
        return UPPER_RANGE;
    }
}
