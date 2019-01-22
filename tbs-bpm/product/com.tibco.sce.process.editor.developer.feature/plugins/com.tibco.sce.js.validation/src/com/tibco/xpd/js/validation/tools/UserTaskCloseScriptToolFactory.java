package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Activity;

public class UserTaskCloseScriptToolFactory extends JavaScriptToolFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
		IPreProcessor processor = null;
		if (input instanceof Activity) {
			Activity activity = (Activity) input;
			boolean b = ProcessScriptUtil.isUserTaskWithCloseScriptType(activity,
					getScriptGrammar());
			if (b) {
				processor = new UserTaskCloseScriptTool(activity);
			}
		}
		return processor;
	}

	public Class<? extends IPreProcessor> getToolClass() {
		return UserTaskCloseScriptTool.class;
	}

}
