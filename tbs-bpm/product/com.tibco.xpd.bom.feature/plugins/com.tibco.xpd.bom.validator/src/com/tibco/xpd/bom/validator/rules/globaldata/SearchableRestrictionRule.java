package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.Collections;

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates against the presence of Searchable attributes in Local classes
 * 
 * @author patkinso
 * @since 9 Oct 2013
 */
public class SearchableRestrictionRule implements IValidationRule {

    /*
     * Sid ACE-470 globaldataBOM.class.local.searchable.restricted.issue removed
     * in favour of ACE specific rule.
     */

    private final String ISSUE_ID_UNSUPPORTED_TYPE =
            "bom.globaldata.attribute.searchable.typerestriction.issue"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) o;
        GlobalDataProfileManager gdManager =
                GlobalDataProfileManager.getInstance();

		/*
		 * Nikita ACE-7540 globaldataBOM.class.local.searchable.restricted.issue should added to ALL classes
		 * Removed the check to skip local classes
		 */
        // Now check all of the search-able attributes to make sure that
        // none of them are of type class, as you cannot have a Class, Case
        // or Global that is search-able
        for (Property property : clazz.getOwnedAttributes()) {
            if (gdManager.isSearchable(property)) {
                Type type = property.getType();
                if (type instanceof org.eclipse.uml2.uml.Class) {
                    String displayName =
                            PrimitivesUtil.getDisplayLabel(type);
                    scope.createIssue(ISSUE_ID_UNSUPPORTED_TYPE,
                            BOMValidationUtil.getLocation(clazz),
                            property.eResource().getURIFragment(property),
                            Collections.singletonList(displayName));
                }
            }
        }
    }

}
