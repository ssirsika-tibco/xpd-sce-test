/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * <p>
 * <i>Created: 18/08/2010</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class UserTaskScheduleScriptTaskScriptTool extends ScriptTool {

    private Activity activity = null;

    public UserTaskScheduleScriptTaskScriptTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getUserTaskScheduleScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.SCHEDULE_USER_TASK;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
