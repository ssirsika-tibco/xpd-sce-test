/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Change to update the reference in the XPDL after the referenced resource is
 * renamed or moved.
 * 
 * @author njpatel
 */
public abstract class UpdateReferenceChange extends Change {

    // Working copy of the xpdl file being updated
    protected Xpdl2WorkingCopyImpl wc;

    // Package of the model being updated
    protected Package pkg;

    // Referenced files that are being renamed/moved
    protected IFile[] currentFiles;

    // The referenced files after their rename/move
    protected IFile[] refactoredFiles;

    protected final TransactionalEditingDomain editingDomain;

    /**
     * Change to update reference in XPDL model after referenced resource is
     * renamed/moved.
     */
    public UpdateReferenceChange() {
        editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * Change to update reference in XPDL model after referenced resource is
     * renamed/moved.
     * 
     * @param wc
     *            working copy of XPDL being refactored
     * @param currentFile
     *            referenced file being renamed/moved
     * @param refactoredFile
     *            referenced file after rename/move
     */
    public UpdateReferenceChange(Xpdl2WorkingCopyImpl wc, IFile[] currentFiles,
            IFile[] refactoredFiles) {
        this();
        setWorkingCopy(wc);
        setCurrentFiles(currentFiles);
        setRefactoredFiles(refactoredFiles);
    }

    /**
     * Get the special folder relative path of the given resource. If the
     * resource is not contained in a special folder then the project relative
     * path will be returned.
     * 
     * @param resource
     * @return
     */
    protected IPath getSpecialFolderRelativePath(IResource resource) {
        IPath path = null;

        /*
         * Not using SpecialFolderUtil.getSpecialFolderRelativePath as that
         * relies on the special folder file binding which some resource kinds
         * haven't got registered, etc wsdl.
         */
        SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(resource);
        if (sf != null) {
            path = SpecialFolderUtil.getSpecialFolderRelativePath(sf, resource);
        } else {
            path = resource.getProjectRelativePath();
        }

        return path;
    }

    /**
     * Set the working copy of the XPDL being refactored.
     * 
     * @param wc
     */
    public void setWorkingCopy(Xpdl2WorkingCopyImpl wc) {
        this.wc = wc;
    }

    /**
     * Set the referenced file that is being moved/renamed.
     * 
     * @param file
     *            the file being refactored
     */
    public void setCurrentFiles(IFile[] files) {
        this.currentFiles = files;
    }

    /**
     * Set the referenced file as it will become after the rename/move.
     * 
     * @param file
     *            the file after refactor
     */
    public void setRefactoredFiles(IFile[] files) {
        this.refactoredFiles = files;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return String.format(Messages.UpdateReferenceChange_change_longdesc,
                wc.getName());
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     */
    @Override
    public final Change perform(IProgressMonitor pm) throws CoreException {
        Assert.isNotNull(currentFiles);
        Assert.isNotNull(refactoredFiles);

        List<Command> cmds = new ArrayList<Command>();

        if (pm != null) {
            pm = SubProgressMonitorEx.createNestedSubProgressMonitor(pm, 1);
        } else {
            pm = new NullProgressMonitor();
        }

        /*
         * Get the change commands for each file that has been renamed/moved
         */
        pm.beginTask(Messages.UpdateReferenceChange_updateReference_progress_shortdesc,
                currentFiles.length);
        for (int idx = 0; idx < currentFiles.length; idx++) {
            IFile currentFile = currentFiles[idx];
            IFile refactoredFile = refactoredFiles[idx];

            List<Command> res =
                    perform(SubProgressMonitorEx.createSubTaskProgressMonitor(pm,
                            1),
                            currentFile,
                            refactoredFile);
            if (res != null && !res.isEmpty()) {
                cmds.addAll(res);
            }
            pm.worked(1);
        }
        try {
            if (!cmds.isEmpty()) {
                CompoundCommand ccmd = new CompoundCommand(cmds);
                if (ccmd.canExecute()) {
                    executeCommandAndSaveWorkingCopy(ccmd);

                    /*
                     * Create an instance of this class and reverse the current
                     * and refactor files for the undo change.
                     */
                    UpdateReferenceChange instance;
                    try {
                        instance = getClass().newInstance();
                        instance.setWorkingCopy(wc);
                        instance.setCurrentFiles(refactoredFiles);
                        instance.setRefactoredFiles(currentFiles);

                        return instance;
                    } catch (Exception e) {
                        Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                .error(e);
                    }
                }
            }
        } finally {
            pm.done();
        }

        return new NullChange();
    }

    /**
     * Get the commands to facilitate the refactor for the change of the given
     * "currentFile" to "refactoredFile" through rename or move.
     * 
     * @param pm
     * @param currentFile
     *            file being renamed/moved.
     * @param refactoredFile
     *            the file as it will be after the change.
     * @return list of commands.
     */
    protected abstract List<Command> perform(IProgressMonitor pm,
            IFile currentFile, IFile refactoredFile);

    // /**
    // * @param project
    // * @param project2
    // * @throws CoreException
    // */
    // private void performProjectReference(IProject project,
    // IProject referenceProject) throws CoreException {
    // // Set the reference
    // IProjectDescription description = project.getDescription();
    // IProject[] referencedProjects = description.getReferencedProjects();
    // IProject[] newReferenceList;
    // if (referencedProjects.length > 0) {
    // newReferenceList =
    // Arrays.copyOf(referencedProjects,
    // referencedProjects.length + 1);
    // newReferenceList[newReferenceList.length - 1] = referenceProject;
    // } else {
    // newReferenceList = new IProject[] { referenceProject };
    // }
    // description.setReferencedProjects(newReferenceList);
    // project.setDescription(description, null);
    // }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        Assert.isNotNull(wc);
        Assert.isNotNull(currentFiles);
        Assert.isNotNull(refactoredFiles);
        if (wc.getRootElement() instanceof Package) {
            pkg = (Package) wc.getRootElement();
        }
        if (pm != null) {
            pm.done();
        }
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        try {
            /*
             * Working copy cannot be in a dirty state
             */
            if (wc.isWorkingCopyDirty()) {
                return RefactoringStatus
                        .createErrorStatus(String
                                .format(Messages.UpdateReferenceChange_workingCopyNeedsSaving_error_longdesc,
                                        wc.getName()));
            } else if (pkg == null) {
                return RefactoringStatus
                        .createErrorStatus(String
                                .format(Messages.UpdateReferenceChange_unableToAccessPackage_error_longdesc,
                                        wc.getName()));
            }
        } finally {
            if (pm != null) {
                pm.done();
            }
        }

        return RefactoringStatus.create(Status.OK_STATUS);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {
        return pkg;
    }

    /**
     * Execute command in the shared editing domain without affecting the
     * undo/redo stacks.
     * 
     * @see #executeCommandAndSaveWorkingCopy(Command)
     * 
     * @param cmd
     * @return result of the command execution.
     * @throws InterruptedException
     * @throws RollbackException
     */
    protected Object executeCommand(final Command cmd)
            throws InterruptedException, RollbackException {
        if (cmd != null && cmd.canExecute()) {
            // Start an unprotected write transaction
            Map<Object, Object> options =
                    Collections
                            .<Object, Object> singletonMap(Transaction.OPTION_UNPROTECTED,
                                    Boolean.TRUE);

            Transaction transaction = null;
            try {
                transaction =
                        ((InternalTransactionalEditingDomain) editingDomain)
                                .startTransaction(false, options);
                cmd.execute();
                return cmd.getResult();
            } finally {
                if (transaction != null) {
                    transaction.commit();
                }
            }
        }
        return null;
    }

    /**
     * Execute the given command and save the working copy (if command executed
     * successfully).
     * 
     * @param cmd
     * @return
     * @throws CoreException
     */
    protected Object executeCommandAndSaveWorkingCopy(final Command cmd)
            throws CoreException {
        final Object[] res = new Object[1];
        /*
         * XPD-6816: executeCommand(cmd) was moved to the main thread as the
         * update of wsdl references was running in a emf transaction running in
         * the ModelContextThread, then notification were fired (synchronized to
         * the main thread) and XPDL indexer was trying to load the updated WSDL
         * WC in the new EMF transaction (from the main thread this time). This
         * was leading to the deadlock and hang of studio. Now both transactions
         * should be started in the main thread so they don't deadlock.
         */
        XpdResourcesPlugin.getStandardDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    res[0] = executeCommand(cmd);
                } catch (Exception e) {
                    throw new RuntimeException(
                            new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            Xpdl2ResourcesPlugin.PLUGIN_ID,
                                            String.format(Messages.UpdateReferenceChange_refactorFailed_error_longdesc,
                                                    wc.getName()), e)));
                }
            }
        });
        try {
            wc.save();
        } catch (IOException e) {
            new CoreException(
                    new Status(
                            IStatus.ERROR,
                            Xpdl2ResourcesPlugin.PLUGIN_ID,
                            String.format(Messages.UpdateReferenceChange_refactorFailed_error_longdesc,
                                    wc.getName()), e));
        }

        return res[0];

    }
}
