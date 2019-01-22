/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import org.eclipse.emf.common.command.Command;
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
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Link System Document Operation Page to be Contributed to the Detail Section
 * of the Document Operations Service Task.
 * 
 * @author aprasad
 * @since 05-Sept-2014
 */
public class LinkSystemDocumentOperationPage extends
        AbstractDocumentOperationPage<LinkSystemDocumentOperation> {

    /**
     * Text Input Control for Document id identifying the Document to be linked.
     */
    private ContentAssistText txtCtrlDocId;

    /**
     * Text Input Control for Case Reference identifying the Case object to link
     * the document to.
     */
    private ContentAssistText txtCtrlCaseRef;

    /**
     * Text Input Control for Return Document Reference of the Document returned
     * by the operation after linking the Document to the Case Object.
     */
    private ContentAssistText txtCtrlDocumentRef;

    /**
     * Ecore feature representing the Move Case Document Operation.
     */
    public static final EReference LINK_SYS_DOC_OPERATION_FEATURE =
            XpdExtensionPackage.eINSTANCE
                    .getDocumentOperation_LinkSystemDocumentOperation();

    /**
     * Constructor takes Parent Detail Section as argument to which this Page is
     * added.
     * 
     * @param section
     *            Parent Details Section to which this page is added.
     */
    public LinkSystemDocumentOperationPage(
            DocumentOperationsTaskServiceSection section) {

        super(Messages.LinkSystemDocumentOperationPage_LinkOperationTitle,
                section);

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param caseDocRefOperation
     *            {@link CaseDocRefOperations} representing the Move Document
     *            Operation to update this Page for.
     */
    @Override
    public void update(LinkSystemDocumentOperation linkSystemDocumentOperation) {

        /* reset before setting */
        updateText(txtCtrlDocId.getText(), ""); //$NON-NLS-1$
        updateText(txtCtrlCaseRef.getText(), ""); //$NON-NLS-1$
        updateText(txtCtrlDocumentRef.getText(), ""); //$NON-NLS-1$

        if (linkSystemDocumentOperation != null) {

            updateText(txtCtrlDocId.getText(),
                    linkSystemDocumentOperation.getDocumentId());

            updateText(txtCtrlCaseRef.getText(),
                    linkSystemDocumentOperation.getCaseRefField());
            updateText(txtCtrlDocumentRef.getText(),
                    linkSystemDocumentOperation.getReturnCaseDocRefField());
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
            LinkSystemDocumentOperation linkSystemDocumentOperation,
            Object control) {

        if (control != null) {

            String newValue = null;
            String oldValue = null;
            EAttribute attributeFeature = null;
            String commandLabel = null;
            Object owner = linkSystemDocumentOperation;

            /* Handle Case Document id change */
            if (control.equals(txtCtrlDocId.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_DocumentId();

                newValue = txtCtrlDocId.getText().getText().trim();
                oldValue = linkSystemDocumentOperation.getDocumentId();
                commandLabel =
                        Messages.LinkSystemDocumentOperationPage_SsetDocIdCmdLabel;

                /* Handle Target Case Reference change */
            } else if (control.equals(txtCtrlCaseRef.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_CaseRefField();

                newValue = txtCtrlCaseRef.getText().getText().trim();
                oldValue = linkSystemDocumentOperation.getCaseRefField();
                commandLabel =
                        Messages.CaseDocMoveOperationPage_SetTargetCaseReferenceCommandLabel;

                /* Handle Case Document Reference change */
            } else if (control.equals(txtCtrlDocumentRef.getText())) {

                attributeFeature =
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_ReturnCaseDocRefField();

                newValue = txtCtrlDocumentRef.getText().getText().trim();
                oldValue =
                        linkSystemDocumentOperation.getReturnCaseDocRefField();
                commandLabel =
                        Messages.CaseDocFindOperationPage_ReturnCaseDocumentRefFieldLabel;

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
                        Messages.LinkSystemDocumentOperationPage_PageDescription);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        c.setLayoutData(gd);

        /* Input Controls for Document reference return */
        Label label =
                toolkit.createLabel(rootContainer,
                        Messages.LinkSystemDocumentOperationPage_DocumentIdFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlDocId =
                new ContentAssistText(rootContainer, toolkit,
                        new TextTypeProposalProvider(this));
        txtCtrlDocId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlDocId);

        /* Input Controls for Source Case Reference */

        label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocFindOperationPage_CaseReferenceFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlCaseRef =
                new ContentAssistText(rootContainer, toolkit,
                        new CaseRefTypeProposalProvider(this));

        txtCtrlCaseRef.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlCaseRef);

        /* Input Controls for Target Case Reference */

        label =
                toolkit.createLabel(rootContainer,
                        Messages.LinkSystemDocumentOperationPage_ReturnDocRefFieldLabel);
        label.setLayoutData(GridDataFactory.swtDefaults().create());

        txtCtrlDocumentRef =
                new ContentAssistText(rootContainer, toolkit,
                        new DocRefTypeProposalProvider(this));

        txtCtrlDocumentRef
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtCtrlDocumentRef);

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

        return LINK_SYS_DOC_OPERATION_FEATURE;
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

            LinkSystemDocumentOperation linkOpr =
                    XPD_EXT_FAC.createLinkSystemDocumentOperation();

            return SetCommand.create(getEditingDomain(),
                    docOperation,
                    getOperationFeatureRef(),
                    linkOpr);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getOperation(com.tibco.xpd.xpdExtension.DocumentOperation)
     * 
     * @param docOperation
     * @return {@link LinkSystemDocumentOperation} , null if
     *         {@link DocumentOperation} is null.
     */
    @Override
    protected EObject getOperation(DocumentOperation docOperation) {

        if (docOperation != null) {
            return docOperation.getLinkSystemDocumentOperation();
        }

        return null;
    }

}