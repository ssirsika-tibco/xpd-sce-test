package com.tibco.xpd.bom.validator.test;

import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class TestValidationProviderExtensionRule  implements IValidationRule {

    static public boolean wasCalled = false;
    
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof org.eclipse.uml2.uml.Class) {
            wasCalled = true;
        }

    }

}
