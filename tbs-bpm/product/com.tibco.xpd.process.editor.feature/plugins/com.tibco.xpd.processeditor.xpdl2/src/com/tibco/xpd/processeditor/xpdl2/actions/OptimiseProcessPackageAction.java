/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceAction;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AutoInlineSubProcesses;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.CreateOptimizedPackageWizard;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblem;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.CreateOptimizedPackageWizard.InlineSubProcConfigItem;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class OptimiseProcessPackageAction extends WorkspaceAction {

    public static final String OPTIMISED_PACKAGE_FOLDERNAME =
            Messages.OptimiseProcessPackageAction_OptimizePackagesFolderName_label;

    public static final String OPTIMIZED_PKGNAME_PREFIX =
            Messages.OptimiseProcessPackageAction_OptimizedPrefix_label;

    private Xpdl2WorkingCopyImpl workingCopy;

    private IFile xpdlFile;

    private ISelectionProvider selProvider = null;

    private Resource newRes;

    public OptimiseProcessPackageAction() {
        super(Display.getCurrent().getActiveShell(),
                Messages.OptimiseProcessPackageAction_OptimizePackage_menu);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.IMG_OPTIMISEPKG));
    }

    @Override
    public void run() {
        if (workingCopy != null && xpdlFile != null) {
            Shell activeShell = Display.getCurrent().getActiveShell();

            //
            // Make sure original package is saved...
            if (workingCopy.isWorkingCopyDirty()) {
                if (!MessageDialog
                        .openConfirm(activeShell,
                                Messages.OptimiseProcessPackageAction_SaveProcPackage_title,
                                Messages.OptimiseProcessPackageAction_SaveProcPackage_longdesc)) {
                    // User cancelled.
                    return;
                }

                if (!saveOriginalPackage(activeShell, workingCopy)) {
                    return;
                }
            }

            Package pkg = (Package) workingCopy.getRootElement();

            //
            // Create and run a wizard that will analyse package and allow user
            // to select processes to inline.
            CreateOptimizedPackageWizard wiz =
                    new CreateOptimizedPackageWizard(pkg);

            WizardDialog wizDig = new WizardDialog(activeShell, wiz);
            if (wizDig.open() != WizardDialog.OK) {
                // User Cancelled.
                return;
            }

            //
            // Save changes to the inline process flag statuses.
            CompoundCommand cmd = new CompoundCommand();
            cmd
                    .setLabel(Messages.OptimiseProcessPackageAction_SetInlineSubprocs_menu);
            boolean haveChanges = false;

            EditingDomain editingDomain = workingCopy.getEditingDomain();

            for (InlineSubProcConfigItem configItem : wiz
                    .getSubProcConfigItems()) {

                Process process = configItem.getProcess();
                if (AnalyseInlineSubProcesses.isInlineSubProcess(process) != configItem
                        .isChecked()) {
                    cmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            process,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_InlineSubProcess(),
                                            new Boolean(configItem.isChecked())));
                    haveChanges = true;
                }
            }

            if (haveChanges) {
                editingDomain.getCommandStack().execute(cmd);

                if (!saveOriginalPackage(activeShell, workingCopy)) {
                    return;
                }
            }

            //
            // Check if any processes with inline-related-problems have been
            // selected for inlining.
            boolean inlineProblems = false;
            for (InlineSubProcConfigItem configItem : wiz
                    .getSubProcConfigItems()) {
                if (configItem.isChecked()) {
                    if (configItem.getProblems() != null
                            && configItem.getProblems().size() > 0) {
                        inlineProblems = true;
                    }
                }
            }

            if (inlineProblems) {
                // User has selected processes for inlining that would cause
                // problems. All we can do is ask the user whether they would
                // like to set the inline status in the source package and fix
                // problems.
                MessageDialog
                        .openWarning(activeShell,
                                Messages.OptimiseProcessPackageAction_CannotCreateOptPkg_title,
                                Messages.OptimiseProcessPackageAction_CannotCreateOptPkg_longdesc);
                return;
            }

            //
            // And finally, all being well we can now create the optimised
            // package.
            final Package optimisedPkg = optimisePkg(pkg);

            if (optimisedPkg != null) {
                IFolder folder = (IFolder) xpdlFile.getParent();

                IFolder optimisedFolder = getOptimisedPackageFolder(folder);

                if (optimisedFolder != null) {
                    IFile newFile = getOptimisedPkgFile(optimisedFolder);

                    if (newFile != null) {

                        ResourceSet resSet = editingDomain.getResourceSet();

                        newRes =
                                resSet.createResource(URI.createURI(newFile
                                        .getFullPath().toString()));

                        if (editingDomain instanceof TransactionalEditingDomain) {
                            TransactionalEditingDomain ted =
                                    (TransactionalEditingDomain) editingDomain;
                            RecordingCommand recordingCmd =
                                    new RecordingCommand(ted) {

                                        @Override
                                        protected void doExecute() {
                                            newRes.getContents()
                                                    .add(optimisedPkg);
                                        }

                                    };
                            recordingCmd
                                    .setLabel(Messages.OptimiseProcessPackageAction_AddInlineContents_shortdesc);
                            if (recordingCmd.canExecute()) {
                                ted.getCommandStack().execute(recordingCmd);
                            }
                        }

                        try {
                            // Remove read only flag.
                            if (newFile.exists()) {
                                setReadOnly(newFile, false);
                            }

                            newRes.save(null);

                            setReadOnly(newFile, true);

                            if (selProvider != null) {
                                Object newSel = newFile;

                                Xpdl2WorkingCopyImpl newWc =
                                        getXpdl2WorkingCopy(newFile);
                                if (newWc != null) {
                                    newSel = newWc.getRootElement();
                                }

                                if (selProvider instanceof TreeViewer) {
                                    TreeViewer tv = (TreeViewer) selProvider;
                                    tv.refresh();
                                    tv.expandToLevel(newSel, 0);
                                }
                                selProvider
                                        .setSelection(new StructuredSelection(
                                                newSel));
                            }

                        } catch (IOException e) {
                            MessageDialog
                                    .openError(activeShell,
                                            Messages.OptimiseProcessPackageAction_OptimizeProcessPackage_Title,
                                            Messages.OptimiseProcessPackageAction_FailedToCreateFile_longdesc
                                                    + newFile.getName());
                        }
                    }
                }
            }
        }

    }

    /**
     * @param activeShell
     * @param workingCopy
     */
    private boolean saveOriginalPackage(Shell activeShell,
            Xpdl2WorkingCopyImpl workingCopy) {
        try {
            workingCopy.save();
        } catch (IOException e) {
            MessageDialog.openError(activeShell,
                    Messages.OptimiseProcessPackageAction_SaveError_title,
                    Messages.OptimiseProcessPackageAction_SaveError_longdesc);
            return false;
        }
        return true;
    }

    /**
     * @param optimisedFolder
     * @param optimisedPkgName
     * @return
     * @throws CoreException
     */
    private IFile getOptimisedPkgFile(IFolder optimisedFolder) {
        String optimisedPkgName =
                OPTIMIZED_PKGNAME_PREFIX + " " + xpdlFile.getName(); //$NON-NLS-1$

        IFile newFile = optimisedFolder.getFile(optimisedPkgName);
        return newFile;
    }

    /**
     * Set the file to be read only, therefore if after inlining there are
     * Validation Problems it at least gives the user the hint to fix them in
     * the original package.
     * 
     * @param newFile
     * @param readOnly
     * 
     */
    private void setReadOnly(IFile newFile, boolean readOnly) {

        ResourceAttributes ra = new ResourceAttributes();
        ra.setReadOnly(readOnly);
        try {
            newFile.setResourceAttributes(ra);
        } catch (CoreException e) {
            if (readOnly) {
                MessageDialog
                        .openError(Display.getCurrent().getActiveShell(),
                                Messages.OptimiseProcessPackageAction_OptimizeProcessPackage_Title,
                                Messages.OptimiseProcessPackageAction_FailedSetReadOnly_longdesc
                                        + newFile.getName());

            } else {
                MessageDialog
                        .openError(Display.getCurrent().getActiveShell(),
                                Messages.OptimiseProcessPackageAction_OptimizeProcessPackage_Title,
                                Messages.OptimiseProcessPackageAction_FailedUnsetReadOnly_longdesc
                                        + newFile.getName());

            }
        }
    }

    private IFolder getOptimisedPackageFolder(IFolder parentFolder) {
        IFolder optimisedFolder =
                parentFolder.getFolder(OPTIMISED_PACKAGE_FOLDERNAME);

        if (!optimisedFolder.exists()) {
            try {
                optimisedFolder.create(true, true, null);

            } catch (CoreException e) {
                MessageDialog
                        .openError(Display.getCurrent().getActiveShell(),
                                Messages.OptimiseProcessPackageAction_OptimizeProcessPackage_Title,
                                Messages.OptimiseProcessPackageAction_FailedToCreateFolder_longdesc
                                        + OPTIMISED_PACKAGE_FOLDERNAME);
                return null;
            }
        }
        return optimisedFolder;
    }

    private Package optimisePkg(Package pkg) {

        String problemString =
                Messages.OptimiseProcessPackageAction_FailedDuringOptimization_longdesc;
        Package optimisedPkg = null;

        // First create a copy of the package.
        Command cpyCmd =
                CopyCommand.create(workingCopy.getEditingDomain(), pkg);
        if (cpyCmd.canExecute()) {
            cpyCmd.execute();

            Collection res = cpyCmd.getResult();
            if (res != null && res.size() == 1) {
                optimisedPkg = (Package) res.iterator().next();

                // re-assign all the unique id's and references to them (to stop
                // them clashing with original).
                // But reassign by using an _Optim suffix - this guarantees
                // uniqueness from original package BUT also guarantees that the
                // result should be consistent next time we optimise the same
                // package.
                Map<String, EObject> idMap =
                        Xpdl2ModelUtil
                                .reassignUniqueIds(res,
                                        workingCopy.getEditingDomain(),
                                        AutoInlineSubProcesses.OPTIMIZED_UNIQUEID_SUFFIX);

                // Set the package name.
                String name =
                        OPTIMIZED_PKGNAME_PREFIX + " " + optimisedPkg.getName(); //$NON-NLS-1$
                optimisedPkg.setName(NameUtil.getInternalName(name, false));
                Xpdl2ModelUtil.setOtherAttribute(optimisedPkg,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        name);

                //
                // And finally OPTIMIZE
                AutoInlineSubProcesses autoInline =
                        new AutoInlineSubProcesses(workingCopy);

                List<InlineSubProcessProblem> problems =
                        autoInline.autoInlinePackage(workingCopy
                                .getEditingDomain(), pkg, optimisedPkg, idMap);

                if (problems != null && problems.size() > 0) {
                    problemString =
                            Messages.OptimiseProcessPackageAction_ProblemDuringInline_label;

                    int i = 0;
                    for (InlineSubProcessProblem problem : problems) {
                        if (problem.subProcessTask != null) {
                            Process p = problem.subProcessTask.getProcess();
                            problemString +=
                                    p.getName()
                                            + "/" + problem.subProcessTask.getName() + ": "; //$NON-NLS-1$ //$NON-NLS-2$
                        } else if (problem.subProcess != null) {
                            problemString +=
                                    problem.subProcess.getName() + ": "; //$NON-NLS-1$

                        }
                        problemString +=
                                "- " + problem.autoInlineProblemDescription //$NON-NLS-1$
                                        + "\n"; //$NON-NLS-1$

                        if (i++ > 5) {
                            problemString += "....."; //$NON-NLS-1$
                        }
                    }

                    optimisedPkg = null;
                }

            }
        }

        if (optimisedPkg == null) {
            MessageDialog
                    .openError(Display.getCurrent().getActiveShell(),
                            Messages.OptimiseProcessPackageAction_OptimizeProcessPackage_Title,
                            problemString);
        }
        return optimisedPkg;
    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (super.updateSelection(selection)) {
            workingCopy = null;
            xpdlFile = null;

            if (selection.size() == 1) {
                Object sel = selection.getFirstElement();

                if (sel instanceof IFile) {
                    workingCopy = getXpdl2WorkingCopy((IFile) sel);
                    if (workingCopy != null) {
                        xpdlFile = (IFile) sel;
                        return true;
                    }
                }
            }

        }

        return false;
    }

    private Xpdl2WorkingCopyImpl getXpdl2WorkingCopy(IFile file) {
        Object o = XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        if (o instanceof Xpdl2WorkingCopyImpl) {
            return (Xpdl2WorkingCopyImpl) o;
        }

        return null;

    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#getOperationMessage()
     * 
     * @return
     */
    @Override
    protected String getOperationMessage() {
        return Messages.OptimiseProcessPackageAction_CreateOptimizedPackage_label;
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#invokeOperation(org.eclipse.core.resources.IResource,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param resource
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void invokeOperation(IResource resource, IProgressMonitor monitor)
            throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setSelectionProvider(ISelectionProvider sp) {
        selProvider = sp;

    }

}
