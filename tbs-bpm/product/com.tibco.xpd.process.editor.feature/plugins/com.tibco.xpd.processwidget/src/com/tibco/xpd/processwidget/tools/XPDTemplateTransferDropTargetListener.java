/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.tools;

import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;

/**
 * XPDTemplateTransferDropTargetListener
 * 
 * 
 * @author aallway
 * @since 3.3 (22 Oct 2009)
 */
public class XPDTemplateTransferDropTargetListener extends
        TemplateTransferDropTargetListener {

    /**
     * @param viewer
     */
    public XPDTemplateTransferDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    @Override
    protected void updateTargetRequest() {
        super.updateTargetRequest();

        if (getTargetRequest() instanceof CreateRequest) {
            CreateRequest createRequest = (CreateRequest) getTargetRequest();

            if (createRequest.getSize() == null) {
                if (TemplateTransfer.getInstance().getTemplate() instanceof XPDElementCreationTool) {
                    XPDElementCreationTool xpdCreationTool =
                            (XPDElementCreationTool) TemplateTransfer
                                    .getInstance().getTemplate();

                    createRequest.setSize(xpdCreationTool
                            .getDefaultObjectSize());

                    Map requestExtendedData =
                            xpdCreationTool.getRequestExtendedData();
                    if (requestExtendedData != null) {
                        createRequest.getExtendedData()
                                .putAll(requestExtendedData);
                    }
                }
            }
        }

        return;
    }

    @Override
    protected CreationFactory getFactory(Object template) {
        /*
         * XPDTemplateTransferDragSourceListener may place
         * XPDElementCreationTool in as template so that our updateRequest()
         * above has access to default size for objects. <p> In which case we
         * need to convert between it and the creation factory that the
         * TemplateTransferDropTargetListener normally expects.
         */
        if (template instanceof XPDElementCreationTool) {
            return ((XPDElementCreationTool) template).getCreationFactory();
        }
        return super.getFactory(template);
    }

    @Override
    protected void handleDragOver() {
        super.handleDragOver();

        Command cmd = getCommand();
        if (cmd == null || !cmd.canExecute()) {
            getCurrentEvent().detail = DND.DROP_NONE;
        }

        return;
    }

    @Override
    protected void handleDrop() {
        super.handleDrop();

        if (getTargetRequest() instanceof CreateRequest) {
            CreateRequest createRequest = (CreateRequest) getTargetRequest();
            Object newModelObject =
                    createRequest.getExtendedData()
                            .get(PaletteFactory.EXTDATA_CREATED_MODEL_OBJECT);

            if (newModelObject != null) {
                EditPartViewer viewer = getViewer();
                viewer.getControl().forceFocus();
                Object editpart =
                        viewer.getEditPartRegistry().get(newModelObject);
                if (editpart instanceof EditPart) {
                    // Force a layout first.
                    getViewer().flush();
                    viewer.select((EditPart) editpart);
                }
            }
        }
        return;
    }

}
