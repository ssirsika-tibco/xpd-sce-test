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
 * User task reschedule script section.
 * 
 * @author aallway
 * @since 19 Jul 2012
 */
public class RescheduleUserTaskScriptSection extends BaseUserTaskScriptSection {

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.RESCHEDULE_USER_TASK;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetRescheduleUserTaskScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    @Override
    protected EObject getScriptNotifierObject(UserTaskScripts userTaskScripts) {
        return userTaskScripts.getRescheduleScript();
    }

    /**
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptDefinitionLabel()
     * 
     * @return
     */
    @Override
    protected String getScriptDefinitionLabel() {
        return Messages.RescheduleUserTaskScriptSection_RescheduleScript_description;
    }

}
