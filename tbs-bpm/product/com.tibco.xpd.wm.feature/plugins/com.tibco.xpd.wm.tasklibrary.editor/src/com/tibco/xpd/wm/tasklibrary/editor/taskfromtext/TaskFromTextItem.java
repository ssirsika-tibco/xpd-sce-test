/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.taskfromtext;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * Class for paste tasks from text items.
 *
 * @author aallway
 * @since 3.2
 */
public class TaskFromTextItem {
    public enum TaskFromTextItemType {
        TASK, TASKSET
    }

    private String name;

    private boolean selected;

    private TaskFromTextItem.TaskFromTextItemType type;
    
    private TaskType createTaskType = TaskType.USER_LITERAL;
    
    private EObject existingObject = null;

    public TaskFromTextItem(String name, TaskFromTextItem.TaskFromTextItemType type, boolean selected) {
        this.name = name;
        this.type = type;
        this.selected = selected;
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the type
     */
    public TaskFromTextItem.TaskFromTextItemType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(TaskFromTextItem.TaskFromTextItemType type) {
        this.type = type;
    }


    /**
     * @return the existingObject
     */
    public EObject getExistingObject() {
        return existingObject;
    }

    /**
     * @param existingObject the existingObject to set
     */
    public void setExistingObject(EObject existingObject) {
        this.existingObject = existingObject;
    }

    /**
     * @return the createTaskType
     */
    public TaskType getCreateTaskType() {
        return createTaskType;
    }

    /**
     * @param createTaskType the createTaskType to set
     */
    public void setCreateTaskType(TaskType createTaskType) {
        this.createTaskType = createTaskType;
    }

}