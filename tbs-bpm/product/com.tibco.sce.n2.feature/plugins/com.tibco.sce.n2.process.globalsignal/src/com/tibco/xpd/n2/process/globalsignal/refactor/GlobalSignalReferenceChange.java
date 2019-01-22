/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.refactoring.PreviewDescription;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to track the change in the catch/throw event activity reference to a
 * global signal.
 * 
 * @author sajain
 * @since May 18, 2015
 */
public class GlobalSignalReferenceChange extends Change {

    /**
     * Old name of global signal.
     */
    private String oldGlobalSignalName;

    /**
     * New name of global signal.
     */
    private static String newGlobalSignalName;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * GSD element being renamed.
     */
    private EObject element;

    /**
     * Activity referencing Global signal.
     */
    private Activity activity;

    /**
     * Editing domain.
     */
    private EditingDomain editingDomain;

    /**
     * Class to track the change in the catch/throw event activity reference to
     * a global signal.
     * 
     * @param oldGlobalSignalName
     * @param args
     * @param element
     * @param activity
     * @param editingDomain
     */
    public GlobalSignalReferenceChange(String oldGlobalSignalName,
            RenameArguments args, EObject element, Activity activity,
            EditingDomain editingDomain) {

        this.oldGlobalSignalName = oldGlobalSignalName;
        this.args = args;
        this.element = element;
        this.activity = activity;
        this.editingDomain = editingDomain;

        GlobalSignalReferenceChange.newGlobalSignalName =
                modifyGlobalSignalName();
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {

        return Messages.GlobalSignalReferenceChange_Name;
    }

    @Override
    public void initializeValidationData(IProgressMonitor pm) {

        // Nothing to do here.

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

    private WorkingCopy getWorkingCopy() {
        return WorkingCopyUtil.getWorkingCopyFor(activity);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        if (isValid(pm).isOK()) {

            pm.beginTask(String
                    .format(Messages.GlobalSignalReferenceChange_ProgressMonitor_Label,
                            getName()),
                    1);

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
     * Return <code>true</code> if there are modifications in the global signal
     * name i.e., the old and new names are different, <code>false</code>
     * otherwise.
     * 
     * @return <code>true</code> if there are modifications in the global signal
     *         name i.e., the old and new names are different,
     *         <code>false</code> otherwise.
     */
    public boolean containsModifications() {

        if (oldGlobalSignalName != null && newGlobalSignalName != null
                && !oldGlobalSignalName.equals(newGlobalSignalName)) {

            return true;

        }

        return false;
    }

    /**
     * Get current name of global signal.
     * 
     * @return Current name of global signal.
     */
    public PreviewDescription getCurrentValue() {

        return new PreviewDescription(Messages.GSDReferenceChange_CurrentValue,
                oldGlobalSignalName);
    }

    /**
     * Get preview description of the refactored name of global signal.
     * 
     * @return Preview description of the refactored name of global signal.
     */
    public PreviewDescription getRefactoredValue() {

        return new PreviewDescription(
                Messages.GSDReferenceChange_RefactoredValue,
                newGlobalSignalName);
    }

    /**
     * Perform the modifications in script according to the element name change.
     * 
     * @return Modified script.
     */
    private String modifyGlobalSignalName() {

        return args.getNewName();
    }

    /**
     * Get command to refactor global signal references.
     * 
     * @return Command to refactor global signal references.
     */
    protected Command getRefactorCommand() {

        if (activity != null) {

            com.tibco.xpd.xpdl2.Event event = activity.getEvent();

            if (event != null) {

                TriggerResultSignal trs = null;

                if (event instanceof IntermediateEvent) {

                    IntermediateEvent intermediateEvent =
                            (IntermediateEvent) event;

                    trs = intermediateEvent.getTriggerResultSignal();

                } else if (event instanceof StartEvent) {

                    StartEvent startEvent = (StartEvent) event;

                    trs = startEvent.getTriggerResultSignal();
                }

                if (trs != null) {

                    return SetCommand
                            .create(editingDomain,
                                    trs,
                                    Xpdl2Package.eINSTANCE
                                            .getTriggerResultSignal_Name(),
                                    getGlobalSignalQualifiedName((GlobalSignal) element));
                }
            }
        }

        return null;
    }

    /**
     * Gets the internal model name (as per the new name) for the passed global
     * signal(i.e.
     * projectId/specialFolderRelativePathWithGsdExtension#signalName) else
     * return <code> null</code> if the signal is not in GSD Special folder(for
     * its sub-folders).
     * 
     * @param globalSignal
     *            the global signal
     * 
     * @return the internal model name (as per the new name) for the passed
     *         gloabal signal(i.e.
     *         projectId/specialFolderRelativePathWithGsdExtension#signalName)
     *         else return <code> null</code> if the signal is not in GSD
     *         Special folder(for its sub-folders).
     */
    public static String getGlobalSignalQualifiedName(GlobalSignal globalSignal) {

        String signalModelName = null;

        IProject project = WorkingCopyUtil.getProjectFor(globalSignal);

        IPath specialFolderRelativePath =
                SpecialFolderUtil.getSpecialFolderRelativePath(globalSignal,
                        GsdConstants.GSD_SPECIAL_FOLDER_KIND);

        if (specialFolderRelativePath != null
                && !specialFolderRelativePath.isEmpty() && project != null) {

            String specialFolderGsdPathString =
                    specialFolderRelativePath.toString();

            specialFolderGsdPathString =
                    specialFolderGsdPathString.substring(0,
                            specialFolderGsdPathString.length());

            signalModelName =
                    ProjectUtil.getProjectId(project)
                            + "/" + specialFolderGsdPathString //$NON-NLS-1$
                            + "#" + newGlobalSignalName; //$NON-NLS-1$

        }

        return signalModelName;
    }

    /**
     * Get editing domain.
     * 
     * @return Editing domain.
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Get new name of global signal.
     * 
     * @return New name of global signal.
     */
    protected String getNewGlobalSignalName() {
        return newGlobalSignalName;
    }

    /**
     * Get activity.
     * 
     * @return Activity.
     */
    protected EObject getActivity() {
        return activity;
    }

    /**
     * Get process containing the activity.
     * 
     * @return Process containing the activity.
     */
    protected Process getProcess() {

        if (getActivity() != null) {

            return Xpdl2ModelUtil.getProcess(getActivity());

        }

        return null;
    }
}
