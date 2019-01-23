/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * API Activities need to be unique across the Process. This is required because
 * WSDL generation from these activities generate
 * 
 * @author rsomayaj
 * 
 */
public class APIActivitiesUniqueNameRule extends ProcessValidationRule {

    public static final String API_ACT_UNIQUE_NAME_ISSUE =
            "bpmn.apiActsUniqueName";

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
    }

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(java.lang.Object)
     * 
     * @param o
     */
    @Override
    public void validate(Object o) {
        Map<Activity, String> activityNames = new HashMap<Activity, String>();
        if (o instanceof Process) {
            Process process = (Process) o;
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            Boolean shouldGenerateWSDLForProcessDestinations =
                    ProcessDestinationUtil
                            .shouldGenerateWSDLForProcessDestinations(process);
            if (shouldGenerateWSDLForProcessDestinations) {
                List<Activity> apiGenActivities =
                        getAPIRequestActivities(allActivitiesInProc);
                // RS - Honestly, this seems to be quite an unusual logic,
                // but haven't been able to come up with anything better

                List<String> tempActName = new ArrayList<String>();

                for (Activity act : apiGenActivities) {
                    if (act.getName() != null
                            && !tempActName.contains(act.getName())) {
                        List<Activity> activitiesWithSameName =
                                EMFSearchUtil
                                        .findManyInList(new ArrayList<Activity>(
                                                apiGenActivities),
                                                Xpdl2Package.eINSTANCE
                                                        .getNamedElement_Name(),
                                                act.getName());
                        if (activitiesWithSameName.size() > 1) {
                            for (Activity activity : activitiesWithSameName) {
                                addIssue(API_ACT_UNIQUE_NAME_ISSUE, activity);
                            }
                        }
                        tempActName.add(act.getName());
                    }
                }
            }
        }
    }

    /**
     * @param allActivitiesInProc
     * @return
     */
    private List<Activity> getAPIRequestActivities(
            Collection<Activity> allActivitiesInProc) {
        List<Activity> apiReqGenActivities = new ArrayList<Activity>();
        for (Activity act : allActivitiesInProc) {
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(act)) {
                apiReqGenActivities.add(act);
            }
        }
        return apiReqGenActivities;
    }
}
