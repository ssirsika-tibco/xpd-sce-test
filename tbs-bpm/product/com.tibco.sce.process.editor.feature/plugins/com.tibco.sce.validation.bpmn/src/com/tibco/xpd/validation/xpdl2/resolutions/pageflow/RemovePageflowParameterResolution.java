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

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;

/**
 * Resolution to remove a pageflow parameter.
 * 
 * @author aallway
 * @since 3.2
 */
public class RemovePageflowParameterResolution extends
        AbstractPageflowParamDataResolution {

    @Override
    protected Command createResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity userTaskActivity = (Activity) target;

            Process pageflowProcess = getPageflowProcessFromMarker(marker);
            String paramName = getPageflowParamNameFromMarker(marker);
            FormalParameter pageflowParam =
                    getPageflowParameter(pageflowProcess, paramName);

            if (pageflowProcess != null && pageflowParam != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.RemovePageflowParameterResolution_RemovePageflowParam_menu);
                //
                // Get Command to remove references
                Command refCmd =
                        ProcessDataUtil
                                .getRemoveProcessDataReferenceCommand(editingDomain,
                                        pageflowParam,
                                        pageflowProcess);
                if (refCmd != null) {
                    cmd.append(refCmd);
                }

                cmd.append(RemoveCommand.create(editingDomain, pageflowParam));
                return cmd;
            }
        }
        return null;
    }

}
