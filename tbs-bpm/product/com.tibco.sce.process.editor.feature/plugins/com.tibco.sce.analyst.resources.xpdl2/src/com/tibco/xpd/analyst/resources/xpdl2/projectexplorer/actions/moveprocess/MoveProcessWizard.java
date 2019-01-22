/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;
import org.eclipse.ltk.internal.core.refactoring.Messages;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdUserInputWizardPage;
import com.tibco.xpd.ui.dialogs.FileSelectionBrowserControl;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactoring wizard to move process to a different package which is assisted
 * by LTK frame-work so that the user can preview the changes.
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
public class MoveProcessWizard extends RefactoringWizard {

    /**
     * 
     * @param process
     */
    public MoveProcessWizard(Process process) {
        super(new MoveRefactoring(new MoveProcessProcessor(process)),
                DIALOG_BASED_USER_INTERFACE);
        setDefaultPageTitle(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_MoveProcessPage_title);
        setWindowTitle(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_MoveProcessWindow_title);

    }

    /**
     * Should be overridden only if we want to display a wizard page to assist
     * the user to select the destination.
     * 
     * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
     * 
     */
    @Override
    protected void addUserInputPages() {

        MoveProcessProcessor processor =
                (MoveProcessProcessor) getRefactoring()
                        .getAdapter(MoveProcessProcessor.class);

        addPage(new MoveProcessRefactoringConfigurationPage(processor));
    }

    /**
     * Provides the user with a wizard page to select the destination
     * folder/package to which the process should be moved.
     * 
     * 
     * @author kthombar
     * @since 14-Sep-2014
     */
    private static class MoveProcessRefactoringConfigurationPage extends
            AbstractXpdUserInputWizardPage implements Listener {

        private final MoveProcessProcessor moveProcessProcessor;

        /**
         * Control that help selecting the package to which we wish to refactor
         * the process.
         */
        private FileSelectionBrowserControl fileSelectionControl;

        /**
         * Text that will hold the new package name
         */
        private Text newPkgTxt;

        private Group group;

        /**
         * The continue check-box which user needs to check in order to proceed
         */
        private Button continueButton;

        /**
         * the valid Package nane pattern
         */
        private final Pattern validPackageNameChars = Pattern
                .compile("^[a-zA-Z0-9\\._-]*[a-zA-Z0-9][a-zA-Z0-9\\._-]*$"); //$NON-NLS-1$

        /**
         * keeps track of the last selection
         */
        private static Object lastSelection;

        public MoveProcessRefactoringConfigurationPage(
                MoveProcessProcessor processor) {
            super("MoveProcessRefactoringConfigurationPage"); //$NON-NLS-1$
            moveProcessProcessor = processor;
        }

        /**
         * 
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @SuppressWarnings("restriction")
        @Override
        public void createControl(Composite parent) {
            initializeDialogUnits(parent);

            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(1, false));
            composite
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            composite.setFont(parent.getFont());

            Label label = new Label(composite, SWT.NONE);

            Process processesToMove = moveProcessProcessor.getProcessToMove();

            label.setText(Messages
                    .format(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_ChooseDestinationPackage_desc,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(processesToMove)));

            label.setLayoutData(new GridData());

            /* Filter for the tree to show */
            ViewerFilter[] viewerFilters = createViewerFilters();

            /* Create the container selection group */
            fileSelectionControl = new FileSelectionBrowserControl();
            Composite ctrl = fileSelectionControl.createControl(composite);
            ctrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

            if (moveProcessProcessor.getDestinationResource() instanceof IFile) {
                /*
                 * In case of Drag and drop, we already know the destination
                 * package to which we want to drop the process. Hence set it as
                 * the initial selection.
                 */
                IFile file =
                        (IFile) moveProcessProcessor.getDestinationResource();

                if (file != null && file.isAccessible()) {
                    fileSelectionControl
                            .setInitialSelection(new StructuredSelection(file));
                }
            } else if (lastSelection != null) {
                /*
                 * If we do not drag and drop the process, then the last
                 * selection will be the initial selection.
                 */
                fileSelectionControl
                        .setInitialSelection(new StructuredSelection(
                                lastSelection));
            }

            for (ViewerFilter filter : viewerFilters) {
                /* apply the filters */
                fileSelectionControl.addFilter(filter);
            }

            fileSelectionControl.setListener(this);

            /*
             * Create a group which allows the user to enter the new package
             * name, which he wants to create.
             */
            group = new Group(composite, SWT.NONE);
            group.setText(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_NewPackageGroup_desc);
            group.setLayout(new GridLayout(1, false));
            GridData groupLayoutData =
                    new GridData(SWT.FILL, SWT.FILL, true, true);
            groupLayoutData.heightHint = 1;
            group.setLayoutData(groupLayoutData);
            group.setVisible(false);
            group.setFont(parent.getFont());

            Label newPackageLbl = new Label(group, SWT.NONE);
            newPackageLbl
                    .setText(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_NewPackageName_label);
            GridData lblGD = new GridData(GridData.FILL_HORIZONTAL);
            lblGD.heightHint = 15;
            newPackageLbl.setLayoutData(lblGD);

            newPkgTxt = new Text(group, SWT.BORDER);
            GridData txtGD = new GridData(GridData.FILL_HORIZONTAL);
            txtGD.heightHint = 15;
            newPkgTxt.setLayoutData(txtGD);
            newPkgTxt.addListener(SWT.Modify, this);

            continueButton = new Button(composite, SWT.CHECK);
            continueButton
                    .setText(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_CannotUndoProcessMove_text);
            GridData contibueButtonGD = new GridData(GridData.FILL_HORIZONTAL);
            continueButton.setLayoutData(contibueButtonGD);
            continueButton.addListener(SWT.Selection, this);

            setPageComplete(false);
            setControl(composite);
        }

