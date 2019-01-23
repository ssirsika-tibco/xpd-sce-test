/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.imports.wizards;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.PackageFolderPicker;
import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.importwizard.ImportSubTask;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;

/**
 * This abstract class extends <code>AbstractImportWizard</code> to provide a
 * wizard that imports resources to create XPDL packages. This class provides
 * the following functionality:
 * <ul>
 * <li>Provides a destination process packages folder picker.</li>
 * <li>Provides validation of the destination folder. Only allowes to select
 * process packages folder as the import destination.</li>
 * <li>Migrates imported xpdl packages to the latest version.</li>
 * </ul>
 * 
 * @see AbstractImportWizard
 * 
 * @author njpatel
 * 
 */
public abstract class ImportXPDLWizard extends AbstractImportWizard {

    private static final String DEST_FILE_EXT = "xpdl"; //$NON-NLS-1$

    private IContainer destinationContainer;

    public ImportXPDLWizard() {
        super();

        // Register subtask to migrate the imported xpdl
        registerSubTask(new ImportSubTask() {

            private XpdlMigrate xpdlMigrate = new XpdlMigrate();

            /*
             * (non-Javadoc)
             * 
             * @see com.tibco.xpd.ui.importexport.importWizard.ImportSubTask#
             * dispose ()
             */
            @Override
            public void dispose() {
                // Nothing to dispose
            }

            /*
             * (non-Javadoc)
             * 
             * @see com.tibco.xpd.ui.importexport.importWizard.ImportSubTask#
             * perform (org.eclipse.core.runtime.SubProgressMonitor,
             * java.io.File, org.eclipse.core.resources.IFile)
             */
            @Override
            public void perform(SubProgressMonitor monitor, File inputFile,
                    IFile outputFile) throws CoreException {
                // Migrate the imported resource
                try {
                    String monitorMsg =
                            String.format(Messages.ImportXPDLWizard_MigratingToLatestVersion_message,
                                    outputFile.getName());
                    monitor.beginTask(monitorMsg, 1);
                    monitor.subTask(monitorMsg);

                    doMigrate(xpdlMigrate, outputFile);

                    monitor.worked(1);
                    /*
                     * This very short sleep allows monitor to update and gives
                     * user-comfort that something actually happened.
                     */
                    Thread.sleep(20);

                } catch (Exception e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            ImportExportPlugin.PLUGIN_ID, IStatus.OK,
                            Messages.ImportXPDLWizard_migrationFailed_message,
                            e));
                } finally {
                    monitor.done();
                }
            }
        });
    }

    /**
     * Perform the migration to latest version.
     * 
     * @param outputFile
     * @throws Exception
     */
    protected void doMigrate(XpdlMigrate xpdlMigrate, IFile outputFile)
            throws Exception {
        xpdlMigrate.transform(outputFile);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.importexport.importWizard.pages.ImportWizardPageIO.
     * IImportWizardPageProvider#getDestinationContainerSelectionDialog()
     */
    @Override
    public SelectionDialog getDestinationContainerSelectionDialog() {
        // Display the Project picker dialog
        PackageFolderPicker packagePicker = new PackageFolderPicker(getShell());
        packagePicker.setAllowMultiple(false);
        packagePicker.setInput(ResourcesPlugin.getWorkspace().getRoot());

        return packagePicker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.importWizard.IImportWizardPageProvider#
     * validateDestinationContainer(org.eclipse.core.resources.IContainer)
     */
    @Override
    public IStatus validateDestinationContainer(IContainer container) {
        IStatus status = null;
        if (container != null && container instanceof IFolder) {
            IFolder folder = (IFolder) container;
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(folder.getProject());

            if (config != null && config.getSpecialFolders() != null) {
                SpecialFolder sFolder =
                        config.getSpecialFolders().getFolderContainer(folder);

                if (sFolder != null
                        && sFolder
                                .getKind()
                                .equals(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)) {
                    destinationContainer = container;
                    status = Status.OK_STATUS;
                }
            }
        }

        return status != null ? status : new Status(IStatus.ERROR,
                ImportExportPlugin.PLUGIN_ID, IStatus.OK,
                Messages.ImportXPDLWizard_destinationError_message, null);

    }

    @Override
    protected String getImportFileExtension() {
        return DEST_FILE_EXT;
    }

    @Override
    protected IContainer getDestinationContainer() {
        return destinationContainer;
    }
}
