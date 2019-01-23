/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ConvertBWToWebServiceResolution
 * 
 * 
 * @author glewis
 */
public class ConvertBWToWebServiceResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            ImplementationTypeHostInfo implementationTypeHost =
                    getImplementationTypeHost((Activity) target);

            if (implementationTypeHost != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ConvertBWToWebServiceCmd_label);
                ActivityMessageProvider messageProvider =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider((Activity) target);
                if (messageProvider != null) {
                    cmd.append(messageProvider
                            .getAssignWebServiceIsRemoteCommand(editingDomain,
                                    (Activity) target,
                                    true));
                }

                String extImplementationType =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute((OtherAttributesContainer) implementationTypeHost.hostObject,
                                        implementationTypeHost.xpdExtImplementationTypeFeature);
                if (TaskImplementationTypeDefinitions.BW_SERVICE
                        .equals(extImplementationType)) {
                    cmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            (OtherAttributesContainer) implementationTypeHost.hostObject,
                                            implementationTypeHost.xpdExtImplementationTypeFeature,
                                            TaskImplementationTypeDefinitions.WEB_SERVICE));
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }
        }

        return null;
    }

    /**
     * @return info on the EObject that should (if necessary) contain the
     *         implementation type - else null if not a web-service activity.
     */
    public static ImplementationTypeHostInfo getImplementationTypeHost(
            Activity activity) {
        /*
         * For events we just go on whether the trigger type is set to message
         * (because sometimes even the xpdl2:Implementation wasn't written!
         */

        if (activity.getEvent() instanceof StartEvent) {
            StartEvent startEvent = (StartEvent) activity.getEvent();

            if (TriggerType.MESSAGE_LITERAL.equals(startEvent.getTrigger())) {
                return new ImplementationTypeHostInfo(startEvent,
                        Xpdl2Package.eINSTANCE.getStartEvent_Implementation());
            }

        } else if (activity.getEvent() instanceof IntermediateEvent) {
            IntermediateEvent intermediateEvent =
                    (IntermediateEvent) activity.getEvent();

            if (TriggerType.MESSAGE_LITERAL.equals(intermediateEvent
                    .getTrigger())) {
                return new ImplementationTypeHostInfo(intermediateEvent,
                        Xpdl2Package.eINSTANCE
                                .getIntermediateEvent_Implementation());
            }

        } else if (activity.getEvent() instanceof EndEvent) {
            EndEvent intermediateEvent = (EndEvent) activity.getEvent();

            if (ResultType.MESSAGE_LITERAL
                    .equals(intermediateEvent.getResult())) {
                return new ImplementationTypeHostInfo(intermediateEvent,
                        Xpdl2Package.eINSTANCE.getEndEvent_Implementation());
            }

        } else {
            /*
             * For tasks, xpdl2:Implementation must have been set to know that
             * it was a web service activity!
             */
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();

                if (ImplementationType.WEB_SERVICE_LITERAL.equals(taskService
                        .getImplementation())) {
                    return new ImplementationTypeHostInfo(taskService,
                            Xpdl2Package.eINSTANCE
                                    .getTaskService_Implementation());
                }

            } else if (TaskType.SEND_LITERAL.equals(taskType)) {
                TaskSend taskSend =
                        ((Task) activity.getImplementation()).getTaskSend();

                if (ImplementationType.WEB_SERVICE_LITERAL.equals(taskSend
                        .getImplementation())) {
                    return new ImplementationTypeHostInfo(taskSend,
                            Xpdl2Package.eINSTANCE.getTaskSend_Implementation());
                }

            } else if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                TaskReceive taskReceive =
                        ((Task) activity.getImplementation()).getTaskReceive();

                if (ImplementationType.WEB_SERVICE_LITERAL.equals(taskReceive
                        .getImplementation())) {
                    return new ImplementationTypeHostInfo(taskReceive,
                            Xpdl2Package.eINSTANCE
                                    .getTaskReceive_Implementation());
                }
            }
        }

        return null;
    }

    /**
     * ImplementationTypeHostInfo
     * <p>
     * Just a little data class containing information about the specific
     * element that should contain the xpdl2:Implementation and
     * xpdExt:ImplementationType attributes within a given activity type.
     * 
     * @author aallway
     * @since 3.3 (16 Dec 2009)
     */
    public static class ImplementationTypeHostInfo {
        public EObject hostObject;

        public EAttribute xpdl2ImplementationFeature;

        public EAttribute xpdExtImplementationTypeFeature =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType();

        /**
         * @param hostObject
         * @param xpdl2ImplemantationFeature
         */
        public ImplementationTypeHostInfo(EObject hostObject,
                EAttribute xpdl2ImplemantationFeature) {
            super();
            this.hostObject = hostObject;
            this.xpdl2ImplementationFeature = xpdl2ImplemantationFeature;
        }

    }

}
