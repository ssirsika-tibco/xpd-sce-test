package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.resolutions.RenameDialog;

/**
 * Resolution to change the dec-places of a numeric attribute to a selected
 * value.
 */
public class AttributeNumericDecPlacesResolution
        extends AbstractWorkingCopyResolution implements IResolution {

    private static final Number DEFAULT_DEC_PLACES = Integer.valueOf(2);

    private final PrimitiveType decimalPrimitiveType;

    public AttributeNumericDecPlacesResolution() {
        super();

        ResourceSet rSet = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        decimalPrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
    }

    @Override
    protected Command getResolutionCommand(EditingDomain domain, EObject target,
            IMarker marker) throws ResolutionException {
        if (target instanceof Property) {
            return resolve(domain, (Property) target);
        }

        if (target instanceof PrimitiveType) {
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
    private Command resolve(EditingDomain domain, final Property aProperty,
            final PrimitiveType aType) {

        // resolution is only for decimal fields
        if (!decimalPrimitiveType
                .equals(PrimitivesUtil.getBasePrimitiveType(aType))) {
            return null;
        }

        // allow the user to select a new value
        Number oldValue = getOriginalValue(aType, null);
        String newScaleValue = getNewValue(oldValue, DEFAULT_DEC_PLACES);

        // if no new value given
        if ((newScaleValue == null) || (newScaleValue.isEmpty())) {
            return null;
        }

        return new RecordingCommand((TransactionalEditingDomain) domain) {
            @Override
            protected void doExecute() {
                PrimitivesUtil.setFacetPropertyValue(aType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                        newScaleValue,
                        aProperty);
            }
        };
    }

    /**
     * Ask the user what value to set as the decimal places.
     * 
     * @param oldValue
     *            the current decimal places setting.
     * @param defaultValue
     *            the default decimal places setting
     * @return the chosen decimal places setting - as a string. If the user
     *         choses "cancel", the return value will be an empty string.
     */
    private String getNewValue(Number oldValue, Number defaultValue) {

        String old = (oldValue == null) ? "" : oldValue.toString(); //$NON-NLS-1$
        String def = (defaultValue == null) ? "" : defaultValue.toString(); //$NON-NLS-1$
        return RenameDialog.getDecimalPlaces(old, def);
    }

    /**
     * Returns the given property's original dec-places value. If no value was
     * specified the return value will be null.
     * 
     * @param aType
     * @param aProperty
     * @return
     */
    private Number getOriginalValue(PrimitiveType aType, Property aProperty) {
        Object result;
        if (aProperty == null) {
            result = PrimitivesUtil.getFacetPropertyValue(aType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);
        } else {
            result = PrimitivesUtil.getFacetPropertyValue(aType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                    aProperty);
        }
        return (result instanceof Number) ? (Number) result : null;
    }
}
