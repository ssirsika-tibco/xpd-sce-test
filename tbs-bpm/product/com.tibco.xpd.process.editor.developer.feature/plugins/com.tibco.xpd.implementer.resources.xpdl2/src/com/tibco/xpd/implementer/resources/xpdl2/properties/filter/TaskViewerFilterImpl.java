package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;

public class TaskViewerFilterImpl implements TaskViewerFilter {

    // private HasTypeFilter hasTypeFilter;

    @Override
    public synchronized IFilter isImplementedBy(ImplementationType i) {
        return new ImplementedByFilter(i);
    }

    class ImplementedByFilter implements IFilter {
        private final ImplementationType implType;

        public ImplementedByFilter(ImplementationType i) {
            this.implType = i;
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Implementation implementation = activity.getImplementation();

                if (implementation instanceof Task) {
                    Task task = (Task) implementation;
                    result = filterTask(task);
                }
            }
            return result;
        }

        private boolean filterTask(Task task) {
            boolean result = false;
            result = filterTaskService(task);
            if (!result) {
                result = filterTaskSend(task);
            }
            if (!result) {
                result = filterTaskReceive(task);
            }
            return result;
        }

        private boolean filterTaskService(Task task) {
            boolean r;
            TaskService service = task.getTaskService();
            if (service != null) {
                ImplementationType implementationType =
                        service.getImplementation();
                if (implType.equals(implementationType)) {
                    r = true;
                } else {
                    r = false;
                }
            } else {
                r = false;
            }
            return r;
        }

        private boolean filterTaskReceive(Task task) {
            boolean r;
            TaskReceive service = task.getTaskReceive();
            if (service != null) {
                ImplementationType implementationType =
                        service.getImplementation();
                if (implType.equals(implementationType)) {
                    r = true;
                } else {
                    r = false;
                }
            } else {
                r = false;
            }
            return r;
        }

        private boolean filterTaskSend(Task task) {
            boolean r;
            TaskSend service = task.getTaskSend();
            if (service != null) {
                ImplementationType implementationType =
                        service.getImplementation();
                if (implType.equals(implementationType)) {
                    r = true;
                } else {
                    r = false;
                }
            } else {
                r = false;
            }
            return r;
        }
    }

    @Override
    public IFilter hasType(TaskType... taskType) {
        return new HasTypeFilter(taskType);
    }

    class HasTypeFilter implements IFilter {

        private final Collection<TaskType> taskTypes;

        public HasTypeFilter(TaskType... taskTypes) {
            this.taskTypes = Arrays.asList(taskTypes);
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Implementation implementation = activity.getImplementation();

                if (implementation instanceof Task) {
                    Task task = (Task) implementation;
                    TaskType type = recogniseTask(task);
                    result = (type == null) ? false : taskTypes.contains(type);
                }
            }

            return result;
        }

        private TaskType recogniseTask(Task task) {
            TaskType result;

            if (task.getTaskReceive() != null) {
                result = TaskType.RECEIVE_LITERAL;
            } else if (task.getTaskSend() != null) {
                result = TaskType.SEND_LITERAL;
            } else if (task.getTaskService() != null) {
                result = TaskType.SERVICE_LITERAL;
            } else {
                result = null;
            }
            return result;
        }

    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter#hasScriptGrammar(java.lang.String[])
     * @param direction
     * @param grammarType
     * 
     * @return
     */
    @Override
    public IFilter hasScriptGrammar(DirectionType direction,
            String... grammarType) {
        return new HasScriptGrammarFilter(direction, grammarType);
    }

    /**
     * Filter to test the Grammar type
     * 
     * @author ssirsika
     * @since 21-Jan-2016
     */
    class HasScriptGrammarFilter implements IFilter {

        private Collection<String> types;

        private DirectionType direction;

        /**
         * @param direction
         * @param grammarType
         */
        public HasScriptGrammarFilter(DirectionType direction,
                String... grammarType) {
            this.direction = direction;
            types = Arrays.asList(grammarType);
        }

        /**
         * Return 'true' if 'grammarType' contains grammar set in the activity.
         * 
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {

            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo instanceof Activity) {
                return types.contains(ScriptGrammarFactory
                        .getGrammarToUse((Activity) eo, direction));
            }

            return false;
        }

    }
}
