/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;

import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * The Database task service implementation section that allows the user to
 * define the database connection info, including the stored procedure.
 * 
 * @author njpatel
 */
public class DatabaseServiceSection extends AbstractDatabaseSection implements
        IPluginContribution {

    private Hyperlink linkToParameters;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite container = (Composite) super.doCreateControls(parent, toolkit);

        // Hyperlink to parameter section
        createLabel(toolkit, container, ""); //$NON-NLS-1$
        linkToParameters =
                toolkit.createHyperlink(container,
                        Messages.DatabaseServiceSection_ProcedureParameterLink,
                        SWT.NONE,
                        "procedureParameterLink"); //$NON-NLS-1$
        linkToParameters.setHref(linkToParameters);
        manageControl(linkToParameters);

        return container;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        if (obj == linkToParameters) {
            // Show the database tab
            Bundle b = NativeServicesActivator.getDefault().getBundle();
            String tabName =
                    Platform.getResourceString(b,
                            NativeServicesConsts.DB_SERVICE_NAME);
            showTab(tabName);
        } else {
            cmd = super.doGetCommand(obj);
        }

        return cmd;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "Database"; //$NON-NLS-1$
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
