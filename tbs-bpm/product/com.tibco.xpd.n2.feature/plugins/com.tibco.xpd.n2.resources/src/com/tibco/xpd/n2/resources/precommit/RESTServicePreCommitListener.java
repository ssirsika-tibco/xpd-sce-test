/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.precommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PreCommitListener for adding/removing REST service as an internal artifact
 * for an incoming web service request activity
 * 
 * @author agondal
 * @since 3 Dec 2012
 */
public class RESTServicePreCommitListener extends
        AbstractActivityPreCommitContributor {

    Xpdl2Factory factory = Xpdl2Factory.eINSTANCE;

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

        Set<Activity> addREST = new HashSet<Activity>();

        Set<Process> removeREST = new HashSet<Process>();

        Set<Activity> unsetRESTflag = new HashSet<Activity>();

        Set<Process> affectedProcesses = new HashSet<Process>();

        EditingDomain ed = event.getEditingDomain();

        for (ENotificationImpl notification : notifications) {

            /*
             * create a list of affected processes to check whether their
             * generated REST services are in correct state later on
             */
            EObject eo = getTypedAncestor(notification, Process.class);
            if (eo != null) {
                if (eo instanceof Process) {

                    affectedProcesses.add((Process) eo);
                }
            }

            if (isNotificationForRestfulActivity(notification)) {

                if (Xpdl2Package.eINSTANCE.getStartEvent_Trigger()
                        .equals(notification.getFeature())) {
                    /*
                     * XPD-7256: if the new Activity is a RESTful activity
                     * however if the trigger type was changed(i.e. from Start
                     * None to Start Message and vice versa; then remove the
                     * publish as rest config and the generated REST service.)
                     */
                    Activity affectedActivity =
                            isRESTActivityContentChange(notification);

                    if (affectedActivity != null) {
                        /*
                         * remove the rest flag and rest service only if the
                         * activity previously was a restful activity.
                         */
                        unsetRESTflag.add(affectedActivity);
                        removeREST.add(RestServiceUtil
                                .getRestService(affectedActivity));
                    }
                } else {

                    Activity affectedActivity = isAddActivity(notification);

                    if (affectedActivity != null) {
                        if (RestServiceUtil.isRESTfulActivity(affectedActivity)) {
                            addREST.add(affectedActivity);
                        }
                    } else {
                        Process affectedRestService =
                                isRemoveActivityOrUnsetRESTflag(notification);
                        if (affectedRestService != null) {
                            removeREST.add(affectedRestService);

                        } else {
                            affectedActivity =
                                    isRESTActivityContentChange(notification);
                            if (affectedActivity != null) {
                                removeREST.add(RestServiceUtil
                                        .getRestService(affectedActivity));
                                addREST.add(affectedActivity);
                            }
                        }
                    }
                }
            } else {

                /*
                 * If the REST flag is set for an activity other than start
                 * none/catch message start/intermediate event and receive task,
                 * we need to unset the plublishAsRESTservice flag and remove
                 * the REST service. Also if the REST flag is on start none
                 * event which is not in business process(user performs convert
                 * to pageflow/service process) then unset the
                 * plublishAsRESTservice flag and remove the REST service
                 */

                Activity affectedActivity =
                        isRESTActivityContentChange(notification);

                if (affectedActivity != null) {

                    unsetRESTflag.add(affectedActivity);
                    removeREST.add(RestServiceUtil
                            .getRestService(affectedActivity));
                }
            }
        }

        /*
         * Handle things outside activity affecting REST service i.e., formal
         * parameters and process name changes
         */
        Set<Activity> affectedActivities =
                changeAffectsRESTServices(notifications);
        if (!affectedActivities.isEmpty()) {
            for (Activity activity : affectedActivities) {
                removeREST.add(RestServiceUtil.getRestService(activity));
            }
            addREST.addAll(affectedActivities);
        }

        /*
         * If processes are added by copy-paste and having RESTful activities,
         * we need to remove all existing rest services and regenerate
         */
        Set<Process> processes = checkProcessAddNotification(notifications);

        for (Process process : processes) {

            if (process != null) {

                removeREST.addAll(RestServiceUtil.getRestServices(process));

                addREST.addAll(getRestActivitiesInProcess(process));
            }
        }

        /*
         * Check that the generated REST services are in correct state, i.e., if
         * its generated for a request activity, it should have a send task
         * otherwise a service task.
         */

        if (!affectedProcesses.isEmpty()) {

            Set<Activity> affectedRestActivities =
                    checkRESTserviceTaskType(affectedProcesses);

            if (!affectedRestActivities.isEmpty()) {

                for (Activity activity : affectedRestActivities) {

                    removeREST.add(RestServiceUtil.getRestService(activity));
                }

                addREST.addAll(affectedRestActivities);
            }
        }

        CompoundCommand cmd = new CompoundCommand();

        if (!unsetRESTflag.isEmpty()) {
            unsetRESTflagCommand(ed, unsetRESTflag, cmd);

            /*
             * Sid XPD-8197 because of the way that the checking of
             * notifications is done above it can mean that we attempt to re-add
             * a REST service that was removed even though it has been tagged to
             * have it's PublishAsRESTServie attribute unset
             * 
             * So we remove any process that's been marked as remove service.
             * That guarantees that we don't get spurious RESTService entries
             * lying around for things not marked as publish as service
             */
            for (Activity activity : unsetRESTflag) {
                addREST.remove(activity);
            }
        }

        if (!removeREST.isEmpty()) {
            removeRESTserviceCommand(ed, removeREST, cmd);
        }

        if (!addREST.isEmpty()) {
            addRESTserviceCommand(ed, addREST, cmd);
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Checks that the generated REST services in the given processes have
     * correct task type, i.e., a rest service should have a send task for the
     * generating request activity and a service task for the generating
     * request-reply activity
     * 
     * @param affectedProcesses
     * @return List of activities with incorrect task type
     */
    private Set<Activity> checkRESTserviceTaskType(
            Set<Process> affectedProcesses) {

        Set<Activity> restfulActivities = new HashSet<Activity>();

        Set<Activity> affectedActivities = new HashSet<Activity>();

        // Get all the RESTful activities
        for (Process process : affectedProcesses) {

            for (Activity activity : ReplyActivityUtil
                    .getAllIncomingRequestActivities(process)) {

                if (RestServiceUtil.isRESTfulActivity(activity)) {
                    restfulActivities.add(activity);
                }
            }
        }

        // Check if the RESTful activities have correct task type

        for (Activity activity : restfulActivities) {

            Process restService = RestServiceUtil.getRestService(activity);

            if (restService != null) {

                if (ReplyActivityUtil.isRequestActivityWithReply(activity)) {
                    /*
                     * its a request-reply activity, its REST service should
                     * have a service task activity
                     */
                    for (Activity restServiceActivity : restService
                            .getActivities()) {

                        if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(restServiceActivity))
                                || TaskType.SUBPROCESS_LITERAL
                                        .equals(TaskObjectUtil
                                                .getTaskTypeStrict(restServiceActivity))) {
                            // incorrect task type found
                            affectedActivities.add(activity);

                        }
                    }
                } else if (ReplyActivityUtil
                        .isIncomingRequestActivity(activity)) {
                    /*
                     * if its a request-only activity, its REST service should
                     * have a send task activity
                     */
                    for (Activity restServiceActivity : restService
                            .getActivities()) {

                        if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(restServiceActivity))
                                || TaskType.SUBPROCESS_LITERAL
                                        .equals(TaskObjectUtil
                                                .getTaskTypeStrict(restServiceActivity))) {
                            // incorrect task type found
                            affectedActivities.add(activity);

                        }
                    }

                } else if (isStartTypeNoneEvent(activity)) {
                    /*
                     * if its a start none event, its REST service should have a
                     * sub process call activity
                     */
                    for (Activity restServiceActivity : restService
                            .getActivities()) {

                        if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(restServiceActivity))
                                || TaskType.SEND_LITERAL
                                        .equals(TaskObjectUtil
                                                .getTaskTypeStrict(restServiceActivity))) {
                            // incorrect task type found
                            affectedActivities.add(activity);

                        }
                    }
                }
            }
        }

        return affectedActivities;
    }

    /**
     * Appends unset publish as REST service flag commands for the given
     * activities
     * 
     * @param ed
     * @param activities
     * @param cmd
     */

    private void unsetRESTflagCommand(EditingDomain ed,
            Set<Activity> activities, CompoundCommand cmd) {

        if (!activities.isEmpty()) {

            LateExecuteCompoundCommand lateCmd =
                    new LateExecuteCompoundCommand();

            for (Activity activity : activities) {

                unsetRESTflagCommand(ed, activity, lateCmd);
            }

            if (!lateCmd.isEmpty()) {
                cmd.append(lateCmd);
            }
        }

    }

    /**
     * Appends unset publish as REST service flag command for the given
     * activities
     * 
     * @param ed
     * @param activity
     * @param lateCmd
     */
    private void unsetRESTflagCommand(EditingDomain ed, Activity activity,
            LateExecuteCompoundCommand lateCmd) {

        if (activity != null) {

            lateCmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PublishAsRestService(),
                    null));
        }

    }

    /**
     * Check if its a notification for an activity that can be RESTful
     * 
     * @param notification
     * @return <code>true</code> if the activity is a RESTful activity else
     *         return <code>false</code>
     */
    private boolean isNotificationForRestfulActivity(
            ENotificationImpl notification) {

        Activity affectedActivity = isActivityContentChange(notification);

        if (affectedActivity == null) {

            affectedActivity = isRemoveActivity(notification);
        }

        if (affectedActivity == null) {

            affectedActivity = isAddActivity(notification);
        }
        /*
         * Only Start None Events in Business Process can be configured as REST
         * Activities.
         */
        return (affectedActivity != null)
                && (ReplyActivityUtil
                        .isIncomingRequestActivity(affectedActivity) || (isStartTypeNoneEvent(affectedActivity) && Xpdl2ModelUtil
                        .isBusinessProcess(affectedActivity.getProcess())));

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
     * Returns the affected REST service if its a remove activity or unset REST
     * flag notification, returns null otherwise
     * 
     * @param notification
     * @return affectedRestService
     */
    private Process isRemoveActivityOrUnsetRESTflag(
            ENotificationImpl notification) {

        Process affectedRestService;

        affectedRestService = isRemoveRESTServiceForActivity(notification);

        if (affectedRestService != null) {

            return affectedRestService;

        } else if (isUnSetRESTserviceFlagNotification(notification)) {

            if (notification.getNotifier() instanceof Activity) {

                Activity activity = (Activity) notification.getNotifier();
                return RestServiceUtil.getRestService(activity);
            }
        }
        return null;
    }

    /**
     * Returns the affected REST service if its a remove activity notification
     * 
     * @param notification
     * @return restService
     */

    private Process isRemoveRESTServiceForActivity(Notification notification) {
        if (Notification.REMOVE == notification.getEventType()
                && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(notification.getFeature())) {
            if (notification.getOldValue() instanceof Activity
                    && notification.getNotifier() instanceof EObject) {

                /*
                 * Activity already removed from model hence must get parent
                 * process from notifier (Process or ActivitySet)
                 */

                Process process =
                        (Process) Xpdl2ModelUtil
                                .getAncestor((EObject) notification
                                        .getNotifier(), Process.class);

                Activity activity = (Activity) notification.getOldValue();

                Process restService =
                        RestServiceUtil.getRestService(activity.getId(),
                                process);

                return restService;
            }
        }
        return null;
    }

    /**
     * Check if its a RESTful activity's content change notification
     * 
     * @param notification
     * @return affectedActivity
     */
    private Activity isRESTActivityContentChange(ENotificationImpl notification) {

        Activity affectedActivity = isActivityContentChange(notification);

        if (RestServiceUtil.isRESTfulActivity(affectedActivity)) {
            return affectedActivity;
        }
        return null;
    }

    /**
     * Returns true if its a name change notification, false otherwise
     * 
     * @param notification
     * @return boolean
     */
    private boolean isNameChangeNotification(ENotificationImpl notification) {

        if (notification.getFeature() instanceof EAttributeImpl) {

            EAttributeImpl ea = (EAttributeImpl) notification.getFeature();

            if (Xpdl2Package.eINSTANCE.getNamedElement_Name().equals(ea)
                    || XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName().equals(ea)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether there is any notification to add process(es)
     * 
     * @param notification
     * @return list of added processes or <code>null</code>
     */
    @SuppressWarnings("unchecked")
    private Set<Process> checkProcessAddNotification(
            Collection<ENotificationImpl> notifications) {

        Set<Process> processes = new HashSet<Process>();

        for (ENotificationImpl notification : notifications) {

            if (notification.getNotifier() instanceof Package
                    && notification.getEventType() == Notification.ADD
                    && notification.getNewValue() instanceof Process
                    && notification.getOldValue() == null) {

                // notification to add one process
                processes.add((Process) notification.getNewValue());

            } else if (notification.getNotifier() instanceof Package
                    && notification.getEventType() == Notification.ADD_MANY
                    && notification.getFeature() == Xpdl2Package.eINSTANCE
                            .getPackage_Processes()
                    && notification.getOldValue() == null) {

                // notification to add multiple processes
                Object addedProcesses = notification.getNewValue();

                if (addedProcesses != null && addedProcesses instanceof List<?>
                        && ((List<Process>) addedProcesses).size() > 0) {

                    processes
                            .addAll((Collection<? extends Process>) addedProcesses);

                }
            }

        }
        return processes;
    }

    /**
     * Returns set of affected activities due to formal parameter change or
     * process name changes
     * 
     * @param notification
     * @return affectedActivities
     */
    private Set<Activity> changeAffectsRESTServices(
            Collection<ENotificationImpl> notifications) {

        Set<Activity> affectedActivities = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {

            EObject eo = getTypedAncestor(notification, FormalParameter.class);
            /*
             * if formal parameter change notification (regardless of its
             * association to the RESTful activity), add all RESTful activities
             * of the containing process to the affected list
             */
            if (eo != null) {
                if (eo instanceof FormalParameter) {
                    FormalParameter fp = (FormalParameter) eo;

                    if (fp.eContainer() instanceof Process) {
                        affectedActivities
                                .addAll(getRestActivitiesInProcess((Process) fp
                                        .eContainer()));
                    } else if (fp.eContainer() instanceof ProcessInterface) {

                        /*
                         * We only deal with parameters of a process interface
                         * being used by the processes in the same package. For
                         * the rest, there is a validation rule that checks the
                         * REST service parameters are consistent with RESTful
                         * activity's associated parameters
                         */

                        ProcessInterface pi =
                                (ProcessInterface) fp.eContainer();

                        affectedActivities
                                .addAll(getAffectedRestActivitiesByProcessInterfaceChange(pi));

                    }
                }
            } else if (notification.getNotifier() instanceof Process) {

                /*
                 * If process name changes or formal param is added/removed, add
                 * affected RESTful activities to the list
                 */
                if (isNameChangeNotification(notification)
                        || isFormalParamAddRemoveNotification(notification)) {
                    affectedActivities
                            .addAll(getRestActivitiesInProcess((Process) notification
                                    .getNotifier()));
                }
            } else if (notification.getNotifier() instanceof ProcessInterface) {

                /*
                 * If process interface's formal parameter is added/removed, add
                 * affected RESTful activities to the list
                 */
                if (isFormalParamAddRemoveNotification(notification)) {
                    ProcessInterface pi =
                            (ProcessInterface) notification.getNotifier();

                    affectedActivities
                            .addAll(getAffectedRestActivitiesByProcessInterfaceChange(pi));
                }
            }
        }
        return affectedActivities;
    }

    /**
     * @param pi
     * 
     * @return RESTful activities of the process in the same package using the
     *         given process interface
     * 
     */
    private Collection<? extends Activity> getAffectedRestActivitiesByProcessInterfaceChange(
            ProcessInterface pi) {

        Set<Activity> affectedActivities = new HashSet<Activity>();

        if (pi != null) {

            Package pckg = Xpdl2ModelUtil.getPackage(pi);

            if (pckg != null) {

                for (Process process : pckg.getProcesses()) {

                    ProcessInterface implementedPi =
                            ProcessInterfaceUtil
                                    .getImplementedProcessInterface(process);

                    if (implementedPi != null) {

                        if (implementedPi.equals(pi)) {

                            affectedActivities
                                    .addAll(getRestActivitiesInProcess(process));
                        }
                    }

                }
            }
        }
        return affectedActivities;
    }

    /**
     * Check if its a formal parameter add/remove notification
     * 
     * @param notification
     * @return true if its a formal parameter add/remove notification, false
     *         otherwise
     */
    private boolean isFormalParamAddRemoveNotification(
            ENotificationImpl notification) {
        int notificationType = notification.getEventType();
        if ((Notification.ADD == notificationType || Notification.REMOVE == notificationType)
                && notification.getFeature() == Xpdl2Package.eINSTANCE
                        .getFormalParametersContainer_FormalParameters()) {
            return true;
        }
        return false;
    }

    /**
     * Returns RESTful activities in the given process
     * 
     * @param process
     * @return restActivities
     */
    private Set<Activity> getRestActivitiesInProcess(Process process) {

        Set<Activity> restActivities = new HashSet<Activity>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            if (RestServiceUtil.isRESTfulActivity(activity)) {
                restActivities.add(activity);
            }
        }
        return restActivities;
    }

    /**
     * Appends remove REST service commands for the given rest services to cmd
     * 
     * @param ed
     * @param restServices
     * @param cmd
     * 
     */
    private void removeRESTserviceCommand(EditingDomain ed,
            Set<Process> restServices, CompoundCommand cmd) {

        if (!restServices.isEmpty()) {

            LateExecuteCompoundCommand lateCmd =
                    new LateExecuteCompoundCommand();

            for (Process restService : restServices) {

                removeRESTserviceCommand(ed, restService, lateCmd);
            }

            if (!lateCmd.isEmpty()) {
                cmd.append(lateCmd);
            }
        }
    }

    /**
     * Appends remove REST service command for the given rest service to the
     * passed cmd
     * 
     * @param ed
     * @param restService
     * @param cmd
     */
    private void removeRESTserviceCommand(EditingDomain ed,
            Process restService, CompoundCommand cmd) {

        if (restService != null) {

            cmd.append(RemoveCommand.create(ed,
                    restService.eContainer(),
                    XpdExtensionPackage.eINSTANCE.getRESTServices(),
                    restService));
        }
    }

    /**
     * Appends add REST service commands for the given activities to cmd
     * 
     * @param ed
     * @param activities
     * @patam cmd
     */
    private void addRESTserviceCommand(EditingDomain ed,
            Set<Activity> activities, CompoundCommand cmd) {

        if (!activities.isEmpty()) {

            for (Activity activity : activities) {

                AbstractLateExecuteCommand abstractLateExecuteCmd =
                        new AbstractLateExecuteCommand(ed, activity) {

                            @Override
                            protected Command createCommand(
                                    EditingDomain editingDomain,
                                    Object contextObject) {

                                Activity activity = (Activity) contextObject;

                                CompoundCommand compCmd = new CompoundCommand();

                                Process restService =
                                        RESTServiceFactory.INSTANCE
                                                .createRestService(activity);
                                /*
                                 * restService will be null if the REST Service
                                 * couldn't be created
                                 */
                                if (restService != null) {

                                    RESTServices restServices =
                                            (RESTServices) Xpdl2ModelUtil
                                                    .getOtherElement(activity
                                                            .getProcess(),
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_RESTServices());

                                    /*
                                     * If REST Services container doesn't exist,
                                     * create and add the REST service to it,
                                     * otherwise simply add the REST service to
                                     * existing container
                                     */
                                    if (restServices == null) {
                                        restServices =
                                                XpdExtensionFactory.eINSTANCE
                                                        .createRESTServices();
                                        restServices.getRESTServices()
                                                .add(restService);
                                        compCmd.append(Xpdl2ModelUtil
                                                .getSetOtherElementCommand(editingDomain,
                                                        activity.getProcess(),
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_RESTServices(),
                                                        restServices));
                                    } else {
                                        compCmd.append(AddCommand
                                                .create(editingDomain,
                                                        restServices,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getRESTServices(),
                                                        restService));
                                    }
                                    return compCmd;
                                }
                                return null;
                            }
                        };

                cmd.append(abstractLateExecuteCmd);
            }
        }
    }

    /**
     * Check if its a notification for unsetting the REST service flag
     * 
     * @param notification
     * @return true if its a notification for unsetting the REST service flag,
     *         false otherwise
     */
    private boolean isUnSetRESTserviceFlagNotification(
            ENotificationImpl notification) {

        if (notification.getEventType() == Notification.SET
                && notification.getNewValue() instanceof Boolean
                && notification.getFeature() instanceof EAttributeImpl) {

            EAttributeImpl ea = (EAttributeImpl) notification.getFeature();
            if (XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_PublishAsRestService().equals(ea)) {

                if (notification.getNewValue().equals(false)) {
                    return true;
                }
            }
        }
        return false;
    }

}
