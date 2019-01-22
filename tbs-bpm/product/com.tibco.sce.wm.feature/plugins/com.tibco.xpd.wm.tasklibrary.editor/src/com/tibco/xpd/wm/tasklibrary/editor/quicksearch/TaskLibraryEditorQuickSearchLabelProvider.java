/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.processeditor.xpdl2.quickSearchContribution.ProcessEditorQuickSearchLabelProvider;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task Library editor quick search popup label provider.
 * <p>
 * For now this should be the same as the process editor.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorQuickSearchLabelProvider extends
        ProcessEditorQuickSearchLabelProvider {

    public TaskLibraryEditorQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof Lane) {
            return TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                    .get(TaskLibraryEditorContstants.IMG_TASKSET);
        }
        return super.getImage(element);
    }

    @Override
    public String getElementPath(Object element) {
        if (element instanceof EObject) {
            Process taskLib =
                    TaskLibraryFactory.INSTANCE.getTaskLibrary(Xpdl2ModelUtil
                            .getPackage((EObject) element));

            if (taskLib != null) {
                String path = Xpdl2ModelUtil.getDisplayNameOrName(taskLib);

                if (!(element instanceof Lane)) {
                    // We don't show pool in diagram so don't show it in the
                    // path (swap it for the task library name instead).
                    String pathWithPool = super.getElementPath(element);
                    
                    if (path != null) {
                        int idx = pathWithPool.indexOf(ELEMENT_PATH_SEPARATOR);
                        if (idx >= 0) {
                            path += pathWithPool.substring(idx);
                        } else {
                            // Connections!
                            path += " - " + pathWithPool; //$NON-NLS-1$
                        }
                    }
                }
                
                return path;
            }
        }

        return super.getElementPath(element);
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof Lane) {
            return Messages.NewTaskLibraryWizard_TaskSet_label;
        }
        return super.getElementTypeName(element);
    }
}
