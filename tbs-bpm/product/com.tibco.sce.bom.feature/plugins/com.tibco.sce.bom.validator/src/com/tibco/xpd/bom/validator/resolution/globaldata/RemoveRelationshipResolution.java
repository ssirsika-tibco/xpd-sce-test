package com.tibco.xpd.bom.validator.resolution.globaldata;

import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.RELATIONSHIP_LOCATION;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Relationship;

import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Removes a relationship: inc. generalization; association; aggregation;
 * composition
 * 
 * @author patkinso
 */
public class RemoveRelationshipResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Relationship || target instanceof Class) {

            // Determine target relationship
            EObject relationship = null;

            if (target instanceof Relationship) {
                relationship = target;
            } else if (target instanceof Class) {
                Properties additionalInfo =
                        ValidationUtil.getAdditionalInfo(marker);

                if (additionalInfo != null) {
                    String location =
                            additionalInfo.getProperty(RELATIONSHIP_LOCATION);
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(target);

                    if (wc != null) {
                        relationship = wc.resolveEObject(location);
                    }
                }
            }

            // create command
            if (relationship != null) {
                CompositeCommand result =
                        new CompositeCommand(
                                Messages.RemoveRelationshipResolution_command_label);

                DestroyElementRequest req =
                        new DestroyElementRequest(relationship, false);
                ICommand editCommand =
                        ElementTypeRegistry.getInstance()
                                .getElementType(relationship)
                                .getEditCommand(req);
                if (editCommand != null) {
                    result.add(editCommand);
                }

                return new EMFOperationCommand(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), result);
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {
        super.run(marker);

        /*
         * Save working copy to force re-validation as resolution's modification
         * was not to supplied target
         */
        try {
            EObject target = getTarget(marker);
            if (target != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(target);

                if (wc != null) {
                    try {
                        wc.save();
                    } catch (IOException e) {
                        throw new ResolutionException(e);
                    }
                }
            }
        } catch (CoreException e) {
            throw new ResolutionException(e);
        }
    }

}
