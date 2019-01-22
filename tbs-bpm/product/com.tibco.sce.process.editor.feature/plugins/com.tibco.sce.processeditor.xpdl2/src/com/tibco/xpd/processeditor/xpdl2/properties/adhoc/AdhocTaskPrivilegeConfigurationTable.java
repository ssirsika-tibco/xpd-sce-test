/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.general.AbstractPrivilegeConfigurationTable;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Organization Privilege configuration Table for Adhoc tasks [User task /
 * re-usable sub process having AdhocConfigurationType set]
 * 
 * 
 * @author kthombar
 * @since 22-Aug-2014
 */
public class AdhocTaskPrivilegeConfigurationTable extends
        AbstractPrivilegeConfigurationTable {

    private EditingDomain editingDomain;

    /**
     * @param parent
     * @param toolkit
     * @param columnLabel
     * @param viewerInput
     */
    public AdhocTaskPrivilegeConfigurationTable(Composite parent,
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
        if (input instanceof Activity) {

            Activity activity = (Activity) input;
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (otherElement instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) otherElement;

                return adhocConfigType.getRequiredAccessPrivileges();
            }
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
        if (input instanceof Activity) {

            CompoundCommand cmd = new CompoundCommand();
            Activity activity = (Activity) input;

            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (otherElement instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) otherElement;

                RequiredAccessPrivileges requiredAccessPrivileges =
                        adhocConfigType.getRequiredAccessPrivileges();

                if (requiredAccessPrivileges != null) {

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_RequiredAccessPrivileges(),
                                    SetCommand.UNSET_VALUE));
                }
            }

            return cmd;
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
        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (otherElement instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) otherElement;

                RequiredAccessPrivileges requiredAccessPrivileges =
                        adhocConfigType.getRequiredAccessPrivileges();

                if (null == requiredAccessPrivileges) {

                    requiredAccessPrivileges =
                            XpdExtensionFactory.eINSTANCE
                                    .createRequiredAccessPrivileges();

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_RequiredAccessPrivileges(),
                                    requiredAccessPrivileges));

                }
                return requiredAccessPrivileges;
            }
        }

        return null;
    }

}
