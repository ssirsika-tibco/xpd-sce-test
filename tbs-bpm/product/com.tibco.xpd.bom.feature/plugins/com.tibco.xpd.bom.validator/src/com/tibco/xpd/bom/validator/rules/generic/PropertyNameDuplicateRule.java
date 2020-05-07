/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate if Concept name has valid name.
 * 
 * Note:<br>
 * Validator to ensure no Duplicate names within a UML Package
 * 
 * @author rsomayaj
 */
public class PropertyNameDuplicateRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof org.eclipse.uml2.uml.Class && !(o instanceof AssociationClass)) {
            org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) o;
            List<NamedElement> ownedAttributes = new ArrayList<NamedElement>();
            ownedAttributes.addAll(clazz.getOwnedAttributes());
            ownedAttributes.addAll(clazz.getOwnedOperations());
            List<NamedElement> duplicateAttributes = new ArrayList<NamedElement>();

            Set<String> set = new HashSet<String>();
            boolean checkDuplicates;
            for (NamedElement attribute : ownedAttributes) {
                checkDuplicates = set.add(attribute.getName());
                if (!checkDuplicates)
                    duplicateAttributes.add(attribute);
            }

            if (!duplicateAttributes.isEmpty()) {
                for (NamedElement attribute : duplicateAttributes) {
                    /*
                     * Sid ACE-3626 Duplicate nam rule went missing because we're passing getLocation RATHER than name
                     * and we changed getLocation() to return attribute label not name.
                     */
                    createIssues(scope,
                            attribute.getName(),
                            ownedAttributes);
                }
            }
        }
    }

    /**
     * @param scope
     * @param name
     * @param ownedAttributes
     */
    private void createIssues(IValidationScope scope, String name,
            List<NamedElement> ownedAttributes) {
        
        List<String> additionalMessages = new ArrayList<String>();
        if (!ownedAttributes.isEmpty()){
            EObject container = ownedAttributes.get(0).eContainer();
            if (container instanceof NamedElement){
                additionalMessages.add(((NamedElement) container).getName());
            }
        }
        
        for (NamedElement attribute : ownedAttributes) {
            if (attribute.getName().equals(name)) {
                scope.createIssue(GenericIssueIds.PROPERTY_NAME_DUPLICATE,
                        BOMValidationUtil.getLocation(attribute),
                        attribute.eResource()
                                .getURIFragment(attribute), additionalMessages);
            }
        }
    }
}
