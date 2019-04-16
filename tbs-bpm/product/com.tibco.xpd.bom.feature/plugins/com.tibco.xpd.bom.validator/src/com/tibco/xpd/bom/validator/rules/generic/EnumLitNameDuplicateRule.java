package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class EnumLitNameDuplicateRule implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Enumeration.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Enumeration) {
            Enumeration en =  (Enumeration) o;
            List<EnumerationLiteral> ownedLits = new ArrayList<EnumerationLiteral>();

            ownedLits.addAll(en.getOwnedLiterals());
            List<EnumerationLiteral> duplicateLits = new ArrayList<EnumerationLiteral>();

            Set<String> set = new HashSet<String>();
            boolean checkDuplicates;
            for (EnumerationLiteral enLit : ownedLits) {
                checkDuplicates = set.add(enLit.getName());
                if (!checkDuplicates)
                    duplicateLits.add(enLit);
            }

            if (!duplicateLits.isEmpty()) {
                for (EnumerationLiteral enLit : duplicateLits) {
                    createIssues(scope,
                            BOMValidationUtil.getLocation(enLit),
                            ownedLits);
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
            List<EnumerationLiteral> ownedLits) {
        
        List<String> additionalMessages = new ArrayList<String>();
        
        if (!ownedLits.isEmpty()){
            additionalMessages.add(ownedLits.get(0).getEnumeration().getName());
        }
        
        for (EnumerationLiteral enLit : ownedLits) {
            if (enLit.getName().equals(name)) {                
                scope.createIssue(GenericIssueIds.ENUMLIT_NAME_DUPLICATE,
                        BOMValidationUtil.getLocation(enLit),
                        enLit.eResource()
                                .getURIFragment(enLit), additionalMessages);
            }
        }
    }

}
