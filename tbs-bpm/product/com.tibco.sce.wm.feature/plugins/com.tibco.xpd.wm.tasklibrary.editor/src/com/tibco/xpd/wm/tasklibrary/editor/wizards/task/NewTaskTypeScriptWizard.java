package com.tibco.xpd.wm.tasklibrary.editor.wizards.task;

import com.tibco.xpd.processwidget.adapters.TaskType;

/**
 * New Task Type Script wizard
 * 
 * @author aallway
 * @since 3.2
 */
public class NewTaskTypeScriptWizard extends AbstractNewTaskWizard {

    public NewTaskTypeScriptWizard() {
        super(TaskType.SCRIPT_LITERAL);
    }

}
