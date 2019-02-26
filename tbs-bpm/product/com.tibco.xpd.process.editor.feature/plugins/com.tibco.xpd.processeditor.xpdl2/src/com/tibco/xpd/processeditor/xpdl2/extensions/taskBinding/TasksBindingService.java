package com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskConfigType;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationService;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * <p>
 * Note that the extension point was originally in processwidget but has been
 * deprecated and moved here to processeditor. We still support contributions to
 * the deprecated processwidget extension point so must handle both.
 * 
 * @author aallway
 * 
 */
public class TasksBindingService {

    private static final String ELEMENT_RESTRICT_TO_TYPE = "taskTypeContext"; //$NON-NLS-1$

    private static final String ATT_TYPE = "type"; //$NON-NLS-1$

    private static final String ATT_ID = "taskImplementationId"; //$NON-NLS-1$

    private static final String ELEMENT_TASK_IMPLEMENTATION =
            "taskImplementation"; //$NON-NLS-1$

    private static final String ELEMENT_DESTINATION_ID = "destinationId"; //$NON-NLS-1$

    public static final TasksBindingService INSTANCE =
            new TasksBindingService();

    private static final String TASK_BINDING_EXTENSIONPOINT = "taskBinding"; //$NON-NLS-1$

    private Bindings bindings;

    private final Logger log =
            LoggerFactory.createLogger(Xpdl2ProcessEditorPlugin.ID);

    /**
     * List with tasks always valid, no matter what binding defines.
     */
    private final List<String> alwaysValid;

    private TasksBindingService() {
        alwaysValid = new ArrayList<String>();
        alwaysValid.add("Unspecified"); //$NON-NLS-1$
    }

