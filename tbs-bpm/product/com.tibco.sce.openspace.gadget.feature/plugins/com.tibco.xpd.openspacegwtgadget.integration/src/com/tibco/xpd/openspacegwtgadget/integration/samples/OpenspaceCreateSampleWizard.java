/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.api.AbstractOpenspaceGadgetWizardPage;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution.OpenspaceSampleOverwritePolicy;
import com.tibco.xpd.openspacegwtgadget.integration.ui.OpenspaceGadgetDevPropertyTester;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.dialogs.FileSelectionBrowserControl;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Abstract Wizard base class for adding Openspace sample gadgets to a
 * destination.
 * 
 * @author aallway
 * @since 9 Jan 2013
 */
public class OpenspaceCreateSampleWizard extends Wizard implements INewWizard {

    /**
     * Standard Key to for the variable properties map for selected Gadget
     * Sample Name (tokenised sample label for use in class naming etc)
     */
    public static final String PROPERTY_SAMPLE_NAME = "sampleName"; //$NON-NLS-1$

    /**
     * Standard Key to for the variable properties map for selected Gadget
     * Sample Label.
     */
    public static final String PROPERTY_SAMPLE_LABEL = "sampleLabel"; //$NON-NLS-1$

    /**
     * Standard Key to for the variable properties map for target source
     * package's id.
     */
    public static final String PROPERTY_TARGET_PACKAGE_NAME = "packageName"; //$NON-NLS-1$

    /**
     * Only created once sample selected (on construction or when sample
     * selection.
     */
    private CreateSampleGadgetWizardPage createSamplePage = null;

    private Object initialSelection = null;

    private OpenspaceSampleCreatorContribution sampleContribution = null;

    /**
     * For construction from standard newWizard extension point etc.
     */
    public OpenspaceCreateSampleWizard() {
        super();
        setWindowTitle(Messages.OpenspaceCreateSampleWizard_createSampleOpenspaceGadgetWizard_title);
    }

    /**
     * @param sampleContribution
     *            Sample ext point contribution (i.e. type of sample to create)
     * @param initialSelection
     *            or null if none set.
     */
    public OpenspaceCreateSampleWizard(
            OpenspaceSampleCreatorContribution sampleContribution,
            Object initialSelection) {
        super();
        setWindowTitle(Messages.OpenspaceCreateSampleWizard_createSampleOpenspaceGadgetWizard_title);

        setHelpAvailable(false);

        this.sampleContribution = sampleContribution;

        init(PlatformUI.getWorkbench(),
                initialSelection != null ? new StructuredSelection(
                        initialSelection) : null);

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
        if (selection != null) {
            this.initialSelection = selection.getFirstElement();
        } else {
            this.initialSelection = null;
        }
    }

