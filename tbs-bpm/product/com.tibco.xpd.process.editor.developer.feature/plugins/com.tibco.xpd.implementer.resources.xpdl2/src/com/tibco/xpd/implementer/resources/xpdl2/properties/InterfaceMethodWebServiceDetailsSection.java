/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class InterfaceMethodWebServiceDetailsSection extends
        WebServiceDetailsSection {

    static final String WSDL_FILE_EXT = ".wsdl"; //$NON-NLS-1$

    private InterfaceMethod methodInput = null;

    @Override
    protected boolean isBWImplementation() {
        return false;
    }

    @Override
    public void setInput(Collection<?> items) {
        // We are only really borroing the control layout from the
        // WebServiceDetaislSection.
        super.setInput(items);

        methodInput = null;

        if (items != null && items.size() > 0) {
            Object input = items.iterator().next();
            if (input instanceof InterfaceMethod) {
                methodInput = (InterfaceMethod) input;
            }
        }
    }

    @Override
    public Command doGetCommand(Object obj) {
        if (obj == transportNameCombo) {
            String transportName =
                    (String) transportNameCombo.getData(transportNameCombo
                            .getText());
            String currentTransportUri = getPortTypeOperation().getTransport();
            if (currentTransportUri == null
                    || !currentTransportUri.equals(transportName)) {
                Command command =
                        SetCommand.create(getEditingDomain(),
                                getPortTypeOperation(),
                                XpdExtensionPackage.eINSTANCE
                                        .getPortTypeOperation_Transport(),
                                transportName);
                return command;
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        if (getPortTypeOperation() != null) {
            updateText(portTypeNameText, getPortTypeOperation()
                    .getPortTypeName());
            updateText(operationNameText, getPortTypeOperation()
                    .getOperationName());
            updateText(wsdlLocationText, getWsdlFileName());

            /*
             * XPD-3669: While fixing XPD-3547 it has been decided that
             * transport type for generated services (and abstract operations
             * from user defined wsdls) must be service virtualisation.
             */
            String transport = SERVICE_VIRTUALIZATION_LABEL;
            transportNameCombo.setText(transport);
        } else {
            updateText(portTypeNameText, ""); //$NON-NLS-1$
            updateText(operationNameText, ""); //$NON-NLS-1$
            updateText(wsdlLocationText, ""); //$NON-NLS-1$
            transportNameCombo.setText(""); //$NON-NLS-1$
            transportNameCombo.setEnabled(false);
        }
    }

    private String getWsdlFileName() {
        String fileName = null;
        if (getMethodInput() != null) {
            IFile file = WorkingCopyUtil.getFile(getMethodInput());
            if (file != null) {
                fileName = file.getName();
                if (fileName.indexOf('.') != -1) {
                    return fileName.substring(0, fileName.indexOf('.'))
                            + WSDL_FILE_EXT;

                }
            }
        }
        return fileName;
    }

    private InterfaceMethod getMethodInput() {
        return methodInput;
    }

    private PortTypeOperation getPortTypeOperation() {
        if (getMethodInput() != null) {
            TriggerResultMessage triggerResultMessage =
                    getMethodInput().getTriggerResultMessage();
            if (triggerResultMessage != null) {
                return (PortTypeOperation) Xpdl2ModelUtil
                        .getOtherElement(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
            }
        }
        return null;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Control control = super.doCreateControls(parent, toolkit);

        selectButton.setEnabled(false);
        importWsdlButton.setEnabled(false);
        clearButton.setEnabled(false);
        aliasBrowse.setEnabled(false);
        clearAlias.setEnabled(false);
        localWsdl.setSelection(true);
        localWsdl.setEnabled(false);
        remoteWsdl.setSelection(false);
        remoteWsdl.setEnabled(false);
        wsdlLocationText.setEditable(false);
        aliasText.setEditable(false);
        securityProfileText.setEditable(false);
        transportNameCombo.setEnabled(false);
        return control;
    }
}