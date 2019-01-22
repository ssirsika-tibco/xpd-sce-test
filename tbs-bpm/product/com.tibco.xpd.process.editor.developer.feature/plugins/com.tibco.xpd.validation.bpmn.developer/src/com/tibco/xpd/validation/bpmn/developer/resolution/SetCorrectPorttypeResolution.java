/*
 * Copyright (c) TIBCO Software Inc. 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.rules.BpmnWebServiceReferenceValidationRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to correct the port type name when port type does not
 * match a concrete operation selection.
 * 
 * @author njpatel
 * @since 3.5.3
 */
public class SetCorrectPorttypeResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            String portTypeName = getPortTypeName(marker);
            if (portTypeName != null) {

                PortTypeOperation portTypeOperation =
                        Xpdl2ModelUtil.getPortTypeOperation(activity);

                if (portTypeOperation != null) {
                    return SetCommand.create(editingDomain,
                            portTypeOperation,
                            XpdExtensionPackage.eINSTANCE
                                    .getPortTypeOperation_PortTypeName(),
                            portTypeName);
                }
            }
        }
        return null;
    }

    /**
     * Get the correct port type name from the marker.
     * 
     * @param marker
     * @return
     */
    private String getPortTypeName(IMarker marker) {
        Properties info = ValidationUtil.getAdditionalInfo(marker);
        if (info != null) {
            return (String) info
                    .get(BpmnWebServiceReferenceValidationRule.ATT_CORRECT_PORTTYPE_NAME);
        }
        return null;
    }

}
