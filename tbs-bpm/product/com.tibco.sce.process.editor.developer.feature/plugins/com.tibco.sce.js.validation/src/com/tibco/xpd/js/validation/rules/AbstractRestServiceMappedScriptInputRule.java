/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.js.validation.tools.RestServiceJScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * REST service mapped input script validation rule.
 * 
 * 
 * @author nwilson
 * @since 26 Mar 2015
 */
public abstract class AbstractRestServiceMappedScriptInputRule extends
        AbstractMappedScriptExpressionInputRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractMappedScriptExpressionOutputRule#getScriptTool(com.tibco.xpd.xpdl2.DataMapping)
     * 
     * @param dataMapping
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(DataMapping dataMapping) {

        if (dataMapping != null) {
            /*
             * Sid XPD-8009: to be on the safe side explicitly do not validate
             * process data mapper script mapping scripts.
             */
            if (!MappingRuleUtil.isProcessDataScriptMappingScript(dataMapping)) {
                /*
                 * Sid XPD-7892: Not all data mappings under a activity apply to
                 * this specific data mapping scenario (i.e. process data mapper
                 * scripts). Need to be more fussy about when we activate.
                 */
                if (Xpdl2ModelUtil.getAncestor(dataMapping, TaskService.class) != null) {
                    Activity activity =
                            (Activity) Xpdl2ModelUtil.getAncestor(dataMapping,
                                    Activity.class);

                    RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                    if (rsta.isRestServiceImplementation(activity)) {

                        return getScope().getTool(RestServiceJScriptTool.class,
                                dataMapping);
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.RESTSERVICE_TASK;
    }
}
