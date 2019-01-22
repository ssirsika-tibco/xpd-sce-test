/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.validation.bpmn.rules.SubProcessRuntimeIdentifierRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * rule to validate 1. if a runtime identifier field is specified. 2. if it is
 * specified, then it must be text (can be array/non array if loop is specified)
 * 
 * 
 * @author bharge
 * @since 3.3 (19 Jul 2010)
 */
public class ReusableSubProc_RuntimeIdentifierRule extends
        SubProcessRuntimeIdentifierRule {

    private static final String RUNTIME_IDENTIFIER_FIELD_ISSUE =
            "bx.runtimeIdentifierFieldIssue"; //$NON-NLS-1$

    private static final String RUNTIME_IDENTIFIER_FOR_A_LOOP_ISSUE =
            "bx.runtimeIdentifierForALoopIssue"; //$NON-NLS-1$

    private static final String RUNTIME_IDENTIFIER_NOT_SPECIFIED =
            "bx.runtimeIdentifierNotSpecified"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.SubProcessRuntimeIdentifierRule#checkRuntimeIdentifier(java.lang.Object,
     *      com.tibco.xpd.xpdl2.Activity)
     * 
     * @param obj
     * @param activity
     */
    @Override
    protected void checkRuntimeIdentifier(Object obj, Activity activity) {
        if (obj instanceof String) {
            String identifierField = (String) obj;
            ProcessRelevantData prd =
                    getProcessRelevantData(identifierField, activity);
            if (null != prd && prd.getDataType() instanceof BasicType) {
                BasicType basicType = (BasicType) prd.getDataType();
                /*
                 * if no loop is defined then runtime identifier must be type
                 * text non array
                 */
                if (null == activity.getLoop()) {
                    if (!BasicTypeType.STRING_LITERAL.equals(basicType
                            .getType())
                            || prd.isIsArray()) {
                        addIssue(RUNTIME_IDENTIFIER_FIELD_ISSUE, activity);
                    }
                } else {
                    /*
                     * rti field must be text array/non-array in case of loop
                     * defined
                     */
                    if (!BasicTypeType.STRING_LITERAL.equals(basicType
                            .getType())) {
                        addIssue(RUNTIME_IDENTIFIER_FOR_A_LOOP_ISSUE, activity);
                    }
                }

            }
        } else if (obj == null) {
            /*
             * if a process interface is referenced then a String rti
             * must be specified
             */
            addIssue(RUNTIME_IDENTIFIER_NOT_SPECIFIED, activity);
        }
    }
}
