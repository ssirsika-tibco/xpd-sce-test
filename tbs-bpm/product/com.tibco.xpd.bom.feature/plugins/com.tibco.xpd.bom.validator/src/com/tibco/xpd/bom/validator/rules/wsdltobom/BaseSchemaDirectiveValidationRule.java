/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdltobom;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsd.XSDSchemaDirective;

import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (11 Mar 2010)
 */
public abstract class BaseSchemaDirectiveValidationRule implements
        IValidationRule {

    private IValidationScope scope;

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    final public Class<?> getTargetClass() {
        return XSDSchemaDirective.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    final public void validate(IValidationScope scope, Object o) {
        this.scope = scope;
        if (o instanceof XSDSchemaDirective) {
            validateSchemaDirective((XSDSchemaDirective) o);
        }
    }

    /**
     * @param o
     */
    protected abstract void validateSchemaDirective(
            XSDSchemaDirective xsdSchemaDirective);

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     */
    protected final void addIssue(EObject o, String id, String location) {
        String uri = o.eResource().getURIFragment(o);
        scope.createIssue(id, location, uri);
    }

}
