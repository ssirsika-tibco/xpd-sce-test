/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.wsdl.Part;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * WebServiceActivityConfigRule
 * <p>
 * <li>Check the configuration of WebService related activites.
 * 
 * <li>Check if an activity is invoking a output only service.
 * 
 * @author aallway
 * @since 3.3 (8 Oct 2009)
 */
public class WebServiceActivityConfigRule extends ProcessValidationRule {

    private static final String USELOCAL_NOT_SUPPORTED =
            "bx.useLocalNotSupported"; //$NON-NLS-1$

    private static final String USEREMOTE_MUST_HAVE_ENDPOINT =
            "bx.useRemoteMustHaveEndPoint"; //$NON-NLS-1$

    public static final String ISSUE_ENDPOINT_PARTIC_NOT_SYSTEM =
            "bx.endpointParticipantMustBeSystem"; //$NON-NLS-1$

    /**
     * Wsdl extension namespace prefix
     */
    public static final String TIBEX = "tibex";//$NON-NLS-1$

    private static final String ISSUE_OUTPUT_ONLY_SERVICE_NOT_SUPPORTED =
            "bx.invokingOutputOnlyServiceIsNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_PART_TYPE_NAME_TOO_LONG =
            "bx.partTypeNameTooLong"; //$NON-NLS-1$

    private static final String ISSUE_PART_TYPE_NAMESPACE_TOO_LONG =
            "bx.partTypeNamespaceTooLong"; //$NON-NLS-1$

