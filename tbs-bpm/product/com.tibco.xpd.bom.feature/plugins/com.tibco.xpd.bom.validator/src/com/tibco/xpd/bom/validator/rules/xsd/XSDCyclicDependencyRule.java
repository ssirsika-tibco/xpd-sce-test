/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.xsd;

import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.rules.generic.CyclicDependencyRule;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * 
 * Specific cyclic dependency rule for the XSD validator that will create an
 * error level issue
 * 
 * @author rgreen
 * 
 */
public class XSDCyclicDependencyRule extends CyclicDependencyRule {

    @Override
    protected void createIssue(IValidationScope scope, String name,
            String fragment) {
        scope.createIssue(XsdIssueIds.XSD_CYCLIC_DEPENDENCY, name, fragment);
    }

}
