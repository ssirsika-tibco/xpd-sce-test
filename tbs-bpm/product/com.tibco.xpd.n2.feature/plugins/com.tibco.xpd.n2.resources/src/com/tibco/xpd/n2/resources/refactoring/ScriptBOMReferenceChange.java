/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.refactoring.PreviewDescription;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 * 
 */
public abstract class ScriptBOMReferenceChange extends Change {

    private String oldScript;

    private String newScript;

    private RenameArguments args = null;

    private EObject element;

    private EObject scriptContainer;

    private EditingDomain editingDomain;

    // XPD-4936: cache for Enumerations in Process package Scope
    private PackageScopeEnumCache packageScopeEnumCache;

    public ScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, EObject scriptContainer,
            EditingDomain editingDomain) {
        this.oldScript = oldScript;
        this.args = args;
        this.element = element;
        this.scriptContainer = scriptContainer;
        this.editingDomain = editingDomain;
        this.newScript = performScriptModifications();
    }

    /**
     * This constructor takes the property map to intitalise additional
     * properties.
     * 
     * @param oldScript
     * @param args
     * @param element
     * @param scriptContainer
     * @param editingDomain
     * @param packageScopeEnumCache
     */
    public ScriptBOMReferenceChange(String oldScript, RenameArguments args,
            EObject element, EObject scriptContainer,
            EditingDomain editingDomain,
            PackageScopeEnumCache packageScopeEnumCache) {
        this.oldScript = oldScript;
        this.args = args;
        this.element = element;
        this.scriptContainer = scriptContainer;
        this.editingDomain = editingDomain;
        this.packageScopeEnumCache = packageScopeEnumCache;
        this.newScript = performScriptModifications();
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {
        String message = Messages.ScriptBOMReferenceChange_RenamingScript;
        String scriptName = getScriptName();
        if (scriptName == null) {
            scriptName = "";//$NON-NLS-1$
        }
        return String.format(message, scriptName);
    }

    protected abstract String getScriptName();

    @Override
    public void initializeValidationData(IProgressMonitor pm) {

    }

    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        Command command = getRefactorCommand();
        RefactoringStatus status = new RefactoringStatus();
        final WorkingCopy wc = getWorkingCopy();
        RefactoringStatusContext context = new RefactoringStatusContext() {
            @Override
            public Object getCorrespondingElement() {
                return wc;
            }
        };

        pm.beginTask(String
                .format(Messages.BOMReferenceChange_ValidatingChange, getName()),
                2);

        if (!wc.isExist()) {
            status.addError(String
                    .format(Messages.BOMReferenceChange_ResourceDoesnotExist,
                            wc.getName()), context);
        } else if (wc.isInvalidFile()) {
            status.addWarning(String
                    .format(Messages.BOMReferenceChange_CannotUpdate,
                            wc.getName()), context);
        } else if (wc.isReadOnly()) {
            status.addWarning(String
                    .format(Messages.BOMReferenceChange_ReadOnly, wc.getName()),
                    context);
        }
        pm.worked(1);

        if (command == null) {
            status.addError(String
                    .format(Messages.BOMReferenceChange_NoCommandFound,
                            getName()), context);
        } else if (!command.canExecute()) {
            status.addError(String
                    .format(Messages.BOMReferenceChange_NoCommandSet, getName()),
                    context);
        }
        pm.worked(1);

        return status;
    }

    private WorkingCopy getWorkingCopy() {
        return WorkingCopyUtil.getWorkingCopyFor(scriptContainer);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {
        if (isValid(pm).isOK()) {
            pm.beginTask(String
                    .format(Messages.ScriptBOMReferenceChange_UpdatingScript,
                            getName()), 1);
            Command cmd = getRefactorCommand();
            if (cmd != null && cmd.canExecute()) {
                WorkingCopy wc = getWorkingCopy();
                if (wc != null) {
                    boolean workingCopyDirty = wc.isWorkingCopyDirty();
                    editingDomain.getCommandStack().execute(cmd);
                    if (!workingCopyDirty) {
                        try {
                            wc.save();
                        } catch (IOException e) {
                            XpdResourcesPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
            pm.worked(1);
        }
        return null;
    }

    public boolean containsModifications() {
        /*
         * XPD-7901: trim the old and new script and then compare them so that
         * whitespaces in the start and end do not affect the decision whether
         * the script was really modified or not.
         */
        if (oldScript != null && newScript != null
                && !oldScript.trim().equals(newScript.trim())) {
            return true;
        }
        return false;
    }

    public PreviewDescription getCurrentValue() {
        return new PreviewDescription(Messages.BOMReferenceChange_CurrentValue,
                oldScript);
    }

    public PreviewDescription getRefactoredValue() {
        return new PreviewDescription(
                Messages.BOMReferenceChange_RefactoredValue, newScript);
    }

    private String performScriptModifications() {
        String oldName = null;
        String newName = args.getNewName();
        if (element instanceof NamedElement) {
            oldName = ((NamedElement) element).getName();

        }
        if (oldName != null && newName != null) {
            return ScriptBOMElementRefactorHelper
                    .replaceReferenceChangesForElement(oldScript,
                            oldName,
                            newName,
                            element,
                            scriptContainer,
                            getScriptType(),
                            getVarNameResolver(),
                            packageScopeEnumCache);
        }
        return null;
    }

    protected abstract Command getRefactorCommand();

    protected abstract String getScriptType();

    protected IVarNameResolver getVarNameResolver() {
        return new DefaultVarNameResolver();
    }

    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    protected String getNewScript() {
        return newScript;
    }

    protected EObject getScriptContainer() {
        return scriptContainer;
    }

    protected String getScriptGrammar() {
        return JsConsts.JAVASCRIPT_GRAMMAR;
    }

    protected Process getProcess() {
        if (getScriptContainer() != null) {
            return Xpdl2ModelUtil.getProcess(getScriptContainer());
        }
        return null;
    }

}