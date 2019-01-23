/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check that the REST service has been generated for the
 * RESTful activity & whether the generated REST service task type, port type
 * operation and parameters are consistent with the request activity
 * 
 * @author agondal
 * @since 2 Jan 2013
 */
public class RESTserviceGenerationRule extends ProcessActivitiesValidationRule {

    protected static final String ISSUE_REST_SERVICE_GENERATION_FAILED =
            "bx.restServiceGenerationFailed"; //$NON-NLS-1$

    protected static final String ISSUE_INCONSISTENT_REST_SERVICE =
            "bx.restServiceInconsistentWithActivity"; //$NON-NLS-1$

    private static final String ISSUE_INPUT_PARAM_TYPE_CASE_REF_NOT_ALLOWED =
            "bx.caseRefParamsOFTypeInCannotPublishAsRest"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    public void validate(Activity activity) {
        if (activity != null) {
            if (RestServiceUtil.isRESTfulActivity(activity)) {

                /*
                 * XPD-7686: Do not allow restful start none events to have in
                 * mode case ref params associated.
                 */
                checkForInCaseRefParticOnStartNoneEvents(activity);

                Process restService = RestServiceUtil.getRestService(activity);
                if (restService == null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(activity.getName());
                    addIssue(ISSUE_REST_SERVICE_GENERATION_FAILED,
                            activity,
                            messages);
                } else {
                    /*
                     * Check whether the generated REST service's parameters
                     * set, send task/service task type and operation is
                     * consistent with the generating request activity
                     */
                    Activity restServiceActivity =
                            getRestServiceActivity(restService);

                    if (restServiceActivity != null) {

                        boolean inconsistent = false;

                        if (!isRestServiceParametersConsistentWithActivity(activity,
                                restService)) {

                            inconsistent = true;

                        } else if (!isRestServiceTaskTypeConsistentWithActivity(activity,
                                restServiceActivity)
                                || !isRestServiceOpConsistentWithActivity(activity,
                                        restServiceActivity)) {

                            inconsistent = true;
                        }

                        if (inconsistent) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(Xpdl2ModelUtil
                                    .getDisplayNameOrName(restService));
                            messages.add(Xpdl2ModelUtil
                                    .getDisplayNameOrName(activity));

                            addIssue(ISSUE_INCONSISTENT_REST_SERVICE,
                                    activity,
                                    messages);
                        }
                    }
                }
            }
        }

    }

    /**
     * Checks if the activity is a restful start event and has Input Model Case
     * REf formal parameter associated with it, if so then raises issue for the
     * same.
     * 
     * @param activity
     */
    private void checkForInCaseRefParticOnStartNoneEvents(Activity activity) {
        /*
         * Only Biz Process Start events are restful activities.
         */
        if (isStartTypeNoneEvent(activity)
                && Xpdl2ModelUtil.isBusinessProcess(activity.getProcess())) {

            Collection<ActivityInterfaceData> activityInterfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);

            for (ActivityInterfaceData interfaceData : activityInterfaceData) {

                if (interfaceData.getData() instanceof FormalParameter) {

                    FormalParameter fp =
                            (FormalParameter) interfaceData.getData();

                    String modeLit = fp.getMode().getLiteral();

                    if (ModeType.INOUT_LITERAL.getLiteral().equals(modeLit)
                            || ModeType.IN_LITERAL.getLiteral().equals(modeLit)) {

                        if (fp.getDataType() instanceof RecordType) {
                            /*
                             * just need to check if the param is record type,
                             * no need to dig into bom to find the actual case
                             * class.
                             */
                            addIssue(ISSUE_INPUT_PARAM_TYPE_CASE_REF_NOT_ALLOWED,
                                    activity);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is a Start Type 'None'
     *         event else return <code>false</code>
     */
    private boolean isStartTypeNoneEvent(Activity activity) {
        return EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                .getFlowType(activity))
                && EventTriggerType.EVENT_NONE_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(activity));
    }

    /**
     * Checks whether the REST service activity's portTypeOperation is
     * consistent with that of the generating activity
     * 
     * @param activity
     * @param restServiceActivity
     * @return
     */
    private boolean isRestServiceOpConsistentWithActivity(Activity activity,
            Activity restServiceActivity) {

        boolean consistentRestServiceOp = false;

        PortTypeOperation restServicePo =
                Xpdl2ModelUtil.getPortTypeOperation(restServiceActivity);

        PortTypeOperation activityPo =
                Xpdl2ModelUtil.getPortTypeOperation(activity);

        if (restServicePo != null && activityPo != null) {

            // compare portTypeName, operationName & externalReference
            if (restServicePo.getOperationName()
                    .equals(activityPo.getOperationName())
                    && restServicePo.getPortTypeName()
                            .equals(activityPo.getPortTypeName())
                    && restServicePo
                            .getExternalReference()
                            .getLocation()
                            .equals(activityPo.getExternalReference()
                                    .getLocation())) {

                consistentRestServiceOp = true;

            }

        } else if (restServicePo == null && activityPo == null) {

            consistentRestServiceOp = true;

        } else {

            consistentRestServiceOp = false;
        }

        return consistentRestServiceOp;
    }

    /**
     * Checks that the given REST service activity has correct task type, i.e.,
     * a rest service should have a send task for the generating request
     * activity and a service task for the generating request-reply activity
     * 
     * @param activity
     * @param restServiceActivity
     * @return <code>true</code> if consistent, <code>false</code> otherwise
     */
    private boolean isRestServiceTaskTypeConsistentWithActivity(
            Activity activity, Activity restServiceActivity) {

        boolean consistentRestServiceTaskType = false;

        if (ReplyActivityUtil.isRequestActivityWithReply(activity)) {

            /*
             * its a request-reply activity, its REST service activity should be
             * a service task activity
             */
            if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(restServiceActivity))) {

                consistentRestServiceTaskType = true;

            }

        } else {

            /*
             * its a request-only activity, its REST service activity should be
             * a send task
             */
            if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(restServiceActivity))) {

                consistentRestServiceTaskType = true;

            }
        }

        return consistentRestServiceTaskType;
    }

    /**
     * Checks that the given REST service formal parameters are same as the
     * generating request activity parameters
     * 
     * @param requestActivity
     * @param restService
     * @return <code>true</code> if consistent, <code>false</code> otherwise
     */
    private boolean isRestServiceParametersConsistentWithActivity(
            Activity requestActivity, Process restService) {

        boolean consistentRestServiceParameters = false;

        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil
                        .getActivityInterfaceData(requestActivity);

        EList<FormalParameter> restServiceParameters =
                restService.getFormalParameters();
        /*
         * In case of user-defined wsdl, we generate REST service parameters
         * from the wsdl parts and hence don't need to compare them. Only if
         * it's a generated request activity then we need to compare the REST
         * service parameters with associated activity interface data.
         */

        if (!Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {
            return true;
        }

        /*
         * If the requestActivity is reply immediate start message event
         * (generated ws) then the generated Rest service will have an extra OUT
         * formal parameter 'ProcessId', which doesn't exist in activity
         * interface data
         */
        int processIdParam = 0;
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(requestActivity)) {

            processIdParam = 1;
        }

        if (restServiceParameters.size() - processIdParam != activityInterfaceData
                .size()) {
            return consistentRestServiceParameters;
        }

        if (activityInterfaceData.isEmpty()
                && restServiceParameters.size() - processIdParam == 0) {
            return true;
        }

        for (ActivityInterfaceData interfaceData : activityInterfaceData) {
            /**
             * XPD-4470: activity may be in bad state where a data field is
             * explicitly associated. So should not assume is formal parameter.
             */
            if (!(interfaceData.getData() instanceof FormalParameter)) {
                return false;
            }

            FormalParameter activityParamter =
                    (FormalParameter) EcoreUtil.copy(interfaceData.getData());

            // Make ID of both the parameters same before checking for equality

            String tempId = "1"; //$NON-NLS-1$

            EcoreUtil.setID(activityParamter, tempId);

            consistentRestServiceParameters = false;

            for (FormalParameter param : restServiceParameters) {

                FormalParameter restServiceParamter = EcoreUtil.copy(param);

                EcoreUtil.setID(restServiceParamter, tempId);

                if (EcoreUtil.equals(activityParamter, restServiceParamter)) {
                    consistentRestServiceParameters = true;
                    break;
                }
            }

            if (!consistentRestServiceParameters) {
                return false;
            }

        }

        return consistentRestServiceParameters;
    }

    /**
     * @param restService
     * 
     * @return send task or service task of the given restService
     */
    private Activity getRestServiceActivity(Process restService) {

        for (Activity activity : restService.getActivities()) {

            if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))
                    || TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict(activity))) {
                return activity;
            }
        }

        return null;
    }
}
