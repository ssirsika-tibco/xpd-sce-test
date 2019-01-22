/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.progress.DeferredTreeContentManager;
import org.tigris.subversion.subclipse.core.ISVNRemoteFolder;
import org.tigris.subversion.subclipse.core.ISVNRepositoryLocation;
import org.tigris.subversion.subclipse.ui.ISVNUIConstants;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.subclipse.ui.repository.RepositoryFilters;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * 
 * copy of
 * org.tigris.subversion.subclipse.ui.wizards.CheckoutWizardSelectionPage with
 * some minor modifications
 * 
 * @author kupadhya
 * @since 11 Dec 2012
 */
public class SVNCheckoutWizardSelectionPage extends WizardPage {
    private static final int LIST_HEIGHT_HINT = 250;

    private static final int LIST_WIDTH_HINT = 450;

    private ISVNRepositoryLocation repositoryLocation;

    private TreeViewer treeViewer;

    private Map<String, Boolean> studioProjectCache =
            new HashMap<String, Boolean>();

    public SVNCheckoutWizardSelectionPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(pageName, title, titleImage);
        setPageComplete(false);
    }

    @Override
    public void createControl(Composite parent) {
        Composite outerContainer = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        outerContainer.setLayout(layout);
        outerContainer.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        treeViewer =
                new TreeViewer(outerContainer, SWT.H_SCROLL | SWT.V_SCROLL
                        | SWT.MULTI | SWT.BORDER);
        RepositoryContentProvider contentProvider =
                new RepositoryContentProvider();
        treeViewer.setContentProvider(contentProvider);
        treeViewer.addFilter(RepositoryFilters.FOLDERS_ONLY);
        treeViewer.setLabelProvider(new RCPSVNTreeLabelProvider());
        treeViewer.setInput(repositoryLocation);

        GridData data =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
        data.heightHint = LIST_HEIGHT_HINT;
        data.widthHint = LIST_WIDTH_HINT;
        treeViewer.getControl().setLayoutData(data);

        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                setPageComplete(false);
                setErrorMessage(null);
                SVNCheckOutWizard wizard = (SVNCheckOutWizard) getWizard();
                final List<ISVNRemoteFolder> folderArray =
                        new ArrayList<ISVNRemoteFolder>();
                IStructuredSelection selection =
                        (IStructuredSelection) treeViewer.getSelection();
                Iterator iter = selection.iterator();
                while (iter.hasNext()) {
                    Object object = iter.next();
                    if (object instanceof ISVNRemoteFolder
                            || object instanceof ISVNRepositoryLocation) {
                        if (object instanceof ISVNRepositoryLocation)
                            folderArray.add(((ISVNRepositoryLocation) object)
                                    .getRootFolder());
                        else
                            folderArray.add((ISVNRemoteFolder) object);
                    }
                }
                final boolean[] allStudioProjects = new boolean[] { true };
                try {
                    getContainer().run(true, true, new IRunnableWithProgress() {
                        /**
                         * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
                         * 
                         * @param monitor
                         * @throws InvocationTargetException
                         * @throws InterruptedException
                         */
                        @Override
                        public void run(IProgressMonitor monitor)
                                throws InvocationTargetException,
                                InterruptedException {
                            monitor.beginTask(Messages.SVNCheckoutWizardSelectionPage_EnsureCurrentSelectionIsStudioProject,
                                    100);
                            monitor.worked(10);
                            int incrementalChunk = 60 / folderArray.size();
                            for (ISVNRemoteFolder svnRemoteFolder : folderArray) {
                                // just to show some initial progress
                                Boolean isStudioProject =
                                        isStudioProject(svnRemoteFolder);
                                if (!isStudioProject) {
                                    allStudioProjects[0] = false;
                                    break;
                                }
                                monitor.worked(incrementalChunk);
                            }
                            monitor.worked(30);
                            monitor.done();
                        }

                    });
                } catch (InvocationTargetException e) {
                } catch (InterruptedException e) {
                }

                if (allStudioProjects[0]
                        && !treeViewer.getSelection().isEmpty()) {
                    ISVNRemoteFolder[] remoteFolders =
                            new ISVNRemoteFolder[folderArray.size()];
                    folderArray.toArray(remoteFolders);
                    wizard.setRemoteFolders(remoteFolders);
                    setPageComplete(true);
                } else {
                    setPageComplete(false);
                    setErrorMessage(Messages.SVNCheckoutWizardSelectionPage_SelectOneOrMoreStudioProjects);
                }
            }
        });

        final Action refreshAction =
                new Action(
                        Messages.SVNRepositoriesView_refreshPopup,
                        SVNUIPlugin
                                .getPlugin()
                                .getImageDescriptor(ISVNUIConstants.IMG_REFRESH)) {
                    @Override
                    public void run() {
                        refreshViewerNode();
                    }
                };
        MenuManager menuMgr = new MenuManager();
        Tree tree = treeViewer.getTree();
        Menu menu = menuMgr.createContextMenu(tree);
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                manager.add(refreshAction);
            }

        });
        menuMgr.setRemoveAllWhenShown(true);
        tree.setMenu(menu);

        setMessage(Messages.SVNCheckoutWizardSelectionPage_text);

        setControl(outerContainer);
    }

    @Override
    public boolean canFlipToNextPage() {
        SVNCheckOutWizard wizard = (SVNCheckOutWizard) getWizard();
        if (wizard != null) {
            return isPageComplete() && wizard.getNextPage(this, false) != null;
        }
        return super.canFlipToNextPage();
    }

    public void setLocation(ISVNRepositoryLocation repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
        if (treeViewer != null) {
            treeViewer.setInput(repositoryLocation.getLocation());
            treeViewer.refresh();
            treeViewer.expandToLevel(2);
        }
    }

    protected void refreshViewerNode() {
        IStructuredSelection selection =
                (IStructuredSelection) treeViewer.getSelection();
        Iterator iter = selection.iterator();
        while (iter.hasNext()) {
            Object object = iter.next();
            if (object instanceof ISVNRepositoryLocation)
                ((ISVNRepositoryLocation) object).refreshRootFolder();
            if (object instanceof ISVNRemoteFolder)
                ((ISVNRemoteFolder) object).refresh();
            treeViewer.refresh(object);
        }
    }

    /**
     * @param svnRemoteFolder
     * @return
     */
    private Boolean isStudioProject(ISVNRemoteFolder svnRemoteFolder) {
        Boolean isStudioProject =
                studioProjectCache.get(svnRemoteFolder.getUrl().toString());
        if (isStudioProject == null) {
            isStudioProject = SVNUtils.isXPDProjectPresent(svnRemoteFolder);
            studioProjectCache.put(svnRemoteFolder.getUrl().toString(),
                    isStudioProject);
        }
        return isStudioProject;
    }

    class RepositoryContentProvider extends WorkbenchContentProvider {
        private DeferredTreeContentManager manager;

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            if (viewer instanceof AbstractTreeViewer) {
                manager =
                        new DeferredTreeContentManager(this,
                                (AbstractTreeViewer) viewer);
            }
            super.inputChanged(viewer, oldInput, newInput);
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element == null)
                return false;
            else
                return true;
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof String) {
                Object[] root = { repositoryLocation };
                return root;
            }
            if (parentElement instanceof ISVNRemoteFolder && manager != null) {
                boolean isStudioProject =
                        isStudioProject((ISVNRemoteFolder) parentElement);
                if (isStudioProject) {
                    return new Object[] {};
                } else {
                    Object[] children = manager.getChildren(parentElement);
                    return children;
                }
            }

            if (manager != null) {
                Object[] children = manager.getChildren(parentElement);
                return children;
            }
            return super.getChildren(parentElement);
        }

    }

}
