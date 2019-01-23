package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to set both ends of an association to optional
 *
 */
public class AssociationOptionalResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain domain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        if( target instanceof Association ) {
            final Association association = (Association) target;

            cmd = new ChangeCommand(domain, new Runnable() {
                public void run() {
                    for (Property property : association.getMemberEnds()) {
                        property.setLower(0);
                    }
                }
            });
        }
        
        return cmd;
    }
}
