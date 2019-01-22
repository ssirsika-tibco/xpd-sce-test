/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.extension.email.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.bx.extension.email.converter.EmailConverterActivator;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 17 Nov 2010
 */
public class EmailCatchableErrorProvider implements
        IBpmnCatchableErrorsContributor {

    public static final String ERRORCODE_EMAIL_SERVER_UNAVAILABLE =
            "EMAIL_SERVER_UNAVAILABLE";

    public static final String ERRORCODE_EMAIL_ATTACHMENT_FILE_NOT_FOUND =
            "EMAIL_ATTACHMENT_FILE_NOT_FOUND";

    public static final String ERRORCODE_EMAIL_INVALID_FROM_ADDRESS =
            "EMAIL_INVALID_FROM_ADDRESS";

    public static final String ERRORCODE_EMAIL_UNKNOWN_RECIPIENT =
            "EMAIL_UNKNOWN_RECIPIENT";

    public static final String ERRORCODE_EMAIL_SEND_COMMUNICATION_ERROR =
            "EMAIL_SEND_COMMUNICATION_ERROR";

    public static final String ERRORCODE_EMAIL_UNKNOWN_HOST =
            "EMAIL_UNKNOWN_HOST";

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#isApplicableErrorThrower(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     * 
     * @return true for email service task.
     */
    @Override
    public boolean isApplicableErrorThrower(Activity errorTask) {
        /* We are only applicable to email service tasks. */
        if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(errorTask))) {
            if (ActionUtil.EMAIL_SERVICE.equals(TaskObjectUtil
                    .getTaskImplementationExtensionId(errorTask))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getCatchableErrorTreeItems(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     * @return
     */
    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorTask) {

        /*
         * It is usual to wrap the errors for a particular task in a folder (so
         * for instance if you look at catching WSDL message faults on a web
         * service task, a folder is created to contain the 1 or more faults
         * possible for the selected operation.
         * 
         * As error catching can be via nested sub-processes (something you
         * don’t have to worry about in your contribution!), the task folder
         * helps the user when selecting errors for a sub-process task that
         * contains many other error-throwing tasks).
         */
        String taskFolderName =
                Xpdl2ModelUtil.getDisplayNameOrName(errorTask) + "<"
                        + TaskObjectUtil.getTaskType(errorTask).toString()
                        + ">";

        Image taskFolderImage = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            taskFolderImage =
                    EmailConverterActivator.getDefault().getImageRegistry()
                            .get(EmailConverterActivator.ICON_EMAIL_TASK);
        }
        DiagramDropObjectUtils.getImageForTaskType(TaskObjectUtil
                .getTaskType(errorTask));

        BpmnCatchableErrorFolder taskFolder =
                new BpmnCatchableErrorFolder(taskFolderName, taskFolderImage);

        /*
         * As an example I will show the addition of 2 statically defined error
         * codes
         * 
         * Not sure if this is similar to your requirement or whether the error
         * codes will be dynamic bnased on the configuration of the email task.
         */

        /*
         * In our case the error thrower is always the errorTask itself, the
         * errorThrower will be passed BACk to us for methods like getErrorImage
         * getErrorThrowerId
         */
        BpmnCatchableError error1 =
                new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        ERRORCODE_EMAIL_SERVER_UNAVAILABLE, this);
        taskFolder.addChild(error1);

        BpmnCatchableError error2 =
                new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        ERRORCODE_EMAIL_ATTACHMENT_FILE_NOT_FOUND, this);
        taskFolder.addChild(error2);

        BpmnCatchableError error3 =
                new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        ERRORCODE_EMAIL_SEND_COMMUNICATION_ERROR, this);
        taskFolder.addChild(error3);

        BpmnCatchableError error4 =
                new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        ERRORCODE_EMAIL_UNKNOWN_HOST, this);
        taskFolder.addChild(error4);

        BpmnCatchableError error5 =
                new BpmnCatchableError(errorTask,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        ERRORCODE_EMAIL_UNKNOWN_RECIPIENT, this);
        taskFolder.addChild(error5);

        /*
         * Return the task folder containing our specific errors.
         */
        List<IBpmnCatchableErrorTreeItem> items =
                new ArrayList<IBpmnCatchableErrorTreeItem>();
        items.add(taskFolder);

        return items;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorImage(java.lang.Object,
     *      java.lang.String)
     * 
     * @param errorThrower
     * @param errorId
     * @return
     */
    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        /*
         * The errorThrower will be the errorTask we used to create the
         * BPmnCatchableError in getCatchableErrorTreeItems()
         * 
         * The errorId is the errorCode which the BpmnCatchableError was
         * constructed with.
         */
        /*
         * if (ERRORCODE_EMAIL_AUTHENTIFICATION_ERROR.equals(errorId)) { return
         * EmailConverterActivator.getDefault().getImageRegistry()
         * .get(EmailConverterActivator.ICON_AUTHENTIFICATION_ERROR);
         * 
         * } else if (ERRORCODE_EMAIL_SERVER_UNAVAILABLE.equals(errorId)) {
         * return EmailConverterActivator.getDefault().getImageRegistry()
         * .get(EmailConverterActivator.ICON_SERVER_UNAVAILABLE); }
         */

        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerContainerId(java.lang.Object)
     * 
     * @param errorThrower
     * 
     * @return The container Id is used in the XPDL model and uniquely
     *         identifies the container of the errorThrower - so for instance
     *         when error is catught several levels of sub-process above the
     *         actual thrower, the error throewer can still be found in the
     *         sub-process invocation hierarchy)
     */
    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        /*
         * The errorThrower will be the errorTask we used to create the
         * BPmnCatchableError in getCatchableErrorTreeItems()
         */
        Process process = ((Activity) errorThrower).getProcess();
        if (process != null) {
            return process.getId();
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerId(java.lang.Object)
     * 
     * @param errorThrower
     * 
     * @return The unique id of the thing (activity in our case) that throws the
     *         error).
     */
    @Override
    public String getErrorThrowerId(Object errorThrower) {
        /*
         * The errorThrower will be the errorTask we used to create the
         * BPmnCatchableError in getCatchableErrorTreeItems()
         */
        return ((Activity) errorThrower).getId();

    }

}
