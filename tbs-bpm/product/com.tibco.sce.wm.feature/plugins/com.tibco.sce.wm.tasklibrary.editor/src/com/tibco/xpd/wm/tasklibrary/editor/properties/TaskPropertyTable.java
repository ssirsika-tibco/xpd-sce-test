/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryProjectExplorerLinkHelper;
import com.tibco.xpd.wm.tasklibrary.editor.util.TaskLibraryTaskUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TaskPropertyTable
 * 
 * 
 * @author bharge
 * @since 3.3 (5 Nov 2009)
 */
public class TaskPropertyTable extends AbstractProcessRelevantDataTable {

    private final EditingDomain editingDomain;

    private Process inputTaskLibrary = null;

    private IContentProvider contentProvider;

    /**
     * @param parent
     * @param toolkit
     * @param object
     * @param b
     */
    public TaskPropertyTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
    }

    /**
     * @return the contentProvider
     */
    @Override
    public IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            contentProvider = new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Lane) {
                        List<EObject> elements = new ArrayList<EObject>();

                        Collection<EObject> nodes =
                                Xpdl2ModelUtil
                                        .getAllNodesInLane((Lane) inputElement);
                        for (EObject node : nodes) {
                            if (node instanceof Activity) {
                                elements.add(node);
                            }
                        }
                        return elements.toArray();
                    }
                    if (inputElement instanceof Process) {
                        inputTaskLibrary = (Process) inputElement;
                        return ((Process) inputElement).getActivities()
                                .toArray();
                    }
                    return new Object[0];
                }

                @Override
                public void dispose() {
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }

            };
        }
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(editingDomain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer);
        }
        new LaneSelectionColumn(editingDomain, viewer);
        new TaskTypeSelectionColumn(editingDomain, viewer);
        new ParticipantSelectionColumn(editingDomain, viewer);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            setColumnProportions(new float[] { 0.2f, // label
                    0.2f, // name
                    0.2f, // lane
                    0.15f, // type
                    0.2f, // participant
            });
        } else {
            setColumnProportions(new float[] { 0.2f, // label
                    0.2f, // lane
                    0.15f, // type
                    0.2f, // participant
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(Xpdl2Package.eINSTANCE.getFlowContainer_Activities());
        return features;
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, Messages.NewTaskWizard_AddTask_menu,
                Messages.NewTaskWizard_AddTask_menu) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object result = null;
                Object input = getInput();

                if (editingDomain != null) {
                    // Get default lane to add it to.
                    Process taskLibrary = null;
                    Lane lane = null;
                    String firstCellVal = getNewRowFirstCellVal();

                    if (input instanceof Process) {
                        // When whole task lib is input then default lane to
                        // first.
                        taskLibrary = (Process) input;
                        List<Lane> lanes =
                                Xpdl2ModelUtil.getProcessLanes(taskLibrary);
                        if (!lanes.isEmpty()) {
                            lane = lanes.get(0);
                        }
                    } else if (input instanceof Lane) {
                        // When task set (lane) is input then use it as default
                        // lane.
                        lane = (Lane) input;

                        taskLibrary = Xpdl2ModelUtil.getProcess(lane);
                    }

                    if (lane != null && taskLibrary != null) {
                        //
                        // Create the new task activity
                        Activity task =
                                TaskLibraryTaskUtil
                                        .createDefaultTask(TaskType.NONE_LITERAL);
                        Xpdl2ModelUtil.setOtherAttribute(task,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                firstCellVal);

                        task.setName(NameUtil.getInternalName(firstCellVal,
                                false));

                        //
                        // Place it in given lane.
                        NodeGraphicsInfo ngi =
                                Xpdl2ModelUtil.getNodeGraphicsInfo(task);
                        ngi.setLaneId(lane.getId());

                        Coordinates newCoords =
                                Xpdl2Factory.eINSTANCE.createCoordinates();

                        int reqdLaneHeight =
                                TaskLibraryTaskUtil
                                        .placeTaskInLane(task,
                                                ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                                ProcessWidgetConstants.TASK_HEIGHT_SIZE,
                                                lane,
                                                newCoords);
                        ngi.setCoordinates(newCoords);

                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.NewTaskWizard_AddTask_menu);
                        cmd.append(AddCommand.create(editingDomain,
                                taskLibrary,
                                Xpdl2Package.eINSTANCE
                                        .getFlowContainer_Activities(),
                                task));

                        NodeGraphicsInfo laneNgi =
                                Xpdl2ModelUtil
                                        .getOrCreateNodeGraphicsInfo(lane,
                                                editingDomain,
                                                cmd);
                        if (laneNgi.getHeight() < reqdLaneHeight) {
                            cmd.append(SetCommand.create(editingDomain,
                                    laneNgi,
                                    Xpdl2Package.eINSTANCE
                                            .getNodeGraphicsInfo_Height(),
                                    new Double(reqdLaneHeight)));
                        }

                        //
                        // Add the activity to the lane.
                        if (cmd.canExecute()) {
                            editingDomain.getCommandStack().execute(cmd);
                            result = task;

                            final Activity selAct = task;
                            Display.getCurrent().asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    TaskLibraryProjectExplorerLinkHelper
                                            .revealIfEditorOpen(PlatformUI
                                                    .getWorkbench()
                                                    .getActiveWorkbenchWindow()
                                                    .getActivePage(),
                                                    inputTaskLibrary,
                                                    selAct,
                                                    false);
                                }
                            });
                        }
                    }

                }
                return result;
            }

            protected String getNewRowFirstCellVal() {
                return TaskLibraryTaskUtil.getUniqueTaskName(inputTaskLibrary,
                        TaskType.NONE_LITERAL.toString());
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.TasksPropertySection_DeleteTask_tooltip,
                Messages.TasksPropertySection_DeleteTask_tooltip) {

            @SuppressWarnings("unchecked")
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                List delObjs = selection.toList();
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.TasksPropertySection_DeleteTask_tooltip);
                if (delObjs.size() > 0) {
                    cmd.append(RemoveCommand.create(editingDomain, delObjs));

                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
                // if (!delObjs.isEmpty()) {
                // DeleteAction.deleteXpdlObject(Display.getCurrent()
                // .getActiveShell(), selection);
                // }
            }

        };
    }

    private Lane getLane(Object element) {
        if (inputTaskLibrary == null) {
            if (element instanceof Activity) {
                Activity activity = (Activity) element;
                inputTaskLibrary = activity.getProcess();
            }
        }
        if (element instanceof Activity && inputTaskLibrary != null) {
            Activity task = (Activity) element;

            NodeGraphicsInfo ngi = Xpdl2ModelUtil.getNodeGraphicsInfo(task);
            if (ngi != null) {
                String laneId = ngi.getLaneId();
                if (laneId != null) {
                    Lane lane =
                            Xpdl2ModelUtil.getLane(inputTaskLibrary, laneId);
                    if (lane != null) {
                        return lane;
                    }
                }
            }
        }
        return null;

    }

    private class LaneNameToLane {
        String name;

        Lane lane;

        public LaneNameToLane(Lane lane) {
            this.lane = lane;
            this.name = Xpdl2ModelUtil.getDisplayNameOrName(lane);
        }
    }

    private List<LaneNameToLane> laneComboValues = Collections.emptyList();

    private class LaneSelectionColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @return
         */
        public String[] setComboItems() {
            setupLaneComboValues();
            String[] names = getLaneNames();
            return names;
        }

        /**
         * @return
         */
        private String[] getLaneNames() {
            String[] names = new String[laneComboValues.size()];
            int i = 0;
            for (LaneNameToLane ln : laneComboValues) {
                names[i++] = ln.name;
            }
            return names;
        }

        /**
         * 
         */
        private void setupLaneComboValues() {
            laneComboValues = new ArrayList<LaneNameToLane>();
            if (inputTaskLibrary == null) {
                EObject input = getInput();
                if (input instanceof Process) {
                    inputTaskLibrary = (Process) input;
                }
            }
            if (inputTaskLibrary != null) {
                List<Lane> lanes =
                        Xpdl2ModelUtil.getProcessLanes(inputTaskLibrary);
                for (Lane lane : lanes) {
                    laneComboValues.add(new LaneNameToLane(lane));
                }
            }
        }

        /**
         * @param editingDomain
         * @param viewer
         */
        public LaneSelectionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.TasksPropertySection_TaskSetColumnTitle_label, 130);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(setComboItems());

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Activity) {
                editor.setInput(setComboItems());
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof Activity && value instanceof String) { // &&
                // value
                // instanceof
                // Integer
                Activity task = (Activity) element;

                int laneIdx = 0;
                for (LaneNameToLane ln : laneComboValues) {

                    if (value.equals(ln.name)) {
                        break;
                    }
                    laneIdx++;
                }

                if (laneIdx >= 0 && laneIdx < laneComboValues.size()) {
                    Lane lane = laneComboValues.get(laneIdx).lane;

                    Command cmd =
                            TaskLibraryTaskUtil
                                    .getSetTaskLaneCommand(getEditingDomain(),
                                            task,
                                            lane);
                    //
                    // Add the activity to the lane.
                    if (cmd != null && cmd.canExecute()) {
                        /*
                         * XPD-5368: Saket- Ensuring that the implementations of
                         * AbstractColumn.getSetValueCommand() return the
                         * command and do not execute it.
                         */

                        final Activity selAct = task;

                        Display.getCurrent().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                TaskLibraryProjectExplorerLinkHelper
                                        .revealIfEditorOpen(PlatformUI
                                                .getWorkbench()
                                                .getActiveWorkbenchWindow()
                                                .getActivePage(),
                                                inputTaskLibrary,
                                                selAct,
                                                false);
                            }
                        });

                        return cmd;
                    }
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            Lane lane = getLane(element);

            if (lane != null) {
                return Xpdl2ModelUtil.getDisplayNameOrName(lane);
            }

            return ""; //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class TaskTypeSelectionColumn extends AbstractColumn {

        private class TypeNameToType {
            String name;

            TaskType taskType;

            public TypeNameToType(TaskType taskType) {
                this.taskType = taskType;
                this.name = taskType.toString();
            }
        }

        private ComboBoxViewerCellEditor editor;

        private List<TypeNameToType> typeComboValues;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public TaskTypeSelectionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.TasksPropertySection_TaskTypePropertyColumn_label,
                    130);
            typeComboValues = new ArrayList<TypeNameToType>();
            typeComboValues.add(new TypeNameToType(TaskType.NONE_LITERAL));
            typeComboValues.add(new TypeNameToType(TaskType.USER_LITERAL));
            typeComboValues.add(new TypeNameToType(TaskType.MANUAL_LITERAL));
            typeComboValues.add(new TypeNameToType(TaskType.SERVICE_LITERAL));
            typeComboValues.add(new TypeNameToType(TaskType.SCRIPT_LITERAL));
            typeComboValues.add(new TypeNameToType(TaskType.SEND_LITERAL));
            typeComboValues
                    .add(new TypeNameToType(TaskType.SUBPROCESS_LITERAL));

            String[] names = new String[typeComboValues.size()];
            int i = 0;
            for (TypeNameToType ln : typeComboValues) {
                names[i++] = ln.name;
            }
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(names);

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Activity) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {

            if (element instanceof Activity) {
                Activity task = (Activity) element;
                int typeIdx = (Integer) getValueIndex((String) value);

                if (typeIdx >= 0 && typeIdx < typeComboValues.size()) {
                    TaskType taskType = typeComboValues.get(typeIdx).taskType;

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.TasksPropertySection_SetTaskType_menu);
                    cmd.append(TaskObjectUtil
                            .getSetTaskTypeCommand(getEditingDomain(),
                                    task,
                                    taskType));

                    NodeGraphicsInfo ngi =
                            Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(task,
                                    getEditingDomain(),
                                    cmd);

                    String fillColor =
                            ProcessWidgetColors
                                    .getInstance(ProcessWidgetType.TASK_LIBRARY)
                                    .getGraphicalNodeColor(null,
                                            taskType.getGetDefaultFillColorId())
                                    .toString();
                    cmd.append(SetCommand.create(getEditingDomain(),
                            ngi,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_FillColor(),
                            fillColor));
                    String lineColor =
                            ProcessWidgetColors
                                    .getInstance(ProcessWidgetType.TASK_LIBRARY)
                                    .getGraphicalNodeColor(null,
                                            taskType.getGetDefaultLineColorId())
                                    .toString();
                    cmd.append(SetCommand.create(getEditingDomain(),
                            ngi,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_BorderColor(),
                            lineColor));

                    // Add the activity to the lane.
                    if (cmd != null && cmd.canExecute()) {
                        /*
                         * XPD-5368: Saket- Ensuring that the implementations of
                         * AbstractColumn.getSetValueCommand() return the
                         * command and do not execute it.
                         */

                        final Activity selAct = task;

                        Display.getCurrent().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                TaskLibraryProjectExplorerLinkHelper
                                        .revealIfEditorOpen(PlatformUI
                                                .getWorkbench()
                                                .getActiveWorkbenchWindow()
                                                .getActivePage(),
                                                inputTaskLibrary,
                                                selAct,
                                                false);
                            }
                        });

                        return cmd;
                    }
                }
            }
            return null;
        }

        private Object getValueIndex(String name) {
            int typeIdx = 0;
            for (TypeNameToType ln : typeComboValues) {
                if (ln.name.equals(name)) {
                    return typeIdx;
                }
                typeIdx++;
            }
            return -1;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof Activity) {
                return TaskObjectUtil.getTaskType((Activity) element)
                        .toString();
            }

            return ""; //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class ParticipantSelectionColumn extends AbstractColumn {

        /**
         * This is small class that is as the 'value' for the cell. This can be
         * passed between the dialog cell editor and the cell to get/set the
         * performer list.
         * 
         */
        private class ActivityAndPerformers {
            Activity activity;

            EObject[] performers;

            public ActivityAndPerformers(Activity activity, EObject[] performers) {
                super();
                this.activity = activity;
                this.performers = performers;
            }

        }

        private final ParticipantPickerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public ParticipantSelectionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.TasksPropertySection_ParticipantsColumn_label, 175);
            editor =
                    new ParticipantPickerCellEditor(
                            (Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Activity) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof Activity
                    && value instanceof ActivityAndPerformers) {
                Command cmd =
                        TaskObjectUtil
                                .getSetPerformersCommand(getEditingDomain(),
                                        (Activity) element,
                                        ((ActivityAndPerformers) value).performers);
                /*
                 * XPD-5368: Saket- Ensuring that the implementations of
                 * AbstractColumn.getSetValueCommand() return the command and do
                 * not execute it.
                 */

                return cmd;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof Activity && inputTaskLibrary != null) {
                Activity activity = (Activity) element;

                EObject[] performers =
                        TaskObjectUtil.getActivityPerformers(activity,
                                inputTaskLibrary);

                if (performers != null && performers.length > 0) {
                    String desc = ""; //$NON-NLS-1$
                    for (int i = 0; i < performers.length; i++) {
                        if (i > 0) {
                            desc += "; "; //$NON-NLS-1$
                        }

                        if (performers[i] instanceof NamedElement) {
                            desc +=
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName((NamedElement) performers[i]);
                        } else {
                            desc += WorkingCopyUtil.getText(performers[i]);
                        }
                    }
                    return desc;
                }
            }

            return ""; //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof Activity) {
                Activity task = (Activity) element;

                return new ActivityAndPerformers(task,
                        TaskObjectUtil.getActivityPerformers(task));
            }
            return getText(element);
        }

        private class ParticipantPickerCellEditor extends DialogCellEditor {

            private ActivityAndPerformers taskAndPerformers = null;

            /**
             * @param control
             */
            public ParticipantPickerCellEditor(Composite control) {
                super(control);
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
             * eclipse.swt.widgets.Control)
             */
            @Override
            protected Object openDialogBox(Control cellEditorWindow) {

                if (taskAndPerformers != null) {
                    List<EObject> perfs =
                            TaskObjectUtil
                                    .selectActivityPerformers(taskAndPerformers.activity
                                            .getProcess(),
                                            taskAndPerformers.activity);
                    if (perfs != null) {
                        taskAndPerformers.performers =
                                perfs.toArray(new EObject[perfs.size()]);
                        return taskAndPerformers;
                    }
                }
                return null;
            }

            @Override
            protected void updateContents(Object value) {
                taskAndPerformers = null;
                if (value instanceof ActivityAndPerformers) {
                    taskAndPerformers = (ActivityAndPerformers) value;
                    String cellText = getText(taskAndPerformers.activity);
                    getDefaultLabel().setText(cellText);
                } else {
                    super.updateContents(value);
                }
            }
        }

    }
}
