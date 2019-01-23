/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.properties;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.tigris.subversion.subclipse.core.SVNException;

import com.tibco.rcp.internal.svn.SVNUtils;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.MAAResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

public class ProjectResourcePropertiesSection extends AbstractPropertySection {

    private Text nameTxt;

    private Text locationTxt;

    private Text lastModifiedTxt;

    private Label svnURLLabel;

    private Text svnURLTxt;

    private WorkspaceListener workspaceListener;

    public ProjectResourcePropertiesSection() {

    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#createControls(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
     * 
     * @param parent
     * @param aTabbedPropertySheetPage
     */
    @Override
    public void createControls(Composite parent,
            TabbedPropertySheetPage aTabbedPropertySheetPage) {
        super.createControls(parent, aTabbedPropertySheetPage);

        TabbedPropertySheetWidgetFactory widgetFactory = getWidgetFactory();

        Composite root = widgetFactory.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        createLabel(widgetFactory,
                root,
                Messages.ProjectResourcePropertiesSection_name_label);
        nameTxt = createText(widgetFactory, root);

        createLabel(widgetFactory,
                root,
                Messages.ProjectResourcePropertiesSection_location_label);
        locationTxt = createText(widgetFactory, root);

        createLabel(widgetFactory,
                root,
                Messages.ProjectResourcePropertiesSection_lastModifiedDate_label);
        lastModifiedTxt = createText(widgetFactory, root);

        svnURLLabel =
                createLabel(widgetFactory,
                        root,
                        Messages.ProjectResourcePropertiesSection_svnUrl_label);
        svnURLTxt = createText(widgetFactory, root);
        showSVNControls(false);

        addWorkspaceListener();
    }

    /**
     * Add the workspace listener.
     */
    private void addWorkspaceListener() {
        workspaceListener = new WorkspaceListener();
        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(workspaceListener,
                        IResourceChangeEvent.POST_CHANGE);
    }

    /**
     * Remove the registered workspace listener.
     */
    private void removeWorkspaceListener() {
        if (workspaceListener != null) {
            ResourcesPlugin.getWorkspace()
                    .removeResourceChangeListener(workspaceListener);
        }
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#dispose()
     * 
     */
    @Override
    public void dispose() {
        removeWorkspaceListener();
        super.dispose();
    }

    /**
     * Show/hide the SVN specific controls.
     * 
     * @param show
     */
    private void showSVNControls(boolean show) {
        if (svnURLLabel != null && !svnURLLabel.isDisposed()) {
            svnURLLabel.setVisible(show);
            svnURLTxt.setVisible(show);
        }
    }

    /**
     * Create a label with a default width.
     * 
     * @param widgetFactory
     * @param parent
     * @param label
     * @return
     */
    private Label createLabel(TabbedPropertySheetWidgetFactory widgetFactory,
            Composite parent, String label) {
        Label lbl = widgetFactory.createLabel(parent, label);
        GridData data = new GridData();
        data.widthHint = 85;
        lbl.setLayoutData(data);
        return lbl;
    }

    /**
     * Create a text control with default min width and grap max horizontal
     * space.
     * 
     * @param widgetFactory
     * @param parent
     * @return
     */
    private Text createText(TabbedPropertySheetWidgetFactory widgetFactory,
            Composite parent) {
        Text text = widgetFactory.createText(parent, ""); //$NON-NLS-1$
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.widthHint = 185;
        text.setLayoutData(data);
        text.setEditable(false);

        return text;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
     * 
     */
    @Override
    public void refresh() {

        IProject project = getInput();

        if (project != null && project.isAccessible() && !nameTxt.isDisposed()) {
            nameTxt.setText(project.getName());

            IRCPContainer resource = RCPResourceManager.getResource();
            if (resource instanceof MAAResource) {
                IPath path = resource.getPath();
                locationTxt
                        .setText(path != null ? path.toString()
                                : Messages.ProjectResourcePropertiesSection_lastModifiedData_unknown_label);
            } else {
                locationTxt.setText(project.getLocation().toString());
            }

            String lastModified = null;
            try {
                IFileStore store = null;
                if (resource instanceof MAAResource) {
                    if (resource.getPath() != null) {
                        File file = resource.getPath().toFile();
                        store = getFileStore(file.toURI());
                    } else {
                        // Just use the project time for now
                        store = getFileStore(project.getLocationURI());
                    }
                } else {
                    store = getFileStore(project.getLocationURI());
                }

                if (store != null) {
                    IFileInfo info = store.fetchInfo();
                    if (info != null && info.exists()) {
                        DateFormat format =
                                DateFormat.getDateTimeInstance(DateFormat.LONG,
                                        DateFormat.MEDIUM);
                        lastModified =
                                format.format(new Date(info.getLastModified()));
                    }
                }
            } catch (CoreException e) {
                // Do nothing
            }

            lastModifiedTxt
                    .setText(lastModified != null ? lastModified
                            : Messages.ProjectResourcePropertiesSection_lastModifiedData_unknown_label);

            /*
             * If this project is under SVN then show the SVN url
             */
            boolean showSVNControls = false;
            if (SVNUtils.isProjectUnderSVN(project)) {
                try {
                    String url = SVNUtils.getSVNRepositoryURL(project);
                    if (url != null) {
                        svnURLTxt.setText(url);
                        showSVNControls = true;
                    }
                } catch (SVNException e) {
                    // Do nothing
                }
            }
            showSVNControls(showSVNControls);
        }
    }

    /**
     * Get the file store for the file at the given URI.
     * 
     * @param uri
     * @return
     * @throws CoreException
     */
    private IFileStore getFileStore(URI uri) throws CoreException {
        return uri != null ? EFS.getStore(uri) : null;
    }

    /**
     * Get the project input of this section.
     * 
     * @return
     */
    private IProject getInput() {

        IStructuredSelection selection = (IStructuredSelection) getSelection();

        if (selection != null && !selection.isEmpty()) {
            Object element = selection.getFirstElement();
            if (element instanceof IProject) {
                return (IProject) element;
            }
        }

        return null;
    }

    /**
     * Workspace listener that will refresh this section when the description of
     * the project changes (primarily for SVN information change).
     * 
     */
    private class WorkspaceListener implements IResourceChangeListener,
            IResourceDeltaVisitor {

        /**
         * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
         * 
         * @param event
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta delta = event.getDelta();
            if (delta != null) {
                try {
                    delta.accept(this);
                } catch (CoreException e) {
                    // Do nothing
                }
            }
        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();

            if (resource instanceof IProject) {
                if (delta.getKind() == IResourceDelta.CHANGED
                        && ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0)) {
                    // If this project is now showing as under SVN control the
                    // notify listeners
                    projectDescriptionChanged((IProject) resource);
                }

                return false;
            }
            return true;
        }

        /**
         * The project's description has changed.
         * 
         * @param project
         */
        private void projectDescriptionChanged(IProject project) {
            IProject input = getInput();
            /*
             * Update this section
             */
            if (project.isAccessible() && project.equals(input)) {
                IWorkbenchPart part = getPart();
                if (part != null && part.getSite() != null) {
                    part.getSite().getShell().getDisplay()
                            .asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    refresh();
                                }
                            });
                }
            }
        }
    }

}
