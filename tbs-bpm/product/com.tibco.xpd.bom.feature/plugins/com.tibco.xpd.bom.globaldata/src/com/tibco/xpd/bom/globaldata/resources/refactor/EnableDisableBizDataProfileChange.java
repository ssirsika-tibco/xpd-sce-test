/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.resources.refactor;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.internal.Messages;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Change that enables/disables the BIZ data profile on the passed bom based
 * upon the value of {@link #shouldApplyBDPProfile} passed. Note that this
 * change does not pre-check if the profile is already present(if we want to
 * remove it) OR the profile is absent(if we want to add it), it simply adds or
 * removes the profile hence the participants that call this change should
 * themselves check if the profile is present or not and pass data correctly.
 * 
 * @author kthombar
 * @since Jul 3, 2015
 */
public class EnableDisableBizDataProfileChange extends Change {

    private IFile bomFile;

    private boolean shouldApplyBDPProfile;

    /**
     * 
     * @param bomFile
     *            the bom file on which the biz data profile should be
     *            added/removed
     * @param shouldApplyBDPProfile
     *            pass <code>true</code> if the biz data profile should be added
     *            to bom , else passs <code>false</code> if it should be
     *            removed.
     */
    public EnableDisableBizDataProfileChange(IFile bomFile,
            boolean shouldApplyBDPProfile) {
        this.bomFile = bomFile;
        this.shouldApplyBDPProfile = shouldApplyBDPProfile;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        if (shouldApplyBDPProfile) {
            return String
                    .format(Messages.EnableDisableBizDataProfileChange_EnableBizDataProfileChange_label,
                            bomFile.getName());
        } else {
            return String
                    .format(Messages.EnableDisableBizDataProfileChange_DisableBizDataProfileChange_label,
                            bomFile.getName());
        }
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        // Dont do anything here
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
        RefactoringStatus status = new RefactoringStatus();

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(bomFile);
        /*
         * Check if the working copy is accessible
         */
        if (workingCopy == null) {
            status.addError(String
                    .format(Messages.EnableDisableBizDataProfileChange_CannotAccessWorkingCopyError_msg,
                            bomFile.getName()));
        }
        return status;
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

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
        boolean isWorkingCopyDirty = wc.isWorkingCopyDirty();

        if (wc instanceof BOMWorkingCopy) {

            final Model model = (Model) wc.getRootElement();
            if (null != model) {

                UnprotectedWriteOperation op =
                        new UnprotectedWriteOperation(XpdResourcesPlugin
                                .getDefault().getEditingDomain()) {

                            @Override
                            protected Object doExecute() {
                                if (shouldApplyBDPProfile) {
                                    /*
                                     * add biz data profile
                                     */
                                    GlobalDataProfileManager.getInstance()
                                            .applyGlobalDataProfile(model);
                                } else {
                                    /*
                                     * remove biz data profile
                                     */
                                    Profile globalDataProfile =
                                            GlobalDataProfileManager
                                                    .getInstance().getProfile();
                                    if (null != model
                                            && model.getAppliedProfiles()
                                                    .contains(globalDataProfile)) {

                                        model.unapplyProfile(globalDataProfile);
                                    }
                                }
                                return model;
                            }
                        };

                op.execute();
            }

            if (!isWorkingCopyDirty) {

                try {
                    /*
                     * save the working copy if it was already in saved state
                     * before the move.
                     */
                    wc.save();
                } catch (IOException e) {

                    GlobalDataActivator.eInstance.getLogger().error(e);
                }
            }

            /*
             * To support undo-redo just pass '!shouldApplyBDPProfile'
             */
            return new EnableDisableBizDataProfileChange(bomFile,
                    !shouldApplyBDPProfile);
        }
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {

        return null;
    }
}
