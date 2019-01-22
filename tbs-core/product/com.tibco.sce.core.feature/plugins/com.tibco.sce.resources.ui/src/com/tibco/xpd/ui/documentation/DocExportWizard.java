/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.documentation.DocumentViewerContributionHelper;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.actions.ExportDocAction;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.IExportDataProvider;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export wizard to for the generation of documentation.
 * 
 * @author mtorres
 * 
 */
public class DocExportWizard extends AbstractExportWizard {

    private static final String WEB_DAV_DEPLOYABLE_SF_KIND = "webdavDeployable"; //$NON-NLS-1$

    private static final String IMG_LOC = "icons/obj16/DocumentationIcon.png"; //$NON-NLS-1$

    private static final String BROWSEDESTFOLDERKEY = "DESTBROWSEFOLDER"; //$NON-NLS-1$

    protected IStructuredSelection selection;

    protected ExportDocAction action;

    protected IPath selectedExportPath = null;

    /**
     * Export Doc Wizard.
     */
    public DocExportWizard() {
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.DocExportWizard_ExportDocumentationTitle);
        setDefaultPageImageDescriptor(XpdResourcesUIActivator
                .imageDescriptorFromPlugin(XpdResourcesUIActivator.ID, IMG_LOC));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        // Default implementation is to check if all pages are complete.
        IWizardPage[] pages = getPages();
        for (int i = 0; i < pages.length; i++) {
            if (!pages[i].isPageComplete()) {
                return false;
            }
        }
        boolean bRet = true;
        if (bRet
                && (getSelectedFiles() == null || getSelectedFiles().isEmpty())) {
            bRet = false;
        }
        return bRet;
    }

    @Override
    protected void performOperation(final IProgressMonitor monitor)
            throws CoreException, InterruptedException {
        if (action == null) {
            action = new ExportDocAction();
        }
        selection = getSelectedFiles();
        IProject project = null;
        // determine if user selects manual location or the default
        // workspace export location
        String path = getPreference(BROWSEDESTFOLDERKEY);
        ExportDestination dest = getExportDestinationSelection();

        // set the output location accordingly
        if (dest.equals(ExportDestination.PROJECT)) {
            if (selection != null
                    && selection.getFirstElement() instanceof IFile) {
                final IFile firstModelFile =
                        (IFile) selection.getFirstElement();
                project = firstModelFile.getProject();
                if (project != null) {
                    IFolder folder =
                            project.getFolder(getWorkspaceExportFolder());

                    createFolders(folder);

                    setupWorkspaceExportFolder(folder);

                    selectedExportPath =
                            project.getFullPath()
                                    .append(getWorkspaceExportFolder())
                                    .addTrailingSeparator();
                    IPath relativeRoot =
                            project.getProject().getLocation()
                                    .append(getWorkspaceExportFolder());
                    action.setOutputPath(relativeRoot);
                }
            }
        } else {
            String rootPath = path + File.separator;
            selectedExportPath = new Path(rootPath);
            action.setOutputPath(selectedExportPath);
        }
        List<IResource> selectedResources = new ArrayList<IResource>();
        if (selection != null) {
            Iterator iterator = selection.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof IResource) {
                    selectedResources.add((IResource) next);
                }
            }
            action.setSelectedResources(selectedResources);
        }
        Display display = getShell().getDisplay();
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                overwriteFileMessageDialog(action.getOutputPath());
            }
        });
        try {
            if (isOutputPathEmpty(action.getOutputPath())) {
                action.run(monitor);
            } else if (IDialogConstants.OK_ID == XpdResourcesUIActivator
                    .getDefault()
                    .getPreferenceStore()
                    .getInt(PreferenceStoreKeys.OVERWRITE_WHEN_EXPORTING_DOCUMENTATION_VALUE)) {
                action.run(monitor);
            } else {
                throw new OperationCanceledException();
            }
        } catch (InterruptedException e) {
            throw new OperationCanceledException();
        }
    }

    public IPath getSelectedExportPath() {
        return selectedExportPath;
    }

    @Override
    public String getExportFileExt() {
        return "html"; //$NON-NLS-1$
    }

    @Override
    protected ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        /*
         * SCF-440: Only show open XPD projects.
         */
        filters[0] = new XpdNatureProjectsOnly(false);

        // Only include contributed special folders
        Set<String> allContributedSpecialFolders =
                DocumentViewerContributionHelper
                        .getAllContributedSpecialFolders();
        if (allContributedSpecialFolders != null
                && !allContributedSpecialFolders.isEmpty()) {
            filters[1] =
                    new SpecialFoldersOnlyFilter(allContributedSpecialFolders); //$NON-NLS-1$
        }

        // Only include files with the right file extension
        Set<String> allContributedFileExtensions =
                DocumentViewerContributionHelper
                        .getAllContributedFileExtensions();
        if (allContributedFileExtensions != null
                && !allContributedFileExtensions.isEmpty()) {
            filters[2] =
                    new FileExtensionInclusionFilter(
                            allContributedFileExtensions); //$NON-NLS-1$
        }

        // Don't drill down into the files
        filters[3] = new NoFileContentFilter();

        return filters;
    }

    @Override
    public Map<String, String> getXsltParameters() {
        // Do nothing, not used
        return null;
    }

    @Override
    public URL[] getXslts() {
        // Do nothing, not used
        return null;
    }

    @Override
    public void addPages() {
        // Add the selection/destination page
        DocExportWizardPage pageIO =
                new DocExportWizardPage(getInitialSelection(), getInput(),
                        getSorter(), getFilters(), new IExportDataProvider() {
                            @Override
                            public String getPreference(String key) {
                                return DocExportWizard.this.getPreference(key);
                            }

                            @Override
                            public String getWorkspaceExportFolder() {
                                return DocExportWizard.this
                                        .getWorkspaceExportFolder();
                            }

                            @Override
                            public void setPreference(String key, String value) {
                                DocExportWizard.this.setPreference(key, value);
                            }
                        });
        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(getWindowMessage());
        pageIO.setEmptySelectionAnError(isEmptySelectionAnError());
        setPageIO(pageIO);
        addPage(pageIO);
    }

    @Override
    protected String getInitialRootExportFolder() {
        return "/" + Messages.AbstractExportWizard_exportDocumentationFolder_label; //$NON-NLS-1$
    }

    private boolean isOutputPathEmpty(IPath outputPath) {
        if (outputPath != null) {
            File file = outputPath.toFile();
            if (file != null && file.exists()
                    && (file.list() == null || file.list().length > 0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Show the overwrite files message dialog
     * 
     * @return <code>{@link OverwriteStatus}</code> value of the selection made
     *         in the dialog
     */
    protected void overwriteFileMessageDialog(IPath outputPath) {
        if (outputPath != null) {
            File file = outputPath.toFile();
            if (file != null && file.exists()
                    && (file.list() == null || file.list().length > 0)) {
                IPreferenceStore preferenceStore =
                        XpdResourcesUIActivator.getDefault()
                                .getPreferenceStore();
                boolean prefAskOverwrite =
                        preferenceStore
                                .getBoolean(PreferenceStoreKeys.DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION);
                if (!prefAskOverwrite) {

                    MessageDialogWithToggle dialog =
                            MessageDialogWithToggle
                                    .openOkCancelConfirm(Display.getDefault()
                                            .getActiveShell(),
                                            Messages.AbstractExportWizard_destFileExistsDlg_title,
                                            Messages.AbstractExportWizard_destFolderNotEmptyDlg_message,
                                            Messages.AbstractExportWizard_dontAskToOverwrite_message,
                                            prefAskOverwrite,
                                            preferenceStore,
                                            PreferenceStoreKeys.DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION);
                    prefAskOverwrite = dialog.getToggleState();
                    preferenceStore
                            .setValue(PreferenceStoreKeys.DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION,
                                    prefAskOverwrite);

                    preferenceStore
                            .setValue(PreferenceStoreKeys.OVERWRITE_WHEN_EXPORTING_DOCUMENTATION_VALUE,
                                    dialog.getReturnCode());
                }
            }
        }
    }

    @Override
    protected boolean isEmptySelectionAnError() {
        return true;
    }

    protected void setupWorkspaceExportFolder(IFolder exportFolder) {
        if (exportFolder != null && exportFolder.exists()) {
            SpecialFolderUtil.getCreateSpecialFolderOfKind(exportFolder
                    .getProject(), WEB_DAV_DEPLOYABLE_SF_KIND, exportFolder
                    .getName());
        }
    }

}
