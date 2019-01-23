/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.ImportDisplayStatus;
import com.tibco.xpd.importexport.imports.wizards.ImportXPDLWizard;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;
import com.tibco.xpd.iprocess.amxbpm.converter.external.IProcessToStudioAMXBPMConverterFramework;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IBpmConversionValidator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IProcessImportValidator;
import com.tibco.xpd.iprocess.amxbpm.resources.IProcessAMXBPMResourcePlugin;
import com.tibco.xpd.iprocess.amxbpm.resources.preferences.IProcessImportDebugPreferencePage;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.TextDetailsDialog;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;

/**
 * Copied from IPM com.tibco.xpd.ipm plugin and customized for IPM to AMX BPM
 * xpdl import. Wizard for Import iprocess XPDL and Convert to AMX BPM.
 * 
 * @author aprasad
 */
public class ImportIProcessToAMXBPMWizard extends ImportXPDLWizard {

    /**
     * XPDL file filter
     */
    private static final String[] fileExtFilter =
            new String[] { Xpdl2ResourcesConsts.XPDL_EXTENSION };

    private URL[] xslts;

    private AbstractValidationPage validatePage;

    public ImportIProcessToAMXBPMWizard() {

        super();

        setWindowTitle(Messages.ImportIProcessToAMXBPMWizard_Title);
        setWindowMessage(Messages.ImportIProcessToAMXBPMWizard_Description);

        setDefaultPageImageDescriptor(IProcessAMXBPMResourcePlugin
                .getImageDescriptor("icons/ImportWizard.png")); //$NON-NLS-1$

    }

    /**
     * @see com.tibco.xpd.importexport.imports.wizards.ImportXPDLWizard#doMigrate(com.tibco.xpd.xpdl2.resources.XpdlMigrate,
     *      org.eclipse.core.resources.IFile)
     * 
     * @param xpdlMigrate
     * @param outputFile
     * @throws Exception
     */
    @Override
    protected void doMigrate(XpdlMigrate xpdlMigrate, IFile outputFile)
            throws Exception {
        /*
         * Because we import as initially xpdl v1 we name the output file .xpdl1
         * to prevent the v1 xpdl being picked up by working copy (which will
         * fail to load anyway).
         * 
         * This also prevents the post-migrate command injection from working.
         * So for tidiness we will explicitly say not to perform command
         * injection and we will then do that injection explicitly when the
         * reset back to .xpdl is done.
         */

        // NOTE: Migration will be part of ConversionFramework, and will not be
        // done here.

    }

    @Override
    public void addPages() {
        // Add the source/destination selection page
        // Our Import Page Should not provide the Overwrite Resources option as
        // we do not intend to allow overwriting existing Processes.
        pageIO =
                new ImportWizardPageIOWithoutOverwriteResources(selection, this);

        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(getWindowMessage());

        // If wizard banner image specified then set it
        if (imgWizardBanner != null)
            pageIO.setImageDescriptor(imgWizardBanner);
        addPage(pageIO);

        validatePage = new AbstractValidationPage() {
            /**
             * @see com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports.AbstractValidationPage#getValidator()
             * 
             * @return
             */
            @Override
            protected IBpmConversionValidator getValidator() {

                return getImportValidator();
            }

            @Override
            protected String getValidationSuccessMessage() {

                return Messages.ImportIProcessToAMXBPMWizard_ValidationSuccessMessage;
            }

            @Override
            protected String getValidationWarningMessage() {

                return Messages.ImportIProcessToAMXBPMWizard_WarningsDuringValidation;
            }
        };
        addPage(validatePage);
    }

