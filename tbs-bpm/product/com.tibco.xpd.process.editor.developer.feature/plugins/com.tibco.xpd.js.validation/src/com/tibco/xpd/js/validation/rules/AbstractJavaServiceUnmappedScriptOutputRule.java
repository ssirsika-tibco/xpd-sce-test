/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.js.validation.tools.JavaServiceUnmappedScriptOutputTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java service unmapped output script validation rule
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractJavaServiceUnmappedScriptOutputRule extends
        AbstractUnmappedScriptExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractUnmappedScriptExpressionRule#getScriptTool(com.tibco.xpd.xpdExtension.ScriptInformation)
     * 
     * @param scriptInformation
     * @return
     */
    @Override
    protected ScriptTool getScriptTool(ScriptInformation scriptInformation) {
        /*
         * Sid XPD-8009: to be on the safe side explicitly do not validate
         * process data mapper script mapping scripts.
         */
        if (!MappingRuleUtil
                .isProcessDataScriptMappingScript(scriptInformation)) {

            if (scriptInformation != null
                    && DirectionType.OUT_LITERAL.equals(scriptInformation
                            .getDirection())) {

                Activity activity =
                        (Activity) Xpdl2ModelUtil
                                .getAncestor(scriptInformation, Activity.class);

                if (activity != null
                        && activity.getImplementation() instanceof Task) {

                    Task task = (Task) activity.getImplementation();
                    TaskService service = task.getTaskService();
                    if (service != null) {

                        String type =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(service,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());

                        if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                                .equals(type)) {
                            return getScope()
                                    .getTool(JavaServiceUnmappedScriptOutputTool.class,
                                            scriptInformation);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.JAVASERVICE_TASK;
    }
}
