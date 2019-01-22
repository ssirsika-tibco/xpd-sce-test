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
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 *
 */
public class UserTaskCloseScriptBOMReferenceChange extends ScriptBOMReferenceChange {

    private Activity activity = null;
    
    public UserTaskCloseScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, Activity activity,
            EditingDomain editingDomain) {
        super(oldScript, args, element, activity, editingDomain);
        this.activity = activity;
    }

    /**
     * Constructor takes additional custom properties Map. Custom properties can
     * be used to extend the functionality, at this stage used for Enumerations
     * cache in Process package scope.
     * 
     * @param scriptTaskScript
     * @param args
     * @param element
     * @param activity2
     * @param editingDomain
     * @param packageScopeEnumCache
     */
    public UserTaskCloseScriptBOMReferenceChange(String oldScript,
            RenameArguments args, EObject element, Activity activity,
            EditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        super(oldScript, args, element, activity, editingDomain,
                packageScopeEnumCache);
        this.activity = activity;
    }

    @Override
    protected Command getRefactorCommand() {
        return ProcessScriptUtil
                .getSetUserTaskCloseScriptCommand(getEditingDomain(),
                        getNewScript(),
                        activity,
                        getScriptGrammar());
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.CLOSE_USER_TASK;
    }
    
    @Override
    protected String getScriptName() {
        return Messages.UserTaskCloseScriptBOMReferenceChange_CloseScript;
    }
}
