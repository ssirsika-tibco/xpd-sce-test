package com.tibco.xpd.script.sourceviewer.internal.viewer.listeners;

import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

import com.tibco.xpd.script.sourceviewer.internal.util.Consts;
import com.tibco.xpd.script.sourceviewer.internal.viewer.SaveUtil;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

public class ScriptFocusListener implements FocusListener {

    private ScriptSourceViewer scriptSourceViewer;

    public ScriptFocusListener(ScriptSourceViewer scriptSourceViewer) {
        this.scriptSourceViewer = scriptSourceViewer;
    }

    @Override
    public void focusGained(FocusEvent e) {
        SaveUtil.compileScript(scriptSourceViewer);
        this.scriptSourceViewer.initialiseActions();
    }

    @Override
    public void focusLost(FocusEvent e) {
        IJobManager jobManager = Job.getJobManager();
        jobManager.cancel(Consts.SCRIPT_JOB_FAMILY);
        SaveUtil.saveScript(scriptSourceViewer);
        unRegisterContentAssist();

        /*
         * Sid ACE-2915 uninstalling the action trigger on refresh caused issues because if we get a refresh (for
         * instance if model changes on undo/redo) then we clear some fields in the action cdoe trigger that are
         * required for correct clearing down of the action's binding to the action binding service when we lose focus.
         * 
         * GIVEN that we do fActivationCodeTrigger.install() as part of focusGained() (via other functions) THEN we
         * really should do fActivationCodeTrigger.uninstall() DURING focusLost().
         * 
         * So moved uninstall from ScriptSourceViewer.doRefresh() to here.
         */
        ScriptSourceViewer.ActivationCodeTrigger activationCodeTrigger = scriptSourceViewer.getActivationCodeTrigger();
        if (activationCodeTrigger != null) {
            activationCodeTrigger.uninstall();
        }

    }

    /**
     * This method unregisters the content assist action on JScriptSourceViewer
     * when it looses focus
     * 
     * @param bool
     */
    private void unRegisterContentAssist() {
        ScriptSourceViewer.ActivationCodeTrigger activationCodeTrigger = scriptSourceViewer
                .getActivationCodeTrigger();
        if (activationCodeTrigger != null) {
            activationCodeTrigger.unregisterAllActionFromKeyActivation();
        }
    }

}
