/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class InterfaceEventAssociatedParameterSection extends
        AssociatedParameterSection {

    public InterfaceEventAssociatedParameterSection() {
        super(XpdExtensionPackage.eINSTANCE.getAssociatedParametersContainer());
    }

    @Override
    protected void doRefresh() {
        super.doRefresh();

        if (assocParamTable != null) {
            Color col = ColorConstants.black;
            String labelText =
                    Messages.AssociatedParamHandler_SelectAssocData_longdesc;

            boolean isMessage = false;
            if (getInput() instanceof InterfaceMethod) {
                InterfaceMethod sm = (InterfaceMethod) getInput();

                if (TriggerType.MESSAGE_LITERAL.equals(sm.getTrigger())) {
                    isMessage = true;
                }
            }

            if (isMessage) {
                col = ColorConstants.blue;
                labelText =
                        Messages.InterfaceEventAssociatedParameterSection_SetInputOutputParametersForGenreatedService_description;
            }

            getTableLabel().setForeground(col);
            setTableLabel(labelText);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#isDisableImplicitAssociation()
     * 
     * @return
     */
    @Override
    protected boolean isDisableImplicitAssociation() {
        if (getInput() instanceof AssociatedParametersContainer) {
            return ProcessInterfaceUtil
                    .isImplicitAssociationDisabled((AssociatedParametersContainer) getInput());
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#getSetDisableImplicitAssociationCommand(java.lang.Boolean)
     * 
     * @param disableImplicitAssociation
     * @return
     */
    @Override
    protected Command getSetDisableImplicitAssociationCommand(
            Boolean disableImplicitAssociation) {
        if (getInput() instanceof AssociatedParametersContainer) {
            AssociatedParametersContainer interfaceMethod =
                    (AssociatedParametersContainer) getInput();

            CompoundCommand cmd =
                    new CompoundCommand(
                            disableImplicitAssociation ? Messages.ActivityAssociatedParameterSection_DisableImplicitAssociation_menu
                                    : Messages.ActivityAssociatedParameterSection_EnableImplicitAssociation_menu);

            cmd.append(SetCommand
                    .create(getEditingDomain(),
                            interfaceMethod,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParametersContainer_DisableImplicitAssociation(),
                            disableImplicitAssociation));

            if (disableImplicitAssociation) {
                EList<AssociatedParameter> associatedParameters =
                        interfaceMethod.getAssociatedParameters();
                if (associatedParameters.size() > 0) {
                    cmd.append(RemoveCommand.create(getEditingDomain(),
                            associatedParameters));
                }
            }

            return cmd;

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#showDisableImplicitButton()
     * 
     * @return
     */
    @Override
    protected boolean showDisableImplicitButton() {
        return true;
    }

}
