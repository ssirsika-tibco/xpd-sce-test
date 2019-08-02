package com.tibco.bds.designtime.validator.ace.rules;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific attribute/primitive-type constraint rules...
 *
 * <li>Only the simple type Text, Number, Date, Time, Date-Time with Timezone,
 * URI and Boolean are supported for attributes.</li>
 * <li>Only the simple type Text, Number, Date, Time, Date-Time with Timezone,
 * URI and Boolean are supported for Primitive Type definitions.</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceConstraintConfigurationRules implements IValidationRule {

    private static final String ISSUE_ACE_NUMBER_PROPERTY_MAX_LENGTH =
            "ace.bom.number.property.max.length"; //$NON-NLS-1$

    private static final String ISSUE_ACE_NUMBER_PRIMITIVE_MAX_LENGTH =
            "ace.bom.number.primitive.max.length"; //$NON-NLS-1$

    private static final String ISSUE_ACE_NUMBER_DEC_PLACES =
            "ace.bom.number.property.dec.places"; //$NON-NLS-1$

    private static final String ISSUE_ACE_NUMBER_MAX_UPPER_LIMIT = "ace.bom.number.upper.limit"; //$NON-NLS-1$

    private static final String ISSUE_ACE_NUMBER_MAX_LOWER_LIMIT = "ace.bom.number.lower.limit"; //$NON-NLS-1$

    private static final int MAX_NUMBER_LENGTH =
            PrimitivesUtil.MAX_FIXED_POINT_NUMBER_LENGTH;

    private final PrimitiveType decimalPrimitiveType;

    /**
     * Maximum value supported for numeric fields like upper value, lower value
     * etc.
     */
    private static final double MAX_RANGE_VALUE = 999999999999999D;

    /**
     * Minimum value supported for numeric fields like upper value, lower value
     * etc.
     */
    private static final double MIN_RANGE_VALUE = -999999999999999D;

    /**
     * Load up the valid type definitions.
     */
    public AceConstraintConfigurationRules() {
        ResourceSet rSet = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        decimalPrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
    }

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Property) {
            validateProperty(scope, (Property) obj);

        } else if (obj instanceof PrimitiveType) {
            validatePrimitiveType(scope, (PrimitiveType) obj);
        }
    }

    /**
     * Validate type of the given primitive type.
     * 
     * @param scope
     * @param primitiveType
     */
    private void validatePrimitiveType(IValidationScope scope,
            PrimitiveType primitiveType) {
        Type type = PrimitivesUtil.getBasePrimitiveType(primitiveType);

        if (decimalPrimitiveType.equals(type)) {
            if (!validLength(primitiveType, null)) {
                scope.createIssue(ISSUE_ACE_NUMBER_PRIMITIVE_MAX_LENGTH,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource()
                                .getURIFragment(primitiveType));
            }

            else if (!validDecimals(primitiveType, null)) {
                scope.createIssue(ISSUE_ACE_NUMBER_DEC_PLACES,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource()
                                .getURIFragment(primitiveType));
            }

            if (!validUpperValue((PrimitiveType) type, null)) {
                scope.createIssue(ISSUE_ACE_NUMBER_MAX_UPPER_LIMIT,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }

            if (!validLowerValue((PrimitiveType) type, null)) {
                scope.createIssue(ISSUE_ACE_NUMBER_MAX_LOWER_LIMIT,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }
    }

    /**
     * Validate the type of the given property
     * 
     * @param scope
     * @param property
     */
    private void validateProperty(IValidationScope scope, Property property) {
        Type type = property.getType();

        if (decimalPrimitiveType.equals(type)) {
            if (!validLength((PrimitiveType) type, property)) {
                scope.createIssue(ISSUE_ACE_NUMBER_PROPERTY_MAX_LENGTH,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }

            else if (!validDecimals((PrimitiveType) type, property)) {
                scope.createIssue(ISSUE_ACE_NUMBER_DEC_PLACES,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }

            if (!validUpperValue((PrimitiveType) type, property)) {
                scope.createIssue(ISSUE_ACE_NUMBER_MAX_UPPER_LIMIT,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }

            if (!validLowerValue((PrimitiveType) type, property)) {
                scope.createIssue(ISSUE_ACE_NUMBER_MAX_LOWER_LIMIT,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }
    }

    /**
     * Test whether the configured max-length of the given property is less than
     * or equal to permitted maximum.
     * 
     * @param aType
     * @param aProperty
     * @return <code>true</code> if the max-length is valid.
     */
    private boolean validLength(PrimitiveType aType, Property aProperty) {
        Number maxLength = getMaxLength(aType, aProperty);

        if (maxLength != null) {
            return (maxLength.intValue() <= MAX_NUMBER_LENGTH);
        }

        return true;
    }

    /**
     * Test whether the configured upper-value of the given property is less
     * than or equal to permitted maximum.
     * 
     * @param aType
     * @param aProperty
     * @return <code>true</code> if the upper-value is valid.
     */
    private boolean validUpperValue(PrimitiveType aType, Property aProperty) {
        Object upperValueObject = getUpperValue(aType, aProperty);

        if (upperValueObject != null) {
            double upperValue = Double.parseDouble(upperValueObject.toString());
            return (upperValue <= MAX_RANGE_VALUE && upperValue >= MIN_RANGE_VALUE);
        }

        return true;
    }

    /**
     * Test whether the configured lower-value of the given property is less
     * than or equal to permitted maximum.
     * 
     * @param aType
     * @param aProperty
     * @return <code>true</code> if the lower-value is valid.
     */
    private boolean validLowerValue(PrimitiveType aType, Property aProperty) {
        Object lowerValueObject = getLowerValue(aType, aProperty);

        if (lowerValueObject != null) {
            double lowerValue = Double.parseDouble(lowerValueObject.toString());
            return (lowerValue <= MAX_RANGE_VALUE && lowerValue >= MIN_RANGE_VALUE);
        }

        return true;
    }

    /**
     * Test whether the configured decimal-places of the given property is less
     * than or equal to the property's max-length.
     * 
     * @param aType
     * @param aProperty
     * @return <code>true</code> if the dec-places is valid.
     */
    private boolean validDecimals(PrimitiveType aType, Property aProperty) {
        Number maxLength = getMaxLength(aType, aProperty);

        if (maxLength != null) {
            Number decimals = getDecimals(aType, aProperty);
            return (decimals == null)
                    || (decimals.intValue() <= maxLength.intValue());
        }

        return true;
    }

    private Number getMaxLength(PrimitiveType aType, Property aProperty) {
        Object result;
        if (aProperty == null) {
            result = PrimitivesUtil.getFacetPropertyValue(aType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);
        } else {
            result = PrimitivesUtil.getFacetPropertyValue(aType,
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                    aProperty);
        }
        return (result instanceof Number) ? (Number) result : null;
    }

    private Object getUpperValue(PrimitiveType aType, Property aProperty) {
        Object result;
        if (aProperty == null) {
            result = PrimitivesUtil.getFacetPropertyValue(aType, PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);
        } else {
            result = PrimitivesUtil
                    .getFacetPropertyValue(aType, PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER, aProperty);
        }
        return result;
    }

    private Object getLowerValue(PrimitiveType aType, Property aProperty) {
        Object result;
        if (aProperty == null) {
            result = PrimitivesUtil.getFacetPropertyValue(aType, PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);
        } else {
            result = PrimitivesUtil
                    .getFacetPropertyValue(aType, PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER, aProperty);
        }
        return result;
    }

    private Number getDecimals(PrimitiveType aType, Property aProperty) {
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