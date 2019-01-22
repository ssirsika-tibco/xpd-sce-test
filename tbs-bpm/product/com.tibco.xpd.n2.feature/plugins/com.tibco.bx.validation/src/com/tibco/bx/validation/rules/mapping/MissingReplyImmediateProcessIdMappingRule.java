/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractProcessDataToWebSvcRule;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate that a reply immediate start event has a mapping from the [Process
 * Id] in the OutPut-Process-Id tab.
 * <p>
 * We will nto actually perform the mapping validation (that is done via
 * {@link AbstractProcessDataToWebSvcRule}). But in the circumstance that the
 * Output Message part is all optional then if the user didn't make a mapping
 * then there would be no problem marker. Butthe user should make a mapping to
 * output part for reply-immedaite with process id otherwise it would probably
 * cause problems in the process engine.
 * 
 * @author aallway
 * @since 28 Aug 2012
 */
public class MissingReplyImmediateProcessIdMappingRule extends
        ProcessActivitiesValidationRule {

    private static final String ISSUE_MISSING_PROCESS_ID_MAPPING =
            "bx.replyImmediateMissingProcessIdMapping"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            /* Output Process-Id from process = DirectionType.IN to WSDL data. */
            List<DataMapping> dataMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);

            boolean hasProcessIdMapping = false;

            for (DataMapping dataMapping : dataMappings) {
                if (isProcessIdMapping(dataMapping)) {
                    hasProcessIdMapping = true;
                    break;
                }
            }

            if (!hasProcessIdMapping) {
                addIssue(ISSUE_MISSING_PROCESS_ID_MAPPING, activity);
            }
        }

    }

    /**
     * @param dataMapping
     * @return true if the is a [Process Id] mapping.
     */
    private boolean isProcessIdMapping(DataMapping dataMapping) {
        Expression actual = dataMapping.getActual();
        if (actual != null) {
            if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                    .getName().equals(actual.getText())) {
                return true;
            }
        }
        return false;
    }
}
