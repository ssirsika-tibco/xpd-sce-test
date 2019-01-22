/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;

/**
 * Section for Web Service SOAP JMS outbound Binding.
 * 
 * @author bharge
 * @since 12 Oct 2011
 */
public class WsSoapJmsOutboundBindingSection extends WsBindingSection {

    private Text inboundDestinationText;

    private Text outboundConnFactoryText;

    private Text outboundDestinationText;

    /**
     * @param eClass
     */
    public WsSoapJmsOutboundBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsBindingSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        createSoapJmsOutboundControls(parent, toolkit);
        return parent;
    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createSoapJmsOutboundControls(Composite parent,
            XpdFormToolkit toolkit) {

        createInboundSettings(parent, toolkit);
        createOutboundSettings(parent, toolkit);

    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createOutboundSettings(Composite parent, XpdFormToolkit toolkit) {

        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapJmsOutboundBindingSection_OutboundConnectionFactory_label1));

        outboundConnFactoryText = toolkit.createText(parent, ""); //$NON-NLS-1$

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(outboundConnFactoryText);

        manageControlUpdateOnDeactivate(outboundConnFactoryText);

        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapJmsOutboundBindingSection_OutboundDestination_label1));

        outboundDestinationText = toolkit.createText(parent, ""); //$NON-NLS-1$

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(outboundDestinationText);

        manageControlUpdateOnDeactivate(outboundDestinationText);
    }

    /**
     * @param parent
     * @param toolkit
     */
    private void createInboundSettings(Composite parent, XpdFormToolkit toolkit) {

        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapJmsOutboundBindingSection_InboundDestination_label1));

        inboundDestinationText = toolkit.createText(parent, ""); //$NON-NLS-1$

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(inboundDestinationText);

        manageControlUpdateOnDeactivate(inboundDestinationText);

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsBindingSection#doGetCommand(java.lang.Object)
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

        if (getInput() instanceof WsSoapJmsOutboundBinding) {
            final WsSoapJmsOutboundBinding binding =
                    (WsSoapJmsOutboundBinding) getInput();
            if (obj == inboundDestinationText) {
                final String inboundDestName = inboundDestinationText.getText();
                if (!inboundDestName.equals(binding.getInboundDestination())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setInboundDestination(inboundDestName);
                        }
                    };
                }
            }

            if (obj == outboundConnFactoryText) {
                final String outboundConnFactName =
                        outboundConnFactoryText.getText();
                if (!outboundConnFactName.equals(binding
                        .getOutboundConnectionFactory())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setOutboundConnectionFactory(outboundConnFactName);
                        }
                    };
                }
            }

            if (obj == outboundDestinationText) {
                final String outboundDestName =
                        outboundDestinationText.getText();
                if (!outboundDestName.equals(binding.getOutboundDestination())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setOutboundDestination(outboundDestName);
                        }
                    };
                }
            }

        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.WsBindingSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        if (getInput() instanceof WsSoapJmsOutboundBinding) {

            WsSoapJmsOutboundBinding binding =
                    (WsSoapJmsOutboundBinding) getInput();
            inboundDestinationText.setText(nullSafe(binding
                    .getInboundDestination()));
            outboundConnFactoryText.setText(nullSafe(binding
                    .getOutboundConnectionFactory()));
            outboundDestinationText.setText(nullSafe(binding
                    .getOutboundDestination()));
        }
    }
}
