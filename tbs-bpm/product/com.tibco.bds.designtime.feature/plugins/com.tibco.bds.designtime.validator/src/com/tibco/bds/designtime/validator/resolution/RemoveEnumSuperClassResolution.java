package com.tibco.bds.designtime.validator.resolution;

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
 * Resolution to remove the superclass from the currently selected Classifier.
 *
 * @author sajain
 * @since Jun 13, 2019
 */
public class RemoveEnumSuperClassResolution
        extends AbstractWorkingCopyResolution implements IResolution {
    @Override
    protected Command getResolutionCommand(EditingDomain domain, EObject target,
            IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Classifier) {
            cmd = RemoveCommand.create(domain,
                    target,
                    UMLPackage.eINSTANCE.getClassifier_Generalization(),
                    ((Classifier) target).getGeneralizations());
        }

        return cmd;
    }
}
