/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;
import com.tibco.xpd.ui.importexport.exportwizard.FileExportWizard;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export wizard to generated XSD schemas from selected BOM files.
 * 
 * @author njpatel
 * 
 */
public class ExportBOMtoXSDWizard extends FileExportWizard {

    private static final String IMG_LOC = "icons/BOMExport.png"; //$NON-NLS-1$

    private OverwriteStatus overwriteDestFile;

    public ExportBOMtoXSDWizard() {
        setWindowTitle(Messages.ExportToXSDWizard_WindowTitle);
        setWindowMessage(Messages.ExportBOMtoXSDWizard_shortdesc);
        setDefaultPageImageDescriptor(Activator
                .imageDescriptorFromPlugin(com.tibco.xpd.bom.xsdtransform.Activator.PLUGIN_ID,
                        IMG_LOC));
        setWorkspaceExportFolder(Activator.EXPORT_FOLDER);
        setSelectionValidator(new XSDExportSelectionValidator());
    }

    @Override
    public void addPages() {
        /*
         * if auto-build is disabled then add the build page
         */
        if (!ExportBOMUtil.isAutoBuildEnabled()) {
            addPage(new BuildPage("build")); //$NON-NLS-1$
        }

        super.addPages();
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {
        // Set the overwrite flag to a default value of overwrite
        overwriteDestFile = OverwriteStatus.FILE;
        IStructuredSelection selectedFiles = getSelectedFiles();
        Set<IFile> rootSelectedFiles =
                TransformHelper.getRootBomsForBom2XsdTransform(selectedFiles
                        .toList());
        selectedFiles = new StructuredSelection(rootSelectedFiles.toArray());

        // calculate the number of tasks to set for the progress
        // monitor
        int monitorTasks = selectedFiles.size() * 2 + 1;

        // If we have subtasks then multiply the task with this
        // number
        monitorTasks += (selectedFiles.size() * subTasks.size());

        monitor.beginTask(Messages.ExportBOMtoXSDWizard_exportingTask_title,
                monitorTasks);

        // Set the overwrite flag to a default value of overwrite
        overwriteDestFile = OverwriteStatus.FILE;

        IOutputFile<?> outputFile = null;
        
        try {
            for (Iterator<?> iter = selectedFiles.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (monitor.isCanceled()) {
                    // User selected to cancel
                    throw new OperationCanceledException();
                }
                monitor.worked(1);
                if (obj instanceof IResource) {
                    exportFile((IFile) obj, monitor);
                }
            }
        } finally {
            /*
             * If the export destination is the workspace then re-sync the
             * workspace with the file system if sub-tasks were executed. This
             * is needed as the sub tasks may have added more resources to the
             * export destination.
             */
            if (subTasks != null && outputFile instanceof WorkspaceOutputFile) {
                IContainer container =
                        ((WorkspaceOutputFile) outputFile).getFile()
                                .getParent();
                if (container != null
                        && !container.isSynchronized(IResource.DEPTH_INFINITE)) {
                    // Synch the container with the file system
                    container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                }
            }

            if (monitor != null)
                monitor.done();
        }
    }

    @Override
    public String getExportFileExt() {
        return Activator.XSD_FILE_EXT;
    }

    @Override
    protected ViewerFilter[] getFilters() {
        return new ViewerFilter[] {
                new XpdNatureProjectsOnly(false),
                new SpecialFoldersOnlyFilter(
                        Collections
                                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)),
                new NoFileContentFilter(), new ViewerFilter() {
                    @Override
                    public boolean select(Viewer viewer, Object parentElement,
                            Object element) {
                        if (element instanceof IFile
                                && !((IFile) element)
                                        .getFileExtension()
                                        .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                            return false;
                        }
                        return true;
                    }
                } };
    }

