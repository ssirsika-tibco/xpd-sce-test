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
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * REST service unmapped input script validation rule.
 * 
 * 
 * @author nwilson
 * @since 26 Mar 2015
 */
public abstract class AbstractRestServiceUnmappedScriptInputRule extends
        AbstractUnmappedScriptExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractUnmappedScriptExpressionRule#getScriptTool(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(ScriptInformation scriptInformation) {

        if (scriptInformation != null
                && (scriptInformation.getDirection() == null || !DirectionType.OUT_LITERAL
                        .equals(scriptInformation.getDirection()))) {

            /*
             * Sid XPD-7892: Not all data mappings under a activity apply to
             * this specific data mapping scenario (i.e. process data mapper
             * scripts). Need to be more fussy about when we activate.
             */
            if (Xpdl2ModelUtil
                    .getAncestor(scriptInformation, TaskService.class) != null) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil
                                .getAncestor(scriptInformation, Activity.class);
                RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                if (rsta.isRestServiceImplementation(activity)) {
                    return getScope().getTool(RestServiceJScriptTool.class,
                            scriptInformation);
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
