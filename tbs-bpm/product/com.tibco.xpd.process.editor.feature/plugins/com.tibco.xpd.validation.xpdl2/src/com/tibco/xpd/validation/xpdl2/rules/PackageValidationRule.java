/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.xpdl2.Package;

/**
 * Abstract base class for rules that operate on an XPDL2 Package.
 * 
 * @author nwilson
 */
public abstract class PackageValidationRule extends Xpdl2ValidationRule {

    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return Package.class;
    }

    /**
     * @param o
     *            The object to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     */
    public void validate(Object o) {
        if (o instanceof Package) {
            Package pckg = (Package) o;

            Destination dest = getScope().getCurrentDestination();
            String destinationId = dest.getId();

            // If destination is not selectable then it is always ON otherwise
            // validate only if current destination is selected in any process.
            if (!dest.isSelectable()
                    || DestinationUtil.isValidationDestinationEnabled(pckg,
                            destinationId)) {
                validate(pckg);
            }
        }
    }

    /**
     * @param pckg
     *            The package to validate.
     */
    public abstract void validate(Package pckg);
}
