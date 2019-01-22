package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Changes the lower-bound multiplicity of a Property
 * to 1, thus making it mandatory.
 * @author smorgan
 *
 */
public class MakePropertyMandatoryResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Property) {
            final Property prop = (Property) target;
            cmd = new ChangeCommand(editingDomain, new Runnable() {
                public void run() {
                    prop.setLower(1);
                }
            });
        }
        return cmd;
    }
}
