/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
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
import com.tibco.xpd.xpdExtension.DeleteCaseDocOperation;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Delete Case Document Operation Page, for contribution to the Details Section
 * of a Case Document Operations Service Task.
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public class CaseDocDeleteOperationPage extends
        AbstractCaseDocumentOperationPage {

    /**
     * Text input for the Document Reference of the Document to be deleted.
     */
    private ContentAssistText txtCtrlDocRef;

    /**
     * Ecore feature representing the Delete Case Document Operation.
     */
    public static final EReference DELETE_OPERATION_FEATURE =
            XpdExtensionPackage.eINSTANCE
                    .getCaseDocRefOperations_DeleteCaseDocOperation();

    /**
     * Constructor takes Parent Detail Section as argument to which this Page is
     * added.
     * 
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public CaseDocDeleteOperationPage(
            DocumentOperationsTaskServiceSection section) {
        super(Messages.CaseDocDeleteOperationPage_Label, section);

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param caseDocRefOperation
     *            {@link CaseDocRefOperations} to update this Page for.
     */
    @Override
    public void update(CaseDocRefOperations caseDocRefOperation) {

        if (caseDocRefOperation != null) {

            updateText(txtCtrlDocRef.getText(),
                    caseDocRefOperation.getCaseDocumentRefField());
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
            CaseDocRefOperations caseDocRefOpr, Object control) {

        if (txtCtrlDocRef.getText().equals(control)) {

            String newVal = txtCtrlDocRef.getText().getText().trim();

            return getSetCommand(editingDomain,
                    caseDocRefOpr,
                    XpdExtensionPackage.eINSTANCE
                            .getCaseDocRefOperations_CaseDocumentRefField(),
                    newVal,
                    caseDocRefOpr.getCaseDocumentRefField(),
                    Messages.CaseDocumentOperationPage_SetCaseDocumentRefCommandLabel);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return Control for this Page to be displayed into the {@link PageBook}
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

        /* Description Controls */
        Control c =
                createWrappedDescriptionText(toolkit,
                        rootContainer,
                        Messages.CaseDocDeleteOperationPage_Description);

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

        return DELETE_OPERATION_FEATURE;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getSetCommandForOperation(org.eclipse.emf.ecore.EReference,
     *      com.tibco.xpd.xpdExtension.DocumentOperation)
     * 
     *      Method to create and return command to set new Operation for the
     *      given Operation Feature.
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

            if (docRefOpr == null) {
                docRefOpr =
                        appendSetDocumentRefOperationCommand(cmd, docOperation);
            }

            DeleteCaseDocOperation delOpr =
                    XPD_EXT_FAC.createDeleteCaseDocOperation();

            cmd.append(SetCommand.create(getEditingDomain(),
                    docRefOpr,
                    CaseDocDeleteOperationPage.DELETE_OPERATION_FEATURE,
                    delOpr));

            return cmd;
        }

        return null;
    }

}