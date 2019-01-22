package com.tibco.xpd.wm.tasklibrary.editor.wizards.task;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * New Task Type Manual wizard
 * 
 * @author aallway
 * @since 3.2
 */
public class NewTaskTypeManualWizard extends AbstractNewTaskWizard {

    public NewTaskTypeManualWizard() {
        super(TaskType.MANUAL_LITERAL);
    }

}
