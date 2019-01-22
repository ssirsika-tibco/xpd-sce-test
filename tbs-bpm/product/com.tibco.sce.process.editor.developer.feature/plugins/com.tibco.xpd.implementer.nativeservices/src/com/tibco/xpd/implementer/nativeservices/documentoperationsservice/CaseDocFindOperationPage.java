/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Property sheet sub Page for Find Case Documents Operation.This Page offers
 * operations "Find By Name" and "Find By Query".
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public class CaseDocFindOperationPage extends
        AbstractDocumentOperationPage<CaseDocFindOperations> {

    /**
     * {@link XpdExtensionPackage} instance.
     */
    private static final XpdExtensionPackage XPD_EXT_PCKG =
            XpdExtensionPackage.eINSTANCE;

    /**
     * Text input for Case Reference
     */
    private ContentAssistText txtCtrlCaseRef;

    /**
     * Text input for Document Reference
     */
    private ContentAssistText txtCtrlDocRef;

    /**
     * Radio button for Find By Name Operation Selection
     */
    private Button radioBtnFindByName;

    /**
     * Radio Button for Find By Query Operation Selection
     */
    private Button radioBtnFindByQuery;

    /**
     * Text input for Find By Name, search input, used for Find By Name option
     * only.
     */
    private ContentAssistText txtCtrlFileNameFindByNameOnly;

    /**
     * Text input for Find By Name, search input, used when Find By Query option
     * is also available.
     */
    private ContentAssistText txtCtrlFileNameFindByOptions;

    /**
     * Text Control for search query input.
     */
    private CMISQLTableControl cmisqlEditorTable;

    /**
     * Feature for CaseDocumentOperation->FindDocOperation
     */
    public static final EReference FIND_OPERATION_FEATURE = XPD_EXT_PCKG
            .getDocumentOperation_CaseDocFindOperations();

    /**
     * Feature for CaseDocumentOperation->FindDocOperation->FindByFileName
     */
    public static final EReference FIND_BY_NAME_OPERATION_FEATURE =
            XPD_EXT_PCKG.getCaseDocFindOperations_FindByFileNameOperation();

    /**
     * Feature for CaseDocumentOperation->FindDocOperation->FindByQuery
     */
    public static final EReference FIND_BY_QUERY_OPERATION_FEATURE =
            XPD_EXT_PCKG.getCaseDocFindOperations_FindByQueryOperation();

    /**
     * Feature for CaseDocumentOperation->CaseRef
     */
    public static final EAttribute CASE_REF_FEATURE = XPD_EXT_PCKG
            .getCaseDocFindOperations_CaseRefField();

    /**
     * Feature for CaseDocumentOperation->DcoumentRef
     */
    public static final EAttribute DOC_REF_FEATURE = XPD_EXT_PCKG
            .getCaseDocFindOperations_ReturnCaseDocRefsField();

    /**
     * Feature for
     * CaseDocumentOperation->FindDocOperation->FindByFileName->FileName
     */
    public static final EAttribute FILE_NAME_FEATURE = XPD_EXT_PCKG
            .getFindByFileNameOperation_FileNameField();

    /**
     * Feature for CaseDocumentOperation->FindDocOperation->FindByQuery->Query
     */
    public static final EReference QUERY_FEATURE = XPD_EXT_PCKG
            .getFindByQueryOperation_CaseDocumentQueryExpression();

    /**
     * Parent Composite for controls when Find By Name is the only search
     * options available.
     */
    private Composite parentCompositeFindByNameOnly;

    /**
     * Parent Composite for controls when Find By Name and Find By Query both
     * search options are available.
     */
    private Composite parentCompositeWithAllFindByOptions;

    /**
     * Flag indicating the Find By Query operation is implemented or not, to be
     * used to display controls appropriately
     */
    private static final boolean FIND_BY_QUERY_IMPLEMENTED = true;

    /**
     * Selected Find By Name 'File Name' input text control, this refers to the
     * text control for the applicable Find By Name search options, in both
     * cases when Find By Query is Implemented or Only Find By Name is
     * Implemented. As different set of Controls is used for both scenarios this
     * element keeps track of the Text input in current use for Find By Name
     * Operation.
     */
    private ContentAssistText selFindByNameTextControl;

    /**
     * Constructor takes Parent Detail Section as argument to which this Page is
     * added.
     * 
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public CaseDocFindOperationPage(DocumentOperationsTaskServiceSection section) {
        super(Messages.CaseDocFindOperationPage_Label, section);

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param findOperation
     *            {@link CaseDocFindOperations} to update this Page with.
     */
    @Override
    public void update(CaseDocFindOperations findOperation) {
        /*
         * Refresh Find Section if set
         */
        FindByFileNameOperation findByNameOperation =
                findOperation.getFindByFileNameOperation();

        FindByQueryOperation findByQueryOperation =
                findOperation.getFindByQueryOperation();

        /*
         * Update the Text inputs for Case Reference and Return Document
         * Reference
         */
        updateText(txtCtrlCaseRef.getText(), findOperation.getCaseRefField());

        updateText(txtCtrlDocRef.getText(),
                findOperation.getReturnCaseDocRefsField());

        /*
         * Show Radio Options only if Model contains FindByQuery element or the
         * FindByQuery Operation is Implemented, as indicated by the designated
         * flag.
         */
        if (FIND_BY_QUERY_IMPLEMENTED || findByQueryOperation != null) {

            /* Hide the Parent Composite containing only 'Find By Name' option */
            GridData layoutData =
                    (GridData) parentCompositeFindByNameOnly.getLayoutData();
            parentCompositeFindByNameOnly.setVisible(false);
            layoutData.heightHint = -1;
            layoutData.exclude = true;

            /*
             * Show Parent Composite containing options for 'Find By Name' and
             * 'Find By Query' Operations
             */
            parentCompositeWithAllFindByOptions.setVisible(true);

            if (findByQueryOperation != null) {

                selectUnSelect(radioBtnFindByQuery, radioBtnFindByName);

                /*
                 * Set Input to Query editor :: OR Refresh it if input has not
                 * changed (if you have cell editor active then set input
                 * somehow causes it to be deactivated and fire a set value in
                 * the middle of a refresh which causes command out of write
                 * transaction errors (where as refresh clears down cell editor
                 * nicely).
                 */
                EObject newInput = getParentDetailSection().getInput();
                Object currentInput = cmisqlEditorTable.getViewer().getInput();

                if (newInput != currentInput) {
                    cmisqlEditorTable.getViewer().setInput(newInput);
                } else {
                    cmisqlEditorTable.getViewer().refresh();
                }

            } else {
                /* If Find By NAME is set or by Default select Find By Name */
                selectUnSelect(radioBtnFindByName, radioBtnFindByQuery);

            }
            /* cache reference to current text input control for 'Find By Name' */
            selFindByNameTextControl = txtCtrlFileNameFindByOptions;

            /* Layout Parents */
            parentCompositeWithAllFindByOptions.layout(true);
            rootContainer.layout(true);
        } else {

            /* Hide Find By Options Composite */
            parentCompositeWithAllFindByOptions.setVisible(false);

            /* cache reference to current text input control for 'Find By Name' */
            selFindByNameTextControl = txtCtrlFileNameFindByNameOnly;

            /* Show on find By Name Operation without Radio Option Buttons */
            GridData layoutData =
                    (GridData) parentCompositeFindByNameOnly.getLayoutData();
            /* Show Find By Name ONly Composite */
            parentCompositeFindByNameOnly.setVisible(true);
            layoutData.heightHint = 100;
            layoutData.exclude = false;
            parentCompositeFindByNameOnly.layout(true);
            rootContainer.layout(true);

        }

        /* Update The Find By Name Text Control with value */
        if (selFindByNameTextControl != null) {

            String findByFileName =
                    (findByNameOperation != null) ? findByNameOperation
                            .getFileNameField() : ""; //$NON-NLS-1$

            updateText(selFindByNameTextControl.getText(), findByFileName);
        }

    }

    /**
     * 
     * This method does necessary setting of Controls visibility, for the
     * selected/unselected radio buttons, thus hiding/showing the related input
     * controls.
     * 
     * @param selButton
     *            , button to be selected , also shows the related controls.
     * @param unSelButton
     *            button to be unselected, also hides the related controls.
     */
    private void selectUnSelect(Button selButton, Button unSelButton) {

        if (radioBtnFindByName.equals(selButton)) {

            showHideControls(radioBtnFindByName,
                    txtCtrlFileNameFindByOptions,
                    true);

            showHideControls(radioBtnFindByQuery, cmisqlEditorTable, false);

        } else {

            showHideControls(radioBtnFindByName,
                    txtCtrlFileNameFindByOptions,
                    false);

            showHideControls(radioBtnFindByQuery, cmisqlEditorTable, true);

        }

        rootContainer.layout(true);

    }

    /**
     * Handle the visibility of the Controls , when not visible , reduces height
     * save space and resets it back when the controls are visible.
     * 
     * @param button
     *            is the radioButton representing the 'Find By Name' or 'Find By
     *            Query's options.
     * @param input
     *            control associated with the find option's input.
     * @param show
     *            true to show the controls, false otherwise.
     */
    private void showHideControls(Button button, Object input, boolean show) {

        Object layoutData =
                (input instanceof Control) ? ((Control) input).getLayoutData()
                        : (input instanceof ContentAssistText) ? ((ContentAssistText) input)
                                .getLayoutControl().getLayoutData() : null;

        Control control =
                (input instanceof Control) ? ((Control) input)
                        : (input instanceof ContentAssistText) ? ((ContentAssistText) input)
                                .getLayoutControl() : null;

        if (layoutData instanceof GridData) {

            GridData gridData = (GridData) layoutData;
            if (show) {

                gridData.heightHint =
                        (radioBtnFindByName.equals(button)) ? 25 : 50;
                gridData.exclude = false;
                /* Show selected controls */
                button.setSelection(show);
                control.setVisible(show);

            } else {
                gridData.heightHint = -1;
                gridData.exclude = true;

                /* unselect/hide unsel controls */
                button.setSelection(false);
                control.setVisible(false);
            }
        }
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object)
     * 
     * @param editingDomain
     * @param opType
     *            Case Document Operation displayed in this Page.
     * @param control
     *            to return the Command for.
     * @return valid command or null if invalid. Valid are the cases when the
     *         control is owned by this page and the old value and the new
     *         values are different.
     */
    @Override
    public Command getCommand(EditingDomain editingDomain,
            CaseDocFindOperations opType, Object control) {

        /* Handle change in Case Reference Field */
        if (txtCtrlCaseRef.getText().equals(control)) {

            /* Command to set case Ref */
            String newValue = txtCtrlCaseRef.getText().getText().trim();

            return getSetCommand(editingDomain,
                    opType,
                    CASE_REF_FEATURE,
                    newValue,
                    opType.getCaseRefField(),
                    Messages.CaseDocumentOperationPage_SetCaseReferenceCommandLabel);

            /* Handle change in Case Document Reference Field */
        } else if (txtCtrlDocRef.getText().equals(control)) {

            /* Command to set Doc ref */
            String newValue = txtCtrlDocRef.getText().getText().trim();

            return getSetCommand(editingDomain,
                    opType,
                    DOC_REF_FEATURE,
                    newValue,
                    opType.getReturnCaseDocRefsField(),
                    Messages.CaseDocumentOperationPage_SetCaseDocumentRefCommandLabel);

            /* Handle change in File Name Field */
        } else if (selFindByNameTextControl.getText().equals(control)) {

            FindByFileNameOperation findByNameOperation =
                    opType.getFindByFileNameOperation();
            String newValue =
                    selFindByNameTextControl.getText().getText().trim();

            if (findByNameOperation != null) {
                /* Command to set File Name */
                return getSetCommand(editingDomain,
                        findByNameOperation,
                        FILE_NAME_FEATURE,
                        newValue,
                        findByNameOperation.getFileNameField(),
                        Messages.CaseDocFindOperationPage_SetFileNameFieldCommandLabel);
            }
            /* Handle change in Query */
        } else if (cmisqlEditorTable.equals(control)) {

            /*
             * all changes are handled in the CellEditor modifications so no
             * Commands here for now
             */
            /* Handle Selection of Find By Name Operation */
        } else if (radioBtnFindByName.equals(control)
                && opType.getFindByFileNameOperation() == null) {

            /* Append command to add FindByNameOperation */
            FindByFileNameOperation op =
                    XpdExtensionFactory.eINSTANCE
                            .createFindByFileNameOperation();

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.CaseDocFindOperationPage_SetFindByFileNameOperationCommandLabel);
            cmd.append(getSetCommand(editingDomain,
                    opType,
                    FIND_BY_NAME_OPERATION_FEATURE,
                    op,
                    null,
                    Messages.CaseDocFindOperationPage_SetFindByFileNameOperationCommandLabel));

            if (opType.getFindByQueryOperation() != null) {
                /* Remove FindByQuery */
                cmd.append(getSetCommand(editingDomain,
                        opType,
                        FIND_BY_QUERY_OPERATION_FEATURE,
                        null,
                        opType.getFindByQueryOperation(),
                        Messages.CaseDocFindOperationPage_RemoveFindByQueryOperationCommandLabel));
            }
            update(opType);
            return cmd;

            /* Handle Selection of Find By Query Operation */
        } else if (radioBtnFindByQuery.equals(control)
                && opType.getFindByQueryOperation() == null) {
            /* Command to Add FindByQuery */

            FindByQueryOperation op =
                    XpdExtensionFactory.eINSTANCE.createFindByQueryOperation();

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.CaseDocFindOperationPage_SetFindByQueryOperationCommandLabel);
            cmd.append(getSetCommand(editingDomain,
                    opType,
                    FIND_BY_QUERY_OPERATION_FEATURE,
                    op,
                    null,
                    Messages.CaseDocFindOperationPage_SetFindByQueryOperationCommandLabel));

            /* Remove FindByName Operation entry */
            if (opType.getFindByFileNameOperation() != null) {
                cmd.append(getSetCommand(editingDomain,
                        opType,
                        FIND_BY_NAME_OPERATION_FEATURE,
                        null,
                        opType.getFindByFileNameOperation(),
                        Messages.CaseDocFindOperationPage_RemoveFindByFileNameOperationCommandLabel));
            }
            update(opType);
            return cmd;

        }
        /* when none satisfies return null. */
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return Control for this Page, to be displayed in the {@link PageBook}.
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {

        rootContainer = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.verticalSpacing = 10;
        rootContainer.setLayout(gl);

        /* Description */
        Control descText =
                createWrappedDescriptionText(toolkit,
                        rootContainer,
                        Messages.CaseDocFindOperationPage_Description);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        descText.setLayoutData(gd);

        /* Input Controls for Case Reference */

        Label label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocFindOperationPage_CaseReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlCaseRef =
                new ContentAssistText(rootContainer, toolkit,
                        new CaseRefTypeProposalProvider(this));

        txtCtrlCaseRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlCaseRef);

        /* Input Controls for Document reference return */
        label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocFindOperationPage_ReturnCaseDocumentRefFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlDocRef =
                new ContentAssistText(rootContainer, toolkit,
                        new DocRefTypeProposalProvider(this) {
                            /**
                             * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage.CaseDocumentOperationProposalProvider#isSingleInstance()
                             * 
                             * @return
                             */
                            @Override
                            protected boolean isSingleInstance() {
                                /* Allow single and multiple instances */
                                return false;
                            }
                        });
        txtCtrlDocRef
                .getText()
                .setToolTipText(Messages.CaseDocFindOperationPage_ReturnDocRefTooltip);

        txtCtrlDocRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlDocRef);

        /* create the container Composite with ONLY 'Find By Name' option */

        parentCompositeFindByNameOnly = toolkit.createComposite(rootContainer);
        GridData compGD = new GridData(GridData.FILL_BOTH);
        compGD.horizontalSpan = 2;
        compGD.heightHint = 60;
        parentCompositeFindByNameOnly.setLayoutData(compGD);
        parentCompositeFindByNameOnly.setLayout(new GridLayout(2, false));

        Label findByNameLabel =
                toolkit.createLabel(parentCompositeFindByNameOnly,
                        Messages.CaseDocFindOperationPage_FileNameFieldLabel);

        findByNameLabel.setLayoutData(GridDataFactory.copyData(gd));

        txtCtrlFileNameFindByNameOnly =
                new ContentAssistText(parentCompositeFindByNameOnly, toolkit,
                        new TextTypeProposalProvider(this));

        GridData txtInputGD = GridDataFactory.copyData(gd);
        txtInputGD.horizontalIndent = 15;
        txtCtrlFileNameFindByNameOnly.setLayoutData(txtInputGD);
        manageControl(txtCtrlFileNameFindByNameOnly);

        /*
         * create Container Composite with both options 'Find By Name' and 'Find
         * By Query' as Radio Buttons
         */

        parentCompositeWithAllFindByOptions =
                toolkit.createComposite(rootContainer);
        compGD = new GridData(GridData.FILL_BOTH);
        compGD.horizontalSpan = 2;
        parentCompositeWithAllFindByOptions.setLayoutData(compGD);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 10;
        parentCompositeWithAllFindByOptions.setLayout(layout);

        radioBtnFindByName =
                toolkit.createButton(parentCompositeWithAllFindByOptions,
                        Messages.CaseDocFindOperationPage_FileNameFieldLabel,
                        SWT.RADIO);

        gd = new GridData();
        gd.horizontalSpan = 2;
        radioBtnFindByName.setLayoutData(gd);
        manageControl(radioBtnFindByName);

        txtCtrlFileNameFindByOptions =
                new ContentAssistText(parentCompositeWithAllFindByOptions,
                        toolkit, new TextTypeProposalProvider(this));
        txtInputGD = new GridData(GridData.FILL_HORIZONTAL);
        txtInputGD.horizontalSpan = 2;
        txtInputGD.horizontalIndent = 15;
        txtCtrlFileNameFindByOptions.setLayoutData(txtInputGD);
        manageControl(txtCtrlFileNameFindByOptions);

        /* Input Controls for Find by Query Operation */

        radioBtnFindByQuery =
                toolkit.createButton(parentCompositeWithAllFindByOptions,
                        Messages.CaseDocFindOperationPage_FindByQueryLabel,
                        SWT.RADIO);
        gd = new GridData();
        gd.horizontalSpan = 2;
        radioBtnFindByQuery.setLayoutData(gd);
        manageControl(radioBtnFindByQuery);

        /* CMSQL Editor Table */
        cmisqlEditorTable =
                new CMISQLTableControl(parentCompositeWithAllFindByOptions,
                        toolkit, getParentDetailSection().getEditingDomain());
        cmisqlEditorTable
                .setToolTipText(Messages.CaseDocFindOperationPage_CMISQueryTooltip);
        txtInputGD = new GridData(GridData.FILL_BOTH);
        txtInputGD.horizontalSpan = 2;
        txtInputGD.horizontalIndent = 15;
        txtInputGD.minimumHeight = 50;
        cmisqlEditorTable.setLayoutData(txtInputGD);
        cmisqlEditorTable.getViewer().setInput(getParentDetailSection()
                .getInput());
        manageControl(cmisqlEditorTable);

        return rootContainer;

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getOperationFeatureRef()
     * 
     * @return Ecore feature for the Case Document Operation this Page
     *         represents.
     */
    @Override
    public EReference getOperationFeatureRef() {

        return FIND_OPERATION_FEATURE;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getSetCommandForOperation(org.eclipse.emf.ecore.EReference,
     *      com.tibco.xpd.xpdExtension.DocumentOperation) Method to create and
     *      return command to set new Operation for the given Operation Feature.
     * 
     * @param operationFeatureRef
     *            Operation feature to get the command for.
     * @param docOperation
     *            , Operation's owner.
     * @return a valid command to set the Operation, if the operationFeatureRef
     *         matches the Feature this Page represents otherwise returns null.
     */
    @Override
    protected Command getSetCommandForOperation(EReference operationFeatureRef,
            DocumentOperation docOperation) {

        if (docOperation != null) {

            CompoundCommand cmd = new CompoundCommand();

            CaseDocFindOperations findOperation =
                    XPD_EXT_FAC.createCaseDocFindOperations();

            cmd.append(SetCommand.create(getEditingDomain(),
                    docOperation,
                    XPD_EXT_PKG.getDocumentOperation_CaseDocFindOperations(),
                    findOperation));

            // Initialize to FindByName by default

            FindByFileNameOperation findByNameOpr =
                    XPD_EXT_FAC.createFindByFileNameOperation();

            cmd.append(SetCommand.create(getEditingDomain(),
                    findOperation,
                    CaseDocFindOperationPage.FIND_BY_NAME_OPERATION_FEATURE,
                    findByNameOpr));

            return cmd;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getOperation(com.tibco.xpd.xpdExtension.DocumentOperation)
     * 
     * @param docOperation
     * @return
     */
    @Override
    protected EObject getOperation(DocumentOperation docOperation) {

        if (docOperation != null) {
            return docOperation.getCaseDocFindOperations();
        }
        return null;
    }

}