/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Script Section for the Timer Events
 * 
 * @author rsomayaj
 * 
 */
public class TimerEventScriptSection extends BaseProcessScriptSection implements
        EventImplementationSection {

    public TimerEventScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;

        Activity eventAct = getActivity();
        if (eventAct != null) {
            currentScriptGrammarId =
                    EventObjectUtil.getExistingSetScriptGrammarId(eventAct);
        }
        return currentScriptGrammarId;
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.TIMER_EVENT;
    }

    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;
        Activity eventAct = getActivity();
        EditingDomain ed = getEditingDomain();
        if (eventAct != null && ed != null && editorSection != null) {
            cmd = editorSection.getSetScriptGrammarCommand(ed, eventAct);
        }
        return cmd;
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    @Override
    public String getLocalId() {
        return "analyst.timerEventScriptSection"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    @Override
    public boolean isLoadProcessData() {
        return false;
    }
}
