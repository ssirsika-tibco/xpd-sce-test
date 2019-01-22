package com.tibco.xpd.validation.xpdl2.rules;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.Bindings;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.TasksBindingService;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.xpdl2.Activator;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public abstract class BindingValidationRule extends ProcessValidationRule {

    private static final Logger log = LoggerFactory
            .createLogger(Activator.PLUGIN_ID);

    private Map<TaskType, Set<String>> tasks;

    private final String issueId;

    private final Map<TaskType, Set<String>> unsupportedImplementations;

    public BindingValidationRule(String issueId) {
        this.issueId = issueId;
        this.unsupportedImplementations = Collections.emptyMap();
    }

    /**
     * Use this constructor to explicit exclude some task implementation. e.g.
     * Unspecified is unsupported implementation in ipe destination.
     * 
     * @param issueId
     * @param unsupportedImplementations
     */
    public BindingValidationRule(String issueId,
            Map<TaskType, Set<String>> unsupportedImplementations) {
        this.issueId = issueId;
        this.unsupportedImplementations = unsupportedImplementations;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Destination currentDestination = getScope().getCurrentDestination();
        String destinationId = currentDestination.getId();
        log.debug("Current destination id: " + destinationId); //$NON-NLS-1$
        log.debug("Object class: " + process.getClass().getCanonicalName()); //$NON-NLS-1$

        Map<TaskType, Set<String>> taskBindings = readBindings(destinationId);

        for (Iterator<Activity> iter = activities.iterator(); iter.hasNext();) {
            Activity activity = iter.next();
            int ttype = recogniseTask(activity);
            boolean isValid = true;
            boolean ignore = true;
            switch (ttype) {
            case TaskType.MANUAL:
                ignore = true;
                break;
            case TaskType.RECEIVE: {
                Set<String> tasksAllowed;
                if (taskBindings.containsKey(TaskType.RECEIVE_LITERAL)) {
                    tasksAllowed = taskBindings.get(TaskType.RECEIVE_LITERAL);
                } else {
                    tasksAllowed = Collections.emptySet();
                }
                isValid = validateReceive(activity, tasksAllowed);
                ignore = false;
                break;
            }
            case TaskType.SCRIPT:
                ignore = true;
                break;
            case TaskType.SEND: {
                Set<String> tasksAllowed;
                if (taskBindings.containsKey(TaskType.SEND_LITERAL)) {
                    tasksAllowed = taskBindings.get(TaskType.SEND_LITERAL);
                } else {
                    tasksAllowed = Collections.emptySet();
                }
                isValid = validateSend(activity, tasksAllowed);
                ignore = false;
                break;
            }
            case TaskType.SERVICE: {
                Set<String> tasksAllowed;
                if (taskBindings.containsKey(TaskType.SERVICE_LITERAL)) {
                    tasksAllowed = taskBindings.get(TaskType.SERVICE_LITERAL);
                } else {
                    tasksAllowed = Collections.emptySet();
                }
                // MR 39888
                if (CapabilityUtil.isDeveloperActivityEnabled()) {
                    isValid = validateService(activity, tasksAllowed);
                    ignore = false;
                }
                break;
            }
            case TaskType.USER:
                ignore = true;
                break;
            default:
                ignore = true;
                break;
            }
            if (!ignore && !isValid) {
                addIssue(issueId, activity);
            }
        }
    }

    private synchronized Map<TaskType, Set<String>> readBindings(
            String destinationId) {
        Bindings bindings = TasksBindingService.INSTANCE.getBindings();
        tasks = bindings.getTasks(destinationId);
        for (Map.Entry<TaskType, Set<String>> entry : tasks.entrySet()) {
            TaskType key = entry.getKey();
            Set<String> value = entry.getValue();
            Set<String> unSupportedSet = unsupportedImplementations.get(key);
            if (unSupportedSet != null) {
                value.removeAll(unSupportedSet);
            }
        }
        return tasks;
    }

    protected boolean validateService(Activity activity,
            Set<String> tasksAllowed) {
        boolean result;
        Implementation implementation = activity.getImplementation();
        TaskService taskService = ((Task) implementation).getTaskService();
        String id =
                (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        if (id == null) {
            Task task = (Task) activity.getImplementation();
            id = task.getTaskService().getImplementation().getName();
        }
        boolean isValid = tasksAllowed.contains(id);
        result = (id == null || isValid);
        return result;
    }

    private boolean validateSend(Activity activity, Set<String> tasksAllowed) {
        boolean result;
        Implementation implementation = activity.getImplementation();
        TaskSend taskSend = ((Task) implementation).getTaskSend();
        String id =
                (String) Xpdl2ModelUtil.getOtherAttribute(taskSend,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        boolean isValid = tasksAllowed.contains(id);
        result = (id == null || isValid);
        return result;
    }

    private boolean validateReceive(Activity activity, Set<String> tasksAllowed) {
        boolean result;
        Implementation implementation = activity.getImplementation();
        TaskReceive taskReceive = ((Task) implementation).getTaskReceive();
        String id =
                (String) Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        boolean isValid = tasksAllowed.contains(id);
        result = (id == null || isValid);
        return result;
    }

    private int recogniseTask(Activity activity) {
        int result = -1;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task t = (Task) impl;
            if (t.getTaskManual() != null) {
                result = -1;
            } else if (t.getTaskReceive() != null) {
                result = TaskType.RECEIVE;
            } else if (t.getTaskScript() != null) {
                result = -1;
            } else if (t.getTaskSend() != null) {
                result = TaskType.SEND;
            } else if (t.getTaskService() != null) {
                result = TaskType.SERVICE;
            } else if (t.getTaskUser() != null) {
                result = -1;
            }
        }
        return result;
    }
}