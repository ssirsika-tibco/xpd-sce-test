/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.rules.BOMValidationRule;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Abstract base class for XSD validations of BOM elements
 * 
 * @author glewis
 */
public abstract class AbstractXsdRule extends BOMValidationRule {

    /** */
    public static final String XSD_NAMES_VALIDATION = "XsdNamesValidation"; //$NON-NLS-1$

    public abstract Class<?> getTargetClass();

    public void validate(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }
        performXSDValidation(scope, o);
    }

    public abstract void performXSDValidation(IValidationScope scope, Object o);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.validator.rules.BOMValidationRule#getPluginContributon
     * ()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return new IPluginContribution() {
            public String getLocalId() {
                return XSD_NAMES_VALIDATION;
            }

            public String getPluginId() {
                return BOMValidatorActivator.PLUGIN_ID;
            }
        };
    }
}
