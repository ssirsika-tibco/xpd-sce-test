/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.validation.bpmn.rules.PageflowUserTaskRule;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to remove the data with name given in marker (by
 * {@link PageflowUserTaskRule}) from the user task's interface
 * 
 * @author aallway
 * @since 3.2
 */
public class RemovePageflowUserTaskAssocParam extends
        AbstractUserTaskPageflowParamSynchResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity userTaskActivity = (Activity) target;

            Process pageflowProcess = getPageflowProcessFromMarker(marker);
            String paramName = getPageflowParamNameFromMarker(marker);
            
            if (pageflowProcess != null) {
                AssociatedParameter assocParam =
                        getAssociatedParameter(userTaskActivity, paramName);

                if (assocParam != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_menu);

                    cmd.append(RemoveCommand.create(editingDomain, assocParam));

                    return cmd;

                } else {
                    MessageDialog
                            .openWarning(Display.getDefault().getActiveShell(),
                                    Messages.RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_title,
                                    Messages.RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_longdesc);
                }
            }
        }

        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String dataName = "???"; //$NON-NLS-1$

        FormalParameter param = getPageflowParameterFromMarker(marker);
        if (param != null) {
            dataName = Xpdl2ModelUtil.getDisplayNameOrName(param);
        } else {
            String dn = getPageflowParamNameFromMarker(marker);
            if (dn != null) {
                dataName = dn;
            }
        }

        return String.format(propertiesLabel, dataName);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        String dataName = "???"; //$NON-NLS-1$

        FormalParameter param = getPageflowParameterFromMarker(marker);
        if (param != null) {
            dataName = Xpdl2ModelUtil.getDisplayNameOrName(param);
        } else {
            String dn = getPageflowParamNameFromMarker(marker);
            if (dn != null) {
                dataName = dn;
            }
        }

        return String.format(propertiesDescription, dataName);
    }
}
