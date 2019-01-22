package com.tibco.xpd.wm.tasklibrary.editor.wizards.task;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * New Task Type Service wizard
 * 
 * @author aallway
 * @since 3.2
 */
public class NewTaskTypeServiceWizard extends AbstractNewTaskWizard {

    public NewTaskTypeServiceWizard() {
        super(TaskType.SERVICE_LITERAL);
    }

}
