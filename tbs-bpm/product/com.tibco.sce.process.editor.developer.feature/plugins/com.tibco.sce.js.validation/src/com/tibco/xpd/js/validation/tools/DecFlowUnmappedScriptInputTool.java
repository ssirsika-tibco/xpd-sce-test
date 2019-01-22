/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 6 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class DecFlowUnmappedScriptInputTool extends ScriptTool {

	private ScriptInformation scriptInformation = null;

	public DecFlowUnmappedScriptInputTool(ScriptInformation scriptInformation) {
		super(scriptInformation);
		this.scriptInformation = scriptInformation;
	}

	@Override
	protected Process getProcess() {
	    Process process = null;
        if(scriptInformation != null){
            process = Xpdl2ModelUtil.getProcess(scriptInformation);
        }
        return process;
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getTaskScript(scriptInformation);
	}
	
	@Override
	protected String getScriptType() {		
		return ProcessJsConsts.DEC_FLOW_SERVICE_TASK;
	}
	
	@Override
    protected Activity getActivity() {
        Activity activity = null;
        if (scriptInformation != null) {
            activity = Xpdl2ModelUtil.getParentActivity(scriptInformation);
        }
        return activity;
    }
	

}
