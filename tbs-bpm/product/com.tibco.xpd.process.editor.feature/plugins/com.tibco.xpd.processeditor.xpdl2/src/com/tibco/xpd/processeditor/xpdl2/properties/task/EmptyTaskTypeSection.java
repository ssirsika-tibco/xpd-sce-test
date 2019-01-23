/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * EmptySection
 * 
 * @author aallway
 */
public class EmptyTaskTypeSection extends AbstractFilteredTransactionalSection
        implements IPluginContribution {

    private String instrumentationPrefixName;

    public EmptyTaskTypeSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public EmptyTaskTypeSection(String instrumentationPrefixName) {
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
