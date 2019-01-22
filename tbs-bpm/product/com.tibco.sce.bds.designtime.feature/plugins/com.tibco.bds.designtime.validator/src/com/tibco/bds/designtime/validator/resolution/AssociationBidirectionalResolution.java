package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to set an association as bidirectional
 * 
 */
public class AssociationBidirectionalResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain domain,
            EObject target, IMarker marker) throws ResolutionException {
        CompoundCommand cmd = null;

        if (target instanceof Association) {
            final Association association = (Association) target;
            Property src = association.getMemberEnds().get(0);
            Property tgt = association.getMemberEnds().get(1);

            if (src != null && tgt != null) {
                Property ownedProp = association.getOwnedEnds().get(0);
                if (ownedProp != null) {

                    cmd = new CompoundCommand();

                    // Remove property from ownends
                    cmd.append(RemoveCommand.create(domain,
                            association,
                            UMLPackage.eINSTANCE.getAssociation_OwnedEnd(),
                            ownedProp));

                    Type addTo = null;
                    if (ownedProp == src) {
                        addTo = tgt.getType();
                    } else if (ownedProp == tgt) {
                        addTo = src.getType();
                    }

                    if (addTo != null) {
                        cmd.append(AddCommand.create(domain,
                                addTo,
                                UMLPackage.eINSTANCE
                                        .getStructuredClassifier_OwnedAttribute(),
                                ownedProp));
                    }
                }
            }
        }

        return cmd;
    }
}
