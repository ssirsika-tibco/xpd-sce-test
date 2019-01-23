/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Factory for reschedule script provision validation tool.
 * 
 * 
 * @author aallway
 * @since 25 Jul 2012
 */
public class UserTaskRescheduleScriptTaskScriptToolFactory extends
        JavaScriptToolFactory {

    @Override
    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            boolean b =
                    ProcessScriptUtil
                            .isUserTaskWithRescheduleScriptType(activity,
                                    getScriptGrammar());
            if (b) {
                processor =
                        new UserTaskRescheduleScriptTaskScriptTool(activity);
            }
        }
        return processor;
    }

    @Override
    public Class<? extends IPreProcessor> getToolClass() {
        return UserTaskRescheduleScriptTaskScriptTool.class;
    }

}
