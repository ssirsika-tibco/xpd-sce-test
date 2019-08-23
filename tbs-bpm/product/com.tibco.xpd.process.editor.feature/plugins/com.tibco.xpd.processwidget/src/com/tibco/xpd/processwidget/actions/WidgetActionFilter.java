/**
 * 
 */
package com.tibco.xpd.processwidget.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionFilter;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GraphicalColorAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.DataObjectEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.NoteEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author aallway
 * 
 */
public class WidgetActionFilter implements IActionFilter {

    public static final String IS_TASK_LIBRARY_EDITOR =
            "com.tibco.xpd.processwidget.IS_TASK_LIBRARY_EDITOR"; //$NON-NLS-1$

    public static final String IS_DECISION_FLOW_EDITOR =
            "com.tibco.xpd.processwidget.IS_DECISION_FLOW_EDITOR"; //$NON-NLS-1$

    public static final String TASK_OR_SUBPROC =
            "com.tibco.xpd.processwidget.TASK_OR_SUBPROC"; //$NON-NLS-1$

    public static final String TASK_OR_SUBPROC_TASK = "TASK"; //$NON-NLS-1$

    public static final String TASK_OR_SUBPROC_SUBPROCESS = "SUBPROCESS"; //$NON-NLS-1$

    public static final String SUPPORTS_FILL_COLOR =
            "com.tibco.xpd.processwidget.SUPPORTS_FILL_COLOR"; //$NON-NLS-1$

    public static final String SUPPORTS_LINE_COLOR =
            "com.tibco.xpd.processwidget.SUPPORTS_LINE_COLOR"; //$NON-NLS-1$

    public static final String IS_TOPLEVEL_EMBEDDED_SUBPROCESS =
            "com.tibco.xpd.processwidget.IS_TOPLEVEL_EMBEDDED_SUBPROCESS"; //$NON-NLS-1$

    public static final String IS_EMBEDDED_SUBPROCESS =
            "com.tibco.xpd.processwidget.IS_EMBEDDED_SUBPROCESS"; //$NON-NLS-1$

    public static final String IS_INDEPENDENT_SUBPROCESS =
            "com.tibco.xpd.processwidget.IS_INDEPENDENT_SUBPROCESS"; //$NON-NLS-1$

    public static final String IS_USER_TASK =
            "com.tibco.xpd.processwidget.IS_USER_TASK"; //$NON-NLS-1$

    public static final String IS_BUSINESS_PROCESS_USER_TASK =
            "com.tibco.xpd.processwidget.IS_BUSINESS_PROCESS_USER_TASK"; //$NON-NLS-1$

    public static final String IS_MANUAL_TASK =
            "com.tibco.xpd.processwidget.IS_MANUAL_TASK"; //$NON-NLS-1$

    public static final String ANSWER_TRUE = "TRUE"; //$NON-NLS-1$

    public static final String ANSWER_FALSE = "FALSE"; //$NON-NLS-1$

    public static final String EVENT_FLOW_TYPE =
            "com.tibco.xpd.processwidget.EVENT_FLOW_TYPE"; //$NON-NLS-1$

    public static final String IS_BUSINESS_PROCESS_START_MESSAGE_EVENT =
            "com.tibco.xpd.processwidget.IS_BUSINESS_PROCESS_START_MESSAGE_EVENT"; //$NON-NLS-1$

    public static final String IS_BUSINESS_PROCESS_REQUEST_ACTIVITY =
            "com.tibco.xpd.processwidget.IS_BUSINESS_PROCESS_REQUEST_ACTIVITY"; //$NON-NLS-1$

    public static final String IS_START_TYPE_NONE =
            "com.tibco.xpd.processwidget.IS_START_TYPE_NONE"; //$NON-NLS-1$

    public static final String IS_START_TYPE_NONE_DIRECTLY_UNDER_PROCESS =
            "com.tibco.xpd.processwidget.IS_START_TYPE_NONE_DIRECTLY_UNDER_PROCESS"; //$NON-NLS-1$

    public static final String EVENT_FLOW_START = "START"; //$NON-NLS-1$

    public static final String EVENT_FLOW_INTERMEDIATE = "INTERMEDIATE"; //$NON-NLS-1$

    public static final String EVENT_FLOW_END = "END"; //$NON-NLS-1$

    public static final String IS_PAGEFLOW =
            "com.tibco.xpd.processwidget.IS_PAGEFLOW"; //$NON-NLS-1$

    public static final String IS_SERVICEPROCESS =
            "`"; //$NON-NLS-1$

