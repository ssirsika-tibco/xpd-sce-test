/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.internal.Messages;
import com.tibco.xpd.validation.xpdl2.resolutions.EnterValueDialog;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set the name of an activity.
 * 
 * @author aallway
 * @since 3.2
 */
public class SetActivityNameResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            EnterValueDialog nameDialog =
                    new EnterValueDialog(
                            Display.getDefault().getActiveShell(),
                            Messages.SetActivityNameResolution_EnterActName_title,
                            Messages.SetActivityNameResolution_Name_label,
                            Xpdl2ModelUtil.getDisplayNameOrName(activity)) {
                        @Override
                        protected String validateValue(String newValue) {
                            if (newValue == null || newValue == "") { //$NON-NLS-1$
                                return Messages.SetActivityNameResolution_MustEnterValidName_longdesc;
                            } else {
                                if (newValue.trim().length() != newValue
                                        .length()) {
                                    return Messages.SetActivityNameResolution_MustNotHaveLeadTrailSpaces_longdesc;
                                }
                            }

                            return super.validateValue(newValue);
                        }
                    };

            if (nameDialog.open() == EnterValueDialog.OK && nameDialog.getValue() != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.SetActivityNameResolution_SetActivityName_menu);
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                nameDialog.getValue()));
                
                return cmd;
            }
        }

        return null;
    }

}
