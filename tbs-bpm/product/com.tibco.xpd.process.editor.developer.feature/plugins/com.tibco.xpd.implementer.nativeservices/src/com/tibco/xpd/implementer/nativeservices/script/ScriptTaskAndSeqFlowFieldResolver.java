/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
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

/**
 * 
 * <p>
 * <i>Created: 12 Apr 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class ScriptTaskAndSeqFlowFieldResolver extends
        ScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getDataReferenceContext(org.eclipse.emf.ecore.EObject)
     * 
     * @param activityOrTransition
     * @return
     */
    @Override
    protected DataReferenceContext getDataReferenceContext(
            EObject activityOrTransition) {
        if (activityOrTransition instanceof Transition) {
            return DataReferenceContext.CONTEXT_SEQUENCEFLOW_SCRIPT;
        } else {
            return DataReferenceContext.CONTEXT_SCRIPT_TASK_SCRIPT;
        }
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param strScript
     * @param eObject
     * @return
     */
    @Override
    protected Command getSetActivityScriptCommand(EditingDomain editingDomain,
            String strScript, Activity activity) {
        return ProcessScriptUtil.getSetScriptTaskScriptCommand(editingDomain,
                strScript,
                activity,
                getJavaScriptGrammar());
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param string
     * @param eObject
     * @return
     */
    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return ProcessScriptUtil.getSetSequenceFlowScriptCommand(editingDomain,
                string,
                transition,
                getJavaScriptGrammar());
    }

    @Override
    protected String getActivityScript(Activity activity) {
        String activityScript = ProcessScriptUtil.getScriptTaskScript(activity);
        return activityScript;
    }

    @Override
    protected String getTransitionScript(Transition transition) {
        String tranScript = ProcessScriptUtil.getSeqFlowScript(transition);
        return tranScript;
    }

    @Override
    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isScriptTaskWithScriptType(activity,
                getJavaScriptGrammar());
    }

    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return ProcessScriptUtil.isSeqFlowWithScriptType(transition,
                getJavaScriptGrammar());
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        // Do not need to remove data field refs from script task - java script
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
