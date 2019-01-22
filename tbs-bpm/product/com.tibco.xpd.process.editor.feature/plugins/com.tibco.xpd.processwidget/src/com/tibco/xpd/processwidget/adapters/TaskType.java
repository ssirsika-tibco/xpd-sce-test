/*
 **
 **  MODULE:             $RCSfile: $
 **                      $Revision: $
 **                      $Date: $
 **
 **  ENVIRONMENT:  Java - Platform independent
 **
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved.
 **
 **  MODIFICATION HISTORY:
 **
 **    $Log: $
 **
 */

package com.tibco.xpd.processwidget.adapters;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.internal.Messages;

public final class TaskType {
    public static final int NONE = 0;

    public static final int SERVICE = 1;

    public static final int USER = 2;

    public static final int MANUAL = 3;

    public static final int RECEIVE = 4;

    public static final int REFERENCE = 5;

    public static final int SCRIPT = 6;

    public static final int SEND = 7;

    public static final int SUBPROCESS = 8;

    /**
     * NOTE:For most situations use isEmbeddedSubProcess not the literal here.
     * As most situations require equivalent handling for both EVENT sub-process
     * AND EMBEDDED sub-process.
     */
    public static final int EMBEDDED_SUBPROCESS = 9;

    public static final int DTABLE = 10;

    /**
     * NOTE:For most situations use isEmbeddedSubProcess not the literal here.
     * As most situations require equivalent handling for both EVENT sub-process
     * AND EMBEDDED sub-process.
     */
    public static final int EVENT_SUBPROCESS = 11;

    public static final TaskType NONE_LITERAL = new TaskType(NONE);

    public static final TaskType SERVICE_LITERAL = new TaskType(SERVICE);

    public static final TaskType USER_LITERAL = new TaskType(USER);

    public static final TaskType MANUAL_LITERAL = new TaskType(MANUAL);

    public static final TaskType RECEIVE_LITERAL = new TaskType(RECEIVE);

    public static final TaskType REFERENCE_LITERAL = new TaskType(REFERENCE);

    public static final TaskType SCRIPT_LITERAL = new TaskType(SCRIPT);

    public static final TaskType SEND_LITERAL = new TaskType(SEND);

    public static final TaskType SUBPROCESS_LITERAL = new TaskType(SUBPROCESS);

    /**
     * NOTE:For most situations use isEmbeddedSubProcess not the literal here.
     * As most situations require equivalent handling for both EVENT sub-process
     * AND EMBEDDED sub-process.
     */
    public static final TaskType EMBEDDED_SUBPROCESS_LITERAL = new TaskType(
            EMBEDDED_SUBPROCESS);

    /**
     * NOTE:For most situations use isEmbeddedSubProcess not the literal here.
     * As most situations require equivalent handling for both EVENT sub-process
     * AND EMBEDDED sub-process.
     */
    public static final TaskType EVENT_SUBPROCESS_LITERAL = new TaskType(
            EVENT_SUBPROCESS);

    public static final TaskType DTABLE_LITERAL = new TaskType(DTABLE);

    public static final TaskType[] types = new TaskType[] { NONE_LITERAL,
            SERVICE_LITERAL, USER_LITERAL, MANUAL_LITERAL, RECEIVE_LITERAL,
            REFERENCE_LITERAL, SCRIPT_LITERAL, SEND_LITERAL,
            SUBPROCESS_LITERAL, EMBEDDED_SUBPROCESS_LITERAL, DTABLE_LITERAL,
            EVENT_SUBPROCESS_LITERAL };

    private final int type;

    private final String name;

    private TaskType(int type) {
        this.type = type;
        switch (type) {
        case NONE:
            name = Messages.TaskType_Task_label;
            break;
        case SERVICE:
            name = Messages.TaskType_ServiceTask_label;
            break;
        case MANUAL:
            name = Messages.TaskType_ManualTask_label;
            break;
        case RECEIVE:
            name = Messages.TaskType_ReceiveTask_label;
            break;
        case REFERENCE:
            name = Messages.TaskType_ReferenceTask_label;
            break;
        case SCRIPT:
            name = Messages.TaskType_ScriptTask_label;
            break;
        case SEND:
            name = Messages.TaskType_SendTask_label;
            break;
        case USER:
            name = Messages.TaskType_UserTask_label;
            break;
        case SUBPROCESS:
            name = Messages.TaskType_IndiSubProc_label2;
            break;
        case EMBEDDED_SUBPROCESS:
            name = Messages.TaskType_EmbSubProc_label;
            break;
        case EVENT_SUBPROCESS:
            name = Messages.TaskType_EventSubProc_label;
            break;
        case DTABLE:
            name = Messages.TaskType_DTable_label;
            break;
        default:
            throw new IllegalArgumentException(String.valueOf(type));
        }
    }

    public int getValue() {
        return type;
    }

    /**
     * @return true if the task type is sub-process or embedded sub-process or
     *         event sub-process.
     */
    public boolean isEmbeddedSubProcessType() {
        if (type == EMBEDDED_SUBPROCESS || type == EVENT_SUBPROCESS) {
            return true;
        }

        return false;
    }

