/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.resources.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.internal.Messages;
import com.tibco.xpd.bom.resources.internal.refactor.BOMRefactorHelper;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Move participant that adds the business data profile on boms that are moved
 * to Global Data Project.
 * 
 * @author kthombar
 * @since Jul 3, 2015
 */
public class AddBizDataCapabilityOnBOMMoveParticipant extends MoveParticipant {

    /**
     * BOM files that have been moved
     */
    private IFile[] movedBom;

    /**
     * the refactoring helper for BOM refactor
     */
    private final BOMRefactorHelper helper;

    /**
     * the target of the move.
     */
    private IFolder targetFolder;

    /**
     * The BOM files as they will be after the move
     */
    private IFile[] newBOMFiles;

    /**
     * default constructor
     */
    public AddBizDataCapabilityOnBOMMoveParticipant() {
        // Initialise the helper
        helper = new BOMRefactorHelper();
    }

    /**
     * @see com.tibco.xpd.bom.resources.internal.refactor.BOMMoveParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
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

        if (targetFolder != null) {

            IProject project = targetFolder.getProject();

            if (project != null) {
                /*
                 * check if the project is biz data project
                 */
                boolean isBusinessDataProject =
                        BOMUtils.isBusinessDataProject(project);

                for (int i = 0; i < movedBom.length; i++) {
                    /*
                     * now check if the bom moved has Global Data Profile on it.
                     */
                    boolean hasGlobalDataProfile =
                            BOMGlobalDataUtils
                                    .hasGlobalDataProfile(movedBom[i]);

                    if (isBusinessDataProject && !hasGlobalDataProfile) {
                        /*
                         * If the target project is biz data project and the
                         * moved bom does not have biz data profile then add it.
                         */
                        changes.add(new EnableDisableBizDataProfileChange(
                                newBOMFiles[i], true));

                    }
                }
            }
        }

        if (!changes.isEmpty()) {
            return new CompositeChange(
                    Messages.ManageBizDataCapabilityOnBOMMoveParticipant_BizDataProfileParticipant_name,
                    changes.toArray(new Change[changes.size()]));
        }
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
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

        if (targetFolder == null) {
            /*
             * XPD-7768: return false immediately if the target is not a bom or
             * in a bom special folder
             */
            return false;
        }

        if (element instanceof IFile) {

            movedBom = new IFile[] { (IFile) element };
            /*
             * get the handle of the moved bom file as we want to add/remove biz
             * data profile from the MOVED boms
             */
            IFile newFile = targetFolder.getFile(((IFile) element).getName());
            newBOMFiles = new IFile[] { newFile };

        } else if (element instanceof IFolder) {

            try {
                List<IFile> bomFiles = helper.getBOMFiles((IFolder) element);
                movedBom = bomFiles.toArray(new IFile[bomFiles.size()]);
                /*
                 * get the handle of the moved bom files as we want to
                 * add/remove biz data profile from the MOVED boms
                 */
                newBOMFiles =
                        helper.getMovedBomFileHandles(movedBom,
                                (IFolder) element,
                                targetFolder);

            } catch (CoreException e) {

                GlobalDataActivator.eInstance.getLogger().error(e);
            }
        }

        return movedBom != null && movedBom.length > 0 && newBOMFiles != null
                && newBOMFiles.length > 0;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return Messages.ManageBizDataCapabilityOnBOMMoveParticipant_EnableBizDataProfileChange_name;
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

        List<BOMWorkingCopy> wCopyOfMovedBom = new ArrayList<BOMWorkingCopy>();

        for (IFile eachBomFile : movedBom) {

            WorkingCopy workingCopy =
                    WorkingCopyUtil.getWorkingCopy(eachBomFile);

            if (workingCopy instanceof BOMWorkingCopy) {
                wCopyOfMovedBom.add((BOMWorkingCopy) workingCopy);
            } else {
                return RefactoringStatus
                        .createFatalErrorStatus(String
                                .format(Messages.ManageBizDataCapabilityOnBOMMoveParticipant_CannotAccessWorkingCopyError_msg,
                                        eachBomFile.getName()));
            }
        }
        /*
         * Make sure none of the affected working copies are dirty - cannot
         * refactor a dirty model.
         */
        RefactoringStatus status =
                helper.checkWorkingCopyDirtyConditions(pm,
                        context,
                        wCopyOfMovedBom);

        return status;
    }
}
