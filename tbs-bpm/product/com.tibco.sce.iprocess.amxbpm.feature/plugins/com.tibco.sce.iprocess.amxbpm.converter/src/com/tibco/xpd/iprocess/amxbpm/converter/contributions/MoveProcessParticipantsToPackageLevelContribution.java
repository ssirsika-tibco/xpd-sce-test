/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/**
 * Conversion contribution to move {@link Participant}/s from {@link Process}
 * scope to {@link Package} scope.
 * 
 * @author kthombar
 * @since 06-Apr-2014
 */
public class MoveProcessParticipantsToPackageLevelContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.bpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            for (Process eachProcess : processes) {

                EList<Participant> participants = eachProcess.getParticipants();

                Package pkg = eachProcess.getPackage();
                /*
                 * we do not check if the package here is null, as we do not
                 * except the package to be null and in case the package is null
                 * then the code will throw a NPE which is expected!
                 */
                if (!participants.isEmpty()) {
                    moveProcessParticpantsToPackageLevel(pkg, participants);
                }

            }
            monitor.worked(1);

            /*
             * XPD-6370: Main framework now reports status with each conversion
             * description. so no need to do it here also.
             */
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Adds the {@link Participant}'s to {@link Package}
     * 
     * @param eachProcess
     * @param participants
     */
    private void moveProcessParticpantsToPackageLevel(Package pkg,
            EList<Participant> participants) {
        /*
         * Note: We do not delete the participants from process because when
         * they are added to the package EMF automatically deletes them from the
         * process.
         */
        /*
         * Note : Important- As the IProcess Studio XPDL to AMX BPM XPDL
         * conversion we do not expect the package to already have any package
         * level participants. All we do is move process level participants to
         * package level.
         */
        pkg.getParticipants().addAll(participants);
    }
}
