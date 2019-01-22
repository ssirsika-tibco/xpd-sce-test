/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.notation.impl.ViewImpl;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;

/**
 * Quick search popup contribution for the OM Diagram editor.
 * 
 * @author patkinso
 * @since 22 Jun 2012
 */
public abstract class AbstractOmEditorQuickSearchContribution extends
        AbstractQuickSearchPopupContribution {

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#selectAndReveal(org.eclipse.ui.IWorkbenchPartReference,
     *      java.lang.Object)
     * 
     * @param partRef
     * @param element
     * @return
     */
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
                    if (impl.getElement().equals(eObject)) {

                        Object object = editPartRegistry.get(impl);
                        if (object instanceof ShapeNodeEditPart) { // select and
                                                                   // reveal
                                                                   // 'org unit'
                                                                   // element
                            viewer.select((ShapeNodeEditPart) object);
                            viewer.reveal((ShapeNodeEditPart) object);
                            break;
                        } else if (object instanceof CompartmentEditPart) { // select
                                                                            // and
                                                                            // reveal
                                                                            // 'positon'
                                                                            // element
                            if (((EditPart) object).getParent() instanceof ListCompartmentEditPart) {
                                viewer.select((CompartmentEditPart) object);
                                viewer.reveal((CompartmentEditPart) object);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
