/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.notation.impl.ViewImpl;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;

/**
 * 
 * Quick search popup contribution for the BOM DIagram editor.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomEditorQuickSearchContribution extends
        AbstractQuickSearchPopupContribution {

    @Override
    public AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef) {
        return new BomEditorQuickSearchContentProvider(partRef);
    }

    @Override
    public AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef) {
        return new BomEditorQuickSearchLabelProvider(partRef);
    }

    @Override
    public Rectangle selectAndReveal(IWorkbenchPartReference partRef,
            Object element) {

        IWorkbenchPart part = partRef.getPart(false);

        if ((part instanceof IDiagramWorkbenchPart)
                && (element instanceof EObject)) {

            IDiagramGraphicalViewer viewer =
                    ((IDiagramWorkbenchPart) part).getDiagramGraphicalViewer();
            Map<?, ?> editPartRegistry = viewer.getEditPartRegistry();

            ViewImpl impl = null;
            EObject eObject = (EObject) element;

            for (Object obj : editPartRegistry.keySet()) {
                if (obj instanceof ViewImpl) {
                    impl = (ViewImpl) obj;

                    /*
                     * Sid ACE-2202 protect against NPE when dealing with
                     * objects in the DIAGRAM model that don't have a
                     * corresponding element in the SEMANTIC model (for example
                     * NOTE's in a BOM diagram.
                     */
                    if (impl.getElement() != null && impl.getElement().equals(eObject)) {

                        Object object = editPartRegistry.get(impl);
                        if (object instanceof ShapeNodeEditPart) {
                            viewer.select((ShapeNodeEditPart) object);
                            viewer.reveal((ShapeNodeEditPart) object);
                            break;
                        }

                    }
                }
            }
        }

        return null;
    }

}
