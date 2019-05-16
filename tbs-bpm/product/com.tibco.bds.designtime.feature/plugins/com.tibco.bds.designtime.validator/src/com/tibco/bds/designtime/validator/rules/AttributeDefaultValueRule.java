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
 * A rule that creates an issue if the given attribute has a multiplicity >1 and
 * a default value defined.
 * 
 * @author smorgan
 * 
 */
public class AttributeDefaultValueRule implements IValidationRule {

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

        PrimitiveType pt = (PrimitiveType) type;
        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType(pt);

        String name =
                PrimitivesUtil.getFacetNameForDefaultValue(basePrimitiveType);
        if (name == null) {
            return;
        }

        Object defVal =
                PrimitivesUtil.getFacetPropertyValue(pt, name, property, false);

        boolean checkedType = false;
        if (isEmpty(defVal)) {
            // No default defined on the property itself,
            // so see if it inherits one from its type.
            defVal = PrimitivesUtil
                    .getFacetPropertyValue(pt, name, property, true);
            checkedType = true;
        }

        if (isEmpty(defVal)) {
            return;
        }

        // It has a default value
        // First deal with the case where it is an imported
        // restriction we only want the errors on imports in
        // this instance
        boolean isImportedRestriction = false;
        if ((property.getLower() == 1) && (property.getUpper() == 1)) {
            if (isImportedRestriction) {
                scope.createIssue(
                        CDSIssueIds.RESTRICTION_VALUE_ATTRIBUTE_DEFAULT,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }

        // If the attribute can take multiple values, a default
        // makes no sense.
        else if (property.isMultivalued()) {
            scope.createIssue(checkedType
                    ? CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT_FROM_TYPE
                    : CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT,
                    BOMValidationUtil.getLocation(property),
                    property.eResource().getURIFragment(property));

        } else if (property.getLower() == 0) {
            // If the attribute is optional, a default makes no
            // sense, as it will _always_ apply, thus making it
            // impossible to not specify the attribute.
            scope.createIssue(
                    checkedType
                            ? CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT_FROM_TYPE
                            : CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT,
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
