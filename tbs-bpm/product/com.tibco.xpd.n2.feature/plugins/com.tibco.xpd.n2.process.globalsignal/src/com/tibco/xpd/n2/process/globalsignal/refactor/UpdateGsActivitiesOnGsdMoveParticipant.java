/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Participant that fixes references in all the Global Signal Activities that
 * reference the Global Signal in the GSD file being moved.
 * 
 * @author kthombar
 * @since Jul 1, 2015
 */
public class UpdateGsActivitiesOnGsdMoveParticipant extends
        AbstractGsdMoveParticipant {

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.UpdateGsActivitiesOnGsdMoveParticipant_UpdateReferencingGsEvents_msg;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext,
     *      org.eclipse.core.resources.IResource,
     *      org.eclipse.core.resources.IFolder, java.util.Map)
     * 
     * @param pm
     * @param context
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @param gsdFileToReferencingActivityMap
     * @return
     * @throws OperationCanceledException
     */
    @Override
    protected RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context, IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap)
            throws OperationCanceledException {
        /*
         * there is nothing to check here.
         */
        return new RefactoringStatus();
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IResource,
     *      org.eclipse.core.resources.IFolder, java.util.Map)
     * 
     * @param pm
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @param gsdFileToReferencingActivityMap
     * @return
     */
    @Override
    protected Change createChange(IProgressMonitor pm,
            IResource sourceMovedResource, IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap) {

        pm.beginTask(getName(), 1);
        /*
         * create a composite change, other changes will be a part of this super
         * composte change.
         */
        CompositeChange compositeChange = null;

        try {

            if (!gsdFileToReferencingActivityMap.values().isEmpty()) {

                compositeChange =
                        new CompositeChange(
                                String.format(Messages.UpdateGsActivitiesOnGsdMoveParticipant_UpdateReferencesChange_name,
                                        sourceMovedResource.getName()));
                compositeChange.markAsSynthetic();

                /*
                 * stores all the changes to execute on the Global Signal
                 * Activities.
                 */
                List<UpdateGsReferencesOnGsdFileRefactorChange> allChangesToExecute =
                        new ArrayList<UpdateGsReferencesOnGsdFileRefactorChange>();

                for (IFile eachGsdFile : gsdFileToReferencingActivityMap
                        .keySet()) {

                    /*
                     * Get the new special folder relative path of the GSD file
                     * after move.
                     */
                    String newGsdSpecialFolderRelPath =
                            getNewGsdSpecialFolderRelPath(sourceMovedResource,
                                    targetGsdSpecialFolder,
                                    eachGsdFile);

                    if (newGsdSpecialFolderRelPath != null
                            && !newGsdSpecialFolderRelPath.isEmpty()) {

                        for (Activity eachReferencingGsActivity : gsdFileToReferencingActivityMap
                                .get(eachGsdFile)) {

                            /*
                             * get the old name
                             */
                            String oldGlobalSignalQualifiedName =
                                    EventObjectUtil
                                            .getSignalName(eachReferencingGsActivity);
                            /*
                             * the global signal name has '#' in it which is
                             * folliwed by the signal name
                             */
                            int indexOfHash =
                                    oldGlobalSignalQualifiedName.indexOf('#');

                            if (indexOfHash != -1) {
                                /*
                                 * create the new qualified name.
                                 */
                                String newQualifiedGsName =
                                        newGsdSpecialFolderRelPath
                                                + oldGlobalSignalQualifiedName
                                                        .substring(indexOfHash);

                                /*
                                 * add all the changes to the list
                                 */
                                allChangesToExecute
                                        .add(new UpdateGsReferencesOnGsdFileRefactorChange(
                                                eachReferencingGsActivity,
                                                oldGlobalSignalQualifiedName,
                                                newQualifiedGsName,
                                                WorkingCopyUtil
                                                        .getEditingDomain(eachReferencingGsActivity)));
                            }
                        }
                    }
                }

                /*
                 * Add all changes to the parent composite change.
                 */
                compositeChange
                        .add(new CompositeChange(
                                String.format(Messages.UpdateGsActivitiesOnGsdMoveParticipant_UpdateReferencesChange_name,
                                        sourceMovedResource.getName()),
                                allChangesToExecute
                                        .toArray(new Change[allChangesToExecute
                                                .size()])));
                pm.worked(1);
            }
        } finally {
            pm.done();
        }
        return compositeChange;
    }

    /**
     * 
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @param gsdFileBoingMoved
     * @return the Gsd File special folder relative path after the move.
     */
    private String getNewGsdSpecialFolderRelPath(IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder, IFile gsdFileBoingMoved) {

        String newGsdSpecialFolderRelativePath = null;

        /*
         * get the special folder relative path of the target folder.
         */
        IPath targetSpecialFolderRelativePath =
                SpecialFolderUtil
                        .getSpecialFolderRelativePath(targetGsdSpecialFolder,
                                GsdConstants.GSD_SPECIAL_FOLDER_KIND);

        IProject targetProject = targetGsdSpecialFolder.getProject();

        if (targetProject != null) {

            String projectId = ProjectUtil.getProjectId(targetProject);

            if (projectId != null) {
                /*
                 * start creating global signal qualified name
                 */
                newGsdSpecialFolderRelativePath = projectId;

                if (targetSpecialFolderRelativePath != null
                        && !targetSpecialFolderRelativePath.isEmpty()) {
                    /*
                     * append the target folder relative path to the signal
                     * name.
                     */
                    newGsdSpecialFolderRelativePath =
                            newGsdSpecialFolderRelativePath
                                    + "/" + targetSpecialFolderRelativePath.toString(); //$NON-NLS-1$
                }

                if (sourceMovedResource instanceof IFolder) {
                    /*
                     * If the source resource moved if a folder then we need to
                     * get its special folder relative path as well because it
                     * should be there in the signal name.
                     */

                    /*
                     * get the gsd relative path
                     */
                    IPath gsdFileSpecialFolderRelativePath =
                            SpecialFolderUtil
                                    .getSpecialFolderRelativePath(gsdFileBoingMoved);
                    /*
                     * get the folder relative path
                     */
                    IPath movedFolderSpecialFolderRelPath =
                            SpecialFolderUtil
                                    .getSpecialFolderRelativePath(sourceMovedResource,
                                            GsdConstants.GSD_SPECIAL_FOLDER_KIND);

                    IPath relativeGsdFilePath = null;

                    if (movedFolderSpecialFolderRelPath.segmentCount() > 1) {
                        /*
                         * make the 2 paths relative
                         */
                        relativeGsdFilePath =
                                gsdFileSpecialFolderRelativePath
                                        .makeRelativeTo(movedFolderSpecialFolderRelPath
                                                .removeLastSegments(1));
                    } else {
                        relativeGsdFilePath = gsdFileSpecialFolderRelativePath;
                    }

                    if (GsdConstants.GSD_SPECIAL_FOLDER_KIND
                            .equals(SpecialFolderUtil
                                    .getSpecialFolderKind((IFolder) sourceMovedResource))) {
                        /*
                         * If the GSD special folder itself was moved from the
                         * source project then add it to the new name.
                         */
                        newGsdSpecialFolderRelativePath =
                                newGsdSpecialFolderRelativePath
                                        + "/" + sourceMovedResource.getName(); //$NON-NLS-1$
                    }

                    newGsdSpecialFolderRelativePath =
                            newGsdSpecialFolderRelativePath
                                    + "/" + relativeGsdFilePath.toString(); //$NON-NLS-1$ 

                } else {
                    /*
                     * if we simply moved the gsd file then add it to the new
                     * gsd path.
                     */
                    newGsdSpecialFolderRelativePath =
                            newGsdSpecialFolderRelativePath
                                    + "/" + gsdFileBoingMoved.getName(); //$NON-NLS-1$

                }
            }
        }
        return newGsdSpecialFolderRelativePath;
    }
}
