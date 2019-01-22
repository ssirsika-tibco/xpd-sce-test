package com.tibco.bds.designtime.validator.rules;

import java.util.List;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class EmptyEnumerationRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return Enumeration.class;
    }

    public void validate(IValidationScope scope, Object obj) {
        
        if (obj instanceof Enumeration) {
            Enumeration enu = (Enumeration) obj;
            List<EnumerationLiteral> lits = enu.getOwnedLiterals();
            if (lits == null || lits.isEmpty()) {
                // No literals
                scope.createIssue(CDSIssueIds.EMPTY_ENUMERATION, enu 
                        .getQualifiedName(), enu.eResource()
                        .getURIFragment(enu));
                
            }
        }
        
    }

    
}
