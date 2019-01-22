package com.tibco.xpd.bom.validator.resolution.globaldata;

import static com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys.SOURCE_FRAGMENT_URI_LOCATION;

import java.util.Properties;

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

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Removes an attribute which serves as the source end of a uni-directional composition (typically inter-BOM) relationship.
 *
 * @author patkinso
 */
public class RemoveAttributeCompositionResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    protected Logger logger = GlobalDataActivator.getDefault().getLogger();

    protected Class intrinsicClass = null;

    public RemoveAttributeCompositionResolution() {
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        intrinsicClass = (Class) target;
        Property attribute = getTargetCompositeAttribute(marker);

        // create command
        if (attribute != null) {
            CompositeCommand result =
                    new CompositeCommand(
                            Messages.RemoveAttributeCompositionResolution_command_label);

            DestroyElementRequest req =
                    new DestroyElementRequest(attribute, false);
            ICommand editCommand =
                    ElementTypeRegistry.getInstance().getElementType(attribute)
                            .getEditCommand(req);
            if (editCommand != null) {
                result.add(editCommand);
            }

            return new EMFOperationCommand(XpdResourcesPlugin.getDefault()
                    .getEditingDomain(), result);
        }

        // log warning that resolution could not be successfully applied
        String fmtMsg =
                "The resolution could not be successfully applied. Therefore the composite attribute Class within the '%1$s' class has not been modified"; //$NON-NLS-1$      
        logger.warn(String.format(fmtMsg, intrinsicClass.getLabel()));
        return null;
    }

    /**
     * @param marker
     *            assumes marker contains the extraneous classes' resource and
     *            fragment locations
     * @return extraneous {@link Class} from the inter-BOM relationship
     */
    private Property getTargetCompositeAttribute(IMarker marker) {

        Properties additionalInfo = ValidationUtil.getAdditionalInfo(marker);
        if (additionalInfo != null) {
            if (intrinsicClass != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(intrinsicClass);

                if (wc != null) {
                    String extraneousFragmentUriStr =
                            additionalInfo
                                    .getProperty(SOURCE_FRAGMENT_URI_LOCATION);
                    EObject eo = wc.resolveEObject(extraneousFragmentUriStr);
                    if (eo instanceof Property) {
                        return (Property) eo;
                    }
                }
            }

            // log that could not determine type from marker
            String fmtMsg =
                    "Could not determine compisite property from the problem marker. The composite attribute may have already been removed."; //$NON-NLS-1$
            logger.warn(fmtMsg);
        }

        return null;
    }

}
