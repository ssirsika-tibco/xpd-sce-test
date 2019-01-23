/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author mtorres
 *
 */
public class SeqFlowScriptBOMReferenceChange extends ScriptBOMReferenceChange {

    private Transition transition = null;
    
    public SeqFlowScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, Transition transition,
            EditingDomain editingDomain) {
        super(oldScript, args, element, transition, editingDomain);
        this.transition = transition;
    }

    /**
     * Constructor takes additional custom properties Map. Custom properties can
     * be used to extend the functionality, at this stage used for Enumerations
     * cache in Process package scope.
     * 
     * @param oldScript
     * @param args
     * @param element
     * @param activity
     * @param editingDomain
     * @param packageScopeEnumCache
     */

    public SeqFlowScriptBOMReferenceChange(String oldScript,
            RenameArguments args, EObject element, Transition transition,
            EditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        super(oldScript, args, element, transition, editingDomain,
                packageScopeEnumCache);
        this.transition = transition;
    }
    @Override
    protected Command getRefactorCommand() {
        return ProcessScriptUtil
                .getSetSequenceFlowScriptCommand(getEditingDomain(),
                        getNewScript(),
                        transition,
                        getScriptGrammar());
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.SEQUENCE_FLOW;
    }
    
    @Override
    protected String getScriptName() {
        return Messages.SeqFlowScriptBOMReferenceChange_SequenceFlowScript;
    } 
}
