/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdltobom;

import java.net.URI;

import org.eclipse.xsd.XSDSchemaDirective;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (11 Mar 2010)
 */
public abstract class AbstractAbsoluteSchemaLocationInvalidRule extends
        BaseSchemaDirectiveValidationRule {
    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    public void validateSchemaDirective(IValidationScope scope, Object o) {
        if (o instanceof XSDSchemaDirective) {
            XSDSchemaDirective xsdSchemaDirective = (XSDSchemaDirective) o;
            String schemaLocation = xsdSchemaDirective.getSchemaLocation();
            URI schemaLocURI = URI.create(schemaLocation);
            if (schemaLocURI.isAbsolute()) {
                String uri =
                        xsdSchemaDirective.eResource()
                                .getURIFragment(xsdSchemaDirective);
                scope.createIssue(getAbsolutePathInvalidIssueId(),
                        xsdSchemaDirective.getSchema().getTargetNamespace(),
                        uri);
            }

        }
    }

    /**
     * @see com.tibco.xpd.bom.validator.rules.wsdltobom.BaseSchemaDirectiveValidationRule#validateSchemaDirective(org.eclipse.xsd.XSDSchemaDirective)
     * 
     * @param o
     */
    @Override
    protected void validateSchemaDirective(XSDSchemaDirective xsdSchemaDirective) {
        String schemaLocation = xsdSchemaDirective.getSchemaLocation();
        if (schemaLocation != null) {
            try {
                URI schemaLocURI = URI.create(schemaLocation);
                // Check protocol
                if (schemaLocURI.isAbsolute()) {
                    addIssue(xsdSchemaDirective,
                            getAbsolutePathInvalidIssueId(),
                            xsdSchemaDirective.getSchema().getTargetNamespace());
                }
            } catch (Exception exception) {
                LOG.warn("Incorrect URI :->" + schemaLocation);
            }
        }

    }

    protected abstract String getAbsolutePathInvalidIssueId();

}
