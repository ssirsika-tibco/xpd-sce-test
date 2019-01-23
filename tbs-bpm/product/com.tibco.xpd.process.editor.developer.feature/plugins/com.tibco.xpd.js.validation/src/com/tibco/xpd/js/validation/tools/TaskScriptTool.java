/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class TaskScriptTool extends ScriptTool {

	private Activity activity = null;

	public TaskScriptTool(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected Process getProcess() {
		return activity.getProcess();
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getScriptTaskScript(activity);
	}
	
	@Override
	protected String getScriptType() {		
		return ProcessJsConsts.SCRIPT_TASK;
	}
	
	@Override
	protected Activity getActivity() {
	    return this.activity;
	}

}
