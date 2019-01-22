/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;

import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.ITaskImplementationInitializer;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * The email implementation section in the task general tab.
 * 
 * @author njpatel
 * 
 */
public class EmailServiceSection extends AbstractEmailSection implements
        ITaskImplementationInitializer {

    private Text to, subject, body;

    /**
     * Email implementation section in the task general tab.
     */
    public EmailServiceSection() {
        super(EmailPackage.eINSTANCE.getEmailType());

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        final int cols = 2;
        GridData gData;
        Composite container = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(cols, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);

        // To
        createLabel(container, toolkit, Messages.EmailServiceSection_To);
        to =
                toolkit.createText(container,
                        null,
                        EmailPackage.eINSTANCE.getDefinitionType_To());
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        to.setLayoutData(gData);
        manageControl(to);

        // Subject
        createLabel(container, toolkit, Messages.EmailServiceSection_Subject);
        subject =
                toolkit.createText(container,
                        null,
                        EmailPackage.eINSTANCE.getDefinitionType_Subject());
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        subject.setLayoutData(gData);
        manageControl(subject);

        // Body
        createLabel(container, toolkit, Messages.EmailServiceSection_Body);
        body = toolkit.createText(container, "", SWT.WRAP | SWT.MULTI //$NON-NLS-1$
                | SWT.V_SCROLL);
        body.setData(XpdFormToolkit.FEATURE_DATA,
                EmailPackage.eINSTANCE.getEmailType_Body());
        gData = new GridData(GridData.FILL_BOTH);
        gData.widthHint = TEXT_WIDTH_HINT;
        gData.heightHint = 30;
        body.setLayoutData(gData);
        manageControl(body);

        // Create blank label to line the hyperlink below under the text
        // controls
        createLabel(container, toolkit, ""); //$NON-NLS-1$

        Hyperlink linkToMoreDetails =
                toolkit.createHyperlink(container,
                        Messages.EmailServiceSection_MoreDetailsLink,
                        SWT.NONE);
        linkToMoreDetails.addHyperlinkListener(new IHyperlinkListener() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
                Bundle b = NativeServicesActivator.getDefault().getBundle();
                String tabName =
                        Platform.getResourceString(b,
                                NativeServicesConsts.EMAIL_SERVICE_NAME);
                showTab(tabName);

            }

            @Override
            public void linkEntered(HyperlinkEvent e) {
                // Do nothing
            }

            @Override
            public void linkExited(HyperlinkEvent e) {
                // Do nothing
            }

        });

        return container;
    }

    @Override
    public Command doGetCommand(Object obj) {

        CompoundCommand cmd = null;
        EStructuralFeature feature = null;
        String value = null;

        if (obj instanceof Text) {
            Text text = (Text) obj;
            // Get the feature being set
            feature =
                    (EStructuralFeature) text
                            .getData(XpdFormToolkit.FEATURE_DATA);
            // Get the value being set for the feature
            if (text.getText().length() > 0) {
                value = text.getText();
            }
        }

        if (feature != null) {
            EditingDomain ed = getEditingDomain();

            cmd = new CompoundCommand();
            // Get the email extension
            TaskService service = getTaskServiceInput();

            if (service != null) {
                EmailType email = EmailExtUtil.getEmailExtension(service);

                // If no email extension found then create it
                if (email == null) {
                    email = EmailExtUtil.createEmailExtension(ed, cmd, service);
                }

                if (email != null) {
                    Command setCmd = null;

                    // If updating body then this is stored under the email
                    // object
                    if (feature == EmailPackage.eINSTANCE.getEmailType_Body()) {
                        setCmd =
                                SetCommand.create(ed,
                                        email,
                                        feature,
                                        value != null ? value
                                                : SetCommand.UNSET_VALUE);
                    } else {
                        // Setting the definition values so get the definition.
                        // If one is not available then create it
                        DefinitionType definition = email.getDefinition();

                        if (definition == null) {
                            definition =
                                    EmailExtUtil.createDefinition(ed,
                                            cmd,
                                            email);
                        }

                        if (definition != null) {
                            if (value != null) {
                                setCmd =
                                        SetCommand.create(ed,
                                                definition,
                                                feature,
                                                value);
                            } else {
                                setCmd =
                                        SetCommand.create(ed,
                                                definition,
                                                feature,
                                                SetCommand.UNSET_VALUE);
                            }
                        }
                    }

                    // If there is a set command then add it to the compound
                    // command
                    if (setCmd != null) {
                        cmd.append(setCmd);
                    }
                }
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        TaskService service = getTaskServiceInput();
        boolean clearValues = true;

        if (service != null) {
            // Get the email extension
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                // Get definition part
                DefinitionType definition = email.getDefinition();

                if (definition != null) {
                    // Don't clear the values in the gui as we have values in
                    // the model
                    clearValues = false;

                    /*
                     * XPD-6424 - If we reach here, we might/might not have data
                     * of all the text fields in the model.So just populate the
                     * text fields with whatever data is in the model. Not doing
                     * that will lead to refresh problem.
                     */
                    // To
                    updateText(to, definition.getTo());

                    // Subject
                    updateText(subject, definition.getSubject());

                    // Body
                    updateText(body, email.getBody());

                }
            }
        }

        // If values are not defined then clear the gui
        if (clearValues) {
            updateText(to, null);
            updateText(subject, null);
            updateText(body, null);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.ITaskImplementationInitializer#getInitialStructureCommand(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    public Command getInitialStructureCommand(Activity activity) {
        TaskService service = null;
        CompoundCommand cmd = null;
        if (activity.getImplementation() instanceof Task) {

            service = ((Task) activity.getImplementation()).getTaskService();
            cmd = new CompoundCommand();
            EditingDomain ed = getEditingDomain();
            EmailType email = EmailExtUtil.getEmailExtension(service);
            // If no email extension found then create it
            if (email == null) {
                email = EmailExtUtil.createEmailExtension(ed, cmd, service);
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.ITaskImplementationInitializer#getCleanupCommand(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    public Command getCleanupCommand(Activity activity) {

        return null;
    }

}
