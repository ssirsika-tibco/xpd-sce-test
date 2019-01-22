/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;

/**
 * For a given project (and in the project references hierarchy) if two wsdls
 * conflicting with each other case sensitively are referenced in a xpdl file,
 * we need to flag an error. Looks for wsdls in Service Descriptors folder and
 * Generated Services folder and validates if the two wsdl names clash with each
 * other case sensitively
 * 
 * @author bharge
 * @since 10 Nov 2011
 */
public class WsdlNameRule extends PackageValidationRule {

    protected static final String ISSUE_WSDL_NAMES_CONFLICT =
            "bx.wsdlNamesConflictCaseInsensitively"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {

        if (null != pckg) {

            Collection<ActivityWebServiceReference> activityWebServiceReferences =
                    ProcessUIUtil.getActivityWebServiceReferences(pckg);

            List<String> wsdlFileNames = new ArrayList<String>();

            for (ActivityWebServiceReference activityWebServiceReference : activityWebServiceReferences) {
                wsdlFileNames.add(activityWebServiceReference
                        .getWsdlFileLocation());
            }

            Set<String> filteredWsdlNames = new HashSet<String>(wsdlFileNames);
            Set<String> duplicates = new HashSet<String>();

            for (String wsdlName : filteredWsdlNames) {
                for (String checkName : filteredWsdlNames) {
                    if (checkName != wsdlName) {
                        if (wsdlName.equalsIgnoreCase(checkName)
                                && !wsdlName.equals(checkName)) {
                            duplicates.add(wsdlName);
                        }
                    }
                }
            }

            if (duplicates.size() > 1) {
                for (String duplicateWsdlName : duplicates) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(duplicateWsdlName);
                    addIssue(ISSUE_WSDL_NAMES_CONFLICT, pckg, messages);
                }
            }

        }

    }
}
