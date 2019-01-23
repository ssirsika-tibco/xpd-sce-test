/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.js.validation.tools.JavaServiceMappedScriptInputTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java service mapped input script validation rule.
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public abstract class AbstractJavaServiceMappedScriptInputRule extends
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
                TaskService service =
                        (TaskService) Xpdl2ModelUtil.getAncestor(dataMapping,
                                TaskService.class);

                if (service != null) {

                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                            .equals(type)) {
                        return getScope()
                                .getTool(JavaServiceMappedScriptInputTool.class,
                                        dataMapping);
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
