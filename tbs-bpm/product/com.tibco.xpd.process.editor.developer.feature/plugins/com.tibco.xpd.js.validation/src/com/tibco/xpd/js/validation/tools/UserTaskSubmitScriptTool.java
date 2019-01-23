package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class UserTaskSubmitScriptTool extends ScriptTool {

	private Activity activity = null;

	public UserTaskSubmitScriptTool(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected Process getProcess() {
		return activity.getProcess();
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getUserTaskSubmitScript(this.activity);
	}

	@Override
	protected String getScriptType() {
		return ProcessJsConsts.SUBMIT_USER_TASK;
	}
	
    @Override
    protected Activity getActivity() {
        return this.activity;
    }
}
