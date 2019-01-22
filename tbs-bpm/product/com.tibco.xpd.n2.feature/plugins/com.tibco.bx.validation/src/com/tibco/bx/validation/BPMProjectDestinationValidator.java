package com.tibco.bx.validation;

import com.tibco.xpd.destinations.validation.AbstractRequiredDestinationsValidator;
import com.tibco.xpd.n2.resources.util.N2Utils;

/**
 * Validates projects in a workspace to check if the BPM destination is
 * available on a project or its referenced project(s).
 * 
 * @author TIBCO Software Inc.
 * @since UNKNOWN
 */
public class BPMProjectDestinationValidator extends
        AbstractRequiredDestinationsValidator {

    private static final String ISSUE_REQUIRED_DESTINATION =
            "bx.requiredBPMDestination"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.destinations.validation.AbstractRequiredDestinationsValidator#getRequiredDestinationId()
     * 
     * @return
     */
    @Override
    protected String getRequiredDestinationId() {
        return N2Utils.N2_GLOBAL_DESTINATION_ID;
    }

    /**
     * @see com.tibco.xpd.destinations.validation.AbstractRequiredDestinationsValidator#getIssueId()
     * 
     * @return
     */
    @Override
    protected String getIssueId() {
        return ISSUE_REQUIRED_DESTINATION;
    }

}
