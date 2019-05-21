/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.TypedViewerFilter;
import com.tibco.xpd.ui.resources.TypedElementSelectionValidator;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick fix to move all files of a given asset to a different project
 *
 * @author aallway
 * @since 21 May 2019
 */
public abstract class AbstractMoveAssetsToOwnProjectResolution
        implements IResolution {

    private String dialogTitle;

    private String noTargetProjectsMessage;

    private String successFullyMovedMessage;

    private String errorMovingAssetsMessage;

    /**
     * @param dialogTitle
     * @param noTargetProjectsMessage
     * @param successFullyMovedMessage
     * @param errorMovingAssetsMessage
     */
    protected AbstractMoveAssetsToOwnProjectResolution(String dialogTitle,
            String noTargetProjectsMessage, String successFullyMovedMessage,
            String errorMovingAssetsMessage) {
        super();
        this.dialogTitle = dialogTitle;
        this.noTargetProjectsMessage = noTargetProjectsMessage;
        this.successFullyMovedMessage = successFullyMovedMessage;
        this.errorMovingAssetsMessage = errorMovingAssetsMessage;
    }

    /**
     * Check if the given project is a suitable target for the asset types in
     * question.
     * 
     * @param project
     * @return <code>true</code> if the given project is a suitable target for
     *         the asset types in question.
     */
    protected abstract boolean isAppropriateTargetProject(IProject project);

    /**
     * 
     * @param srcSpecialFolder
     * @return All of the source assets to move from the given source special
     *         folder.
     */
    protected abstract Collection<IResource> getSourceAssetsToMove(
            SpecialFolder srcSpecialFolder);

    /**
     * @return The special folder kind we're interested in.
     */
    protected abstract String getSpecialFolderKind();

    /**
     * @return The special folder kind we're interested in.
     */
    protected abstract String getAssetType();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    @Override
    public void run(final IMarker marker) throws ResolutionException {

        if (marker.getResource() instanceof IProject) {
            final IProject project = (IProject) marker.getResource();
            try {
                /*
                 * Ask user for the target special folder.
                 */
                SpecialFolder tgtSpecialFolder =
                        getTargetSpecialFolder(project);

                if (tgtSpecialFolder != null) {
                    /*
                     * Go thru all the source project's special folders moving
                     * all assets from them into target.
                     */
                    List<SpecialFolder> srcSpecialFolders = SpecialFolderUtil
                            .getAllSpecialFoldersOfKind(project,
                                    getSpecialFolderKind());

                    if (srcSpecialFolders != null) {
                        for (SpecialFolder srcSpecialFolder : srcSpecialFolders) {
                            moveAssetsToUserFolder(srcSpecialFolder,
                                    tgtSpecialFolder);
                        }
                    }

                    /*
                     * Clean up the existing project (removing assets, special
                     * folders, builders etc)
                     */
                    cleanupSourceProject(project, srcSpecialFolders);

                    /*
                     * Finally, add a dependency on the chosen project.
                     */
                    IProjectDescription description = project.getDescription();

                    ArrayList<IProject> projectDependencies =
                            new ArrayList<IProject>(Arrays.asList(
                                    description.getReferencedProjects()));

                    IProject tgtProject = tgtSpecialFolder.getProject();
                    if (!projectDependencies.contains(tgtProject)) {
                        projectDependencies.add(tgtProject);

                        description.setReferencedProjects(
                                projectDependencies.toArray(new IProject[0]));

                        project.setDescription(description, null);
                    }

                    /*
                     * Popup a confirmation dialog.
                     */
                    MessageDialog.openInformation(
                            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                    .getShell(),
                            dialogTitle,
                            String.format(successFullyMovedMessage,
                                    tgtProject.getName()));
                }

            } catch (Exception e) {
                BxValidationPlugin.getDefault().getLogger().error(e,
                        "Error moving assets to target project"); //$NON-NLS-1$

                MessageDialog.openInformation(
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell(),
                        dialogTitle,
                        errorMovingAssetsMessage + "\n\n" //$NON-NLS-1$
                                + e.getLocalizedMessage());
            }
        }
        return;
    }

    /**
     * Ask the user to select a target asset special folder
     * 
     * @param srcProject
     * 
     * @return the special folder chosen or <code>null</code> if user cancelled.
     */
    private SpecialFolder getTargetSpecialFolder(IProject srcProject) {
        List<IProject> targetProjects = getAllTargetProjects(srcProject);

        if (targetProjects.isEmpty()) {
            MessageDialog
                    .openInformation(
                            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                    .getShell(),
                            dialogTitle,
                            noTargetProjectsMessage);

            return null;
        }

        ISelectionStatusValidator validator =
                new TypedElementSelectionValidator(
                        new Class[] { SpecialFolder.class }, false);

        ArrayList<Object> rejectedElements = new ArrayList<Object>(0);
        ViewerFilter typedFilter = new TypedViewerFilter(
                new Class[] { IProject.class, SpecialFolder.class },
                rejectedElements.toArray());

        ElementTreeSelectionDialog dialog =
                new ElementTreeSelectionDialog(
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell(),
                        new BrowseDialogLabelProvider(),
                        new BrowseDialogContentProvider());
        dialog.setTitle(dialogTitle);
        dialog.setValidator(validator);
        dialog.addFilter(typedFilter);
        dialog.setInput(targetProjects);
        dialog.setAllowMultiple(false);

        if (dialog.open() == Dialog.OK) {
            Object selection = dialog.getFirstResult();

            if (selection instanceof SpecialFolder) {
                return (SpecialFolder) selection;
            }
        }

        /* User cancelled. */
        return null;
    }

    /**
     * 
     * @param srcProject
     * @return All of the open projects in workspace of appropriate type to move
     *         the assets to.
     */
    private List<IProject> getAllTargetProjects(IProject srcProject) {
        List<IProject> targetProjects = new ArrayList<IProject>();

        for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
                .getProjects()) {

            if (!srcProject.equals(project) && project.isAccessible()
                    && isAppropriateTargetProject(project)) {
                targetProjects.add(project);
            }
        }

        return targetProjects;
    }

    /**
     * 
     * @param projectConfig
     * @param assetId
     * 
     * @return <code>true</code> if project has given asset type
     */
    protected boolean hasAssetType(ProjectConfig projectConfig,
            String assetId) {
        for (AssetType asset : projectConfig.getAssetTypes()) {
            if (assetId.equals(asset.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move all the assets in the given source project special folder to the
     * target project
     * 
     * @param srcSpecialFolder
     * @param tgtSpecialFolder
     * @throws CoreException
     */
    private void moveAssetsToUserFolder(SpecialFolder srcSpecialFolder,
            SpecialFolder tgtSpecialFolder) throws CoreException {

        IPath srcFolderPath = srcSpecialFolder.getFolder().getFullPath();
        IFolder tgtFolder = tgtSpecialFolder.getFolder();
        IPath tgtFolderPath = tgtFolder.getFullPath();

        /* Get all asset files from source folder. */
        Collection<IResource> assetFiles =
                getSourceAssetsToMove(srcSpecialFolder);

        /* Move them to target folder, creating target folder as we go. */
        for (IResource assetFile : assetFiles) {
            IContainer assetSrcFolder = assetFile.getParent();

            IPath assetSrcParentPath = assetSrcFolder.getFullPath();
            IPath assetParentRelativePath =
                    assetSrcParentPath.makeRelativeTo(srcFolderPath);

            IPath assetTgtParentPath =
                    tgtFolderPath.append(assetParentRelativePath);

            IFolder assetTgtFolder = ResourcesPlugin.getWorkspace().getRoot()
                    .getFolder(assetTgtParentPath);
            if (!assetTgtFolder.exists()) {
                createFolder(assetTgtFolder);
            }

            IPath assetTgtFilePath =
                    assetTgtParentPath.append(assetFile.getName());
            assetFile.move(assetTgtFilePath, true, null);

        }
    }

    /**
     * Create the given folder. All non-existing parent folders will also be
     * created.
     * 
     * @param folder
     * @throws CoreException
     */
    private void createFolder(IFolder folder) throws CoreException {
        if (folder != null) {
            IContainer parent = folder.getParent();

            if (!parent.exists()) {
                if (parent instanceof IFolder) {
                    // Create the parent
                    createFolder((IFolder) parent);
                }
            }
            folder.create(false, true, null);
        }
    }

    /**
     * Clean up the existing project (removing assets, special folders, builders
     * etc)
     * 
     * @param project
     * @param srcSpecialFolders
     * @throws CoreException
     * @throws IOException
     */
    protected void cleanupSourceProject(IProject project,
            List<SpecialFolder> srcSpecialFolders)
            throws CoreException, IOException {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        SpecialFolders specialFolders = projectConfig.getSpecialFolders();
        for (Iterator<?> iterator =
                specialFolders.getFolders().iterator(); iterator.hasNext();) {
            SpecialFolder specialFolder = (SpecialFolder) iterator.next();

            if (srcSpecialFolders.contains(specialFolder)) {
                IFolder folder = specialFolder.getFolder();

                if (folder != null) {
                    folder.delete(true, null);
                }

                iterator.remove();
            }
        }

        /* Remove the asset configuration */
        for (Iterator<AssetType> iterator =
                projectConfig.getAssetTypes().iterator(); iterator.hasNext();) {
            AssetType asset = iterator.next();

            if (getAssetType().equals(asset.getId())) {
                iterator.remove();
            }
        }

        /* Save project config. */
        ProjectConfigWorkingCopy wc = (ProjectConfigWorkingCopy) WorkingCopyUtil
                .getWorkingCopyFor(projectConfig);
        wc.save();
    }

    /**
     * Content provider for the project browse dialog
     * 
     */
    private class BrowseDialogContentProvider implements ITreeContentProvider {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang
         * .Object)
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang
         * .Object)
         */
        @Override
        public Object getParent(Object element) {
            Object parent = null;
            if (element instanceof SpecialFolder) {
                // Get the project
                return ((SpecialFolder) element).getProject();

            } else if (element instanceof IResource) {
                // return ((IResource) element).getParent();
                parent = ((IResource) element).getParent();

            }

            /*
             * Our content use Special-Folder BUT the parent of a Sub-Folder is
             * just the IFolder that's the sub-folder or resource's parent
             * folder in workspace.
             * 
             * So must see if parent is a folder whether it's a special folder
             * and return that instead
             */
            if (parent instanceof IFolder) {
                String specialFolderKind = SpecialFolderUtil
                        .getSpecialFolderKind((IFolder) parent);
                if (specialFolderKind != null
                        && specialFolderKind.length() > 0) {
                    parent = SpecialFolderUtil.getSpecialFolderOfKind(
                            ((IFolder) parent).getProject(),
                            specialFolderKind);
                }
            }

            return parent;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang
         * .Object)
         */
        @Override
        public boolean hasChildren(Object element) {

            if (element instanceof IProject || element instanceof SpecialFolder
                    || element instanceof List) {
                return getElements(element) != null
                        && getElements(element).length > 0;
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
         * java.lang.Object)
         */
        @Override
        @SuppressWarnings("unchecked")
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof List) {
                return ((List) inputElement).toArray();

            } else if (inputElement instanceof IProject) {
                // Only list the Special folders of correct kind.
                List<SpecialFolder> specialFolders = SpecialFolderUtil
                        .getAllSpecialFoldersOfKind((IProject) inputElement,
                                getSpecialFolderKind());

                return specialFolders
                        .toArray(new Object[specialFolders.size()]);

            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
         * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
            // Do nothing
        }

    }

    /**
     * Label provider for the project browse dialog
     * 
     */
    private static class BrowseDialogLabelProvider extends LabelProvider {

        private WorkbenchLabelProvider provider = new WorkbenchLabelProvider();

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {

            if (element instanceof EObject) {
                return WorkingCopyUtil.getImage((EObject) element);
            } else if (provider != null) {
                return provider.getImage(element);
            }

            return super.getImage(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {
            if (element instanceof EObject) {
                return WorkingCopyUtil.getText((EObject) element);
            } else if (provider != null) {
                return provider.getText(element);
            }

            return super.getText(element);
        }
    }

}
