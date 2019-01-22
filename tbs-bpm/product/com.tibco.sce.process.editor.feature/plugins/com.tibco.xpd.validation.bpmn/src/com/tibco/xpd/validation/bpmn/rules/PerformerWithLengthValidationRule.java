/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (8 Jan 2010)
 */
public class PerformerWithLengthValidationRule extends PackageValidationRule {

    private static final String ISSUE_PERFORMER_WITH_LENGTH =
            "bpmn.performerDataWithLength"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        Collection<ProcessRelevantData> allDataInPackage =
                ProcessInterfaceUtil.getAllDataInPackage(pckg);

        for (ProcessRelevantData data : allDataInPackage) {
            if (data.getDataType() instanceof BasicType) {
                BasicType basicType = (BasicType) data.getDataType();

                if (BasicTypeType.PERFORMER_LITERAL.equals(basicType.getType())) {
                    if (basicType.getLength() != null
                            || basicType.getPrecision() != null
                            || basicType.getScale() != null) {
                        addIssue(ISSUE_PERFORMER_WITH_LENGTH, data);
                    }
                }
            }
        }

        return;
    }

}
