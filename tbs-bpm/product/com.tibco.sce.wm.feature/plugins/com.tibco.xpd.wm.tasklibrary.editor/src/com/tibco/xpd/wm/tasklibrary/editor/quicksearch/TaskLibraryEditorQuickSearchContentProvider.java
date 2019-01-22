/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

class TaskLibraryEditorQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private AbstractProcessDiagramEditor editor;

    private Process taskLibrary;

    private static final String TASKS_QS_CATEGORY_ID =
            "process.diagram.objects.tasks"; //$NON-NLS-1$

    private static final String TASKSETS_QS_CATEGORY_ID =
            "process.diagram.objects.tasksets"; //$NON-NLS-1$

    private static final String ARTIFACTS_QS_CATEGORY_ID =
            "process.diagram.objects.artifacts"; //$NON-NLS-1$

    private static final String ASSOC_QS_CATEGORY_ID =
            "process.diagram.connections.association"; //$NON-NLS-1$

    private List<QuickSearchPopupCategory> searchCategories;

    public TaskLibraryEditorQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        editor = (AbstractProcessDiagramEditor) partRef.getPart(false);

        ProcessEditorInput input = (ProcessEditorInput) editor.getEditorInput();
        taskLibrary = input.getProcess();

        initSearchCategories();
    }

    @Override
    public void dispose() {

    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        return searchCategories;
    }

    @Override
    public Collection<?> getElements() {
        return getElements(Collections.EMPTY_LIST);
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {
        List<Object> elements = new ArrayList<Object>();

        // All lanes (Task Sets) in the pool in this task lib.
        if (isCategoryEnabled(categories, TASKSETS_QS_CATEGORY_ID)) {
            for (Lane lane : Xpdl2ModelUtil.getProcessLanes(taskLibrary)) {
                elements.add(lane);
            }
        }

        // All tasks
        if (isCategoryEnabled(categories, TASKS_QS_CATEGORY_ID)) {
            for (Activity act : Xpdl2ModelUtil
                    .getAllActivitiesInProc(taskLibrary)) {
                String name = Xpdl2ModelUtil.getDisplayNameOrName(act);
                if (name != null && name.length() > 0) {
                    elements.add(act);
                }
            }
        }

        // All artifacts.
        if (isCategoryEnabled(categories, ARTIFACTS_QS_CATEGORY_ID)) {
            for (Artifact artifact : Xpdl2ModelUtil
                    .getAllArtifactsInProcess(taskLibrary)) {
                String name = Xpdl2ModelUtil.getDisplayNameOrName(artifact);
                if (name != null && name.length() > 0) {
                    elements.add(artifact);
                }
            }
        }

        // All associations.
        if (isCategoryEnabled(categories, ASSOC_QS_CATEGORY_ID)) {
            for (Association assoc : Xpdl2ModelUtil
                    .getAllAssociationsInProc(taskLibrary)) {
                String name = Xpdl2ModelUtil.getDisplayNameOrName(assoc);
                if (name != null && name.length() > 0) {
                    elements.add(assoc);
                }
            }
        }

        return elements;
    }

    /**
     * Initialise the search categories list.
     */
    private void initSearchCategories() {
        searchCategories = new ArrayList<QuickSearchPopupCategory>();

        searchCategories
                .add(new QuickSearchPopupCategory(
                        TASKS_QS_CATEGORY_ID,
                        Messages.TaskLibraryEditorQuickSearchContentProvider_TasksQuickSearchCategory_label));

        searchCategories
                .add(new QuickSearchPopupCategory(
                        TASKSETS_QS_CATEGORY_ID,
                        Messages.TaskLibraryEditorQuickSearchContentProvider_TaskSetsQuickSearchCategory_label));

        searchCategories
                .add(new QuickSearchPopupCategory(
                        ARTIFACTS_QS_CATEGORY_ID,
                        Messages.TaskLibraryEditorQuickSearchContentProvider_ArtifactsQuickSearchCategory_label));

        searchCategories
                .add(new QuickSearchPopupCategory(
                        ASSOC_QS_CATEGORY_ID,
                        Messages.TaskLibraryEditorQuickSearchContentProvider_AssocsQuickSearchCategory_label));

    }

}