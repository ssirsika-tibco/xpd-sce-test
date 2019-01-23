/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.importexport.maa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * Export to MAA wizard.
 * 
 * @author bharge
 * 
 */
public class MAAFromProjectExportWizard extends Wizard implements IExportWizard {

    /** MAA resources selection page. */
    private MAAInputOutputSelectionWizardPage projectSelectionPage;

    private static final Logger LOG = XpdResourcesUIActivator.getDefault()
            .getLogger();

    private IStructuredSelection initialSelection = StructuredSelection.EMPTY;

    public MAAFromProjectExportWizard() {
        setWindowTitle(Messages.MAAFromProjectExportWizard_title);
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        boolean finish = true;
        final List<IProject> selectedProjects = getSelectedProjects();
        final DestinationLocationType exportLocationType =
                projectSelectionPage.getLocationType();
        final String exportPath = projectSelectionPage.getLocationPath();

        if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
            return false;
        }

        try {
            getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    try {
                        performOperation(selectedProjects,
                                exportLocationType,
                                exportPath,
                                monitor);
                    } catch (CoreException e) {
                        throw new InvocationTargetException(e);
                    } catch (IOException e) {
                        throw new InvocationTargetException(e);
                    }

                }
            });
        } catch (InvocationTargetException e) {
            IStatus status = null;
            if (e.getCause() instanceof CoreException) {
                status = ((CoreException) e.getCause()).getStatus();
            } else {
                status =
                        new Status(IStatus.ERROR, ImportExportPlugin.PLUGIN_ID,
                                e.getLocalizedMessage(), e.getCause());
            }

            ErrorDialog
                    .openError(getShell(),
                            Messages.MAAFromProjectExportWizard_errorCreatingMAA_dialog_title,
                            Messages.MAAFromProjectExportWizard_errorCreatingMAA_dialog_longdesc,
                            status);
            finish = false;
        } catch (InterruptedException e) {
            finish = false;
        }

        return finish;

    }

    /**
     * Perform the export operation.
     * 
     * @param selectedProjects
     * @param exportLocationType
     * @param exportPath
     * @param monitor
     * 
     * @throws CoreException
     * @throws FileNotFoundException
     */
    protected void performOperation(List<IProject> selectedProjects,
            DestinationLocationType exportLocationType, String exportPath,
            IProgressMonitor monitor) throws CoreException, IOException {

        // Validate the export path given (if exporting to external location)
        if (exportLocationType == DestinationLocationType.SYSTEM_FILE) {
            IPath path = new Path(exportPath);
            if (!MAAInputOutputSelectionWizardPage.MAA_EXT.equals(path
                    .getFileExtension())) {
                throw new CoreException(new Status(IStatus.ERROR,
                        ImportExportPlugin.PLUGIN_ID,
                        String.format(Messages.MAAFromProjectExportWizard_invalidPath_error_longdesc, exportPath)));
            }
        }

        monitor.beginTask(String
                .format(Messages.MAAFromProjectExportWizard_exporting_monitor_label,
                        exportPath),
                selectedProjects.size());

        if (exportLocationType == DestinationLocationType.PROJECT) {
            // This option is only available if a single project is selected
            IProject project = selectedProjects.get(0);
            MaaHandler handler = null;
            try {
                handler = new MaaHandler(project, exportPath);
                handler.addAllMembersToContainer(project,
                        new Path(project.getName()));
            } finally {
                monitor.done();
                if (handler != null) {
                    handler.closeContainer();
                }
            }
        } else {
            MaaHandler maaHandler = null;
            try {
                maaHandler = new MaaHandler(exportPath);

                for (IProject project : selectedProjects) {

                    monitor.subTask(String
                            .format(Messages.MAAFromProjectExportWizard_addingProjectToExport_monitor_label,
                                    project.getName()));

                    IPath pathPrefix = new Path(project.getName());
                    maaHandler.addAllMembersToContainer(project, pathPrefix);
                    monitor.worked(1);

                    // If monitor is cancelled then delete maa file and quit
                    if (monitor.isCanceled()) {
                        maaHandler.closeContainer();
                        maaHandler = null;

                        // Delete the file
                        File file = new File(exportPath);
                        if (file.exists()) {
                            file.delete();
                        }

                        throw new OperationCanceledException();
                    }
                }
            } finally {
                if (maaHandler != null) {
                    maaHandler.closeContainer();
                }
            }
        }

    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
        setDefaultPageImageDescriptor(IDEWorkbenchPlugin
                .getIDEImageDescriptor("wizban/exportzip_wiz.png"));//$NON-NLS-1$
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        projectSelectionPage =
                new MAAInputOutputSelectionWizardPage(initialSelection);
        addPage(projectSelectionPage);
    }

    protected List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<IProject>();
        for (Object o : projectSelectionPage.getSelectedObjects()) {
            if (o instanceof IProject) {
                projects.add((IProject) o);
            }
        }
        return Collections.unmodifiableList(projects);
    }

    /**
     * Show the overwrite file message dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(final IPath outputPath) {
        final OverwriteStatus[] result = new OverwriteStatus[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String msg =
                        String.format(Messages.MAAFromImportExportWizard_DestinationExistsDialog_message,
                                outputPath.lastSegment(),
                                outputPath.removeLastSegments(1).toOSString());

                OverwriteFileMessageDialog dialog =
                        new OverwriteFileMessageDialog(
                                getShell(),
                                Messages.MAAFromImportExportWizard_DestinationExistsDialog_title,
                                msg);
                OverwriteStatus status =
                        dialog.getOverwriteStatus(dialog.open());
                synchronized (this) {
                    result[0] = status;
                }
            }
        };
        Display.getDefault().syncExec(runnable);
        synchronized (runnable) {
            return result[0];
        }
    }

    private class MaaHandler {

        private static final String MODELLED_ARCHIVE_NAME = ".maa"; //$NON-NLS-1$

        private IFile workspaceMAAFile;

        private File systemMAAFile;

        private final ZipOutputStream zip;

        private static final int BUFFER_SIZE = 1024;

        /**
         * Create the MAA in the project location (in the workspace).
         * 
         * @param project
         * @param outputPath
         * @throws FileNotFoundException
         */
        public MaaHandler(IProject project, String outputPath)
                throws FileNotFoundException {
            IPath projectPath = project.getFullPath();
            IPath workspacePath =
                    projectPath.append(outputPath).append(project.getName()
                            + MODELLED_ARCHIVE_NAME);
            workspaceMAAFile =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFile(workspacePath);
            IContainer parent = workspaceMAAFile.getParent();

            if (workspaceMAAFile.exists()) {
                OverwriteStatus overwriteDestFile = OverwriteStatus.FILE;
                overwriteDestFile = overwriteFileMessageDialog(workspacePath);
                if (overwriteDestFile != OverwriteStatus.NO) {
                    try {
                        workspaceMAAFile
                                .delete(true, new NullProgressMonitor());
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                }
            }

            try {
                ProjectUtil.createFolder(parent,
                        false,
                        new NullProgressMonitor());
            } catch (CoreException e) {
                LOG.error(e);
            }

            zip =
                    new ZipOutputStream(new FileOutputStream(workspaceMAAFile
                            .getLocation().toOSString()));
        }

        /**
         * Create the MAA in an external file location.
         * 
         * @param generationRoot
         * @param projectName
         * @param locationType
         * @param outContainerPath
         * @throws FileNotFoundException
         * 
         */
        public MaaHandler(String maaFilePath) throws FileNotFoundException {

            systemMAAFile = new File(maaFilePath);

            File parent = systemMAAFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (systemMAAFile.exists()) {
                OverwriteStatus overwriteDestFile = OverwriteStatus.FILE;
                overwriteDestFile =
                        overwriteFileMessageDialog(new Path(maaFilePath));
                if (overwriteDestFile != OverwriteStatus.NO) {
                    try {
                        systemMAAFile.delete();
                    } catch (Exception e) {
                        LOG.error(e);
                    }
                }
            }

            zip =
                    new ZipOutputStream(new FileOutputStream(
                            systemMAAFile.getAbsolutePath()));

        }

        /**
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFileToContainer(IFile file, IPath path)
                throws IOException, CoreException {
            addFileToZip(zip, file, path.toPortableString());
        }

        /**
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addFolderToContainer(IFolder folder, IPath path)
                throws IOException, CoreException {
            addFolderToZip(zip, folder, path.toOSString());

        }

        /**
         * 
         * @throws CoreException
         * @throws IOException
         */
        public void addAllMembersToContainer(IContainer parent, final IPath path)
                throws IOException, CoreException {
            IResource[] members = parent.members();
            for (final IResource member : members) {
                if (!member.isDerived()) {
                    if (member instanceof IFile) {
                        IPath memberPath = path.append(member.getName());
                        addFileToContainer((IFile) member, memberPath);
                    } else if (member instanceof IFolder) {
                        addFolderToContainer((IFolder) member, path);
                    }
                }
            }

        }

        /**
         * 
         * @throws IOException
         * @throws CoreException
         */
        public void closeContainer() throws IOException, CoreException {
            zip.close();
            if (null != workspaceMAAFile) {
                workspaceMAAFile.refreshLocal(IResource.DEPTH_ZERO, null);
            }
        }

        /**
         * Adds file to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param file
         *            the file to add. File must exist.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         * @throws CoreException
         *             if there is problem with eclipse resources.
         */
        private void addFileToZip(ZipOutputStream zip, IFile file, String path)
                throws IOException, CoreException {
            /*
             * must not add contents of .maa file if it already exists in the
             * path
             */
            if (!path.contains(MODELLED_ARCHIVE_NAME)) {
                addEntryToZip(zip, file.getContents(), path);
            }
        }

        /**
         * Adds file to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param folder
         *            the file to add. File must exist.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         * @throws CoreException
         *             if there is problem with eclipse resources.
         */
        private void addFolderToZip(ZipOutputStream zip, IFolder folder,
                String pathPrefix) throws IOException, CoreException {
            String folderPath =
                    new Path(pathPrefix).append(folder.getName())
                            .toPortableString();
            IResource[] members = folder.members();
            if (members.length == 0) {
                ZipEntry zipEntry = new ZipEntry(folderPath + '/');
                try {
                    zip.putNextEntry(zipEntry);
                } catch (ZipException e) {
                    // ignore if zip entry already exist
                }
            }
            for (IResource member : members) {
                if (member instanceof IFile) {
                    if (!member.isDerived()) {
                        String zipPath =
                                new Path(folderPath).append(member.getName())
                                        .toPortableString();
                        addFileToZip(zip, (IFile) member, zipPath);
                    }
                } else if (member instanceof IFolder) {
                    addFolderToZip(zip, (IFolder) member, folderPath);
                }
            }
        }

        /**
         * Adds content of input stream to zipped output stream.
         * 
         * @param zip
         *            the zipped output stream.
         * @param in
         *            the input stream. Its content will be added as an entry's
         *            contents.
         * @param path
         *            path of the zip entry.
         * @throws IOException
         *             if there is problem with reading file or writing to
         *             stream.
         */
        private void addEntryToZip(ZipOutputStream zip, InputStream in,
                String path) throws IOException {
            ZipEntry zipEntry = new ZipEntry(path);
            try {
                zip.putNextEntry(zipEntry);
                byte buffer[] = new byte[BUFFER_SIZE];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zip.write(buffer, 0, len);
                }
                in.close();
            } catch (ZipException e) {
                // ignore if zip entry already exist
            }
        }
    }

}