    private static final String ISSUE_GENERATED_PART_ELEMENT_NAME_TOO_LONG =
            "bx.generatedPartElementNameTooLong"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        validateProcessActivities(activities);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com
     * .tibco.xpd.xpdl2.Process)
     */
    private void validateProcessActivities(EList<Activity> activities) {

        for (Activity activity : activities) {
            /*
             * Attempt to get the web-service message provider for activity - if
             * it isn't a web-service related task/event then we get null.
             */
            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (messageProvider != null
                    && messageProvider.isActualWebServiceActivity()) {
                /*
                 * Don't bother with reply activity (as content is copied from
                 * request activity, there is no point complaining about the
                 * reply).
                 */
                if (!ReplyActivityUtil.isReplyActivity(activity)) {
                    checkWebServiceActivityConfiguration(activity,
                            messageProvider);

                }
            }
        } /* Next Activity */

        return;
    }

    /**
     * Check various rules for Webservice related activity configutation.
     * 
     * @param activity
     * @param messageProvider
     */
    private void checkWebServiceActivityConfiguration(Activity activity,
            ActivityMessageProvider messageProvider) {

        WebServiceOperation webServiceOperation =
                messageProvider.getWebServiceOperation(activity);

        PortTypeOperation portTypeOperation =
                messageProvider.getPortTypeOperation(activity);

        if (webServiceOperation != null
                && isWebServiceOperationAssigned(webServiceOperation,
                        portTypeOperation)) {

            Service service = webServiceOperation.getService();

            if (service != null) {
                /*
                 * Check message parts configuration.
                 */
                checkMessageParts(activity,
                        messageProvider,
                        webServiceOperation,
                        portTypeOperation,
                        service);

                /*
                 * Check - Use local not supported.
                 */
                Boolean isRemote =
                        Xpdl2ModelUtil.getOtherAttributeAsBoolean(service,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsRemote());

                if (!isRemote) {
                    addIssue(USELOCAL_NOT_SUPPORTED, activity);

                } else {
                    /*
                     * Check - use remote must have valid system participant.
                     */
                    Participant endPointAlias = null;

                    String aliasId =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(webServiceOperation,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Alias());
                    if (aliasId != null) {
                        // Tries to retrieve the participant from the package
                        endPointAlias =
                                activity.getProcess().getPackage()
                                        .getParticipant(aliasId);
                        // If the participant isn't found try and find
                        // participants at the process level
                        if (endPointAlias == null) {
                            endPointAlias =
                                    activity.getProcess()
                                            .getParticipant(aliasId);
                        }
                    }

                    if (endPointAlias == null) {
                        // if found no participant at all add issue
                        // Even if the operation is generated in the same
                        // project and will be auto-wired we still need the
                        // participant or the composite/DAA generation fails.
                        addIssue(USEREMOTE_MUST_HAVE_ENDPOINT, activity);
                    } else {
                        // checks if the participant is System
                        ParticipantTypeElem participantType =
                                endPointAlias.getParticipantType();

                        if (participantType == null
                                || !ParticipantType.SYSTEM_LITERAL
                                        .equals(participantType.getType())) {
                            addIssue(ISSUE_ENDPOINT_PARTIC_NOT_SYSTEM, activity);
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * Check message part related issues.,
     * 
     * @param activity
     */
    private void checkMessageParts(Activity activity,
            ActivityMessageProvider activityMessageProvider,
            WebServiceOperation webServiceOperation,
            PortTypeOperation portTypeOperation, Service service) {

        /* Check the quick things first! */
        IProject project = WorkingCopyUtil.getProjectFor(activity);

        if (null != project) {
            String filePath = Xpdl2WsdlUtil.getLocation(service);
            String serviceName = service.getServiceName();
            String operationName = webServiceOperation.getOperationName();
            String portName = service.getPortName();

            String portTypeName = null;
            String portTypeOperationName = null;

            if (null != portTypeOperation) {

                portTypeName = portTypeOperation.getPortTypeName();
                portTypeOperationName = portTypeOperation.getOperationName();

                if (null == filePath) {
                    filePath = Xpdl2WsdlUtil.getLocation(portTypeOperation);
                }
            }

            WsdlServiceKey key =
                    new WsdlServiceKey(serviceName, portName, operationName,
                            portTypeName, portTypeOperationName, filePath);

            Collection<Part> inputParts =
                    WsdlIndexerUtil.getInputParts(project, key, true, true);

            Collection<Part> outputParts =
                    WsdlIndexerUtil.getOutputParts(project, key, true, true);

            /**
             * Out only web-service not supported.
             */
            if (!Xpdl2ModelUtil.isProcessAPIActivity(activity)
                    && !TaskImplementationTypeDefinitions.INVOKE_BUSINESS_PROCESS
                            .equals(TaskObjectUtil
                                    .getTaskImplementationExtensionId(activity))) {

                /*
                 * Sid XPD-2820: A web-service can only be considered out-only
                 * IF it actually has output parameters!!
                 */
                if (outputParts != null && outputParts.size() > 0) {
                    if ((null == inputParts || inputParts.size() == 0)) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(portTypeOperationName);
                        addIssue(ISSUE_OUTPUT_ONLY_SERVICE_NOT_SUPPORTED,
                                activity,
                                messages);
                    }
                }
            }

            /**
             * Because of restrictions in the runtime PVMP_DATA_TYPE table
             * columns, we must ensure that...
             * 
             * Element / type name referenced from Input/Output parts are <= 6
             * characters.
             * 
             * The namespace URI for those elements/types are < 256 characters.
             */
            if (inputParts != null) {
                for (Part part : inputParts) {
                    checkMessagePartConfiguration(activity, part);
                }
            }

            if (outputParts != null) {
                for (Part part : outputParts) {
                    checkMessagePartConfiguration(activity, part);
                }
            }
        }
    }

    /**
     * Check that the configuration of the part is valid.
     * 
     * @param activity
     * @param part
     */
    private void checkMessagePartConfiguration(Activity activity, Part part) {
        String nameWithoutPrefix = null;
        String namespace = null;

        if (part.getElementName() != null) {
            nameWithoutPrefix = part.getElementName().getLocalPart();
            namespace = part.getElementName().getNamespaceURI();

        } else if (part.getTypeName() != null) {
            nameWithoutPrefix = part.getTypeName().getLocalPart();
            namespace = part.getTypeName().getNamespaceURI();
        }

        if (nameWithoutPrefix != null && nameWithoutPrefix.length() > 64) {
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                /*
                 * If this is a generated WSDL then use a different error
                 * message to lead the user towards the source of problem.
                 */
                List<String> params = new ArrayList<String>();
                params.add(nameWithoutPrefix);
                params.add(String.valueOf(nameWithoutPrefix.length()));
                addIssue(ISSUE_GENERATED_PART_ELEMENT_NAME_TOO_LONG,
                        activity,
                        params);

            } else {
                List<String> params = new ArrayList<String>();
                params.add(nameWithoutPrefix);
                params.add(String.valueOf(nameWithoutPrefix.length()));
                addIssue(ISSUE_PART_TYPE_NAME_TOO_LONG, activity, params);
            }
        }

        if (namespace != null && namespace.length() > 256) {
            List<String> params = new ArrayList<String>();
            params.add(namespace);
            params.add(String.valueOf(namespace.length()));
            addIssue(ISSUE_PART_TYPE_NAMESPACE_TOO_LONG, activity, params);
        }
    }

    /**
     * @param webServiceOperation
     * @param portTypeOperation
     * 
     * @return true if the activity has a web service operation configured.
     */
    private boolean isWebServiceOperationAssigned(
            WebServiceOperation webServiceOperation,
            PortTypeOperation portTypeOperation) {
        String operationName = null;

        if (portTypeOperation != null) {
            operationName = portTypeOperation.getOperationName();
        }

        if (operationName == null || operationName.length() == 0) {
            if (webServiceOperation != null) {
                operationName = webServiceOperation.getOperationName();
            }
        }

        if (operationName != null && operationName.length() > 0) {
            return true;
        }

        return false;
    }
}
