package com.tibco.xpd.processeditor.xpdl2.quickSearchContribution;

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
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;

/**
 * ProcessEditorQuickSearchContribution.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

/**
 * ProcessEditorQuickSearchContribution
 * 
 */
public class ProcessEditorQuickSearchContribution extends
        AbstractQuickSearchPopupContribution {

    /**
     * 
     */
    public ProcessEditorQuickSearchContribution() {
    }

    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        IWorkbenchPart part = partRef.getPart(false);

        if (part instanceof AbstractProcessDiagramEditor) {
            return new ProcessDiagramQuickSearchContentProvider(partRef);
        }
        return null;
    }

    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new ProcessEditorQuickSearchLabelProvider(partRef);
    }

    @Override
    public Rectangle selectAndReveal(IWorkbenchPartReference partRef, Object element) {
        Rectangle revealRect = null;
        
        IWorkbenchPart part = partRef.getPart(false);

        if (part instanceof AbstractProcessDiagramEditor) {
            if (element instanceof EObject) {
                AbstractProcessDiagramEditor editor = (AbstractProcessDiagramEditor) part;

                List<EObject> eObjects = new ArrayList<EObject>();
                eObjects.add((EObject) element);

                revealRect = editor.navigateToObjects(eObjects, true, true, true, false);

            }
        }
        return revealRect;
    }

    static boolean isTask(Activity act) {
        Implementation implementation = act.getImplementation();

        if (implementation instanceof Task || implementation instanceof No
                || implementation instanceof SubFlow
                || implementation instanceof Reference
                || act.getBlockActivity() != null) {
            return true;
        }

        return false;
    }

    static boolean isEvent(Activity act) {
        if (act.getEvent() != null) {
            return true;
        }

        return false;
    }

    static boolean isGateway(Activity act) {
        if (act.getRoute() != null) {
            return true;
        }

        return false;
    }
}
