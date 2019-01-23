package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates against the presence of Searchable attributes in Local classes
 * 
 * @author patkinso
 * @since 9 Oct 2013
 */
public class SearchableRestrictionRule implements IValidationRule {

    private final String ISSUE_ID =
            "bom.globaldata.class.local.searchable.restricted.issue"; //$NON-NLS-1$

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

        if (gdManager.isLocal(clazz)) {
            boolean searchablePresent = false;

            for (Iterator<Property> it = clazz.getOwnedAttributes().iterator(); it
                    .hasNext() && !searchablePresent;) {
                Property prop = it.next();
                boolean isCaseIdentifier =
                        gdManager.isCID(prop)
                                || gdManager.isAutoCaseIdentifier(prop);
                searchablePresent =
                        gdManager.isSearchable(prop) && !isCaseIdentifier;
            }

            if (searchablePresent) {
                scope.createIssue(ISSUE_ID, clazz.eClass().getName(), clazz
                        .eResource().getURIFragment(clazz));
            }
        } else {
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
                                clazz.eClass().getName(),
                                property.eResource().getURIFragment(property),
                                Collections.singletonList(displayName));
                    }
                }
            }
        }
    }

}