    public synchronized Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
            try {
                loadBindings();
            } catch (NullPointerException ex) {
                log.error(ex);
            } catch (Exception ex) {
                log.error(ex);
                throw new RuntimeException(ex);
            }
        }
        return bindings;
    }

    private void loadBindings() {
        //
        // Support older contributions to deprecated processwidget extension
        // point for task bindings.
        //
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ProcessWidgetPlugin.ID,
                                TASK_BINDING_EXTENSIONPOINT);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (IExtension ext : extensions) {
                IConfigurationElement[] elements =
                        ext.getConfigurationElements();
                for (IConfigurationElement bindingElement : elements) {
                    TaskBinding binding = createBinding(bindingElement);
                    bindings.add(binding);
                }
            }
        }

        //
        // Now load the contributions to new processeditor task binding
        // extension point
        //
        point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                TASK_BINDING_EXTENSIONPOINT);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (IExtension ext : extensions) {
                IConfigurationElement[] elements =
                        ext.getConfigurationElements();
                for (IConfigurationElement bindingElement : elements) {
                    TaskBinding binding = createBinding(bindingElement);
                    bindings.add(binding);
                }
            }
        }
    }

    private TaskBinding createBinding(IConfigurationElement bindingElement) {
        TaskBinding result;
        String destination =
                bindingElement.getAttribute(ELEMENT_DESTINATION_ID);
        if (destination == null) {
            throw new NullPointerException("Destination id is null."); //$NON-NLS-1$
        }
        Set<TaskReference> tasks = new HashSet<TaskReference>();
        for (IConfigurationElement taskImplElement : bindingElement
                .getChildren(ELEMENT_TASK_IMPLEMENTATION)) {
            String taskId = taskImplElement.getAttribute(ATT_ID);
            if (taskId == null) {
                log
                        .error("Task binding element for destination \"" + destination + "\" don't have task id attribute: " + ATT_ID); //$NON-NLS-1$ //$NON-NLS-2$
                continue;
            }
            IConfigurationElement[] taskTypes =
                    taskImplElement.getChildren(ELEMENT_RESTRICT_TO_TYPE);
            addTasks(taskId, taskTypes, destination, tasks);

            /*
             * Sid ACE-118 - shouldn't bneed any WebService bindings any more).
             */

            // // This little workaround here
            // // because in old days we had inconsistent
            // // task identifiers for web service implementation.
            // // Correct is: WebService
            // // Incorrect is: Web Service
            // // We have to deal with that for backward compatibility
            // if (taskId.equals("WebService")) { //$NON-NLS-1$
            // // addTasks("Web Service",taskTypes,destination,tasks);
            // // //$NON-NLS-1$
            // Set<TaskType> types = getTypes(taskId, taskTypes);
            // validate(destination, taskId, types);
            // TaskReference t = new TaskReferenceImpl("Web Service", types);
            // //$NON-NLS-1$
            // tasks.add(t);
            // }
        }
        ;

        addDefaults(tasks);
        result = new TaskBinding(destination, tasks);

        return result;
    }

    private void addTasks(String taskId, IConfigurationElement[] taskTypes,
            String destination, Set<TaskReference> tasks) {
        Set<TaskType> types = getTypes(taskId, taskTypes);
        validate(destination, taskId, types);
        TaskReference t = new TaskReferenceImpl(taskId, types);
        tasks.add(t);
    }

    private void addDefaults(Set<TaskReference> tasks) {
        for (String taskId : alwaysValid) {
            if (!tasks.contains(taskId)) {
                Set<TaskType> definedTypes =
                        TaskImplementationService.INSTANCE.getImplementations()
                                .getTaskTypes(taskId);
                TaskReference tr = new TaskReferenceImpl(taskId, definedTypes);
                tasks.add(tr);
            }
        }

    }

    private void validate(String destination, String taskId, Set<TaskType> types) {
        Set<TaskType> definedTypes =
                TaskImplementationService.INSTANCE.getImplementations()
                        .getTaskTypes(taskId);
        if (definedTypes == null) {
            log
                    .error("Task binding for destination \"" + destination + "\" refers to not existing task id: " + taskId); //$NON-NLS-1$ //$NON-NLS-2$
            types.clear();
        } else {
            for (Iterator<TaskType> iter = types.iterator(); iter.hasNext();) {
                TaskType next = iter.next();
                boolean isBound = definedTypes.contains(next);
                boolean isAlwaysValid = alwaysValid.contains(taskId);
                if (!isBound || isAlwaysValid) {
                    iter.remove();
                }
            }
        }
    }

    private Set<TaskType> getTypes(String taskId,
            IConfigurationElement[] taskTypes) {
        Set<TaskType> result = new HashSet<TaskType>();
        // read defined types from task implementation extension point
        Set<TaskType> definedTypes =
                TaskImplementationService.INSTANCE.getImplementations()
                        .getTaskTypes(taskId);
        for (IConfigurationElement element : taskTypes) {
            String typeName = element.getAttribute(ATT_TYPE);
            TaskType taskType;
            if (typeName.equals(TaskConfigType.TYPE_SERVICE)) {
                taskType = TaskType.SERVICE_LITERAL;
            } else if (typeName.equals(TaskConfigType.TYPE_SEND)) {
                taskType = TaskType.SEND_LITERAL;
            } else if (typeName.equals(TaskConfigType.TYPE_RECEIVE)) {
                taskType = TaskType.RECEIVE_LITERAL;
            } else if (typeName.equals(TaskConfigType.TYPE_USER)) {
                taskType = TaskType.USER_LITERAL;
            } else if (typeName.equals(TaskConfigType.TYPE_SCRIPT)) {
                taskType = TaskType.SCRIPT_LITERAL;
            } else {
                log
                        .warn("Unsupported task type \"" + typeName + "\". Ignoring."); //$NON-NLS-1$ //$NON-NLS-2$
                continue;
            }
            if (definedTypes.contains(taskType)) {
                result.add(taskType);
            } else {
                log
                        .warn("Binding refers to not existing task implementation. Expected task type \"" + typeName + "\" for task \"" + taskId + "\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
        }
        return result;
    }
}
