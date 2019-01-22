package com.tibco.xpd.wm.tasklibrary.editor.wizards.task;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * New Task Type Sub-Process wizard
 * 
 * @author aallway
 * @since 3.2
 */
public class NewTaskTypeSubProcWizard extends AbstractNewTaskWizard {

    public NewTaskTypeSubProcWizard() {
        super(TaskType.SUBPROCESS_LITERAL);
    }

}
