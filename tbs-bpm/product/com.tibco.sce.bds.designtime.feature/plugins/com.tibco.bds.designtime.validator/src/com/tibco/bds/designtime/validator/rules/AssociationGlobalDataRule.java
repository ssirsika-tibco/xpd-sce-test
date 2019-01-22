package com.tibco.bds.designtime.validator.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Handles all validations for Associations in a global data BOM
 * 
 */
public class AssociationGlobalDataRule implements IValidationRule {

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    public Class<?> getTargetClass() {
        return Association.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Association) {
            Association association = (Association) o;

            // Make sure that it is not a Composition
            if (UML2ModelUtil.getAggregationType(association) != AggregationKind.COMPOSITE_LITERAL) {

                if (!UML2ModelUtil.isAssociationBiDirectional(association)) {
                    // Association and aggregation must be Bidirectional
                    // Make sure we only display this validation error for
                    // global data BOMs
                    Model model = association.getModel();
                    if (BOMGlobalDataUtils.isGlobalDataBOM(model)) {
                        scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_ASSOCIATION_BIDIRECTIONAL,
                                association.getQualifiedName(),
                                association.eResource()
                                        .getURIFragment(association));
                        return;
                    }
                }

                // Get the classes at each end of the association
                for (Property property : association.getMemberEnds()) {
                    // Check to ensure that a property is not a 1..1
                    // multiplicity
                    if (property.getLower() > 0) {

                        // Before raising a validation error need to check to
                        // see if this is linked to a Global Data class type
                        org.eclipse.uml2.uml.Class class_ =
                                getClassFromAssociationProperty(property);

                        if ((class_ != null)
                                && ((BOMGlobalDataUtils.isCaseClass(class_) || BOMGlobalDataUtils
                                        .isGlobalClass(class_)))) {
                            scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_ASSOCIATION_MANDATORY,
                                    association.getQualifiedName(),
                                    association.eResource()
                                            .getURIFragment(association));
                            return; // Only want one error
                        }
                    }
                }

                // Need to check to ensure that we do not have an association to
                // ourself as this is not supported
                EList<Property> memberEnds = association.getMemberEnds();
                if ((memberEnds != null) && (memberEnds.size() > 1)) {
                    org.eclipse.uml2.uml.Class class1 =
                            getClassFromAssociationProperty(memberEnds.get(0));
                    org.eclipse.uml2.uml.Class class2 =
                            getClassFromAssociationProperty(memberEnds.get(1));

                    if (class1 == class2) {
                        // There are already validations to ensure associations
                        // are for case classes only, so before creating the
                        // error make sure we are dealing with Case Classes
                        // Just check one as we know they are the same
                        if (BOMGlobalDataUtils.isCaseClass(class1)) {
                            scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_ASSOCIATION_SELFREFERENCE,
                                    association.getQualifiedName(),
                                    association.eResource()
                                            .getURIFragment(association));
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the class from the property on either end of the association
     * 
     * @param property
     *            Property to get the class from
     * @return
     */
    private org.eclipse.uml2.uml.Class getClassFromAssociationProperty(
            Property property) {
        org.eclipse.uml2.uml.Class class_ = property.getClass_();

        // Check for the case where we deal with the source of
        // the association
        if (class_ == null) {
            Type type = property.getType();
            if ((type != null) && (type instanceof org.eclipse.uml2.uml.Class)) {
                class_ = (org.eclipse.uml2.uml.Class) type;
            }
        }
        return class_;
    }
}
