/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * Provides the Script Tool to deal with Adhoc Precondition Scripts.
 * 
 * 
 * @author kthombar
 * @since 15-Aug-2014
 */
public class AdhocTaskScriptTool extends ScriptTool {

    private Activity activity = null;

    public AdhocTaskScriptTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getAdhocTaskPreconditionScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.ADHOC_PRECONDITION;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }
}
