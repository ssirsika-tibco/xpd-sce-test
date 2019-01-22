/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Statically named iProcess user/group user step addressees are the equivalent
 * of AMX BPM participant RQL queries that reference organisation model
 * resources with specific names.
 * <p>
 * During iProcess to AMX BPM conversion:
 * <p>
 * 1. Make sure that all the user task participant performers are of
 * 'Organisation Model Query' type, with the following RQL expression..
 * <p>
 * • resource(name = "name of the user/group")
 * <p>
 * 2. This participant will be reused (by name) when used in multiple tasks.
 * <p>
 * 
 * @author sajain
 * @since Jun 10, 2014
 */
public class UserTaskAddresseeHandlingContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * RQL expression text which is supposed to be put for an Org model query
     * type participant.
     */
    private final String RQL_EXPRESSION_TEXT = "resource(name = \"%s\")"; //$NON-NLS-1$

    /**
     * RQL grammar type.
     */
    private static final String RQL_GRAMMAR = "RQL"; //$NON-NLS-1$

    private XpdExtensionPackage xpdExtensionPackage = getXpdExtensionPackage();

    private Xpdl2Factory xpdlFactory = getXpdlFactory();

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
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
            monitor.beginTask("", processes.size()); //$NON-NLS-1$

            for (Process eachProcess : processes) {

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

                modifyUserTaskPerformersToOrgModelQueryType(allActivitiesInProc,
                        eachProcess);

                monitor.worked(1);
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Make sure that all the user task participant performers are of
     * Organisation Model Query Type.
     * 
     * @param allActivitiesInProc
     *            all the activities in process
     * @param eachProcess
     *            the process
     */
    private void modifyUserTaskPerformersToOrgModelQueryType(
            Collection<Activity> allActivitiesInProc, Process eachProcess) {

        Package pkg = eachProcess.getPackage();

        List<Participant> participants = new ArrayList<Participant>();

        participants.addAll(pkg.getParticipants());

        participants.addAll(eachProcess.getParticipants());

        for (Activity eachActivity : allActivitiesInProc) {

            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskUser userTask = task.getTaskUser();

                /*
                 * Check if it is a user task.
                 */
                if (null != userTask) {

                    EList<Performer> allPerformers =
                            eachActivity.getPerformerList();

                    /*
                     * We don't want to do anything when the user task doesn't
                     * have any performer at all. So proceed only if there is at
                     * least one performer.
                     */
                    if (allPerformers != null && !allPerformers.isEmpty()) {

                        for (Performer eachPerformer : allPerformers) {

                            /*
                             * Check if there's a participant with the given
                             * performer id
                             */
                            Participant currentParticipant =
                                    getParticipantFromPerformerId(eachPerformer.getValue(),
                                            participants);

                            if (currentParticipant != null) {

                                /*
                                 * There is a participant with the specified
                                 * performer id, so now check if it's type is
                                 * set to 'Organisation Model Query'.
                                 */
                                if (!isOrgModelQueryTypeParticipant(currentParticipant)) {

                                    /*
                                     * If it is not, then reset it's type to
                                     * organisation model query and also adds
                                     * appropriate RQL expression.
                                     */
                                    setParticipantTypeToOrgModelQuery(currentParticipant);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Return the participant with the specified performer id. If no such
     * participant exists, return <code>null</code>.
     * 
     * @param performerId
     * @param participants
     * @return
     */
    private Participant getParticipantFromPerformerId(String performerId,
            List<Participant> participants) {

        for (Participant eachParticipant : participants) {

            if (performerId.equals(eachParticipant.getId())) {

                /*
                 * Participant with the specified performer id found, return it.
                 */
                return eachParticipant;
            }
        }

        return null;
    }

    /**
     * Sets the participant type of the passed participant to 'Organisation
     * model query' and accordingly adds the RQL expression.
     * 
     * @param participant
     */
    private void setParticipantTypeToOrgModelQuery(Participant participant) {

        /*
         * Create a new participant type element.
         */
        ParticipantTypeElem participantType =
                xpdlFactory.createParticipantTypeElem();

        /*
         * Set it's type to RESOURCE_SET.
         */
        participantType.setType(ParticipantType.RESOURCE_SET_LITERAL);

        /*
         * Create RQL expression.
         */
        Expression expression =
                xpdlFactory.createExpression(String.format(RQL_EXPRESSION_TEXT,
                        participant.getName()));

        /*
         * Set the expression grammar to RQL.
         */
        expression.setScriptGrammar(RQL_GRAMMAR);

        /*
         * Set the expression to the participant type element.
         */
        setExtensionElement(participantType,
                xpdExtensionPackage.getDocumentRoot_ParticipantQuery(),
                expression);

        participant.setParticipantType(participantType);

    }

    /**
     * Returns <code>true</code> if the passed participant is an Organisation
     * Model Query Type participant, returns <code>false</code> otherwise.
     * 
     * @param participant
     * 
     * @return <code>true</code> if the passed participant is an Org Model Query
     *         Type participant, return <code>false</code> otherwise.
     */
    private boolean isOrgModelQueryTypeParticipant(Participant participant) {

        ParticipantTypeElem participantType = participant.getParticipantType();

        if (participantType != null) {

            ParticipantType type = participantType.getType();

            if (type != null) {

                return type.equals(ParticipantType.RESOURCE_SET_LITERAL);
            }
        }
        return false;
    }
}
