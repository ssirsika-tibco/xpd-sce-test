/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.aris.arisxml.imports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.osgi.framework.Bundle;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.aris.internal.Messages;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.importexport.ImportExportGeneratorConsts;
import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.imports.custom.CustomImportWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.importwizard.ImportSubTask;
import com.tibco.xpd.ui.importexport.importwizard.PostImportTask;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ARIS import wizard. Allows import of ARIS 6.2 / 7.0 ARIS ePC models (from AML
 * files).
 * <p>
 * SINCE 3.5 this class extends {@link CustomImportWizard} so that it is made
 * available in the Studio RCP application.
 * </p>
 * 
 * @author njpatel
 * 
 * 
 */
public class ImportWizard extends CustomImportWizard {

    // Import xslt
    private static final String XSLT = "xslt/ARISXML_2_XPDL2.xslt"; //$NON-NLS-1$

    public ImportWizard() {
        // Register subtask to migrate the imported xpdl
        registerSubTask(new ImportSubTask() {

            private XpdlMigrate xpdlMigrate = new XpdlMigrate();

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.ui.importexport.importWizard.ImportSubTask#dispose
             * ()
             */
            @Override
            public void dispose() {
                // Nothing to dispose
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.ui.importexport.importWizard.ImportSubTask#perform
             * (org.eclipse.core.runtime.SubProgressMonitor, java.io.File,
             * org.eclipse.core.resources.IFile)
             */
            @Override
            public void perform(SubProgressMonitor monitor, File inputFile,
                    IFile outputFile) throws CoreException {
                // Migrate the imported resource
                try {
                    xpdlMigrate.transform(outputFile);
                } catch (Exception e) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    ImportExportPlugin.PLUGIN_ID,
                                    IStatus.OK,
                                    Messages.ImportWizard_migrationFailed_error_message,
                                    e));
                }
            }
        });

        // post import task to set the destination environment
        registerPostImportTask(new PostImportTask() {

            @Override
            public void perform(IProgressMonitor monitor, IFile outputFile)
                    throws CoreException {

                try {
                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(outputFile);

                    if (workingCopy != null
                            && workingCopy.getRootElement() instanceof Package) {
                        Package pkg = (Package) workingCopy.getRootElement();

                        String msg =
                                String.format(Messages.ImportWizard_Perform_Post_Import_task_message,
                                        outputFile.getName());
                        monitor.beginTask(msg, pkg.getProcesses().size());

                        /*
                         * Don't want to perform post-import on command stack so
                         * create a separate transaction for it.
                         */
                        Map<String, Object> attrs =
                                new HashMap<String, Object>();
                        attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
                        InternalTransaction transaction = null;

                        try {
                            transaction =
                                    ((InternalTransactionalEditingDomain) workingCopy
                                            .getEditingDomain())
                                            .startTransaction(false, attrs);

                            for (Process process : pkg.getProcesses()) {
                                String subMsg =
                                        String.format(" (%1$s)", //$NON-NLS-1$
                                                Xpdl2ModelUtil
                                                        .getDisplayNameOrName(process));
                                monitor.subTask(msg + subMsg);

                                setDestinationEnvironment(process, outputFile);

                                monitor.worked(1);

                                /* Little sleep to allow update of prog monitor. */
                                Thread.sleep(20);

                            }

                        } catch (InterruptedException e1) {
                        } finally {
                            if (transaction != null) {
                                try {
                                    transaction.commit();
                                } catch (RollbackException e) {
                                }
                            }
                        }
                    }

                } finally {
                    monitor.done();
                }

            }

            private void setDestinationEnvironment(Process process,
                    IFile outputFile) {

                if (!XpdResourcesPlugin.isRCP()) {
                    IProject project = outputFile.getProject();

                    if (project != null) {
                        DestinationUtil
                                .addPassedDestinationToProcess(com.tibco.xpd.destinations.GlobalDestinationUtil
                                        .getEnabledGlobalDestinations(project,
                                                true),
                                        process);
                    }
                }
            }

            @Override
            public void dispose() {
                // dispose nothing
            }
        });
    }

    @Override
    protected Properties getPluginProperties(Bundle bundle,
            String propertiesFile) throws IOException {
        /*
         * Create our own properties as they are not stored in the
         * plugin.properties.
         */
        Properties prop = new Properties();
        prop.put(ImportExportGeneratorConsts.PROP_WIZARD_NAME,
                Messages.ImportWizard_title);
        prop.put(ImportExportGeneratorConsts.PROP_WIZARD_DESCRIPTION,
                Messages.ImportWizard_shortDesc);
        prop.put(ImportExportGeneratorConsts.PROP_OUTPUT_FILE_EXT,
                Xpdl2ResourcesConsts.XPDL_EXTENSION);
        prop.put(ImportExportGeneratorConsts.PROP_XSL, XSLT);
        prop.put(ImportExportGeneratorConsts.PROP_SPECIALFOLDER_FILTER,
                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        prop.put(ImportExportGeneratorConsts.PROP_FILE_EXT_FILTER, "xml"); //$NON-NLS-1$

        return prop;
    }
}
