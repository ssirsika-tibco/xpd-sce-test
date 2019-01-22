/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule for DE deployment that checks for uniqueness of a
 * {@link Group} across the whole Organization Model.
 * 
 * @author njpatel
 * 
 */
public class UniqueGroupNameRule implements IValidationRule {

    public static final String NAME_ISSUE = "om.uniqueGroupName.issue"; //$NON-NLS-1$
    public static final String DISPLAYNAME_ISSUE = "om.uniqueGroupDisplayName.issue"; //$NON-NLS-1$

    public Class<?> getTargetClass() {
        return Group.class;
    }

    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Group) {
            Group targetGroup = (Group) obj;
            OrgModel model = OMUtil.getModel(targetGroup);
            if (model != null) {
                Collection<Group> groups = OMUtil.getAllGroups(model);
                if (groups != null) {
                    for (Group group : groups) {
                        // Don't report problem if siblings
                        if (targetGroup != group
                                && targetGroup.eContainer() != group
                                        .eContainer()) {
                            // Check for duplicate name
                            if (targetGroup.getName() != null
                                    && targetGroup.getName().equals(
                                            group.getName())) {
                                doCreateIssue(targetGroup, scope, NAME_ISSUE,
                                        targetGroup.getName());
                            }
                            // Check for duplicate display name
                            if (targetGroup.getDisplayName() != null
                                    && targetGroup.getDisplayName().equals(
                                            group.getDisplayName())) {
                                doCreateIssue(targetGroup, scope,
                                        DISPLAYNAME_ISSUE, targetGroup
                                                .getDisplayName());
                            }
                        }
                    }
                }
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
