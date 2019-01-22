/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * 
 * Representation of the task implementation template element. This will provide
 * all information set by the extension of the taskImplementation extension
 * point
 * <p>
 * Note that the extension point was originally in processwidget but has been
 * deprecated and moved here to processeditor. We still support contributions to
 * the deprecated processwidget extension point so must handle both.
 * 
 * @author njpatel
 */
public class TaskImplementationElement {

    public static final String ATT_ID = "id"; //$NON-NLS-1$

    public static final String ATT_NAME = "name"; //$NON-NLS-1$

    public static final String ATT_IMPLEMENTATIONTYPE = "implementationType"; //$NON-NLS-1$

    public static final String ATT_TASKTYPE = "taskType"; //$NON-NLS-1$

    public static final String ATT_CLASS = "class"; //$NON-NLS-1$

    public static final String ATT_PRIORITY = "priority"; //$NON-NLS-1$

    public static final String ATT_DEFAULT = "default"; //$NON-NLS-1$

    private static final String ATT_SHOW_FOR_PAGEFLOW = "showForPageflow"; //$NON-NLS-1$

    private IConfigurationElement configElement = null;

    private ISection iSectionClass = null;

    private TaskType taskType = null;

    private String id;

    /**
     * Default constructor
     * 
     * @param configElement
     */
    public TaskImplementationElement(IConfigurationElement configElement) {
        this.configElement = configElement;
        id = getAttribute(ATT_ID);
    }

    /**
     * Get the extension configuration element
     * 
     * @return
     */
    public IConfigurationElement getConfigElement() {
        return configElement;
    }

    /**
     * Get the <i>name</i> set in this extension
     * 
     * @return Extension name
     */
    public String getName() {
        return getAttribute(ATT_NAME);
    }

    /**
     * Get the <i>taskType</i> set in this extension
     * 
     * @return selected type <code>TaskType</code>
     */
    public TaskType getTaskType() {

        if (taskType == null) {

            String task = getAttribute(ATT_TASKTYPE);

            if (task != null) {
                if (task.equals(TaskConfigType.TYPE_USER)) {
                    taskType = TaskType.USER_LITERAL;
                } else if (task.equals(TaskConfigType.TYPE_SERVICE)) {
                    taskType = TaskType.SERVICE_LITERAL;
                } else if (task.equals(TaskConfigType.TYPE_SEND)) {
                    taskType = TaskType.SEND_LITERAL;
                } else if (task.equals(TaskConfigType.TYPE_RECEIVE)) {
                    taskType = TaskType.RECEIVE_LITERAL;
                } else if (task.equals(TaskConfigType.TYPE_SCRIPT)) {
                    taskType = TaskType.SCRIPT_LITERAL;
                }
            }
        }

        return taskType;
    }

    /**
     * Get the <i>priority</i> set in the extension
     * 
     * @return Priority int value
     */
    public int getPriority() {
        int ret = 0;
        String value = getAttribute(ATT_PRIORITY);

        if (value != null) {
            try {
                ret = Integer.parseInt(value);

            } catch (NumberFormatException e) {
                // Do nothing - return priority as 0
            }
        }

        return ret;
    }

    public boolean getDefault() {
        boolean ret = false;
        String value = getAttribute(ATT_DEFAULT);

        if (value != null) {
            ret = Boolean.parseBoolean(value);
        }

        return ret;
    }

    /**
     * Get the <i>class</i> set in the extension
     * 
     * @return <code>ISection</code> object defined in the extension
     * @throws CoreException
     */
    public ISection getISectionExec() throws CoreException {

        if (configElement != null && iSectionClass == null) {
            iSectionClass =
                    (ISection) configElement
                            .createExecutableExtension(ATT_CLASS);
        }

        return iSectionClass;
    }

    /**
     * Get the given attribute from the configuration element
     * 
     * @param attr
     * @return Attribute
     */
    private String getAttribute(String attr) {
        if (configElement != null) {
            return configElement.getAttribute(attr);
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public String getImplementationType() {
        return getAttribute(ATT_IMPLEMENTATIONTYPE);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result =
                PRIME * result + ((taskType == null) ? 0 : taskType.hashCode());
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
        final TaskImplementationElement other = (TaskImplementationElement) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (taskType == null) {
            if (other.taskType != null)
                return false;
        } else if (!taskType.equals(other.taskType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("["); //$NON-NLS-1$
        sb.append(id);
        sb.append(","); //$NON-NLS-1$
        sb.append(taskType.toString());
        sb.append("]"); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * @return
     */
    public boolean getShowForPageflow() {
        return Boolean.parseBoolean(getAttribute(ATT_SHOW_FOR_PAGEFLOW));
    }
}
