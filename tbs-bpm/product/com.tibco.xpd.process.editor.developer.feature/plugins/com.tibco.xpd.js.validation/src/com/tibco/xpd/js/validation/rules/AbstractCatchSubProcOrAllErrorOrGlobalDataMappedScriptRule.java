/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.SubProcessMappedScriptOutputTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapped Mapping script validation for catch specific sub-process error or
 * catch-all error event or Global data error event
 * 
 * 
 * @author agondal
 * @since 18 Sep 2013
 */
public abstract class AbstractCatchSubProcOrAllErrorOrGlobalDataMappedScriptRule extends
        AbstractMappedScriptExpressionOutputRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractJsEventMapperScriptRule#getScriptTool(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(ScriptInformation scriptInformation) {

        if (scriptInformation != null) {

            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil
                    .isProcessDataScriptMappingScript(scriptInformation)) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil
                                .getAncestor(scriptInformation, Activity.class);

                if (activity != null && activity.getEvent() != null
                        && isScriptMappingSupportedForBpmnCatchError(activity)) {

                    if (BpmnCatchableErrorUtil
                            .isCatchSubProcessErrorEvent(activity)
                            || isCatchAllOrByName(activity)
                            || BpmnCatchableErrorUtil
                                    .isCatchGlobalDataErrorEvent(activity)) {

                        if (scriptInformation.eContainer() instanceof DataMapping) {
                            DataMapping dataMapping =
                                    (DataMapping) scriptInformation
                                            .eContainer();

                            return getScope()
                                    .getTool(SubProcessMappedScriptOutputTool.class,
                                            dataMapping);
                        }
                    }
                }

            }
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.SUBPROCESS_TASK;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity is a CATCH_ALL or CATCH_BY_NAME
     *         error event.
     */
    private boolean isCatchAllOrByName(Activity activity) {
        ErrorCatchType catchType =
                BpmnCatchableErrorUtil.getCatchTypeStrict(activity);

        return ErrorCatchType.CATCH_ALL.equals(catchType)
                || ErrorCatchType.CATCH_BY_NAME.equals(catchType);
    }

    /**
     * 
     * @param activity
     * 
     * @return <code>true</code> if script mapping is supported for BPMN catch
     *         error event, <code>false</code> otherwise
     */
    protected abstract boolean isScriptMappingSupportedForBpmnCatchError(
            Activity activity);

}
