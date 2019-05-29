package com.tibco.bds.designtime.validator.rules;

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
        return org.eclipse.uml2.uml.Property.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (!(o instanceof Property)) {
            return;
        }

        Property property = (Property) o;
        Type type = property.getType();
        if (!(type instanceof PrimitiveType)) {
            return;
        }

        // retrieve pattern value from property
        PrimitiveType pt = (PrimitiveType) type;
        Object pattern = PrimitivesUtil.getFacetPropertyValue(pt,
                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
                property,
                false);

        if (!isEmpty(pattern)) {
            scope.createIssue(CDSIssueIds.RESTRICTION_TEXT_ATTRIBUTE_PATTERN,
                    BOMValidationUtil.getLocation(property),
                    property.eResource().getURIFragment(property));
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
