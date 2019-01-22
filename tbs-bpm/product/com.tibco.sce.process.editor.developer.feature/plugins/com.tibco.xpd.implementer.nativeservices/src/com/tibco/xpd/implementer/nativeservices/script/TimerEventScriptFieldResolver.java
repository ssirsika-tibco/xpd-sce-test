package com.tibco.xpd.implementer.nativeservices.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

public class TimerEventScriptFieldResolver extends ScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getDataReferenceContext(org.eclipse.emf.ecore.EObject)
     * 
     * @param activityOrTransition
     * @return
     */
    @Override
    protected DataReferenceContext getDataReferenceContext(
            EObject activityOrTransition) {
        return DataReferenceContext.CONTEXT_TIMER_EVENT_SCRIPT;
    }

    @Override
    protected String getActivityScript(Activity activity) {
        String timerEventScript =
                ProcessScriptUtil.getTimerEventScript(activity);
        return timerEventScript;
    }

    @Override
    protected Command getSetActivityScriptCommand(EditingDomain editingDomain,
            String strScript, Activity activity) {
        return ProcessScriptUtil.getSetTimerEventScriptCommand(editingDomain,
                strScript,
                activity,
                getJavaScriptGrammar());
    }

    @Override
    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isTimerEventWithScriptType(activity,
                getJavaScriptGrammar());
    }

    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return null;
    }

    @Override
    protected String getTransitionScript(Transition transition) {
        return null;
    }

    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return false;
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        // Do not need to remove data field refs from timer event script- java
        // script
        // validation will show errors where deleted field is referenced.
        return null;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        // Do not need to remove data field refs from transityion condition -
        // java script
        // validation will show errors where deleted field is referenced.
        return null;
    }

}
