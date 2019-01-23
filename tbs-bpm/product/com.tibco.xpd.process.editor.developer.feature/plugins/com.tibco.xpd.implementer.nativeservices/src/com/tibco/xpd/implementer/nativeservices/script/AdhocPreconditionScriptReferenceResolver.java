/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
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
 * Reference Resolver for Adhoc Task Precondition Scripts. Runs through the
 * Script updating all the field or parameters whose names were changed.
 * 
 * 
 * @author kthombar
 * @since 19-Aug-2014
 */
public class AdhocPreconditionScriptReferenceResolver extends
        ScriptProcessFieldResolver {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromActivityCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromTransitionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param transition
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getDataReferenceContext(org.eclipse.emf.ecore.EObject)
     * 
     * @param activityOrTransition
     * @return
     */
    @Override
    protected DataReferenceContext getDataReferenceContext(
            EObject activityOrTransition) {

        return DataReferenceContext.CONTEXT_ADHOC_PRECONDITION;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getVarNameResolver()
     * 
     * @return
     */
    @Override
    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#isInterestingTransition(com.tibco.xpd.xpdl2.Transition)
     * 
     * @param transition
     * @return
     */
    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#isInterestingActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil
                .isAdhocPreconditionTaskWithScriptType(activity,
                        getJavaScriptGrammar());
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getSetTransitionScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.String, com.tibco.xpd.xpdl2.Transition)
     * 
     * @param editingDomain
     * @param string
     * @param transition
     * @return
     */
    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getSetActivityScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.String, com.tibco.xpd.xpdl2.Activity)
     * 
     * @param editingDomain
     * @param strScript
     * @param activity
     * @return
     */
    @Override
    protected Command getSetActivityScriptCommand(EditingDomain editingDomain,
            String strScript, Activity activity) {

        return ProcessScriptUtil.getSetAdhocScriptCommand(editingDomain,
                strScript,
                activity,
                getJavaScriptGrammar());
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getActivityScript(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getActivityScript(Activity activity) {
        return ProcessScriptUtil.getAdhocTaskPreconditionScript(activity);
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getTransitionScript(com.tibco.xpd.xpdl2.Transition)
     * 
     * @param transition
     * @return
     */
    @Override
    protected String getTransitionScript(Transition transition) {
        return null;
    }
}
