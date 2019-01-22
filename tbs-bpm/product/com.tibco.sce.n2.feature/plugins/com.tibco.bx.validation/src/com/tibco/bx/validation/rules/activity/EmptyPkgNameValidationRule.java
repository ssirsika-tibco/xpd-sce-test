/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;

/**
 * Separated out from {@link EmptyNameValidationRule}. Package name needs to be
 * validated if any process within has destination env set (which will happen
 * for PackageValidaitonRule's when Xpdl2ScopeProvider is used)- however,
 * process/tasks should only be validated if the specific process has
 * destination set.
 * <p>
 * Easiest way to do this is to validate the process and tasks as a
 * ProcessValidationRule which I have changed {@link EmptyNameValidationRule}
 * to.
 * 
 * @author aallway
 * @since 21 Aug 2012
 */
public class EmptyPkgNameValidationRule extends PackageValidationRule {

    private static final String PACKAGE_NAME_BLANK_ID =
            "bx.processPackageNameBlank"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {
        String name = pckg.getName();

        if (name != null && name.length() == 0) {
            addIssue(PACKAGE_NAME_BLANK_ID, pckg);
        }
    }
}
