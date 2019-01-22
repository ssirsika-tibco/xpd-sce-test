/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.scriptdata;

import com.tibco.xpd.process.datamapper.common.AbstractExpressionScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;

/**
 * ScriptDataMapper element provider for Script Task main script.
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public class ScriptTaskScriptDataMapperProvider extends
        AbstractExpressionScriptDataMapperProvider {

    /**
     * Explicitly constructs super for script tasks,
     */
    public ScriptTaskScriptDataMapperProvider() {
        super(ProcessScriptContextConstants.SCRIPT_TASK,
                DirectionType.IN_LITERAL);
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.AbstractExpressionScriptDataMapperProvider#getExpression(java.lang.Object)
     * 
     * @param contextInputObject
     * @return
     */
    @Override
    protected Expression getExpression(Object contextInputObject) {
        Expression script = null;

        if (contextInputObject instanceof Activity) {
            Activity activity = (Activity) contextInputObject;
            if (activity.getImplementation() instanceof Task
                    && ((Task) activity.getImplementation()).getTaskScript() != null) {

                TaskScript taskScript =
                        ((Task) activity.getImplementation()).getTaskScript();

                script = taskScript.getScript();
            }
        }
        return script;
    }

}
