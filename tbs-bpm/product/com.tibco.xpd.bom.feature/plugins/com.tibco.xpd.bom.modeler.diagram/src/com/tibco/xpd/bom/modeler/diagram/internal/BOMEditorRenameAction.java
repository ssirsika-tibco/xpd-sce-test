/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Action to rename an element in the BOM diagram.
 * 
 * @author njpatel
 */
public class BOMEditorRenameAction extends AbstractActionHandler {

    private final Request request;

    /**
     * @param workbenchPage
     */
    public BOMEditorRenameAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
        request = new Request(RequestConstants.REQ_DIRECT_EDIT);

    }

    /**
     * @see org.eclipse.gmf.runtime.common.ui.action.IActionWithProgress#refresh()
     * 
     */
    @Override
    public void refresh() {
        GraphicalEditPart ep = getSelectedEditPart();
        boolean isEnabled = false;

        if (ep != null) {
            isEnabled = ep.understandsRequest(request);
        }

        setEnabled(isEnabled);
    }

    /**
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#doRun(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param progressMonitor
     */
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {
        GraphicalEditPart ep = getSelectedEditPart();

        if (ep != null) {
            ep.performRequest(request);
        }
    }

    /**
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#isSelectionListener()
     * 
     * @return
     */
    @Override
    protected boolean isSelectionListener() {
        return true;
    }

    /**
     * Get the selected edit part.
     * 
     * @return
     */
    private GraphicalEditPart getSelectedEditPart() {
        if (getSelection() instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) getSelection();
            Object element = sel.getFirstElement();

            return (GraphicalEditPart) (element instanceof GraphicalEditPart ? element
                    : null);
        }
        return null;
    }
}
