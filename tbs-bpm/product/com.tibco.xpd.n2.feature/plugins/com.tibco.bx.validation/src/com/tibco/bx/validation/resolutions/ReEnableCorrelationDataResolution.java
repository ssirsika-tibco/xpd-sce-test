/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Re-enables correlation data association on selected activity.
 * 
 * @author sajain
 * @since Sep 18, 2014
 */
public class ReEnableCorrelationDataResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
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
        CompoundCommand cmd = new CompoundCommand();

        if (target instanceof Activity) {

            Activity activity = (Activity) target;

            /*
             * Fetch associated correlation fields element.
             */
            AssociatedCorrelationFields acfs =
                    (AssociatedCorrelationFields) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());

            /*
             * Check if associated correlation fields is null.
             */
            if (acfs != null) {

                /*
                 * Check if user has set
                 * "No correlation data initialisation required" option.
                 */
                if (acfs.isDisableImplicitAssociation()) {

                    /*
                     * If yes, then reset it to "false".
                     */

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    acfs,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedCorrelationFields_DisableImplicitAssociation(),
                                    false));
                }
            }

        }

        return cmd;
    }
}
