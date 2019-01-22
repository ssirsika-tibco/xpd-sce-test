/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;

/**
 * Validation rule to check that the generated REST service names / participant
 * names for all the RESTful activities in the package clash with another
 * process / participant name in the same package
 * 
 * @author agondal
 * @since 2 Jan 2013
 */
public class RESTserviceNameRule extends PackageValidationRule {

    private static final String ISSUE_REST_SERVICE_NAME_CONFLICT =
            "bx.restServiceNameConflict"; //$NON-NLS-1$

    private static final String ISSUE_REST_SERVICE_PARTICIPANT_NAME_CONFLICT =
            "bx.restServiceParticipantNameConflict"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {
        if (pckg != null) {

            Set<Process> restServices = new HashSet<Process>();
            Set<String> processNames = new HashSet<String>();
            Set<String> restServiceParticipantNames = new HashSet<String>();
            Set<String> participantNames = new HashSet<String>();

            // get package participant names
            for (Participant p : pckg.getParticipants()) {
                participantNames.add(p.getName());
            }

            // get process/rest service/participant names
            for (Process process : pckg.getProcesses()) {
                processNames.add(process.getName());
                restServices.addAll(RestServiceUtil.getRestServices(process));
                for (Participant p : process.getParticipants()) {
                    participantNames.add(p.getName());
                }
            }

            Set<String> duplicateRestServicesList = new HashSet<String>();
            Set<String> duplicateParticipantList = new HashSet<String>();

            /*
             * Build list of duplicate rest service names and participant names
             */
            for (Process restService : restServices) {

                // check if restService name clashes with another process in the
                // package
                for (String processName : processNames) {
                    if (processName.equals(restService.getName())) {
                        duplicateRestServicesList.add(processName);
                    }
                }
                for (Participant p : restService.getParticipants()) {
                    restServiceParticipantNames.add(p.getName());
                }
            }

            for (String restParticipant : restServiceParticipantNames) {

                // check if restService participant name clashes with another
                // participant
                for (String participant : participantNames) {
                    if (restParticipant.equals(participant)) {
                        duplicateParticipantList.add(participant);
                    }
                }
            }

            /*
             * if duplicate rest service names, raise duplicate issue
             */

            if (!duplicateRestServicesList.isEmpty()) {
                for (String duplicateRestServiceName : duplicateRestServicesList) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(duplicateRestServiceName);
                    addIssue(ISSUE_REST_SERVICE_NAME_CONFLICT, pckg, messages);
                }
            }

            /*
             * if duplicate rest service participant, raise issue
             */

            if (!duplicateParticipantList.isEmpty()) {
                for (String duplicateRestServiceParticipantName : duplicateParticipantList) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(duplicateRestServiceParticipantName);
                    addIssue(ISSUE_REST_SERVICE_PARTICIPANT_NAME_CONFLICT,
                            pckg,
                            messages);
                }
            }
        }

    }
}
