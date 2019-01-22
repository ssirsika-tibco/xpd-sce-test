package com.tibco.xpd.iprocess.amxbpm.resources.wizards.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.PackageFolderPicker;
import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;
import com.tibco.xpd.iprocess.amxbpm.converter.external.IProcessToStudioAMXBPMConverterFramework;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IBpmConversionValidator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpsToBpmConversionValidator;
import com.tibco.xpd.iprocess.amxbpm.resources.IProcessAMXBPMResourcePlugin;
import com.tibco.xpd.iprocess.amxbpm.resources.preferences.IProcessImportDebugPreferencePage;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports.AbstractValidationPage;
import com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports.ImportIProcessToAMXBPMWizard;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.dialogs.TextDetailsDialog;
import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider;

public class ConvertIProcessToAMXBPMWizard extends Wizard implements
        IImportWizardPageProvider {

    private AbstractValidationPage validatePage;

    ConvertWizardResourceSelectionPage resourceSelectionPage;

    private String windowMessage =
            Messages.ConvertIProcessToAMXBPMWizard_WizardDefaultMessage;

    private static final String[] fileExtFilter =
            new String[] { Xpdl2ResourcesConsts.XPDL_EXTENSION };

    private IContainer destinationContainer;

    private Collection<IResource> initialSelectedXpdls;

    private Set<IResource> initialPlusImplicitlySelectedXpdls;

    private IProject sourceProject;

    /**
     * @param sourceProject
     * @param collection
     *            of Xpdl resources selected for conversion.
     * 
     */
    public ConvertIProcessToAMXBPMWizard(Collection<IResource> selXpdls,
            IProject sourceProject) {
        super();
        this.initialSelectedXpdls = selXpdls;
        this.sourceProject = sourceProject;

        setWindowTitle(Messages.ConvertIProcessToAMXBPMWizard_WizardTitle);

        setDefaultPageImageDescriptor(IProcessAMXBPMResourcePlugin
                .getImageDescriptor("icons/ImportWizard.png")); //$NON-NLS-1$

        initialPlusImplicitlySelectedXpdls = new HashSet<IResource>();

        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {

        resourceSelectionPage =
                new ConvertWizardResourceSelectionPage(getWindowTitle(), this,
                        initialSelectedXpdls);

        addPage(resourceSelectionPage);

        resourceSelectionPage.setTitle(getWindowTitle());
        resourceSelectionPage.setDescription(getWindowMessage());

        validatePage = new AbstractValidationPage() {
            @Override
            public String getTitle() {
                return Messages.ConvertIProcessToAMXBPMWizard_ConvertForBpmDestinationWizard_title;
            }

            @Override
            protected IBpmConversionValidator getValidator() {

                return new IpsToBpmConversionValidator(getAllXpdlsToConvert(),
                        destinationContainer);
            }

            @Override
            protected String getValidationSuccessMessage() {

                return Messages.ConvertIProcessToAMXBPMWizard_ValidationSuccessfulMessage;
            }

            @Override
            protected String getValidationWarningMessage() {

                return Messages.ConvertIProcessToAMXBPMWizard_WarningsDuringValidation;
            }
        };
        addPage(validatePage);
    }

    /**
     * 
     * @return all the Xpdl's (explicilty selected by the user and implicitly
     *         dependent resources)
     */
    private Set<IResource> getAllXpdlsToConvert() {
        initialPlusImplicitlySelectedXpdls.addAll(resourceSelectionPage
                .getAllXpdlsToConvert());

        return initialPlusImplicitlySelectedXpdls;
    }

    /**
     * Set the message that will appear under the main title at the top of the
     * wizard pages
     * 
     * @param szMessage
     */
    protected void setWindowMessage(String szMessage) {
        windowMessage = szMessage;
    }

    /**
     * Get the window message.
     * 
     * @see #setWindowMessage(String)
     * 
     * @return The wizard message if set, <b>null</b> otherwise.
     */
    protected String getWindowMessage() {
        return windowMessage;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider#getDestinationContainerSelectionDialog()
     * 
     * @return
     */
    @Override
    public SelectionDialog getDestinationContainerSelectionDialog() {
        // Display the Project picker dialog
        PackageFolderPicker packagePicker = new PackageFolderPicker(getShell());
        packagePicker.setAllowMultiple(false);
        packagePicker.setInput(ResourcesPlugin.getWorkspace().getRoot());

        return packagePicker;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider#getFileExtensionFilter()
     * 
     * @return
     */
    @Override
    public String[] getFileExtensionFilter() {

        return fileExtFilter;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider#validateDestinationContainer(org.eclipse.core.resources.IContainer)
     * 
     * @param container
     * @return
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
                } else {
                    new Status(
                            IStatus.ERROR,
                            ImportExportPlugin.PLUGIN_ID,
                            IStatus.OK,
                            Messages.ConvertIProcessToAMXBPMWizard_DestinationNotProcessPackageError_msg,
                            null);
                }
            }
            if (status == Status.OK_STATUS) {

                /* Source Project can not be used as the Target Project */

                if (sourceProject.equals(folder.getProject())) {

                    status =
                            new Status(
                                    IStatus.ERROR,
                                    ImportExportPlugin.PLUGIN_ID,
                                    IStatus.OK,
                                    Messages.ConvertIProcessToAMXBPMWizard_SourceAndTargetFolderSameError_msg,
                                    null);
                }
            }
        }

        return status != null ? status : new Status(IStatus.ERROR,
                ImportExportPlugin.PLUGIN_ID, IStatus.OK,
                Messages.ConvertIProcessToAMXBPMWizard_TargetSelection_msg,
                null);

    }

    /**
     * @return
     */
    protected IContainer getDestinationContainer() {
        return destinationContainer;
    }

    /**
     * Performs the Conversion of IPS to AMX-BPM xpdl. On completion shows the
     * appropriate dialog(Error/Success) to the user.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        boolean operationFinished = true;

        WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {
                try {

                    monitor.beginTask("", 1); //$NON-NLS-1$
                    monitor.setTaskName(Messages.ConvertIProcessToAMXBPMWizard_ConvertingIpsXpdlsMonitor_desc);

                    /*
                     * Make sure that the destination folder is in sync with the
                     * file system, if it doesn't then sync it
                     */
                    if (!destinationContainer
                            .isSynchronized(IResource.DEPTH_ONE)) {
                        destinationContainer.refreshLocal(IResource.DEPTH_ONE,
                                monitor);
                    }

                    IProcessToStudioAMXBPMConverterFramework
                            .logTimeStampMsg(Messages.AbstractValidationPage_ConversionTimestampInfo,
                                    Messages.LogInfoStatus_Started);
                    /* perform the conversion */
                    IStatus convertionStatus =
                            performConversion(new SubProgressMonitor(monitor, 1));

                    IProcessToStudioAMXBPMConverterFramework
                            .logTimeStampMsg(Messages.AbstractValidationPage_ConversionTimestampInfo,
                                    Messages.LogInfoStatus_Completed);

                    monitor.worked(1);

                    /* Reset progress meter before creating finished dialog. */
                    monitor.setTaskName(""); //$NON-NLS-1$
                    monitor.subTask(""); //$NON-NLS-1$

                    if (convertionStatus != null) {
                        /* Generate the report of conversion */
                        reportImportStatus(convertionStatus);
                    }
                } catch (OperationCanceledException e) {

                    Display.getDefault().syncExec(new Runnable() {

                        @Override
                        public void run() {

                            MessageDialog.open(MessageDialog.INFORMATION,
                                    getShell(),
                                    Messages.ConvertIProcessToAMXBPMWizard_UserCancelledOperationDialog_title,
                                    Messages.ConvertIProcessToAMXBPMWizard_UserCancelledOperationDialog_desc,
                                    SWT.NONE);

                        }
                    });

                } catch (final Exception e) {
                    IProcessAMXBPMResourcePlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    Messages.ConvertIProcessToAMXBPMWizard_UnexpectedExceptionError_msg);
                    Display.getDefault().syncExec(new Runnable() {

                        @Override
                        public void run() {

                            MessageDialog.openError(getShell(),
                                    Messages.ConvertIProcessToAMXBPMWizard_UnexpectedExceptionError_msg,
                                    String.format(Messages.ConvertIProcessToAMXBPMWizard_ExceptionDetails_msg,
                                            e.toString()));
                        }
                    });

                } finally {

                    monitor.done();

                }
            }
        };
        try {

            getContainer().run(true, true, operation);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof CoreException) {
                CoreException exception = (CoreException) e.getCause();

                ErrorDialog
                        .openError(getShell(),
                                Messages.ConvertIProcessToAMXBPMWizard_IpsToBpmConversionFailedDialog_title,
                                Messages.ConvertIProcessToAMXBPMWizard_IpsToBpmConversionFailedDialog_desc,
                                exception.getStatus());
            } else {
                XpdResourcesUIActivator.getDefault().getLogger()
                        .error(e.getCause());
            }
        } catch (InterruptedException e) {
            // Operation cancelled so don't close the wizard
            operationFinished = false;
        }
        return operationFinished;
    }

    /**
     * Perform the IPS to BPM xpdl conversion.
     * 
     * @param monitor
     * @return the Status of the conversion
     * @throws OperationCanceledException
     * @throws CoreException
     */
    private IStatus performConversion(IProgressMonitor monitor)
            throws OperationCanceledException {

        IStatus convertionStatus = null;

        List<IFile> iFile = new ArrayList<IFile>();

        for (IResource c : initialPlusImplicitlySelectedXpdls) {
            iFile.add((IFile) c);
        }

        try {
            IProcessToStudioAMXBPMConverterFramework ipsToBpmConverter =
                    new IProcessToStudioAMXBPMConverterFramework(
                            destinationContainer.getProject(),
                            IProcessImportDebugPreferencePage.getDebugMode());

            /* trigger the IPS to AMX-BPM conversion */
            convertionStatus =
                    ipsToBpmConverter
                            .convertStudioIProcessToStudioAMXBPM(iFile,
                                    (IFolder) destinationContainer,
                                    monitor);

        } catch (CoreException e) {
            /*
             * Do nothing, all CoreExceptions are captured in the Status List
             */
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

                StringBuffer messageDetails = new StringBuffer();

                int severity =
                        ImportIProcessToAMXBPMWizard
                                .getStatusMessageAndSeverity(convertionStatus,
                                        messageDetails,
                                        false,
                                        debugMode);

                String dialogTitle =
                        Messages.ConvertIProcessToAMXBPMWizard_ConversionjSummaryDialog_title;

                switch (severity) {
                case IStatus.ERROR:

                    TextDetailsDialog
                            .openError(getShell(),
                                    dialogTitle,
                                    Messages.ConvertIProcessToAMXBPMWizard_ConversionFailureSummary_desc,
                                    messageDetails.toString());
                    break;
                default:
                    TextDetailsDialog
                            .openInfo(getShell(),
                                    dialogTitle,
                                    Messages.ConvertIProcessToAMXBPMWizard_ConversionSuccessfullSummaryDialog_desc,
                                    messageDetails.toString());

                }

            }

        });
    }
}
