/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.bom.resources.internal.refactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;

/**
 * BOM file move participant. This will ensure all references to the BOM (from
 * other BOMs) are updated with the new file path (if the move is within the
 * special folder).
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class BOMMoveParticipant extends MoveParticipant {

    private final BOMRefactorHelper helper;

    /**
     * BOMs affected by this move.
     */
    private Set<BOMWorkingCopy> affectedBOMs;

    /**
     * The Projects that the target project should reference post move.
     */
    private Set<IProject> bomProjectsToReference;

    /**
     * All projects that are affected by this move. XPD-7522: Changed name to
     * 'affectedBomProjects' as the previous name was confusing.
     */
    private Set<IProject> affectedBomProjects;

    private Set<IProject> allProjectsToAddReferenceFrom;

    /**
     * BOM files that have been moved
     */
    private IFile[] oldBOMFiles;

    /**
     * The BOM files as they will be after the move
     */
    private IFile[] newBOMFiles;

    /**
     * 
     */
    public BOMMoveParticipant() {
        helper = new BOMRefactorHelper();
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        IFolder targetFolder = null;
        if (getArguments().getDestination() instanceof IFolder) {
            IFolder target = (IFolder) getArguments().getDestination();
            /*
             * Can only refactor if the file is moved into a target folder that
             * is contained in a BOM special folder
             */
            if (helper.isTargetInBOMSpecialFolder(target)) {
                targetFolder = target;
            }
        }

        if (targetFolder != null) {
            if (element instanceof IFile) {
                IFile currentFile = (IFile) element;
                IFile newFile = targetFolder.getFile(currentFile.getName());
                /*
                 * If move is in same project and relative path of the file
                 * being moved does not change then no refactor required.
                 */
                if (currentFile.getProject().equals(targetFolder.getProject())
                        && BOMRefactorHelper
                                .isRelativePathsIdentical(currentFile, newFile)) {
                    return false;
                }

                affectedBOMs = helper.getAllAffectedBOMs(currentFile);
                affectedBomProjects = getAffectedProjects(affectedBOMs);
                bomProjectsToReference =
                        helper.getAllBOMProjectsToReference(currentFile);
                allProjectsToAddReferenceFrom =
                        helper.getAllProjectsToAddReferenceFrom(currentFile);

                /*
                 * If the move of this file results in a file that has the same
                 * special folder relative path then if all affected projects
                 * already reference the target project then no refactoring is
                 * required.
                 */
                try {
                    if (BOMRefactorHelper.isRelativePathsIdentical(currentFile,
                            newFile)
                            && allProjectsHaveReference(affectedBomProjects,
                                    targetFolder.getProject(),
                                    true)
                            && allProjectsHaveReference(bomProjectsToReference,
                                    targetFolder.getProject(),
                                    false)) {
                        // No refactor required
                        return false;
                    }
                } catch (CoreException e) {

                    e.printStackTrace();
                }

                oldBOMFiles = new IFile[] { currentFile };
                newBOMFiles = new IFile[] { newFile };
            } else if (element instanceof IFolder) {
                IFolder currentFolder = (IFolder) element;
                IFolder newFolder =
                        targetFolder.getFolder(currentFolder.getName());

                /*
                 * If move is in same project and relative path of the folder
                 * being moved does not change then no refactor required.
                 */
                if (currentFolder.getProject()
                        .equals(targetFolder.getProject())
                        && BOMRefactorHelper
                                .isRelativePathsIdentical(currentFolder,
                                        newFolder)) {
                    return false;
                }

                try {
                    List<IFile> bomFiles =
                            helper.getBOMFiles((IFolder) element);
                    oldBOMFiles = bomFiles.toArray(new IFile[bomFiles.size()]);
                    newBOMFiles =
                            helper.getMovedBomFileHandles(oldBOMFiles,
                                    currentFolder,
                                    targetFolder);

                    if (!bomFiles.isEmpty()) {
                        affectedBOMs = new HashSet<BOMWorkingCopy>();

                        if (bomProjectsToReference == null) {
                            bomProjectsToReference = new HashSet<IProject>();
                        }

                        if (allProjectsToAddReferenceFrom == null) {
                            allProjectsToAddReferenceFrom =
                                    new HashSet<IProject>();
                        }

                        for (IFile bomFile : bomFiles) {
                            affectedBOMs.addAll(helper
                                    .getAllAffectedBOMs(bomFile));

                            bomProjectsToReference.addAll(helper
                                    .getAllBOMProjectsToReference(bomFile));

                            allProjectsToAddReferenceFrom.addAll(helper
                                    .getAllProjectsToAddReferenceFrom(bomFile));
                        }

                        affectedBomProjects = getAffectedProjects(affectedBOMs);

                        /*
                         * If the folder is being moved to another project and
                         * the special folder relative path of the folder does
                         * not change then, if all affected projects already
                         * reference the target project then no refactor is
                         * required.
                         */
                        if (BOMRefactorHelper
                                .isRelativePathsIdentical(currentFolder,
                                        newFolder)
                                && allProjectsHaveReference(affectedBomProjects,
                                        targetFolder.getProject(),
                                        true)
                                && allProjectsHaveReference(bomProjectsToReference,
                                        targetFolder.getProject(),
                                        false)) {
                            return false;
                        }
                    }

                } catch (CoreException e) {
                    BOMResourcesPlugin.getDefault().getLogger().error(e);
                }

            }
        }
        return (affectedBOMs != null && !affectedBOMs.isEmpty()
                && oldBOMFiles.length > 0 && newBOMFiles.length > 0)
                || (bomProjectsToReference != null && !bomProjectsToReference
                        .isEmpty());
    }

    /**
     * Check if all the projects in the set have a reference to OR from the
     * targetProject based upon the value of the 'referenceToProject' parameter
     * 
     * @param projects
     * @param targetProject
     * @param referenceToProject
     *            if passed <code>true</code> this method will check if all the
     *            passed projects reference the target project, else if passed
     *            <code>false</code> this method will check if the target
     *            project references all the passed projects
     * @return
     * @throws CoreException
     */
    private boolean allProjectsHaveReference(Set<IProject> projects,
            IProject targetProject, boolean referenceToProject)
            throws CoreException {

        if (referenceToProject) {
            for (IProject project : projects) {
                if (!ProjectUtil.isProjectReferenced(project, targetProject)) {
                    return false;
                }
            }
        } else {
            for (IProject project : projects) {
                if (!ProjectUtil.isProjectReferenced(targetProject, project)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.BOMMoveParticipant_shortdesc;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        /*
         * Make sure none of the affected working copies are dirty - cannot
         * refactor a dirty model.
         */
        RefactoringStatus status =
                helper.checkWorkingCopyDirtyConditions(pm,
                        context,
                        affectedBOMs);

        if (status.isOK()) {
            /*
             * If moving the resource then check that any project references
             * that are added won't end up in a cyclic reference.
             */
            IProject targetProject =
                    ((IFolder) getArguments().getDestination()).getProject();

            /*
             * Check if adding project reference to target project will create
             * cyclic dependency.
             */
            for (IProject project : affectedBomProjects) {
                if (!project.equals(targetProject)) {
                    /*
                     * Affected project will have to reference target project so
                     * if target project already references affected project
                     * then there will be a cyclic reference created.
                     */
                    if (willCauseCyclicReference(project, targetProject)) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.BOMMoveParticipant_cyclicReference_error_longdesc,
                                                project.getName(),
                                                targetProject.getName()));
                    }
                }
            }
            /*
             * XPD-7522: Check if adding reference from the target project to
             * other projects will cause cyclic dependency.
             */
            for (IProject projectToReference : bomProjectsToReference) {
                if (!projectToReference.equals(targetProject)) {
                    if (willCauseCyclicReference(targetProject,
                            projectToReference)) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.BOMMoveParticipant_cyclicReference_error_longdesc,
                                                projectToReference.getName(),
                                                targetProject.getName()));
                    }
                }
            }
            /*
             * XPD-7522: This is a special case. Finally check if moving the BOM
             * does not require adding reference to AND from the same project.
             * eg moving BOM 'myBom'(from project A to B) requires XPDL in
             * project A to reference myBom in Project B at the same time myBom
             * needs to reference a BOM in Project A. We need to this extra
             * check over here because the BOM move participant for updating
             * xpdl stuff is XpdlReferenceMoveParticipant and hence these 2
             * participants wouldn't know about each other.
             */
            for (IProject projectThatMustRefTargetProject : allProjectsToAddReferenceFrom) {
                if (bomProjectsToReference
                        .contains(projectThatMustRefTargetProject)) {
                    return RefactoringStatus
                            .createFatalErrorStatus(String
                                    .format(Messages.BOMMoveParticipant_CyclicProjectReferenceError_msg,
                                            projectThatMustRefTargetProject
                                                    .getName(),
                                            targetProject.getName()));
                }
            }
        }
        return status;
    }

    /**
     * @param affectedBOMs2
     * @return
     */
    private Set<IProject> getAffectedProjects(Set<BOMWorkingCopy> workingCopies) {
        Set<IProject> projects = new HashSet<IProject>();

        for (BOMWorkingCopy wc : workingCopies) {
            IResource resource = wc.getEclipseResources().get(0);
            projects.add(resource.getProject());
        }
        return projects;
    }

    /**
     * Check whether by setting the given referenced project we will end up with
     * a cyclic reference.
     * 
     * @param project
     * @param referencedProject
     * @return
     */
    private boolean willCauseCyclicReference(IProject project,
            IProject referencedProject) {

        try {
            Set<IProject> refProjects =
                    ProjectUtil2
                            .getReferencedProjectsHierarchy(referencedProject,
                                    true);
            for (IProject ref : refProjects) {
                if (ref.equals(project)) {
                    return true;
                }
            }
        } catch (CyclicDependencyException e) {
            // Do nothing - there should already be validation markers showing
            // this problem
        }
        return false;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        List<Change> changes = new ArrayList<Change>();

        /*
         * First check if we need to add any project references.
         */
        IProject refProject =
                ((IFolder) getArguments().getDestination()).getProject();
        for (IProject project : affectedBomProjects) {
            if (!ProjectUtil.isProjectReferenced(project, refProject)) {
                changes.add(new AddProjectReferenceChange(project, refProject));
            }
        }
        /*
         * XPD-7522: Check and add project references from BOM project to other
         * projects
         */
        for (IProject project : bomProjectsToReference) {
            if (!ProjectUtil.isProjectReferenced(refProject, project)) {
                changes.add(new AddProjectReferenceChange(refProject, project));
            }
        }

        /*
         * Create a change for each working copy that needs to be updated with
         * the new reference.
         */
        changes.addAll(helper.createUpdateReferenceChanges(pm,
                affectedBOMs,
                oldBOMFiles,
                newBOMFiles));

        return new CompositeChange(
                Messages.BOMRefactorHelper_updateReferenceInModels_change_shortdesc,
                changes.toArray(new Change[changes.size()]));
    }

    /**
     * Change to update the project reference.
     * 
     */
    private class AddProjectReferenceChange extends Change {

        private final IProject project;

        private final IProject refProject;

        /**
         * 
         */
        public AddProjectReferenceChange(IProject project, IProject refProject) {
            this.project = project;
            this.refProject = refProject;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getName()
         * 
         * @return
         */
        @Override
        public String getName() {
            return String
                    .format(Messages.BOMMoveParticipant_updateReference_change_shortdesc,
                            project.getName(),
                            refProject.getName());
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         */
        @Override
        public void initializeValidationData(IProgressMonitor pm) {
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
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            if (pm != null) {
                pm.done();
            }
            return new RefactoringStatus();
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         */
        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {

            ProjectUtil.addReferenceProject(project, refProject);

            if (pm != null) {
                pm.done();
            }

            // Shouldn't undo this
            return new NullChange();
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
         * 
         * @return
         */
        @Override
        public Object getModifiedElement() {
            return project;
        }

    }

}
