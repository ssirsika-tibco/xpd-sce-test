/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to disallow formal parameters of Case Ref types if there are any
 * incoming request activities in a process and the wsdl referenced by the
 * incoming request activity is studio generated wsdl
 * 
 * @author bharge
 * @since 9 Oct 2013
 */
public class CaseRefTypesInPaaSRule extends ProcessActivitiesValidationRule {

    private static final String CASE_REF_FORMAL_PARAM_ISSUE =
            "bx.caseRefFormalParamIssue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

            Collection<ActivityInterfaceData> interfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);

            for (ActivityInterfaceData ifcData : interfaceData) {

                ProcessRelevantData data = ifcData.getData();
                if (data.getDataType() instanceof RecordType) {

                    addIssue(CASE_REF_FORMAL_PARAM_ISSUE,
                            activity,
                            Collections.singletonList(Xpdl2ModelUtil
                                    .getDisplayNameOrName(data)));
                }
            }

        }

    }

}
