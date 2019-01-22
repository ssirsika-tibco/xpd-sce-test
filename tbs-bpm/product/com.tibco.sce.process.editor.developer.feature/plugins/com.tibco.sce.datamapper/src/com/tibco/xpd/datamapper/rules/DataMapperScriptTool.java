/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script tool to support validation of scripts contained in a Data Mapper.
 * These scripts will be inserted into the body of a Javascript function and so
 * must have a return statement at the end.
 * 
 * @author nwilson
 * @since 28 Apr 2015
 */
public class DataMapperScriptTool extends ScriptTool {

    private String script;

    private Activity activity;

    private Process process;

    private String scriptType;

    public DataMapperScriptTool(ScriptInformation si, String scriptType) {
        super(si);
        script = si.getExpression().getText();
        activity = Xpdl2ModelUtil.getParentActivity(si);
        process = activity.getProcess();
        this.scriptType = scriptType;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScript()
     * 
     * @return
     */
    @Override
    protected String getScript() {
        return script;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return scriptType;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getProcess()
     * 
     * @return
     */
    @Override
    protected Process getProcess() {
        return process;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getActivity()
     * 
     * @return
     */
    @Override
    protected Activity getActivity() {
        return activity;
    }

}
