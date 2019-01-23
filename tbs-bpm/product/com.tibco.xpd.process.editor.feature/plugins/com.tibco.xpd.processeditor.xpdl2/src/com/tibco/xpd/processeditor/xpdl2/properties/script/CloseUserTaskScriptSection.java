package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;

public class CloseUserTaskScriptSection extends BaseUserTaskScriptSection {

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.CLOSE_USER_TASK;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetCloseUserTaskScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    @Override
    protected EObject getScriptNotifierObject(UserTaskScripts userTaskScripts) {
        return userTaskScripts.getCloseScript();
    }

    protected String getScriptDefinitionLabel() {
        return Messages.UserTaskScriptSection_CloseUserTaskDefinitionScripts;
    }

}
