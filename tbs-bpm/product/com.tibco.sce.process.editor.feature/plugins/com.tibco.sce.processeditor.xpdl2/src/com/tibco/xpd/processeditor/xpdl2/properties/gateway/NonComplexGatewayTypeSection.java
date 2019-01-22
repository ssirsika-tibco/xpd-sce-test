/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.gateway;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * GatewayTypeNoneSection
 * 
 * @author kupadhya
 */
public class NonComplexGatewayTypeSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    private String instrumentationPrefixName;

    public NonComplexGatewayTypeSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public NonComplexGatewayTypeSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    protected void doRefresh() {
        // Nothing to do
    }

    public String getLocalId() {
        return "analyst.noneSection"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
