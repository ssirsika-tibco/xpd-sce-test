/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * Script tool class for reschedule timer event script.
 * 
 * 
 * @author aallway
 * @since 19 Mar 2013
 */
public class RescheduleTimerEventScriptTool extends ScriptTool {

    private Activity activity = null;

    public RescheduleTimerEventScriptTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getRescheduleTimerEventScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
