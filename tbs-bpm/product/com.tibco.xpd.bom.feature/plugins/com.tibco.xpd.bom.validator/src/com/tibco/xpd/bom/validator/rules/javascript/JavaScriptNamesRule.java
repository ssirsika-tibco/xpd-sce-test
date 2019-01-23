package com.tibco.xpd.bom.validator.rules.javascript;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.JavaScriptReservedWords;
import com.tibco.xpd.bom.validator.JavaScriptIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class JavaScriptNamesRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        boolean needToCheck = false;
        if (o instanceof org.eclipse.uml2.uml.Class || o instanceof Operation) {
            // Check all Classes and Operations
            needToCheck = true;
        } else if (o instanceof Property) {
            // Check all properties except for those that are
            // the originating end of a uni-directional relationship.
            // These are invisible in the BOM diagram and would never
            // be used in scripts / mappings, so there seems no point
            // in forcing them to have JavaScript-friendly names.
            needToCheck = true;
            Property prop = (Property) o;
            AggregationKind agg = prop.getAggregation();
            Association ass = prop.getAssociation();
            if (agg == AggregationKind.NONE_LITERAL && ass != null) {
                EList<Property> owned = ass.getOwnedEnds();
                if (owned.size() == 1) {
                    // The fact it has one owned end appears to
                    // be an adequate check, but we'll check it's
                    // _this_ property as a sanity check:
                    if (owned.get(0) == prop) {
                        // This is the originating end of
                        // a uni-directional relationship, so we don't care
                        // about the name.
                        needToCheck = false;
                    }
                }
            }
        }

        if (needToCheck) {
            NamedElement elem = (NamedElement) o;
            if (JavaScriptReservedWords.getReservedWords()
                    .contains(elem.getName())) {
                scope.createIssue(JavaScriptIssueIds.NAME_IS_JAVASCRIPT_KEYWORD,
                        BOMValidationUtil.getLocation(elem),
                        elem.eResource().getURIFragment(elem),
                        Collections.singleton(elem.getName()));
            }
        }
    }
}
