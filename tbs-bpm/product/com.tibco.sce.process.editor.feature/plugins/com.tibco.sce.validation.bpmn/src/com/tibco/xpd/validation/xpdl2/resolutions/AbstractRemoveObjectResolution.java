/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Abstract Resolution that removes the validation marker target (by default).
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractRemoveObjectResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        EObject removeTarget = getObjectToRemove(target, marker);
        if (removeTarget != null) {
            CompoundCommand cmd = new CompoundCommand();

            String label = getCommandLabel(target, marker);
            if (label != null) {
                cmd.setLabel(label);
            }

            cmd.append(RemoveCommand.create(editingDomain, removeTarget));

            return cmd;
        }
        return null;
    }

    protected abstract String getCommandLabel(EObject validationMarkerTarget,
            IMarker marker);

    protected EObject getObjectToRemove(EObject validationMarkerTarget,
            IMarker marker) {
        return validationMarkerTarget;
    }

}