    /**
     * Add the files (as returned by {@link #getSampleFileContributions()} for
     * the given sample to the given targetSourcePackage (or relative to it/its
     * project).
     * <p>
     * The variableProperties parameter is passed to the
     * {@link OpenspaceSampleFileContribution}'s associated with the given
     * {@link OpenspaceSampleCreatorContribution} and hence can be used for
     * variable replacement in the source / target files.
     * 
     * @param destinationSourcePackage
     * @param variableProperties
     *            There are currently three variable properties that can be used
     *            in source/target paths by placing them with { } delimiters.
     *            The currently available variable properties are are
     * 
     *            <li> {@link #PROPERTY_SAMPLE_NAME} (
     *            {@value #PROPERTY_SAMPLE_NAME} ): The gadget name set by user
     *            during creation (initially set by ext point contribution.</li>
     * 
     *            <li> {@link #PROPERTY_TARGET_PACKAGE_NAME} (
     *            {@value #PROPERTY_TARGET_PACKAGE_NAME} ): The GWT gadget
     *            source package selected as a destination for new sample</li>
     * 
     *            <li> {@link #PROPERTY_SAMPLE_LABEL} (
     *            {@value #PROPERTY_SAMPLE_LABEL} ): The gadget name set by user
     *            during creation (initially set by ext point contribution.</li>
     * 
     */
    private void addSampleGadgetToPackage(
            IPackageFragment destinationSourcePackage,
            Map<String, String> variableProperties) {

        int lastConfirmOverwriteResult = -1;

        List<OpenspaceSampleFileContribution> sampleFileContributions =
                sampleContribution.getSampleFileContributions();

        for (OpenspaceSampleFileContribution sampleFileContribution : sampleFileContributions) {

            SampleSourceFileContribution sourceFileContribution =
                    sampleFileContribution.getSourceFileContribution();

            SampleTargetFileContribution targetFileContribution =
                    sampleFileContribution.getTargetFileContribution();

            /**
             * Create the input stream for source file.
             */
            InputStream sourceInputStream;

            try {
                sourceInputStream =
                        sourceFileContribution
                                .createInputStream(variableProperties);
            } catch (Exception e) {
                String message =
                        String.format(Messages.OpenspaceCreateSampleWizard_SampleFileCreationErrorSource_message,
                                sourceFileContribution.getSourceName());
                OpenspaceGadgetPlugin.getDefault().getLogger()
                        .error(e, message);

                MessageDialog
                        .openError(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.OpenspaceCreateSampleWizard_SampleFileCreationError_title,
                                message);
                return;
            }

            /**
             * Get the targetFile
             */
            IFile targetFile;

            try {
                targetFile =
                        targetFileContribution
                                .getTargetFile(destinationSourcePackage,
                                        variableProperties);

                /**
                 * Create the target from the source.
                 */

                /* If target file doesn't exist then simply create it. */
                if (!targetFile.exists()) {
                    targetFile.create(sourceInputStream, true, null);

                } else {
                    /*
                     * Otherwise decide whether to overwrite it depending on the
                     * overwrite policy setting.
                     */
                    boolean doOverwrite = false;

                    OpenspaceSampleOverwritePolicy overwritePolicy =
                            targetFileContribution.getOverwritePolicy();

                    if (OpenspaceSampleOverwritePolicy.always
                            .equals(overwritePolicy)) {
                        doOverwrite = true;

                    } else if (OpenspaceSampleOverwritePolicy.prompt
                            .equals(overwritePolicy)) {

                        if (lastConfirmOverwriteResult == MessageDialogWithYesNoToAll.BTN_YES_TO_ALL) {
                            doOverwrite = true;

                        } else if (lastConfirmOverwriteResult != MessageDialogWithYesNoToAll.BTN_NO_TO_ALL) {
                            lastConfirmOverwriteResult =
                                    MessageDialogWithYesNoToAll
                                            .open(PlatformUI
                                                    .getWorkbench()
                                                    .getModalDialogShellProvider()
                                                    .getShell(),
                                                    Messages.OpenspaceSampleFileFactory_SampleFileAlreadyExists_dialog_title,
                                                    String.format(Messages.OpenspaceSampleFileFactory_SampleFileAlreadyExists_dialog_message,
                                                            targetFile
                                                                    .getFullPath()
                                                                    .toString()));

                            if (lastConfirmOverwriteResult == MessageDialogWithYesNoToAll.BTN_CANCEL) {
                                throw new InterruptedException();
                            }

                            if (lastConfirmOverwriteResult == MessageDialogWithYesNoToAll.BTN_YES
                                    || lastConfirmOverwriteResult == MessageDialogWithYesNoToAll.BTN_YES_TO_ALL) {
                                doOverwrite = true;
                            }
                        }
                    }

                    if (doOverwrite) {
                        targetFile.setContents(sourceInputStream,
                                true,
                                true,
                                null);
                    }
                }

            } catch (InterruptedException e) {
                OpenspaceGadgetPlugin
                        .getDefault()
                        .getLogger()
                        .info(String.format(Messages.OpenspaceCreateSampleWizard_GadgetCreationCancelled_message,
                                targetFileContribution
                                        .getTargetFileName(variableProperties),
                                sourceFileContribution.getSourceName()));

                return;

            } catch (Exception e) {
                String message =
                        String.format(Messages.OpenspaceCreateSampleWizard_SampleFileCreationErrorTarget_message,
                                targetFileContribution
                                        .getTargetFileName(variableProperties));
                OpenspaceGadgetPlugin.getDefault().getLogger()
                        .error(e, message);

                MessageDialog
                        .openError(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.OpenspaceCreateSampleWizard_SampleFileCreationError_title,
                                message);

                return;

            } finally {
                if (sourceInputStream != null) {
                    try {
                        sourceInputStream.close();
                    } catch (IOException e) {
                    }
                }
            }

        } /* Next SampleFileContribution. */

    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        createSamplePage =
                new CreateSampleGadgetWizardPage(sampleContribution,
                        initialSelection);

        addPage(createSamplePage);

        /*
         * Add additional pages if any are contributed. Each one is given the
         * same variable properties map so that it can add additional properties
         * that can be used in SampleFile contributions' paths and in JET
         * templates for SourceJetEmitter contributions.
         */
        try {
            List<AdditionalWizardPageContribution> additonalWizardPageContributions =
                    sampleContribution.getAdditonalWizardPages();

            for (AdditionalWizardPageContribution pageContribution : additonalWizardPageContributions) {
                AbstractOpenspaceGadgetWizardPage page =
                        pageContribution.getWizardPage();

                page.setVariableProperties(createSamplePage
                        .getVariableProperties());

                addPage(page);
            }

        } catch (CoreException e) {
            MessageDialog.openError(null, "Error", //$NON-NLS-1$
                    "Could not load additional wizard pages:\n\n" //$NON-NLS-1$
                            + e.getMessage());

            throw new RuntimeException(e);
        }
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        try {
            createSamplePage.setInPerformFinish(true);

            addSampleGadgetToPackage(createSamplePage.getDestinationSourcePackage(),
                    createSamplePage.getVariableProperties());
        } finally {
            createSamplePage.setInPerformFinish(false);
        }
        return true;
    }

