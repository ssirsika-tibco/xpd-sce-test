/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.quicksearch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;

/**
 * Task library editor quick search popup contribution.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorQuickSearchContribution extends
        AbstractQuickSearchPopupContribution {

    /**
     * 
     */
    public TaskLibraryEditorQuickSearchContribution() {

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.api.quicksearch.
     * AbstractQuickSearchPopupContribution
     * #createContentProvider(org.eclipse.ui.IWorkbenchPartReference)
     */
    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new TaskLibraryEditorQuickSearchContentProvider(partRef);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.api.quicksearch.
     * AbstractQuickSearchPopupContribution
     * #createLabelProvider(org.eclipse.ui.IWorkbenchPartReference)
     */
    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new TaskLibraryEditorQuickSearchLabelProvider(partRef);
    }

    @Override
    public Rectangle selectAndReveal(IWorkbenchPartReference partRef,
            Object element) {
        Rectangle revealRect = null;

        IWorkbenchPart part = partRef.getPart(false);

        if (part instanceof AbstractProcessDiagramEditor) {
            if (element instanceof EObject) {
                AbstractProcessDiagramEditor editor =
                        (AbstractProcessDiagramEditor) part;

                List<EObject> eObjects = new ArrayList<EObject>();
                eObjects.add((EObject) element);

                revealRect =
                        editor.navigateToObjects(eObjects,
                                true,
                                true,
                                true,
                                false);

            }
        }
        return revealRect;
    }

}
