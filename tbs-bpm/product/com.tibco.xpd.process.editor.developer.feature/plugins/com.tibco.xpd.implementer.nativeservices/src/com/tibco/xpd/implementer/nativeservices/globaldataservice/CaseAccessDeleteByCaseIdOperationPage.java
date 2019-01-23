/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;

/**
 * Delete Case Object(s) by Case Identifier operation properties section of the
 * Global Data Task.
 * 
 * @author njpatel
 */
public class CaseAccessDeleteByCaseIdOperationPage extends
        AbstractOperationPage<CaseAccessOperationsType> {

    private ContentAssistText deleteCaseIdName;

    private ContentAssistText deleteFieldPath;

    /**
     * Delete Case Object(s) by Case Identifier operation properties section of
     * the Global Data Task.
     * 
     * @param section
     */
    public CaseAccessDeleteByCaseIdOperationPage(
            GlobalDataTaskServiceSection section) {
        super(Messages.CaseAccessDeleteByCaseIdOperationPage_label, section);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param opType
     */
    @Override
    public void update(CaseAccessOperationsType opType) {
        /*
         * Refresh Delete by case id section if set
         */
        DeleteByCaseIdentifierType deleteIdentifier =
                opType.getDeleteByCaseIdentifier();
        if (deleteIdentifier != null) {
            updateText(deleteCaseIdName.getText(),
                    deleteIdentifier.getIdentifierName());
            updateText(deleteFieldPath.getText(),
                    deleteIdentifier.getFieldPath());
        } else {
            updateText(deleteCaseIdName.getText(), ""); //$NON-NLS-1$
            updateText(deleteFieldPath.getText(), ""); //$NON-NLS-1$
        }

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object)
     * 
     * @param editingDomain
     * @param opType
     * @param control
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain,
            CaseAccessOperationsType opType, Object control) {
        Command cmd = null;

        if (control == deleteCaseIdName.getText()) {
            if (opType != null && opType.getDeleteByCaseIdentifier() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getDeleteByCaseIdentifier(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDeleteByCaseIdentifierType_IdentifierName(),
                                        text);
            }
        } else if (control == deleteFieldPath.getText()) {
            if (opType != null && opType.getDeleteByCaseIdentifier() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getDeleteByCaseIdentifier(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDeleteByCaseIdentifierType_FieldPath(),
                                        text);
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#createPage(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    public Control createPage(Composite parent, XpdFormToolkit toolkit) {
        GridLayout gl;
        Label lb;

        Composite delByIdCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        delByIdCtrlsContainer.setLayout(gl);

        Control c =
                createWrappedDescriptionText(toolkit,
                        delByIdCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_deleteCaseObjWithMatchingCaseId_longdesc);
        c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer =
                toolkit.createComposite(delByIdCtrlsContainer);
        innerContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        innerContainer.setLayout(gl);

        lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_whereCaseId_label);
        lb.setLayoutData(new GridData());

        deleteCaseIdName =
                createContentAssistText(innerContainer,
                        toolkit,
                        new IContentProposalProvider() {

                            @Override
                            public IContentProposal[] getProposals(
                                    String contents, int position) {
                                // Get the case identifiers of the selected case
                                // class
                                GlobalDataOperation dataOp =
                                        getSection().getGlobalDataOperation();
                                if (dataOp != null) {
                                    CaseAccessOperationsType accessOperationsType =
                                            dataOp.getCaseAccessOperations();
                                    if (accessOperationsType != null) {
                                        ExternalReference reference =
                                                accessOperationsType
                                                        .getCaseClassExternalReference();

                                        if (reference != null) {
                                            Class caseClass =
                                                    GlobalDataTaskServiceSection
                                                            .getReferencedClass(WorkingCopyUtil
                                                                    .getProjectFor(reference),
                                                                    reference);

                                            if (caseClass != null) {
                                                List<Property> identifiers =
                                                        GlobalDataTaskServiceSection
                                                                .getCaseIdentifiers(caseClass);

                                                if (!identifiers.isEmpty()) {
                                                    List<ContentProposal> proposals =
                                                            new ArrayList<ContentProposal>();

                                                    for (Property ident : identifiers) {
                                                        String displayName =
                                                                PrimitivesUtil
                                                                        .getDisplayLabel(ident);

                                                        if (doesProposalMatch(ident
                                                                .getName(),
                                                                contents,
                                                                position)) {
                                                            proposals
                                                                    .add(new ContentProposal(
                                                                            ident.getName(),
                                                                            displayName));
                                                        }
                                                    }

                                                    return proposals
                                                            .toArray(new IContentProposal[proposals
                                                                    .size()]);
                                                }
                                            }
                                        }
                                    }
                                }

                                return new IContentProposal[0];
                            }
                        });
        deleteCaseIdName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        manageControl(deleteCaseIdName);

        lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_equalsValueOf_label);
        lb.setLayoutData(new GridData());

        deleteFieldPath =
                createContentAssistText(innerContainer,
                        toolkit,
                        new LocalDataProposalProvider(true));
        deleteFieldPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageTextControl(deleteFieldPath.getText());

        return delByIdCtrlsContainer;
    }

}
