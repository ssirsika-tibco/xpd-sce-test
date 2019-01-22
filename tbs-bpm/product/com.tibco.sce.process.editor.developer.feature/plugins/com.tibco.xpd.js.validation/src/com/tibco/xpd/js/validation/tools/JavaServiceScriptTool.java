/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author nwilson
 */
public class JavaServiceScriptTool extends ScriptTool {

    private Activity activity;
    
    private Process process;

    private String script;

    private String scriptType;

    /**
     * @param object
     */
    public JavaServiceScriptTool(Activity activity, Process process, String script,
            String scriptType) {
        super(process);
        this.activity = activity;
        this.process = process;
        this.script = script;
        this.scriptType = scriptType;
    }

    /**
     * @return
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getProcess()
     */
    @Override
    protected Process getProcess() {
        return process;
    }

    /**
     * @return
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScript()
     */
    @Override
    protected String getScript() {
        return script;
    }

    /**
     * @return
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     */
    @Override
    protected String getScriptType() {
        return scriptType;
    }
    
    /**
     * @return
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getActivity()
     */
    @Override
    protected Activity getActivity() {
        return activity;
    }

}
