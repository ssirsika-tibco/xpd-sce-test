/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Common parent class for JavaScript mapping rules that needs the parseScript
 * method.
 * 
 * @author nwilson
 * @since 21 Apr 2015
 */
public abstract class AbstractDeveloperActivityMappingJavaScriptRule extends
        AbstractActivityMappingJavaScriptRule {
    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        if (mapping instanceof Mapping
                && ((Mapping) mapping).getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
            ScriptInformation scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);
            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }
            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (activity != null && scriptInformation != null
                    && process != null) {
                return ScriptMappingUtil
                        .parseScript(mapping,
                                getValidationStrategyList(process, activity),
                                getProcessDestinationList(process),
                                getScriptRelevantDataTypeMap(process,
                                        scriptInformation),
                                getScriptType());
            }
        }
        return null;
    }

}
