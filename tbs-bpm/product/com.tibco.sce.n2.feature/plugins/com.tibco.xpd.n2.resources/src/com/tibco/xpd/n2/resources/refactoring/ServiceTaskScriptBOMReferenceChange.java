/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 *
 */
public class ServiceTaskScriptBOMReferenceChange extends ScriptBOMReferenceChange {

    private Activity activity = null;
    
    private ScriptInformation scriptInformation;
    
    public ServiceTaskScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, Activity activity, ScriptInformation scriptInformation,
            EditingDomain editingDomain) {
        super(oldScript, args, element, scriptInformation, editingDomain);
        this.activity = activity;
        this.scriptInformation = scriptInformation;
    }

    /**
     * Constructor takes additional custom properties Map. Custom properties can
     * be used to extend the functionality, at this stage used for Enumerations
     * cache in Process package scope.
     * 
     * @param args
     * @param element
     * @param activity
     * @param scriptInformation
     * @param editingDomain
     * @param packageScopeEnumCache
     */
    public ServiceTaskScriptBOMReferenceChange(String oldScript,
            RenameArguments args, EObject element, Activity activity,
            ScriptInformation scriptInformation, EditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        super(oldScript, args, element, scriptInformation, editingDomain,
                packageScopeEnumCache);
        this.activity = activity;
        this.scriptInformation = scriptInformation;
    }

    @Override
    protected Command getRefactorCommand() {
        return Xpdl2WsdlUtil
                    .getSetWebServiceTaskScriptCommand(getEditingDomain(),
                            getNewScript(),
                            scriptInformation,
                            getScriptGrammar());    
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.WEB_SERVICE_TASK;
    }
    
    @Override
    protected String getScriptName() {
        return scriptInformation.getName();
    } 
}
