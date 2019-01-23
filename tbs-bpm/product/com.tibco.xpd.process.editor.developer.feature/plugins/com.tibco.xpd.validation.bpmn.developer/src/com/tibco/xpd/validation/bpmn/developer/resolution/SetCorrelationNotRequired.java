/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class SetCorrelationNotRequired extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command command = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            /*
             * XPD-4709: Explicit correlation data association should be checked
             * for Start Message Event as well as Receive Task and should be
             * removed. Initially it was only checked for Start Message Event.
             */
            if (Xpdl2ModelUtil.isMessageProcessStartActivity(activity)) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.SetCorrelationNotRequired_CommandLabel);
                AssociatedCorrelationFields fieldContainer =
                        XpdExtensionFactory.eINSTANCE
                                .createAssociatedCorrelationFields();
                fieldContainer.setDisableImplicitAssociation(true);
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedCorrelationFields(),
                                fieldContainer));
                command = cmd;
            }

        }
        return command;
    }

}
