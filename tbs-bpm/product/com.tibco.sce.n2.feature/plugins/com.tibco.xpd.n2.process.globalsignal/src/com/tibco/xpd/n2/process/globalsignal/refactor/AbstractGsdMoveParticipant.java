package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.globalSignalDefinition.workingcopy.GsdWorkingCopy;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Abstract class for Gsd File and Folder(inside GSD Special folder that
 * contains GSD Files) move.
 * 
 * 
 * @author kthombar
 * @since Jul 1, 2015
 */
public abstract class AbstractGsdMoveParticipant extends MoveParticipant {

    /**
     * Stores the Gsd files that will be actually moved.
     */
    private List<IFile> gsdFilesToMove = new ArrayList<IFile>();

    /**
     * the target of the move
     */
    private IFolder targetGsdSpecialFolder;

    /**
     * the resource that is moved(may be GSD file or a Folder that contains GSD
     * files)
     */
    private IResource sourceMovedResource;

    /**
     * Map that stores the gsd file to the activites that reference it.
     */
    private Map<IFile, List<Activity>> gsdFileToReferencingActivityMap =
            new HashMap<IFile, List<Activity>>();

    public AbstractGsdMoveParticipant() {
        // Do nothing here.
    }

    @Override
    protected boolean initialize(Object element) {
        /*
         * just return true from there the other overloaded
         * initialize(RefactoringProcessor, Object , RefactoringArguments)
         * method does the real computation
         */
        return true;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor,
     *      java.lang.Object,
     *      org.eclipse.ltk.core.refactoring.participants.RefactoringArguments)
     * 
     * @param processor
     * @param element
     * @param arguments
     * @return
     */
    @Override
    public final boolean initialize(RefactoringProcessor processor,
            Object element, RefactoringArguments arguments) {

        if (super.initialize(processor, element, arguments)) {

            if (arguments instanceof MoveArguments) {

                if (element instanceof IFile) {

                    sourceMovedResource = (IFile) element;

                    gsdFilesToMove.add((IFile) element);

                } else if (element instanceof IFolder) {
                    /*
                     * We a folder is moved then get all the GSD files in the
                     * folder.
                     */
                    sourceMovedResource = (IFolder) element;

                    try {

                        gsdFilesToMove.addAll(GsdRefactorHelper
                                .getGsdFiles((IFolder) element));
                    } catch (CoreException e) {

                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    }
                }

                if (((MoveArguments) arguments).getDestination() instanceof IFolder) {

                    IFolder targetfolder =
                            (IFolder) ((MoveArguments) arguments)
                                    .getDestination();

                    if (isTargetInGSDSpecialFolder(targetfolder)) {
                        targetGsdSpecialFolder =
                                (IFolder) ((MoveArguments) arguments)
                                        .getDestination();
                    }
                }
            }
        }
        return !gsdFilesToMove.isEmpty() && targetGsdSpecialFolder != null
                && sourceMovedResource != null
                && initialize(sourceMovedResource, targetGsdSpecialFolder);
    }

    /**
     * Give a chance to the extending classes to 'additionally' decide if the
     * participant is fit to be initialized. By default return <code>true</code>
     * 
     * @param sourceMovedResource
     *            the resource that is moved(may be GSD file or a Folder that
     *            contains GSD files)
     * @param targetGsdSpecialFolder
     *            the target folder of move
     * @return
     */
    protected boolean initialize(IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder) {

        return true;
    }

    @Override
    public abstract String getName();

    @Override
    public final RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        RefactoringStatus status = new RefactoringStatus();

        pm.beginTask(String
                .format(Messages.AbstractGsdMoveParticipant_ValiddatingConditionsProgressMonitor_label,
                        getName()),
                1);

        try {

            List<GsdWorkingCopy> gsdWorkingCopies =
                    new ArrayList<GsdWorkingCopy>();

            for (IFile eachGsdFileToMove : gsdFilesToMove) {

                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(eachGsdFileToMove);

                if (workingCopy == null) {
                    /*
                     * complain if the working copy cannot be fetched.
                     */
                    return RefactoringStatus
                            .createFatalErrorStatus(String
                                    .format(Messages.AbstractGsdMoveParticipant_CouldNotAccessWorkingCopyError_msg,
                                            eachGsdFileToMove.getName()));

                } else {
                    if (workingCopy instanceof GsdWorkingCopy) {
                        gsdWorkingCopies.add((GsdWorkingCopy) workingCopy);
                    }
                }

                List<Activity> allRefrencingGlobalSignalActivities =
                        GsdRefactorHelper
                                .getAllRefrencingGlobalSignalActivities(eachGsdFileToMove);

                if (!allRefrencingGlobalSignalActivities.isEmpty()) {
                    /*
                     * add the gsd file to all referencing activity info to the
                     * map.
                     */
                    gsdFileToReferencingActivityMap.put(eachGsdFileToMove,
                            allRefrencingGlobalSignalActivities);
                }
            }

            /*
             * Do not allow refactor is the GSD files being moved are dirty.
             */
            RefactoringStatus checkWorkingCopyDirtyConditions =
                    GsdRefactorHelper.checkWorkingCopyDirtyConditions(pm,
                            context,
                            gsdWorkingCopies);

            if (!checkWorkingCopyDirtyConditions.isOK()) {
                return checkWorkingCopyDirtyConditions;
            }

            status =
                    checkConditions(pm,
                            context,
                            sourceMovedResource,
                            targetGsdSpecialFolder,
                            gsdFileToReferencingActivityMap);

            pm.worked(1);
        } finally {
            pm.done();
        }
        return status;
    }

    @Override
    public final Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        return createChange(pm,
                sourceMovedResource,
                targetGsdSpecialFolder,
                gsdFileToReferencingActivityMap);
    }

    /**
     * 
     * @param pm
     * @param context
     * @param sourceMovedResource
     *            the resource that is moved(may be GSD file or a Folder that
     *            contains GSD files)
     * @param targetGsdSpecialFolder
     *            the target folder of move
     * @param gsdFileToReferencingActivityMap
     *            Map that stores the gsd file to the global signal activites
     *            that reference it.
     * @return
     * @throws OperationCanceledException
     */
    protected abstract RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context, IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap)
            throws OperationCanceledException;

    /**
     * @param pm
     * @param sourceMovedResource
     *            the resource that is moved(may be GSD file or a Folder that
     *            contains GSD files)
     * @param targetGsdSpecialFolder
     *            the target folder of move
     * @param gsdFileToReferencingActivityMap
     *            Map that stores the gsd file to the global signal activites
     *            that reference it.
     * @return the Change to be executed.
     */
    protected abstract Change createChange(IProgressMonitor pm,
            IResource sourceMovedResource, IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap);

    /**
     * Check if the target of this move is a folder that is a GSD special
     * folder, or contained in a GSD special folder.
     * 
     * @param target
     * @return
     */
    private boolean isTargetInGSDSpecialFolder(IFolder targetFolder) {
        /*
         * Check if the target is contained in a GSD special folder
         */
        SpecialFolder sFolder =
                SpecialFolderUtil.getRootSpecialFolder(targetFolder);

        return sFolder != null
                && GsdConstants.GSD_SPECIAL_FOLDER_KIND.equals(sFolder
                        .getKind());
    }

}
