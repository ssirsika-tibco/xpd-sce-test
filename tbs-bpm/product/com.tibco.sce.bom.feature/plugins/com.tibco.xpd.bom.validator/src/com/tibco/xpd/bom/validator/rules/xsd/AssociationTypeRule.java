package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * An XSD validation rule that enforces that only uni-directional Composition
 * relationships are allowed.
 * 
 * @author glewis
 * 
 */
public class AssociationTypeRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return Association.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        boolean raiseIssue = false;

        if (o instanceof Association) {
            Association ass = (Association) o;
            // Don't enforce this rule if it's a global data BOM.
            // For now, we allow any relationship there. Later, we'll
            // implement more sophisticated validation that enforces
            // global data's own rules about relationships and their
            // participants.
            Model model = ass.getModel();
            boolean isGlobalDataBOM =
                    (model != null && BOMGlobalDataUtils
                            .hasGlobalDataProfile(model));
            if (!isGlobalDataBOM) {
                // Enforce rule that bi-directional associations are not allowed
                if (UML2ModelUtil.isAssociationBiDirectional(ass)) {
                    // Relationship is bi-directional, so we need to raise an
                    // issue
                    raiseIssue = true;
                } else {
                    // Relationship is unidirectional, but may be of an
                    // unsupported kind
                    AggregationKind kind =
                            UML2ModelUtil.getAggregationType(ass);
                    if (kind != AggregationKind.COMPOSITE_LITERAL) {
                        // Relationship is a non-composition association, so
                        // we need to raise an issue
                        raiseIssue = true;
                    }
                }

                if (raiseIssue) {
                    scope.createIssue(XsdIssueIds.UNSUPPORTED_RELATIONSHIP,
                            ass.getQualifiedName(),
                            ass.eResource().getURIFragment(ass));
                }
            }
        }
    }
}
