package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class UserTaskOpenScriptTool extends ScriptTool {

	private Activity activity = null;

	public UserTaskOpenScriptTool(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected Process getProcess() {
		return activity.getProcess();
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getUserTaskOpenScript(this.activity);
	}

	@Override
	protected String getScriptType() {
		return ProcessJsConsts.OPEN_USER_TASK;
	}
	
    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
