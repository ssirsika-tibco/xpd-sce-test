/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;

/**
 * Singleton factory for Activity Message Providers. Assumes that they are all
 * stateless.
 * 
 * @author scrossle
 * 
 */
public final class ActivityMessageProviderFactory {

    public static final ActivityMessageProviderFactory INSTANCE =
            new ActivityMessageProviderFactory();

    ActivityMessageProvider eventAdapter;

    ActivityMessageProvider eventBWAdapter;

    ActivityMessageProvider taskServiceAdapter;

    ActivityMessageProvider taskSendAdapter;

    ActivityMessageProvider taskReceiveAdapter;

    ActivityMessageProvider taskBWServiceAdapter;

    ActivityMessageProvider catchWsdlErrorEventAdapter;

    ActivityMessageProvider throwWsdlErrorEventAdapter;

    ActivityMessageProvider taskSendInvokeBusinessProcessAdapter;

    /**
     * Hide constructor for singleton.
     */
    private ActivityMessageProviderFactory() {
    }

    /**
     * Get an adapter for messages from the activity, or null if not applicable
     * for the activity.
     * 
     * @param activity
     * @return
     */
    public ActivityMessageProvider getMessageProvider(Activity activity,
            boolean isBWService) {

        if (activity == null) {
            return null;
        }

        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            /*
             * XPD-7539 Message Events can be REST sdervce as well as
             * WebService; only want to deal with WebService
             */
            if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {
                if (isBWService) {
                    return getEventBWAdapter();
                } else {
                    return getEventAdapter();
                }
            }

        } else if (activity.getEvent() instanceof IntermediateEvent
                && activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            return getCatchWsdlErrorEventAdapter();

        } else if (activity.getEvent() instanceof EndEvent
                && activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            return getThrowFaultMessageErrorEventAdapter();

        } else if (activity.getImplementation() != null
                && activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (task.getTaskService() != null
                    && ImplementationType.WEB_SERVICE_LITERAL.equals(task
                            .getTaskService().getImplementation())) {
                /*
                 * XPD-1778: If the user drags & drops a WSDL operation from
                 * project explorer on the service task then Studio should not
                 * try to guess the implementation type to BW or WebService. It
                 * should always treat it as WebService.
                 */
                return getTaskServiceAdapter();
            } else if (task.getTaskSend() != null) {
                /*
                 * XPD-288: if the xpdExt:ImplementationType is
                 * InvokeBusinessProcess then return the corresponding adapter
                 */
                if (WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(task
                                .getTaskSend())) {
                    return getTaskSendInvokeBusinessProcessAdapter();
                }
                return getTaskSendAdapter();
            } else if (task.getTaskReceive() != null) {
                return getTaskReceiveAdapter();
            }
        }
        return null;
    }

    public ActivityMessageProvider getMessageProvider(Activity activity) {
        return getMessageProvider(activity, false);
    }

    private ActivityMessageProvider getTaskBWServiceAdapter() {
        if (taskBWServiceAdapter == null) {
            taskBWServiceAdapter = new TaskBWServiceMessageAdapter();
        }
        return taskBWServiceAdapter;
    }

    /**
     * @return the eventAdapter
     */
    protected ActivityMessageProvider getEventAdapter() {
        if (eventAdapter == null) {
            eventAdapter = new EventMessageAdapter();
        }
        return eventAdapter;
    }

    /**
     * @return the eventAdapter
     */
    protected ActivityMessageProvider getEventBWAdapter() {
        if (eventBWAdapter == null) {
            eventBWAdapter = new EventBWMessageAdapter();
        }
        return eventBWAdapter;
    }

    /**
     * @return the taskReceiveAdapter
     */
    protected ActivityMessageProvider getTaskReceiveAdapter() {
        if (taskReceiveAdapter == null) {
            taskReceiveAdapter = new TaskReceiveMessageAdapter();
        }
        return taskReceiveAdapter;
    }

    /**
     * @return the taskSendAdapter
     */
    protected ActivityMessageProvider getTaskSendAdapter() {
        if (taskSendAdapter == null) {
            taskSendAdapter = new TaskSendMessageAdapter();
        }
        return taskSendAdapter;
    }

    /**
     * @return the taskSendInvokeBusinessProcessAdapter
     */
    public ActivityMessageProvider getTaskSendInvokeBusinessProcessAdapter() {
        if (null == taskSendInvokeBusinessProcessAdapter) {
            taskSendInvokeBusinessProcessAdapter =
                    new TaskSendInvokeBusinessProcessAdapter();
        }
        return taskSendInvokeBusinessProcessAdapter;
    }

    /**
     * @return the taskServiceAdapter
     */
    protected ActivityMessageProvider getTaskServiceAdapter() {
        if (taskServiceAdapter == null) {
            taskServiceAdapter = new TaskServiceMessageAdapter();
        }
        return taskServiceAdapter;
    }

    /**
     * @return the catchWsdlErrorEventAdapter
     */
    protected ActivityMessageProvider getCatchWsdlErrorEventAdapter() {
        if (catchWsdlErrorEventAdapter == null) {
            catchWsdlErrorEventAdapter =
                    new CatchWsdlErrorEventMessageAdapter();
        }
        return catchWsdlErrorEventAdapter;
    }

    /**
     * @return the catchWsdlErrorEventAdapter
     */
    protected ActivityMessageProvider getThrowFaultMessageErrorEventAdapter() {
        if (throwWsdlErrorEventAdapter == null) {
            throwWsdlErrorEventAdapter =
                    new ThrowWsdlErrorEventMessageAdapter();
        }
        return throwWsdlErrorEventAdapter;
    }

    /**
     * Most ActivityMessageProvider's are for activities that REALLY are web
     * service activities.
     * <p>
     * Other message providers are for activities that are only INDIRECTLY
     * ActivityMessageProviders that don't invoke/receive/reply operations but
     * are related enough to need an activity message provider in order to do
     * some form of mapping to / from parts of a web-service (such ascatch fault
     * message eror event).
     * 
     * @return true if the activity is really directly a web service activity
     *         rather than indirectly.
     */
    public boolean isActualWebServiceActivity(Activity activity) {
        ActivityMessageProvider messageProvider = getMessageProvider(activity);
        if (messageProvider != null) {
            return messageProvider.isActualWebServiceActivity();
        }
        return false;
    }

}
