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

    private PrimitiveType decimalPrimitiveType;

    private static final int MAX_NUMBER_LENGTH = 15;

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

    /**
     * Validate type of the given primitive type.
     * 
     * @param scope
     * @param primitiveType
     */
    protected void validatePrimitiveType(IValidationScope scope,
            PrimitiveType primitiveType) {
        if (decimalPrimitiveType
                .equals(PrimitivesUtil.getBasePrimitiveType(primitiveType))) {
            boolean badLength = true;

            Object maxLength =
                    PrimitivesUtil.getFacetPropertyValue(primitiveType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

            if (maxLength instanceof Integer
                    && (Integer) maxLength <= MAX_NUMBER_LENGTH) {
                badLength = false;
            }

            if (badLength) {
                scope.createIssue(ISSUE_ACE_NUMBER_PRIMITIVE_MAX_LENGTH,
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
        Type type = property.getType();

        if (decimalPrimitiveType.equals(type)) {
            boolean badLength = true;

            Object maxLength =
                    PrimitivesUtil.getFacetPropertyValue((PrimitiveType) type,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                            property);

            if (maxLength instanceof Integer
                    && (Integer) maxLength <= MAX_NUMBER_LENGTH) {
                badLength = false;
            }

            if (badLength) {
                scope.createIssue(ISSUE_ACE_NUMBER_PROPERTY_MAX_LENGTH,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Property) {
            validateProperty(scope, (Property) obj);

        } else if (obj instanceof PrimitiveType) {
            validatePrimitiveType(scope, (PrimitiveType) obj);
        }
    }
}
