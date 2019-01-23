/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.OverviewEditorInput;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;

/**
 * The Overview (form) editor that will display the contents of the project
 * being edited.
 * 
 * @author njpatel
 * 
 */
public class OverviewEditor extends FormEditor implements
        ITabbedPropertySheetPageContributor {

    public static final String ID = "com.tibco.xpd.rcp.overviewEditor"; //$NON-NLS-1$

    // private OverviewPage overviewPage;

    private ISelectionProvider selectionProvider =
            new OverviewEditorSelectionProvider(this);

    private ProjectViewOverviewPage page;

    @Override
    protected void addPages() {
        IRCPContainer resource = null;

        IEditorInput input = getEditorInput();
        if (input instanceof OverviewEditorInput) {
            resource = ((OverviewEditorInput) input).getResource();
        }

        //        overviewPage = new OverviewPage(this, "overview", //$NON-NLS-1$
        // Messages.OverviewEditor_title, resource);
        page = new ProjectViewOverviewPage(this, "overview2", //$NON-NLS-1$
                Messages.OverviewEditor_ExplorerOverview, resource);
        if (page != null && page.getEditor() != null
                && page.getEditor().getSite() != null) {
            page.getEditor().getSite().setSelectionProvider(selectionProvider);
        }
        try {
            addPage(page);
            // addPage(overviewPage);
        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(),
                    Messages.OverviewEditor_errorDialog_title,
                    Messages.OverviewEditor_initError_dialog_message,
                    new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                            .getLocalizedMessage(), e));
        }
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param site
     * @param input
     * @throws PartInitException
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {

        // Update the selection in the editor input - required to ensure correct
        // update of the problems view.
        if (selectionProvider != null
                && input instanceof ISelectionChangedListener) {
            selectionProvider
                    .addSelectionChangedListener((ISelectionChangedListener) input);
        }

        super.init(site, input);
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
     * 
     */
    @Override
    public void dispose() {
        IEditorInput input = getEditorInput();
        if (selectionProvider != null
                && input instanceof ISelectionChangedListener) {
            selectionProvider
                    .removeSelectionChangedListener((ISelectionChangedListener) input);
        }

        super.dispose();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        // Do nothing
    }

    @Override
    public void doSaveAs() {
        // Do nothing
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        if (page != null) {
            return page.getContributorId();
        }
        return null;
    }

    /**
     * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (page != null) {
            return page.getAdapter(adapter);
        }
        return super.getAdapter(adapter);
    }

}
