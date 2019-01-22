/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * JUnit test to protect 'Event can have single incoming flow' contribution.
 * 
 * @author sajain
 * @since Jul 14, 2014
 */
public class IpsBpm11_EventCanHaveSingleFlowTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm11_EventCanHaveSingleFlowTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iProcessToAMXBPMConversion.AbstractConversionTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpsBpm11_EventCanHaveSingleFlowTest/Process Packages{processes}/evtCanHaveSingleFlow.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        for (IFile file : convertedXpdls) {
            if ("evtCanHaveSingleFlow.xpdl".equalsIgnoreCase(file.getName())) { //$NON-NLS-1$
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                EObject rootElement = wc.getRootElement();
                if (rootElement instanceof Package) {
                    Package pkg = (Package) rootElement;
                    for (Process eachProcess : pkg.getProcesses()) {
                        if ("evtCanHaveSingleFlow".equalsIgnoreCase(eachProcess //$NON-NLS-1$
                                .getName())) {
                            List<Activity> allProcessActivities =
                                    new ArrayList<Activity>();

                            /*
                             * Fetch all activities from processes.
                             */
                            allProcessActivities.addAll(eachProcess
                                    .getActivities());

                            /*
                             * Fetch all activities from activity sets.
                             */
                            EList<ActivitySet> allActivitySets =
                                    eachProcess.getActivitySets();
                            for (ActivitySet eachActivitySet : allActivitySets) {
                                allProcessActivities.addAll(eachActivitySet
                                        .getActivities());
                            }

                            if (allProcessActivities != null
                                    && !allProcessActivities.isEmpty()) {
                                for (Activity eachProcessActivity : allProcessActivities) {
                                    if (eachProcessActivity != null) {
                                        EventFlowType eventFlowType =
                                                EventObjectUtil
                                                        .getFlowType(eachProcessActivity);
                                        /*
                                         * Check if it's an intermediate or an
                                         * end event.
                                         */
                                        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                                .equals(eventFlowType)
                                                || EventFlowType.FLOW_END_LITERAL
                                                        .equals(eventFlowType)) {
                                            /*
                                             * Check if there are multiple
                                             * transitions for this event.
                                             */
                                            if (hasMultipleIncomingTransitions(eachProcessActivity
                                                    .getId(),
                                                    eachProcess)) {
                                                /*
                                                 * So there are multiple
                                                 * incoming flows, hence test
                                                 * fails.
                                                 */
                                                fail(String
                                                        .format("Event %s with multiple incoming flows detected, there should not be any such event after the conversion!", //$NON-NLS-1$
                                                                eachProcessActivity
                                                                        .getName()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns true if the specified activity has multiple incoming transitions.
     * 
     * @param activityId
     * @param flowContainer
     * @return <code>true</code> if the activity has multiple incoming
     *         transitions, <code>false</code> otherwise.
     */
    private boolean hasMultipleIncomingTransitions(String activityId,
            FlowContainer flowContainer) {
        List<Transition> transitions = new ArrayList<Transition>();

        if (flowContainer != null) {
            for (Iterator<Transition> iter =
                    flowContainer.getTransitions().iterator(); iter.hasNext();) {
                Transition trans = iter.next();

                if (activityId.equals(trans.getTo())) {
                    transitions.add(trans);
                }
            }
        }

        return (transitions.size() > 1);
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // No other resources.
        return null;
    }
}