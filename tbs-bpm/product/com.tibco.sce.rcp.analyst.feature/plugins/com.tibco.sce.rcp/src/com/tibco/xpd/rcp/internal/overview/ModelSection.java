/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.rcp.internal.models.AbstractModelProvider;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;

/**
 * Abstract class to be implemented by the section contributor to the Overview
 * page in the Overview editor.
 * 
 * @author njpatel
 * 
 */
/* public */class ModelSection implements IDisposable,
        ISelectionChangedListener {

    private final FormToolkit toolkit;

    private final String sectionTitle;

    private final IWorkbenchWindow window;

    private TreeViewer viewer;

    private IProject project;

    private final AbstractModelProvider modelProvider;

    private ISelectionProvider selectionProvider;

    private ModelSectionSelectionChangeListener modelSectionSelectionChangeListener;

    /**
     * Section in the overview page.
     * 
     * @param window
     * @param modelProvider
     * @param toolkit
     * @param sectionTitle
     * @param projectResource
     */
    public ModelSection(IWorkbenchWindow window,
            AbstractModelProvider modelProvider,
            ISelectionProvider selectionProvider, FormToolkit toolkit,
            String sectionTitle, IProject project) {
        this.window = window;
        this.modelProvider = modelProvider;
        this.selectionProvider = selectionProvider;
        this.toolkit = toolkit;
        this.sectionTitle = sectionTitle;
        this.project = project;
    }

    /**
     * Get the Tree viewer.
     * 
     * @return
     */
    public TreeViewer getViewer() {
        return viewer;
    }

    @Override
    public void dispose() {
        if (viewer != null) {
            viewer.removeSelectionChangedListener(modelSectionSelectionChangeListener);
        }
        if (selectionProvider != null) {
            selectionProvider.removeSelectionChangedListener(this);
        }
    }

    /**
     * Create the form section.
     * 
     * @param parent
     * @return
     */
    public Section createSection(Composite parent) {
        Section section =
                toolkit.createSection(parent, Section.EXPANDED
                        | Section.TITLE_BAR);

        section.setText(sectionTitle);

        if (modelProvider.getToolbarManager() != null) {
            ToolBar toolbar =
                    modelProvider.getToolbarManager().createControl(section);
            section.setTextClient(toolbar);

            modelProvider.getToolbarManager().update(true);
        }

        viewer = createListViewer(section);
        modelSectionSelectionChangeListener =
                new ModelSectionSelectionChangeListener(selectionProvider);
        viewer.addSelectionChangedListener(modelSectionSelectionChangeListener);

        section.setClient(viewer.getControl());
        if (selectionProvider != null) {
            selectionProvider.addSelectionChangedListener(this);
        }
        return section;
    }

    /**
     * Notification that the RCP resource has changed.
     * 
     * @param resource
     * @param eventType
     * @param eventObj
     */
    public void resourceChanged(IRCPResource resource,
            RCPResourceEventType eventType, Object eventObj) {
        // See if the section needs updating because of the change
        if (viewer != null && !viewer.getControl().isDisposed()
                && isAffectingEvent(eventType, eventObj)) {
            window.getShell().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        viewer.refresh();
                    }
                }
            });
        }
    }

    /**
     * Get the {@link IContentProvider}s to add to this section.
     * 
     * @return
     */
    protected IContentProvider getContentProvider() {
        return modelProvider.getContentProvider();
    }

    /**
     * Get the label provider for this section.
     * 
     * @return
     */
    protected IBaseLabelProvider getContentLabelProvider() {
        return modelProvider.getContentLabelProvider();
    }

    /**
     * Check if the change event affects this section - if it does then the
     * section will be refreshed.
     * 
     * @see RCPResourceChangeListener
     * 
     * @param eventType
     * @return
     */
    protected boolean isAffectingEvent(RCPResourceEventType eventType,
            Object eventObj) {
        return modelProvider.isAffectingEvent(eventType, eventObj);
    }

    /**
     * Create the hyperlink list viewer with the given content provider.
     * 
     * @param parent
     * @param contentProvider
     * @return
     */
    private TreeViewer createListViewer(Composite parent) {
        TreeViewer viewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);

        viewer.getControl().setLayoutData(new GridData());
        viewer.setContentProvider(getContentProvider());
        viewer.setLabelProvider(getContentLabelProvider());
        viewer.setInput(project);
        return viewer;
    }

    private class ModelSectionSelectionChangeListener implements
            ISelectionChangedListener {

        private final ISelectionProvider selectionProvider;

        public ModelSectionSelectionChangeListener(
                ISelectionProvider selectionProvider) {
            this.selectionProvider = selectionProvider;
        }

        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         * 
         * @param event
         */
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            if (selectionProvider != null) {
                ITreeSelection selection =
                        (ITreeSelection) event.getSelection();
                if (selection != null && selection.getFirstElement() != null) {
                    selectionProvider.setSelection(new StructuredSelection(
                            selection.getFirstElement()));
                }
            }
        }
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     * 
     * @param event
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        if (modelProvider != null) {
            modelProvider.selectionChanged(event.getSelection());
        }
    }

}
