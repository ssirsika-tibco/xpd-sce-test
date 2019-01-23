/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.overview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.tigris.subversion.subclipse.ui.actions.UpdateAction;

import com.tibco.rcp.internal.svn.SVNCommitAction;
import com.tibco.rcp.internal.svn.SVNRevertAction;
import com.tibco.rcp.internal.svn.SVNSharingWizard;
import com.tibco.rcp.internal.svn.SVNUtils;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.NewProjectAction;
import com.tibco.xpd.rcp.internal.actions.RefreshWorkspaceAction;
import com.tibco.xpd.rcp.internal.actions.saveas.SaveAsMaaAction;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.utils.ZipArchiver;

/**
 * Project viewer in the Overview editor.
 * 
 * @author njpatel
 */
public class ProjectViewer extends Composite {

    private TableViewer viewer;

    private RCPTeamAction svnCommitAction;

    private RCPTeamAction svnRevertAction;

    private RCPTeamAction svnUpdateAction;

    private RCPTeamAction svnShareAction;

    private SVNConflictAction svnConflictExportAction;

    private NewProjectAction newProjectAction;

    private static final String TEAM_RCP_MENU_ID = "com.tibco.rcp.analyst.team"; //$NON-NLS-1$

    private static final String DOT = "."; //$NON-NLS-1$

    private static final Boolean includeReferencedProjects = Boolean.TRUE;

    private ILabelProviderListener labelProviderListener;

    private WorkspaceResourceLabelProvider workspaceLabelProvider;

    /**
     * Project viewer in the Overview editor.
     * 
     * @param parent
     * @param style
     */
    public ProjectViewer(Composite parent, int style, FormToolkit toolkit) {
        super(parent, style);
        setLayout(new FillLayout());

        createControls(toolkit, this);
    }

    /**
     * @return the viewer
     */
    /* public */TableViewer getViewer() {
        return viewer;
    }

