/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Move Case Document Operation Page to be Contributed to the Detail Section of
 * the Case Document Service Task.
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public class CaseDocMoveOperationPage extends AbstractCaseDocumentOperationPage {

    /**
     * Text Input Control for Document reference identifying the Document to be
     * moved.
     */
    private ContentAssistText txtCtrlDocRef;

    /**
     * Text Input Control for Source Case Reference identifying the Case object
     * to move from.
     */
    private ContentAssistText txtCtrlSrcCaseRef;

    /**
     * Text Input Control for Target Case Reference identifying the Case object
     * to move to.
     */
    private ContentAssistText txtCtrlTrgtCaseRef;

    /**
     * Ecor feature representing the Move Case Document Operation.
     */
    public static final EReference MOVE_OPERATION_FEATURE =
            XpdExtensionPackage.eINSTANCE
                    .getCaseDocRefOperations_MoveCaseDocOperation();

    /**
     * Constructor takes Parent Detail Section as argument to which this Page is
     * added.
     * 
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public CaseDocMoveOperationPage(DocumentOperationsTaskServiceSection section) {

        super(Messages.CaseDocMoveOperationPage_Label, section);

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param caseDocRefOperation
     *            {@link CaseDocRefOperations} representing the Move Document
     *            Operation to update this Page for.
     */
    @Override
    public void update(CaseDocRefOperations caseDocumentOperation) {

        /* reset before setting */
        updateText(txtCtrlDocRef.getText(), ""); //$NON-NLS-1$
        updateText(txtCtrlSrcCaseRef.getText(), ""); //$NON-NLS-1$
        updateText(txtCtrlTrgtCaseRef.getText(), ""); //$NON-NLS-1$

        if (caseDocumentOperation != null) {

            updateText(txtCtrlDocRef.getText(),
                    caseDocumentOperation.getCaseDocumentRefField());

            MoveCaseDocOperation moveCaseDocument =
                    caseDocumentOperation.getMoveCaseDocOperation();

            if (moveCaseDocument != null) {

                updateText(txtCtrlSrcCaseRef.getText(),
                        moveCaseDocument.getSourceCaseRefField());
                updateText(txtCtrlTrgtCaseRef.getText(),
                        moveCaseDocument.getTargetCaseRefField());
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
            CaseDocRefOperations caseDocRefOperation, Object control) {

        if (control != null) {

            String newValue = null;
            String oldValue = null;
            EAttribute attributeFeature = null;
            String commandLabel = null;
            Object owner = caseDocRefOperation.getMoveCaseDocOperation();

            /* Handle Case Document Reference change */
            if (control.equals(txtCtrlDocRef.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getCaseDocRefOperations_CaseDocumentRefField();

                newValue = txtCtrlDocRef.getText().getText().trim();
                oldValue = caseDocRefOperation.getCaseDocumentRefField();
                commandLabel =
                        Messages.CaseDocMoveOperationPage_SetCaseDocumentRefCommandLabel;
                owner = caseDocRefOperation;

                /* Handle Source Case Reference change */
            } else if (control.equals(txtCtrlSrcCaseRef.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getMoveCaseDocOperation_SourceCaseRefField();

                newValue = txtCtrlSrcCaseRef.getText().getText().trim();
                oldValue =
                        caseDocRefOperation.getMoveCaseDocOperation()
                                .getSourceCaseRefField();
                commandLabel =
                        Messages.CaseDocMoveOperationPage_SetSrcCaseReferenceCommandLabel;

                /* Handle Target Case Reference change */
            } else if (control.equals(txtCtrlTrgtCaseRef.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getMoveCaseDocOperation_TargetCaseRefField();

                newValue = txtCtrlTrgtCaseRef.getText().getText().trim();
                oldValue =
                        caseDocRefOperation.getMoveCaseDocOperation()
                                .getTargetCaseRefField();
                commandLabel =
                        Messages.CaseDocMoveOperationPage_SetTargetCaseReferenceCommandLabel;

            }

            if (attributeFeature != null) {

                return getSetCommand(editingDomain,
                        owner,
                        attributeFeature,
                        newValue,
                        oldValue,
                        commandLabel);
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return Root Control for this Page to be displayed in the
     *         {@link PageBook}.
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {

        /* Move Operations page Container */
        rootContainer = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.verticalSpacing = 10;
        rootContainer.setLayout(gl);

        /* Description Controls */
        Control c =
                createWrappedDescriptionText(toolkit,
                        rootContainer,
                        Messages.CaseDocMoveOperationPage_Description);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        c.setLayoutData(gd);

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
                        Messages.CaseDocMoveOperationPage_SourceCaseReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlSrcCaseRef =
                new ContentAssistText(rootContainer, toolkit,
                        new CaseRefTypeProposalProvider(this));

        txtCtrlSrcCaseRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlSrcCaseRef);

        /* Input Controls for Target Case Reference */

        label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocMoveOperationPage_TargetCaseReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlTrgtCaseRef =
                new ContentAssistText(rootContainer, toolkit,
                        new CaseRefTypeProposalProvider(this));

        txtCtrlTrgtCaseRef
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlTrgtCaseRef);

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

        return MOVE_OPERATION_FEATURE;
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

            CaseDocRefOperations caseDocRefOpr =
                    docOperation.getCaseDocRefOperation();

            CompoundCommand cmd = new CompoundCommand();

            /* Append Command to create CaseDocRefOperations if does not exist */
            if (caseDocRefOpr == null) {
                caseDocRefOpr =
                        appendSetDocumentRefOperationCommand(cmd, docOperation);
            }

            MoveCaseDocOperation moveOperation =
                    XPD_EXT_FAC.createMoveCaseDocOperation();

            cmd.append(SetCommand.create(getEditingDomain(),
                    caseDocRefOpr,
                    CaseDocMoveOperationPage.MOVE_OPERATION_FEATURE,
                    moveOperation));

            return cmd;

        }
        return null;
    }

}