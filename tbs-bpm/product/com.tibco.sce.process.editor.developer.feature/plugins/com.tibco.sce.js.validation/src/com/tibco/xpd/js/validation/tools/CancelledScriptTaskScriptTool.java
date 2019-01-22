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
 * <i>Created: 26 Mar 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class CancelledScriptTaskScriptTool extends ScriptTool {

    private Activity activity = null;

    public CancelledScriptTaskScriptTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getCancelledScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
