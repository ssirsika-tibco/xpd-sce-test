/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule that checks if a ResourceType is marked as many special
 * ResourceTypes (for example: Human and Consumable).
 * 
 * @author Jan Arciuchiewicz
 * @since 3.3
 */
public class MutuallyExclusiveSpecialResourcTypesRule implements
        IValidationRule {

    public static Collection<EReference> SPECIAL_RESOURCE_TYPE_FEATURES =
            Arrays.asList(OMPackage.Literals.ORG_MODEL__HUMAN_RESOURCE_TYPE,
                    OMPackage.Literals.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE,
                    OMPackage.Literals.ORG_MODEL__DURABLE_RESOURCE_TYPE);

    public static final String MANY_SPECIAL_RESOURCE_TYPES_ISSUE =
            "om.markedAsManySpecialResourceType.issue"; //$NON-NLS-1$

    public Class<?> getTargetClass() {
        return OrgModel.class;
    }

    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof OrgModel) {
            OrgModel targetOM = (OrgModel) obj;
            Map<ResourceType, List<EReference>> markedSpecialTypes =
                    new LinkedHashMap<ResourceType, List<EReference>>();
            for (EReference resTypeFeature : SPECIAL_RESOURCE_TYPE_FEATURES) {
                ResourceType resType =
                        (ResourceType) targetOM.eGet(resTypeFeature);
                if (resType != null) {
                    if (!markedSpecialTypes.containsKey(resType)) {
                        markedSpecialTypes.put(resType,
                                new ArrayList<EReference>());
                    }
                    markedSpecialTypes.get(resType).add(resTypeFeature);
                }
            }
            for (ResourceType resType : markedSpecialTypes.keySet()) {

                if (markedSpecialTypes.get(resType).size() > 1) {
                    doCreateIssue(resType,
                            scope,
                            MANY_SPECIAL_RESOURCE_TYPES_ISSUE,
                            targetOM.getDisplayName());
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
