/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal;

import org.eclipse.core.internal.resources.mapping.SimpleResourceMapping;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Editor input used by the Overview editor. This input is also a selection
 * listener to the overview editor. This is required for the correct update of
 * the problems view as the Overview Editor now acts as the project explorer
 * (this input is adapted to a {@link ResourceMapping} which is required by the
 * problems view to determine the current resource selection (
 * <code>ExtendedMarkersView.class</code>).
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
public class OverviewEditorInput implements IEditorInput,
        ISelectionChangedListener {

    private final IRCPContainer resource;

    private ISelection selection;

    /**
     * Editor input used by the Overview editor.
     * 
     * @param resource
     */
    public OverviewEditorInput(IRCPContainer resource) {
        this.resource = resource;
    }

    /**
     * @return the resource
     */
    public IRCPContainer getResource() {
        return resource;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        /*
         * Need to adapt to a ResourceMapper to handle the problem view
         * filtering correctly as the Overview editor is the Project explorer in
         * the RCP.
         */
        if (adapter == ResourceMapping.class) {
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection stSel = (IStructuredSelection) selection;
                if (!stSel.isEmpty()) {
                    Object element = stSel.getFirstElement();
                    IResource resource = null;

                    if (element instanceof IResource) {
                        resource = (IResource) element;
                    } else if (element instanceof EObject) {
                        resource = WorkingCopyUtil.getFile((EObject) element);
                    }

                    if (resource != null) {
                        return new SimpleResourceMapping(resource);
                    }
                }
            }
        }

        return null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#exists()
     * 
     * @return
     */
    @Override
    public boolean exists() {
        return resource != null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     * 
     * @return
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return resource.getName() != null ? resource.getName() : ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     * 
     * @return
     */
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     * 
     * @return
     */
    @Override
    public String getToolTipText() {
        return resource.getName() != null ? resource.getName() : ""; //$NON-NLS-1$
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return resource.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OverviewEditorInput) {
            return ((OverviewEditorInput) obj).getResource()
                    .equals(getResource());
        }
        return super.equals(obj);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     * 
     * @param event
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        selection = event.getSelection();
    }
}
