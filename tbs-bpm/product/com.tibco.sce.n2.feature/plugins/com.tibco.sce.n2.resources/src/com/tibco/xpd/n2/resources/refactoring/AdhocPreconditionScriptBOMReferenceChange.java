/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Adhoc Precondition Script Bom Reference resolver that updates all BOM
 * factories and BOM fully qualified names which are used in the Adhoc Task
 * Precondition Scripts. This change is called/triggered when we 'RIGHT CLICK'
 * on the BOM Package badge and say Refactor->Rename.
 * 
 * 
 * @author kthombar
 * @since 18-Aug-2014
 */
public class AdhocPreconditionScriptBOMReferenceChange extends
        ScriptBOMReferenceChange {

    private Activity activity = null;

    /**
     * 
     * @param oldScript
     * @param args
     * @param element
     * @param activity
     * @param editingDomain
     * @param packageScopeEnumCache
     */
    public AdhocPreconditionScriptBOMReferenceChange(String oldScript,
            RenameArguments args, EObject element, Activity activity,
            TransactionalEditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        super(oldScript, args, element, activity, editingDomain,
                packageScopeEnumCache);
        this.activity = activity;
    }

    /**
     * @see com.tibco.xpd.n2.resources.refactoring.ScriptBOMReferenceChange#getScriptName()
     * 
     * @return
     */
    @Override
    protected String getScriptName() {
        return Messages.AdhocPreconditionScriptBOMReferenceChange_AdhocPreconditionScript_label;
    }

    /**
     * @see com.tibco.xpd.n2.resources.refactoring.ScriptBOMReferenceChange#getRefactorCommand()
     * 
     * @return
     */
    @Override
    protected Command getRefactorCommand() {

        return ProcessScriptUtil.getSetAdhocScriptCommand(getEditingDomain(),
                getNewScript(),
                activity,
                getScriptGrammar());
    }

    /**
     * @see com.tibco.xpd.n2.resources.refactoring.ScriptBOMReferenceChange#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.ADHOC_PRECONDITION;
    }
}
