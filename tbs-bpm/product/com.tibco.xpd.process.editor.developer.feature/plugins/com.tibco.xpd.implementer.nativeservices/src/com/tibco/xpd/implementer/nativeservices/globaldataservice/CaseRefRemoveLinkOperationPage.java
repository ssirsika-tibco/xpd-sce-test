/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Remove link(s) to other case object(s) operation properties section of the
 * Global Data Task.
 * 
 * @author njpatel
 */
public class CaseRefRemoveLinkOperationPage extends
        AbstractOperationPage<CaseReferenceOperationsType> {

    private ContentAssistText unlinkAssociationPath;

    private ContentAssistText unlinkCaseReferencePath;

    /**
     * Remove link(s) to other case object(s) operation properties section of
     * the Global Data Task.
     * 
     * @param section
     */
    public CaseRefRemoveLinkOperationPage(GlobalDataTaskServiceSection section) {
        super(Messages.CaseRefRemoveLinkOperationPage_label, section);
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

        Composite unlinkCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        unlinkCtrlsContainer.setLayout(gl);

        Control c =
                createWrappedDescriptionText(toolkit,
                        unlinkCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_unlinkCaseObj_longdesc);
        c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer =
                toolkit.createComposite(unlinkCtrlsContainer);
        innerContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        innerContainer.setLayout(gl);

        Label lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_caseClassAssoc_label);
        lb.setLayoutData(new GridData());

        unlinkAssociationPath =
                createContentAssistText(innerContainer,
                        toolkit,
                        new CaseReferenceOperationAssociationProposalProvider());

        unlinkAssociationPath.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageControl(unlinkAssociationPath);

        lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_caseObjRef_label);
        lb.setLayoutData(new GridData());

        unlinkCaseReferencePath =
                createContentAssistText(innerContainer,
                        toolkit,
                        new CaseRefDataTypeProposalProvider() {
                            @Override
                            protected Activity getInput() {
                                EObject input = getSection().getInput();
                                return (Activity) (input instanceof Activity ? input
                                        : null);
                            }
                        });

        unlinkCaseReferencePath.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageTextControl(unlinkCaseReferencePath.getText());

        return unlinkCtrlsContainer;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractCaseRefOperationPage#update(com.tibco.xpd.xpdExtension.CaseReferenceOperationsType)
     * 
     * @param opType
     */
    @Override
    public void update(CaseReferenceOperationsType opType) {
        RemoveLinkAssociationsType removeLinkAssociations =
                opType.getRemoveLinkAssociations();

        if (removeLinkAssociations != null) {
            updateText(unlinkAssociationPath.getText(),
                    removeLinkAssociations.getAssociationName());
            updateText(unlinkCaseReferencePath.getText(),
                    removeLinkAssociations.getRemoveCaseRefField());
        } else {
            updateText(unlinkAssociationPath.getText(), ""); //$NON-NLS-1$
            updateText(unlinkCaseReferencePath.getText(), ""); //$NON-NLS-1$
        }

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractCaseRefOperationPage#getCommand(com.tibco.xpd.xpdExtension.CaseReferenceOperationsType,
     *      java.lang.Object)
     * 
     * @param opType
     * @param control
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain,
            CaseReferenceOperationsType opType, Object control) {
        Command cmd = null;
        if (control == unlinkAssociationPath.getText()) {
            /*
             * update the case association in the unlink operation of a case
             * reference operation
             */
            if (opType != null && opType.getRemoveLinkAssociations() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getRemoveLinkAssociations(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getRemoveLinkAssociationsType_AssociationName(),
                                        text);
            }
        } else if (control == unlinkCaseReferencePath.getText()) {
            /*
             * update the case reference object in the unlink operation of a
             * case reference operation
             */
            if (opType != null && opType.getRemoveLinkAssociations() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getRemoveLinkAssociations(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getRemoveLinkAssociationsType_RemoveCaseRefField(),
                                        text);
            }
        }
        return cmd;
    }

}
