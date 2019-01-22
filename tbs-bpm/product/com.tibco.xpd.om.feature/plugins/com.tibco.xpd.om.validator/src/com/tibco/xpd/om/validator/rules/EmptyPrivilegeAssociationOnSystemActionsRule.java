/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.om.validator.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * This rule validates against any 'empty' privilege association on system
 * actions.
 * 
 * @author sajain
 * @since Mar 5, 2014
 */
public class EmptyPrivilegeAssociationOnSystemActionsRule implements
        IValidationRule {

    private static final String ISSUE_EMPTY_PRIVILEGE_ASSOCIATIONS =
            "om.issueEmptyPrivilegeAssociationsOnSystemActions"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return EObject.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Authorizable && ((EObject) obj).eResource() != null) {
            Authorizable authrzbl = (Authorizable) obj;
            boolean needToCreateIssue = false;
            /*
             * Fetch all system actions from the authorizable.
             */
            EList<SystemAction> sysActions = authrzbl.getSystemActions();
            for (SystemAction eachSysAction : sysActions) {
                /*
                 * Fetch all privilege associations of current system action.
                 */
                EList<PrivilegeAssociation> privAssocs =
                        eachSysAction.getPrivilegeAssociations();
                for (PrivilegeAssociation eachPrivAssoc : privAssocs) {
                    /*
                     * Check if the current privilege association is an empty
                     * privilege association i.e., a privilege association
                     * without any privilege.
                     */
                    if (eachPrivAssoc.getPrivilege() == null) {
                        /*
                         * If it is, then we need to create an issue.
                         */
                        needToCreateIssue = true;
                        break;
                    }
                }
            }
            if (needToCreateIssue) {
                scope.createIssue(ISSUE_EMPTY_PRIVILEGE_ASSOCIATIONS,
                        ((NamedElement) authrzbl).getDisplayName(),
                        authrzbl.eResource().getURIFragment(authrzbl));
            }
        }
    }
}
