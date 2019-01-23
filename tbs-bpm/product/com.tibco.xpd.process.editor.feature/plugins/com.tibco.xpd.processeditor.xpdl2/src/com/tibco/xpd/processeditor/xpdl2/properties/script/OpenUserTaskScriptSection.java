/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Script Section for Open User Task
 * 
 * @author rsomayaj
 * 
 */
public class OpenUserTaskScriptSection extends BaseUserTaskScriptSection {

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.OPEN_USER_TASK;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetOpenUserTaskScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    @Override
    protected EObject getScriptNotifierObject(UserTaskScripts userTaskScripts) {
        return userTaskScripts.getOpenScript();
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptDefinitionLabel()
     * 
     * @return
     */
    @Override
    protected String getScriptDefinitionLabel() {
        return Messages.UserTaskScriptSection_InitUserTaskDefinitionScripts;
    }

}
