/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.EmailAttachmentsSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.EmailBodySection;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.EmailDefinitionSection;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * E-Mail tab section
 * 
 * @author njpatel
 * 
 */
public class EmailTabSection extends AbstractFilteredTransactionalSection
        implements IPluginContribution {

    private CTabFolder tabFolder;

    private Set<AbstractXpdSection> subSections =
            new HashSet<AbstractXpdSection>();

    // Definition section
    private EmailDefinitionSection definition;

    // Body section
    private EmailBodySection body;

    // Attachments section
    private EmailAttachmentsSection attachments;

    // SMTP configuration section
    // private EmailSmtpConfigSection smtp;

    // Error handling section
    // private EmailErrorHandlingSection errorHandling;

    /**
     * E-Mail tab section
     */
    public EmailTabSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

        // Create all sub-sections
        definition = new EmailDefinitionSection(this.eClass);
        subSections.add(definition);
        body = new EmailBodySection(this.eClass);
        subSections.add(body);
        attachments = new EmailAttachmentsSection(this.eClass);
        subSections.add(attachments);
        // XPD-154 : remove smtp and error handling sections
        // smtp = new EmailSmtpConfigSection(this.eClass);
        // subSections.add(smtp);
        // errorHandling = new EmailErrorHandlingSection(this.eClass);
        // subSections.add(errorHandling);
        // XPD-154
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        // Update all subsections
        if (subSections != null) {
            for (AbstractXpdSection section : subSections) {
                section.setInput(items);
            }
        }
    }

    @Override
    public boolean select(Object toTest) {
        boolean ret = super.select(toTest);

        if (ret) {
            if (toTest instanceof TaskEditPart) {
                TaskEditPart editPart = (TaskEditPart) toTest;
                TaskAdapter taskAdapter =
                        (TaskAdapter) editPart.getModelAdapter();

                if (taskAdapter != null) {
                    String extensionName =
                            taskAdapter.getTaskImplementationExtensionId();

                    ret =
                            (extensionName != null && extensionName
                                    .equals(NativeServicesConsts.EMAIL_SERVICE_ID));

                } else {
                    ret = false;
                }
            } else {
                ret = false;
            }
        }

        return ret;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        CTabItem tabItem;

        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        // Create tabs
        tabFolder = toolkit.createTabFolder(root, SWT.FLAT | SWT.MULTI);
        tabFolder.setBorderVisible(true);

        GridData gData = new GridData(GridData.FILL_BOTH);
        gData.widthHint = 10;
        gData.heightHint = 10;
        tabFolder.setLayoutData(gData);

        // Definitions tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.EmailTabSection_DefinitionTab);
        tabItem.setControl(definition.createControls(tabFolder, toolkit));

        tabFolder.setSelection(tabItem);

        // Message tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.EmailTabSection_BodyTab);
        tabItem.setControl(body.createControls(tabFolder, toolkit));

        // Attachment tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.EmailTabSection_AttachmentsTab);
        tabItem.setControl(attachments.createControls(tabFolder, toolkit));

        // XPD-154 : remove smtp and error handling sections
        // SMTP Configuration tab
        // tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        // tabItem.setText(Messages.EmailTabSection_SMTPConfigTab);
        // tabItem.setControl(smtp.createControls(tabFolder, toolkit));

        // Error handling tab
        // tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        // tabItem.setText(Messages.EmailTabSection_ErrorHandlingTab);
        // tabItem.setControl(errorHandling.createControls(tabFolder, toolkit));
        // XPD-154

        return root;
    }

    @Override
    public Command doGetCommand(Object obj) {
        // Do nothing here as this will be handled by the sub-sections
        return null;
    }

    @Override
    protected void doRefresh() {
        // Refresh all subsections
        if (subSections != null && tabFolder != null && !tabFolder.isDisposed()) {
            for (AbstractXpdSection section : subSections) {
                section.refresh();
            }
        }
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "EmailSection"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    public String getPluginId() {
        return NativeServicesActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getPluginContributon()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

}
