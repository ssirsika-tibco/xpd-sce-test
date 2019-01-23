package com.tibco.bds.designtime.validator.rules;

import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
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

    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Property.class;
    }

    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Property) {
            Property p = (Property) o;

            boolean isImportedRestriction = false;
            // Check if this is an imported type, if so we do not want to
            // apply this validation rule
            if (BOMUtils.getProfileApplied(p.getClass_(),
                    XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME) != null) {
                // If it is created from an imported schema, then it may have
                // restrictions of other complex types, these need to be checked
                // to see if there is a default value which is on a non optional
                // attribute
                isImportedRestriction =
                        XSDUtil.isClassXsdComplexTypeRestriction(p.getClass_());
                if (isImportedRestriction != true) {
                    return;
                }
            }

            Type type = p.getType();

            if (type instanceof PrimitiveType) {
                PrimitiveType pt = (PrimitiveType) type;

                PrimitiveType basePrimitiveType =
                        PrimitivesUtil.getBasePrimitiveType(pt);

                String name =
                        PrimitivesUtil
                                .getFacetNameForDefaultValue(basePrimitiveType);

                if (name != null) {
                    Object defVal =
                            PrimitivesUtil.getFacetPropertyValue(pt,
                                    name,
                                    p,
                                    false);
                    boolean checkedType = false;
                    if (defVal == null || defVal.equals("")) {
                        // No default defined on the property itself,
                        // so see if it inherits one from its type.
                        defVal =
                                PrimitivesUtil.getFacetPropertyValue(pt,
                                        name,
                                        p,
                                        true);
                        checkedType = true;
                    }
                    if (defVal != null && !defVal.equals("")) {
                        // It has a default value
                        // First deal with the case where it is an imported
                        // restriction we only want the errors on imports in
                        // this instance
                        if (isImportedRestriction) {
                            if ((p.getLower() == 1) && (p.getUpper() == 1)) {
                                scope
                                        .createIssue(CDSIssueIds.RESTRICTION_VALUE_ATTRIBUTE_DEFAULT,
                                                p.getQualifiedName(),
                                                p.eResource().getURIFragment(p));
                            }
                        }
                        // If the attribute can take multiple values, a default
                        // makes no sense.
                        else if (p.getUpper() != 1) {
                            scope
                                    .createIssue(checkedType ? CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT_FROM_TYPE
                                            : CDSIssueIds.MULTIPLE_VALUE_ATTRIBUTE_DEFAULT,
                                            p.getQualifiedName(),
                                            p.eResource().getURIFragment(p));

                        } else if (p.getLower() == 0) {
                            // If the attribute is optional, a default makes no
                            // sense, as it will _always_ apply, thus making it
                            // impossible to not specify the attribute.
                            scope
                                    .createIssue(checkedType ? CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT_FROM_TYPE
                                            : CDSIssueIds.OPTIONAL_ATTRIBUTE_DEFAULT,
                                            p.getQualifiedName(),
                                            p.eResource().getURIFragment(p));
                        }
                    }
                }
            }
        }
    }

}
