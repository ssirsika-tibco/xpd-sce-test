/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule for checking for unique name and display name of Organization
 * Model {@link NamedElement}s.
 * 
 * @author rgreen, njpatel
 * 
 */
public class UniqueNameRule implements IValidationRule {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse
     * .emf.validation.IValidationContext)
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof NamedElement) {
            NamedElement target = (NamedElement) o;

            // Groups are slightly different to other OM NamedElements in that
            // the subGroups form an actual parent-child hierarchical
            // relationship (as opposed to flat for Capabilities, OrgUnits etc).
            if (target instanceof Group) {
                Group group = (Group) target;
                validateGroup(scope, group);
                return;
            }

            NamedElement container = (NamedElement) target.eContainer();

            if (container != null) {
                Iterator<EObject> iter = container.eAllContents();

                while (iter.hasNext()) {
                    EObject childElem = iter.next();

                    /*
                     * XPD-5300: Ignore the dynamic elements and also
                     * relationships.
                     */
                    if (childElem instanceof DynamicOrgUnit
                            || childElem instanceof DynamicOrgIdentifier
                            || childElem instanceof OrgUnitRelationship) {
                        continue;
                    }

                    if (childElem instanceof NamedElement
                            && childElem != target) {
                        NamedElement element = (NamedElement) childElem;

                        if (element.eClass() != target.eClass()) {
                            continue;
                        }

                        // Check for duplicate name
                        if (element.getName() != null
                                && element.getName().equals(target.getName())) {
                            doCreateIssue(target,
                                    scope,
                                    GenericIssueIds.DUPLICATE_NAME,
                                    target.getName());
                        }

                        if (element.getDisplayName() != null
                                && element.getDisplayName()
                                        .equals(target.getDisplayName())) {
                            doCreateIssue(target,
                                    scope,
                                    GenericIssueIds.DUPLICATE_DISPLAYNAME,
                                    target.getDisplayName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * The validation rule for Groups is slightly different to the other OM
     * NamedElements. Uniqueness is a requirement ONLY for siblings in the Group
     * hierarchy.
     * 
     * 
     * @param scope
     * @param targetGroup
     */
    private void validateGroup(IValidationScope scope, Group targetGroup) {
        if (targetGroup != null) {
            EObject container = targetGroup.eContainer();
            if (container instanceof Group) {
                // Get all the groups in this Group
                Group parentGroup = (Group) container;
                EList<Group> subGroups = parentGroup.getSubGroups();
                validateListOfGroups(subGroups, targetGroup, scope);

            } else if (container instanceof OrgModel) {
                OrgModel om = (OrgModel) container;
                EList<Group> groups = om.getGroups();
                validateListOfGroups(groups, targetGroup, scope);
            }
        }
    }

    /**
     * 
     * Check the target group against the groups in the List and create the
     * Issue if needed.
     * 
     * @param groups
     * @param targetGroup
     * @param scope
     */
    private void validateListOfGroups(EList<Group> groups, Group targetGroup,
            IValidationScope scope) {

        if (groups != null && targetGroup != null && scope != null) {
            for (Group grp : groups) {
                if (grp == targetGroup) {
                    continue;
                }

                // Check for duplicate name
                if (grp.getName() != null
                        && grp.getName().equals(targetGroup.getName())) {
                    doCreateIssue(targetGroup,
                            scope,
                            GenericIssueIds.DUPLICATE_NAME,
                            targetGroup.getName());
                }

                // Check for duplicate display name
                if (grp.getDisplayName() != null
                        && grp.getDisplayName()
                                .equals(targetGroup.getDisplayName())) {
                    doCreateIssue(targetGroup,
                            scope,
                            GenericIssueIds.DUPLICATE_DISPLAYNAME,
                            targetGroup.getDisplayName());
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
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
