/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
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
 * User task reschedule script field resolver (looks for data references in user
 * task reschedule script).
 * 
 * 
 * @author aallway
 * @since 19 Jun 2012
 */
public class UserTaskRescheduleScriptFieldResolver extends
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
        return DataReferenceContext.CONTEXT_RESCHEDULE_SCRIPT;
    }

    @Override
    protected String getActivityScript(Activity activity) {
        String userTaskOpenScript =
                ProcessScriptUtil.getUserTaskRescheduleScript(activity);
        return userTaskOpenScript;
    }

    @Override
    protected Command getSetActivityScriptCommand(EditingDomain editingDomain,
            String strScript, Activity activity) {
        Command cmd =
                ProcessScriptUtil
                        .getSetUserTaskRescheduleScriptCommand(editingDomain,
                                strScript,
                                activity,
                                getJavaScriptGrammar());
        return cmd;
    }

    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected String getTransitionScript(Transition transition) {
        return null;
    }

    @Override
    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isUserTaskWithRescheduleScriptType(activity,
                getJavaScriptGrammar());
    }

    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return false;
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        return null;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return null;
    }

}
