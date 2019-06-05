package com.tibco.bds.designtime.validator.rules;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A rule that creates an issue if the given text attribute has a pattern
 * defined.
 * 
 * @author pwatson
 */
public class AttributeTextPatternRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            validate(scope, (Property) o);
        } else if (o instanceof PrimitiveType) {
            validate(scope, (PrimitiveType) o);
        }
    }

    private void validate(IValidationScope scope,
            PrimitiveType aPrimitiveType) {
        // Resolution is currently only for text fields
        PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType(aPrimitiveType);
        if (!primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            return;
        }

        // retrieve pattern value from property
        Object pattern = PrimitivesUtil.getFacetPropertyValue(aPrimitiveType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);

        if (!isEmpty(pattern)) {
            scope.createIssue(CDSIssueIds.RESTRICTION_TEXT_ATTRIBUTE_PATTERN,
                    BOMValidationUtil.getLocation(aPrimitiveType),
                    aPrimitiveType.eResource().getURIFragment(aPrimitiveType));
        }
    }

    private void validate(IValidationScope scope, Property aProperty) {
        Type type = aProperty.getType();

        if (!(type instanceof PrimitiveType)) {
            return;
        }

        // Resolution is currently only for text fields
        PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
        if (!primType.getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            return;
        }

        // retrieve pattern value from property
        Object pattern = PrimitivesUtil.getFacetPropertyValue(primType,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
                aProperty,
                false);

        if (!isEmpty(pattern)) {
            scope.createIssue(CDSIssueIds.RESTRICTION_TEXT_ATTRIBUTE_PATTERN,
                    BOMValidationUtil.getLocation(aProperty),
                    aProperty.eResource().getURIFragment(aProperty));
        }
    }

    /**
     * Tests the given object to determine if it is an "empty" value. That is, a
     * null or empty string.
     * 
     * @param aValue
     *            the value to be tested.
     * @return <code>true</code> if the given value is "empty".
     */
    private boolean isEmpty(Object aValue) {
        return (aValue == null) || (aValue.equals("")); //$NON-NLS-1$
    }
}
