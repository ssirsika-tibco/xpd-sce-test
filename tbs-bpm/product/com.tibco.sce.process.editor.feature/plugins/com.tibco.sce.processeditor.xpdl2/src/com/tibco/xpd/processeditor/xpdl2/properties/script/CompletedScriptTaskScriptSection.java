/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author Miguel Torres
 */
public class CompletedScriptTaskScriptSection extends
        BaseScriptTaskAuditScriptSection {

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetCompletedAuditScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    @Override
    protected EObject getScriptNotifierObject(Audit audit) {
        return TaskObjectUtil.getAuditEvent(AuditEventType.COMPLETED_LITERAL,
                audit);
    }

    protected String getScriptDefinitionLabel() {
        return Messages.AuditScriptSection_CompletedAuditDefinitionScripts;
    }
}
