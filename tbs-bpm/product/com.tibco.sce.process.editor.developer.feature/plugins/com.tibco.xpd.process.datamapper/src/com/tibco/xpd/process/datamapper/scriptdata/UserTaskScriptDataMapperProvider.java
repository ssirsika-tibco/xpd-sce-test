/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.scriptdata;

import com.tibco.xpd.process.datamapper.common.AbstractExpressionScriptDataMapperProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;

/**
 * ScriptDataMapper element provider for User Task Work Manager Scripts
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public class UserTaskScriptDataMapperProvider extends
        AbstractExpressionScriptDataMapperProvider {

    private String context;

    public UserTaskScriptDataMapperProvider(String context) {
        super(context, DirectionType.IN_LITERAL);
        this.context = context;
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
            UserTaskScripts userTaskScripts =
                    TaskObjectUtil.getUserTaskScripts(activity);
            if (userTaskScripts != null) {
                switch (context) {
                case ProcessScriptContextConstants.CLOSE_USER_TASK:
                    script = userTaskScripts.getCloseScript();
                    break;
                case ProcessScriptContextConstants.OPEN_USER_TASK:
                    script = userTaskScripts.getOpenScript();
                    break;
                case ProcessScriptContextConstants.RESCHEDULE_USER_TASK:
                    script = userTaskScripts.getRescheduleScript();
                    break;
                case ProcessScriptContextConstants.SCHEDULE_USER_TASK:
                    script = userTaskScripts.getScheduleScript();
                    break;
                case ProcessScriptContextConstants.SUBMIT_USER_TASK:
                    script = userTaskScripts.getSubmitScript();
                    break;
                }
            }
        }
        return script;
    }

}
