/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit command contributor for maintaining a send task / intermediate/end
 * throw message event that has been configured as a reply to an incoming
 * request activity.
 * <p>
 * When an incoming request activity changes, update the copy of the web service
 * details in any activities setup to be replies to it.
 * 
 * @author aallway
 * @since 3.2
 */
public class MaintainWebServiceReplyActivity extends
        AbstractProcessPreCommitContributor {

    @Override
    protected boolean allowContributionRecursion(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand
     * (org.eclipse.emf.transaction.ResourceSetChangeEvent,
     * java.util.Collection)
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand cmd = new CompoundCommand();
        Set<Activity> alreadyDone = new HashSet<Activity>();
        Set<Activity> alreadyDoneReplyImmediate = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            // outputNotfication(notification);

            Activity actAncestor = getActivityAncestor(notification);
            if (actAncestor != null && actAncestor.getProcess() != null) {

                /*
                 * Check if this is an Unset of the ReplyImmediately flag on
                 * start event and remove any reply immediate mappings if it is.
                 */
                if (!alreadyDoneReplyImmediate.contains(actAncestor)
                        && XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyImmediately()
                                .equals(notification.getFeature())
                        && !ReplyActivityUtil
                                .isStartMessageWithReplyImmediate(actAncestor)) {
                    alreadyDoneReplyImmediate.add(actAncestor);

                }

                if (Notification.SET == notification.getEventType()) {
                    if (!alreadyDone.contains(actAncestor)) {
                        //
                        // Check if this is a set on something underneath an
                        // incoming request activity.
                        //
                        if (ReplyActivityUtil
                                .isIncomingRequestActivity(actAncestor)) {

                            alreadyDone.add(actAncestor);

                            // Get list of activities configured to be a reply
                            // to this activity.
                            List<Activity> replyActivities =
                                    ReplyActivityUtil
                                            .getReplyActivities(actAncestor);
                            for (Activity replyAct : replyActivities) {
                                // System.out
                                // .println(this.getClass()
                                // .getSimpleName()
                                //                                                + ": UPDATING REPLY ACTIVITY: " + replyAct.getName()); //$NON-NLS-1$

                                updateReplyActivity(actAncestor,
                                        replyAct,
                                        event.getEditingDomain(),
                                        cmd,
                                        true);
                            }

                        }
                        //
                        // Check if this is a reply activity having it's
                        // ReplyToActivity id set.
                        // ReplyActivityUtil.
                        // getSetRequestActivityForReplyActivityCommand() will
                        // set the xpdExt:ReplyToActivityId
                        //
                        else if (notification.getFeature() == XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyToActivityId()) {
                            // System.out.println(this.getClass().getSimpleName()
                            //                                    + ": SET REPLY TO ACTIVITY!!!"); //$NON-NLS-1$

                            // Check that the request activity is accessible and
                            // if so, update the send task to match it.
                            String newStringValue =
                                    notification.getNewStringValue();
                            if (newStringValue != null
                                    && newStringValue.length() > 0) {
                                Activity requestAct =
                                        ReplyActivityUtil
                                                .getRequestActivityForReplyActivity(actAncestor);
                                if (requestAct != null) {
                                    alreadyDone.add(actAncestor);

                                    updateReplyActivity(requestAct,
                                            actAncestor,
                                            event.getEditingDomain(),
                                            cmd,
                                            false);
                                }
                            }
                        }
                        //
                        // Check for end event being set to message type
                        //
                        else if (notification.getNotifier() instanceof EndEvent
                                && notification.getNewValue() instanceof TriggerResultMessage) {
                            if (ReplyActivityUtil.isReplyActivity(actAncestor)) {
                                // System.out
                                // .println(this.getClass()
                                // .getSimpleName()
                                //                                                + ": CHANGE END EVENT TO MESSAGE TYPE!!!"); //$NON-NLS-1$

                                Activity requestAct =
                                        ReplyActivityUtil
                                                .getRequestActivityForReplyActivity(actAncestor);

                                if (requestAct != null) {
                                    updateReplyActivity(requestAct,
                                            actAncestor,
                                            event.getEditingDomain(),
                                            cmd,
                                            true);
                                }
                            }
                        }
                    }
                }
            } else if (Notification.ADD == notification.getEventType()
                    && notification.getFeature() == Xpdl2Package.eINSTANCE
                            .getFlowContainer_Activities()) {
                if (notification.getNewValue() instanceof Activity) {
                    Activity addedAct = (Activity) notification.getNewValue();

                    if (ReplyActivityUtil.isReplyActivity(addedAct)) {
                        // System.out.println(this.getClass().getSimpleName()
                        //                                + ": ADDED REPLY ACTIVITY!!!"); //$NON-NLS-1$

                        Activity requestAct =
                                ReplyActivityUtil
                                        .getRequestActivityForReplyActivity(addedAct);

                        if (requestAct != null) {
                            updateReplyActivity(requestAct,
                                    addedAct,
                                    event.getEditingDomain(),
                                    cmd,
                                    true);
                        }
                    }
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Append the commands that will re-synchronise the given reply activity
     * with the given receive activity.
     * <p>
     * If the referenced web service operation is the same in both then we can
     * preserve the user's data mappings in the reply activity xpdl2:Message
     * element.
     * <p>
     * Otherwise if the Web service operation has changed then we will have to
     * delete the user's data mappings.
     * <p>
     * In both cases we can copy and replace the xpdl2:WebServiceOperation and
     * xpdExt:PortTypeOperation completely.
     * 
     * 
     * @param requestAct
     * @param replyAct
     * @param editingDomain
     * @param cmd
     * @param updateReplyActName
     */
    private void updateReplyActivity(Activity requestAct, Activity replyAct,
            EditingDomain editingDomain, CompoundCommand cmd,
            boolean updateReplyActName) {
        //
        /*
         * Compare the web service operations specified (or unspecified) in the
         * request and reply. XPD-3999 this flag is used to decide on ONLY for
         * resetting the ,mappings,
         */
        boolean preserveMappings = true;

        WebServiceOperation requestWSO = getWebServiceOperation(requestAct);
        PortTypeOperation requestPTO = getPortTypeOperation(requestAct);

        WebServiceOperation replyWSO = getWebServiceOperation(replyAct);
        PortTypeOperation replyPTO = getPortTypeOperation(replyAct);

        boolean isRequestGenerated =
                Xpdl2ModelUtil.isGeneratedRequestActivity(requestAct);

        //
        // If the request is concrete then compare the concrete parts.
        if (!compareWebServiceOperation(requestWSO, replyWSO)) {
            // if does not match for concrete, check if the abstract operation
            // is same
            if (!comparePortTypeOperations(requestPTO, replyPTO)) {
                // XPD-3999 set to false only if both matches fail, so that
                // mappings get reset for new port and operation
                preserveMappings = false;
            }
        }

        //
        // Create copies of the WebServiceOperation and PortTypeOperation
        // (We're doing a set so null is ok)
        WebServiceOperation copyWSO = null;
        if (requestWSO != null) {
            Command ccmd = CopyCommand.create(editingDomain, requestWSO);
            ccmd.execute();
            if (!ccmd.getResult().isEmpty()) {
                copyWSO =
                        (WebServiceOperation) ccmd.getResult().iterator()
                                .next();
            }
        }

        PortTypeOperation copyPTO = null;
        if (requestPTO != null) {
            Command ccmd = CopyCommand.create(editingDomain, requestPTO);
            ccmd.execute();
            if (!ccmd.getResult().isEmpty()) {
                copyPTO =
                        (PortTypeOperation) ccmd.getResult().iterator().next();
            }
        }

        //
        // Update the reply activity depending on it's type.
        Implementation implementation = replyAct.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;

            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                // If operation has changed and Remove mappings (effectively by
                // replacing the
                // xpdl2:Message element).
                // BUT not if the request is an auto generated one because a
                // different command contributor will sort out creating the data
                // mappings so we shouldn't touch the xpdl2:Message
                if (!preserveMappings && !isRequestGenerated) {
                    cmd.append(SetCommand.create(editingDomain,
                            taskSend,
                            Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                            Xpdl2Factory.eINSTANCE.createMessage()));

                    //                    System.out.println("   Removing existing mappings."); //$NON-NLS-1$

                }

                //
                // Copy the xpdl2 & xpdExt Implementation types.
                ImplementationType implType = getImplementation(requestAct);
                cmd.append(SetCommand.create(editingDomain,
                        taskSend,
                        Xpdl2Package.eINSTANCE.getTaskSend_Implementation(),
                        implType));

                String extImplType = getExtImplementation(requestAct);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType(),
                                extImplType));

                //
                // Set the copy web service operation / port type operation.
                cmd.append(SetCommand.create(editingDomain,
                        taskSend,
                        Xpdl2Package.eINSTANCE
                                .getTaskSend_WebServiceOperation(),
                        copyWSO));
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation(),
                                copyPTO));
            }

        } else {
            //
            // Handle updating end/intermediate throw message event that's
            // configured as a reply.reply
            //
            Event event = replyAct.getEvent();
            if (event != null) {
                EObject trigTypeNode = event.getEventTriggerTypeNode();
                if (trigTypeNode instanceof TriggerResultMessage) {

                    TriggerResultMessage triggerResultMessage =
                            (TriggerResultMessage) trigTypeNode;

                    //
                    // The only message related thing that is 'personal' to the
                    // reply activity is the data mappings (contained within the
                    // xpdl2:Message element. If the request activity's web svc
                    // operation has changed then we need to delete all mappings
                    // and start again.
                    //
                    // So if message isn't there or if request activity
                    // operation has changed, create and set a new xpdl2:Message
                    // element.
                    // BUT not if the request is an auto generated one because a
                    // different command contributor will sort out creating the
                    // data
                    // mappings so we shouldn't touch the xpdl2:Message
                    Message replyMsg = triggerResultMessage.getMessage();

                    if ((!preserveMappings && !isRequestGenerated)
                            || replyMsg == null) {
                        // Remove mappings (effectively by replacing the
                        // xpdl2:Message element.
                        replyMsg = Xpdl2Factory.eINSTANCE.createMessage();

                        cmd.append(SetCommand.create(editingDomain,
                                triggerResultMessage,
                                Xpdl2Package.eINSTANCE
                                        .getTriggerResultMessage_Message(),
                                replyMsg));
                        //                        System.out.println("   Removing existing mappings."); //$NON-NLS-1$

                    }

                    // Set implementation type
                    EAttribute implTypeAttr = null;
                    if (event instanceof IntermediateEvent) {
                        implTypeAttr =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_Implementation();
                    } else if (event instanceof EndEvent) {
                        implTypeAttr =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_Implementation();
                    }
                    if (implTypeAttr != null) {
                        ImplementationType implType =
                                getImplementation(requestAct);
                        cmd.append(SetCommand.create(editingDomain,
                                event,
                                implTypeAttr,
                                implType));

                        String extImplType = getExtImplementation(requestAct);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        event,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementationType(),
                                        extImplType));
                    }

                    //
                    // Set the copy web service operation / port type operation.
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    triggerResultMessage,
                                    Xpdl2Package.eINSTANCE
                                            .getTriggerResultMessage_WebServiceOperation(),
                                    copyWSO));
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    triggerResultMessage,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation(),
                                    copyPTO));

                    //
                    // Copy the message name etc from the request event (if it
                    // is one)
                    //
                    if (requestAct.getEvent() != null
                            && requestAct.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
                        Message requestMsg =
                                ((TriggerResultMessage) requestAct.getEvent()
                                        .getEventTriggerTypeNode())
                                        .getMessage();
                        if (requestMsg != null) {
                            cmd.append(SetCommand.create(editingDomain,
                                    replyMsg,
                                    Xpdl2Package.eINSTANCE
                                            .getNamedElement_Name(),
                                    requestMsg.getName()));
                            cmd.append(SetCommand.create(editingDomain,
                                    replyMsg,
                                    Xpdl2Package.eINSTANCE.getMessage_From(),
                                    requestMsg.getFrom()));
                            cmd.append(SetCommand.create(editingDomain,
                                    replyMsg,
                                    Xpdl2Package.eINSTANCE.getMessage_To(),
                                    requestMsg.getTo()));
                            cmd.append(SetCommand.create(editingDomain,
                                    replyMsg,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_FaultName(),
                                    requestMsg.getFaultName()));
                        }
                    }
                }
            }
        }

        if (isRequestGenerated) {
            //
            // If the request activity is auto generating WSDL then IT defines
            // the 'interface' for its operation therefore we should copy the
            // associated i/o parameters to the reply activity.
            Object obj =
                    Xpdl2ModelUtil.getOtherElement(requestAct,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters());

            if (obj != null) {
                AssociatedParameters requestAssoc = (AssociatedParameters) obj;

                Command ccmd = CopyCommand.create(editingDomain, requestAssoc);
                ccmd.execute();
                obj = ccmd.getResult().iterator().next();
            }

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    replyAct,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters(),
                    obj));

            //
            // Copy the visibility attribute from the request activity too.
            Object visibility =
                    Xpdl2ModelUtil.getOtherAttribute(requestAct,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Visibility());
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            replyAct,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Visibility(),
                            visibility));

        }

        //
        // Finally, if the current reply activity is roughly what it should be
        // (i.e. "Reply To: " or "Reply To (n):" then replace that with the
        // latest name.
        //
        if (updateReplyActName) {
            String requestName =
                    ReplyActivityUtil.getRequestActivityLabel(requestAct);

            String replyName = Xpdl2ModelUtil.getDisplayNameOrName(replyAct);

            String newLabel = null;

            if (replyName == null || replyName.length() == 0) {
                replyName =
                        ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL
                                + ": " + requestName; //$NON-NLS-1$
            }

            // XPD-4248: update reply activity if process implements any
            // process interface

            String pattern =
                    ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL
                            + ".*:";//$NON-NLS-1$

            if (replyName.matches(pattern + ".*")) { //$NON-NLS-1$
                String[] split = replyName.split(pattern, 2);
                if (split.length > 0) {
                    int lastPartLen = 0;
                    if (split.length > 1) {
                        lastPartLen = split[1].length();
                    }

                    String firstPart =
                            replyName.substring(0, replyName.length()
                                    - lastPartLen);

                    newLabel = firstPart + " " + requestName; //$NON-NLS-1$
                }
            }
            if (newLabel == null) {
                newLabel =
                        ReplyActivityUtil.getReplyToLabel(requestAct, replyAct);
            }

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            replyAct,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            newLabel));
            cmd.append(SetCommand.create(editingDomain,
                    replyAct,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    NameUtil.getInternalName(newLabel, false)));
        }
        return;
    }

    /**
     * @param requestAct
     * @return the xpdl2 Implementation type for the given request activity.
     */
    private ImplementationType getImplementation(Activity requestAct) {
        ImplementationType type = null;
        if (requestAct.getImplementation() instanceof Task) {
            Task task = (Task) requestAct.getImplementation();

            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null && taskReceive.isSetImplementation()) {
                type = taskReceive.getImplementation();
            }
        } else {
            Event event = requestAct.getEvent();
            if (event instanceof IntermediateEvent
                    && ((IntermediateEvent) event).isSetImplementation()) {
                type = ((IntermediateEvent) event).getImplementation();

            } else if (event instanceof StartEvent
                    && ((StartEvent) event).isSetImplementation()) {
                type = ((StartEvent) event).getImplementation();
            }

        }

        if (type == null) {
            type = ImplementationType.UNSPECIFIED_LITERAL;
        }

        return type;
    }

    /**
     * @param requestAct
     * @return the xpdExt Implementation type for the given request activity.
     */
    private String getExtImplementation(Activity requestAct) {
        if (requestAct.getImplementation() instanceof Task) {
            Task task = (Task) requestAct.getImplementation();

            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null) {
                return (String) Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
            }
        } else {
            Event event = requestAct.getEvent();
            if (event != null) {
                return (String) Xpdl2ModelUtil.getOtherAttribute(event,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
            }
        }

        return null;
    }

    /**
     * Compare the concrete operation (if defined) of the two web service
     * operations, Does not check for wsdl file reference.This Method is used
     * for resetting the mappings, only if the operation and Port Type are
     * changed, should not depend on file location.
     * 
     * @param requestWSO
     * @param replyWSO
     * 
     * @return true if the same.
     */
    /*
     * Mappings should only be reset for change in Port Type and operation and
     * also service in case of Concrete Binding File change with same service
     * ,operation and port should not reset mappings so wsdl location is not
     * considered in comparison
     */
    private boolean compareWebServiceOperation(WebServiceOperation requestWSO,
            WebServiceOperation replyWSO) {
        //
        // Get request info.
        String requestOpName = null;
        String requestSvcName = null;
        String requestPortName = null;

        if (requestWSO != null) {
            requestOpName = requestWSO.getOperationName();

            Service service = requestWSO.getService();
            if (service != null) {
                requestSvcName = service.getServiceName();
                requestPortName = service.getPortName();
            }
        }
        //
        // Get reply info.
        String replyOpName = null;
        String replySvcName = null;
        String replyPortName = null;

        if (replyWSO != null) {
            replyOpName = replyWSO.getOperationName();

            Service service = replyWSO.getService();
            if (service != null) {
                replySvcName = service.getServiceName();
                replyPortName = service.getPortName();
            }
        }

        // Compare them
        /*
         * return false if both request and reply have abstract binding, these
         * values will be null for both, so to be able to proceed with abstract
         * comparison
         */
        if (!compareValuesNotNullAndEqual(requestOpName, replyOpName)
                || !compareValuesNotNullAndEqual(requestSvcName, replySvcName)
                || !compareValuesNotNullAndEqual(requestPortName, replyPortName)) {
            return false;
        }

        return true;
    }

    /**
     * Compare the abstract operation parts (if defined) of the two web service
     * operations.Does not check for wsdl file reference.This Method is used for
     * resetting the mappings, only if the operation and Port Type are changed,
     * should not depend on file location.
     * 
     * @param requestWSO
     * @param replyWSO
     * 
     * @return true if the same.
     */
    private boolean comparePortTypeOperations(PortTypeOperation requestPTO,
            PortTypeOperation replyPTO) {
        //
        // Get request info.
        String requestOpName = null;
        String requestPortTypeName = null;

        if (requestPTO != null) {
            requestOpName = requestPTO.getOperationName();

            requestPortTypeName = requestPTO.getPortTypeName();

        }

        // Get reply info.
        String replyOpName = null;
        String replyPortTypeName = null;

        if (replyPTO != null) {
            replyOpName = replyPTO.getOperationName();
            replyPortTypeName = replyPTO.getPortTypeName();
        }

        //
        // Compare them
        if (!compareValuesNotNullAndEqual(requestOpName, replyOpName)
                || !compareValuesNotNullAndEqual(requestPortTypeName,
                        replyPortTypeName)) {
            return false;
        }
        return true;
    }

    /**
     * Returns false if both values are null or not Equal.
     * 
     * @param value1
     * @param value2
     * @return
     */
    private boolean compareValuesNotNullAndEqual(String value1, String value2) {
        if (value1 != null && value2 != null) {
            return value1.equals(value2);
        }
        return false;
    }

    /**
     * @param reqOrReplyAct
     * 
     * @return The WebServiceOperation element (if defined) for the given
     *         activity.
     */
    private WebServiceOperation getWebServiceOperation(Activity reqOrReplyAct) {
        Implementation implementation = reqOrReplyAct.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;

            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                return taskSend.getWebServiceOperation();

            }

            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null) {
                return taskReceive.getWebServiceOperation();
            }

        } else {
            Event event = reqOrReplyAct.getEvent();
            if (event != null) {
                EObject trigTypeNode = event.getEventTriggerTypeNode();
                if (trigTypeNode instanceof TriggerResultMessage) {
                    return ((TriggerResultMessage) trigTypeNode)
                            .getWebServiceOperation();
                }
            }
        }

        return null;
    }

    /**
     * @param reqOrReplyAct
     * 
     * @return The PortTypeOperation element (if defined) for the given
     *         activity.
     */
    private PortTypeOperation getPortTypeOperation(Activity reqOrReplyAct) {
        if (reqOrReplyAct.getImplementation() instanceof Task) {
            Task task = (Task) reqOrReplyAct.getImplementation();

            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                return (PortTypeOperation) Xpdl2ModelUtil
                        .getOtherElement(taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());

            }

            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null) {
                return (PortTypeOperation) Xpdl2ModelUtil
                        .getOtherElement(taskReceive,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
            }

        } else {
            Event event = reqOrReplyAct.getEvent();
            if (event != null) {
                EObject trigTypeNode = event.getEventTriggerTypeNode();
                if (trigTypeNode instanceof TriggerResultMessage) {
                    return (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement((TriggerResultMessage) trigTypeNode,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation());
                }
            }
        }

        return null;
    }

    /**
     * @param notification
     * @return The activity ancestor of the notifier eobject in the given
     *         notification.
     */
    private Activity getActivityAncestor(ENotificationImpl notification) {
        if (notification.getNotifier() instanceof EObject) {
            EObject eo = (EObject) notification.getNotifier();

            while (eo != null) {
                if (eo instanceof Activity) {
                    return (Activity) eo;
                }
                eo = eo.eContainer();
            }
        }

        return null;
    }

}