    /**
     * Select sample gadget wizard page (for target container selection etc)
     * <p>
     * When finished makes available for the wizard (via accessor methods) the
     * selected {@link OpenspaceSampleCreatorContribution} (which can be
     * optionally passed in at start), the user defined gadget name and the
     * destination Openspace Gadget project source package.
     * 
     * @author aallway
     * @since 9 Jan 2013
     */
    private static class CreateSampleGadgetWizardPage extends
            AbstractXpdWizardPage {

        private Object destinationSourcePackage;

        /**
         * When an Openspace Gadget project is pre-selected from selection
         * context then this value is non-null, indicating that other projects
         * should b e filtered out of the selection.
         */
        private Object contextProject = null;

        private Map<String, String> variableProperties;

        /**
         * Sample info contribution
         */
        private OpenspaceSampleCreatorContribution sampleContribution;

        private Text gadgetLabelControl;

        /**
         * Set true during perform finish so that can ignore selection events
         * from package selection viewer (because it does so as content changes
         * as we are copying sample files!).
         */
        private boolean inPerformFinish = false;

        /**
         * Add sample gadget wizard page (for target container selection etc)
         * 
         * @param sampleContribution
         *            Sample gadget to create or <code>null</code> will add
         *            sample gadget selection list to dialog (from
         *            OpenspaceSampleGadget ext point contributions)
         * @param initialSelection
         */
        public CreateSampleGadgetWizardPage(
                OpenspaceSampleCreatorContribution sampleContribution,
                Object initialSelection) {
            super(
                    "AddSampleGadgetWizardPage", //$NON-NLS-1$
                    String.format(Messages.OpenspaceCreateSampleWizard_createSampleOpenspaceGadgetWizardPage_title,
                            sampleContribution.getSampleName()),
                    (sampleContribution.getWizardIcon() != null ? sampleContribution
                            .getWizardIcon()
                            : OpenspaceGadgetPlugin
                                    .getImageDescriptor(OpenspaceGadgetPlugin.IMG_OPENSPACE_SAMPLE_WIZARD)));

            this.sampleContribution = sampleContribution;
            this.destinationSourcePackage = initialSelection;

            this.variableProperties = new HashMap<String, String>();

            String gadgetLabel =
                    sampleContribution != null ? sampleContribution
                            .getSampleName() : ""; //$NON-NLS-1$

            this.variableProperties
                    .put(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_LABEL,
                            gadgetLabel);
            this.variableProperties
                    .put(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_NAME,
                            NameUtil.getInternalName(gadgetLabel, true));

            setDescription(Messages.OpenspaceCreateSampleWizard_createSampleOpenspaceGadgetWizardPage_description);

            if (isValidSourcePackageSelected(initialSelection)
                    && isCurrentPage()) {
                setPageComplete(true);
            } else {
                setPageComplete(false);
            }
        }

        /**
         * @return the targetSourcePackage
         */
        public IPackageFragment getDestinationSourcePackage() {
            if (destinationSourcePackage instanceof IPackageFragment) {
                return (IPackageFragment) destinationSourcePackage;
            }
            return null;
        }

        /**
         * @return the variableProperties
         */
        public Map<String, String> getVariableProperties() {
            return variableProperties;
        }

        /**
         * @param inPerformFinish
         *            the inPerformFinish to set
         */
        public void setInPerformFinish(boolean inPerformFinish) {
            this.inPerformFinish = inPerformFinish;
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {
            XpdWizardToolkit toolkit = new XpdWizardToolkit(parent);

            Composite root = toolkit.createComposite(parent);

            root.setLayout(new GridLayout(2, false));

            /*
             * Gadget name controls.
             */
            Label label =
                    toolkit.createLabel(root,
                            Messages.OpenspaceCreateSampleWizard_GadgetName_label);
            label.setLayoutData(new GridData());

            String gadgetLabel =
                    variableProperties
                            .get(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_LABEL);

            gadgetLabelControl =
                    toolkit.createText(root, gadgetLabel != null ? gadgetLabel
                            : "", SWT.BORDER); //$NON-NLS-1$

            gadgetLabelControl.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));

            gadgetLabelControl.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    String gadgetLabel = ((Text) e.widget).getText();
                    variableProperties
                            .put(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_LABEL,
                                    gadgetLabel);

                    variableProperties
                            .put(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_NAME,
                                    NameUtil.getInternalName(gadgetLabel, true));

                    setPageComplete(validateControlValues());
                }
            });

            /*
             * Project selection.
             */
            label =
                    toolkit.createLabel(root,
                            Messages.OpenspaceCreateSampleWizard_PackageLocation_label);
            GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
            gd.verticalIndent = 4;
            label.setLayoutData(gd);

            final FileSelectionBrowserControl packageSelectViewer =
                    new FileSelectionBrowserControl();

            /*
             * If the initial selection is set to a resource within an Openspace
             * Gadget Dev Project then set up the contextProject to filter out
             * all except the context project in tree. Otherwise if there's no
             * context project then we will force user to select one.
             */
            if (destinationSourcePackage != null) {
                /* Get ancestor project of selection. */
                IProject project =
                        OpenspaceGadgetDevPropertyTester
                                .getProject(destinationSourcePackage);

                if (OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
                    contextProject = project;
                }
            }

            Composite packageSelectControl =
                    packageSelectViewer.createControl(root);

            packageSelectControl.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL | GridData.GRAB_VERTICAL));

            packageSelectViewer.addFilter(new ViewerFilter() {
                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {

                    try {

                        if (element instanceof IProject) {
                            /*
                             * When a specific context project is selected then
                             * we will only include that, regardless of anything
                             * else.
                             */
                            if (contextProject != null) {

                                if (contextProject.equals(element)) {
                                    return true;
                                }

                            } else {
                                /*
                                 * When no specific project is selected, we will
                                 * show all the projects with Openspace Gadget
                                 * Dev nature on.
                                 */
                                if (OpenspaceGadgetNature
                                        .isOpenspaceGadgetDevProject((IProject) element)) {
                                    return true;
                                }
                            }

                        } else if (element instanceof IPackageFragmentRoot) {
                            /* Make sure it's not JAR file content. */
                            IPackageFragmentRoot fragmentRoot =
                                    (IPackageFragmentRoot) element;

                            /**
                             * Sid XPD-6716: It appears that GWT Toolkit has
                             * hard coded 'test' as a special src root folder
                             * that it ignores because if you create any gadget
                             * sample under the test root package then GWT
                             * Compile fails. I've tried all sorts of names and
                             * only "test" seems to suffer. Therefore we will
                             * explicitly exclude "test" root package.
                             */
                            if (fragmentRoot.getKind() != IPackageFragmentRoot.K_BINARY
                                    && !fragmentRoot.isReadOnly()
                                    && !"test".equals(fragmentRoot
                                            .getElementName())) {
                                return true;
                            }

                        } else if (element instanceof IPackageFragment) {
                            /* Make sure it's not JAR file content. */
                            IPackageFragment fragment =
                                    (IPackageFragment) element;

                            if (fragment.getKind() != IPackageFragmentRoot.K_BINARY
                                    && !fragment.isReadOnly()) {
                                return true;
                            }
                        }
                    } catch (JavaModelException e) {
                    }
                    return false;
                }

            });

            /*
             * Do initial selection.
             */
            if (destinationSourcePackage != null) {
                /*
                 * If user selected java element but not package (i.e. a class
                 * etc) then set selection to nearest package.
                 */
                if (destinationSourcePackage instanceof IJavaElement
                        && !(destinationSourcePackage instanceof IPackageFragment)) {
                    Object parentPackage =
                            ((IJavaElement) destinationSourcePackage)
                                    .getAncestor(IPackageFragment.PACKAGE_FRAGMENT);

                    if (parentPackage instanceof IPackageFragment) {
                        destinationSourcePackage = parentPackage;
                    }
                }

                packageSelectViewer
                        .forceSetInitialSelection(destinationSourcePackage);

                updatePackageIdVariableProperty();
            }

            /* Handle selection changes. */
            packageSelectViewer.setListener(new Listener() {

                @Override
                public void handleEvent(Event event) {
                    /*
                     * MUST NOT respond to selection event when in the middle of
                     * perform finish. browser control refreshes as sample files
                     * are added to project, so can unset selection in the
                     * middle!!
                     */
                    if (event.type == SWT.Selection && !inPerformFinish) {
                        destinationSourcePackage =
                                packageSelectViewer.getSelection();

                        updatePackageIdVariableProperty();

                        setPageComplete(validateControlValues());
                    }
                }
            });

            /*
             * For per-sample customisation the sub-class can add further
             * controls. The subclass can override this method and add extra
             * values into the variableProperites map (which can then be used
             * from the OpenspaceSampleFileDescriptor specifications and also
             * the JET template variable data.
             */
            addCustomControls(root, variableProperties);

            /*
             * Set the control status.
             */
            setPageComplete(validateControlValues());

            gadgetLabelControl.selectAll();

            setControl(root);
        }

        /**
         * Update the
         * {@link OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME} in
         * {@link #variableProperties} with the package if of the currently
         * selected project
         */
        private void updatePackageIdVariableProperty() {
            String pkgId = null;
            if (destinationSourcePackage instanceof IPackageFragment) {
                pkgId =
                        ((IPackageFragment) destinationSourcePackage)
                                .getElementName();
            }

            variableProperties
                    .put(OpenspaceCreateSampleWizard.PROPERTY_TARGET_PACKAGE_NAME,
                            pkgId != null ? pkgId : ""); //$NON-NLS-1$
        }

        /**
         * For per-sample customisation the sub-class can add further controls.
         * The subclass can override this method and add extra values into the
         * variableProperites map (which can then be used from the
         * OpenspaceSampleFileDescriptor specifications and also the JET
         * template variable data.
         * 
         * @param root
         *            Root composite with a 2 column {@link GridLayout}
         * @param variableProperties
         *            You can add your own variable properties from your extra
         *            controls and then these values can be accessed in your
         *            {@link OpenspaceSampleFileContribution} target file
         *            specifications (and also in source JET template
         *            variables).
         */
        private void addCustomControls(Composite root,
                Map<String, String> variableProperties) {
        }

        /**
         * Validate the page control selections (sets title error message
         * appropriately when invalid)
         * 
         * @return <code>true</code> if control values are all ok (and finish
         *         can be pressed.
         */
        protected boolean validateControlValues() {

            if (isValidSourcePackageSelected(destinationSourcePackage)) {
                String gadgetName =
                        variableProperties
                                .get(OpenspaceCreateSampleWizard.PROPERTY_SAMPLE_NAME);

                if (gadgetName != null && gadgetName.length() > 0) {

                    /* Wipe the current error message. */
                    setErrorMessage(null);

                    return true;

                } else {
                    setErrorMessage(String
                            .format(Messages.OpenspaceCreateSampleWizard_InvalidGadgetname_message,
                                    gadgetName != null ? gadgetName : "")); //$NON-NLS-1$
                }

            } else {
                setErrorMessage(Messages.OpenspaceCreateSampleWizard_InvalidSourcePackageLocation_message);
            }

            return false;
        }

        /**
         * @return <code>true</code> if the current selection
         *         {@link #destinationSourcePackage} is a valid destination for
         *         sample gadget
         */
        private boolean isValidSourcePackageSelected(Object selectedTarget) {
            if (selectedTarget instanceof IPackageFragment) {
                IPackageFragment targetSourcePackage =
                        (IPackageFragment) selectedTarget;

                try {
                    if (targetSourcePackage.getKind() != IPackageFragmentRoot.K_BINARY
                            && !targetSourcePackage.isReadOnly()) {
                        IResource pkgResource =
                                targetSourcePackage.getResource();

                        if (pkgResource instanceof IFolder) {

                            if (OpenspaceGadgetNature
                                    .isOpenspaceGadgetDevProject(pkgResource
                                            .getProject())) {
                                return true;
                            }
                        }
                    }
                } catch (JavaModelException e) {
                }
            }

            return false;
        }

    }

}
