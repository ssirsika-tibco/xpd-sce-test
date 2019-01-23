/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * @author rgreen
 * 
 */
public class UniqueDisplayNameRule implements IValidationRule {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse
     * .emf.validation.IValidationContext)
     */
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof NamedElement) {
            NamedElement target = (NamedElement) o;

            // Groups are slightly different to other OM NamedElements in that
            // the subGroups form an actual parent-child hierarchical
            // relationship (as opposed to flat for Capabilities, OrgUnits etc).
            if (target instanceof Group) {
                Group group = (Group) target;
                validateGroupName(scope, group);
                return;
            }

            NamedElement container = (NamedElement) target.eContainer();

            if (container != null) {
                Iterator<EObject> iter = container.eAllContents();

                while (iter.hasNext()) {
                    EObject childElem = iter.next();

                    if (childElem instanceof NamedElement
                            && childElem != target) {
                        NamedElement element = (NamedElement) childElem;

                        // At the moment orgUnitRelationships are not named so
                        // skip
                        if (element instanceof OrgUnitRelationship) {
                            continue;
                        }

                        if (element.eClass() != target.eClass()) {
                            continue;
                        }

                        if (element.getDisplayName().equals(
                                target.getDisplayName())) {
                            doCreateIssue(target, scope);
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
    private void validateGroupName(IValidationScope scope, Group targetGroup) {
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
                if (grp.getDisplayName().equals(targetGroup.getDisplayName())) {
                    doCreateIssue(targetGroup, scope);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public Class getTargetClass() {
        return NamedElement.class;
    }

    private void doCreateIssue(NamedElement target, IValidationScope scope) {
        String location = target.eClass().getName();
        String fragment = target.eResource().getURIFragment(target);
        List<String> additionalMessages = new ArrayList<String>();
        additionalMessages.add(target.getDisplayName());

        scope.createIssue(GenericIssueIds.DUPLICATE_DISPLAYNAME, location,
                fragment, additionalMessages);
    }

}
