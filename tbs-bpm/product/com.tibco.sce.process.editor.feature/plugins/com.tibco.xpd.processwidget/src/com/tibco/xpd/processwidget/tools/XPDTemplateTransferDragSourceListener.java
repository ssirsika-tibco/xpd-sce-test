/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.tools;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;

/**
 * XPDTemplateTransferDragSourceListener
 * 
 * 
 * @author aallway
 * @since 3.3 (22 Oct 2009)
 */
public class XPDTemplateTransferDragSourceListener extends
        TemplateTransferDragSourceListener {

    public XPDTemplateTransferDragSourceListener(EditPartViewer viewer) {
        super(viewer);
    }

    @Override
    protected Object getTemplate() {
        List selection = getViewer().getSelectedEditParts();
        if (selection.size() == 1) {
            EditPart editpart =
                    (EditPart) getViewer().getSelectedEditParts().get(0);
            Object model = editpart.getModel();

            /*
             * The XPDElementCreationTool has extra info regarding the default
             * size etc of the object so rather than using the template from the
             * tool we will return the tool itself.
             * 
             * This means that XPDTemplateTransferDropTargetListener can pick up
             * the default size of objects from the XPDElementCreationTool and
             * then grab the factory from it to pass onto to underlying
             * TemplateTransferDropTargetListener for normal behaviour.
             */
            if (model instanceof XPDElementCreationTool) {
                return model;
            }
        }

        return super.getTemplate();
    }

}
