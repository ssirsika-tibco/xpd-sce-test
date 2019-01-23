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

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution that creates an E-Mail system participant for E-Mail Tasks *
 * Does the following.
 * <p>
 * 1. Gets all the E-Mail activities in the Process which have no participant
 * configured.
 * <p>
 * 2. Creates a Single E-Mail System Participant(with unique name) at package
 * level
 * <p>
 * 3. Assigns this Participant to all the activities we fetched in Step 1 above
 * 
 * @author kthombar
 * @since 10-Jun-2014
 */
public class SystemParticipantForEmailTaskContribution extends
        AbstractIProcessToBPMContribution {

    private XpdExtensionPackage xpdExtensionPackage = getXpdExtensionPackage();

    private Xpdl2Factory xpdlFactory = getXpdlFactory();

    private XpdExtensionFactory xpdExtensionFactory = getXpdExtensionFactory();

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

                createSystemParticipantsForEmailTask(eachProcess);

                monitor.worked(1);
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Does the following.
     * <p>
     * 1. Gets all the E-Mail activities in the Process which have no
     * participant configured.
     * <p>
     * 2. Creates a Single E-Mail System Participant(with unique name) at
     * package level
     * <p>
     * 3. Assigns this Participant to all the activities we fetched in Step 1
     * above
     * 
     * @param eachProcess
     */
    private void createSystemParticipantsForEmailTask(Process eachProcess) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

        /* Get all E-Mail activities */
        List<Activity> allEmailTaskActivities =
                getAllEmailTaskActivities(allActivitiesInProc);

        if (allEmailTaskActivities != null && !allEmailTaskActivities.isEmpty()) {
            /* create and get E-Mail participant */
            Participant participant =
                    getCreateSystemParticipant(eachProcess.getPackage(),
                            Messages.SystemParticipantForEmailTaskContribution_EmailParticipant_name);

            /* Assign Performer to Activities. */
            for (Activity activity : allEmailTaskActivities) {
                assignPerformerToEmailTask(participant, activity);
            }
        }
    }

    /**
     * Adds the passed Participant as the Activity Performer.
     * 
     * @param participant
     * @param activity
     */
    private void assignPerformerToEmailTask(Participant participant,
            Activity activity) {
        Performers performers = xpdlFactory.createPerformers();

        Performer createPerformer = xpdlFactory.createPerformer();

        createPerformer.setValue(participant.getId());

        performers.getPerformers().add(createPerformer);

        activity.setPerformers(performers);
    }

    /**
     * 
     * @param allActivitiesInProc
     *            all activities in process
     * @return all E-Mail Activities which have no Performers configured.
     */
    private List<Activity> getAllEmailTaskActivities(
            Collection<Activity> allActivitiesInProc) {

        List<Activity> allEmailTaskActivities = new ArrayList<Activity>();

        for (Activity activity : allActivitiesInProc) {

            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;
                TaskService taskService = task.getTaskService();

                if (taskService != null) {
                    /* we are interested only in service tasks */
                    Object extensionAttribute =
                            getExtensionAttribute(taskService,
                                    xpdExtensionPackage
                                            .getDocumentRoot_ImplementationType());

                    if (extensionAttribute instanceof String) {

                        String ImplType = extensionAttribute.toString();

                        if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                                .equalsIgnoreCase(ImplType)) {

                            /* we are only interested in Email tasks */
                            EList<Performer> performerList =
                                    activity.getPerformerList();

                            if (performerList == null
                                    || performerList.isEmpty()) {
                                /*
                                 * proceed only if the Email task has no
                                 * performers, if it already has performers that
                                 * indicates that the custumer has contributed
                                 * and created a performer himself
                                 */

                                allEmailTaskActivities.add(activity);
                            }
                        }
                    }
                }
            }
        }
        return allEmailTaskActivities;
    }

    /**
     * Creates and returns an E-Mail System Participant with the Passed name
     * 
     * @param pkg
     *            the package under which a Email System participant is to be
     *            created
     * @param particName
     *            the name with which a participant is to be created
     * @return the created Email System Participant
     */
    private Participant getCreateSystemParticipant(Package pkg,
            String particName) {

        particName =
                Xpdl2ModelUtil.getUniqueLabelInSet(particName,
                        pkg.getParticipants());

        Participant participant = xpdlFactory.createParticipant();

        participant.setName(NameUtil.getInternalName(particName, false));

        setExtensionAttribute(participant,
                xpdExtensionPackage.getDocumentRoot_DisplayName(),
                particName);

        ParticipantTypeElem typeElem = xpdlFactory.createParticipantTypeElem();

        typeElem.setType(ParticipantType.SYSTEM_LITERAL);
        /* set Participant type to SYSTEM */
        participant.setParticipantType(typeElem);

        ParticipantSharedResource createParticipantSharedResource =
                xpdExtensionFactory.createParticipantSharedResource();

        EmailResource emailResource = xpdExtensionFactory.createEmailResource();

        createParticipantSharedResource.setEmail(emailResource);
        /* Create and assign Email shared resource */
        setExtensionElement(participant,
                xpdExtensionPackage.getDocumentRoot_ParticipantSharedResource(),
                createParticipantSharedResource);
        /* add participant to the package */
        pkg.getParticipants().add(participant);

        return participant;
    }
}
