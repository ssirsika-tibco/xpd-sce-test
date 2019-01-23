/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * CaseOrBizServPrivilegesConfigurationTable provides implementation for
 * add/remove commands on a Privilege Configuration table.
 * 
 * @author bharge
 * @since 31 Jul 2014
 */
public class CaseOrBizServPrivilegesConfigurationTable extends
        AbstractPrivilegeConfigurationTable {

    private EditingDomain editingDomain;

    /**
     * @param parent
     * @param toolkit
     * @param columnLabel
     * @param viewerInput
     */
    public CaseOrBizServPrivilegesConfigurationTable(Composite parent,
            XpdToolkit toolkit, EditingDomain editingDomain, String columnLabel) {

        super(parent, toolkit, editingDomain, columnLabel);
        this.editingDomain = editingDomain;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigurationTable#getRequiredAccessPrivileges()
     * 
     * @return
     */
    @Override
    protected RequiredAccessPrivileges getRequiredAccessPrivileges() {

        Object input = this.getViewer().getInput();
        if (input instanceof Process) {

            Process process = (Process) input;
            RequiredAccessPrivileges requiredAccessPrivileges =
                    (RequiredAccessPrivileges) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RequiredAccessPrivileges());
            return requiredAccessPrivileges;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigurationTable#getRemovePrivilegesContainerCmd(java.lang.Object)
     * 
     * @return
     */
    @Override
    protected Command getRemovePrivilegesContainerCmd() {

        Object input = this.getViewer().getInput();
        if (input instanceof Process) {

            CompoundCommand cmd = new CompoundCommand();
            Process process = (Process) input;
            RequiredAccessPrivileges requiredAccessPrivileges =
                    (RequiredAccessPrivileges) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RequiredAccessPrivileges());

            if (null != requiredAccessPrivileges) {

                cmd.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(editingDomain,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RequiredAccessPrivileges(),
                                requiredAccessPrivileges));

                return cmd;
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigurationTable#getCreatePrivilegesContainerCmd(com.tibco.xpd.xpdl2.ExternalReference,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param extReference
     * @param cmd
     * @return
     */
    @Override
    protected RequiredAccessPrivileges getCreatePrivilegesContainerCmd(
            CompoundCommand cmd) {

        Object input = this.getViewer().getInput();
        if (input instanceof Process) {

            Process process = (Process) input;

            RequiredAccessPrivileges requiredAccessPrivileges =
                    (RequiredAccessPrivileges) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RequiredAccessPrivileges());

            if (null == requiredAccessPrivileges) {

                requiredAccessPrivileges =
                        XpdExtensionFactory.eINSTANCE
                                .createRequiredAccessPrivileges();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RequiredAccessPrivileges(),
                                requiredAccessPrivileges));
            }

            return requiredAccessPrivileges;
        }

        return null;
    }

}
