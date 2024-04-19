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

	private Activity	activity	= null;

	private String		scriptType	= ProcessJsConsts.SCRIPT_TASK;

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
		return scriptType;
	}
	
	@Override
	protected Activity getActivity() {
	    return this.activity;
	}

	/**
	 * Overrides the script type associated with the current task script tool
	 * 
	 * @param newScriptType
	 * @return
	 */
	public String overrideScriptType(String newScriptType)
	{
		scriptType = newScriptType;

		return newScriptType;
	}

}
