package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to remove the pattern property from text attributes.
 */
public class RemoveTextPatternResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain domain, EObject target,
            IMarker marker) throws ResolutionException {

        // Should only be set for Property types
        if (!(target instanceof Property)) {
            return null;
        }

        final Property prop = (Property) target;
        final Type type = prop.getType();

        // Only deal with Primitive Types
        if (!(type instanceof PrimitiveType)) {
            return null;
        }

        // Resolution is currently only for text fields
        PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
        if (!primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            return null;
        }

        // issue command to set the pattern property to null
        return new RecordingCommand((TransactionalEditingDomain) domain) {
            @Override
            protected void doExecute() {
                PrimitivesUtil.setFacetPropertyValue((PrimitiveType) type,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
                        null,
                        prop);
            }
        };
    }
}