    @Override
    protected void exportFile(final IFile inputFile, IProgressMonitor monitor)
            throws CoreException {
        /*
         * If the overwrite file message was shown and NO selected then the
         * value needs to be reset back to default, otherwise no further files
         * will be processed
         */
        if (overwriteDestFile == OverwriteStatus.NO)
            overwriteDestFile = OverwriteStatus.FILE;

        // Update progress monitor
        monitor.subTask(inputFile.getName());

        // Get the output file names
        Collection<String> fileNames =
                XSDUtil.getOutputFileNamesForBOMFile(inputFile);
        if (fileNames != null && !fileNames.isEmpty()) {
            if (overwriteDestFile != OverwriteStatus.ALL_FILES) {
                // Check if any files exist and if they do ask user if they can
                // be overwritten
                if (filesExist(inputFile.getProject(), fileNames)) {
                    // Ask user if the existing file can be
                    // overwritten
                    getShell().getDisplay().syncExec(new Runnable() {
                        @Override
                        public void run() {
                            overwriteDestFile =
                                    overwriteFileMessageDialog(inputFile);
                        }
                    });
                }
            }

            // Check whether we can process the file
            if (overwriteDestFile.canOverwrite()) {
                // Sub-tasks may have reset the subtask name.
                monitor.subTask(inputFile.getName());

                processFile(inputFile, null, monitor);
                monitor.worked(1);
            }

            // If the overwrite file dialog was shown and the
            // user cancelled then stop the export process
            if (overwriteDestFile == OverwriteStatus.CANCEL) {
                throw new OperationCanceledException();
            }
        }
    }

    /**
     * Get the selected output path of the export.
     * 
     * @param project
     * @return
     */
    private IPath getOutputPath(IProject project) {
        if (project != null) {
            if (isWorkspaceRelativeOutputPath()) {
                return project.getFolder(getWorkspaceExportFolder())
                        .getFullPath();

            } else {
                return getDestinationPath();
            }
        }
        return null;
    }

    /**
     * @return <code>true</code> if output folder is in workspace else
     *         <code>false</code> if in outside file system
     */
    protected boolean isWorkspaceRelativeOutputPath() {
        return getExportDestinationSelection() == ExportDestination.PROJECT;
    }

    @Override
    protected void processFile(IFile inputFile, IOutputFile<?> outputFile,
            IProgressMonitor monitor) throws CoreException {
        IPath exportPath = getOutputPath(inputFile.getProject());

        if (exportPath != null) {
            List<IStatus> result =
                    XSDUtil.transformBOMToXSD(inputFile,
                            exportPath,
                            true,
                            isWorkspaceRelativeOutputPath());

            inputFile.getProject()
                    .refreshLocal(IFolder.DEPTH_INFINITE, monitor);
            if (!result.isEmpty()) {
                // If there are errors in the result then throw exception
                List<IStatus> errors = new ArrayList<IStatus>();
                for (IStatus res : result) {
                    if (res.getSeverity() == IStatus.ERROR) {
                        errors.add(res);
                    }
                }
                if (!errors.isEmpty()) {
                    throw new CoreException(
                            new MultiStatus(
                                    Activator.PLUGIN_ID,
                                    IStatus.ERROR,
                                    errors.toArray(new IStatus[errors.size()]),
                                    Messages.ExportClassToXSDWizard_generatorErrors_shortdesc,
                                    null));
                }
            }
        } else {
            throw new NullPointerException(
                    Messages.ExportBOMtoXSDWizard_nullExportPath_error_message);
        }
    }

    /**
     * Check if the files that will be generated for the selected BOM already
     * exist.
     * 
     * @param project
     * @param fileNames
     * @return
     * @throws CoreException
     */
    private boolean filesExist(IProject project, Collection<String> fileNames)
            throws CoreException {

        if (project != null && fileNames != null && !fileNames.isEmpty()) {
            IPath targetLocation = null;
            if (isWorkspaceRelativeOutputPath()) {
                IFolder folder = project.getFolder(getWorkspaceExportFolder());
                if (folder.exists()) {
                    targetLocation = folder.getFullPath();
                    for (String fileName : fileNames) {
                        IResource member = folder.findMember(fileName);
                        if (member instanceof IFile && member.exists()) {
                            return true;
                        }
                    }
                }
            } else /* export to given location */{
                targetLocation = getDestinationPath();
                if (targetLocation != null) {
                    for (String fileName : fileNames) {
                        File file = targetLocation.append(fileName).toFile();
                        if (file != null && file.exists()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Show the overwrite file message dialog
     * 
     * @param inputFile
     * 
     * @return <code>{@link OverwriteStatus}</code> value of the selection made
     *         in the dialog
     */
    private OverwriteStatus overwriteFileMessageDialog(IFile inputFile) {
        OverwriteFileMessageDialog dialog =
                new OverwriteFileMessageDialog(
                        getShell(),
                        Messages.ExportBOMtoXSDWizard_fileExists_dialog_title,
                        String.format(Messages.ExportBOMtoXSDWizard_fileExists_dialog_longdesc,
                                inputFile.getName()));

        return dialog.getOverwriteStatus(dialog.open());
    }

}
