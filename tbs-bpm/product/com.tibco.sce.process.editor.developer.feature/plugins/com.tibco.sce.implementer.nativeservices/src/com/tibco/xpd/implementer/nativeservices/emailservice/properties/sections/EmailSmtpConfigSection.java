/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.AbstractEmailSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email tab's SMTP configuration section
 * 
 * @author njpatel
 * 
 */
public class EmailSmtpConfigSection extends AbstractEmailSection {

    // Default SMTP port
    private static final String DEFAULT_PORT = Messages.EmailSmtpConfigSection_DefaultPortNumber;

    private static int PORT_TEXT_LIMIT = 6;

    private Button serverConfig, customConfig;

    private Label lblHost, lblPort;

    private Text host, port;

    public EmailSmtpConfigSection(EClass eclass) {
        super(eclass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridData gData;
        final int cols = 2;
        final int indent = 30;

        ScrolledComposite scrolledComposite = toolkit.createScrolledComposite(
                parent, SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);

        Composite container = toolkit.createComposite(scrolledComposite);
        scrolledComposite.setContent(container);
        container.setLayout(new GridLayout(cols, false));

        // Server configuration

        serverConfig = toolkit
                .createButton(container,
                        Messages.EmailSmtpConfigSection_ServerSMTPRadioLabel,
                        SWT.RADIO);
        gData = new GridData();
        gData.horizontalSpan = cols;
        serverConfig.setLayoutData(gData);
        // serverConfig.setSelection(true);
        manageControl(serverConfig);

        // Custom configuration
        customConfig = toolkit
                .createButton(container,
                        Messages.EmailSmtpConfigSection_CustomSMTPRadioLabel,
                        SWT.RADIO);
        gData = new GridData();
        gData.horizontalSpan = cols;
        customConfig.setLayoutData(gData);
        // customConfig.setSelection(false);
        manageControl(customConfig);

        lblHost = toolkit.createLabel(container,
                Messages.EmailSmtpConfigSection_HostLabel, SWT.WRAP);
        gData = new GridData();
        gData.horizontalIndent = indent;
        lblHost.setLayoutData(gData);

        host = toolkit.createText(container, null, EmailPackage.eINSTANCE
                .getSMTPType_Host());
        gData = new GridData();
        gData.widthHint = TEXT_WIDTH_HINT;
        host.setLayoutData(gData);
        manageControl(host);

        lblPort = toolkit.createLabel(container, MessageFormat.format(
                Messages.EmailSmtpConfigSection_PortLabel,
                new Object[] { DEFAULT_PORT }), SWT.WRAP);
        gData = new GridData();
        gData.horizontalIndent = indent;
        lblPort.setLayoutData(gData);

        port = toolkit.createText(container, null, EmailPackage.eINSTANCE
                .getSMTPType_Port());
        port.setTextLimit(PORT_TEXT_LIMIT);
        // Set default port value
        port.setText(DEFAULT_PORT);
        port.addVerifyListener(new VerifyListener() {

            public void verifyText(VerifyEvent e) {
                e.doit = e.text.length() == 0
                        || Character.isDigit(e.text.charAt(0));
            }

        });
        gData = new GridData();
        gData.widthHint = TEXT_WIDTH_HINT;
        port.setLayoutData(gData);
        manageControl(port);

        // Disable the custom SMTP settings controls
        setCustomSMTPSettingsEnabled(false);

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

            // If the email extension doesn't exist then create it
            if (email == null) {
                email = EmailExtUtil.createEmailExtension(ed, cmd, service);
            }

            if (email != null) {
                EStructuralFeature feature = null;
                Object value = null;
                Object owner = null;

                if (obj instanceof Button) {
                    // Only process button if it was selected
                    if (((Button) obj).getSelection()) {
                        // Radio buttons clicked so SMTP configuration type
                        // changed
                        feature = EmailPackage.eINSTANCE.getEmailType_SMTP();
                        SMTPType smtp = EmailFactory.eINSTANCE.createSMTPType();
                        if (serverConfig.equals(obj)) {
                            // Server config set
                            smtp
                                    .setConfiguration(ConfigurationType.SERVER_LITERAL);
                            setCustomSMTPSettingsEnabled(false);
                        } else {
                            // Custom config set
                            smtp
                                    .setConfiguration(ConfigurationType.CUSTOM_LITERAL);

                            // If port or host is set then add it to the model
                            if (port.getText().length() > 0) {
                                smtp.setPort(new BigDecimal(port.getText()));
                            }

                            if (host.getText().length() > 0) {
                                smtp.setHost(host.getText());
                            }

                            setCustomSMTPSettingsEnabled(true);
                        }

                        // Update the value and owner that gets set for the
                        // feature
                        value = smtp;
                        owner = email;
                    }
                } else if (obj instanceof Text) {
                    SMTPType smtp = email.getSMTP();

                    if (smtp != null) {
                        Text text = (Text) obj;

                        // Text controls changed - custom SMTP configuration
                        // changed
                        feature = (EStructuralFeature) text
                                .getData(XpdFormToolkit.FEATURE_DATA);

                        // If the feature is of Decimal type then set the
                        // value
                        // accordingly
                        if (feature.getEType().getInstanceClass().equals(
                                BigDecimal.class)) {
                            if (text.getText().length() > 0)
                                value = new BigDecimal(text.getText());
                            else
                                value = new BigDecimal(DEFAULT_PORT);
                        } else {
                            if (text.getText().length() > 0)
                                value = text.getText();
                        }

                        owner = smtp;
                    }
                }

                // If feature set then update command
                if (feature != null && owner != null) {
                    cmd.append(SetCommand.create(ed, owner, feature,
                            (value != null ? value : SetCommand.UNSET_VALUE)));
                }
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        TaskService service = getTaskServiceInput();
        boolean clearSmtp = true;

        if (service != null) {
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                SMTPType smtp = email.getSMTP();
                clearSmtp = false;

                if (smtp.getConfiguration() == null
                        || smtp.getConfiguration().getValue() == ConfigurationType.SERVER) {
                    // Server selected
                    setCustomSMTPSettingsEnabled(false);
                } else {
                    // Custom selected
                    setCustomSMTPSettingsEnabled(true);

                    // Get the host and port settings
                    updateText(host, smtp.getHost());
                    updateText(port, smtp.getPort().toPlainString());
                }
            }
        }

        // Check if we need to clear controls
        if (clearSmtp) {
            updateText(host, null);
            updateText(port, DEFAULT_PORT);
            setCustomSMTPSettingsEnabled(false);
        }
    }

    /**
     * Enable the custom SMTP settings
     * 
     * @param enabled
     *            Set to <code>true</code> to enable the controls
     */
    private void setCustomSMTPSettingsEnabled(boolean enabled) {
        serverConfig.setSelection(!enabled);
        customConfig.setSelection(enabled);
        lblHost.setEnabled(enabled);
        host.setEnabled(enabled);
        lblPort.setEnabled(enabled);
        port.setEnabled(enabled);
    }

}
