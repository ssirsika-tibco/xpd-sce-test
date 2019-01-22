/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This contribution checks for overlength gateway names (specifically) and
 * truncates them to 58 characters.
 * 
 * @author sajain
 * @since Jul 16, 2014
 */
public class GatewayNameOutOfBoundsContribution extends
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

        try {

            monitor.beginTask("", processes.size()); //$NON-NLS-1$

            for (Process eachProcess : processes) {

                Collection<Activity> allProcessActivities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

                /*
                 * Process only if there's at least one activity.
                 */
                if (allProcessActivities != null
                        && !allProcessActivities.isEmpty()) {

                    /*
                     * Handle out of bound gateway names by truncating all the
                     * characters following the 58th character.
                     */
                    handleOutOfBoundGatewayNames(allProcessActivities);
                }

            }

            monitor.worked(1);

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Handle out of bound gateway names by truncating all the characters
     * following the 58th character.
     * 
     * @param allProcessActivities
     *            List of activities and methods in processes.
     */
    private void handleOutOfBoundGatewayNames(
            Collection<Activity> allProcessActivities) {

        /*
         * Process all activities.
         */
        for (Activity eachActivity : allProcessActivities) {

            /*
             * Check if the activity currently being processed is a gateway and
             * then only proceed.
             */
            if (null != GatewayObjectUtil.getGatewayType(eachActivity)) {

                /*
                 * Process the name of the specified activity and truncate it if
                 * it's length exceeds 58.
                 */
                processGatewayName(eachActivity);
            }
        }
    }

    /**
     * Process the name of the specified activity and truncate it if it's length
     * exceeds 58.
     * 
     * @param act
     *            Activity to be processed.
     */
    private void processGatewayName(NamedElement act) {

        /*
         * Fetch activity name.
         */
        String name = act.getName();

        /*
         * Check if it's length exceeds 58 characters.
         */
        if (null != name && name.length() > 58) {

            /*
             * Truncate activity name.
             */
            name = getTruncatedString(name);
        }

        /*
         * Set it to activity.
         */
        act.setName(name);
    }

    /**
     * Truncate all characters following the 58th character and return the
     * truncated string.
     * <p>
     * If truncated string ends with whitespace(s) then this method would remove
     * them as a label cannot end with a whitespace.
     * <p>
     * 
     * @param str
     *            String to be truncated.
     * 
     * @return Return truncated string after truncating all the characters
     *         following the 58th character.
     */
    private String getTruncatedString(String str) {

        String truncatedStr = null;

        /*
         * Truncate the characters after 58th character.
         */
        if (str.length() > 58) {

            truncatedStr = str.substring(0, 57);

            /*
             * If truncated string ends with whitespace(s) then remove them as a
             * label cannot end with a whitespace.
             */
            truncatedStr = truncatedStr.trim();
        }

        return truncatedStr;
    }
}
