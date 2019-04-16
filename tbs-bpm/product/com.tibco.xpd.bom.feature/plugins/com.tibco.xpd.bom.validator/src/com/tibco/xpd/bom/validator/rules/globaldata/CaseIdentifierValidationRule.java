/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * This rule validates the Auto/CaseIdentifier , currently validating for
 * mandatory multiplicity values.
 * 
 * @author aprasad
 * @since 30 May 2013
 */
public class CaseIdentifierValidationRule implements IValidationRule {



    /*
     * Sid ACE-470 cid.invalid.multiplicity.issue.message removed in favour of
     * ACE specific rules.
     */

    @Override
    public Class<?> getTargetClass() {
        return Property.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;
            List<String> additionalMessages = new ArrayList<String>();
            // Note: Auto CID extends CID so should be check first
            if (GlobalDataProfileManager.getInstance()
                    .isAutoCaseIdentifier(prop)) {
                /*
                 * AutoCID has only allowed multiplicity 0..1, show error
                 * otherwise
                 */
                if (!(prop.getUpper() == 1 && prop.getLower() == 0)) {
                    /*
                     * Sid ACE-470 cid.invalid.multiplicity.issue.message /
                     * auto.cid.invalid.multiplicity.issue.message removed in
                     * favour of ACE specific rules
                     */

                }
            } else if (GlobalDataProfileManager.getInstance().isCID(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isCompositeCaseIdentifier(prop)) {
                /*
                 * Case Identifiers are mandatory , show error when the
                 * multiplicity is not 1.
                 */
                if (!(prop.getUpper() == 1 && prop.getLower() == 1)) {

                    /*
                     * Sid ACE-470 cid.invalid.multiplicity.issue.message
                     * removed in favour of ACE specific rules.
                     */
                }
            }
        }
    }
}
