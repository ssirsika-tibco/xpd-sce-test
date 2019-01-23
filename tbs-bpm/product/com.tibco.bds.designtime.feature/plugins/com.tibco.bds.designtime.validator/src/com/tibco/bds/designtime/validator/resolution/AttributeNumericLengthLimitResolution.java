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
 * supported
 * 
 */
public class AttributeNumericLengthLimitResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    protected Command getResolutionCommand(EditingDomain domain,
            EObject target, IMarker marker) throws ResolutionException {

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

        // Get the base type in case this is a BOM primitive type defined by
        // the user, we want to get the type of that for checking
        final PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

        // Resolution is currently only for text fields
        if (primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)
                || primType.getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {

            return new RecordingCommand((TransactionalEditingDomain) domain) {

                protected void doExecute() {
                    // Make sure the correct value is used for the update
                    String propertyName =
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH;
                    if (primType.getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                        propertyName =
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH;
                    }

                    PrimitivesUtil
                            .setFacetPropertyValue((PrimitiveType) type,
                                    propertyName,
                                    Integer.toString(BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION),
                                    prop);
                }
            };
        }

        return null;
    }
}
