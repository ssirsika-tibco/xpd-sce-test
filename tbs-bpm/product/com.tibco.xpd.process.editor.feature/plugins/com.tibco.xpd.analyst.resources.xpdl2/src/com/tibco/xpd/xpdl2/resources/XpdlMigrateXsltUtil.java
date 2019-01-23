/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility for the XPDL Migrate xslts.
 * 
 * @author rsomayaj
 * @since 3.3 (13 Aug 2010)
 */
public class XpdlMigrateXsltUtil {

    /**
     * @return unique identifier used in the XSLT, because the XSLT generate-id
     *         doesn't generate unique identifiers
     */
    public static String generateUUID() {
        return EcoreUtil.generateUUID();
    }

    /**
     * @param processId
     * @param activityName
     * 
     * @return Get the Id of the activity with the given name from the given
     *         process.
     */
    public static String getActivityIdByName(String processId,
            String activityName) {
        String activityId = null;

        if (processId != null && processId.trim().length() > 0
                && activityName != null && activityName.trim().length() > 0) {

            Process process =
                    Xpdl2WorkingCopyImpl.locateProcess(processId.trim());
            if (process != null) {
                activityName = activityName.trim();

                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : activities) {
                    if (activityName.equals(activity.getName())) {
                        activityId = activity.getId();
                    }
                }

            }
        }
        return activityId != null ? activityId : ""; //$NON-NLS-1$
    }

    /**
     * Check whether the given formal parameter name exists in the given
     * sub-process.
     * 
     * @param subProcessOrInterfaceId
     * @param subProcessParamName
     * 
     * @return "EXISTS" if the sub-process is found and parameter exists.
     *         "NOT_EXISTS" if the sub-process is found but the parameter does
     *         NOT exist. "SUBPROC_NOT_EXISTS" if the sub-process/process
     *         interface doesn't exists (hasn't been imported yet probably).
     */
    public static String subProcessParamExists(String subProcessOrInterfaceId,
            String subProcessParamName) {

        if (subProcessOrInterfaceId != null) {
            subProcessOrInterfaceId = subProcessOrInterfaceId.trim();

            if (subProcessOrInterfaceId.length() > 0) {
                Process process =
                        Xpdl2WorkingCopyImpl
                                .locateProcess(subProcessOrInterfaceId.trim());

                Collection<FormalParameter> allFormalParameters = null;

                if (process != null) {
                    allFormalParameters =
                            ProcessInterfaceUtil
                                    .getAllFormalParameters(process);
                } else {
                    ProcessInterface processInterface =
                            Xpdl2WorkingCopyImpl
                                    .locateProcessInterface(subProcessOrInterfaceId);
                    if (processInterface != null) {
                        allFormalParameters =
                                processInterface.getFormalParameters();
                    }
                }

                if (allFormalParameters != null) {
                    boolean found = false;
                    if (subProcessParamName != null) {
                        for (FormalParameter parameter : allFormalParameters) {
                            if (subProcessParamName.equals(parameter.getName())) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if (found) {
                        return "EXISTS"; //$NON-NLS-1$
                    } else {
                        return "NOT_EXISTS"; //$NON-NLS-1$
                    }
                }
            }
        }

        return "SUBPROC_NOT_EXISTS"; //$NON-NLS-1$
    }
}
