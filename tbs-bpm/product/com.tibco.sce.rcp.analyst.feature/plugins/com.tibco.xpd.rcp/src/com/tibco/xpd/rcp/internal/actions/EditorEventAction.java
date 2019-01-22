/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.PartEventAction;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Abstract class to be implemented by any action that needs to listen to editor
 * activation.
 * 
 * @author njpatel
 * 
 */
public abstract class EditorEventAction extends PartEventAction implements
        IDisposable {

    private final IWorkbenchWindow window;

    public EditorEventAction(IWorkbenchWindow window, String text) {
        super(text);
        this.window = window;
        this.window.getPartService().addPartListener(this);
    }

    /**
     * Get the shared editing domain.
     * 
     * @return
     */
    protected TransactionalEditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * Get the workbench window.
     * 
     * @return
     */
    protected IWorkbenchWindow getWindow() {
        return window;
    }

    @Override
    public void partActivated(IWorkbenchPart part) {
        super.partActivated(part);

        setEnabled(getEditorActivated(part) != null);
    }

    /**
     * Get the Editor Part activated. If the part is an Editor Part then this
     * will be returned. If it's a view part then the active editor (if any)
     * will be returned.
     * 
     * @param part
     * @return editor part, <code>null</code> if no active editor part is found.
     */
    protected final IEditorPart getEditorActivated(IWorkbenchPart part) {
        if (part instanceof IEditorPart) {
            return (IEditorPart) part;
        } else if (part instanceof IViewPart) {
            IWorkbenchPartSite site = part.getSite();
            if (site != null && site.getPage() != null) {
                return site.getPage().getActiveEditor();
            }
        }
        return null;
    }

    @Override
    public void run() {
        IWorkbenchPart part = getActivePart();

        if (part instanceof IEditorPart) {
            try {
                run(((IEditorPart) part).getEditorInput());
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dispose() {
        if (window != null) {
            window.getPartService().removePartListener(this);
        }
    }

    /**
     * Run the action for the editor with the given input.
     * 
     * @param input
     * @throws CoreException
     */
    protected abstract void run(IEditorInput input) throws CoreException;

}
