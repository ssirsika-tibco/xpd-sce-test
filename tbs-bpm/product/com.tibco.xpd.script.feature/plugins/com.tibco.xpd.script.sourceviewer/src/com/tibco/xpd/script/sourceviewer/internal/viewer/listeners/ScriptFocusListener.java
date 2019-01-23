package com.tibco.xpd.script.sourceviewer.internal.viewer.listeners;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.IJobManager;
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

    public void focusGained(FocusEvent e) {
        SaveUtil.compileScript(scriptSourceViewer);
        this.scriptSourceViewer.initialiseActions();
    }

    public void focusLost(FocusEvent e) {
        IJobManager jobManager = Platform.getJobManager();
        jobManager.cancel(Consts.SCRIPT_JOB_FAMILY);
        SaveUtil.saveScript(scriptSourceViewer);
        unRegisterContentAssist();
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