        /**
         * create the filters on the tree viewer
         * 
         * @return ViewerFilter[]
         */
        private ViewerFilter[] createViewerFilters() {

            List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

            filters.add(new XpdNatureProjectsOnly(false));
            filters.add(new NoFileContentFilter());
            filters.add(new SpecialFoldersOnlyFilter(
                    Collections
                            .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)));
            filters.add(new FileExtensionInclusionFilter(Collections
                    .singleton(Xpdl2ResourcesConsts.XPDL_EXTENSION)));

            return filters.toArray(new ViewerFilter[filters.size()]);
        }

        /**
         * 
         * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#setVisible(boolean)
         * 
         * @param visible
         */
        @Override
        public void setVisible(boolean visible) {
            if (visible) {
                if (getErrorMessage() != null) {
                    /* No error message untill user interacts */
                    setErrorMessage(null);
                }
            }
            super.setVisible(visible);
        }

        /**
         * 
         * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#performFinish()
         * 
         * @return
         */
        @Override
        protected boolean performFinish() {
            initializeRefactoring();
            return super.performFinish();
        }

        /**
         * 
         * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#getNextPage()
         * 
         * @return
         */
        @Override
        public IWizardPage getNextPage() {
            initializeRefactoring();
            return super.getNextPage();
        }

        /**
         * Sets the target package to which the processes are to be moved.
         */
        @SuppressWarnings("restriction")
        private void initializeRefactoring() {

            Object selection = fileSelectionControl.getSelection();

            if (selection instanceof IFile) {
                /*
                 * Initialize the refactoring with the target package. Also
                 * keeps track of the last selection.
                 */
                lastSelection = selection;
                moveProcessProcessor.setDestinationResource((IFile) selection);

            } else if (selection instanceof SpecialFolderImpl) {
                SpecialFolderImpl specialFolder = (SpecialFolderImpl) selection;
                IFolder folder = specialFolder.getFolder();

                if (folder instanceof IResource) {

                    IResource destinationResource = folder;
                    moveProcessProcessor
                            .setDestinationResource(destinationResource);

                    moveProcessProcessor.setNewPackaqeName(newPkgTxt.getText()
                            + "." + Xpdl2ResourcesConsts.XPDL_EXTENSION); //$NON-NLS-1$
                }
            }
        }

        /**
         * 
         * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
         * 
         * @param event
         */
        @Override
        public void handleEvent(Event event) {
            RefactoringStatus status = new RefactoringStatus();

            Object selection = fileSelectionControl.getSelection();

            if (selection instanceof IFile) {
                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy((IFile) selection);

                EObject rootElement = workingCopy.getRootElement();

                if (rootElement instanceof Package) {
                    /*
                     * If the destination is a package , then check if it not
                     * the package of the source process itself.
                     */
                    status =
                            moveProcessProcessor
                                    .validateDestination((com.tibco.xpd.xpdl2.Package) rootElement);

                    GridData lblLayoutData = (GridData) group.getLayoutData();
                    lblLayoutData.heightHint = 1;
                    group.setVisible(false);
                    group.getParent().layout(true);
                }
            } else if (selection instanceof SpecialFolder) {
                /*
                 * If the destination is a Process Package folder, then show the
                 * text box where the user can enter the new package name.
                 */
                GridData lblLayoutData = (GridData) group.getLayoutData();
                lblLayoutData.heightHint = 100;
                group.setVisible(true);
                group.getParent().layout(true);

                String text = newPkgTxt.getText();

                if (text == null || text.isEmpty()) {
                    /*
                     * If the selection is a package and the user hasnt entered
                     * a package name then complain.
                     */
                    status.merge(RefactoringStatus
                            .createFatalErrorStatus(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_EnterNewPackageNameValidation_msg));
                } else {
                    /*
                     * If the user has provided a name, however a package with
                     * that name already exists then complain.
                     */
                    text = text + "." + Xpdl2ResourcesConsts.XPDL_EXTENSION; //$NON-NLS-1$

                    IFolder folder = ((SpecialFolder) selection).getFolder();

                    try {
                        IResource[] members = folder.members();

                        for (IResource iResource : members) {

                            String name = iResource.getName();

                            if (name != null && !name.isEmpty()) {

                                if (name.equalsIgnoreCase(text)) {

                                    status.merge(RefactoringStatus.createFatalErrorStatus(String
                                            .format(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_PackageAlreadyExistsValidation_msg,
                                                    newPkgTxt.getText())));
                                }
                            }
                        }
                    } catch (CoreException e) {

                        e.printStackTrace();
                    }

                    /*
                     * Check if the Package name has valid characters
                     */
                    if (!validPackageNameChars.matcher(newPkgTxt.getText())
                            .matches()) {
                        status.merge(RefactoringStatus.createFatalErrorStatus(String
                                .format(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_PackageNameInvalidValidation_msg,
                                        newPkgTxt.getText())));
                    }

                }

            } else {
                /*
                 * If the destination is a project then ask the user to select a
                 * process package.
                 */
                GridData lblLayoutData = (GridData) group.getLayoutData();
                lblLayoutData.heightHint = 1;
                group.setVisible(false);
                group.getParent().layout(true);
                status.merge(RefactoringStatus
                        .createFatalErrorStatus(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_ChooseProcessPackageValidation_msg));
            }

            if (!continueButton.getSelection()) {
                /*
                 * If there are no errors , but if the user has not selected the
                 * agree to continue button then do not allow the page to
                 * complete.
                 */
                status.merge(RefactoringStatus
                        .createFatalErrorStatus(com.tibco.xpd.analyst.resources.xpdl2.internal.Messages.MoveProcessWizard_AcceptConditionToProceedValidation_msg));
            }
            setPageComplete(status);
        }
    }
}
