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

import com.tibco.bds.designtime.generator.common.BDSConstants;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to change the length of a numeric attribute to the maximum
 * supported.
 */
public class AttributeNumericLengthLimitResolution
        extends AbstractWorkingCopyResolution implements IResolution {
    @Override
    protected Command getResolutionCommand(EditingDomain domain, EObject target,
            IMarker marker) throws ResolutionException {
        // Should only be set for Property types
        if (target instanceof Property) {
            return resolve(domain, (Property) target);
        }

        else if (target instanceof PrimitiveType) {
            return resolve(domain, null, (PrimitiveType) target);
        }

        return null;
    }

    /**
     * Returns the resolution for property based markers.
     * 
     * @param domain
     * @param aProperty
     * @return
     */
    private Command resolve(EditingDomain domain, Property aProperty) {
        final Type type = aProperty.getType();

        // Only deal with Primitive Types
        if (!(type instanceof PrimitiveType)) {
            return null;
        }

        return resolve(domain, aProperty, (PrimitiveType) type);
    }

    /**
     * Returns the resolution for primitive-type based markers, optionally
     * sourced from a property based marker.
     * 
     * @param domain
     * @param aProperty
     * @param aType
     * @return
     */
    private Command resolve(EditingDomain domain, Property aProperty,
            PrimitiveType aType) {
        // Get the base type in case this is a BOM primitive type defined by
        // the user, we want to get the type of that for checking
        final PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType(aType);

        // resolution is only for numeric fields
        final boolean isDecimal = primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        final boolean isInteger = primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        if ((!isDecimal) && (!isInteger)) {
            return null;
        }

        return new RecordingCommand((TransactionalEditingDomain) domain) {
            @Override
            protected void doExecute() {
                // Make sure the correct value is used for the update
                String propertyName = (isInteger)
                        ? PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH
                        : PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH;

                PrimitivesUtil.setFacetPropertyValue(aType,
                        propertyName,
                        Integer.toString(
                                BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION),
                        aProperty);
            }
        };
    }
}
