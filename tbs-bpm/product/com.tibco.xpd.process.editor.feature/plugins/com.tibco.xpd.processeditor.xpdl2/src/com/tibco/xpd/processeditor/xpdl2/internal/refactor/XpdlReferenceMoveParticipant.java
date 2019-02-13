package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRefactorHelper.RefactorType;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.AddProjectReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.IRefactorReferenceChangeProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateBOMReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateOMReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateWsdlReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateXpdlReferenceChange;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Refactoring participant to update all references in an XPDL model when a
 * reference file is moved. This class should be used with a parameter to
 * indicate the type of reference it needs to deal with. For example, if dealing
 * with BOM references then this class will be called with the parameter "bom"
 * for in the class attribute of the refactor extension:
 * 
 * <pre>
 * class="com.tibco.xpd.analyst.resources.xpdl2.internal.refactor.XpdlReferenceMoveParticipant: bom"
 * </pre>
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class XpdlReferenceMoveParticipant extends MoveParticipant implements
        IRefactorReferenceChangeProvider, IExecutableExtension {

    /**
     * Refactor helper instance.
     */
    private final XpdlReferenceRefactorHelper helper;

    /**
     * XPDLs affected by this move.
     */
    private Set<Xpdl2WorkingCopyImpl> affectedXpdls;

    /**
     * All projects that are affected by this move.
     */
    private Set<IProject> affectedProjects;

    /**
     * Files that have been moved
     */
    private IFile[] oldFiles;

    /**
     * Files as they will be after the move
     */
    private IFile[] newFiles;

    /**
     * The type of file being renamed
     */
    private RefactorType type;

    /**
     * 
     */
    public XpdlReferenceMoveParticipant() {
        helper = createHelper();
    }

    /**
     * Create the participant helper class.
     * 
     * @return
     */
    protected XpdlReferenceRefactorHelper createHelper() {
        return new XpdlReferenceRefactorHelper();
    }

    @Override
    protected boolean initialize(Object element) {
        if (type == null) {
            return false;
        }

        IFolder targetFolder = null;

        // Can only refactor if target is in the right special folder
        if (getArguments().getDestination() instanceof IFolder
                && isTargetValid((IFolder) getArguments().getDestination())) {
            targetFolder = (IFolder) getArguments().getDestination();
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
                        && helper
                                .isRelativePathsIdentical(currentFile, newFile)) {
                    return false;
                }

                /*
                 * XPD-7367: Get the direct dependent xpdls as well as
                 * indirectly dependent xpdls from the generated boms.
                 */
                affectedXpdls = getAffectedOrDependentXpdls(currentFile);
                affectedProjects = getAffectedProjects(affectedXpdls);

                /*
                 * If the move of this file results in a file that has the same
                 * special folder relative path then if all affected projects
                 * already reference the target project then no refactoring is
                 * required.
                 */
                try {
                    if (helper.isRelativePathsIdentical(currentFile, newFile)
                            && allProjectsHaveReference(affectedProjects,
                                    targetFolder.getProject())) {
                        // No refactor required
                        return false;
                    }
                } catch (CoreException e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                }

                oldFiles = new IFile[] { currentFile };
                newFiles = new IFile[] { newFile };
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
                        && helper.isRelativePathsIdentical(currentFolder,
                                newFolder)) {
                    return false;
                }

                try {
                    List<IFile> files =
                            helper.getAllFiles((IFolder) element, type);
                    oldFiles = files.toArray(new IFile[files.size()]);
                    newFiles =
                            getNewFileNames(oldFiles,
                                    currentFolder,
                                    targetFolder);

                    if (!files.isEmpty()) {
                        affectedXpdls = new HashSet<Xpdl2WorkingCopyImpl>();

                        for (IFile bomFile : files) {
                            affectedXpdls.addAll(helper
                                    .getAffectedXpdls(bomFile));
                        }

                        affectedProjects = getAffectedProjects(affectedXpdls);

                        /*
                         * If the folder is being moved to another project and
                         * the special folder relative path of the folder does
                         * not change then, if all affected projects already
                         * reference the target project then no refactor is
                         * required.
                         */
                        if (helper.isRelativePathsIdentical(currentFolder,
                                newFolder)
                                && allProjectsHaveReference(affectedProjects,
                                        targetFolder.getProject())) {
                            return false;
                        }
                    }

                } catch (CoreException e) {
                    BOMResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }

        return affectedXpdls != null && !affectedXpdls.isEmpty()
                && oldFiles.length > 0 && newFiles.length > 0;
    }

    /**
     * Gets all the Xpdl files that are dependent on the file being moved,
     * incase the file being moved is a 'wsdl' file then additionally this
     * method returns the xpdls which depend on the BOMs that are generated from
     * the wsdl.
     * 
     * @param currentFile
     *            the current File that is being moved.
     * @return a Set of Xpdl files which are dependent on the file that is being
     *         refactored.
     */
    private Set<Xpdl2WorkingCopyImpl> getAffectedOrDependentXpdls(
            IFile currentFile) {

        Set<Xpdl2WorkingCopyImpl> allAffectedXpdls =
                new HashSet<Xpdl2WorkingCopyImpl>();
        /*
         * add all the xpdl's that are directly affected.
         */
        allAffectedXpdls.addAll(helper.getAffectedXpdls(currentFile));

        /*
         * Sid ACE-194 - we don't support WSDL in ACE
         */

        return allAffectedXpdls;
    }

    /**
     * Get the list of files as they will be after the move of the given folder
     * (corresponding to the files being passed in).
     * 
     * @param oldFiles
     *            bom files affected by the move of the folder
     * @param folderBeingMoved
     *            folder being moved
     * @param targetFolder
     *            the new target of the folder
     * @return
     */
    private IFile[] getNewFileNames(IFile[] oldFiles, IFolder folderBeingMoved,
            IFolder targetFolder) {
        int count = folderBeingMoved.getFullPath().segmentCount() - 1;
        IFile[] newFiles = new IFile[oldFiles.length];

        for (int idx = 0; idx < oldFiles.length; idx++) {
            newFiles[idx] =
                    targetFolder.getFile(oldFiles[idx].getFullPath()
                            .removeFirstSegments(count));
        }

        return newFiles;
    }

    /**
     * Check if all the projects in the set have a reference to the refProject.
     * 
     * @param projects
     * @param refProject
     * @return
     * @throws CoreException
     */
    private boolean allProjectsHaveReference(Set<IProject> projects,
            IProject refProject) throws CoreException {
        for (IProject project : projects) {
            if (!ProjectUtil.isProjectReferenced(project, refProject)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param affectedBOMs2
     * @return
     */
    private Set<IProject> getAffectedProjects(
            Set<Xpdl2WorkingCopyImpl> workingCopies) {
        Set<IProject> projects = new HashSet<IProject>();

        for (Xpdl2WorkingCopyImpl wc : workingCopies) {
            IResource resource = wc.getEclipseResources().get(0);
            projects.add(resource.getProject());
        }
        return projects;
    }

    /**
     * Check if the target of this move is a folder that is a special folder, or
     * contained in a special folder of the right type.
     * 
     * @param target
     * @return
     */
    private boolean isTargetValid(IResource target) {

        // Check if the target is a special folder
        if (target instanceof IFolder) {
            String kind =
                    SpecialFolderUtil.getSpecialFolderKind((IFolder) target);

            if (kind != null && kind.equals(type.getSpecialFolderKind())) {
                return true;
            }
        }

        // Check if the target is in the right special folder
        SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(target);

        if (sf != null && sf.getKind().equals(type.getSpecialFolderKind())) {
            return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return Messages.XpdlReferenceMoveParticipant_participant_shortdesc;
    }

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
                        affectedXpdls);

        if (status.isOK()) {
            /*
             * If moving the resource then check that any project references
             * that are added won't end up in a cyclic reference.
             */
            IProject targetProject =
                    ((IFolder) getArguments().getDestination()).getProject();

            for (IProject project : affectedProjects) {
                if (!project.equals(targetProject)) {
                    /*
                     * Affected project will have to reference target project so
                     * if target project already references affected project
                     * then there will be a cyclic reference created.
                     */
                    if (willCauseCyclicReference(project, targetProject)) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.XpdlReferenceMoveParticipant_cyclicReference_error_longdesc,
                                                project.getName(),
                                                targetProject.getName()));
                    }
                }
            }
        }
        return status;
    }

    @Override
    public final Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        List<Change> changes = new ArrayList<Change>();

        /*
         * First check if we need to add any project references.
         */
        IProject refProject =
                ((IFolder) getArguments().getDestination()).getProject();
        for (IProject project : affectedProjects) {
            if (!ProjectUtil.isProjectReferenced(project, refProject)) {
                changes.add(new AddProjectReferenceChange(project, refProject));
            }
        }

        /*
         * Create a change for each working copy that needs to be updated with
         * the new reference.
         */
        changes.addAll(helper.createUpdateReferenceChanges(pm,
                affectedXpdls,
                oldFiles,
                newFiles,
                this));

        return new CompositeChange(getUpdateReferencesMessage(),
                changes.toArray(new Change[changes.size()]));
    }

    /**
     * Get the update references message string to be shown in the refactor
     * preview.
     * 
     * @return The update references message string to be shown in the refactor
     *         preview.
     */
    protected String getUpdateReferencesMessage() {

        return Messages.XpdlReferenceMoveParticipant_updateReferenceChange_shortdesc;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.internal.refactor.change.IRefactorReferenceChangeProvider#createChange(com.tibco.xpd.resources.WorkingCopy,
     *      java.lang.String, java.lang.String)
     * 
     * @param wc
     * @param currentFiles
     * @param refactoredFiles
     * @return
     */
    @Override
    public UpdateReferenceChange createChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles) {

        UpdateReferenceChange change = null;

        switch (type) {
        case BOM:
            change = new UpdateBOMReferenceChange();
            break;
        case OM:
            change = new UpdateOMReferenceChange();
            break;
        case WSDL:
            change = new UpdateWsdlReferenceChange();
            break;
        case XPDL:
            change = new UpdateXpdlReferenceChange();
            break;
        }

        if (change != null) {
            change.setWorkingCopy(wc);
            change.setCurrentFiles(currentFiles);
            change.setRefactoredFiles(refactoredFiles);
        }
        return change;
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
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
     *      java.lang.String, java.lang.Object)
     * 
     * @param config
     * @param propertyName
     * @param data
     * @throws CoreException
     */
    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        if (data instanceof String) {
            type = RefactorType.getTypeFromFileExt((String) data);

            if (type == null) {
                throw new CoreException(new Status(IStatus.ERROR,
                        Xpdl2ResourcesPlugin.PLUGIN_ID,
                        String.format(getInitializationDataExceptionMessage(),
                                (String) data)));
            }
        }
    }

    /**
     * Get the message for intialization data exception.
     * 
     * @return The message for intialization data exception.
     */
    protected String getInitializationDataExceptionMessage() {
        return "Invalid parameter '%s' passed to the XPDL Move refactor participant"; //$NON-NLS-1$
    }
}
