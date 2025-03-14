package com.tibco.bds.designtime.validator.ace.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate that attributes and primitive types are in the set of supported
 * types for the ACE destination
 *
 * <li>Only the simple type Text, Number, Date, Time, Date-Time with Timezone,
 * URI and Boolean are supported for attributes.</li>
 * <li>Only the simple type Text, Number, Date, Time, Date-Time with Timezone,
 * URI and Boolean are supported for Primitive Type definitions.</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceSupportedBomTypesRule implements IValidationRule {

    private static final String ISSUE_ACE_ILLEGAL_PROPERTY_TYPE =
            "ace.bom.illegal.property.type"; //$NON-NLS-1$

    private static final String ISSUE_ACE_ILLEGAL_PRIMITIVE_TYPE =
            "ace.bom.illegal.primitive.type"; //$NON-NLS-1$

    private static final String ISSUE_ACE_ILLEGAL_PROPERTY_DATE_TYPE =
            "ace.bom.illegal.property.date.type"; //$NON-NLS-1$

    private static final String ISSUE_ACE_ILLEGAL_PRIMITIVE_DATE_TYPE =
            "ace.bom.illegal.primitive.date.type"; //$NON-NLS-1$

    private PrimitiveType textPrimitiveType;

    private PrimitiveType decimalPrimitiveType;

    private PrimitiveType datePrimitiveType;

    private PrimitiveType timePrimitiveType;

    private PrimitiveType dateTimeTZPrimitiveType;

    private PrimitiveType dateTimePrimitiveType;

    private PrimitiveType uriPrimitiveType;

    private PrimitiveType booleanPrimitiveType;

    /**
     * Load up the valid type definitions.
     */
    public AceSupportedBomTypesRule() {
        ResourceSet rSet = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        textPrimitiveType = PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        decimalPrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        datePrimitiveType = PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);

        timePrimitiveType = PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);

        dateTimeTZPrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);

        dateTimePrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);

        uriPrimitiveType = PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);

        booleanPrimitiveType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);

    }

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    /**
     * Validate type of the given primitive type.
     * 
     * @param scope
     * @param primitiveType
     */
    protected void validatePrimitiveType(IValidationScope scope,
            PrimitiveType primitiveType) {
        if (!isValidType(PrimitivesUtil.getBasePrimitiveType(primitiveType))) {
            if (isDateTimeType(primitiveType)) {
                // assign issue with quick-fix
                scope.createIssue(ISSUE_ACE_ILLEGAL_PRIMITIVE_DATE_TYPE,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource()
                                .getURIFragment(primitiveType));

            } else {
                scope.createIssue(ISSUE_ACE_ILLEGAL_PRIMITIVE_TYPE,
                        BOMValidationUtil.getLocation(primitiveType),
                        primitiveType.eResource()
                                .getURIFragment(primitiveType));
            }
        }
    }

    /**
     * Validate the type of the given property
     * 
     * @param scope
     * @param property
     */
    protected void validateProperty(IValidationScope scope, Property property) {
        /*
         * Sid ACE-1327 "Must be Text, Number etc" problem should not be raised
         * for attributes that haven't had a type selected.
         * 
         * We should raise a separate issue instead (a generic one in bas BOM
         * validation
         */
        if (property.getType() != null && !isValidType(property.getType())) {
            if (isDateTimeType(property.getType())) {
                // assign issue with quick-fix
                scope.createIssue(ISSUE_ACE_ILLEGAL_PROPERTY_DATE_TYPE,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            } else {
                scope.createIssue(ISSUE_ACE_ILLEGAL_PROPERTY_TYPE,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }
    }

    /**
     * @param aType
     * @return <code>true</code> if the given type is a complex type OR is a
     *         supported simple type.
     */
    private boolean isValidType(Type aType) {
        if (aType == null) {
            return false;
        }

        if (!(aType instanceof PrimitiveType)) {
            /* Complex types are all ok as far as this rule is concerned */
            return true;
        }

        /*
         * Sid ACE-1051: Allow properties and primitive types to be of
         * user-defined primitive types (the base super-type of all of these
         * will be validated ultimately.
         */
        EList<Classifier> superType = ((PrimitiveType) aType).getGenerals();
        if (superType != null && !superType.isEmpty()) {
            return true;
        }

        if (textPrimitiveType.equals(aType)
                || decimalPrimitiveType.equals(aType)
                || datePrimitiveType.equals(aType)
                || timePrimitiveType.equals(aType)
                || dateTimeTZPrimitiveType.equals(aType)
                || uriPrimitiveType.equals(aType)
                || booleanPrimitiveType.equals(aType)) {
            return true;
        }

        return false;
    }

    /**
     * Tests if the given type is a simple date-time type - without time-zone.
     * 
     * @param aType
     *            the type to be tested.
     * @return <code>true</code> if the given type is a date-time type.
     */
    private boolean isDateTimeType(Type aType) {
        // is it the date-time primitive
        if (dateTimePrimitiveType.equals(aType)) {
            return true;
        }

        if (aType instanceof PrimitiveType) {
            // is it a subclass of date-time
            PrimitiveType pt = (PrimitiveType) aType;
            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(pt);
            if (dateTimePrimitiveType.equals(basePrimitiveType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Property) {
            /*
             * Sid ACE-525. Don't raise the 'must be type a,b,c' rule for case
             * id as it will have it's own 'Case identifier must be Text type'
             * problem.
             */
            if (!BOMGlobalDataUtils.isCID((Property) obj)) {
                validateProperty(scope, (Property) obj);
            }

        } else if (obj instanceof PrimitiveType) {
            validatePrimitiveType(scope, (PrimitiveType) obj);
        }
    }
}
