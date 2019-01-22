/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Org Query is not supported in N2.
 * 
 * @author rsomayaj
 * 
 */
public class OrgModelQueryUnsupportedRule implements IValidationRule {

    // ORG QUERIES ARE NOT SUPPORTED FOR N2
    private static final String ORGQUERY_UNSUPPORTED =
            "om.orgQueryUnsupported.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    public Class<?> getTargetClass() {
        return OrgModel.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof OrgModel) {
            OrgModel orgModel = (OrgModel) o;
            List<OrgQuery> orgQueries = orgModel.getQueries();
            for (OrgQuery orgQuery : orgQueries) {
                doCreateIssue(orgQuery, scope, ORGQUERY_UNSUPPORTED, orgQuery
                        .getDisplayName());
            }

        }
    }

    /**
     * Raise the given issue against the target.
     * 
     * @param target
     * @param scope
     * @param issueId
     * @param targetName
     *            the Name or Display Name to set in the error message.
     */
    private void doCreateIssue(NamedElement target, IValidationScope scope,
            String issueId, String targetName) {
        String location = target.getName();
        String fragment = target.eResource().getURIFragment(target);
        List<String> additionalMessages = new ArrayList<String>();
        additionalMessages.add(targetName);

        scope.createIssue(issueId, location, fragment, additionalMessages);
    }
}
