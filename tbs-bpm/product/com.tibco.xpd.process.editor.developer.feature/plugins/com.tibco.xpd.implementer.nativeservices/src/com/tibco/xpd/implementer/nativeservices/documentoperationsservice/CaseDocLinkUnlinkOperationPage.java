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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Link/Unlink Operation page for the Case Document, to be used in the Detailed
 * Section of the Case Document Service Task. This page is used for both Link
 * and Unlink operation, and can be customised on creation by passing
 * appropriate OPERATION_TYPE to the constructor.
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public class CaseDocLinkUnlinkOperationPage extends
        AbstractCaseDocumentOperationPage {

    /**
     * Text Input Control for Document Reference of the Document to Link/Unlink.
     */
    private ContentAssistText txtCtrlDocRef;

    /**
     * Text Input Control for Case reference to the Case Object to be
     * Linked/Unlinked.
     */
    private ContentAssistText txtCtrlCaseRef;

    /**
     * Operation Type LINK/UNLINK for which the page is created.
     */
    private OPERATION_TYPE operationType;

    /**
     * Ecore feature representing the Link Case Document Operation feature.
     */
    public static final EReference LINK_OPERATION_FEATURE =
            XpdExtensionPackage.eINSTANCE
                    .getCaseDocRefOperations_LinkCaseDocOperation();

    /**
     * Ecore feature representing the Unlink Case Document Operation feature.
     */
    public static final EReference UNLINK_OPERATION_FEATURE =
            XpdExtensionPackage.eINSTANCE
                    .getCaseDocRefOperations_UnlinkCaseDocOperation();

    /**
     * Link Operation Page Description.
     */
    private static final String LINK_DESC =
            Messages.CaseDocLinkUnlinkOperationPage_LinkDescription;

    /**
     * Unlink Operation Page Description.
     */
    private static final String UNLINK_DESC =
            Messages.CaseDocLinkUnlinkOperationPage_UnlinkDescription;

    /**
     * Operation Label for Link.
     */
    private static final String LINK_OPERATION_LABEL =
            Messages.CaseDocLinkUnlinkOperationPage_LinkLabel;

    /**
     * Operation Label for Unlink.
     */
    private static final String UNLINK_OPERATION_LABEL =
            Messages.CaseDocLinkUnlinkOperationPage_UnlinkLabel;

    /**
     * Constructor takes the Parent Details Section as argument and the
     * {@link OPERATION_TYPE} representing the Operation this page represents.
     * 
     * @param label
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public CaseDocLinkUnlinkOperationPage(
            DocumentOperationsTaskServiceSection section,
            OPERATION_TYPE operation) {

        /* Set Operation Label for Operation Type */
        super((operation == OPERATION_TYPE.LINK) ? LINK_OPERATION_LABEL
                : UNLINK_OPERATION_LABEL, section);

        this.operationType = operation;

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param caseDocRefOperation
     *            for Link/Unlink case Document Operation to update this page
     *            with.
     */
    @Override
    public void update(CaseDocRefOperations caseDocRefOperation) {

        if (caseDocRefOperation != null) {

            updateText(txtCtrlDocRef.getText(),
                    caseDocRefOperation.getCaseDocumentRefField());

            LinkCaseDocOperation linkOp =
                    caseDocRefOperation.getLinkCaseDocOperation();

            UnlinkCaseDocOperation unlinkOp =
                    caseDocRefOperation.getUnlinkCaseDocOperation();

            updateText(txtCtrlCaseRef.getText(),
                    (linkOp != null) ? linkOp.getTargetCaseRefField()
                            : ((unlinkOp != null) ? unlinkOp
                                    .getSourceCaseRefField() : "")); //$NON-NLS-1$

        } else {
            updateText(txtCtrlDocRef.getText(), ""); //$NON-NLS-1$
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
            CaseDocRefOperations caseDocRefOperation, Object control) {

        String newValue = null;
        EObject owner = null;
        String oldValue = null;
        EAttribute feature = null;
        String commandLabel = null;

        /* Handle Case Document Reference field change */
        if (control.equals(txtCtrlDocRef.getText())) {

            newValue = txtCtrlDocRef.getText().getText().trim();
            oldValue = caseDocRefOperation.getCaseDocumentRefField();

            feature =
                    XpdExtensionPackage.eINSTANCE
                            .getCaseDocRefOperations_CaseDocumentRefField();

            owner = caseDocRefOperation;
            commandLabel =
                    Messages.CaseDocumentOperationPage_SetCaseDocumentRefCommandLabel;

            /* Handle Source/Target Case Reference Field change */

        } else if (control.equals(txtCtrlCaseRef.getText())) {

            newValue = txtCtrlCaseRef.getText().getText().trim();

            if (OPERATION_TYPE.LINK.equals(this.operationType)) {

                oldValue =
                        caseDocRefOperation.getLinkCaseDocOperation()
                                .getTargetCaseRefField();
                feature =
                        XpdExtensionPackage.eINSTANCE
                                .getLinkCaseDocOperation_TargetCaseRefField();

                owner = caseDocRefOperation.getLinkCaseDocOperation();

            } else if (OPERATION_TYPE.UNLINK.equals(this.operationType)) {

                oldValue =
                        caseDocRefOperation.getUnlinkCaseDocOperation()
                                .getSourceCaseRefField();
                feature =
                        XpdExtensionPackage.eINSTANCE
                                .getUnlinkCaseDocOperation_SourceCaseRefField();
                owner = caseDocRefOperation.getUnlinkCaseDocOperation();
            }
            commandLabel =
                    Messages.CaseDocumentOperationPage_SetCaseReferenceCommandLabel;
        }

        if (feature != null) {
            return getSetCommand(editingDomain,
                    owner,
                    feature,
                    newValue,
                    oldValue,
                    commandLabel);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return root container for this Page.
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {

        /* Root Container */
        rootContainer = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.verticalSpacing = 10;
        rootContainer.setLayout(gl);

        /* Description */
        Control descControl =
                createWrappedDescriptionText(toolkit,
                        rootContainer,
                        (operationType == OPERATION_TYPE.LINK) ? LINK_DESC
                                : UNLINK_DESC);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        descControl.setLayoutData(gd);

        /* Input Controls for Document reference return */
        Label label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocumentOperationPage_DocumentReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlDocRef =
                new ContentAssistText(rootContainer, toolkit,
                        new DocRefTypeProposalProvider(this));
        txtCtrlDocRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlDocRef);

        /* Input Controls for Source Case Reference */

        label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocLinkUnlinkOperationPage_CaseReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlCaseRef =
                new ContentAssistText(rootContainer, toolkit,
                        new CaseRefTypeProposalProvider(this));

        txtCtrlCaseRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlCaseRef);

        return rootContainer;
    }

    /**
     * Enum for specifying Operation Type for the
     * {@link CaseDocLinkUnlinkOperationPage}.
     * 
     * @author aprasad
     * @since 14-Aug-2014
     */
    enum OPERATION_TYPE {
        LINK, UNLINK

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getOperationFeatureRef()
     * 
     * @return Ecore feature for the Case Document Operation this Page
     *         represents.
     */
    @Override
    public EReference getOperationFeatureRef() {

        if (operationType.equals(OPERATION_TYPE.UNLINK)) {
            return UNLINK_OPERATION_FEATURE;
        }
        return LINK_OPERATION_FEATURE;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getSetCommandForOperation(org.eclipse.emf.ecore.EReference,
     *      com.tibco.xpd.xpdExtension.DocumentOperation)
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

            CaseDocRefOperations docRefOpr =
                    docOperation.getCaseDocRefOperation();

            CompoundCommand cmd = new CompoundCommand();

            /* Append Command to create CaseDocRefOperations if does not exist */
            if (docRefOpr == null) {
                docRefOpr =
                        appendSetDocumentRefOperationCommand(cmd, docOperation);
            }

            if (operationType.equals(OPERATION_TYPE.UNLINK)) {

                UnlinkCaseDocOperation unlinkOperation =
                        XPD_EXT_FAC.createUnlinkCaseDocOperation();

                cmd.append(SetCommand
                        .create(getEditingDomain(),
                                docRefOpr,
                                CaseDocLinkUnlinkOperationPage.UNLINK_OPERATION_FEATURE,
                                unlinkOperation));

            } else if (operationType.equals(OPERATION_TYPE.LINK)) {

                LinkCaseDocOperation linkOperation =
                        XPD_EXT_FAC.createLinkCaseDocOperation();

                cmd.append(SetCommand.create(getEditingDomain(),
                        docRefOpr,
                        CaseDocLinkUnlinkOperationPage.LINK_OPERATION_FEATURE,
                        linkOperation));

            }

            if (!cmd.isEmpty()) {
                return cmd;
            }

        }
        return null;
    }
}