    /**
     * @return true if the task type is sub-process or embedded sub-process or
     *         event sub-process.
     */
    public boolean isSubProcessType() {
        if (type == SUBPROCESS || isEmbeddedSubProcessType()) {
            return true;
        }

        return false;
    }

    /**
     * Returns the default activity name text for the given type.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this task type.
     * 
     * @return
     */
    public String getGetDefaultFillColorId() {
        String colorID = null;

        switch (getValue()) {
        case TaskType.SUBPROCESS:
            colorID = ProcessWidgetColors.SUBPROCESS_TASK_FILL;
            break;
        case TaskType.EMBEDDED_SUBPROCESS:
            colorID = ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_FILL;
            break;
        case TaskType.EVENT_SUBPROCESS:
            colorID = ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_FILL;
            break;
        case TaskType.MANUAL:
            colorID = ProcessWidgetColors.MANUAL_TASK_FILL;
            break;
        case TaskType.RECEIVE:
            colorID = ProcessWidgetColors.RECEIVE_TASK_FILL;
            break;
        case TaskType.REFERENCE:
            colorID = ProcessWidgetColors.REFERENCE_TASK_FILL;
            break;
        case TaskType.SCRIPT:
            colorID = ProcessWidgetColors.SCRIPT_TASK_FILL;
            break;
        case TaskType.SEND:
            colorID = ProcessWidgetColors.SEND_TASK_FILL;
            break;
        case TaskType.SERVICE:
            colorID = ProcessWidgetColors.SERVICE_TASK_FILL;
            break;
        case TaskType.USER:
            colorID = ProcessWidgetColors.USER_TASK_FILL;
            break;
        case TaskType.DTABLE:
            colorID = ProcessWidgetColors.DTABLE_TASK_FILL;
            break;
        case TaskType.NONE:
        default:
            colorID = ProcessWidgetColors.TASK_FILL;
        }

        return colorID;
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this task type.
     * 
     * @return
     */
    public String getGetDefaultLineColorId() {
        String colorID = null;

        switch (getValue()) {
        case TaskType.SUBPROCESS:
            colorID = ProcessWidgetColors.SUBPROCESS_TASK_LINE;
            break;
        case TaskType.EMBEDDED_SUBPROCESS:
            colorID = ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_LINE;
            break;
        case TaskType.EVENT_SUBPROCESS:
            colorID = ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_LINE;
            break;
        case TaskType.MANUAL:
            colorID = ProcessWidgetColors.MANUAL_TASK_LINE;
            break;
        case TaskType.RECEIVE:
            colorID = ProcessWidgetColors.RECEIVE_TASK_LINE;
            break;
        case TaskType.REFERENCE:
            colorID = ProcessWidgetColors.REFERENCE_TASK_LINE;
            break;
        case TaskType.SCRIPT:
            colorID = ProcessWidgetColors.SCRIPT_TASK_LINE;
            break;
        case TaskType.SEND:
            colorID = ProcessWidgetColors.SEND_TASK_LINE;
            break;
        case TaskType.SERVICE:
            colorID = ProcessWidgetColors.SERVICE_TASK_LINE;
            break;
        case TaskType.USER:
            colorID = ProcessWidgetColors.USER_TASK_LINE;
            break;
        case TaskType.DTABLE:
            colorID = ProcessWidgetColors.DTABLE_TASK_LINE;
            break;
        case TaskType.NONE:
        default:
            colorID = ProcessWidgetColors.TASK_LINE;
        }

        return colorID;
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this task type.
     * 
     * @return
     */
    public ProcessEditorObjectType getProcessEditorObjectType() {
        ProcessEditorObjectType objectType = null;

        switch (getValue()) {
        case TaskType.SUBPROCESS:
            objectType = ProcessEditorObjectType.task_subprocess;
            break;
        case TaskType.EMBEDDED_SUBPROCESS:
            objectType = ProcessEditorObjectType.embedded_subprocess;
            break;
        case TaskType.EVENT_SUBPROCESS:
            objectType = ProcessEditorObjectType.event_subprocess;
            break;
        case TaskType.MANUAL:
            objectType = ProcessEditorObjectType.task_manual;
            break;
        case TaskType.RECEIVE:
            objectType = ProcessEditorObjectType.task_receive;
            break;
        case TaskType.REFERENCE:
            objectType = ProcessEditorObjectType.task_reference;
            break;
        case TaskType.SCRIPT:
            objectType = ProcessEditorObjectType.task_script;
            break;
        case TaskType.SEND:
            objectType = ProcessEditorObjectType.task_send;
            break;
        case TaskType.SERVICE:
            objectType = ProcessEditorObjectType.task_service;
            break;
        case TaskType.USER:
            objectType = ProcessEditorObjectType.task_user;
            break;
        case TaskType.DTABLE:
            objectType = ProcessEditorObjectType.task_decisiontable;
            break;
        case TaskType.NONE:
            objectType = ProcessEditorObjectType.task_none;
            break;
        }

        return objectType;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + type;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TaskType other = (TaskType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
