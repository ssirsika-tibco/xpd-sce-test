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
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Create Case Object(s) from Local Data page of the Global Data operations
 * section.
 * 
 * @author njpatel
 */
public class CaseAccessCreateOperationPage extends
        AbstractOperationPage<CaseAccessOperationsType> {

    private ContentAssistText createLocalDataFieldPath;

    private ContentAssistText createToCaseRefField;

    /**
     * Create Case Object(s) from Local Data page of the Global Data operations
     * section.
     * 
     * @param section
     */
    public CaseAccessCreateOperationPage(GlobalDataTaskServiceSection section) {
        super(Messages.CaseAccessCreateOperationPage_label, section);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage#update(org.eclipse.emf.ecore.EObject)
     * 
     * @param opType
     */
    @Override
    public void update(CaseAccessOperationsType opType) {
        /*
         * Refresh Create section if set
         */
        CreateCaseOperationType create = opType.getCreate();
        if (create != null) {
            updateText(createLocalDataFieldPath.getText(),
                    create.getFromFieldPath());
            updateText(createToCaseRefField.getText(),
                    create.getToCaseRefField());
        } else {
            updateText(createLocalDataFieldPath.getText(), ""); //$NON-NLS-1$
            updateText(createToCaseRefField.getText(), ""); //$NON-NLS-1$
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
        if (control == createLocalDataFieldPath.getText()) {
            if (opType != null && opType.getCreate() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getCreate(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getCreateCaseOperationType_FromFieldPath(),
                                        text);

            }
        } else if (control == createToCaseRefField.getText()) {
            if (opType != null && opType.getCreate() != null) {
                String text = ((Text) control).getText().trim();
                cmd =
                        SetCommand
                                .create(editingDomain,
                                        opType.getCreate(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getCreateCaseOperationType_ToCaseRefField(),
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

        Composite createCtrlsContainer = toolkit.createComposite(parent);

        gl = new GridLayout();
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        createCtrlsContainer.setLayout(gl);

        Control c =
                createWrappedDescriptionText(toolkit,
                        createCtrlsContainer,
                        Messages.GlobalDataTaskServiceSection_createNewCaseObjFromLocalData_longdesc);
        c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer =
                toolkit.createComposite(createCtrlsContainer);
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

        createLocalDataFieldPath =
                createContentAssistText(innerContainer,
                        toolkit,
                        new LocalDataProposalProvider(false));

        createLocalDataFieldPath.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageControl(createLocalDataFieldPath);
        lb =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_returnCaseRef_label);
        lb.setLayoutData(new GridData());

        createToCaseRefField =
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

        createToCaseRefField.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageTextControl(createToCaseRefField.getText());

        return createCtrlsContainer;
    }

}