    /**
     * 
     * @return the validator for this Conversion.
     */
    private IProcessImportValidator getImportValidator() {

        List<File> filesToValidate = new ArrayList<File>();

        List<FilteredFSElement> elements = getImportResources();

        for (FilteredFSElement element : elements) {

            Object fso = element.getFileSystemObject();
            if (fso instanceof File) {

                File file = (File) fso;
                if (!filesToValidate.contains(file)) {

                    filesToValidate.add(file);
                }
            }
        }

        return new IProcessImportValidator(getTargetProject(), filesToValidate);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.importWizard.pages.ImportWizardPageIO.
     * IImportWizardPageProvider#getFileExtensionFilter()
     */
    @Override
    public String[] getFileExtensionFilter() {
        return fileExtFilter;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXslts()
     */
    @Override
    public URL[] getXslts() {
        if (xslts == null) {

            // IPM to IPS transformation will happen in ConvertorFramework and
            // should not be done here so do not return any xslt.
            // xslts = IProcessAMXBPMConverterPlugin.getDefault().getXslts();
            xslts = new URL[0];
        }
        return xslts;
    }

    @Override
    public Map<String, String> getXsltParameters() {

        // IPM to IPS transformation will happen in ConvertorFramework and
        // should not be done here so do not return any xslt.
        return null;

    }

    /**
     * @return Returns the list of resources selected for import.
     */
    public List<FilteredFSElement> getImportResources() {
        return getSelectedResources();
    }

    /**
     * @return returns the target {@link IProject}.
     */
    public IProject getTargetProject() {

        IProject project = null;
        IContainer container = getDestinationContainer();

        if (container != null) {
            project = container.getProject();
        }
        return project;
    }

    @Override
    protected String getOutputFileName(File srcFile) {
        return "." + super.getOutputFileName(srcFile) + "1"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {

        // AS this wizard is not supposed to do transformations, we do not
        // need to check for available xslts.
        IWizardPage[] pages = getPages();
        // Default implementation is to check if all pages are complete.
        for (int i = 0; i < pages.length; i++) {
            if (!pages[i].isPageComplete()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, OperationCanceledException {

        /*
         * XPD-6361: Saket: Adding pre-import timestamp.
         */
        IProcessToStudioAMXBPMConverterFramework
                .logTimeStampMsg(Messages.AbstractValidationPage_ImportTimestampInfo,
                        Messages.LogInfoStatus_Started);

        IStatus convertionStatus = null;
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$
            monitor.setTaskName(Messages.ImportIProcessToAMXBPMWizard_MsgImporting);

            // convert to BPM XPDLs
            convertionStatus =
                    performConversion(new SubProgressMonitor(monitor, 1));

            /* Reset progress meter before creating finished dialog. */
            monitor.setTaskName(""); //$NON-NLS-1$
            monitor.subTask(""); //$NON-NLS-1$

            // report available import/convert status to user
            if (convertionStatus != null) {

                /*
                 * XPD-6361: Saket: Adding post-import timestamp.
                 */
                IProcessToStudioAMXBPMConverterFramework
                        .logTimeStampMsg(Messages.AbstractValidationPage_ImportTimestampInfo,
                                Messages.LogInfoStatus_Completed);

                reportImportStatus(convertionStatus);
            }

        } catch (OperationCanceledException e) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {

                    MessageDialog.open(MessageDialog.INFORMATION,
                            getShell(),
                            Messages.ImportIProcessToAMXBPMWizard_UserCancelledTitle,
                            Messages.ImportIProcessToAMXBPMWizard_UserCancelledMsg,
                            SWT.NONE);

                }
            });

        } catch (final Exception e) {
            IProcessAMXBPMResourcePlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            Messages.ImportIProcessToAMXBPMWizard_ErrorsDuringImportDialog_title);
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {

                    MessageDialog.openError(getShell(),
                            Messages.ImportIProcessToAMXBPMWizard_ErrorsDuringImportDialog_title,
                            String.format(Messages.ImportIProcessToAMXBPMWizard_ErrorsDuringImportDialog_msg,
                                    e.toString()));
                }
            });

        } finally {

            monitor.done();
        }

    }

    /**
     * Converts the iProcess Modeler XPDLs to BPM XPDLs using
     * {@link IProcessToStudioAMXBPMConverterFramework}.
     * 
     * @param monitor
     * 
     */
    private IStatus performConversion(IProgressMonitor monitor)
            throws OperationCanceledException {

        IStatus convertionStatus = null;
        IContainer targetFolder = pageIO.getDestinationContainer();

        if (targetFolder != null) {
            try {
                // Use ConversionFramework to perform conversion
                IProcessToStudioAMXBPMConverterFramework framework =
                        new IProcessToStudioAMXBPMConverterFramework(
                                targetFolder.getProject(),
                                IProcessImportDebugPreferencePage
                                        .getDebugMode());

                // Call ConversionFramework to convert iProcess XPDLs to BPM
                // XPDLs
                convertionStatus =
                        framework
                                .convertIPMToStudioAMXBPM(getSelectedFilesToImport(),
                                        (IFolder) targetFolder,
                                        monitor);

            } catch (CoreException e) {
                // Do nothing, all CoreExceptions are captured in the Status
                // List
            }
        }

        return (convertionStatus == null) ? Status.OK_STATUS : convertionStatus;
    }

    /**
     * Reports the Import Status Report to user as appropriate
     * Error/Warning/Info message.
     * 
     * @param convertionStatus
     *            , {@link Collection} of IStatus representing
     *            Error(s)/Warning(s)/Info(s), related to the iProcess
     *            Import/Convert to BPM.
     */
    private void reportImportStatus(final IStatus convertionStatus) {

        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {

                DEBUG_MODE debugMode =
                        IProcessImportDebugPreferencePage.getDebugMode();

                // XPD-6166: Changes to Display selective messages and in
                // appropriate details dialog.
                StringBuffer messageDetails = new StringBuffer();

                int severity =
                        getStatusMessageAndSeverity(convertionStatus,
                                messageDetails,
                                false,
                                debugMode);

                String dialogTitle =
                        Messages.ImportIProcessToAMXBPMWizard_ImportConvertSummaryDialogTitle;

                switch (severity) {
                case IStatus.ERROR:

                    TextDetailsDialog
                            .openError(getShell(),
                                    dialogTitle,
                                    Messages.ImportIProcessToAMXBPMWizard_ImportConvertFailedErrorMsg,
                                    messageDetails.toString());
                    break;
                // XPD-6154: we only show Error and informtion to user
                // case IStatus.WARNING:
                //
                // TextDetailsDialog
                // .openWarning(getShell(),
                // dialogTitle,
                // Messages.ImportIProcessToAMXBPMWizard_ImportConvertWarningMsg,
                // messageDetails.toString());
                // break;
                default:
                    TextDetailsDialog
                            .openInfo(getShell(),
                                    dialogTitle,
                                    Messages.ImportIProcessToAMXBPMWizard_ImportConvertCompletionInfoMsg,
                                    messageDetails.toString());

                }

            }

        });
    }

    /**
     * Get the selected resources for import.
     * 
     * @return list of <code>File</code> objects
     * 
     */
    protected Collection<File> getSelectedFilesToImport() {

        Collection<File> filesToImport = new LinkedList<File>();

        for (FilteredFSElement fSElement : getSelectedResources()) {

            if (fSElement.getFileSystemObject() instanceof File) {
                filesToImport.add((File) fSElement.getFileSystemObject());
            }

        }

        return filesToImport;
    }

    /**
     * Gather the ERROR / ImportDisplayStatus messages into the messageDetails
     * buffer.
     * 
     * @param convertionStatus
     * @param messageDetails
     * @param parentStatusLogged
     * @param debugMode
     * 
     * @return The worst severity of the nested statuses
     */
    public static int getStatusMessageAndSeverity(IStatus convertionStatus,
            StringBuffer messageDetails, boolean parentStatusLogged,
            DEBUG_MODE debugMode) {

        int parentSeverity = convertionStatus.getSeverity();

        // Display following info Messages && Errors
        String statMsg = convertionStatus.getMessage();

        if (convertionStatus instanceof ImportDisplayStatus
                || parentSeverity == IStatus.ERROR) {

            messageDetails.append(statMsg);
            messageDetails
                    .append(IProcessAMXBPMConverterPlugin.SYSTEM_LINE_SEPARATOR);
        }

        /*
         * XPD-6370 reduce logging where possible...
         */
        /*
         * do not log the nested children messages separately from the top level
         * message (which for MultiStatus will already include the messages of
         * children.
         */
        if (!parentStatusLogged) {
            /*
             * Output all errors / warnings. And output all in debug mode
             */
            if (convertionStatus.getSeverity() == IStatus.WARNING
                    || convertionStatus.getSeverity() == IStatus.ERROR
                    || !DEBUG_MODE.NONE.equals(debugMode)) {
                /*
                 * Don't log messages unless they are NOT empty or jsut plain
                 * unset (like default "OK" message).
                 */
                if (statMsg != null && !statMsg.isEmpty()) {
                    if (!statMsg.equals(Status.OK_STATUS.getMessage())) {

                        IProcessAMXBPMResourcePlugin.getDefault().getLogger()
                                .log(convertionStatus);

                        parentStatusLogged = true;
                    }
                }
            }
        }

        for (IStatus iStatus : convertionStatus.getChildren()) {
            int childSeverity =
                    getStatusMessageAndSeverity(iStatus,
                            messageDetails,
                            parentStatusLogged,
                            debugMode);
            if (childSeverity > parentSeverity)
                parentSeverity = childSeverity;

        }

        return parentSeverity;
    }
}
