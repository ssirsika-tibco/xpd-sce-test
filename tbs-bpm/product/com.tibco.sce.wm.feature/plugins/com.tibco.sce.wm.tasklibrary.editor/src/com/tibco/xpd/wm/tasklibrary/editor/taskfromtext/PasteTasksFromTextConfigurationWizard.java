/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.taskfromtext;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class PasteTasksFromTextConfigurationWizard extends Wizard {

    private List<TaskFromTextItem> taskFromTextItems;

    private Process taskLibrary;

    private PasteTasksFromTextConfigPage configPage;

    public PasteTasksFromTextConfigurationWizard(List<TaskFromTextItem> items,
            Process taskLibrary) {

        taskFromTextItems = items;
        this.taskLibrary = taskLibrary;

        setWindowTitle(Messages.PasteTasksFromTextConfigurationWizard_PasteTasksFromText_title);
    }

    /**
     * @return the allowDuplicateTaskCreation
     */
    public boolean isAllowDuplicateTaskCreation() {
        if (configPage != null) {
            return configPage.isAllowDuplicateTaskCreation();
        }
        return false;
    }

    @Override
    public void addPages() {
        configPage =
                new PasteTasksFromTextConfigPage(taskFromTextItems, taskLibrary);

        addPage(configPage);

    }

    @Override
    public boolean performFinish() {
        return true;
    }

}
