package com.tibco.xpd.bom.validator.resolution.xsd;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * A resolution that removes an Operation from a Classifier
 * 
 * @author smorgan
 * 
 */
public class OperationRemovalResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        final Classifier c = (Classifier) target.eContainer();
        return RemoveCommand.create(editingDomain, c, UMLPackage.eINSTANCE
                .getClass_OwnedOperation(), target);
    }

}
