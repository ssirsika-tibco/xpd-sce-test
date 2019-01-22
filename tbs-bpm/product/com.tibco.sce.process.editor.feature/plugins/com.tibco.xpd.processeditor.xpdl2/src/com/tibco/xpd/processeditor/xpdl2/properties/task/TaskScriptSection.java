/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class TaskScriptSection extends BaseProcessScriptSection implements
        IPluginContribution {

    public TaskScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public TaskScriptSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    public String getScriptContext() {
        return ProcessScriptContextConstants.SCRIPT_TASK;
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    /**
     * Get the currently selected script grammar from the model
     * 
     * @return
     */
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;
        Activity act = getActivity();

        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil.getExistingSetScriptGrammarId(act);
        }
        return currentScriptGrammarId;
    }

    /**
     * Returns the command which will set the grammar for the expression.
     */
    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;

        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null && editorSection != null) {
            cmd = editorSection.getSetScriptGrammarCommand(ed, act);
        }
        return cmd;
    }

    public String getLocalId() {
        return "analyst.editor"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
