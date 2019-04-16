/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.Iterator;

import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates against the presence of Case Identifiers in non-Case classes
 * 
 * @author patkinso
 * @since 8 Oct 2013
 */
public class CaseIdAttributeRestrictionRule implements IValidationRule {

    private static final String LOCAL_ISSUE_ID =
            "bom.globaldata.class.local.caseidentifier.restricted.issue"; //$NON-NLS-1$

    private static final String GLOBAL_ISSUE_ID =
            "bom.globaldata.class.global.caseidentifier.restricted.issue"; //$NON-NLS-1$

    private static final String GENERALIZATION_ISSUE_ID =
            "bom.globaldata.class.case.caseidentifier.generalization.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) o;
        GlobalDataProfileManager gdManager =
                GlobalDataProfileManager.getInstance();

        // Check if this class has a case identifier in it
        boolean caseIdPresent = false;
        for (Iterator<Property> it = clazz.getOwnedAttributes().iterator(); it
                .hasNext() && !caseIdPresent;) {
            Property prop = it.next();
            caseIdPresent =
                    gdManager.isCID(prop)
                            || gdManager.isAutoCaseIdentifier(prop)
                            || gdManager.isCompositeCaseIdentifier(prop);
        }

        if (caseIdPresent) {
            if (gdManager.isGlobal(clazz) || gdManager.isLocal(clazz)) {
                String issue_id =
                        gdManager.isGlobal(clazz) ? GLOBAL_ISSUE_ID
                                : LOCAL_ISSUE_ID;
                scope.createIssue(issue_id,
                        BOMValidationUtil.getLocation(clazz),
                        clazz
                        .eResource().getURIFragment(clazz));
            } else if (gdManager.isCase(clazz)) {
                // If this class is a Case class then we need to ensure that it
                // enforces the rule that the case identifiers must only exist
                // in the base class. This is due to the fact that it will get a
                // unique constraint in the database when it is created at
                // runtime, and creating instances of the base class only will
                // result in nulls being inserted into the DB - which some DBs
                // (Oracle) will not allow more than a single null due to the
                // unique constraint
                if (!clazz.getGenerals().isEmpty()) {
                    scope.createIssue(GENERALIZATION_ISSUE_ID,
                            BOMValidationUtil.getLocation(clazz),
                            clazz.eResource().getURIFragment(clazz));
                }
            }
        }
    }
}
