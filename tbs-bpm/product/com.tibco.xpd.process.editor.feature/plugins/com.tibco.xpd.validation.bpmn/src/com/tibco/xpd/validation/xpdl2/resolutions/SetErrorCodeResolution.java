/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to set error code of an error event.
 * 
 * @author aallway
 * @since 3.2
 */
public class SetErrorCodeResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            EnterValueDialog nameDialog =
                    new EnterValueDialog(
                            Display.getDefault().getActiveShell(),
                            Messages.SetErrorCodeResolution_EnterErrorCode_title,
                            Messages.SetErrorCodeResolution_ErrorCode_label, "") { //$NON-NLS-1$
                        @Override
                        protected String validateValue(String newValue) {
                            if (newValue == null || newValue == "") { //$NON-NLS-1$
                                return Messages.SetErrorCodeResolution_ErrorCodeMustHaveAValue_longdesc;
                            } else {
                                if (newValue.trim().length() != newValue
                                        .length()) {
                                    return Messages.SetErrorCodeResolution_ErrorCodeMustNotHaveLeadTrailSpace_longdesc;
                                }
                            }

                            return super.validateValue(newValue);
                        }
                    };

            if (nameDialog.open() == EnterValueDialog.OK
                    && nameDialog.getValue() != null) {
                return EventObjectUtil.getSetErrorCodeCommand(editingDomain,
                        activity,
                        nameDialog.getValue());
            }
        }

        return null;
    }

}
