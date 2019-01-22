/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.AbstractEmailSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailComboInputField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailDelimTextField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailTextInputField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF.IDataPP;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email tab's definition section
 * 
 * @author njpatel
 * 
 */
public class EmailDefinitionSection extends AbstractEmailSection implements
        IDataPP {

    // From selection
    private Button fromServerConfig, fromCustomConfig;

    // Addressees and other email fields
    private EmailDelimTextField from, to, cc, bcc, replyTo, headers;

    private EmailTextInputField subject;

    private EmailComboInputField priority;

    private Shell shell;

    public EmailDefinitionSection(EClass eclass) {
        super(eclass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayout gLayout;
        GridData gData;

        shell = parent.getShell();

        ScrolledComposite scrolledComposite =
                toolkit.createScrolledComposite(parent, SWT.H_SCROLL
                        | SWT.V_SCROLL);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);

        Composite container = toolkit.createComposite(scrolledComposite);
        scrolledComposite.setContent(container);
        container.setLayout(new GridLayout());

        // From container
        Composite fromContainer = toolkit.createComposite(container);
        gLayout = new GridLayout(4, false);
        gLayout.marginWidth = 0;
        gLayout.marginHeight = 0;
        fromContainer.setLayout(gLayout);
        fromContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        /*
         * FROM
         */
        Label lblFrom =
                toolkit.createLabel(fromContainer,
                        Messages.EmailDefinitionSection_FromLabel,
                        SWT.WRAP);
        gData = new GridData();
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.verticalSpan = 2;
        lblFrom.setLayoutData(gData);

        fromServerConfig =
                toolkit.createButton(fromContainer,
                        Messages.EmailDefinitionSection_FromServerConfigLabel,
                        SWT.RADIO);
        gData = new GridData();
        gData.horizontalSpan = 3;
        fromServerConfig.setLayoutData(gData);
        fromServerConfig.setSelection(true);
        manageControl(fromServerConfig);

        fromCustomConfig =
                toolkit.createButton(fromContainer,
                        Messages.EmailDefinitionSection_FromCustomConfigLabel,
                        SWT.RADIO);
        fromCustomConfig.setSelection(false);
        manageControl(fromCustomConfig);

        // Add the from control
        from =
                new EmailDelimTextField(toolkit, fromContainer, null,
                        EmailPackage.eINSTANCE.getFromType_Value(), this);
        // Disable the From custom config controls
        from.setEnabled(false);
        manageControl(from.getTextControl());

        // Container for the rest of the parameters
        Composite restContainer = toolkit.createComposite(container);
        gLayout = new GridLayout(3, false);
        gLayout.marginWidth = 0;
        gLayout.marginHeight = 0;
        restContainer.setLayout(gLayout);
        restContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

        // TO
        to =
                new EmailDelimTextField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_ToLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_To(), this);
        manageControl(to.getTextControl());
        // CC
        cc =
                new EmailDelimTextField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_CcLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_Cc(), this);
        manageControl(cc.getTextControl());

        // BCC
        bcc =
                new EmailDelimTextField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_BccLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_Bcc(), this);
        manageControl(bcc.getTextControl());

        // REPLY TO
        replyTo =
                new EmailDelimTextField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_ReplyToLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_ReplyTo(),
                        this);
        manageControl(replyTo.getTextControl());

        // HEADERS
        headers =
                new EmailDelimTextField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_HeadersLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_Headers(),
                        this);
        manageControl(headers.getTextControl());

        priority =
                new EmailComboInputField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_PriorityLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_Priority(),
                        getPriorityPossibleValues(), this);
        manageControl(priority.getComboControl());

        // SUBJECT
        subject =
                new EmailTextInputField(toolkit, restContainer,
                        Messages.EmailDefinitionSection_SubjectLabel,
                        EmailPackage.eINSTANCE.getDefinitionType_Subject(),
                        this);
        manageControl(subject.getTextControl());

        scrolledComposite.setMinSize(container.computeSize(SWT.DEFAULT,
                SWT.DEFAULT));

        return scrolledComposite;
    }

    @Override
    public Command doGetCommand(Object obj) {
        TaskService service = getTaskServiceInput();
        CompoundCommand cmd = null;
        Object value = null;
        EStructuralFeature feature = null;
        Widget widget = (Widget) obj;

        if (widget != null) {
            if (widget.getData() instanceof EmailIF) {
                EmailIF emailIF = (EmailIF) widget.getData();
                feature = (emailIF).getEFeature();
                String string = null;
                // Get the value
                if (widget instanceof Text) {
                    string = emailIF.getText();
                } else if (widget instanceof CCombo) {
                    string = emailIF.getText();
                }
                // Update the value to set in the model
                if (string != null && string.length() > 0) {
                    value = string;
                }

            } else if (widget instanceof Button) {
                Button btn = (Button) widget;

                if ((btn.getStyle() & SWT.RADIO) == SWT.RADIO
                        && btn.getSelection()) {
                    // Check which button was selected and set the
                    // EStructuralFeature accordingly
                    if (btn.equals(fromServerConfig)
                            || btn.equals(fromCustomConfig)) {
                        // Server configuration for the From parameter
                        feature =
                                EmailPackage.eINSTANCE.getDefinitionType_From();
                        FromType fromType =
                                EmailFactory.eINSTANCE.createFromType();
                        if (btn.equals(fromServerConfig)) {
                            // Disable the custom from controls
                            from.setEnabled(false);
                            fromType
                                    .setConfiguration(ConfigurationType.SERVER_LITERAL);
                        } else {
                            // Enable the custom from controls
                            from.setEnabled(true);
                            fromType
                                    .setConfiguration(ConfigurationType.CUSTOM_LITERAL);

                            // If the from text control has value set then set
                            // it here
                            if (from.getText() != null
                                    && from.getText().length() > 0) {
                                fromType.setValue(from.getText());
                            }
                        }

                        value = fromType;
                    }
                }
            }
        }

        if (service != null && feature != null) {
            cmd = new CompoundCommand();
            EditingDomain ed = getEditingDomain();
            EmailType email = EmailExtUtil.getEmailExtension(service);

            // If no email extension found then create it
            if (email == null) {
                email = EmailExtUtil.createEmailExtension(ed, cmd, service);
            }

            if (email != null) {
                DefinitionType definition = email.getDefinition();

                // If no definition has been defined then create it
                if (definition == null) {
                    definition = EmailExtUtil.createDefinition(ed, cmd, email);
                }

                if (definition != null) {
                    // Append the set command for the changed
                    // feature
                    if (feature.eContainer() == EmailPackage.eINSTANCE
                            .getDefinitionType()) {
                        cmd.append(SetCommand
                                .create(ed,
                                        definition,
                                        feature,
                                        (value != null ? value
                                                : SetCommand.UNSET_VALUE)));
                    } else if (feature.eContainer() == EmailPackage.eINSTANCE
                            .getFromType()) {
                        FromType from = definition.getFrom();

                        // If the from hasn't been created then do so
                        if (from == null) {
                            from = EmailExtUtil.createFrom(ed, cmd, definition);
                        }

                        if (from != null) {
                            cmd.append(SetCommand.create(ed,
                                    from,
                                    feature,
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
        boolean clearDefinition = true;

        if (service != null) {
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                DefinitionType definition = email.getDefinition();
                if (definition != null) {
                    clearDefinition = false;

                    // FROM
                    FromType fromValue = definition.getFrom();
                    // Assume server configuration for From
                    ConfigurationType configType =
                            ConfigurationType.SERVER_LITERAL;

                    if (fromValue != null) {
                        configType = fromValue.getConfiguration();
                    }

                    // Update the from radio controls
                    fromServerConfig
                            .setSelection(configType.getValue() == ConfigurationType.SERVER);
                    fromCustomConfig
                            .setSelection(configType.getValue() == ConfigurationType.CUSTOM);
                    from.setText(fromValue.getValue());
                    from
                            .setEnabled(configType.getValue() == ConfigurationType.CUSTOM);

                    // Update all values
                    to.setText(definition.getTo());
                    cc.setText(definition.getCc());
                    bcc.setText(definition.getBcc());
                    replyTo.setText(definition.getReplyTo());
                    headers.setText(definition.getHeaders());
                    priority.setText(definition.getPriority());
                    subject.setText(definition.getSubject());

                }
            }
        }

        if (clearDefinition) {
            // Clear all values
            to.setText(""); //$NON-NLS-1$
            cc.setText(""); //$NON-NLS-1$
            bcc.setText(""); //$NON-NLS-1$
            replyTo.setText(""); //$NON-NLS-1$
            headers.setText(""); //$NON-NLS-1$
            priority.setText(""); //$NON-NLS-1$
            subject.setText(""); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.nativeservices.properties.controls.EmailInputField
     * .IDataPickerProvider#getDataPicker()
     */
    public DataFilterPicker getDataPicker(boolean multi) {
        DataFilterPicker dataPicker = null;
        Activity activity = getActivityInput();
        if (activity != null) {
            dataPicker =
                    new DataFilterPicker(shell,
                            DataPickerType.DATAFIELD_FORMALPARAMETER, false);
            dataPicker.setScope(activity);
        }
        return dataPicker;
    }

    private Map<String, String> getPriorityPossibleValues() {
        Map<String, String> priorityPossibelValues =
                new HashMap<String, String>();
        priorityPossibelValues.put("Normal", //$NON-NLS-1$
                Messages.EmailDefinitionSection_NormalPriority);
        priorityPossibelValues.put("High", //$NON-NLS-1$
                Messages.EmailDefinitionSection_HighPriority);
        priorityPossibelValues.put("Low", //$NON-NLS-1$
                Messages.EmailDefinitionSection_LowPriority);
        return priorityPossibelValues;
    }
}