    /**
     * Set the input for this viewer.
     * 
     * @param projects
     */
    public void setProjects(final Collection<IProject> projects) {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        viewer.setInput(projects);
                    }
                }
            });
        }
    }

    /**
     * Set the current selection.
     * 
     * @param project
     */
    public void setSelection(final IProject project) {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        viewer.setSelection(new StructuredSelection(project));
                    }
                }
            });
        }
    }

    /**
     * Add a selection change listener.
     * 
     * @param listener
     */
    public void addSelectionChangeListener(ISelectionChangedListener listener) {
        if (viewer != null) {
            viewer.addSelectionChangedListener(listener);
        }
    }

    /**
     * Remove a selection change listener.
     * 
     * @param listener
     */
    public void removeSelectionChangeListener(ISelectionChangedListener listener) {
        if (viewer != null) {
            viewer.removeSelectionChangedListener(listener);
        }
    }

    /**
     * Refresh the project viewer.
     * 
     * @param project
     *            the project to refresh, <code>null</code> to refresh the whole
     *            viewer.
     */
    public void refresh(IProject project) {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            if (project != null) {
                viewer.refresh(project);
            } else {
                viewer.refresh();
            }
        }
    }

    /**
     * Get the row count of the project in the table.
     * 
     * @param project
     * @return index of the project, -1 if the project has not been found in the
     *         table.
     */
    public int getIndex(IProject project) {
        Table table = viewer.getTable();
        if (!table.isDisposed()) {
            TableItem[] items = table.getItems();
            int index = 0;
            for (TableItem item : items) {
                if (project.equals(item.getData())) {
                    break;
                }
                ++index;
            }

            if (index < items.length) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Select the idx'th item in the table. If this value is greater than the
     * number of items in the table the last item will be selected.
     * 
     * @param idx
     */
    public void setSelection(int idx) {
        Table table = viewer.getTable();
        if (!table.isDisposed()) {

            if (idx < 0) {
                idx = 0;
            } else if (idx >= table.getItemCount()) {
                idx = table.getItemCount() - 1;
            }

            if (idx >= 0) {
                TableItem item = table.getItem(idx);
                if (item != null) {
                    viewer.setSelection(new StructuredSelection(item.getData()));
                }
            }
        }
    }

    /**
     * @param projectViewer
     */
    private void createControls(FormToolkit toolkit, Composite parent) {
        Section section =
                toolkit.createSection(parent, Section.EXPANDED
                        | Section.TITLE_BAR);
        section.setText(Messages.ProjectViewer_Projects_title);
        // section.setBackground(gray_color);
        viewer =
                new TableViewer(section, SWT.H_SCROLL | SWT.V_SCROLL
                        | SWT.MULTI);
        viewer.setContentProvider(new ContentProvider());
        viewer.setLabelProvider(getLabelProvider());
        // viewer.getControl().setBackground(gray_color);
        makeActions();
        IWorkbenchPartSite site =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart().getSite();
        hookContextMenu(site);
        hookToolBar(section, site);
        section.setClient(viewer.getControl());
    }

    private void hookContextMenu(IWorkbenchPartSite site) {
        MenuManager menuMgr = new MenuManager() {
            @Override
            protected void doItemFill(
                    org.eclipse.jface.action.IContributionItem ci, int index) {
                if (ci != null
                        && ProjectViewer.TEAM_RCP_MENU_ID.equals(ci.getId())) {
                    super.doItemFill(ci, index);
                }
            };
        };
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                if (viewer.getSelection().isEmpty()) {
                    return;
                }
                IStructuredSelection structuredSel =
                        (IStructuredSelection) viewer.getSelection();
                Iterator<IProject> iterator = structuredSel.iterator();
                List<IProject> selectedProjects = new ArrayList<IProject>();
                while (iterator.hasNext()) {
                    selectedProjects.add(iterator.next());
                }
                IProject[] selProjectArray =
                        new IProject[selectedProjects.size()];
                selectedProjects.toArray(selProjectArray);
                ProjectViewer.this.fillContextMenu(manager, selProjectArray);

            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        menuMgr.setRemoveAllWhenShown(true);
        viewer.getControl().setMenu(menu);

        site.registerContextMenu(menuMgr, viewer);
    }

    private void hookToolBar(Section section, IWorkbenchPartSite site) {
        ToolBarManager toolBarManager = new ToolBarManager();

        /*
         * Add refresh workspace action
         */
        Action refreshAction =
                new Action(Messages.ProjectViewer_refreshProject_action) {
                    /**
                     * @see org.eclipse.jface.action.Action#run()
                     * 
                     */
                    @Override
                    public void run() {
                        RefreshWorkspaceAction.scheduleWorkspaceRefresh();
                    }
                };
        refreshAction
                .setToolTipText(Messages.ProjectViewer_refreshProject_action_tooltip);
        refreshAction.setImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.REFRESH.getPath()));
        toolBarManager.add(refreshAction);

        /*
         * Add new project action
         */
        newProjectAction = new NewProjectAction(site.getWorkbenchWindow());
        toolBarManager.add(newProjectAction);

        ToolBar toolBar = toolBarManager.createControl(section);
        section.setTextClient(toolBar);
    }

    public void updateToolBarActions(IStructuredSelection selection) {
    }

    private void fillContextMenu(IMenuManager manager, IProject[] project) {
        MenuManager menuMgr =
                new MenuManager("Team", ProjectViewer.TEAM_RCP_MENU_ID); //$NON-NLS-1$
        manager.add(menuMgr);
        boolean svnProject = SVNUtils.areAllProjectsUnderSVNControl(project);
        int selectedProjectsCount = project.length;
        if (svnProject) {
            svnCommitAction.setSelectedProject(project);
            menuMgr.add(svnCommitAction);
            svnUpdateAction.setSelectedProject(project);
            menuMgr.add(svnUpdateAction);
            svnRevertAction.setSelectedProject(project);
            menuMgr.add(svnRevertAction);
            if (selectedProjectsCount == 1) {
                svnConflictExportAction.setSelectedProject(project);
                menuMgr.add(svnConflictExportAction);
                svnConflictExportAction.setEnabled(true);
            } else {
                svnConflictExportAction.setEnabled(false);
            }

        } else {
            svnShareAction.setSelectedProject(project);
            menuMgr.add(svnShareAction);
            svnShareAction.setEnabled(true);
            if (selectedProjectsCount > 1) {
                // only one project can be shared with SVN at a given moment of
                // time
                svnShareAction.setEnabled(false);
            }
        }
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    private void makeActions() {
        // SVN Commit
        svnCommitAction = new RCPTeamAction() {
            @Override
            public void run() {
                SVNCommitAction commitAction = new SVNCommitAction();
                IProject[] selectedProjects =
                        svnCommitAction.getSelectedProject();
                commitAction.setSelectedResources(selectedProjects);
                commitAction.run(this);
            }
        };
        svnCommitAction.setText(Messages.ProjectViewer_commit_action);
        // SVN Update to HEAD
        svnUpdateAction = new RCPTeamAction() {
            @Override
            public void run() {
                UpdateAction updateAction = new UpdateAction();
                IProject[] selectedProjects =
                        svnUpdateAction.getSelectedProject();
                updateAction.setSelectedResources(selectedProjects);
                updateAction.run(this);
            }
        };
        svnUpdateAction.setText(Messages.ProjectViewer_updateToHead_action);
        // SVN Revert
        svnRevertAction = new RCPTeamAction() {
            @Override
            public void run() {
                SVNRevertAction revertAction = new SVNRevertAction();
                IProject[] selectedProjects =
                        svnRevertAction.getSelectedProject();
                revertAction.setSelectedResources(selectedProjects);
                revertAction.run(this);
            }
        };
        svnRevertAction.setText(Messages.ProjectViewer_revert_action);
        // SVN Share Action
        svnShareAction = new RCPTeamAction() {
            @Override
            public void run() {
                SVNSharingWizard sharingWizard = new SVNSharingWizard();
                sharingWizard.init(PlatformUI.getWorkbench(),
                        svnShareAction.getSelectedProject()[0]);
                Shell activeShell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                WizardDialog dlg = new WizardDialog(activeShell, sharingWizard);
                dlg.setPageSize(0, 200);
                dlg.open();
            }

        };
        svnShareAction.setText(Messages.ProjectViewer_shareProject_action);
        // SVN Share Action
        // SVN Conflict Export Action
        Shell sh =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        svnConflictExportAction = new SVNConflictAction(sh);
        svnConflictExportAction
                .setText(Messages.ProjectViewer_exportMAAWithSVNMetaInformation_action);
        // SVN Conflict Export Action

    }

    private LabelProvider getLabelProvider() {

        if (workspaceLabelProvider == null) {
            workspaceLabelProvider = new WorkspaceResourceLabelProvider();

            labelProviderListener = new ILabelProviderListener() {

                @Override
                public void labelProviderChanged(LabelProviderChangedEvent event) {

                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        Object[] elements = event.getElements();

                        if (elements != null) {
                            for (Object object : elements) {
                                if (object instanceof IProject) {
                                    viewer.refresh(object);
                                }
                            }
                        } else {
                            viewer.refresh();
                        }
                    }
                }
            };

            workspaceLabelProvider.addListener(labelProviderListener);
        }

        return workspaceLabelProvider;
    }

    /**
     * @see org.eclipse.swt.widgets.Widget#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (workspaceLabelProvider != null) {
            workspaceLabelProvider.removeListener(labelProviderListener);
        }
        super.dispose();
    }

    private class ContentProvider implements ITreeContentProvider {

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Collection<?>) {
                return ((Collection<?>) inputElement).toArray();
            }
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {

        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         * 
         * @param parentElement
         * @return
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Object getParent(Object element) {
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public boolean hasChildren(Object element) {
            return false;
        }

    }

    private class RCPTeamAction extends Action {
        private IProject[] selectedProject = null;

        public void setSelectedProject(IProject[] project) {
            this.selectedProject = project;
        }

        public IProject[] getSelectedProject() {
            return this.selectedProject;
        }
    }

    private class SVNConflictAction extends SaveAsMaaAction {

        private IProject selectedProject[] = null;

        /**
         * @param shell
         */
        public SVNConflictAction(Shell shell) {
            super(shell);
        }

        public void setSelectedProject(IProject[] project) {
            this.selectedProject = project;
        }

        public IProject[] getSelectedProject() {
            return this.selectedProject;
        }

        /**
         * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsMaaAction#export(com.tibco.xpd.rcp.internal.resources.IRCPContainer,
         *      java.io.File, org.eclipse.core.runtime.IProgressMonitor)
         * 
         *      The only project that is selected should be exported in the
         *      Archive
         * 
         * @param resource
         * @param target
         * @param monitor
         * @throws CoreException
         */
        @Override
        protected void export(IRCPContainer resource, File target,
                IProgressMonitor monitor) throws CoreException {
            List<IProject> toArchiveList = new ArrayList<IProject>();
            /** assuming only one project has been selected **/
            ProjectResource toArchive =
                    getProjectResource(resource,
                            getSelectedProject()[0].getName());
            toArchiveList.add(toArchive.getProject());
            if (includeReferencedProjects) {
                IProject[] referencedProjects =
                        toArchive.getProject().getReferencedProjects();
                if (referencedProjects != null) {
                    for (IProject iProject : referencedProjects) {
                        if (!iProject.getName().startsWith(DOT)) {
                            toArchiveList.add(iProject);
                        }
                    }
                }
            }

            /*
             * (XPD-4413) Use the archiver that will archive ALL files,
             * including team related, hidden and phantom.
             */
            ZipArchiver archiver = new ZipArchiver(target);
            try {
                archiver.createArchive(toArchiveList
                        .toArray(new IProject[toArchiveList.size()]));
            } catch (CoreException e) {
                if (target.exists()) {
                    target.delete();
                }
                throw e;
            } catch (FileNotFoundException e) {
                // Unlikely to happen
                e.printStackTrace();
            }
        }

        private ProjectResource getProjectResource(IRCPContainer resource,
                String projectName) {
            Collection<ProjectResource> projectResources =
                    resource.getProjectResources();
            ProjectResource toReturn = null;
            for (ProjectResource projectResource : projectResources) {
                if (projectResource.getProject().getName().equals(projectName)) {
                    toReturn = projectResource;
                    break;
                }
            }
            return toReturn;
        }

        /**
         * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsMaaAction#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return null;
        }
    }
}
