/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.actions;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * Delete action for task library content.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryContentDeleteAction extends DeleteAction {

    public TaskLibraryContentDeleteAction(Shell shell) {
        super(shell);
    }

    @Override
    protected boolean validateSelection(List selectionList) {
        if (super.validateSelection(selectionList)) {
            //
            // Disallow delete of task library element (process) because there's
            // a direct one-to-one relationship between file and library
            // process.
            if (!ActionUtil.isClassTypeInList(selectionList, Process.class)) {
                return true;
            }
        }
        return false;
    }
}
