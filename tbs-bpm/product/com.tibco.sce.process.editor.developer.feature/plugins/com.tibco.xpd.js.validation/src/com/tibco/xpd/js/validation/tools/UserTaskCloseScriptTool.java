package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class UserTaskCloseScriptTool extends ScriptTool {

	private Activity activity = null;

	public UserTaskCloseScriptTool(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected Process getProcess() {
		return activity.getProcess();
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getUserTaskCloseScript(this.activity);
	}

	@Override
	protected String getScriptType() {
		return ProcessJsConsts.CLOSE_USER_TASK;
	}
	   
    @Override
    protected Activity getActivity() {
        return this.activity;
    }

}
