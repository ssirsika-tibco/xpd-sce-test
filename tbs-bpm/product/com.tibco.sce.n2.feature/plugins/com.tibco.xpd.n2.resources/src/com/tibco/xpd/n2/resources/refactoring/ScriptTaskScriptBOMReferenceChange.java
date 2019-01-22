/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 *
 */
public class ScriptTaskScriptBOMReferenceChange extends ScriptBOMReferenceChange {

    private Activity activity = null;
    
    public ScriptTaskScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, Activity activity,
            EditingDomain editingDomain) {
        super(oldScript, args, element, activity, editingDomain);
        this.activity = activity;
    }

    /**
     * Constructor takes additional {@link PackageScopeEnumCache} which holds
     * the enumerations for given process package scope. be used to extend the
     * functionality, at this stage used for cache of Enumerations in Process
     * package scope.
     * 
     * @param scriptTaskScript
     * @param args
     * @param element
     * @param activity
     * @param editingDomain
     * @param packageScopeEnumCache
     */
    public ScriptTaskScriptBOMReferenceChange(String oldScript,
            RenameArguments args, EObject element, Activity activity,
            TransactionalEditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        super(oldScript, args, element, activity, editingDomain,
                packageScopeEnumCache);
        this.activity = activity;
    }

    @Override
    protected Command getRefactorCommand() {
        return ProcessScriptUtil
                .getSetScriptTaskScriptCommand(getEditingDomain(),
                        getNewScript(),
                        activity,
                        getScriptGrammar());
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.SCRIPT_TASK;
    }
    
    @Override
    protected String getScriptName() {
        return Messages.ScriptTaskScriptBOMReferenceChange_ScriptTask;
    } 
}
