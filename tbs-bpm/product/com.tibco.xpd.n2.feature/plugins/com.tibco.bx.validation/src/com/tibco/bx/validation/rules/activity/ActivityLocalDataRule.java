/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. activity local data is supported for embedded sub proc, database task and
 * email task. for all other activities error message is shown
 * 
 * 
 * @author bharge
 * @since 3.3 (4 Aug 2010)
 */
public class ActivityLocalDataRule extends FlowContainerValidationRule {

    private static final String ACTIVITY_LOCAL_DATA_NOT_SUPPORTED =
            "bx.activityLocalDataNotSupported"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            if (!(isEmbeddedSubProc(activity) || isDatabaseTask(activity) || isEmailTask(activity))) {
                if (activity.getDataFields().size() > 0) {
                    addIssue(ACTIVITY_LOCAL_DATA_NOT_SUPPORTED, activity);
                }
            }
        }
    }

    private static boolean isEmbeddedSubProc(Activity activity) {
        if (null != activity.getBlockActivity()) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    public static boolean isDatabaseTask(Activity activity) {
        String implementationType = getImplementationType(activity);
        if (null != implementationType
                && TaskImplementationTypeDefinitions.DATABASE_SERVICE.equals(implementationType)) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    public static boolean isEmailTask(Activity activity) {
        String implementationType = getImplementationType(activity);
        if (null != implementationType
                && TaskImplementationTypeDefinitions.EMAIL_SERVICE.equals(implementationType)) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    private static String getImplementationType(Activity activity) {
        String implementationType = null;
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            TaskService taskService = task.getTaskService();
            if (null != taskService) {
                implementationType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
            }
        }
        return implementationType;
    }
}
