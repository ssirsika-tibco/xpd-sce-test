/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.js.validation.tools.RestServiceJScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * REST service mapped output script validation rule.
 * 
 * 
 * @author nwilson
 * @since 26 Mar 2015
 */
public abstract class AbstractRestServiceMappedScriptOutputRule extends
        AbstractMappedScriptExpressionOutputRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractMappedScriptExpressionOutputRule#getScriptTool(com.tibco.xpd.xpdl2.DataMapping)
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
                /*
                 * Sid XPD-7892: Not all data mappings under a activity apply to
                 * this specific data mapping scenario (i.e. process data mapper
                 * scripts). Need to be more fussy about when we activate.
                 */
                TaskService service =
                        (TaskService) Xpdl2ModelUtil
                                .getAncestor(scriptInformation,
                                        TaskService.class);

                if (service != null) {

                    Activity activity =
                            (Activity) Xpdl2ModelUtil
                                    .getAncestor(scriptInformation,
                                            Activity.class);

                    RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                    if (rsta.isRestServiceImplementation(activity)) {

                        if (scriptInformation.eContainer() instanceof DataMapping) {

                            DataMapping dataMapping =
                                    (DataMapping) scriptInformation
                                            .eContainer();

                            return getScope()
                                    .getTool(RestServiceJScriptTool.class,
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
        return ProcessJsConsts.RESTSERVICE_TASK;
    }
}
