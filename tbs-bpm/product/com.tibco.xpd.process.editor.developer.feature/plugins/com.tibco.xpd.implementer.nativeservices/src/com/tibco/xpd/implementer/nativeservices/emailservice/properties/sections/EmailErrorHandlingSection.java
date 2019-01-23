/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.AbstractEmailSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailROnlyField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF.IDataPP;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email tab's error handling section
 * 
 * @author njpatel
 * 
 */
public class EmailErrorHandlingSection extends AbstractEmailSection implements
        IDataPP {

    // Status codes
    private EmailROnlyField returnStatusCode, returnStatusMessage;

    private Shell shell;

    public EmailErrorHandlingSection(EClass eclass) {
        super(eclass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        shell = parent.getShell();

        ScrolledComposite scrolledComposite = toolkit.createScrolledComposite(
                parent, SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);

        Composite container = toolkit.createComposite(scrolledComposite);
        scrolledComposite.setContent(container);
        container.setLayout(new GridLayout(4, false));

        Label msg = toolkit.createLabel(container,
                Messages.EmailErrorHandlingSection_ReturnStatusCodeLabel,
                SWT.WRAP);
        GridData gData = new GridData();
        gData.horizontalSpan = 4;
        msg.setLayoutData(gData);

        // Status code
        returnStatusCode = new EmailROnlyField(toolkit, container,
                Messages.EmailErrorHandlingSection_CodeLabel,
                EmailPackage.eINSTANCE.getErrorType_ReturnCode(), this);
        manageControl(returnStatusCode.getTextControl());

        // Status message
        returnStatusMessage = new EmailROnlyField(toolkit, container,
                Messages.EmailErrorHandlingSection_MessageLabel,
                EmailPackage.eINSTANCE.getErrorType_ReturnMessage(), this);
        manageControl(returnStatusMessage.getTextControl());

        scrolledComposite.setMinSize(container.computeSize(SWT.DEFAULT,
                SWT.DEFAULT));

        return scrolledComposite;
    }

    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;
        TaskService service = getTaskServiceInput();

        if (service != null) {
            EditingDomain ed = getEditingDomain();
            EmailType email = EmailExtUtil.getEmailExtension(service);
            cmd = new CompoundCommand();

            // If email extension doesn't exist then create it
            if (email == null) {
                email = EmailExtUtil.createEmailExtension(ed, cmd, service);
            }

            if (email != null) {
                ErrorType error = email.getError();

                // If no error type exists then create it
                if (error == null) {
                    error = EmailExtUtil.createError(ed, cmd, email);
                }

                if (error != null) {
                    // Get the feature to change
                    Widget widget = (Widget) obj;

                    if (widget != null) {
                        EStructuralFeature feature = null;
                        Object value = null;

                        if (widget.getData() instanceof EmailIF) {
                            EmailIF inputField = (EmailIF) widget.getData();

                            feature = inputField.getEFeature();

                            if (inputField.getText() != null
                                    && inputField.getText().length() > 0) {
                                value = inputField.getText();
                            }
                        }

                        if (feature != null) {
                            cmd.append(SetCommand.create(ed, error, feature,
                                    (value != null ? value
                                            : SetCommand.UNSET_VALUE)));
                        }
                    }
                }
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        TaskService service = getTaskServiceInput();
        boolean clearError = true;

        if (service != null) {
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                ErrorType error = email.getError();

                if (error != null) {
                    clearError = false;

                    returnStatusCode.setText(error.getReturnCode());
                    returnStatusMessage.setText(error.getReturnMessage());
                }
            }
        }

        // Clear controls if indicated
        if (clearError) {
            returnStatusCode.setText(""); //$NON-NLS-1$
            returnStatusMessage.setText(""); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.nativeservices.properties.sections.controls
     * .EmailInputField.IDataPickerProvider#getDataPicker()
     */
    public DataFilterPicker getDataPicker(boolean multi) {
        DataFilterPicker dataPicker = null;
        Activity activity = getActivityInput();
        if (activity != null) {
            dataPicker = new DataFilterPicker(shell,
                    DataPickerType.DATAFIELD_FORMALPARAMETER, multi);
            dataPicker.setScope(activity);
        }
        return dataPicker;
    }

}
