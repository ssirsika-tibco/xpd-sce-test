/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.UserTask;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contributions that checks if the {@link UserTask} has Work distribution
 * strategy and if it does not have then sets the Work distribution strategy to
 * "Offer To All"
 * 
 * 
 * @author kthombar
 * @since 16-Jul-2014
 */
public class UserTaskWorkAllocationStrategyContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        for (Process eachProcess : processes) {
            /* get all activities in process */
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

            for (Activity activity : allActivitiesInProc) {

                Implementation impl = activity.getImplementation();

                if (impl instanceof Task) {

                    Task task = (Task) impl;

                    if (task.getTaskUser() != null) {
                        /* we are interested only in user tasks. */
                        setUserTaskAllocationStrategy(activity);
                    }
                }
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Checks if the {@link UserTask} passed has no Work distribution strategy
     * set then creates a "Offer to All" work distribution stretegy for the user
     * task.
     * 
     * @param activity
     *            the {@link UserTask} whose allocation strategy is to be set
     */
    private void setUserTaskAllocationStrategy(Activity activity) {

        ActivityResourcePatterns patterns = null;
        AllocationStrategy allocationStrategy = null;

        EStructuralFeature feature =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ActivityResourcePatterns();

        Object resourcePattern =
                Xpdl2ModelUtil.getOtherElement(activity, feature);

        if (resourcePattern instanceof ActivityResourcePatterns) {

            patterns = (ActivityResourcePatterns) resourcePattern;

            allocationStrategy = patterns.getAllocationStrategy();

            if (allocationStrategy == null) {
                /*
                 * If the allocation strategy is null then create a Allocation
                 * strategy
                 */
                allocationStrategy =
                        XpdExtensionFactory.eINSTANCE
                                .createAllocationStrategy();

                patterns.setAllocationStrategy(allocationStrategy);
            }
        } else {
            /*
             * If the user task does not have Resource pattern then create a
             * Resource pattern and allocation strategy
             */
            patterns =
                    XpdExtensionFactory.eINSTANCE
                            .createActivityResourcePatterns();

            allocationStrategy =
                    XpdExtensionFactory.eINSTANCE.createAllocationStrategy();

            patterns.setAllocationStrategy(allocationStrategy);

            Xpdl2ModelUtil.setOtherElement(activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns(),
                    patterns);
        }

        if (allocationStrategy != null && allocationStrategy.getOffer() == null) {
            /* Set allocation strategy == Offer to All */
            allocationStrategy.setOffer(AllocationType.OFFER_ALL);
        }
    }
}
