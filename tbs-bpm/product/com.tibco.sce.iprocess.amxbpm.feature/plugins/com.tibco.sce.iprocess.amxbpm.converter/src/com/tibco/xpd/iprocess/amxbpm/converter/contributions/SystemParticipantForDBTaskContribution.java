/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.DatabaseTask;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.ipm.iProcessExt.TaskProperties;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.JdbcResource;
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
 * This contribution checks if a Task is a DB task with DataBase
 * {@link TaskProperties} information. If yes then creates a Java Syatem
 * Participant with the given server name.
 * <p>
 * i.e. if we have server name = "MyServer" then a Java System Participant with
 * the name "Database Participant MyServer" will be created, if the server name
 * is empty or null then a Java System Participant with the name
 * "Database Participant BPM Server" will be created.
 * <p>
 * Note : There will be only 1 Java System Participant per Server Name.
 * 
 * @author kthombar
 * @since 28-May-2014
 */
public class SystemParticipantForDBTaskContribution extends
        AbstractIProcessToBPMContribution {

    private XpdExtensionPackage xpdExtensionPackage = getXpdExtensionPackage();

    private Xpdl2Factory xpdlFactory = getXpdlFactory();

    private IProcessExtPackage iProcessExtPackage = getIProcessExtPackage();

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

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);
                createSystemParticipantsForDBTask(allActivitiesInProc,
                        eachProcess);

                monitor.worked(1);
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Checks if a Task is a DB task with DataBase {@link TaskProperties}
     * information. If yes then creates a Java Syatem Participant with the given
     * server name.
     * <p>
     * i.e. if we have server name = "MyServer" then a Java System Participant
     * with the name "Database Participant MyServer" will be created, if the
     * server name is empty or null then a Java System Participant with the name
     * "Database Participant BPM Server" will be created.
     * <p>
     * Note : There will be only 1 Java System Participant per Server Name.
     * 
     * @param allActivitiesInProc
     *            all the activities in process
     * @param eachProcess
     *            the process
     */
    private void createSystemParticipantsForDBTask(
            Collection<Activity> allActivitiesInProc, Process eachProcess) {

        Package pkg = eachProcess.getPackage();
        EList<Participant> participants = pkg.getParticipants();

        /**
         * Map of the server name to the Participant.
         */
        Map<String, Participant> serverNameToParticipantMap =
                new HashMap<String, Participant>();

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

                        if (TaskImplementationTypeDefinitions.DATABASE_SERVICE
                                .equalsIgnoreCase(ImplType)) {

                            /* we are only interested in DB tasks */
                            EList<Performer> performerList =
                                    activity.getPerformerList();

                            if (performerList == null
                                    || performerList.isEmpty()) {
                                /*
                                 * proceed only if the DB task has no
                                 * performers, if it already has performers that
                                 * indicates that the custumer has contributed
                                 * and created a performer himself
                                 */
                                Object extensionElement =
                                        getExtensionElement(activity,
                                                iProcessExtPackage
                                                        .getDocumentRoot_TaskProperties());

                                if (extensionElement instanceof TaskProperties) {
                                    /*
                                     * Scan the Task Properties for server
                                     * information
                                     */
                                    TaskProperties properties =
                                            (TaskProperties) extensionElement;
                                    DatabaseTask databaseTask =
                                            properties.getDatabaseTask();

                                    if (databaseTask != null) {

                                        String serverName =
                                                databaseTask.getServer();
                                        /*
                                         * Create System Participant with the
                                         * server name and assign it to the DB
                                         * task.
                                         */
                                        createSystemParticipantWithServerName(activity,
                                                pkg,
                                                serverName,
                                                participants,
                                                serverNameToParticipantMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if a Java system Participant is already confoigured for the passed
     * server name. If yes then assign the same participant to the passed DB
     * task, if no Participant is configured then creates a new participant and
     * assigns it to the DB task.
     * 
     * @param activity
     *            the DB Task Activity
     * @param pkg
     *            the package
     * @param serverName
     *            the server name for which the participant is to be created.
     *            Can be null as well
     * @param participants
     *            all participants in the packgage
     * @param serverNameToParticipantMap
     *            map of Server name to its respective participant.
     */
    private void createSystemParticipantWithServerName(Activity activity,
            Package pkg, String serverName, EList<Participant> participants,
            Map<String, Participant> serverNameToParticipantMap) {

        String internalServerName = null;

        if (serverName == null || serverName.isEmpty()) {
            internalServerName = "%#$default_Server"; //$NON-NLS-1$
        } else {

            internalServerName = NameUtil.getInternalName(serverName, false);
        }

        Participant participantInMap =
                serverNameToParticipantMap.get(internalServerName);

        if (participantInMap != null) {
            /*
             * if participant for the server name already present , then re-use
             * it
             */
            assignPerformerToDBTask(participantInMap, activity);

        } else {

            String particLabel =
                    Messages.SystemParticipantForDBTaskContribution_DataBaseParticipantBpmServer_label;

            if (serverName != null && !serverName.isEmpty()) {
                /*
                 * If we have a server name then the participant name should
                 * contain the server name
                 */
                particLabel =
                        String.format(Messages.SystemParticipantForDBTaskContribution_ConfiguredSystemParticipant_label,
                                serverName);

            }

            /*
             * If there already is an Participant with the passed name then
             * return it, else create a participant with the passed name
             */
            Participant existingOrNewCreatedSystemPartic =
                    getExistingOrCreateNewSystemPartic(particLabel,
                            pkg,
                            participants);
            /* add server name and participant to map */
            serverNameToParticipantMap.put(internalServerName,
                    existingOrNewCreatedSystemPartic);

            /*
             * Assign the Participant to the activity.
             */
            assignPerformerToDBTask(existingOrNewCreatedSystemPartic, activity);
        }
    }

    /**
     * Creates a Java System Participant with the Passed name
     * 
     * @param pkg
     *            the package under which a Java System participant is to be
     *            created
     * @param particName
     *            the name with which a participant is to be created
     * @return the created Java System Participant
     */
    private Participant createSystemParticipant(Package pkg, String particName) {
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

        JdbcResource jdbc = xpdExtensionFactory.createJdbcResource();

        createParticipantSharedResource.setJdbc(jdbc);
        /* Create and assign Jdbc shared resource */
        setExtensionElement(participant,
                xpdExtensionPackage.getDocumentRoot_ParticipantSharedResource(),
                createParticipantSharedResource);
        /* add participant to the package */
        pkg.getParticipants().add(participant);

        return participant;

    }

    /**
     * Adds the passed Participant as the Activity Performer.
     * 
     * @param participant
     * @param activity
     */
    private void assignPerformerToDBTask(Participant participant,
            Activity activity) {
        Performers performers = xpdlFactory.createPerformers();

        Performer createPerformer = xpdlFactory.createPerformer();

        createPerformer.setValue(participant.getId());

        performers.getPerformers().add(createPerformer);

        activity.setPerformers(performers);
    }

    /**
     * Checks if a participant for the passed name is already present, if yes
     * then returns it, else creates a new Java System participant and returns
     * it.
     * 
     * @param particLabel
     * @param pkg
     * @param participants
     * @return the existing Or newly created Java System Participant
     */
    private Participant getExistingOrCreateNewSystemPartic(String particLabel,
            Package pkg, EList<Participant> participants) {

        for (Participant participant : participants) {

            String participantName = participant.getName();

            if (participantName.equals(NameUtil.getInternalName(particLabel,
                    false))) {

                /*
                 * if we have a particpant with the same name check if it is an
                 * Java System participant, if yes then return it!
                 */
                if (isJavaSystemParticipant(participant)) {

                    return participant;
                }
                /*
                 * If we have participant with same name but it is not java
                 * system participant then get unique label for the participant.
                 */

                particLabel =
                        Xpdl2ModelUtil.getUniqueLabelInSet(particLabel,
                                participants);
                break;
            }
        }
        return createSystemParticipant(pkg, particLabel);
    }

    /**
     * 
     * @param participant
     * @return <code>true</code> if the passed participant is a Java system
     *         Participant , else return <code>false</code>
     */
    private boolean isJavaSystemParticipant(Participant participant) {

        ParticipantTypeElem participantType = participant.getParticipantType();

        if (participantType != null) {

            ParticipantType type = participantType.getType();
            if (type != null && type.equals(ParticipantType.SYSTEM_LITERAL)) {

                Object extensionElement =
                        getExtensionElement(participant,
                                xpdExtensionPackage
                                        .getDocumentRoot_ParticipantSharedResource());

                if (extensionElement instanceof ParticipantSharedResource) {

                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) extensionElement;
                    JdbcResource jdbc = psr.getJdbc();
                    if (jdbc != null) {

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
