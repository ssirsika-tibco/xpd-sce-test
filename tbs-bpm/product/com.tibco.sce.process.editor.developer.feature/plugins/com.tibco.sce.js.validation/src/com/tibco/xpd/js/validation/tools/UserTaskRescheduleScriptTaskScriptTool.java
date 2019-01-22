/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * reschedule script provision validaiton tool
 * 
 * @author aallway
 * @since 25 Jul 2012
 */
public class UserTaskRescheduleScriptTaskScriptTool extends ScriptTool {

    private Activity activity = null;

    public UserTaskRescheduleScriptTaskScriptTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getUserTaskRescheduleScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.RESCHEDULE_USER_TASK;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
