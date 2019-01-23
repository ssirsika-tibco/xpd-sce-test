/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class CorrelationDataRule extends ProcessValidationRule {

    private static final String NON_BASIC = "bpmn.nonBasicCorrelationData"; //$NON-NLS-1$

    private static final String IS_ARRAY = "bpmn.arrayTypeCorrelationData"; //$NON-NLS-1$

    private static final String HAS_INITIAL_VALUE =
            "bpmn.initialValueCorrelationData"; //$NON-NLS-1$

    private static final String NON_CORRELATION =
            "bpmn.associatedNonCorrelationData"; //$NON-NLS-1$

    private static final String UNUSED = "bpmn.notAssociatedCorrelationData"; //$NON-NLS-1$

    private static final String NO_CORRELATEMODE_DATA_FOR_REQACTIVITY =
            "bpmn.activityWithNoCorrelateModeCorrelationData"; //$NON-NLS-1$

    private static final String INVALID_START_MODE =
            "bpmn.invalidStartCorrelationMode"; //$NON-NLS-1$

    private static final String INVALID_JOIN_MODE =
            "bpmn.invalidJoinCorrelationMode"; //$NON-NLS-1$

    public static final String FIELD_KEY = "field"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        Map<String, DataField> defined = new HashMap<String, DataField>();
        for (DataField field : process.getDataFields()) {
            if (field.isCorrelation()) {
                BasicType type =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(field);
                if (type == null) {
                    addIssue(NON_BASIC, field);
                } else if (field.isIsArray()) {
                    addIssue(IS_ARRAY, field);
                }

                List<String> initVals =
                        ProcessDataUtil.getDataInitialValues(field, false);
                if (!initVals.isEmpty()) {
                    addIssue(HAS_INITIAL_VALUE, field);
                }
                defined.put(field.getName(), field);
            }
        }

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        Set<String> unused = new HashSet<String>(defined.keySet());
        for (Activity activity : activities) {
            if (Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)) {

                /*
                 * XPD-6751: Saket: Modified
                 * Xpdl2ModelUtil.isMessageProcessStartActivity(activity) to
                 * return true for only those activities that are in the main
                 * process and therefore we don't need to have
                 * Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)
                 * alongside it.
                 */
                boolean isStartActivity =
                        Xpdl2ModelUtil.isMessageProcessStartActivity(activity);

                Object other =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedCorrelationFields());
                if (other instanceof AssociatedCorrelationFields) {
                    AssociatedCorrelationFields fields =
                            (AssociatedCorrelationFields) other;
                    List<AssociatedCorrelationField> fieldList =
                            fields.getAssociatedCorrelationField();
                    if (fieldList.size() == 0) {
                        unused.clear();

                    } else {

                        boolean hasCorrelateModeAssoc = false;

                        for (AssociatedCorrelationField field : fieldList) {
                            String name = field.getName();
                            if (defined.containsKey(name)) {
                                unused.remove(name);
                            } else {
                                List<String> messages = new ArrayList<String>();
                                messages.add(name);
                                Map<String, String> info =
                                        new HashMap<String, String>();
                                info.put(FIELD_KEY, name);
                                addIssue(NON_CORRELATION,
                                        activity,
                                        messages,
                                        info);
                            }

                            CorrelationMode correlationMode =
                                    field.getCorrelationMode();

                            if (CorrelationMode.CORRELATE
                                    .equals(correlationMode)) {
                                hasCorrelateModeAssoc = true;
                            }

                            if (isStartActivity
                                    && CorrelationMode.CORRELATE
                                            .equals(correlationMode)) {
                                List<String> messages = new ArrayList<String>();
                                messages.add(name);
                                Map<String, String> info =
                                        new HashMap<String, String>();
                                info.put(FIELD_KEY, name);
                                addIssue(INVALID_START_MODE,
                                        activity,
                                        messages,
                                        info);
                            } else if (!isStartActivity
                                    && CorrelationMode.JOIN
                                            .equals(correlationMode)) {
                                List<String> messages = new ArrayList<String>();
                                messages.add(name);
                                Map<String, String> info =
                                        new HashMap<String, String>();
                                info.put(FIELD_KEY, name);
                                addIssue(INVALID_JOIN_MODE,
                                        activity,
                                        messages,
                                        info);
                            }
                        }

                        // If this is an inflow event with at least one explicit
                        // correlaiton association then at least one must be in
                        // correlate mode,.
                        if (!isStartActivity && !hasCorrelateModeAssoc) {
                            addIssue(NO_CORRELATEMODE_DATA_FOR_REQACTIVITY,
                                    activity);
                        }

                    }
                } else {
                    // No explicit associations
                    unused.clear();
                }

            }
        }
        for (String name : unused) {
            DataField field = defined.get(name);
            addIssue(UNUSED, field);
        }
    }

}
