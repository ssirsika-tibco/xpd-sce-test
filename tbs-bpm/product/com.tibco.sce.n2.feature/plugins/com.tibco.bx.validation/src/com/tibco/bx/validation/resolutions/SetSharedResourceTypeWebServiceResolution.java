/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetSharedResourceTypeWebServiceResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Nov 2009)
 */
public class SetSharedResourceTypeWebServiceResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Participant) {

            final Participant participant = (Participant) target;

            final ParticipantSharedResource sharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil
                            .getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());

            return new RecordingCommand(
                    (TransactionalEditingDomain) editingDomain) {
                @Override
                protected void doExecute() {
                    if (sharedResource == null) {
                        EReference sharedResFeature =
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantSharedResource();
                        ParticipantSharedResource psr =
                                XpdExtensionFactory.eINSTANCE
                                        .createParticipantSharedResource();
                        Xpdl2ModelUtil.addOtherElement(participant,
                                sharedResFeature,
                                psr);
                    }
                    if (sharedResource != null) {
                        WsResource wsResource =
                                XpdExtensionFactory.eINSTANCE
                                        .createWsResource();

                        /*
                         * XPD-7751: Saket: Need to set the type of the web
                         * service participant to Web service consumer for Web
                         * service task, send one way task and send one way
                         * throw message intermediate/end events.
                         */
                        boolean isWebServiceConsumerRequired = false;

                        /*
                         * XPD-7751: Saket: Need to set the type of the web
                         * service participant to Web service provider for
                         * incoming request activities.
                         */
                        boolean isWebServiceProviderRequired = false;

                        Package pkg = Xpdl2ModelUtil.getPackage(participant);

                        List<Process> allProcesses = pkg.getProcesses();

                        for (Process eachProcess : allProcesses) {

                            List<Activity> allActivities =
                                    eachProcess.getActivities();

                            for (Activity eachActivity : allActivities) {

                                List<Performer> allPerformers =
                                        eachActivity.getPerformerList();

                                for (Performer eachPerformer : allPerformers) {

                                    if (participant.getId()
                                            .equals(eachPerformer.getValue())) {

                                        if (isWebServiceConsumerRequired(eachActivity)) {

                                            isWebServiceConsumerRequired = true;
                                        }

                                        if (ReplyActivityUtil
                                                .isIncomingRequestActivity(eachActivity)) {

                                            isWebServiceProviderRequired = true;
                                        }
                                    }
                                }
                            }
                        }

                        /*
                         * XPD-7751: Saket: If the same participant is used in
                         * activities which need a web service consumer as well
                         * as web service provider, then simply do what we have
                         * been doing till now, i.e., just add the web service
                         * extension to the participant (and let the user decide
                         * whether they want to make it a consumer or a
                         * provider).
                         */
                        if (!(isWebServiceConsumerRequired && isWebServiceProviderRequired)) {

                            if (isWebServiceConsumerRequired) {

                                WsOutbound wsOutBound =
                                        XpdExtensionFactory.eINSTANCE
                                                .createWsOutbound();

                                WsVirtualBinding wsVirtualBinding =
                                        XpdExtensionFactory.eINSTANCE
                                                .createWsVirtualBindingDefault();

                                wsOutBound.setBinding(wsVirtualBinding);

                                wsResource.setOutbound(wsOutBound);

                                sharedResource.setSharedResource(wsResource);

                            } else if (isWebServiceProviderRequired) {

                                WsInbound wsInBound =
                                        XpdExtensionFactory.eINSTANCE
                                                .createWsInbound();

                                WsVirtualBinding wsVirtualBinding =
                                        XpdExtensionFactory.eINSTANCE
                                                .createWsVirtualBindingDefault();

                                wsInBound.setVirtualBinding(wsVirtualBinding);

                                wsResource.setInbound(wsInBound);

                                sharedResource.setSharedResource(wsResource);

                            }
                        }
                    }
                }

                /**
                 * Return <code>true</code> if the specified activity is either
                 * a Web service task or a send one way task or a send one way
                 * throw message intermediate/end event, <code>false</code>
                 * otherwise.
                 * 
                 * @param eachActivity
                 * 
                 * @return <code>true</code> if the specified activity is either
                 *         a Web service task or a send one way task or a send
                 *         one way throw message intermediate/end event,
                 *         <code>false</code> otherwise.
                 */
                private boolean isWebServiceConsumerRequired(
                        Activity eachActivity) {

                    boolean isWebServiceConsumerRequired = false;

                    TaskType taskType =
                            TaskObjectUtil.getTaskTypeStrict(eachActivity);

                    if (TaskType.SERVICE_LITERAL.equals(taskType)) {

                        TaskService taskSvc =
                                ((Task) eachActivity.getImplementation())
                                        .getTaskService();

                        if (isWebServiceImplementationType(taskSvc)) {

                            isWebServiceConsumerRequired = true;

                        }

                    } else if (TaskType.SEND_LITERAL.equals(taskType)) {

                        TaskSend taskSend =
                                ((Task) eachActivity.getImplementation())
                                        .getTaskSend();

                        Object replyToActivityId =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(taskSend,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ReplyToActivityId());

                        if (null == replyToActivityId
                                && isWebServiceImplementationType(taskSend)) {

                            isWebServiceConsumerRequired = true;

                        }

                    } else if (null != eachActivity.getEvent()) {

                        Event event = eachActivity.getEvent();

                        if (event instanceof IntermediateEvent
                                || event instanceof EndEvent) {

                            TriggerResultMessage trm;

                            if (event instanceof IntermediateEvent) {
                                trm =
                                        ((IntermediateEvent) event)
                                                .getTriggerResultMessage();
                            } else {
                                trm =
                                        ((EndEvent) event)
                                                .getTriggerResultMessage();
                            }

                            if (null != trm) {

                                Object replyToActivityId =
                                        Xpdl2ModelUtil
                                                .getOtherAttribute(trm,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_ReplyToActivityId());

                                if (replyToActivityId == null
                                        && ImplementationType.WEB_SERVICE_LITERAL
                                                .equals(EventObjectUtil
                                                        .getEventImplementationType(eachActivity))) {

                                    if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                                            .equals(EventObjectUtil
                                                    .getEventTriggerType(eachActivity))) {

                                        isWebServiceConsumerRequired = true;

                                    }
                                }
                            }
                        }
                    }

                    return isWebServiceConsumerRequired;
                }
            };

        }

        return null;
    }

    /**
     * Return <code>true</code> if the specified Send task is of web service
     * implementation type, <code>false</code> otherwise.
     * 
     * @param taskService
     * 
     * @return <code>true</code> if the specified Send task is of web service
     *         implementation type, <code>false</code> otherwise.
     */
    private static boolean isWebServiceImplementationType(
            OtherAttributesContainer implementationTypeContainer) {

        Object objImplementationType =
                Xpdl2ModelUtil.getOtherAttribute(implementationTypeContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());

        return TaskImplementationTypeDefinitions.WEB_SERVICE
                .equals(objImplementationType);
    }
}
