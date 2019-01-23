/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.bpmn.developer.rules.MissingImplementationTypeRule;
import com.tibco.xpd.validation.bpmn.developer.rules.MissingImplementationTypeRule.ImplementationTypeHostInfo;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * FixMissingXpdExtImplementationTypeResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (16 Dec 2009)
 */
public class FixMissingXpdExtImplementationTypeResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            ImplementationTypeHostInfo implementationTypeHost =
                    MissingImplementationTypeRule
                            .getImplementationTypeHost(activity);

            if (implementationTypeHost != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.FixMissingXpdExtImplementationTypeResolution_FixImplType_menu);

                ImplementationType implementationType =
                        (ImplementationType) implementationTypeHost.hostObject
                                .eGet(implementationTypeHost.xpdl2ImplementationFeature);
                if (implementationType == null
                        || !implementationTypeHost.hostObject
                                .eIsSet(implementationTypeHost.xpdl2ImplementationFeature)) {
                    cmd.append(SetCommand.create(editingDomain,
                            implementationTypeHost.hostObject,
                            implementationTypeHost.xpdl2ImplementationFeature,
                            ImplementationType.WEB_SERVICE_LITERAL));
                }

                String extImplementationType =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute((OtherAttributesContainer) implementationTypeHost.hostObject,
                                        implementationTypeHost.xpdExtImplementationTypeFeature);
                if (extImplementationType == null
                        || extImplementationType.length() == 0) {
                    cmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            (OtherAttributesContainer) implementationTypeHost.hostObject,
                                            implementationTypeHost.xpdExtImplementationTypeFeature,
                                            TaskImplementationTypeDefinitions.WEB_SERVICE));
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }

            }
        }
        return null;
    }

}
