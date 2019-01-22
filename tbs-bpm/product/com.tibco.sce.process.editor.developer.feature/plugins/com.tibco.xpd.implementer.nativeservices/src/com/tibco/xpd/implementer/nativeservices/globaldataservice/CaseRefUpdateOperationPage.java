/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import org.eclipse.emf.common.command.Command;
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
import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Update Case Object(s) From Local Data operation properties section of the
 * Global Data Task.
 * 
 * @author njpatel
 */
public class CaseRefUpdateOperationPage extends
        AbstractOperationPage<CaseReferenceOperationsType> {

    private ContentAssistText updateLocalDataFieldPath;

    /**
     * Update Case Object(s) From Local Data operation properties section of the
     * Global Data Task.
     * 
     * @param section
     */
    public CaseRefUpdateOperationPage(GlobalDataTaskServiceSection section) {
        super(Messages.CaseRefUpdateOperationPage_label, section);
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

        Composite updateCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        updateCtrlsContainer.setLayout(gl);

        Control descText =
                createWrappedDescriptionText(toolkit,
                        updateCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_updateCaseRefFromLocalData_shortdesc);
        descText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer =
                toolkit.createComposite(updateCtrlsContainer);
        innerContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        innerContainer.setLayout(gl);

        Label lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_localDataValue_label);
        lb.setLayoutData(new GridData());

        updateLocalDataFieldPath =
                createContentAssistText(innerContainer,
                        toolkit,
                        new LocalDataProposalProvider(false));

        updateLocalDataFieldPath.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageControl(updateLocalDataFieldPath);

        return updateCtrlsContainer;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractCaseRefOperationPage#update(com.tibco.xpd.xpdExtension.CaseReferenceOperationsType)
     * 
     * @param opType
     */
    @Override
    public void update(CaseReferenceOperationsType opType) {
        UpdateCaseOperationType update = opType.getUpdate();
        updateText(updateLocalDataFieldPath.getText(),
                update != null ? update.getFromFieldPath() : ""); //$NON-NLS-1$
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
        if (control == updateLocalDataFieldPath.getText()) {
            // update the local data in the upate operation of a case
            // reference operation
            if (opType != null && opType.getUpdate() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getUpdate(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getUpdateCaseOperationType_FromFieldPath(),
                                        text);
            }
        }
        return cmd;
    }

}
