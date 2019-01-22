/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdltobom;

import java.net.URI;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Import;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Rule to validate the wsdl import schema location. We allow only relative
 * schema location and discourage the usage of absolute schema location.
 * 
 * @author kthombar
 * @since 16-Feb-2014
 */
public abstract class AbstractWsdlAbsoluteSchemaLocationInvalidRule implements
        IValidationRule {

    private IValidationScope scope;

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {

        return org.eclipse.wst.wsdl.Import.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    final public void validate(IValidationScope scope, Object o) {
        this.scope = scope;
        if (o instanceof Import) {
            validateWsdlImportSchemaLocation((Import) o);
        }
    }

    /**
     * checks if the schema location provided(is absolute or relative). If the
     * schema location is absolute then raise error marker.
     * 
     * @param wsdlSchema
     */
    protected void validateWsdlImportSchemaLocation(Import wsdlSchema) {
        String schemaLocation = wsdlSchema.getLocationURI();
        try {
            URI schemaLocURI = URI.create(schemaLocation);

            if (schemaLocURI.isAbsolute()) {
                /* If absolute, add issue */
                addIssue(wsdlSchema,
                        getAbsolutePathInvalidIssueId(),
                        schemaLocation);
            }
        } catch (Exception exception) {
            XpdResourcesPlugin.getDefault().getLogger()
                    .warn("Incorrect URI :->" + schemaLocation);
        }
    }

    /**
     * gets the issue id
     * 
     * @return
     */
    abstract protected String getAbsolutePathInvalidIssueId();

    /**
     * Add the issue.
     * 
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
