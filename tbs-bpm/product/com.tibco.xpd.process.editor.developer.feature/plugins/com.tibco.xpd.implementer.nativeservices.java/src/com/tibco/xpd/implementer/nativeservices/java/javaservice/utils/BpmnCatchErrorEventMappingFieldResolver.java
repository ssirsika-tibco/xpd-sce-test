/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Script process field resolver for catch error events that either Catch-All or
 * catch error thrown by sub-process errors.
 * 
 * @author aallway
 * @since 4 Sep 2012
 */
public class BpmnCatchErrorEventMappingFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        return Messages.BpmnCatchErrorEventMappingFieldResolver_MapFromError_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        return Messages.BpmnCatchErrorEventMappingFieldResolver_MapFromError_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        if (EventObjectUtil.isAttachedToTask(activity)) {
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                    .getEventTriggerType(activity))) {
                ErrorCatchType catchType =
                        BpmnCatchableErrorUtil.getCatchType(activity);

                /* We will deal with catch unspecific (ALL or By Name only) */
                if (ErrorCatchType.CATCH_ALL.equals(catchType)
                        || ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
                    return true;

                } else {
                    /* And specific sub-process throw end error events */
                    if (BpmnCatchableErrorUtil
                            .isCatchSubProcessErrorEvent(activity)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getActivityScriptInformationList(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil.getAllEventScriptInformations(activity,
                getGrammarType());

    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getTaskName()
     * 
     * @return
     */
    @Override
    protected String getTaskName() {
        return ProcessJsConsts.SUBPROCESS_TASK;
    }

}
