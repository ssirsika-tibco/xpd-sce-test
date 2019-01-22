package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.uml2.uml.AssociationClass;

import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * An XSD validation rule that enforces that Association Classes prevent the transformation from BOM to XSD / WSDL.
 * 
 * @author glewis
 * 
 */
public class AssociationClassTypeRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return AssociationClass.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof AssociationClass) {
        	AssociationClass associationClass = (AssociationClass) o;
            scope.createIssue(XsdIssueIds.ASSOCIATION_CLASS_UNSUPPORTED, associationClass
                    .getQualifiedName(), associationClass.eResource()
                    .getURIFragment(associationClass));
            
        }
    }
}
