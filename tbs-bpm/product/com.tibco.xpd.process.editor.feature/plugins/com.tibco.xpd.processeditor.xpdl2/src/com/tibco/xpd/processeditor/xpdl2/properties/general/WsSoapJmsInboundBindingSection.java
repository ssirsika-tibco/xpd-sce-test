/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;

/**
 * 
 * 
 * @author bharge
 * @since 12 Oct 2011
 */
public class WsSoapJmsInboundBindingSection extends WsSoapBindingSection {

    protected static final GridDataFactory indentedGridData = GridDataFactory
            .swtDefaults().indent(5, 0);

    private Text inboundConnFactConfigurationText;

    private Text inboundDestConfigText;

    private Text outboundConnFactoryText;

    /**
     * @param eClass
     */
    public WsSoapJmsInboundBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsSoapBindingSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapHttpInboundBindingSection_BindingDetails_label));
        createNameControl(parent, toolkit);
        GridDataFactory span =
                GridDataFactory.fillDefaults().span(2, 1).grab(true, false);
        createSoapControls(parent, toolkit);
        createSoapJmsInboundControls(parent, toolkit);
        return parent;
    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createSoapJmsInboundControls(Composite parent,
            XpdFormToolkit toolkit) {

        createInboundSettings(parent, toolkit);
        createOutboundSettings(parent, toolkit);

    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createOutboundSettings(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapJmsInboundBindingSection_Outbound_label));

        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapJmsInboundBindingSection_ConnFactory_label));
        outboundConnFactoryText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(outboundConnFactoryText);
        manageControlUpdateOnDeactivate(outboundConnFactoryText);

    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createInboundSettings(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapJmsInboundBindingSection_Inbound_label));

        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapJmsInboundBindingSection_ConnFactoryConfiguration_label));
        inboundConnFactConfigurationText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(inboundConnFactConfigurationText);
        manageControlUpdateOnDeactivate(inboundConnFactConfigurationText);

        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapJmsInboundBindingSection_DestinationConfiguration_label));
        inboundDestConfigText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(inboundDestConfigText);
        manageControlUpdateOnDeactivate(inboundDestConfigText);

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsSoapBindingSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        Command cmd = super.doGetCommand(obj);
        if (cmd != null) {
            return cmd;
        }

        if (getInput() instanceof WsSoapJmsInboundBinding) {
            final WsSoapJmsInboundBinding jmsBinding =
                    (WsSoapJmsInboundBinding) getInput();

            if (obj == inboundConnFactConfigurationText) {
                final String text = inboundConnFactConfigurationText.getText();
                if (null != text && text.length() > 0) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            jmsBinding
                                    .setInboundConnectionFactoryConfiguration(text);
                        }
                    };
                }
            }

            if (obj == inboundDestConfigText) {
                final String text = inboundDestConfigText.getText();
                if (null != text && text.length() > 0) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            jmsBinding.setInboundDestination(text);
                        }
                    };
                }
            }

            if (obj == outboundConnFactoryText) {
                final String text = outboundConnFactoryText.getText();
                if (null != text && text.length() > 0) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            jmsBinding.setOutboundConnectionFactory(text);
                        }
                    };
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsSoapBindingSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (getInput() instanceof WsSoapJmsInboundBinding) {
            super.doRefresh();
            final WsSoapJmsInboundBinding jmsBinding =
                    (WsSoapJmsInboundBinding) getInput();

            inboundConnFactConfigurationText.setText(jmsBinding
                    .getInboundConnectionFactoryConfiguration());
            inboundDestConfigText.setText(jmsBinding.getInboundDestination());
            outboundConnFactoryText.setText(jmsBinding
                    .getOutboundConnectionFactory());
        }
    }

}
