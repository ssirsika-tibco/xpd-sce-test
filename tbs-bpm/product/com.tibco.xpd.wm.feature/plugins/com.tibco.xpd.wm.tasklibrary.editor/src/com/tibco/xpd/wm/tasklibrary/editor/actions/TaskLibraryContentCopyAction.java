/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdl2.Process;

/**
 * Project explorer copy action for task library content.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryContentCopyAction extends CopyAction {

    @Override
    protected boolean validateSelection(List selectionList) {
        if (super.validateSelection(selectionList)) {
            //
            // Disallow copy of task library element (process) because there's
            // no-where it can be copied to.
            if (!ActionUtil.isClassTypeInList(selectionList, Process.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected List getElementsToCopy(List selList) {

        Process taskLibrary = getTaskLibrary(selList);
        if (taskLibrary != null) {
            HashSet<Object> finalElementsToCopy = new HashSet<Object>();
            finalElementsToCopy.addAll(selList);

            Xpdl2ProcessDiagramUtils
                    .addObjectsReferencedFromCopyObjects(taskLibrary,
                            selList,
                            finalElementsToCopy,
                            false);

            ArrayList<Object> finalList = new ArrayList<Object>();
            finalList.addAll(finalElementsToCopy);
            return finalList;
        }

        return Collections.EMPTY_LIST;
    }

    private Process getTaskLibrary(List selList) {
        if (!selList.isEmpty()) {
            Object o = selList.get(0);
            if (o instanceof INavigatorGroup) {
                o = ((INavigatorGroup) o).getParent();
            }

            if (o instanceof EObject) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) o);

                if (wc != null) {
                    return TaskLibraryFactory.INSTANCE.getTaskLibrary(wc);
                }
            }
        }
        return null;
    }

}
