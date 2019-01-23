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
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;

/**
 * Section for SOAP over HTTP outbound (service consumer) binding type.
 * 
 * @author Jan Arciuchiewicz
 */
public class WsSoapHttpOutboundBindingSection extends WsBindingSection {

    public WsSoapHttpOutboundBindingSection(EClass eClass) {
        super(eClass);
    }

    private Text instanceText;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapHttpOutboundBindingSection_BindingDetails_label));
        // createNameControl(parent, toolkit);
        createSoapHttpOutboundControls(parent, toolkit);
        return parent;
    }

    protected void createSoapHttpOutboundControls(Composite parent,
            XpdFormToolkit toolkit) {
        indentedGridData
                .applyTo(toolkit
                        .createLabel(parent,
                                Messages.WsSoapHttpOutboundBindingSection_HttpClientInstance_label));
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

        if (getInput() instanceof WsSoapHttpOutboundBinding) {
            final WsSoapHttpOutboundBinding binding =
                    (WsSoapHttpOutboundBinding) getInput();
            if (obj == instanceText && instanceText != null) {
                final String instanceName = instanceText.getText();
                if (!instanceName.equals(binding.getHttpClientInstanceName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setHttpClientInstanceName(instanceName);
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
        if (getInput() instanceof WsSoapHttpOutboundBinding) {
            final WsSoapHttpOutboundBinding binding =
                    (WsSoapHttpOutboundBinding) getInput();

            instanceText.setText(nullSafe(binding.getHttpClientInstanceName()));
        }
    }
}
