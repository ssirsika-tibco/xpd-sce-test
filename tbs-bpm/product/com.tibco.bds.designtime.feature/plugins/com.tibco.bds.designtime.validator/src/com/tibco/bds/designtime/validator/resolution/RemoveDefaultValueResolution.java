package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Removes the default value from a Property backed by a PrimitiveType
 * 
 * @author smorgan
 * 
 */
public class RemoveDefaultValueResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        Command cmd = null;
        final Property prop = (Property) target;
        Type type = prop.getType();
        // At the time of writing, this resolution would only
        // be used when the prop's type is a PT
        if (type instanceof PrimitiveType) {
            PrimitiveType baseType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
            // Determine the facet to nullify and construct
            // a command to nullify it.
            final String facetName =
                    PrimitivesUtil.getFacetNameForDefaultValue(baseType);
            if (prop.eResource() != null) {
                ResourceSet rSet = prop.eResource().getResourceSet();
                if (rSet != null) {
                    final Stereotype st =
                            PrimitivesUtil.getFacetsStereotype(rSet);
                    cmd = new ChangeCommand(editingDomain, new Runnable() {
                        public void run() {
                            prop.setValue(st, facetName, null);
                        }
                    });
                }
            }
        }
        return cmd;
    }
}
