/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.util;

import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * Utility methods for providing REST service support
 * 
 * @author aallway
 * @since 23 Nov 2012
 */
public class RestServiceUtil {

    /**
     * Identity provider attribute.
     */
    public static final String IDENTITY_PROVIDER_ATTRIBUTE = "IdentityProvider"; //$NON-NLS-1$

    /**
     * Returns REST services for the given process
     * 
     * @param businessProcess
     * @return restServices or empty list if no REST service found in the
     *         process
     */
    public static Collection<Process> getRestServices(Process businessProcess) {
        RESTServices restServices =
                (RESTServices) Xpdl2ModelUtil.getOtherElement(businessProcess,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_RESTServices());

        if (restServices != null) {
            return restServices.getRESTServices();
        }
        return Collections.emptyList();
    }

    /**
     * Get REST service for the given activity
     * 
     * @param activity
     * @return REST service or <code>null</code> if it doesn't exist
     */
    public static Process getRestService(Activity activity) {

        if (activity != null) {
            RESTServices restServices =
                    (RESTServices) Xpdl2ModelUtil.getOtherElement(activity
                            .getProcess(), XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_RESTServices());

            if (restServices != null) {

                for (Process restService : restServices.getRESTServices()) {
                    if (activity.getId()
                            .equals(getActivityIdForRestService(restService))) {
                        return restService;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get ID of the activity for which this REST service has been generated
     * 
     * @param restService
     * 
     * @return activityId
     */
    private static String getActivityIdForRestService(Process restService) {
        String activityId;
        activityId =
                (String) Xpdl2ModelUtil.getOtherAttribute(restService,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_RestActivityId());
        if (activityId != null) {
            return activityId;
        }
        return null;
    }

    /**
     * Checks whether the activity is published as a REST service
     * 
     * @param activity
     * @return true if its a REST activity, false otherwise
     */
    public static boolean isRESTfulActivity(Activity activity) {

        boolean isRestActivity = false;
        isRestActivity =
                Xpdl2ModelUtil.getOtherAttributeAsBoolean(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PublishAsRestService());
        return isRestActivity;
    }

    /**
     * Creates REST service name for the given activity, i.e., <Process
     * Name>_<Activity Name>
     * 
     * @param activity
     * @return restServiceName
     */
    public static String getRestServiceName(Activity activity) {
        String restServiceName = null;

        restServiceName =
                activity.getProcess().getName() + "_" + activity.getName(); //$NON-NLS-1$

        return restServiceName;
    }

    /**
     * Returns REST service module name which is workspace relevant path of the
     * process package
     * 
     * @param activity
     * @return REST service module name
     */
    public static String getRestServiceModuleName(Activity activity) {
        return WorkingCopyUtil.getFile(activity).getFullPath().toString();

    }

    /**
     * Get REST service for the given activityId and its parent process
     * 
     * @param activityId
     * @param process
     * 
     * @return REST service or <code>null</code> if it doesn't exist
     * 
     */
    public static Process getRestService(String activityid, Process process) {
        if (!activityid.isEmpty() && process != null) {

            RESTServices restServices =
                    (RESTServices) Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RESTServices());

            if (restServices != null) {

                for (Process restService : restServices.getRESTServices()) {
                    if (activityid
                            .equals(getActivityIdForRestService(restService))) {
                        return restService;
                    }
                }
            }
        }
        return null;
    }

}
