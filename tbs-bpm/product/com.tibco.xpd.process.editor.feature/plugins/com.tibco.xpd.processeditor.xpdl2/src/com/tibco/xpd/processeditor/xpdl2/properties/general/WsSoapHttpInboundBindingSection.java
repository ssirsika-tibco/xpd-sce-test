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
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;

/**
 * Section for SOAP over HTTP inbound (service provider) binding type.
 * 
 * @author Jan Arciuchiewicz
 */
public class WsSoapHttpInboundBindingSection extends WsSoapBindingSection {

    private Text endpointUriPathText;

    private Text instanceText;

    /**
     * @param eClass
     */
    public WsSoapHttpInboundBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        GridDataFactory.swtDefaults().span(2, 1).applyTo(toolkit
                .createLabel(parent, Messages.WsSoapHttpInboundBindingSection_BindingDetails_label));
        createNameControl(parent, toolkit);
        GridDataFactory span =
                GridDataFactory.fillDefaults().span(2, 1).grab(true, false);
        createSoapControls(parent, toolkit);
        createSoapHttpInboundControls(parent, toolkit);
        return parent;
    }

    protected void createSoapHttpInboundControls(Composite parent,
            XpdFormToolkit toolkit) {
        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapHttpInboundBindingSection_EndpointPath_label));
        endpointUriPathText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(endpointUriPathText);
        manageControlUpdateOnDeactivate(endpointUriPathText);

        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapHttpInboundBindingSection_HttpConnectorInstance_label));
        instanceText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false).applyTo(instanceText);
        manageControlUpdateOnDeactivate(instanceText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = super.doGetCommand(obj);
        if (cmd != null) {
            return cmd;
        }

        if (getInput() instanceof WsSoapHttpInboundBinding) {
            final WsSoapHttpInboundBinding binding =
                    (WsSoapHttpInboundBinding) getInput();

            if (obj == endpointUriPathText) {
                final String text = endpointUriPathText.getText();

                if (!text.equals(binding.getHttpConnectorInstanceName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setEndpointUrlPath(text);
                        }
                    };
                }
            }
            if (obj == instanceText) {
                final String instanceName = instanceText.getText();
                if (!instanceName
                        .equals(binding.getHttpConnectorInstanceName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setHttpConnectorInstanceName(instanceName);
                        }
                    };
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        if (getInput() instanceof WsSoapHttpInboundBinding) {
            final WsSoapHttpInboundBinding binding =
                    (WsSoapHttpInboundBinding) getInput();

            endpointUriPathText.setText(nullSafe(binding.getEndpointUrlPath()));
            instanceText.setText(nullSafe(binding
                    .getHttpConnectorInstanceName()));
        }
    }
}
