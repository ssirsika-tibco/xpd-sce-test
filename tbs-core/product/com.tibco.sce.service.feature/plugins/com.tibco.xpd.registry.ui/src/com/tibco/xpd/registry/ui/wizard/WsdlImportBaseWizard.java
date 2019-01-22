package com.tibco.xpd.registry.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;
import com.tibco.xpd.registry.ui.wizard.WsdlImportWizard.IImportProjectProvider;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.dialogs.TextDetailsDialog;
import com.tibco.xpd.wsdl.CertificateAcceptanceTracker;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * A wizard which handles import of wsdl from some source. It contains a source
 * page (supplied by the subclass) and a target page which is of the same type
 * in all cases. Where there are multiple source files they are all imported to
 * the same location. Factored out some common methods from the wizards which
 * handle importing of wsdl
 */
public abstract class WsdlImportBaseWizard extends Wizard implements
        IImportWizardWithOperationPicker, IImportProjectProvider {

    private IProject project;

    /** This is the same type in all cases */
    final private TargetSelectionPage targetSelectionPage;

    private OperationPickerPage operationPickerPage = null;

    /**
     * Subclasses have to provide one of these for the specific source they
     * select from
     */
    final private WsdlSourceBaseWizardPage sourceWizardPage;

    /**
     * 
     * @param sourceWizardPage
     *            a wizard page which can produce a list of wsdl sources to
     *            import
     */
    WsdlImportBaseWizard(WsdlSourceBaseWizardPage sourceWizardPage,
            String windowTitle) {
        targetSelectionPage = new TargetSelectionPage();
        this.sourceWizardPage = sourceWizardPage;
        if (sourceWizardPage != null)
            sourceWizardPage.targetSelectionPage = targetSelectionPage;
        setNeedsProgressMonitor(true);
        setWindowTitle(windowTitle);
    }

    void updateTarget(WsdlImportMapping[] mappings) {
        targetSelectionPage.setWsdlMappings(mappings);
    }

    @Override
    public void addPages() {
        if (sourceWizardPage != null) {
            addPage(sourceWizardPage);
        }
        addPage(targetSelectionPage);
        if (operationPickerPage != null) {
            addPage(operationPickerPage);
        }
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        IWizardPage nextPage = super.getNextPage(page);
        // if operation picker page is not null then we know this page has been
        // called from an action and we
        // need to import and show the wsdl methods before user can finish.
        if (operationPickerPage != null && operationPickerPage.equals(nextPage)) {
            if (!targetSelectionPage.getDestinationLocationResource()
                    .getLocation().toOSString()
                    .equals(operationPickerPage.getNavigateLocation())) {
                if (!performFinish()) {
                    nextPage = null;
                }
            }
            operationPickerPage.setNavigateLocation(targetSelectionPage
                    .getDestinationLocationResource().getLocation()
                    .toOSString());
            // MR 35822 - begin
            // to show the project and service descriptor folder name in the
            // project location (if the user changes the default by clicking
            // browse button)
            operationPickerPage.setInput(targetSelectionPage
                    .getDestinationLocationResource().getProject());
            // to show the latest downloaded wsdl file selected
            operationPickerPage.setSelection(new Object[] { targetSelectionPage
                    .getResource() });
            // MR 35822 - end
        }
        return nextPage;
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#setOperationPickerPage(com.tibco.xpd.registry.ui.wizard.OperationPickerPage)
     * 
     * @param operationPickerPage
     */
    @Override
    public final void setOperationPickerPage(
            OperationPickerPage operationPickerPage) {
        this.operationPickerPage = operationPickerPage;
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#getOperationPickerPage()
     * 
     * @return
     */
    @Override
    public final OperationPickerPage getOperationPickerPage() {
        return operationPickerPage;
    }

    @Override
    public final void init(IWorkbench workbench, IStructuredSelection selection) {
        if ((selection != null) && (selection.size() == 1)) {
            Object target = selection.getFirstElement();
            if (target instanceof IAdaptable) {
                IAdaptable adaptable = (IAdaptable) target;
                // MR 41969
                IProject project = null;
                SpecialFolder specialFolderOfKind = null;
                if (adaptable instanceof IProject) {
                    project = (IProject) adaptable;
                    specialFolderOfKind =
                            SpecialFolderUtil.getSpecialFolderOfKind(project,
                                    WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);
                }
                String location = null;
                if (adaptable instanceof SpecialFolder) {
                    SpecialFolder specialFolder = (SpecialFolder) adaptable;
                    location = specialFolder.getLocation();
                    project = specialFolder.getProject();
                    specialFolderOfKind =
                            SpecialFolderUtil.getSpecialFolderOfKind(project,
                                    WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);
                }
                if (null != project && null != specialFolderOfKind) {
                    if (null == location) {
                        location = specialFolderOfKind.getLocation();
                    }
                    targetSelectionPage.setLocation(project.getName()
                            + TargetSelectionPage.pathSeparator + location);
                }
                // MR 41969
            }
        }
    }

    void handleException(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            MessageDialog
                    .openError(getShell(),
                            Messages.WsdlRegistryImportWizard_ImportProblem_title,
                            Messages.WsdlImportBaseWizard_connectionTimedOut_error_message);
        } else {
            String message =
                    Messages.WsdlRegistryImportWizard_ImportProblem_message;
            String title =
                    Messages.WsdlRegistryImportWizard_ImportProblem_title;
            String details =
                    String.format(Messages.WsdlRegistryImportWizard_ImportExceptionDetails_longdesc,
                            e.getClass().toString());
            if (e.getLocalizedMessage() != null
                    && e.getLocalizedMessage().trim().length() > 0) {
                details += '\n' + e.getLocalizedMessage();
            } else if (e.getMessage() != null
                    && e.getMessage().trim().length() > 0) {
                details += '\n' + e.getMessage();
            }
            TextDetailsDialog.openError(getShell(), title, message, details);
        }
        Activator.getDefault().getLogger().warn(e, e.getMessage());
    }

    @Override
    public final boolean performFinish() {
        if (operationPickerPage != null && operationPickerPage.isPageComplete()) {
            return true;
        } else {
            final IContainer target =
                    targetSelectionPage.getDestinationLocationResource();
            final WsdlImportMapping[] wsdlImportMappings =
                    targetSelectionPage.getWsdlMappings();

            final CertificateAcceptanceTracker certificateAcceptanceTracker =
                    new CertificateAcceptanceTracker() {
                        private X509Certificate trustedCertificate;

                        private String urlForAlias;

                        @Override
                        public boolean shouldAccept(
                                final X509Certificate[] chain,
                                final String urlString,
                                final String[] existingAliases) {
                            final boolean[] resultFlag = new boolean[1];
                            getContainer().getCurrentPage().getControl()
                                    .getDisplay().syncExec(new Runnable() {
                                        @Override
                                        public void run() {
                                            urlForAlias = urlString;
                                            MessageBox messageBox =
                                                    new MessageBox(
                                                            getShell(),
                                                            SWT.YES
                                                                    | SWT.NO
                                                                    | SWT.ICON_WARNING);
                                            messageBox
                                                    .setText(Messages.WsdlImportBaseWizard_unknown_certificate_warning_message);
                                            messageBox
                                                    .setMessage(urlString
                                                            + Messages.WsdlImportBaseWizard_would_you_like_to_trust_prompt_message);
                                            boolean result =
                                                    (messageBox.open() == SWT.YES);
                                            if (result) {
                                                // for now we just take the last
                                                // one on the
                                                // list - which is
                                                // the machine itself.
                                                trustedCertificate =
                                                        chain[chain.length - 1];
                                            }
                                            resultFlag[0] = result;
                                        }

                                    });
                            return resultFlag[0];
                        }

                        @Override
                        public X509Certificate getTrustedCertificate() {
                            return trustedCertificate;
                        }

                        @Override
                        public String getAlias() {
                            return urlForAlias;
                        }
                    };
            /*
             * XPD-6127: Use WorkspaceModifyOperation instead of
             * IRunnableWithProgress so that the transformation is started after
             * all the resources are fully in workspace
             */
            WorkspaceModifyOperation copyOperation =
                    new WorkspaceModifyOperation() {

                        @Override
                        protected void execute(IProgressMonitor monitor)
                                throws CoreException,
                                InvocationTargetException, InterruptedException {

                            try {
                                monitor.beginTask(Messages.WsdlFileImportWizard_ImportingProgress_message,
                                        IProgressMonitor.UNKNOWN);
                                for (WsdlImportMapping thisMapping : wsdlImportMappings) {
                                    if (thisMapping.getSourceUrl() != null) {
                                        thisMapping
                                                .copyTo(target,
                                                        certificateAcceptanceTracker,
                                                        targetSelectionPage
                                                                .isOverwriteExistingResources(),
                                                        monitor);
                                    }
                                }
                            } catch (Throwable e) {
                                throw new InvocationTargetException(e);
                            } finally {
                                monitor.done();
                            }
                        }
                    };

            try {
                getContainer().run(true, false, copyOperation);
            } catch (InterruptedException e) {
                return false;
            } catch (InvocationTargetException e) {
                Throwable realException = e.getTargetException();
                handleException(realException);
                return false;
            }
        }
        return true;
    }

    static WsdlImportMapping[] getWsdlImportMappings(
            RegistryServiceSelector registryServiceSelector) {
        String[] urls = registryServiceSelector.getWsdlUrls();
        String[] serviceNames = registryServiceSelector.getServiceNames();
        WsdlImportMapping[] wsdlImportMappings =
                new WsdlImportMapping[urls.length];
        for (int index = 0; index < urls.length; index++) {
            wsdlImportMappings[index] =
                    new WsdlImportMapping(urls[index],
                            applyWsdlExtension(serviceNames[index]));
        }
        return wsdlImportMappings;
    }

    /**
     * @return true if the specified file exists.
     */
    protected static boolean isValidURI(String candidate) {
        return validateURI(candidate) == null;
    }

    /**
     * Validate the given uri and return error message if invalid.
     * 
     * @return <code>null</code> if the uri is valid, otherwise it will return
     *         the error message.
     * @since 3.5
     */
    protected static String validateURI(String uriPath) {
        if (uriPath == null) {
            return Messages.WsdlImportBaseWizard_nullURI_error_message;
        }
        try {
            final URI uri = new URI(uriPath);
            final IPath path = new Path(uri.getPath());
            if (path.segmentCount() == 0) {
                return Messages.WsdlImportBaseWizard_incompleteURI_error_message;
            }
        } catch (URISyntaxException e) {
            return e.getLocalizedMessage();
        }
        return null;
    }

    /**
     * @return a filename ending in .wsdl derived from the last segment of the
     *         uri path, or an empty string if the uriString is invalid
     */
    public static String guessImportedFileNameFromURI(String uriString) {
        if (!isValidURI(uriString))
            return ""; //$NON-NLS-1$
        try {
            final URI uri = new URI(uriString);
            final IPath path = new Path(uri.getPath());
            return applyWsdlExtension(path.lastSegment());
        } catch (URISyntaxException exception) {
            // Shouldn't happen - the validity of the uri is checked
            com.tibco.xpd.registry.ui.Activator
                    .getDefault()
                    .getLogger()
                    .error(exception,
                            "Asked to create a file name from an invalid uri " //$NON-NLS-1$
                                    + uriString);
            return ""; //$NON-NLS-1$
        }
    }

    /**
     * 
     * @param fileName
     *            a string which may or may not end in wsdl
     * @return fileName guaranteed to end with .wsdl (lower case)
     */
    public static String applyWsdlExtension(String fileName) {
        while (fileName.endsWith(".")) { //$NON-NLS-1$
            fileName = fileName.substring(0, fileName.length() - 1);
        }
        if (hasWsdlExtension(fileName, false)) {
            return fileName;
        }
        if (hasWsdlExtension(fileName, true)) {
            return fileName.substring(0, fileName.length() - 5) + ".wsdl"; //$NON-NLS-1$
        }
        return fileName + ".wsdl"; //$NON-NLS-1$
    }

    /**
     * @param fileName
     *            The filename to check.
     * @return true if the extension is a wsdl type.
     */
    private static boolean hasWsdlExtension(String fileName, boolean ignoreCase) {
        boolean wsdl = false;
        if (fileName != null) {
            int dot = fileName.lastIndexOf('.');
            if (dot != -1 && dot < (fileName.length() - 1)) {
                String extension = fileName.substring(dot + 1);
                if (ignoreCase) {
                    if ("wsdl".equalsIgnoreCase(extension)) { //$NON-NLS-1$
                        wsdl = true;
                    }
                } else {
                    if ("wsdl".equals(extension)) { //$NON-NLS-1$
                        wsdl = true;
                    }
                }
            }
        }
        return wsdl;
    }

    /**
     * Every subclass of the wizard must also implement one of these. It handles
     * of the particulars of selecting the sources from which WSDL files will be
     * imported.
     */
    static abstract class WsdlSourceBaseWizardPage extends
            AbstractXpdWizardPage {
        TargetSelectionPage targetSelectionPage;

        protected WsdlSourceBaseWizardPage(String pageName) {
            super(pageName);
            setTitle(pageName);
        }

        void updateTarget(WsdlImportMapping[] mappings) {
            targetSelectionPage.setWsdlMappings(mappings);
        }

        /**
         * Get the result from this source page so that the target page can be
         * populated and the import can be performed.
         * 
         * @return a non-null, non-empty array of wsdl import mappings.
         */
        abstract WsdlImportMapping[] getWsdlImportMappings();
    }

}
