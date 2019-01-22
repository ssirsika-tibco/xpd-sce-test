/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processinterface.properties.BaseMethodSection;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Pre-commit listener which creates {@link PortTypeOperation}s and
 * {@link WebServiceOperation}s for Request activities.
 * 
 * @author rsomayaj
 * 
 */
public class PortTypeGenerationCmdContributor extends
        AbstractProcessPreCommitContributor {

    private static boolean debug = false;

    public PortTypeGenerationCmdContributor() {
    }

    /**
     * @see com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor#allowContributionRecursion(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     */
    @Override
    protected boolean allowContributionRecursion(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {

        boolean needsRecursion = false;

        for (Notification notification : notifications) {
            if (Xpdl2Package.eINSTANCE.getNamedElement_Name()
                    .equals(notification.getFeature())) {
                needsRecursion = true;
                break;
            } else if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParameter_FormalParam()
                    .equals(notification.getFeature())) {
                needsRecursion = true;
                break;
            } else {
                /*
                 * Check for update of a reply activity as a result of request
                 * activity chaging - we may need to rebuild mappings etc when
                 * request activity associated param changes which then gets
                 * copied to the reply activity associated params.
                 * 
                 * So after that we will need to regenerate the mappings for the
                 * reply activity.
                 */
                if (notification.getNotifier() instanceof Activity) {
                    Activity activity = (Activity) notification.getNotifier();

                    if (ReplyActivityUtil.isReplyActivity(activity)) {
                        if (notification.getEventType() == Notification.ADD) {
                            if (notification.getNewValue() instanceof AssociatedParameters) {
                                needsRecursion = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return needsRecursion;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        CompoundCommand cmd = new CompoundCommand();
        TransactionalEditingDomain editingDomain = event.getEditingDomain();

        Package pkg = isCreateNewPackageNotification(notifications);
        if (pkg != null) {
            /*
             * Treat initial package creation as a special case. i.e. Add
             * initial port type stuff as required.
             * 
             * This allows us to configure a package just created from a
             * template that contains process api message activities.
             */
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                for (ProcessInterface processInterface : processInterfaces
                        .getProcessInterface()) {
                    appendInitialActivitySetupCommands(cmd,
                            editingDomain,
                            processInterface);
                }
            }

            for (Process process : pkg.getProcesses()) {
                appendInitialActivitySetupCommands(cmd, editingDomain, process);
            }

        } else {
            appendMaintainDefaultApiParticipantCommands(cmd,
                    editingDomain,
                    notifications);

            for (ENotificationImpl notification : notifications) {
                int eventType = notification.getEventType();

                if (debug) {
                    outputNotfication(notification);
                }

                if (isAddDestinationNotification(notification)) {
                    /* Handle destination environment added */
                    appendAddDestinationCommands(cmd,
                            editingDomain,
                            notification);

                } else if (isRemoveDestinationNotification(notification)) {
                    /* Handle destination environment removed. */
                    appendRemoveDestinationCommands(cmd,
                            editingDomain,
                            notification);

                } else if (Notification.ADD == eventType) {
                    /* Handle single element added. */
                    appendAddEventTypeListeners(cmd,
                            editingDomain,
                            notification);

                } else if (Notification.SET == eventType) {
                    /* Handle set of element/attribute */
                    appendSetEventTypeListenerCommands(cmd,
                            editingDomain,
                            notification);

                } else if (Notification.REMOVE == eventType) {
                    // Handle remove single element. */
                    appendRemoveNotificationListenerCommands(cmd,
                            editingDomain,
                            notification);

                } else if (Notification.ADD_MANY == eventType) {
                    /* Handle many elements added */
                    appendAddManyEventTypeListeners(cmd,
                            editingDomain,
                            notification);

                } else if (Notification.REMOVE_MANY == eventType) {
                    /* Handle remove many elements */
                    appendRemoveManyNotificationListenerCommands(cmd,
                            editingDomain,
                            notification);
                }
            }
        }

        if (cmd.canExecute()) {
            return cmd;
        }

        return null;
    }

    /**
     * Append any commands necessary to maintain definition the default api
     * activity endpoint participant for the process.
     * <p>
     * This participant is nominally created by AddPortTypeCommand when it
     * detects that a process api activity requires a default endpoint
     * participant to be set.
     * <p>
     * This participant is created with a default Label, Name, Shared Resource
     * Name and Shared Resource URI. These are all based on the package and
     * process name.
     * <p>
     * This method changes these values when package / process name changes
     * PROVIDED that they have not been previously modified by the user (i.e.
     * they have the correct default value before the process name change).
     * 
     * @param cmd
     * @param editingDomain
     * @param notifications
     */
    private void appendMaintainDefaultApiParticipantCommands(
            CompoundCommand cmd, TransactionalEditingDomain editingDomain,
            Collection<ENotificationImpl> notifications) {
        Object nameChangedObject = null;

        String oldName = null;
        String newName = null;

        String oldLabel = null;
        String newLabel = null;

        /*
         * Name / label is can be unset (or set to null) then set to new value
         * in separate commands so we have to keep track of both ends.
         * 
         * So we will grab the old process and package name and label
         * (displayName) values and then wok out later if we need to do
         * anything.
         */
        for (ENotificationImpl notification : notifications) {
            Object notifier = notification.getNotifier();

            if (notifier instanceof Package || notifier instanceof Process) {
                NamedElement procOrPackage = (NamedElement) notifier;

                if (Xpdl2Package.eINSTANCE.getNamedElement_Name()
                        .equals(notification.getFeature())) {
                    /*
                     * Package or process name change.
                     */
                    if (nameChangedObject != null
                            && notifier != nameChangedObject) {
                        /*
                         * Don't expect this to be likely, and not worth
                         * jeopardising things just for the sake of default api
                         * partic name.
                         */
                        System.out
                                .println(this.getClass().getName()
                                        + "Not maintining process api partic - Cannot handle 2 processes or packages changing name at same time."); //$NON-NLS-1$
                        return;
                    }
                    nameChangedObject = notifier;

                    if ((notification.getEventType() == Notification.SET || notification
                            .getEventType() == Notification.UNSET)) {
                        /* Save first non-null 'previous value' we come across. */
                        if (notification.getOldStringValue() != null) {
                            oldName = notification.getOldStringValue();
                        }

                        /* Save the final non-null 'new value' we come across. */
                        if (notification.getNewStringValue() != null) {
                            newName = notification.getNewStringValue();
                        }

                    }
                } else if (XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_DisplayName()
                        .equals(notification.getFeature())) {
                    /*
                     * Package or process label change.
                     */
                    if (nameChangedObject != null
                            && notifier != nameChangedObject) {
                        /*
                         * Don't expect this to be likely, and not worth
                         * jeopardising things just for the sake of default api
                         * partic name.
                         */
                        System.out
                                .println(this.getClass().getName()
                                        + "Not maintining process api partic - Cannot handle 2 processes or packages changing name at same time."); //$NON-NLS-1$
                        return;
                    }
                    nameChangedObject = notifier;

                    if ((notification.getEventType() == Notification.SET || notification
                            .getEventType() == Notification.UNSET)) {
                        /* Save first non-null 'previous value' we come across. */
                        if (notification.getOldStringValue() != null) {
                            oldLabel = notification.getOldStringValue();
                        }

                        /* Save the final non-null 'new value' we come across. */
                        if (notification.getNewStringValue() != null) {
                            newLabel = notification.getNewStringValue();
                        }
                    }

                } else if (Xpdl2Package.eINSTANCE
                        .getParticipantsContainer_Participants()
                        .equals(notification.getFeature())) {
                    /*
                     * Check for removal of a an api endpoint participant and
                     * remove the
                     * xpdl2:WorkflowProcess/xpdExt:ApiEndPointParticipant
                     * reference to it.
                     */
                    if (notification.getEventType() == Notification.REMOVE) {
                        if (notification.getOldValue() instanceof Participant) {
                            Participant partic =
                                    (Participant) notification.getOldValue();

                            Package pkg =
                                    Xpdl2ModelUtil
                                            .getPackage((EObject) notifier);
                            if (pkg != null) {
                                for (Process process : pkg.getProcesses()) {
                                    String apiParticId =
                                            (String) Xpdl2ModelUtil
                                                    .getOtherAttribute(process,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_ApiEndPointParticipant());

                                    if (partic.getId().equals(apiParticId)) {
                                        cmd.append(Xpdl2ModelUtil
                                                .getSetOtherAttributeCommand(editingDomain,
                                                        process,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_ApiEndPointParticipant(),
                                                        null));
                                    }
                                }
                            }
                        }
                    }
                } else if (Xpdl2Package.eINSTANCE.getPackage_Processes()
                        .equals(notification.getFeature())) {

                    if (nameChangedObject != null
                            && notifier != nameChangedObject) {
                        /*
                         * Don't expect this to be likely, and not worth
                         * jeopardising things just for the sake of default api
                         * partic name.
                         */
                        System.out
                                .println(this.getClass().getName()
                                        + "Not maintining process api partic - Cannot handle 2 processes or packages changing name at same time."); //$NON-NLS-1$
                        return;
                    }

                    if (Notification.ADD == notification.getEventType()
                            && notification.getNewValue() instanceof Process) {
                        Process process = (Process) notification.getNewValue();

                        Participant apiEndPointPartic = null;
                        boolean isProcessCopiedIntoSamePackage = false;

                        /*
                         * find if the process is copied in the same package.
                         * (if other processes in the package has same
                         * ApiEndpointParticipant, it means the process is
                         * copied in the same package)
                         */
                        Package pckg = process.getPackage();
                        Participant originalProcessApiParticipant =
                                Xpdl2ModelUtil
                                        .getProcessApiActivityParticipant(process);
                        if (null != originalProcessApiParticipant) {
                            for (Process proc : pckg.getProcesses()) {
                                Participant processApiParticipant =
                                        Xpdl2ModelUtil
                                                .getProcessApiActivityParticipant(proc);
                                /* not comparing with the same process */
                                if (!proc.getId()
                                        .equalsIgnoreCase(process.getId())) {
                                    if (null != processApiParticipant
                                            && originalProcessApiParticipant
                                                    .equals(processApiParticipant)) {
                                        isProcessCopiedIntoSamePackage = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isProcessCopiedIntoSamePackage) {
                            /* create and assign a new ApiEndpointParticipant */
                            apiEndPointPartic =
                                    AddPortTypeCommand
                                            .assignNewApiEndpointParticpant(editingDomain,
                                                    process,
                                                    cmd);
                            for (Activity activity : process.getActivities()) {
                                /*
                                 * check if the process has incoming request
                                 * activity
                                 */
                                if (ReplyActivityUtil
                                        .isIncomingRequestActivity(activity)) {

                                    WebServiceOperation webServiceOperation =
                                            null;
                                    Event event = activity.getEvent();
                                    if (null != event) {
                                        TriggerResultMessage triggerResultMessage =
                                                EventObjectUtil
                                                        .getTriggerResultMessage(activity);

                                        if (triggerResultMessage != null) {
                                            webServiceOperation =
                                                    triggerResultMessage
                                                            .getWebServiceOperation();
                                        }
                                    } else if (activity.getImplementation() instanceof Task) {
                                        Task task =
                                                (Task) activity
                                                        .getImplementation();
                                        TaskReceive taskReceive =
                                                task.getTaskReceive();
                                        webServiceOperation =
                                                taskReceive
                                                        .getWebServiceOperation();
                                    }

                                    if (null != webServiceOperation) {
                                        /*
                                         * Set participant to activity
                                         * performer.
                                         */
                                        List<EObject> perfs =
                                                new ArrayList<EObject>();
                                        perfs.add(apiEndPointPartic);
                                        Command perfCommand =
                                                TaskObjectUtil
                                                        .getSetPerformersCommand(editingDomain,
                                                                activity,
                                                                perfs.toArray(new EObject[0]));
                                        if (perfCommand != null) {
                                            cmd.append(perfCommand);
                                        }

                                        /*
                                         * Set Participant in endpoint alias
                                         */
                                        cmd.append(Xpdl2ModelUtil
                                                .getSetOtherAttributeCommand(editingDomain,
                                                        webServiceOperation,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Alias(),
                                                        apiEndPointPartic
                                                                .getId()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
         * Check for changes.
         */
        if (nameChangedObject instanceof Process) {
            /*
             * Look for api participant for specific process and update it.
             */
            Process process = (Process) nameChangedObject;
            if (process.getPackage() != null) {
                /* Check there is an api end point participant. */
                Participant apiEndPointPartic =
                        Xpdl2ModelUtil
                                .getProcessApiActivityParticipant(process);
                if (apiEndPointPartic != null) {

                    /*
                     * Load up all the configurable data we need... (Defaulting
                     * to existing value in case that it wasn't involved in a
                     * notification)
                     */
                    String oldProcessName =
                            oldName != null ? oldName : process.getName();
                    String newProcessName =
                            newName != null ? newName : oldProcessName;
                    String oldProcessLabel =
                            oldLabel != null ? oldLabel : Xpdl2ModelUtil
                                    .getDisplayNameOrName(process);
                    String newProcessLabel =
                            newLabel != null ? newLabel : oldProcessLabel;

                    /* Package name is not changing so just use existing values. */
                    String oldPackageName = process.getPackage().getName();
                    String newPackageName = oldPackageName;
                    String oldPackageLabel =
                            Xpdl2ModelUtil.getDisplayNameOrName(process
                                    .getPackage());
                    String newPackageLabel = oldPackageLabel;

                    /* Then update the api participant if necessary. */
                    appendUpdateDefaultApiParticipantCommand(process,
                            editingDomain,
                            cmd,
                            apiEndPointPartic,
                            oldProcessName,
                            newProcessName,
                            oldProcessLabel,
                            newProcessLabel,
                            oldPackageName,
                            newPackageName,
                            oldPackageLabel,
                            newPackageLabel);
                }
            }
        } else if (nameChangedObject instanceof Package) {
            /*
             * Look for api participant for any process and update it.
             */
            Package pkg = (Package) nameChangedObject;

            for (Process process : pkg.getProcesses()) {
                /* Check there is an api end point participant. */
                Participant apiEndPointPartic =
                        Xpdl2ModelUtil
                                .getProcessApiActivityParticipant(process);
                if (apiEndPointPartic != null) {
                    /*
                     * Load up all the configurable data we need... (Defaulting
                     * to existing value in case that it wasn't involved in a
                     * notification)
                     */
                    String oldPackageName =
                            oldName != null ? oldName : pkg.getName();
                    String newPackageName =
                            newName != null ? newName : oldPackageName;
                    String oldPackageLabel =
                            oldLabel != null ? oldLabel : Xpdl2ModelUtil
                                    .getDisplayNameOrName(pkg);
                    String newPackageLabel =
                            newLabel != null ? newLabel : oldPackageLabel;

                    /* Process name is not changing so just use existing values. */
                    String oldProcessName = process.getName();
                    String newProcessName = oldProcessName;
                    String oldProcessLabel =
                            Xpdl2ModelUtil.getDisplayNameOrName(process);
                    String newProcessLabel = oldProcessLabel;

                    /* Then update the api participant if necessary. */
                    appendUpdateDefaultApiParticipantCommand(process,
                            editingDomain,
                            cmd,
                            apiEndPointPartic,
                            oldProcessName,
                            newProcessName,
                            oldProcessLabel,
                            newProcessLabel,
                            oldPackageName,
                            newPackageName,
                            oldPackageLabel,
                            newPackageLabel);

                }
            }

        }

        return;
    }

    /**
     * Given all the old name/label new name/label info for process and/or
     * package, append any necessary commands to update the given default
     * process api participant with the new default label, name and shared
     * resource extended data if it is currently in default state.
     * 
     * @param editingDomain
     * @param cmd
     * @param apiEndPointPartic
     * @param oldProcessName
     * @param newProcessName
     * @param oldProcessLabel
     * @param newProcessLabel
     * @param oldPackageName
     * @param newPackageName
     * @param oldPackageLabel
     * @param newPackageLabel
     */
    private void appendUpdateDefaultApiParticipantCommand(Process process,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd,
            Participant apiEndPointPartic, String oldProcessName,
            String newProcessName, String oldProcessLabel,
            String newProcessLabel, String oldPackageName,
            String newPackageName, String oldPackageLabel,
            String newPackageLabel) {

        /* Ensure no null for safety */
        oldProcessName = (oldProcessName != null) ? oldProcessName : ""; //$NON-NLS-1$
        newProcessName = (newProcessName != null) ? newProcessName : ""; //$NON-NLS-1$
        oldProcessLabel = (oldProcessLabel != null) ? oldProcessLabel : ""; //$NON-NLS-1$
        newProcessLabel = (newProcessLabel != null) ? newProcessLabel : ""; //$NON-NLS-1$

        oldPackageName = (oldPackageName != null) ? oldPackageName : ""; //$NON-NLS-1$
        newPackageName = (newPackageName != null) ? newPackageName : ""; //$NON-NLS-1$
        oldPackageLabel = (oldPackageLabel != null) ? oldPackageLabel : ""; //$NON-NLS-1$
        newPackageLabel = (newPackageLabel != null) ? newPackageLabel : ""; //$NON-NLS-1$

        /* Get the shared resource extension data. */
        ParticipantSharedResource participantSharedRes =
                (ParticipantSharedResource) Xpdl2ModelUtil
                        .getOtherElement(apiEndPointPartic,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantSharedResource());

        if (!newProcessName.equals(oldProcessName)
                || !newPackageName.equals(oldPackageName)) {
            /*
             * Token name has changed, so if current name is same as default
             * would be for old token name then can reset it to correct default
             * for new token name.
             */

            /*
             * XPD-685: MR42008 should have prevented setting of SHARED RESOURCE
             * NAME when the process or package name changes, however the
             * setting of Participant name was commented out instead!
             */

            String oldParticDefaultName_ver1 =
                    // legacy format
                    AddPortTypeCommand
                            .getDefaultParticipantNameForProcessApi(oldProcessName,
                                    oldPackageName);

            String oldParticDefaultName_ver2 =
                    // current format
                    AddPortTypeCommand
                            .getDefaultParticipantNameForProcessApi(oldProcessName);

            String participantName = apiEndPointPartic.getName();
            boolean isParticipantNameADefault =
                    oldParticDefaultName_ver1.equals(participantName)
                            || oldParticDefaultName_ver2
                                    .equals(participantName);

            if (isParticipantNameADefault) {

                /*
                 * XPD-5911: if a process name has leading digit(s) then prefix
                 * with underscore
                 */
                if (newProcessName != null
                        && Character.isDigit(newProcessName.charAt(0))) {
                    newProcessName = "_" + newProcessName; //$NON-NLS-1$
                }

                String newDefaultParticName =
                        AddPortTypeCommand
                                .getDefaultParticipantNameForProcessApi(newProcessName);

                cmd.append(SetCommand.create(editingDomain,
                        apiEndPointPartic,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        newDefaultParticName));
            }

            if (participantSharedRes != null) {
                /*
                 * And the same again for the shared resource name
                 */
                /*
                 * XPD-685: MR42008 should have prevented setting of SHARED
                 * RESOURCE NAME - For API activity participants the shared
                 * resource name is not used.
                 * 
                 * String oldSharedResourceDefaultName = AddPortTypeCommand
                 * .getDefaultSharedResourceNameForProcessApi(oldProcessName,
                 * oldPackageName);
                 * 
                 * if (oldSharedResourceDefaultName.equals(sharedResource
                 * .getName())) { String newDefaultSharedResourceName =
                 * AddPortTypeCommand
                 * .getDefaultSharedResourceNameForProcessApi(newProcessName,
                 * newPackageName);
                 * 
                 * cmd.append(SetCommand.create(editingDomain, sharedResource,
                 * XpdExtensionPackage.eINSTANCE .getSharedResource_Name(),
                 * newDefaultSharedResourceName)); }
                 */

                /*
                 * And the same again for shared resource URI.
                 */
                String oldSharedResourceDefaultUri =
                        AddPortTypeCommand
                                .getDefaultSharedResourceURIForProcessApi(process,
                                        oldProcessName,
                                        oldPackageName);

                // Shared resource URI is obtained from the first SOAP over HTTP
                // binding in the Webservice resource of the participant.
                String sharedResourceUri = null;
                EObject sharedResource =
                        participantSharedRes.getSharedResource();
                if (sharedResource instanceof WsResource) {
                    WsResource wsResource = (WsResource) sharedResource;
                    WsInbound inbound = wsResource.getInbound();
                    if (inbound != null
                            && inbound.getSoapHttpBinding().size() > 0) {
                        WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                inbound.getSoapHttpBinding().get(0);
                        sharedResourceUri =
                                wsSoapHttpInboundBinding.getEndpointUrlPath();

                        if (oldSharedResourceDefaultUri
                                .equals(sharedResourceUri)) {
                            String newDefaultSharedResourceUri =
                                    AddPortTypeCommand
                                            .getDefaultSharedResourceURIForProcessApi(process,
                                                    newProcessName,
                                                    newPackageName);

                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            wsSoapHttpInboundBinding,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getWsSoapHttpInboundBinding_EndpointUrlPath(),
                                            newDefaultSharedResourceUri));
                        }
                    }

                }
            }
        }

        if (!newProcessLabel.equals(oldProcessLabel)
                || !newPackageLabel.equals(oldPackageLabel)) {
            /*
             * Label (display name) has changed, so if current label is same as
             * default would be for old label then can reset it to correct
             * default for new label.
             */
            String oldParticDefaultLabel =
                    AddPortTypeCommand
                            .getDefaultParticipantLabelForProcessApi(oldProcessLabel,
                                    oldPackageLabel);

            if (oldParticDefaultLabel.equals(Xpdl2ModelUtil
                    .getDisplayNameOrName(apiEndPointPartic))) {
                String newDefaultParticLabel =
                        AddPortTypeCommand
                                .getDefaultParticipantLabelForProcessApi(newProcessLabel,
                                        newPackageLabel);
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                apiEndPointPartic,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                newDefaultParticLabel));
            }
        }

        return;
    }

    /**
     * @param notification
     * 
     * @return true if this notification signifies the addition of a process
     *         destination environment.
     */
    private boolean isAddDestinationNotification(ENotificationImpl notification) {
        if (Notification.ADD == notification.getEventType()) {
            if (Xpdl2Package.eINSTANCE
                    .getExtendedAttributesContainer_ExtendedAttributes()
                    .equals(notification.getFeature())) {
                if (notification.getNewValue() instanceof ExtendedAttribute) {
                    String extAttrName =
                            ((ExtendedAttribute) notification.getNewValue())
                                    .getName();

                    if (DestinationUtil.DESTINATION_ATTRIBUTE
                            .equals(extAttrName)) {
                        if (notification.getNotifier() instanceof Process) {
                            return true;
                        } else if (notification.getNotifier() instanceof ProcessInterface) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * When destination has been added, check whether there are now at least one
     * 'generate webservice for api activity enabled' destinations - if so, set
     * all the api activities that do not have custom wsdl operations selcted to
     * "wsdl generated for" and add the port type and mappings etc.
     * 
     * @param cmd
     * @param editingDomain
     * @param notification
     */
    private void appendAddDestinationCommands(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain,
            ENotificationImpl notification) {

        Object notifier = notification.getNotifier();

        appendInitialActivitySetupCommands(cmd, editingDomain, notifier);

        return;
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param processOrInterface
     */
    protected void appendInitialActivitySetupCommands(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain, Object processOrInterface) {
        if (processOrInterface instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) processOrInterface;

            /*
             * If this processInterface is having WSDL port type generated for
             * it the add port type to all message events.
             */
            if (ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations(processInterface)) {
                for (InterfaceMethod method : processInterface
                        .getStartMethods()) {
                    if (TriggerType.MESSAGE_LITERAL.equals(method.getTrigger())) {
                        cmd.append(BaseMethodSection
                                .getUpdateMethodWsdlInfoCommand(editingDomain,
                                        processInterface,
                                        method));
                    }
                }

                for (InterfaceMethod method : processInterface
                        .getIntermediateMethods()) {
                    if (TriggerType.MESSAGE_LITERAL.equals(method.getTrigger())) {
                        cmd.append(BaseMethodSection
                                .getUpdateMethodWsdlInfoCommand(editingDomain,
                                        processInterface,
                                        method));
                    }
                }

            }

            /* Add the data mappings */
            cmd.append(UpdateMappingsCommandFactory
                    .getUpdateMappingsCommand(editingDomain, processInterface));

        } else if (processOrInterface instanceof Process) {
            Process process = (Process) processOrInterface;

            /*
             * If this process is having WSDL port type generated for it the add
             * port type to all api activity which do not have custom wsdl
             * operation assigned.
             */
            if (ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(process)) {
                /*
                 * Make sure all Process API activities are setup to be
                 * generated and have their port types and mappings setup.
                 */
                Collection<Activity> allActivities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : allActivities) {
                    if (isSuitableForPortTypeGeneration(activity)) {
                        /* Add (or re-add) the port type to activity */
                        if (isSuitableForPortTypeGeneration(activity)) {
                            if (ProcessInterfaceUtil
                                    .isEventImplemented(activity)) {
                                cmd.append(new AddPortTypeForImplementedActivityCommand(
                                        editingDomain, activity));
                            } else {
                                cmd.append(new AddPortTypeCommand(
                                        editingDomain, process, activity));
                            }

                        }
                    }
                }

                /* Add the data mappings */
                cmd.append(UpdateMappingsCommandFactory
                        .getUpdateMappingsCommand(editingDomain, process));
            }
        }
    }

    /**
     * @param notification
     * @return true if this notification signifies the removal of a process
     *         destination environment.
     */
    private boolean isRemoveDestinationNotification(
            ENotificationImpl notification) {
        if (Notification.REMOVE == notification.getEventType()) {
            if (Xpdl2Package.eINSTANCE
                    .getExtendedAttributesContainer_ExtendedAttributes()
                    .equals(notification.getFeature())) {
                if (notification.getOldValue() instanceof ExtendedAttribute) {
                    String extAttrName =
                            ((ExtendedAttribute) notification.getOldValue())
                                    .getName();

                    if (DestinationUtil.DESTINATION_ATTRIBUTE
                            .equals(extAttrName)) {
                        if (notification.getNotifier() instanceof Process) {
                            return true;
                        } else if (notification.getNotifier() instanceof ProcessInterface) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * When destination has been removed, check whether there are no longer any
     * 'generate webservice for api activity enabled' destinations - if so, set
     * all the api activities set to be "wsdl generated for" in process back to
     * ungenerated.
     * 
     * @param cmd
     * @param editingDomain
     * @param notification
     */
    private void appendRemoveDestinationCommands(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain,
            ENotificationImpl notification) {
        if (notification.getNotifier() instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) notification.getNotifier();

            /*
             * If this process interface has no destinations left that want WSDL
             * generation then remove the port type from all message events
             */
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations(processInterface)) {
                for (InterfaceMethod method : processInterface
                        .getStartMethods()) {
                    if (TriggerType.MESSAGE_LITERAL.equals(method.getTrigger())) {
                        CompoundCommand clearCmd =
                                BaseMethodSection
                                        .getClearInterfaceMethodWsdlCommand(editingDomain,
                                                method);
                        if (clearCmd != null) {
                            cmd.append(clearCmd);
                        }
                    }
                }

                for (InterfaceMethod method : processInterface
                        .getIntermediateMethods()) {
                    if (TriggerType.MESSAGE_LITERAL.equals(method.getTrigger())) {
                        CompoundCommand clearCmd =
                                BaseMethodSection
                                        .getClearInterfaceMethodWsdlCommand(editingDomain,
                                                method);
                        if (clearCmd != null) {
                            cmd.append(clearCmd);
                        }
                    }
                }

            }

        } else if (notification.getNotifier() instanceof Process) {
            Process process = (Process) notification.getNotifier();

            /*
             * If this process has no destinations left that want WSDL
             * generation then remove the port type from all api activity which
             * were being generated.
             */
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(process)) {
                /*
                 * Make sure all Process API activities are setup to be
                 * generated and have their port types and mappings setup.
                 */
                Collection<Activity> allActivities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : allActivities) {
                    if (isSuitableForPortTypeGeneration(activity)) {
                        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                            Command clearCmd =
                                    getClearGeneratedWebServiceCommand(editingDomain,
                                            activity);
                            if (clearCmd != null) {
                                cmd.append(clearCmd);
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param editingDomain
     * @param activity
     *            Process API incoming request / reply activity
     * 
     * @return Command to remove the web service (and xpdExt:Generated flag)
     *         from the given activity.
     */
    private Command getClearGeneratedWebServiceCommand(
            TransactionalEditingDomain editingDomain, Activity activity) {
        CompoundCommand cmd = null;

        if (activity.getEvent() != null) {
            if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
                TriggerResultMessage trm =
                        (TriggerResultMessage) activity.getEvent()
                                .getEventTriggerTypeNode();

                cmd = new CompoundCommand();

                /* Remove mappings (overwrite message with new empty one)... */
                cmd.append(SetCommand.create(editingDomain,
                        trm,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message(),
                        Xpdl2Factory.eINSTANCE.createMessage()));

                /*
                 * We are only responsible for the data mappings in api reply
                 * activities - everything is updated by
                 * MaintainWebServiceReplyActivity
                 */
                if (!ReplyActivityUtil.isReplyActivity(activity)) {

                    /* xpdExt:Generated = false */
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    trm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Generated(),
                                    false));

                    /* Remove web service info */
                    WebServiceOperation wso = trm.getWebServiceOperation();
                    if (wso != null) {
                        cmd.append(RemoveCommand.create(editingDomain, wso));
                    }

                    PortTypeOperation pto =
                            (PortTypeOperation) Xpdl2ModelUtil
                                    .getOtherElement(trm,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_PortTypeOperation());
                    if (pto != null) {
                        cmd.append(Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        trm,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation(),
                                        pto));
                    }
                }
            }

        } else if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            if (task.getTaskReceive() != null) {
                TaskReceive taskReceive = task.getTaskReceive();

                cmd = new CompoundCommand();

                /* Remove mappings (overwrite message with new empty one)... */
                cmd.append(SetCommand.create(editingDomain,
                        taskReceive,
                        Xpdl2Package.eINSTANCE.getTaskReceive_Message(),
                        Xpdl2Factory.eINSTANCE.createMessage()));

                /* xpdExt:Generated = false */
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                taskReceive,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Generated(),
                                false));

                /* Remove web service info */
                WebServiceOperation wso = taskReceive.getWebServiceOperation();
                if (wso != null) {
                    cmd.append(RemoveCommand.create(editingDomain, wso));
                }

                PortTypeOperation pto =
                        (PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(taskReceive,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation());
                if (pto != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    taskReceive,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation(),
                                    pto));
                }
            } else if (task.getTaskSend() != null) {
                cmd = new CompoundCommand();

                /*
                 * We are only responsible for the data mappings in api reply
                 * activities - everything is updated by
                 * MaintainWebServiceReplyActivity
                 */
                cmd.append(SetCommand.create(editingDomain,
                        task.getTaskSend(),
                        Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                        Xpdl2Factory.eINSTANCE.createMessage()));
            }
        }

        if (cmd != null && !ReplyActivityUtil.isReplyActivity(activity)) {
            /*
             * Recurs and do any reply activities.
             */
            List<Activity> replyActivities =
                    ReplyActivityUtil.getReplyActivities(activity);

            for (Activity replyActivity : replyActivities) {
                Command replyCmd =
                        getClearGeneratedWebServiceCommand(editingDomain,
                                replyActivity);
                if (replyCmd != null) {
                    cmd.append(replyCmd);
                }
            }
        }

        return cmd;
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notification
     */
    private void appendRemoveManyNotificationListenerCommands(
            CompoundCommand cmd, TransactionalEditingDomain editingDomain,
            ENotificationImpl notification) {
        Object notifier = notification.getNotifier();
        Object feature = notification.getFeature();
        if (notifier instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getProcessInterface(errorMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            ErrorMethod implementedErrorMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedErrorMethod(act);
                            if (errorMethod.equals(implementedErrorMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                if (notification.getOldValue() instanceof Collection
                                        && assocParamsObj instanceof AssociatedParameters) {
                                    AssociatedParameters associatedParameters =
                                            (AssociatedParameters) assocParamsObj;
                                    Collection<AssociatedParameter> associatedParametersDeleted =
                                            (Collection<AssociatedParameter>) notification
                                                    .getOldValue();
                                    List<AssociatedParameter> associatedParamsToBeDeleted =
                                            new ArrayList<AssociatedParameter>();

                                    for (AssociatedParameter assocParam : associatedParametersDeleted) {
                                        EObject object =
                                                EMFSearchUtil
                                                        .findInList(associatedParameters
                                                                .getAssociatedParameter(),
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getAssociatedParameter_FormalParam(),
                                                                assocParam
                                                                        .getFormalParam());
                                        if (object instanceof AssociatedParameter) {
                                            associatedParamsToBeDeleted
                                                    .add((AssociatedParameter) object);
                                        }
                                    }

                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParamsToBeDeleted));

                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }
                            }
                        }
                    }
                }
            }

        } else if (notifier instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());

                                if (notification.getOldValue() instanceof Collection
                                        && assocParamsObj instanceof AssociatedParameters) {
                                    AssociatedParameters associatedParameters =
                                            (AssociatedParameters) assocParamsObj;

                                    Collection<AssociatedParameter> associatedParamsDeleted =
                                            (Collection<AssociatedParameter>) notification
                                                    .getOldValue();
                                    List<AssociatedParameter> associatedParamsToBeDeleted =
                                            new ArrayList<AssociatedParameter>();
                                    for (AssociatedParameter associatedParam : associatedParamsDeleted) {

                                        EObject object =
                                                EMFSearchUtil
                                                        .findInList(associatedParameters
                                                                .getAssociatedParameter(),
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getAssociatedParameter_FormalParam(),
                                                                associatedParam
                                                                        .getFormalParam());
                                        associatedParamsToBeDeleted
                                                .add((AssociatedParameter) object);
                                    }

                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParamsToBeDeleted));
                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notification
     */
    private void appendAddManyEventTypeListeners(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain,
            ENotificationImpl notification) {
        Object notifier = notification.getNotifier();
        Object feature = notification.getFeature();
        Object newValue = notification.getNewValue();
        /*
         * Handle Processes added to package
         */
        if (notifier instanceof Package) {
            if (Xpdl2Package.eINSTANCE.getPackage_Processes().equals(feature)
                    && notification.getOldValue() == null && newValue != null) {

                if (newValue instanceof List<?>) {
                    // Get the list of processes being added
                    List<Process> processes = (List<Process>) newValue;

                    // Update port type info
                    for (Process process : processes) {
                        cmd.append(getPortTypeNameChangeForProcess(editingDomain,
                                process));
                    }
                }
            }
        } else if (notifier instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                UpdateMappingsCommand updateMappingsCmd =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        interfaceMethod);
                if (updateMappingsCmd.canExecute()) {
                    cmd.append(updateMappingsCmd);
                }

                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                AssociatedParameters associatedParams = null;
                                if (assocParamsObj instanceof AssociatedParameters) {
                                    associatedParams =
                                            (AssociatedParameters) assocParamsObj;
                                }

                                if (associatedParams == null) {
                                    associatedParams =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createAssociatedParameters();
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    associatedParams));
                                }
                                if (newValue instanceof Collection) {

                                    Collection<AssociatedParameter> associatedParamsAdded =
                                            (Collection) newValue;
                                    List<AssociatedParameter> associatedParamsToBeAdded =
                                            new ArrayList<AssociatedParameter>();

                                    for (AssociatedParameter associatedParam : associatedParamsAdded) {
                                        EObject copiedAssocParam =
                                                EcoreUtil.copy(associatedParam);
                                        associatedParamsToBeAdded
                                                .add((AssociatedParameter) copiedAssocParam);

                                    }
                                    cmd.append(AddCommand
                                            .create(editingDomain,
                                                    associatedParams,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParamsToBeAdded));
                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }

                            }
                        }
                    }
                }

            }
        }
        /*
         * Handle many assoc params added to error method
         */
        else if (notifier instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getProcessInterface(errorMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            ErrorMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedErrorMethod(act);
                            if (errorMethod.equals(implementedMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                AssociatedParameters associatedParams = null;
                                if (assocParamsObj instanceof AssociatedParameters) {
                                    associatedParams =
                                            (AssociatedParameters) assocParamsObj;
                                }

                                if (associatedParams == null) {
                                    associatedParams =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createAssociatedParameters();
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    associatedParams));
                                }
                                if (newValue instanceof Collection) {
                                    Collection<AssociatedParameter> associatedParamsAdded =
                                            (Collection<AssociatedParameter>) newValue;
                                    List<AssociatedParameter> associatedParamsToBeAdded =
                                            new ArrayList<AssociatedParameter>();

                                    for (AssociatedParameter associatedParam : associatedParamsAdded) {
                                        EObject copiedAssocParam =
                                                EcoreUtil.copy(associatedParam);
                                        associatedParamsToBeAdded
                                                .add((AssociatedParameter) copiedAssocParam);
                                    }
                                    cmd.append(AddCommand
                                            .create(editingDomain,
                                                    associatedParams,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    associatedParamsToBeAdded));
                                }
                            }

                            /*
                             * Update the mappings for the implementing error
                             * event.
                             */
                            UpdateMappingsCommand updateMappingsCommand =
                                    UpdateMappingsCommandFactory
                                            .getUpdateMappingsCommand(editingDomain,
                                                    act);
                            if (updateMappingsCommand.canExecute()) {
                                cmd.append(updateMappingsCommand);
                            }

                        }
                    }
                }
            }
        }

    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notifier
     * @param feature
     * @param oldValue
     */
    private void appendRemoveNotificationListenerCommands(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain, Notification notification) {

        Object notifier = notification.getNotifier();
        Object feature = notification.getFeature();
        Object oldValue = notification.getOldValue();

        if (notifier instanceof Process) {
            Process process = (Process) notifier;
            if (Xpdl2Package.eINSTANCE
                    .getFormalParametersContainer_FormalParameters()
                    .equals(feature)) {
                // When a Formal parameter is removed, all the
                // activities mappings should be regenerated.
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        process);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }

            if (Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                    .equals(feature)) {
                if (oldValue instanceof Activity) {
                    Activity activity = (Activity) oldValue;

                    updateReplyActivitiesForGeneratedRequest(editingDomain,
                            activity,
                            cmd);
                }
            }

        } else if (notifier instanceof AssociatedParameters) {
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParameters_AssociatedParameter()
                    .equals(feature)) {
                if (oldValue instanceof AssociatedParameter) {
                    Activity parentActivity =
                            Xpdl2ModelUtil
                                    .getParentActivity((EObject) notifier);
                    if (parentActivity != null
                            && (Xpdl2ModelUtil
                                    .isGeneratedRequestActivity(parentActivity) || ThrowErrorEventUtil
                                    .shouldGenerateMappingsForErrorEvent(parentActivity))) {
                        appendUpdateMappingsCommands(editingDomain,
                                parentActivity,
                                cmd);
                    }
                }
            }
        } else if (notifier instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {

                UpdateMappingsCommand updateMappingsCmd =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        interfaceMethod);
                if (updateMappingsCmd.canExecute()) {
                    cmd.append(updateMappingsCmd);
                }

                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());

                                if (notification.getOldValue() instanceof AssociatedParameter
                                        && assocParamsObj instanceof AssociatedParameters) {
                                    AssociatedParameters associatedParameters =
                                            (AssociatedParameters) assocParamsObj;
                                    AssociatedParameter associatedParameter =
                                            (AssociatedParameter) notification
                                                    .getOldValue();
                                    EObject object =
                                            EMFSearchUtil
                                                    .findInList(associatedParameters
                                                            .getAssociatedParameter(),
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getAssociatedParameter_FormalParam(),
                                                            associatedParameter
                                                                    .getFormalParam());

                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    object));
                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }

                            }
                        }
                    }
                }

            }

        } else if (notifier instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getProcessInterface(errorMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            ErrorMethod implementedErrorMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedErrorMethod(act);
                            if (errorMethod.equals(implementedErrorMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                if (notification.getOldValue() instanceof AssociatedParameter
                                        && assocParamsObj instanceof AssociatedParameters) {
                                    AssociatedParameters associatedParameters =
                                            (AssociatedParameters) assocParamsObj;
                                    AssociatedParameter associatedParameter =
                                            (AssociatedParameter) notification
                                                    .getOldValue();
                                    EObject object =
                                            EMFSearchUtil
                                                    .findInList(associatedParameters
                                                            .getAssociatedParameter(),
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getAssociatedParameter_FormalParam(),
                                                            associatedParameter
                                                                    .getFormalParam());

                                    cmd.append(RemoveCommand
                                            .create(editingDomain,
                                                    associatedParameters,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    object));

                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }
                            }
                        }
                    }
                }

            }
        }

        /*
         * Handle xpdExt:AssociatedParameters removed from activity
         */
        if (notifier instanceof Activity
                && XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters()
                        .equals(notification.getFeature())) {
            Activity errorEventActivity = (Activity) notifier;
            if (errorEventActivity != null
                    && ThrowErrorEventUtil
                            .shouldGenerateMappingsForErrorEvent(errorEventActivity)) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        errorEventActivity);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }
        return;
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notifier
     */
    private void appendSetEventTypeListenerCommands(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain, Notification notification) {
        Object notifier = notification.getNotifier();
        Object feature = notification.getFeature();
        Object oldValue = notification.getOldValue();
        Object newValue = notification.getNewValue();

        if (notifier instanceof FormalParameter) {
            // When Formal Parameter's name, mode, or datatype is changed,
            // regenerate mappings for all
            // activities in the process.
            FormalParameter formalParameter = (FormalParameter) notifier;
            if (Xpdl2Package.eINSTANCE.getNamedElement_Name().equals(feature)
                    || Xpdl2Package.eINSTANCE.getFormalParameter_Mode()
                            .equals(feature)
                    || Xpdl2Package.eINSTANCE.getProcessRelevantData_DataType()
                            .equals(feature)) {
                EObject container = formalParameter.eContainer();
                if (container instanceof ProcessInterface) {
                    ProcessInterface processInterface =
                            (ProcessInterface) container;
                    Package xpdlPackage =
                            Xpdl2ModelUtil.getPackage(processInterface);
                    List<Process> processes = xpdlPackage.getProcesses();

                    for (Process proc : processes) {
                        if (processInterface.equals(ProcessInterfaceUtil
                                .getImplementedProcessInterface(proc))) {
                            UpdateMappingsCommand updateMappingsCommand =
                                    UpdateMappingsCommandFactory
                                            .getUpdateMappingsCommand(editingDomain,
                                                    proc);
                            if (updateMappingsCommand.canExecute()) {
                                cmd.append(updateMappingsCommand);
                            }
                        }
                    }
                }
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        container);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }

        }
        /*
         * If associated parameter formalParam changes (may be after by token
         * name precommit listener updates token name for formal param display
         * name change) - update mappings in parent object.
         * 
         * Sid XPD-2087: Otherwise may be a set on the
         * DisableImplicitAssociation flag in which case we need to redo parent
         * activity / method mappings.
         */
        else if (notifier instanceof AssociatedParameter
                || notifier instanceof AssociatedParameters) {
            ProcessInterface processInterface =
                    ProcessInterfaceUtil
                            .getProcessInterface((EObject) notifier);

            if (processInterface != null) {
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                proc);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }
                    }
                }
            }

            EObject assocParamContainer =
                    ProcessInterfaceUtil.getParentMethod((EObject) notifier);
            if (assocParamContainer == null) {
                assocParamContainer =
                        Xpdl2ModelUtil.getParentActivity((EObject) notifier);
            }

            if (assocParamContainer != null) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        assocParamContainer);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }
        /*
         * Handle Changes directly Activity element
         */
        else if (notifier instanceof Activity) {
            Activity activity = (Activity) notifier;
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                if (Xpdl2Package.eINSTANCE.getNamedElement_Name()
                        .equals(feature)) {
                    // Activity name change should regenerate mappings
                    // for itself and reply activities associated.
                    updatePortTypeOperationName(cmd,
                            editingDomain,
                            activity,
                            newValue);

                    appendUpdateMappingsCommands(editingDomain, activity, cmd);
                }
            } else if (Xpdl2Package.eINSTANCE.getActivity_Implementation()
                    .equals(feature)) {
                if (oldValue instanceof Implementation
                        && newValue instanceof Task) {
                    // When the user changes the task type
                    Task newTask = (Task) newValue;
                    if (oldValue instanceof Task
                            && ((Task) oldValue).getTaskReceive() != null) {
                        // do nothing
                    } else if (newTask.getTaskReceive() != null) {
                        cmd.append(new AddPortTypeCommand(editingDomain,
                                activity.getProcess(), activity));
                        updatePortTypeOperationName(cmd,
                                editingDomain,
                                activity,
                                Xpdl2ModelUtil.getDisplayName(newTask
                                        .getActivity()));

                        appendUpdateMappingsCommands(editingDomain,
                                activity,
                                cmd);
                    }
                }
            } else if (Xpdl2Package.eINSTANCE.getActivity_Event()
                    .equals(feature)) {
                if (oldValue instanceof Event) {
                    // When event implementation type set from
                    // unspecified to WEB SERVICE
                    Event oldEvent = (Event) oldValue;
                    Object oldImplementationType =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(oldEvent,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (newValue instanceof Event) {
                        Event newEvent = (Event) newValue;
                        Object newImplementationType =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(newEvent,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());
                        if (TaskImplementationTypeDefinitions.WEB_SERVICE
                                .equals(newImplementationType)
                                && !(TaskImplementationTypeDefinitions.WEB_SERVICE
                                        .equals(oldImplementationType))) {
                            EObject container = newEvent.eContainer();
                            if (container instanceof Activity) {
                                cmd.append(new AddPortTypeCommand(
                                        editingDomain, activity.getProcess(),
                                        activity));
                            }
                        }
                    }
                }
            }
        } else if (notifier instanceof Task) {
            Task task = (Task) notifier;
            if (Xpdl2Package.eINSTANCE.getTask_TaskReceive().equals(feature)) {
                // When the user changes from Unspecified to Web Service
                // in a Receive Task

                if (oldValue instanceof TaskReceive) {
                    TaskReceive oldTaskRecv = (TaskReceive) oldValue;
                    Object implementationAttrib =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(oldTaskRecv,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (!(TaskImplementationTypeDefinitions.WEB_SERVICE)
                            .equals(implementationAttrib)) {
                        Activity activity = task.getActivity();
                        cmd.append(new AddPortTypeCommand(editingDomain, task
                                .getActivity().getProcess(), activity));

                        appendUpdateMappingsCommands(editingDomain,
                                activity,
                                cmd);

                    }
                }
            }
        } else if (notifier instanceof TaskSend) {
            TaskSend taskSend = (TaskSend) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ReplyToActivityId().equals(feature)) {
                // When Task Send reply to activity is SET
                if (newValue != null) {
                    Activity taskSendActivity =
                            Xpdl2ModelUtil.getParentActivity(taskSend);
                    if (taskSendActivity != null) {
                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                taskSendActivity);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }
                    }
                }
            }
        } else if (notifier instanceof TriggerResultMessage) {
            TriggerResultMessage trm = (TriggerResultMessage) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ReplyToActivityId().equals(feature)) {
                // When end message event or intermediate message throw
                // event reply to activity is SET
                if (newValue != null) {
                    Activity parentActivity =
                            Xpdl2ModelUtil.getParentActivity(trm);
                    if (parentActivity != null) {
                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                parentActivity);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }
                    }
                }
            } else if (XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_ReplyImmediately().equals(feature)
                    || XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_Generated().equals(feature)) {
                /*
                 * Reply immediately flag has been changed
                 */
                cmd.append(getReplyImmediateMappingCommand(editingDomain,
                        trm,
                        (Activity) getTypedAncestor(notification,
                                Activity.class)));
            }
        } else if (notifier instanceof Event) {
            Event notifiedEvent = (Event) notifier;
            // when the trigger type is changed to message
            if (Xpdl2Package.eINSTANCE.getStartEvent_TriggerResultMessage()
                    .equals(feature)
                    || Xpdl2Package.eINSTANCE
                            .getIntermediateEvent_TriggerResultMessage()
                            .equals(feature)) {
                if (newValue instanceof TriggerResultMessage
                        && oldValue == null) {
                    EObject container = notifiedEvent.eContainer();
                    if (container instanceof Activity) {
                        Activity act = (Activity) container;
                        if (ProcessInterfaceUtil.isEventImplemented(act)) {
                            cmd.append(new AddPortTypeForImplementedActivityCommand(
                                    editingDomain, act));
                            updateImplementedErrorActivitiesForGeneratedRequest(editingDomain,
                                    act,
                                    cmd);
                        } else {
                            cmd.append(new AddPortTypeCommand(editingDomain,
                                    act.getProcess(), act));
                        }

                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                act);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }

                    }
                }
            } else if (Xpdl2Package.eINSTANCE
                    .getEndEvent_TriggerResultMessage().equals(feature)) {
                if (newValue instanceof TriggerResultMessage
                        && oldValue == null) {
                    EObject container = notifiedEvent.eContainer();
                    if (container instanceof Activity) {
                        Activity act = (Activity) container;

                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                act);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }
                    }
                }
            }
        } else if (notifier instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                if (!ReplyActivityUtil.isReplyActivity(act)) {
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherAttributeCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_DisplayName(),
                                                    newValue));
                                }

                            }
                        }
                    }
                }
            }
            if (Xpdl2Package.eINSTANCE.getNamedElement_Name().equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);

                if (TriggerType.MESSAGE_LITERAL.equals(interfaceMethod
                        .getTrigger())) {
                    if (newValue == null
                            || (newValue != null && "".equals(newValue))) { //$NON-NLS-1$
                        TriggerResultMessage triggerResultMessage =
                                interfaceMethod.getTriggerResultMessage();

                        if (triggerResultMessage != null) {
                            String substitutedName =
                                    OperationNameUtil
                                            .getSubstitutedName(interfaceMethod);

                            PortTypeOperation porttypeOperation =
                                    (PortTypeOperation) Xpdl2ModelUtil
                                            .getOtherElement(triggerResultMessage,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_PortTypeOperation());
                            if (porttypeOperation != null) {
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                porttypeOperation,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getPortTypeOperation_OperationName(),
                                                substitutedName));
                            }
                        }
                    }
                }
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                cmd.append(SetCommand.create(editingDomain,
                                        act,
                                        Xpdl2Package.eINSTANCE
                                                .getNamedElement_Name(),
                                        newValue));

                                appendUpdateMappingsCommands(editingDomain,
                                        act,
                                        cmd);
                            }
                        }
                    }
                }
            } else if (XpdExtensionPackage.eINSTANCE
                    .getInterfaceMethod_TriggerResultMessage().equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        processInterface);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }

            } else if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_DisableImplicitAssociation()
                    .equals(feature)) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        interfaceMethod);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }

            }

        } else if (notifier instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE.getErrorMethod_ErrorCode()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getProcessInterface(errorMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            ErrorMethod implementedErrorMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedErrorMethod(act);
                            if (errorMethod.equals(implementedErrorMethod)) {
                                Event event = act.getEvent();
                                if (event instanceof EndEvent) {
                                    EndEvent endEvent = (EndEvent) event;

                                    String errorCode = (String) newValue;
                                    String errorEventName =
                                            ImplementInterfaceUtil
                                                    .getImplementingErrorEventName(errorCode);
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherAttributeCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_DisplayName(),
                                                    errorEventName));

                                    cmd.append(SetCommand
                                            .create(editingDomain,
                                                    act,
                                                    Xpdl2Package.eINSTANCE
                                                            .getNamedElement_Name(),
                                                    NameUtil.getInternalName(errorEventName,
                                                            false)));

                                    cmd.append(EventObjectUtil
                                            .getSetErrorCodeCommand(editingDomain,
                                                    act,
                                                    errorCode));
                                }
                            }
                        }
                    }
                }
            } else if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_DisableImplicitAssociation()
                    .equals(feature)) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        errorMethod);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }

            }
        }

        /*
         * Check for a change in the generated status of a request activity and
         * update any throw error events for it (they may need their mappings
         * now generated or not-generated.
         */
        if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated()
                .equals(feature) && notifier instanceof EObject) {
            Activity notifierActivity =
                    Xpdl2ModelUtil.getParentActivity((EObject) notifier);

            if (notifierActivity != null) {
                List<Activity> errorEventActivities =
                        ThrowErrorEventUtil
                                .getThrowErrorEvents(notifierActivity);
                for (Activity throwErrorActivity : errorEventActivities) {
                    UpdateMappingsCommand updateMappingsCommand =
                            UpdateMappingsCommandFactory
                                    .getUpdateMappingsCommand(editingDomain,
                                            throwErrorActivity);
                    if (updateMappingsCommand.canExecute()) {
                        cmd.append(updateMappingsCommand);
                    }
                }
            }
        }

        /*
         * Handle set of EndEvent to type Error
         */
        if (notifier instanceof EndEvent
                && Xpdl2Package.eINSTANCE.getEndEvent_ResultError()
                        .equals(notification.getFeature())) {
            Activity parentActivity =
                    Xpdl2ModelUtil.getParentActivity((EndEvent) notifier);
            if (parentActivity != null) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        parentActivity);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }

        return;
    }

    /**
     * @param editingDomain
     * @param trm
     * @param activity
     * @return
     */
    private Command getReplyImmediateMappingCommand(
            TransactionalEditingDomain editingDomain, TriggerResultMessage trm,
            Activity activity) {
        if (activity != null) {
            Message message = trm.getMessage();

            if (message != null) {

                /*
                 * Reply Immediate that is auto generated should add default
                 * process id mapping - anything else should remove it.
                 */
                if (ReplyActivityUtil
                        .isStartMessageWithReplyImmediate(activity)
                        && Xpdl2ModelUtil.isGeneratedRequestActivity(activity)
                        && ProcessDestinationUtil
                                .isBPMDestinationSelected(activity.getProcess())) {

                    ReplyImmediateDataMappings dataMappings =
                            (ReplyImmediateDataMappings) Xpdl2ModelUtil
                                    .getOtherElement(message,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ReplyImmediateDataMappings());

                    dataMappings =
                            XpdExtensionFactory.eINSTANCE
                                    .createReplyImmediateDataMappings();
                    DataMapping mapping =
                            Xpdl2Factory.eINSTANCE.createDataMapping();
                    mapping.setDirection(DirectionType.IN_LITERAL);
                    mapping.setFormal(String
                            .format("wso:/part:%1$s", StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME)); //$NON-NLS-1$

                    Expression expression =
                            Xpdl2ModelUtil
                                    .createExpression(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                                            .getName());
                    expression.setScriptGrammar("XPath"); //$NON-NLS-1$
                    mapping.setActual(expression);

                    dataMappings.getDataMappings().add(mapping);

                    return Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    message,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyImmediateDataMappings(),
                                    dataMappings);
                } else {
                    /*
                     * Remove the reply immediate from non generated reply
                     * imemdiate start events.
                     */
                    ReplyImmediateDataMappings dataMappings =
                            (ReplyImmediateDataMappings) Xpdl2ModelUtil
                                    .getOtherElement(message,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ReplyImmediateDataMappings());
                    if (dataMappings != null) {
                        return Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        trm.getMessage(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ReplyImmediateDataMappings(),
                                        dataMappings);
                    }

                }

            }
        }

        return null;
    }

    /**
     * Append any commands needed to update the auto-generated mappings in the
     * given activity.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     */
    private void appendUpdateMappingsCommands(
            TransactionalEditingDomain editingDomain, Activity activity,
            CompoundCommand cmd) {
        UpdateMappingsCommand updateMappingsCommand =
                UpdateMappingsCommandFactory
                        .getUpdateMappingsCommand(editingDomain, activity);
        if (updateMappingsCommand.canExecute()) {
            cmd.append(updateMappingsCommand);
        }

        updateReplyActivitiesForGeneratedRequest(editingDomain, activity, cmd);

        return;
    }

    /**
     * Update the reply activity mappings for the given request activity with
     * auto-generated operation.
     * 
     * @param editingDomain
     * @param requestActivity
     * @param cmd
     */
    private void updateReplyActivitiesForGeneratedRequest(
            TransactionalEditingDomain editingDomain, Activity requestActivity,
            CompoundCommand cmd) {
        List<Activity> replyActivities =
                ReplyActivityUtil.getReplyActivities(requestActivity);
        for (Activity replyActivity : replyActivities) {
            UpdateMappingsCommand updateReplyMappings =
                    UpdateMappingsCommandFactory
                            .getUpdateMappingsCommand(editingDomain,
                                    replyActivity);
            if (updateReplyMappings.canExecute()) {
                cmd.append(updateReplyMappings);
            }
        }

        updateImplementedErrorActivitiesForGeneratedRequest(editingDomain,
                requestActivity,
                cmd);

        return;
    }

    /**
     * @param editingDomain
     * @param requestActivity
     * @param cmd
     */
    private void updateImplementedErrorActivitiesForGeneratedRequest(
            TransactionalEditingDomain editingDomain, Activity requestActivity,
            CompoundCommand cmd) {
        /*
         * Also update throw error method mappings for implemented error methos
         * for implemented requests
         */
        if (Xpdl2ModelUtil.isEventImplemented(requestActivity)) {
            List<Activity> throwErrorEvents =
                    ThrowErrorEventUtil.getThrowErrorEvents(requestActivity);
            for (Activity errorEventActivity : throwErrorEvents) {
                if (ThrowErrorEventUtil
                        .isThrowFaultMessageErrorEvent(errorEventActivity)) {
                    UpdateMappingsCommand updateReplyMappings =
                            UpdateMappingsCommandFactory
                                    .getUpdateMappingsCommand(editingDomain,
                                            errorEventActivity);
                    if (updateReplyMappings.canExecute()) {
                        cmd.append(updateReplyMappings);
                    }
                }
            }
        }
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notification
     */
    private void appendAddEventTypeListeners(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain,
            ENotificationImpl notification) {
        Object notifier = notification.getNotifier();
        Object feature = notification.getFeature();
        Object newValue = notification.getNewValue();

        /*
         * HandleProcess added to package
         */
        if (notifier instanceof Package) {
            if (Xpdl2Package.eINSTANCE.getPackage_Processes().equals(feature)) {
                cmd.append(getPortTypeNameChangeForProcess(editingDomain,
                        (Process) newValue));
            }
        }
        /*
         * Handle process interface added to package.
         */
        else if (notifier instanceof ProcessInterfaces) {
            if (XpdExtensionPackage.eINSTANCE
                    .getProcessInterfaces_ProcessInterface().equals(feature)) {
                cmd.append(getPortTypeNameChangeForProcIfc(editingDomain,
                        newValue));
            }
        }

        /*
         * Handle activity added to process / embedded sub-process.
         */
        if (notifier instanceof FlowContainer) {
            if (Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                    .equals(feature)) {
                // To add the WebService operations and port type
                // operations for request activities
                Activity activity = (Activity) notification.getNewValue();
                if (isSuitableForPortTypeGeneration(activity)) {
                    cmd.append(new AddPortTypeCommand(editingDomain, activity
                            .getProcess(), activity));
                    UpdateMappingsCommand updateMappingsCommand =
                            UpdateMappingsCommandFactory
                                    .getUpdateMappingsCommand(editingDomain,
                                            activity);
                    if (updateMappingsCommand.canExecute()) {
                        cmd.append(updateMappingsCommand);
                    }
                } else {
                    if (ReplyActivityUtil.isReplyActivity(activity)
                            || ThrowErrorEventUtil
                                    .isThrowFaultMessageErrorEvent(activity)) {
                        UpdateMappingsCommand updateMappingsCommand =
                                UpdateMappingsCommandFactory
                                        .getUpdateMappingsCommand(editingDomain,
                                                activity);
                        if (updateMappingsCommand.canExecute()) {
                            cmd.append(updateMappingsCommand);
                        }
                    }
                }
            }
        }

        /*
         * Handle parameter added to process / process interface.
         */
        if (notifier instanceof FormalParametersContainer) {
            FormalParametersContainer formalParametersContainer =
                    (FormalParametersContainer) notifier;

            if (Xpdl2Package.eINSTANCE
                    .getFormalParametersContainer_FormalParameters()
                    .equals(feature)) {
                // To add mappings when a formal parameter is added to a
                // process or process interface.
                UpdateMappingsCommand command =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        formalParametersContainer);
                if (command.canExecute()) {
                    cmd.append(command);
                }

                if (notifier instanceof ProcessInterface) {
                    ProcessInterface processInterface =
                            (ProcessInterface) notifier;
                    Package xpdlPackage =
                            Xpdl2ModelUtil.getPackage(processInterface);
                    List<Process> processes = xpdlPackage.getProcesses();

                    for (Process proc : processes) {
                        if (processInterface.equals(ProcessInterfaceUtil
                                .getImplementedProcessInterface(proc))) {
                            UpdateMappingsCommand updateMappingsCommand =
                                    UpdateMappingsCommandFactory
                                            .getUpdateMappingsCommand(editingDomain,
                                                    proc);
                            if (updateMappingsCommand.canExecute()) {
                                cmd.append(updateMappingsCommand);
                            }
                        }
                    }
                }
            }
        }

        /*
         * Start / Intermediate Events added to process interface.
         */
        if (notifier instanceof ProcessInterface) {
            if (XpdExtensionPackage.eINSTANCE
                    .getProcessInterface_IntermediateMethods().equals(feature)
                    || XpdExtensionPackage.eINSTANCE
                            .getProcessInterface_StartMethods().equals(feature)) {
                // if associated Parameters are added to the
                // start/intermediate
                // methods of a process interface.
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        (ProcessInterface) notifier);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }

        /*
         * Associated parameter added to auto-generated operation request
         * activity or throw error event for such.
         */
        if (notifier instanceof AssociatedParameters) {
            // When associated parameters added to activity, activity
            // should regenerate mappings for itself and reply
            // activities associated.
            AssociatedParameters associatedParameters =
                    (AssociatedParameters) notifier;

            if (newValue instanceof AssociatedParameter
                    && associatedParameters.eContainer() instanceof Activity) {
                Activity activity =
                        (Activity) associatedParameters.eContainer();
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)
                        || ThrowErrorEventUtil
                                .shouldGenerateMappingsForErrorEvent(activity)) {
                    appendUpdateMappingsCommands(editingDomain, activity, cmd);
                }
            }
        }

        /*
         * Handle associated parameters added to interface event.
         */
        if (notifier instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                UpdateMappingsCommand updateMappingsCmd =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        interfaceMethod);
                if (updateMappingsCmd.canExecute()) {
                    cmd.append(updateMappingsCmd);
                }

                ProcessInterface processInterface =
                        ProcessInterfaceUtil
                                .getProcessInterface(interfaceMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            InterfaceMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedMethod(act);
                            if (interfaceMethod.equals(implementedMethod)) {
                                /*
                                 * Sid XPD-2087. We didn't used to re-synch the
                                 * associated parameters for non-generated
                                 * implemented type-none start events when
                                 * really all we should NOT do if it's not a
                                 * generated request activity is update the
                                 * mappings.
                                 * 
                                 * Besides which the update mappigns command is
                                 * conditionallised internally to not do
                                 * anything unless appropriate, so let that sort
                                 * things out (then it will work for reply
                                 * activities etc.
                                 */
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                AssociatedParameters associatedParams = null;
                                if (assocParamsObj instanceof AssociatedParameters) {
                                    associatedParams =
                                            (AssociatedParameters) assocParamsObj;
                                }

                                if (associatedParams == null) {
                                    associatedParams =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createAssociatedParameters();
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    associatedParams));
                                }
                                if (newValue instanceof AssociatedParameter) {
                                    EObject copiedAssocParam =
                                            EcoreUtil
                                                    .copy((AssociatedParameter) newValue);
                                    cmd.append(AddCommand
                                            .create(editingDomain,
                                                    associatedParams,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    copiedAssocParam));

                                    appendUpdateMappingsCommands(editingDomain,
                                            act,
                                            cmd);
                                }

                            }
                        }
                    }
                }
            }
        }

        /**
         * Handle associated parameters added to interface error method.
         */
        if (notifier instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) notifier;
            if (XpdExtensionPackage.eINSTANCE
                    .getAssociatedParametersContainer_AssociatedParameters()
                    .equals(feature)) {
                ProcessInterface processInterface =
                        ProcessInterfaceUtil.getProcessInterface(errorMethod);
                Package xpdlPackage =
                        Xpdl2ModelUtil.getPackage(processInterface);
                List<Process> processes = xpdlPackage.getProcesses();

                for (Process proc : processes) {
                    if (processInterface.equals(ProcessInterfaceUtil
                            .getImplementedProcessInterface(proc))) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        for (Activity act : allActivitiesInProc) {
                            ErrorMethod implementedMethod =
                                    ProcessInterfaceUtil
                                            .getImplementedErrorMethod(act);
                            if (errorMethod.equals(implementedMethod)) {
                                Object assocParamsObj =
                                        Xpdl2ModelUtil
                                                .getOtherElement(act,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_AssociatedParameters());
                                AssociatedParameters associatedParams = null;
                                if (assocParamsObj instanceof AssociatedParameters) {
                                    associatedParams =
                                            (AssociatedParameters) assocParamsObj;
                                }

                                if (associatedParams == null) {
                                    associatedParams =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createAssociatedParameters();
                                    cmd.append(Xpdl2ModelUtil
                                            .getSetOtherElementCommand(editingDomain,
                                                    act,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_AssociatedParameters(),
                                                    associatedParams));
                                }
                                if (newValue instanceof AssociatedParameter) {
                                    EObject copiedAssocParam =
                                            EcoreUtil
                                                    .copy((AssociatedParameter) newValue);
                                    cmd.append(AddCommand
                                            .create(editingDomain,
                                                    associatedParams,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    copiedAssocParam));
                                }

                                /*
                                 * Update the mappings for the implementing
                                 * error event.
                                 */
                                UpdateMappingsCommand updateMappingsCommand =
                                        UpdateMappingsCommandFactory
                                                .getUpdateMappingsCommand(editingDomain,
                                                        act);
                                if (updateMappingsCommand.canExecute()) {
                                    cmd.append(updateMappingsCommand);
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
         * Handle configuration of throw end error event to throw error for
         * auto-generated request activity.
         */
        if ((notifier instanceof ResultError && XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_FaultMessage()
                .equals(notification.getFeature()))) {
            Activity errorEventActivity =
                    Xpdl2ModelUtil.getParentActivity((EObject) notifier);
            if (errorEventActivity != null
                    && ThrowErrorEventUtil
                            .shouldGenerateMappingsForErrorEvent(errorEventActivity)) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        errorEventActivity);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }

        /*
         * Handle xpdExt:AssociatedParameters added to activity
         */
        if (notifier instanceof Activity
                && XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters()
                        .equals(notification.getFeature())) {
            Activity activity = (Activity) notifier;
            if (ThrowErrorEventUtil
                    .shouldGenerateMappingsForErrorEvent(activity)
                    || ReplyActivityUtil.isReplyActivity(activity)) {
                UpdateMappingsCommand updateMappingsCommand =
                        UpdateMappingsCommandFactory
                                .getUpdateMappingsCommand(editingDomain,
                                        activity);
                if (updateMappingsCommand.canExecute()) {
                    cmd.append(updateMappingsCommand);
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * @return
     */
    private boolean isSuitableForPortTypeGeneration(Activity activity) {
        if (TaskType.RECEIVE_LITERAL.equals(TaskObjectUtil
                .getTaskType(activity))) {
            return true;
        } else if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))) {
            return true;
        }
        return false;
    }

    /**
     * @param editingDomain
     * @param newValue
     * @return
     */
    private Command getPortTypeNameChangeForProcIfc(
            TransactionalEditingDomain editingDomain, Object newValue) {
        CompoundCommand cmd = null;
        if (newValue instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) newValue;
            cmd = new CompoundCommand();
            Collection<InterfaceMethod> ifcMessageMethods =
                    ProcessInterfaceUtil.getIfcMessageMethods(processInterface);
            for (InterfaceMethod ifcMethod : ifcMessageMethods) {
                TriggerResultMessage triggerResultMessage =
                        ifcMethod.getTriggerResultMessage();
                if (triggerResultMessage != null) {
                    Object otherElement =
                            Xpdl2ModelUtil
                                    .getOtherElement(triggerResultMessage,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_PortTypeOperation());
                    if (otherElement instanceof PortTypeOperation) {
                        PortTypeOperation portTypeOperation =
                                (PortTypeOperation) otherElement;
                        cmd.append(getUpdatePortTypeNameCmd(editingDomain,
                                portTypeOperation,
                                processInterface.getName()));
                    }
                }

            }
        }

        return cmd;
    }

    /**
     * @param editingDomain
     * @param process
     * @return
     */
    private Command getPortTypeNameChangeForProcess(
            TransactionalEditingDomain editingDomain, Process process) {
        CompoundCommand cmd = null;
        cmd = new CompoundCommand();
        Collection<Activity> activitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : activitiesInProc) {
            if (!ProcessInterfaceUtil.isEventImplemented(activity)
                    && Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                PortTypeOperation portTypeOperation =
                        Xpdl2ModelUtil.getPortTypeOperation(activity);
                if (portTypeOperation != null) {
                    cmd.append(getUpdatePortTypeNameCmd(editingDomain,
                            portTypeOperation,
                            process.getName()));
                }
                // set the web service and porttype operation location to the
                // new location of the process
                IFile xpdlFile = WorkingCopyUtil.getFile(process);
                if (xpdlFile != null) {
                    String fileName = Xpdl2ModelUtil.getWsdlFileName(xpdlFile);

                    cmd.append(SetCommand.create(editingDomain,
                            portTypeOperation.getExternalReference(),
                            Xpdl2Package.eINSTANCE
                                    .getExternalReference_Location(),
                            fileName));
                    TriggerResultMessage triggerResultMessage =
                            EventObjectUtil.getTriggerResultMessage(activity);
                    ExternalReference extRef = null;
                    if (triggerResultMessage != null
                            && triggerResultMessage.getWebServiceOperation() != null
                            && triggerResultMessage.getWebServiceOperation()
                                    .getService() != null
                            && triggerResultMessage.getWebServiceOperation()
                                    .getService().getEndPoint() != null) {
                        extRef =
                                triggerResultMessage.getWebServiceOperation()
                                        .getService().getEndPoint()
                                        .getExternalReference();
                    } else {
                        if (activity.getImplementation() instanceof Task) {
                            TaskReceive taskReceive =
                                    ((Task) activity.getImplementation())
                                            .getTaskReceive();
                            if (taskReceive != null
                                    && taskReceive.getWebServiceOperation() != null
                                    && taskReceive.getWebServiceOperation()
                                            .getService() != null
                                    && taskReceive.getWebServiceOperation()
                                            .getService().getEndPoint() != null) {
                                extRef =
                                        taskReceive.getWebServiceOperation()
                                                .getService().getEndPoint()
                                                .getExternalReference();

                            }
                        }
                    }
                    if (extRef != null) {
                        cmd.append(SetCommand.create(editingDomain,
                                extRef,
                                Xpdl2Package.eINSTANCE
                                        .getExternalReference_Location(),
                                fileName));
                    }
                }

            }
        }
        return cmd;
    }

    /**
     * @param editingDomain
     * @param portTypeOperation
     * @param portTypeName
     * @return
     */
    private Command getUpdatePortTypeNameCmd(
            TransactionalEditingDomain editingDomain,
            PortTypeOperation portTypeOperation, String portTypeName) {
        return SetCommand.create(editingDomain,
                portTypeOperation,
                XpdExtensionPackage.eINSTANCE
                        .getPortTypeOperation_PortTypeName(),
                portTypeName);
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param object
     * @param notifier
     */
    private void updatePortTypeOperationName(CompoundCommand cmd,
            TransactionalEditingDomain editingDomain, Activity activity,
            Object newValue) {
        if (!(newValue instanceof String)) {
            return;
        }
        String portTypeOpName =
                NameUtil.getInternalName((String) newValue, false);
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null) {
                Object element =
                        Xpdl2ModelUtil.getOtherElement(taskReceive,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
                if (element instanceof PortTypeOperation) {
                    PortTypeOperation op = (PortTypeOperation) element;

                    if (portTypeOpName == null
                            || (portTypeOpName != null && "".equals(portTypeOpName))) { //$NON-NLS-1$

                        portTypeOpName =
                                OperationNameUtil.getSubstitutedName(activity);
                    }
                    /*
                     * XPD-5911: if a port type operation name has leading
                     * digit(s) then prefix with underscore
                     */
                    if (portTypeOpName != null
                            && Character.isDigit(portTypeOpName.charAt(0))) {

                        portTypeOpName = "_" + portTypeOpName; //$NON-NLS-1$
                    }
                    cmd.append(SetCommand.create(editingDomain,
                            op,
                            XpdExtensionPackage.eINSTANCE
                                    .getPortTypeOperation_OperationName(),
                            portTypeOpName));
                }
            }
        } else {
            Event event = activity.getEvent();
            if (event != null) {
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                        .equals(eventTriggerType)) {
                    TriggerResultMessage trigResultMsg = null;
                    if (event instanceof StartEvent) {
                        trigResultMsg =
                                ((StartEvent) event).getTriggerResultMessage();
                    } else if (event instanceof IntermediateEvent) {
                        trigResultMsg =
                                ((IntermediateEvent) event)
                                        .getTriggerResultMessage();
                    }
                    Object element =
                            Xpdl2ModelUtil
                                    .getOtherElement(trigResultMsg,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_PortTypeOperation());
                    if (element instanceof PortTypeOperation) {
                        PortTypeOperation op = (PortTypeOperation) element;
                        if (portTypeOpName == null
                                || (portTypeOpName != null && "".equals(portTypeOpName))) { //$NON-NLS-1$
                            portTypeOpName =
                                    OperationNameUtil
                                            .getSubstitutedName(activity);
                        }

                        /*
                         * XPD-5911: if a port type operation name has leading
                         * digit(s) then prefix with underscore
                         */
                        if (portTypeOpName != null
                                && Character.isDigit(portTypeOpName.charAt(0))) {

                            portTypeOpName = "_" + portTypeOpName; //$NON-NLS-1$
                        }
                        cmd.append(SetCommand.create(editingDomain,
                                op,
                                XpdExtensionPackage.eINSTANCE
                                        .getPortTypeOperation_OperationName(),
                                portTypeOpName));
                    }
                }
            }
        }

    }
}
