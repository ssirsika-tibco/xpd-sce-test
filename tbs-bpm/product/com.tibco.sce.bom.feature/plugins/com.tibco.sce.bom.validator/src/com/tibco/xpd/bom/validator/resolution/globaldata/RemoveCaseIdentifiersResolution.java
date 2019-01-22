package com.tibco.xpd.bom.validator.resolution.globaldata;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Given a class it will remove all the case identifiers
 *
 */
public class RemoveCaseIdentifiersResolution extends
        AbstractWorkingCopyResolution {

    public RemoveCaseIdentifiersResolution() {
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
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

        Class clazz = (Class) target;

        GlobalDataProfileManager gdManager =
                GlobalDataProfileManager.getInstance();

        CompositeCommand result =
                new CompositeCommand(
                        Messages.RemoveAttributeCompositionResolution_command_label);

        // Get each case identifier and add it to the command list
        for (Property prop : clazz.getOwnedAttributes()) {
            if (gdManager.isCID(prop) || gdManager.isAutoCaseIdentifier(prop)
                    || gdManager.isCompositeCaseIdentifier(prop)) {
                DestroyElementRequest req =
                        new DestroyElementRequest(prop, false);
                ICommand editCommand =
                        ElementTypeRegistry.getInstance().getElementType(prop)
                                .getEditCommand(req);
                if (editCommand != null) {
                    result.add(editCommand);
                }
            }
        }

        if (!result.isEmpty()) {
            return new EMFOperationCommand(XpdResourcesPlugin.getDefault()
                    .getEditingDomain(), result);
        }

        return null;
    }

}
