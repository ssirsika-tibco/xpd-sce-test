/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 * 
 * 
 */

package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * All features should be typed
 * 
 * @author rgreen
 * 
 */
public class AttributeDefaultValueRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return Attribute.class;
    }

    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Attribute) {
            Attribute attr = (Attribute) o;

            if (attr.getDefaultValue() != null) {
                List<String> additionalMessages = new ArrayList<String>();
                additionalMessages.add(attr.getDisplayName());

                createOUIssue(scope,
                        attr,
                        GenericIssueIds.ATTRIBUTE_DEFAULT_VALUE,
                        additionalMessages);
            }
        }
    }

    private void createOUIssue(IValidationScope scope, NamedElement ote,
            String issueId, List<String> messages) {
        scope.createIssue(issueId, ote.getDisplayName(), ote.eResource()
                .getURIFragment(ote), messages);
    }

}