    public static final String IS_CASE_SERVICE =
            "com.tibco.xpd.processwidget.IS_CASE_SERVICE"; //$NON-NLS-1$

    public static final String IS_WM_FEATURE_AVAILABLE =
            "com.tibco.xpd.processwidget.IS_WM_FEATURE_AVAILABLE"; //$NON-NLS-1$

    public static final String IS_BUSINESS_SERVICE =
            "com.tibco.xpd.processwidget.IS_BUSINESS_SERVICE"; //$NON-NLS-1$

    public static final String IS_BUSINESS_PROCESS =
            "com.tibco.xpd.processwidget.IS_BUSINESS_PROCESS"; //$NON-NLS-1$

    public static final String IS_RCP_APPLICATION =
            "com.tibco.xpd.processwidget.IS_RCP_APPLICATION"; //$NON-NLS-1$

    public static final String SERVICE_TASK_IMPLEMENTATION_TYPE =
            "com.tibco.xpd.processwidget.SERVICE_TASK_IMPLEMENTATION_TYPE"; //$NON-NLS-1$

    public static final String IS_EVENT_HANDLER_ACTIVITY =
            "com.tibco.xpd.processwidget.IS_EVENT_HANDLER_ACTIVITY"; //$NON-NLS-1$
    
    public static final String IS_READ_ONLY =
            "com.tibco.xpd.processwidget.IS_READ_ONLY"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionFilter#testAttribute(java.lang.Object,
     * java.lang.String, java.lang.String)
     */
    @Override
    public boolean testAttribute(Object target, String name, String value) {
        if (IS_TASK_LIBRARY_EDITOR.equals(name)) {
            boolean isTaskLib = false;
            if (target instanceof BaseGraphicalEditPart) {
                isTaskLib =
                        ProcessWidgetType.TASK_LIBRARY
                                .equals(((BaseGraphicalEditPart) target)
                                        .getProcessWidgetType());
            } else if (target instanceof BaseConnectionEditPart) {
                isTaskLib =
                        ProcessWidgetType.TASK_LIBRARY
                                .equals(((BaseConnectionEditPart) target)
                                        .getProcessWidgetType());
            }

            if (isTaskLib) {
                return ANSWER_TRUE.equalsIgnoreCase(value);
            } else {
                return ANSWER_FALSE.equalsIgnoreCase(value);
            }
        } else if (IS_DECISION_FLOW_EDITOR.equals(name)) {
            boolean isDecisionFlow = false;
            if (target instanceof BaseGraphicalEditPart) {
                isDecisionFlow =
                        ProcessWidgetType.DECISION_FLOW
                                .equals(((BaseGraphicalEditPart) target)
                                        .getProcessWidgetType());
            } else if (target instanceof BaseConnectionEditPart) {
                isDecisionFlow =
                        ProcessWidgetType.DECISION_FLOW
                                .equals(((BaseConnectionEditPart) target)
                                        .getProcessWidgetType());
            }

            if (isDecisionFlow) {
                return ANSWER_TRUE.equalsIgnoreCase(value);
            } else {
                return ANSWER_FALSE.equalsIgnoreCase(value);
            }
        } else if (IS_START_TYPE_NONE.equals(name)) {

            /*
             * ABPM-897: Saket: Deprecating Message event's 'Generate Business
             * Service' capability and moving it over to Type None Event.
             */
            if (target instanceof EventEditPart) {
                EventAdapter adapter =
                        (EventAdapter) ((EventEditPart) target)
                                .getModelAdapter();
                EventTriggerType eventTriggerType =
                        adapter.getEventTriggerType();
                EventFlowType eventFlowType = adapter.getFlowType();

                /*
                 * ABPM-897: Saket: 'Generate business service' capability moved
                 * over to start type none event.
                 */
                if (EventTriggerType.EVENT_NONE_LITERAL
                        .equals(eventTriggerType)
                        && EventFlowType.FLOW_START_LITERAL
                                .equals(eventFlowType)) {
                    if (ProcessWidgetType.BPMN_PROCESS.equals(adapter
                            .getProcessType())) {
                        return ANSWER_TRUE.equals(value);
                    }
                }
            }
            return ANSWER_FALSE.equals(value);

        } else if (IS_START_TYPE_NONE_DIRECTLY_UNDER_PROCESS.equals(name)) {

            /*
             * ABPM-897: Saket: Deprecating Message event's 'Generate Business
             * Service' capability and moving it over to Type None Event.
             */
            if (target instanceof EventEditPart) {
                EventAdapter adapter =
                        (EventAdapter) ((EventEditPart) target)
                                .getModelAdapter();
                EventTriggerType eventTriggerType =
                        adapter.getEventTriggerType();
                EventFlowType eventFlowType = adapter.getFlowType();

                /*
                 * ABPM-897: Saket: 'Generate business service' capability moved
                 * over to start type none event.
                 */
                if (EventTriggerType.EVENT_NONE_LITERAL
                        .equals(eventTriggerType)
                        && EventFlowType.FLOW_START_LITERAL
                                .equals(eventFlowType)
                        && adapter.isEventContainedDirectlyUnderProcess()) {

                    if (ProcessWidgetType.BPMN_PROCESS.equals(adapter
                            .getProcessType())) {

                        return ANSWER_TRUE.equals(value);
                    }
                }
            }
            return ANSWER_FALSE.equals(value);

        } else if (IS_BUSINESS_PROCESS_REQUEST_ACTIVITY.equals(name)) {
            if (target instanceof EventEditPart) {
                EventAdapter adapter =
                        (EventAdapter) ((EventEditPart) target)
                                .getModelAdapter();
                EventTriggerType eventTriggerType =
                        adapter.getEventTriggerType();
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                        .equals(eventTriggerType)) {
                    if (ProcessWidgetType.BPMN_PROCESS.equals(adapter
                            .getProcessType())) {
                        return ANSWER_TRUE.equals(value);
                    }
                }
            } else if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;
                TaskAdapter adapter = taskEP.getActivityAdapter();
                if (adapter != null) {
                    TaskType taskType = adapter.getTaskType();
                    if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                        return (ANSWER_TRUE.equals(value));
                    }
                }
            }
            return ANSWER_FALSE.equals(value);

        } else if (IS_BUSINESS_PROCESS_START_MESSAGE_EVENT.equals(name)) {
            if (target instanceof EventEditPart) {
                EventAdapter adapter =
                        (EventAdapter) ((EventEditPart) target)
                                .getModelAdapter();
                EventTriggerType eventTriggerType =
                        adapter.getEventTriggerType();
                EventFlowType flowType = adapter.getFlowType();
                if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                    if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                            .equals(eventTriggerType)) {
                        if (ProcessWidgetType.BPMN_PROCESS.equals(adapter
                                .getProcessType())) {
                            return ANSWER_TRUE.equals(value);
                        }
                    }
                }
                return ANSWER_FALSE.equals(value);
            }
        } else if (EVENT_FLOW_TYPE.equals(name)) {
            if (target instanceof EventEditPart) {
                EventAdapter adapter =
                        (EventAdapter) ((EventEditPart) target)
                                .getModelAdapter();

                String typeStr = "BAD"; //$NON-NLS-1$
                EventFlowType type = adapter.getFlowType();

                if (EventFlowType.FLOW_START_LITERAL.equals(type)) {
                    typeStr = EVENT_FLOW_START;
                } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(type)) {
                    typeStr = EVENT_FLOW_INTERMEDIATE;
                } else if (EventFlowType.FLOW_END_LITERAL.equals(type)) {
                    typeStr = EVENT_FLOW_END;
                }

                if (value.indexOf(typeStr) != -1) {
                    return true;
                }
            }

        } else if (IS_INDEPENDENT_SUBPROCESS.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskAdapter adapter =
                        (TaskAdapter) ((TaskEditPart) target).getModelAdapter();

                TaskType tt = adapter.getTaskType();

                if (TaskType.SUBPROCESS_LITERAL.equals(tt)) {
                    return (ANSWER_TRUE.equals(value));
                }

                return (ANSWER_FALSE.equals(value));
            }

        } else if (TASK_OR_SUBPROC.equals(name)) {

            if (target instanceof TaskEditPart) {
                TaskAdapter adapter =
                        (TaskAdapter) ((TaskEditPart) target).getModelAdapter();

                TaskType tt = adapter.getTaskType();

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */
                if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(tt)
                        || TaskType.EVENT_SUBPROCESS_LITERAL.equals(tt)
                        || TaskType.SUBPROCESS_LITERAL.equals(tt)) {
                    return (TASK_OR_SUBPROC_SUBPROCESS.equals(value));
                } else {
                    return (TASK_OR_SUBPROC_TASK.equals(value));
                }
            }

        } else if (IS_TOPLEVEL_EMBEDDED_SUBPROCESS.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;

                if (taskEP.isEmbeddedSubProc()) {
                    if (!(taskEP.getParent() instanceof TaskEditPart)) {
                        return (ANSWER_TRUE.equals(value));
                    }
                }
            }
            return (ANSWER_FALSE.equals(value));

        } else if (IS_EMBEDDED_SUBPROCESS.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;

                if (taskEP.isEmbeddedSubProc()) {
                    return (ANSWER_TRUE.equals(value));
                }
            }
            return (ANSWER_FALSE.equals(value));

        } else if (SUPPORTS_FILL_COLOR.equals(name)) {

            ModelAdapterEditPart ep = (ModelAdapterEditPart) target;
            Object adp = ep.getModelAdapter();

            if (adp instanceof GraphicalColorAdapter
                    && !(adp instanceof BaseConnectionAdapter)
                    && ep.getFillColorIDForPartType() != null) {
                return (ANSWER_TRUE.equals(value));
            } else {
                return (ANSWER_FALSE.equals(value));
            }

        } else if (SUPPORTS_LINE_COLOR.equals(name)) {
            ModelAdapterEditPart ep = (ModelAdapterEditPart) target;
            Object adp = ep.getModelAdapter();
            if (adp instanceof GraphicalColorAdapter
                    && ep.getLineColorIDForPartType() != null) {
                return (ANSWER_TRUE.equals(value));
            } else {
                return (ANSWER_FALSE.equals(value));
            }
        } else if (IS_USER_TASK.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;
                TaskAdapter adapter = taskEP.getActivityAdapter();
                if (adapter != null) {
                    TaskType taskType = adapter.getTaskType();
                    if (TaskType.USER_LITERAL.equals(taskType)) {
                        return (ANSWER_TRUE.equals(value));
                    }
                }
            }
        } else if (IS_BUSINESS_PROCESS_USER_TASK.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;
                TaskAdapter adapter = taskEP.getActivityAdapter();
                if (adapter != null) {
                    TaskType taskType = adapter.getTaskType();
                    if (TaskType.USER_LITERAL.equals(taskType)) {
                        if (ProcessWidgetType.BPMN_PROCESS.equals(adapter
                                .getProcessType())) {
                            return (ANSWER_TRUE.equals(value));
                        }
                    }
                }
            }
        } else if (IS_MANUAL_TASK.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) target;
                TaskAdapter adapter = taskEP.getActivityAdapter();
                if (adapter != null) {
                    TaskType taskType = adapter.getTaskType();
                    if (TaskType.MANUAL_LITERAL.equals(taskType)) {
                        return (ANSWER_TRUE.equals(value));
                    }
                }
            }
        } else if (IS_BUSINESS_PROCESS.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.BPMN_PROCESS.equals(adapter
                            .getProcessType());
                }
            } else if (target instanceof BaseConnectionEditPart) {
                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.BPMN_PROCESS.equals(adapter
                            .getProcessType());
                }
            }
        } else if (IS_CASE_SERVICE.equals(name)) {

            if (target instanceof BaseGraphicalEditPart) {

                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {

                    return ProcessWidgetType.CASE_SERVICE.equals(adapter
                            .getProcessType());
                }
            } else if (target instanceof BaseConnectionEditPart) {

                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {

                    return ProcessWidgetType.CASE_SERVICE.equals(adapter
                            .getProcessType());
                }
            }
        } else if (IS_BUSINESS_SERVICE.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.BUSINESS_SERVICE.equals(adapter
                            .getProcessType());
                }
            } else if (target instanceof BaseConnectionEditPart) {
                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.BUSINESS_SERVICE.equals(adapter
                            .getProcessType());
                }
            }
        } else if (IS_PAGEFLOW.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.PAGEFLOW_PROCESS.equals(adapter
                            .getProcessType());
                }
            } else if (target instanceof BaseConnectionEditPart) {
                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {
                    return ProcessWidgetType.PAGEFLOW_PROCESS.equals(adapter
                            .getProcessType());
                }
            }
        } else if (IS_SERVICEPROCESS.equals(name)) {

            if (target instanceof BaseGraphicalEditPart) {

                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {

                    return ProcessWidgetType.SERVICE_PROCESS.equals(adapter
                            .getProcessType());
                }
            } else if (target instanceof BaseConnectionEditPart) {

                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;
                BaseProcessAdapter adapter = ep.getModelAdapter();
                if (adapter != null) {

                    return ProcessWidgetType.SERVICE_PROCESS.equals(adapter
                            .getProcessType());
                }
            }
        } else if (IS_WM_FEATURE_AVAILABLE.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;

                if (ep.getModelAdapter() != null) {
                    if (ep.getModelAdapter().isWMFeatureAvailable()) {
                        return (ANSWER_TRUE.equals(value));
                    } else {
                        return (ANSWER_FALSE.equals(value));
                    }
                }
            } else if (target instanceof BaseConnectionEditPart) {
                BaseConnectionEditPart ep = (BaseConnectionEditPart) target;

                if (ep.getModelAdapter() != null) {
                    if (ep.getModelAdapter().isWMFeatureAvailable()) {
                        return (ANSWER_TRUE.equals(value));
                    } else {
                        return (ANSWER_FALSE.equals(value));
                    }
                }
            }
        } else if (SERVICE_TASK_IMPLEMENTATION_TYPE.equals(name)) {
            if (target instanceof TaskEditPart) {
                TaskAdapter taskAdapter =
                        ((TaskEditPart) target).getActivityAdapter();
                if (taskAdapter != null) {
                    if (TaskType.SERVICE_LITERAL.equals(taskAdapter
                            .getTaskType())) {

                        String taskImplementationExtensionId =
                                taskAdapter.getTaskImplementationExtensionId();
                        if (value != null && value.length() != 0
                                && !value.equalsIgnoreCase("unspecified")) { //$NON-NLS-1$
                            if (value
                                    .equalsIgnoreCase(taskImplementationExtensionId)) {
                                return true;
                            }

                        } else if (taskImplementationExtensionId == null
                                || taskImplementationExtensionId.length() == 0) {
                            /*
                             * Impl Type unspecified and so is value so that's
                             * ok!
                             */
                            return true;
                        }
                    }
                }
            }
        } else if (IS_RCP_APPLICATION.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;

                if (ep.getModelAdapter() != null) {
                    if (ep.getModelAdapter().isRCPApplication()) {
                        return (ANSWER_TRUE.equals(value));
                    } else {
                        return (ANSWER_FALSE.equals(value));
                    }
                }
            }
        } else if (IS_EVENT_HANDLER_ACTIVITY.equals(name)) {

            if (target instanceof BaseGraphicalEditPart) {

                BaseGraphicalEditPart baseGEP = (BaseGraphicalEditPart) target;

                EditPartViewer epviewer = baseGEP.getViewer();

                if (epviewer != null) {

                    ISelection selection = epviewer.getSelection();
                    {

                        if (!selection.isEmpty()) {
                            IStructuredSelection sel =
                                    (IStructuredSelection) selection;

                            /*
                             * Exclude objects parented by embedded sub-proces
                             * that are also selected.
                             */

                            List parentsOnlyList =
                                    ToolUtilities
                                            .getSelectionWithoutDependants(sel
                                                    .toList());

                            HashSet<Object> selectedObjects =
                                    new HashSet<Object>(sel.size());

                            for (Iterator iter = parentsOnlyList.iterator(); iter
                                    .hasNext();) {
                                Object obj = iter.next();

                                if (obj instanceof BaseFlowNodeEditPart
                                        || obj instanceof DataObjectEditPart
                                        || obj instanceof NoteEditPart) {

                                    BaseGraphicalEditPart gep =
                                            (BaseGraphicalEditPart) obj;

                                    selectedObjects.add(gep.getModel());

                                    if (gep instanceof TaskEditPart) {
                                        /*
                                         * Make sure that task border attached
                                         * events are selected
                                         */
                                        Collection<EventEditPart> events =
                                                ((TaskEditPart) gep)
                                                        .getAttachedEvents();

                                        for (Iterator iterator =
                                                events.iterator(); iterator
                                                .hasNext();) {
                                            EventEditPart evEP =
                                                    (EventEditPart) iterator
                                                            .next();

                                            selectedObjects
                                                    .add(evEP.getModel());
                                        }
                                    }
                                }
                            }

                            BaseProcessAdapter modelAdapter =
                                    ((BaseGraphicalEditPart) target)
                                            .getParentProcess()
                                            .getModelAdapter();

                            if (modelAdapter instanceof ProcessDiagramAdapter) {

                                ProcessDiagramAdapter pda =
                                        (ProcessDiagramAdapter) modelAdapter;

                                if (pda.isEventHandlerFlow(selectedObjects)) {
                                    return (ANSWER_TRUE.equals(value));
                                }
                            }
                        }
                    }
                }
            }
            return (ANSWER_FALSE.equals(value));

        } else if (IS_READ_ONLY.equals(name)) {
            if (target instanceof BaseGraphicalEditPart) {
                BaseGraphicalEditPart ep = (BaseGraphicalEditPart) target;

                if (ep.getModelAdapter() != null) {
                    if (ep.getModelAdapter().isReadOnly()) {
                        return (ANSWER_TRUE.equals(value));
                    } else {
                        return (ANSWER_FALSE.equals(value));
                    }
                }
            }
        }
        return false;
    }
}
