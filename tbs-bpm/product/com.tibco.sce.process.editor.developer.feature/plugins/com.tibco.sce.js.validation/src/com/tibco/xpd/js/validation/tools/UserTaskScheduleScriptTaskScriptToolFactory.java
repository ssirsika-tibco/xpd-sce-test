/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class UserTaskScheduleScriptTaskScriptToolFactory extends JavaScriptToolFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
		IPreProcessor processor = null;
		if (input instanceof Activity) {
			Activity activity = (Activity) input;
			boolean b = ProcessScriptUtil.isUserTaskWithScheduleScriptType(activity,
					getScriptGrammar());
			if (b) {
				processor = new UserTaskScheduleScriptTaskScriptTool(activity);
			}
		}
		return processor;
	}

	public Class<? extends IPreProcessor> getToolClass() {
		return UserTaskScheduleScriptTaskScriptTool.class;
	}

}
