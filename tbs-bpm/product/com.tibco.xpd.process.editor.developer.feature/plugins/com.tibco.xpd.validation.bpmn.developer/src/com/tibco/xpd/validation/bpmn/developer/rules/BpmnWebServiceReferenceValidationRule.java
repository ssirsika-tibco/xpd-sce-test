/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.indexing.ActivityWebServiceReferenceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlBomProjectNature;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate the web-service related activities have web-service selected and
 * that the given operation is available.
 * 
 * @author aallway
 * @since 3.3 (20 Jan 2010)
 */
public class BpmnWebServiceReferenceValidationRule extends
        ProcessValidationRule {

    public static final String ISSUE_WEBSERVICE_NOT_SET =
            "bpmn.dev.webServiceNotSet"; //$NON-NLS-1$

    public static final String ISSUE_CANT_ACCESS_WEBSERVICE =
            "bpmn.dev.cantAccessWebService"; //$NON-NLS-1$

    private static final String ISSUE_WEB_SERVICE_REFERENCE_NOT_INDEXED =
            "bpmn.dev.webServiceReferenceNotIndexed"; //$NON-NLS-1$

    public static final String ISSUE_CANT_ACCESS_BIZPROC_SERVICE =
            "bpmn.dev.businessProcessWsdlConfigProblem"; //$NON-NLS-1$

    public static final String ISSUE_INCORRECT_PORT_TYPE_SET =
            "bpmn.dev.incorrectPortTypeSet"; //$NON-NLS-1$

    public static final String ISSUE_WSDL_FROM_NON_BPM_PROJECT =
            "bpmn.dev.wsdlFromNonBPMProject"; //$NON-NLS-1$

    public static final String ATT_CORRECT_PORTTYPE_NAME =
            "correct-port-type-name"; //$NON-NLS-1$

    /**
     * Send task requires a reference to a sub-process activity
     */
    private static final String ISSUE_NO_PROCESS_ACTIVITY_SELECTED =
            "bpmn.dev.noBusinessProcessRequestActivitySelected"; //$NON-NLS-1$

    /**
     * Send task refers a business process request activity that does not exist
     */
    private static final String ISSUE_REFERENCED_REQUEST_ACTIVITY_DOES_NOT_EXIST =
            "bpmn.dev.referencedBusinessProcessRequestActivityDoesNotExist"; //$NON-NLS-1$

    /**
     * The business process request activity is not set for the Send task
     */
    private static final String ISSUE_REFERENCED_REQUEST_ACTIVITY_NOT_SET =
            "bpmn.dev.referencedBusinessProcessRequestActivityNotSet"; //$NON-NLS-1$

    /**
     * The invoked business process activity must be request-only (the
     * referenced request activity has a reply-output message).
     */
    private static final String ISSUE_INVOKED_REQUEST_ACTIVITY_MUST_BE_REQUEST_ONLY =
            "bpmn.dev.selectedBusinessProcessActivityMustBeRequestOnly"; //$NON-NLS-1$

    /**
     * The send task web service operation is inconsistent with the invoked
     * business process request activity
     */
    private static final String ISSUE_INCONSISTENT_SEND_TASK_WEB_SERVICE_OPERATION =
            "bpmn.dev.inconsistentSendTaskWebServiceOperation"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(Process process) {

        Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap =
                null;

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            /*
             * If activity has a message provider then it's web-service related.
             * 
             * (Also check that it's not a message provider for an activity that
             * only has operation by reference (such as throw/catch wsdl error).
             */
            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);

            if (messageProvider != null
                    && messageProvider.isActualWebServiceActivity()) {

                /*
                 * Create web-service reference map once we know we have
                 * references.
                 */
                if (activityWebServiceReferenceMap == null) {
                    activityWebServiceReferenceMap =
                            ProcessUIUtil
                                    .getActivityWebServiceReferenceMap(process
                                            .getPackage());
                }

                /*
                 * MR 42774: Only complain about web service not set if the
                 * activity has not been set to unspecified.
                 */
                if (ImplementationType.WEB_SERVICE_LITERAL
                        .equals(messageProvider.getImplementation(activity))) {

                    if (isInvokeBusinessProcessType(activity)) {
                        checkInvokeBusinessProcessIssues(activity,
                                activityWebServiceReferenceMap);
                    } else {
                        checkWebServiceIssues(activity,
                                messageProvider,
                                activityWebServiceReferenceMap);
                    }
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * @param activityWebServiceReferenceMap
     */
    private void checkInvokeBusinessProcessIssues(
            Activity activity,
            Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap) {

        if (checkSubProcessActivityReference(activity)) {
            /*
             * Sub-process activity reference is ok - check that it's request
             * only too.
             */

            /*
             * SID XPD-1675: We should not base our decision of whether the
             * process is 'request-only' on whether ANY request event has
             * out/inout parameters as this causes a variety of problems...
             * 
             * If the process as a reply activity with no associated output data
             * then it is still request-response.
             * 
             * If there are multiple start events we check all (not just the one
             * we reference).
             * 
             * If there are secondary intermediate request activities then we
             * would complain unnecessarily.
             * 
             * We simply should base our decision on whether the WSDL operation
             * that we reference for the process has an output message as it is
             * the presence of an output message that defines a service as
             * request-response.
             */
            if (Xpdl2WsdlUtil.getOperation(activity) != null) {

                /*
                 * The operation exists so lets check whether we have the same
                 * operation in both the activities
                 */

                boolean consistentOp =
                        checkInvokeBizProcSendTaskOperationConsistency(activity,
                                activityWebServiceReferenceMap);

                if (!consistentOp) {

                    List<String> messages = new ArrayList<String>();
                    messages.add(Xpdl2ModelUtil.getDisplayNameOrName(activity));
                    addIssue(ISSUE_INCONSISTENT_SEND_TASK_WEB_SERVICE_OPERATION,
                            activity,
                            messages);

                } else {

                    /*
                     * The operation is consistent so lets check whether we have
                     * an output part.
                     */
                    Output wsdlOutputPart =
                            Xpdl2WsdlUtil.getWsdlOperationOutput(activity);
                    if (wsdlOutputPart != null) {
                        addIssue(ISSUE_INVOKED_REQUEST_ACTIVITY_MUST_BE_REQUEST_ONLY,
                                activity);

                    } else if (!isWebServiceReferenceIndexed(activity,
                            activityWebServiceReferenceMap)) {
                        /*
                         * Only bother checking if not index if every thing else
                         * is ok - if the has to change something the we'll get
                         * re-indexed anyway!.
                         */
                        addIssue(ISSUE_WEB_SERVICE_REFERENCE_NOT_INDEXED,
                                activity);
                    }
                }

            } else {
                /*
                 * XPD-685: If this is Business Process web service invocation
                 * (i.e. from a business service pageflow) then we still need to
                 * check that the wsdl operation is available. It might not be
                 * if business process name changes or request activity name
                 * changes etc.
                 * 
                 * Only complain if the business process is actually available -
                 * if not there is a different validation rule for that.
                 */
                addIssue(ISSUE_CANT_ACCESS_BIZPROC_SERVICE, activity);
            }
        }
    }

    /**
     * @param activity
     * @param activityWebServiceReferenceMap
     * 
     * @return true if the web service operation is consistent, false otherwise
     */
    private boolean checkInvokeBizProcSendTaskOperationConsistency(
            Activity activity,
            Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap) {

        ActivityWebServiceReference sendTaskWebServiceRef =
                activityWebServiceReferenceMap.get(activity.getId());

        /* get activity id of the invoked business process activity */

        BusinessProcess invokedBizProc =
                WebServiceOperationUtil.getBusinessProcess(activity);

        if (invokedBizProc != null && invokedBizProc.getActivityId() != null) {

            /*
             * If the request activity is in different package then we need to
             * first get the correct web service reference as it will not be
             * present in our activityWebServiceReferenceMap, so we create
             * invokedActivityWebServiceRef from the referenced request activity
             */

            ActivityWebServiceReference invokedActivityWebServiceRef = null;

            if (invokedBizProc.getPackageRefId() != null
                    && !invokedBizProc.getPackageRefId().isEmpty()
                    && !invokedBizProc.getPackageRefId().equals(activity
                            .getProcess().getPackage().getId())
                    && invokedBizProc.getProcessId() != null
                    && !invokedBizProc.getProcessId().isEmpty()) {

                Process refProcess =
                        ProcessUIUtil.getProcesById(invokedBizProc
                                .getProcessId());

                if (refProcess != null) {

                    Activity refActivity =
                            Xpdl2ModelUtil.getActivityById(refProcess,
                                    invokedBizProc.getActivityId());

                    if (refActivity != null) {

                        invokedActivityWebServiceRef =
                                ActivityWebServiceReference
                                        .createActivityWebServiceReference(activity);

                    }

                }

            } else {

                invokedActivityWebServiceRef =
                        activityWebServiceReferenceMap.get(invokedBizProc
                                .getActivityId());

            }

            if (sendTaskWebServiceRef != null
                    && invokedActivityWebServiceRef != null) {

                // compare the two and return true if both are the same

                if (sendTaskWebServiceRef.getOperation() != null
                        && sendTaskWebServiceRef.getPortTypeName() != null
                        && sendTaskWebServiceRef.getWsdlFileLocation() != null
                        && invokedActivityWebServiceRef.getOperation() != null
                        && invokedActivityWebServiceRef.getPortTypeName() != null
                        && invokedActivityWebServiceRef.getWsdlFileLocation() != null) {

                    if (sendTaskWebServiceRef
                            .getOperation()
                            .equals(invokedActivityWebServiceRef.getOperation())
                            && sendTaskWebServiceRef
                                    .getPortTypeName()
                                    .equals(invokedActivityWebServiceRef.getPortTypeName())
                            && sendTaskWebServiceRef
                                    .getWsdlFileLocation()
                                    .equals(invokedActivityWebServiceRef.getWsdlFileLocation())) {

                        return true;

                    }
                }
            } else if (sendTaskWebServiceRef == null
                    && invokedActivityWebServiceRef == null) {

                return true;

            }
        }

        return false;
    }

    /**
     * Check the reference to the sub-process activity from the given
     * sub-process task. Sid XPD-2918 - moved from
     * {@link SendTaskInvokeBusinessProcessReferenceRule} so that we can only
     * check webServiceReference is indexed if there is no "can't access
     * business process type commands.
     * 
     * @param activity
     */
    private boolean checkSubProcessActivityReference(Activity activity) {
        EObject subProcessOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);
        if (subProcessOrInterface == null) {
            /*
             * Can't find the business process (at least in anything that's
             * referencible from our activity).
             * 
             * This might be that the other process simply does not exist
             * anymore OR could be that it that the referenced process is in
             * another unreferenced project. In this case the process's id may
             * well be found in the index so we can give a different error that
             * will allow a quick fix that can fix things up nicely.
             */
            String subProcessId = null;
            String refActivityId = null;
            Task task = (Task) activity.getImplementation();
            if (null != task.getTaskSend()) {
                if (WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(task
                                .getTaskSend())) {
                    BusinessProcess businessProcess =
                            WebServiceOperationUtil
                                    .getBusinessProcess(activity);
                    if (null != businessProcess) {
                        subProcessId = businessProcess.getProcessId();
                        refActivityId = businessProcess.getActivityId();
                    } else {
                        addIssue(ISSUE_NO_PROCESS_ACTIVITY_SELECTED, activity);
                        return false;
                    }
                }
            }
            if ((refActivityId != null && refActivityId.length() > 0 && !TaskObjectUtil.UNKNOWN_REF_ID
                    .equals(refActivityId))
                    || (subProcessId != null && subProcessId.length() > 0 && !TaskObjectUtil.UNKNOWN_REF_ID
                            .equals(subProcessId))) {
                /*
                 * Cannot access process activity even though there's a
                 * reference in model.
                 */
                addIssue(ISSUE_REFERENCED_REQUEST_ACTIVITY_DOES_NOT_EXIST,
                        activity);
            }
        } else {

            Activity refActivity =
                    TaskObjectUtil
                            .getInvokeBusinessProcessReferencedActivity(activity);
            if (refActivity == null) {
                List<Activity> startActivities =
                        TaskObjectUtil
                                .getInvokeBusinessProcessStartActivities(TaskObjectUtil
                                        .getInvokeBusinessProcess(activity));
                // XPD-4978: check if the referenced process has single Start
                // activity.
                if (startActivities != null && startActivities.size() == 1) {
                    addIssue(ISSUE_REFERENCED_REQUEST_ACTIVITY_NOT_SET,
                            activity);
                } else {
                    addIssue(ISSUE_REFERENCED_REQUEST_ACTIVITY_DOES_NOT_EXIST,
                            activity);
                }
                return false;
            }
        }
        return true;
    }

    /**
     * @param activity
     * @param messageProvider
     * @param activityWebServiceReferenceMap
     */
    private void checkWebServiceIssues(
            Activity activity,
            ActivityMessageProvider messageProvider,
            Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap) {
        if (!isWebServiceDefined(activity, messageProvider)) {
            addIssue(ISSUE_WEBSERVICE_NOT_SET, activity);

        } else if (!isGeneratedRequestOrReply(activity)) {
            /*
             * Don't complain about activities that WSDL ops are generated for -
             * they may not be generated until an appropriate dest env is
             * selected.
             */
            Operation operation = Xpdl2WsdlUtil.getOperation(activity);
            if (operation == null) {
                addIssue(ISSUE_CANT_ACCESS_WEBSERVICE, activity);

            } else if (!isWsdlFromBPMProject(WorkingCopyUtil.getProjectFor(activity),
                    operation)) {
                if (isJavaScriptGrammarSelected(activity)) {
                    addIssue(ISSUE_WSDL_FROM_NON_BPM_PROJECT, activity);
                }
            } else if (!isWebServiceReferenceIndexed(activity,
                    activityWebServiceReferenceMap)) {
                addIssue(ISSUE_WEB_SERVICE_REFERENCE_NOT_INDEXED, activity);

            } else {
                /*
                 * If concrete operation selected then ensure the port-type is
                 * correct (the user may have changed the port-type name in the
                 * wsdl after selecting this concrete operation).
                 */
                if (operation instanceof EObject) {
                    EObject container = ((EObject) operation).eContainer();

                    if (container instanceof PortType) {
                        QName qName = ((PortType) container).getQName();
                        if (qName != null) {
                            WsdlServiceKey serviceKey =
                                    Xpdl2WsdlUtil.getServiceKey(activity);
                            if (serviceKey != null
                                    && serviceKey.getPortType() != null) {
                                if (!serviceKey.getPortType()
                                        .equals(qName.getLocalPart())) {
                                    Map<String, String> info =
                                            new HashMap<String, String>();
                                    info.put(ATT_CORRECT_PORTTYPE_NAME,
                                            qName.getLocalPart());

                                    addIssue(ISSUE_INCORRECT_PORT_TYPE_SET,
                                            activity,
                                            Collections
                                                    .singletonList(serviceKey
                                                            .getPortType()),
                                            info);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if JavaScript grammar is used in the Input/Output mapping on this
     * activity.
     * 
     * @param activity
     * @return
     */
    private boolean isJavaScriptGrammarSelected(Activity activity) {

        /*
         * XPD-3422: Saket: Check if the activity is an incoming request
         * activity because then we check only for OUT mappings as reply
         * activities never have IN mappings.
         * 
         * Note: we don't do anything special about reply-immediate start events
         * because there IN (from-web-svc) mappings are forced to be XPath
         * anyway by the UI.
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
            return isJavaScriptGrammarSelected(activity,
                    DirectionType.OUT_LITERAL);
        }
        /*
         * XPD-3422: Saket: Check if the activity is a reply activity because
         * then we check only for IN mappings as reply activities never have OUT
         * mappings.
         */
        else if (ReplyActivityUtil.isReplyActivity(activity)) {
            return isJavaScriptGrammarSelected(activity,
                    DirectionType.IN_LITERAL);
        }
        /*
         * XPD-3422: Saket: Check if the activity is an invoke-one-way-activity
         * because then we check only for IN mappings.
         */
        else if (Xpdl2ModelUtil.isSendOneWayRequest(activity)) {
            return isJavaScriptGrammarSelected(activity,
                    DirectionType.IN_LITERAL);
        }
        /*
         * XPD-3422: Saket: By default, we check for both IN and OUT mappings.
         */
        else {
            return isJavaScriptGrammarSelected(activity,
                    DirectionType.IN_LITERAL)
                    || isJavaScriptGrammarSelected(activity,
                            DirectionType.OUT_LITERAL);
        }
    }

    /**
     * Check if JavaScript grammar is selected for the mapping in the given
     * direction.
     * 
     * @param activity
     * @param direction
     * @return the grammar from the mapping in the model, or default grammar if
     *         no mapping set yet.
     */
    private boolean isJavaScriptGrammarSelected(Activity activity,
            DirectionType direction) {
        String grammar =
                ScriptGrammarFactory.getScriptGrammar(activity, direction);
        if (grammar == null) {
            grammar = ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }
        return ScriptGrammarFactory.JAVASCRIPT.equals(grammar);
    }

    /**
     * Check if the referenced WSDL is contained in a BPM project with the
     * Business Process asset configured (only under these projects will the
     * BOMs be generated for WSDLs).
     * 
     * @param activity
     * @return
     */
    private boolean isWsdlFromBPMProject(IProject hostProject,
            Operation operation) {

        if (hostProject != null && operation instanceof EObject) {
            IFile wsdlFile = WorkingCopyUtil.getFile((EObject) operation);

            if (wsdlFile != null) {
                IProject refProject = wsdlFile.getProject();

                if (!hostProject.equals(refProject)) {
                    // Wsdl referenced from external project
                    try {
                        if (refProject
                                .hasNature(WsdlBomProjectNature.NATURE_ID)
                                && BOMValidatorActivator
                                        .getDefault()
                                        .isValidationDestinationEnabled(refProject,
                                                BOMValidatorActivator.VALIDATION_DEST_ID_WSDL_TO_BOM)) {
                            return true;
                        }
                    } catch (CoreException e) {
                        // Do nothing
                    }
                } else {
                    // Wsdl referenced from local project
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Because we do not sequence the order in which indexer's run, it is
     * possible that when the {@link ActivityWebServiceReferenceIndexProvider}
     * runs the Wsdl indexer service has not yet run.
     * <p>
     * The activity web service index provider is dependent upon there being an
     * entry for the selected operation etc in the wsdl index and will not
     * create webServiceReference indexes for activity otherwise. This can
     * happen on first import if the webServiceReference Indexer is executed
     * befiore the wsdl indexer.
     * <p>
     * So until we can sequence the index providers we should ensure that there
     * is a wbServiceReference index entry for the activity.
     * 
     * 
     * @param activity
     * @param activityWebServiceReferenceMap
     * @return <code>true</code> if an entry was found in the
     *         webServiceReference index for the given activity.
     */
    private boolean isWebServiceReferenceIndexed(
            Activity activity,
            Map<String, ActivityWebServiceReference> activityWebServiceReferenceMap) {
        if (activityWebServiceReferenceMap.containsKey(activity.getId())) {
            return true;
        }
        return false;
    }

    /**
     * @param activity
     * @return true if the activity is a generated request or a reply to one.
     */
    private boolean isGeneratedRequestOrReply(Activity activity) {
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            activity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
        }

        if (activity != null) {
            return Xpdl2ModelUtil.isGeneratedRequestActivity(activity);
        }

        return false;
    }

    private boolean isInvokeBusinessProcessType(Activity activity) {
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (null != task.getTaskSend()) {
                return WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(task
                                .getTaskSend());
            }
        }
        return false;
    }

    /**
     * @param activity
     * @param messageProvider
     * @return true if the web service operation is defined.
     */
    private boolean isWebServiceDefined(Activity activity,
            ActivityMessageProvider messageProvider) {
        WebServiceOperation wso =
                messageProvider.getWebServiceOperation(activity);
        PortTypeOperation pto = messageProvider.getPortTypeOperation(activity);

        if (wso != null && pto != null) {
            String op = pto.getOperationName();
            if (op != null && op.length() > 0) {
                return true;
            }
        }
        return false;
    }

}
