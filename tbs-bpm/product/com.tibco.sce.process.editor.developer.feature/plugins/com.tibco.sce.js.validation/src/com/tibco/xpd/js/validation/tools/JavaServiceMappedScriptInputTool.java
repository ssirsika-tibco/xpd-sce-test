/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 5 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class JavaServiceMappedScriptInputTool extends ScriptTool {

	private DataMapping dataMapping = null;

	public JavaServiceMappedScriptInputTool(DataMapping dataMapping) {
        super(ProcessScriptUtil.getScriptInformationFromDataMapping(dataMapping));
        this.dataMapping = dataMapping;
    }

	@Override
	protected Process getProcess() {
	    Process process = null;
	    if(dataMapping != null){
	        process = Xpdl2ModelUtil.getProcess(dataMapping);
	    }
		return process;
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getDataMappingScript(dataMapping);
	}
	
	@Override
	protected String getScriptType() {		
		return ProcessJsConsts.JAVASERVICE_TASK;
	}
	
	@Override
    protected Activity getActivity(){
	    Activity activity = null;
	    if (dataMapping != null) {
	        activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
        }
	    return activity;
	}

}
