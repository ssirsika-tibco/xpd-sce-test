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

        if (o instanceof Property) {
            Property p = (Property) o;

            boolean isImportedRestriction = false;

            /*
             * Sid ACE-122 - we don't do XSD generation anymore so removed code
             * related to XSD stereotype.
             */

            Type type = p.getType();

            if (type instanceof PrimitiveType) {
                PrimitiveType pt = (PrimitiveType) type;

                PrimitiveType basePrimitiveType =
                        PrimitivesUtil.getBasePrimitiveType(pt);

                String name = PrimitivesUtil
                        .getFacetNameForDefaultValue(basePrimitiveType);

                if (name != null) {
                    Object defVal = PrimitivesUtil
                            .getFacetPropertyValue(pt, name, p, false);
                    boolean checkedType = false;
                    if (defVal == null || defVal.equals("")) {
                        // No default defined on the property itself,
                        // so see if it inherits one from its type.
                        defVal = PrimitivesUtil
                                .getFacetPropertyValue(pt, name, p, true);
                        checkedType = true;
                    }
                    if (defVal != null && !defVal.equals("")) {
                        // It has a default value
                        // First deal with the case where it is an imported
                        // restriction we only want the errors on imports in
                        // this instance
                        if (isImportedRestriction) {
                            if ((p.getLower() == 1) && (p.getUpper() == 1)) {
                                scope.createIssue(
                                        CDSIssueIds.RESTRICTION_VALUE_ATTRIBUTE_DEFAULT,
                                        BOMValidationUtil.getLocation(p),
                                        p.eResource().getURIFragment(p));
                            }
                        }
                        // If the attribute can take multiple values, a default
                        // makes no sense.
                        else if (p.getUpper() != 1) {
                            scope.createIssue(checkedType
                                    ? CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT_FROM_TYPE
                                    : CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT,
                                    BOMValidationUtil.getLocation(p),
                                    p.eResource().getURIFragment(p));

                        } else if (p.getLower() == 0) {
                            // If the attribute is optional, a default makes no
                            // sense, as it will _always_ apply, thus making it
                            // impossible to not specify the attribute.
                            scope.createIssue(checkedType
                                    ? CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT_FROM_TYPE
                                    : CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT,
                                    BOMValidationUtil.getLocation(p),
                                    p.eResource().getURIFragment(p));
                        }
                    }
                }
            }
        }
    }

}
