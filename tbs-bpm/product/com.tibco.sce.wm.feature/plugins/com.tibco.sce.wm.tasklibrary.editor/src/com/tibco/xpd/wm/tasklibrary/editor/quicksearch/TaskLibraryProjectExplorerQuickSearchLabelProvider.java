/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Label provider for Project explorer quick search contribution for Task
 * Library stuff.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    /**
     * @param partRef
     */
    public TaskLibraryProjectExplorerQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof IndexerItem) {
            String type = ((IndexerItem) element).getType();

            if (Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEX_TYPE.equals(type)) {
                return Messages.TaskLibraryProjectExplorerQuickSearchLabelProvider_TaskLibraryTypeName_label;

            } else if (Xpdl2ResourcesPlugin.TASK_LIBRARY_TASKSET_INDEX_TYPE
                    .equals(equals(type))) {
                return Messages.TaskLibraryProjectExplorerQuickSearchLabelProvider_TaskSetTypeName_label;

            } else if (Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE
                    .equals(equals(type))) {
                return Messages.TaskLibraryProjectExplorerQuickSearchLabelProvider_TaskTypeName_label;
            }
        }

        return super.getElementTypeName(element);
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof IndexerItem) {
            Image img =
                    ProcessUIUtil.getImageForIndexedItem((IndexerItem) element);
            if (img != null) {
                return img;
            }
        }
        return super.getImage(element);
    }

    @Override
    public String getText(Object element) {
        if (element instanceof IndexerItem) {
            String label =
                    ProcessUIUtil.getLabelForIndexedItem((IndexerItem) element);
            if (label != null) {
                return label;
            }
        }
        return super.getText(element);
    }
    
    
}
