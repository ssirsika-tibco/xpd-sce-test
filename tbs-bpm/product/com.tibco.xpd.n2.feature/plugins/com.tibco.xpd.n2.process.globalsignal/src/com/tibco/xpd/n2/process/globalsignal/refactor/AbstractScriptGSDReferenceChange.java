/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

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

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.ui.refactor.GSDElementRefactorHelper;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.refactoring.PreviewDescription;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;

/**
 * Base class to provide the change that has occured in a script referencing a
 * GSD element(s).
 * 
 * @author sajain
 * @since May 13, 2015
 */
public abstract class AbstractScriptGSDReferenceChange extends Change {

    /**
     * Old script.
     */
    private String oldScript;

    /**
     * New script.
     */
    private String newScript;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * GSD element being renamed.
     */
    private EObject element;

    /**
     * Script container.
     */
    private EObject scriptContainer;

    /**
     * Editing domain.
     */
    private EditingDomain editingDomain;

    /**
     * Payload prefix.
     */
    private static final String SIGNAL_PAYLOAD_DATA_PREFIX = "SIGNAL_"; //$NON-NLS-1$

    /**
     * Class to provide the change that has occured in a script referencing a
     * GSD element(s).
     * 
     * @param oldScript
     * @param args
     * @param element
     * @param scriptContainer
     * @param editingDomain
     */
    public AbstractScriptGSDReferenceChange(String oldScript,
            RenameArguments args, EObject element, EObject scriptContainer,
            EditingDomain editingDomain) {

        this.oldScript = oldScript;
        this.args = args;
        this.element = element;
        this.scriptContainer = scriptContainer;
        this.editingDomain = editingDomain;
        this.newScript = performScriptModifications();
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {

        String message = Messages.ScriptGSDReferenceChange_RenamingScript;

        String scriptName = getScriptName();

        if (scriptName == null) {
            scriptName = "";//$NON-NLS-1$
        }

        return String.format(message, scriptName);
    }

    /**
     * Get script name.
     * 
     * @return Script name.
     */
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
                .format(Messages.GSDReferenceChange_ValidatingChange, getName()),
                2);

        if (!wc.isExist()) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_ResourceDoesnotExist,
                            wc.getName()), context);

        } else if (wc.isInvalidFile()) {

            status.addWarning(String
                    .format(Messages.GSDReferenceChange_CannotUpdate,
                            wc.getName()), context);

        } else if (wc.isReadOnly()) {

            status.addWarning(String
                    .format(Messages.GSDReferenceChange_ReadOnly, wc.getName()),
                    context);

        }
        pm.worked(1);

        if (command == null) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_NoCommandFound,
                            getName()), context);

        } else if (!command.canExecute()) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_NoCommandSet, getName()),
                    context);

        }
        pm.worked(1);

        return status;
    }

    /**
     * Get working copy of the script container.
     * 
     * @return Working copy of the script container.
     */
    private WorkingCopy getWorkingCopy() {

        return WorkingCopyUtil.getWorkingCopyFor(scriptContainer);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        if (isValid(pm).isOK()) {

            pm.beginTask(String
                    .format(Messages.ScriptGSDReferenceChange_UpdatingScript,
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

    /**
     * Return <code>true</code> if there are modifications in the script i.e.,
     * the old and new script are different, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if there are modifications in the script i.e.,
     *         the old and new script are different, <code>false</code>
     *         otherwise.
     */
    public boolean containsModifications() {

        if (oldScript != null && newScript != null
                && !oldScript.equals(newScript)) {

            return true;

        }

        return false;
    }

    /**
     * Get the preview description of the current value.
     * 
     * @return The preview description of the current value.
     */
    public PreviewDescription getCurrentValue() {

        return new PreviewDescription(Messages.GSDReferenceChange_CurrentValue,
                oldScript);
    }

    /**
     * Get the preview description of the refactored value.
     * 
     * @return The preview description of the refactored value.
     */
    public PreviewDescription getRefactoredValue() {

        return new PreviewDescription(
                Messages.GSDReferenceChange_RefactoredValue, newScript);
    }

    /**
     * Perform the modifications in script according to the element name change.
     * 
     * @return Modified script.
     */
    private String performScriptModifications() {

        if (element instanceof PayloadDataField) {

            String oldName =
                    SIGNAL_PAYLOAD_DATA_PREFIX
                            + ((PayloadDataField) element).getName();

            String newName = SIGNAL_PAYLOAD_DATA_PREFIX + args.getNewName();

            if (oldName != null && newName != null) {

                return GSDElementRefactorHelper
                        .replaceReferenceChangesForElement(oldScript,
                                oldName,
                                newName,
                                element,
                                scriptContainer,
                                getScriptType(),
                                getVarNameResolver());
            }
        }

        return null;
    }

    /**
     * Get command to refactor the script references to GSD element (Payload
     * data).
     * 
     * @return Command to refactor the script references to GSD element (Payload
     *         data).
     */
    protected abstract Command getRefactorCommand();

    /**
     * Get script type.
     * 
     * @return Script type.
     */
    protected abstract String getScriptType();

    /**
     * Get the default variable name resolver instance.
     * 
     * @return The default variable name resolver instance.
     */
    protected IVarNameResolver getVarNameResolver() {
        return new DefaultVarNameResolver();
    }

    /**
     * Get the editing domain.
     * 
     * @return The editing domain.
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Get the new script.
     * 
     * @return The new script.
     */
    protected String getNewScript() {
        return newScript;
    }

    /**
     * Get the script grammar.
     * 
     * @return The script grammar.
     */
    protected String getScriptGrammar() {
        return JsConsts.JAVASCRIPT_GRAMMAR;
    }
}
