/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Script Section for Schedule User Task
 * 
 * @author mtorres
 * 
 */
public class ScheduleUserTaskScriptSection extends BaseUserTaskScriptSection {

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.SCHEDULE_USER_TASK;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetScheduleUserTaskScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    @Override
    protected EObject getScriptNotifierObject(UserTaskScripts userTaskScripts) {
        return userTaskScripts.getScheduleScript();
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptDefinitionLabel()
     * 
     * @return
     */
    @Override
    protected String getScriptDefinitionLabel() {
        return Messages.UserTaskScriptSection_ScheduleUserTaskDefinitionScripts;
    }

}
