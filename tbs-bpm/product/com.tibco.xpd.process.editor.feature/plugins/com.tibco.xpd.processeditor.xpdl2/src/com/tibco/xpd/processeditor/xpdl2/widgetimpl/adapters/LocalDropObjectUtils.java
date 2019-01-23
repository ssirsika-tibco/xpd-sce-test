/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectConnectionType;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectObjectType;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem.DropPopupCustomCommand;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A few utility methods to help in some common drop-object on diagram
 * functionality.
 * 
 * @author aallway
 * 
 */
public class LocalDropObjectUtils {

    /**
     * Simple class to support getCreateTaskFromProcessDropPopupItems() when
     * multiple processes are selected, sets up a custom command execution drop
     * popup item to show dialog to allow user to select task sequence order.
     * 
     * @author aallway
     * 
     */
    private static class CreateParticTaskSequencePopupCommand implements
            DropPopupCustomCommand, ILabelProvider {

        private String laneId;

        private List<Object> dropObjects;

        private Point location;

        private TaskType taskType;

        private Process process;

        public CreateParticTaskSequencePopupCommand(Process process,
                String laneId, List<Object> dropObjects, Point location,
                TaskType taskType) {
            super();
            this.laneId = laneId;
            this.dropObjects = dropObjects;
            this.location = location;
            this.taskType = taskType;
            this.process = process;
        }

        @Override
        public void addListener(ILabelProviderListener listener) {
        }

        @Override
        public void dispose() {
        }

        @Override
        public DropObjectPopupItem execute(Control hostControl) {

            List<Object> orderedObjects =
                    DiagramDropObjectUtils
                            .selectObjectOrder(hostControl.getShell(),
                                    dropObjects,
                                    this,
                                    String.format(Messages.DropObjectUtils_SetTaskOrder_title,
                                            taskType.toString()));

            if (orderedObjects != null && orderedObjects.size() > 0) {
                return createMultiTasksForParticipants(process,
                        laneId,
                        orderedObjects,
                        location,
                        taskType,
                        true);
            }
            return null;
        }

        @Override
        public Image getImage(Object element) {
            return DiagramDropObjectUtils.getImageForTaskType(taskType);
        }

        @Override
        public String getText(Object element) {
            return getParticTaskDisplayName(element);
        }

        @Override
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {
        }

    }

    /**
     * Simple class to support getCreateTaskFromProcessDropPopupItems() when
     * multiple processes are selected, sets up a custom command execution drop
     * popup item to show dialog to allow user to select task sequence order.
     * 
     * @author aallway
     */
    private static class CreateSubProcSequencePopupCommand implements
            DropPopupCustomCommand, ILabelProvider {

        private String laneId;

        private List<Object> dropObjects;

        private Point location;

        private Package destPkg;

        private Process process;

        public CreateSubProcSequencePopupCommand(Process process,
                String laneId, List<Object> dropObjects, Point location,
                Package destPkg) {
            super();
            this.laneId = laneId;
            this.dropObjects = dropObjects;
            this.location = location;
            this.destPkg = destPkg;
            this.process = process;
        }

        @Override
        public void addListener(ILabelProviderListener listener) {
        }

        @Override
        public void dispose() {
        }

        @Override
        public DropObjectPopupItem execute(Control hostControl) {
            List<Object> orderedObjects =
                    DiagramDropObjectUtils
                            .selectObjectOrder(hostControl.getShell(),
                                    dropObjects,
                                    this,
                                    String.format(Messages.DropObjectUtils_SetTaskOrder_title,
                                            TaskType.SUBPROCESS_LITERAL
                                                    .toString()));

            if (orderedObjects != null && orderedObjects.size() > 0) {
                return createTaskFromProcessDropPopupItems(process,
                        laneId,
                        orderedObjects,
                        location,
                        destPkg,
                        true);
            }
            return null;
        }

        @Override
        public Image getImage(Object element) {
            return DiagramDropObjectUtils
                    .getImageForTaskType(TaskType.SUBPROCESS_LITERAL);
        }

        @Override
        public String getText(Object element) {
            return getCallProcessName((NamedElement) element);
        }

        @Override
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {
        }

    }

    /**
     * Simple class to support getCreateLibraryTaskReferenceDropPopupItems()
     * when multiple library tasks are selected, sets up a custom command
     * execution drop popup item to show dialog to allow user to select task
     * sequence order.
     * 
     * @author aallway
     * @since 3.2
     */
    private static class CreateRefTaskSequencePopupCommand implements
            DropPopupCustomCommand, ILabelProvider {

        private String laneId;

        private List<Object> dropObjects;

        private Point location;

        private Package destPkg;

        private Process process;

        public CreateRefTaskSequencePopupCommand(Process process,
                String laneId, List<Object> dropObjects, Point location,
                Package destPkg) {
            super();
            this.laneId = laneId;
            this.dropObjects = dropObjects;
            this.location = location;
            this.destPkg = destPkg;
            this.process = process;
        }

        @Override
        public void addListener(ILabelProviderListener listener) {
        }

        @Override
        public void dispose() {
        }

        @Override
        public DropObjectPopupItem execute(Control hostControl) {
            List<Object> orderedObjects =
                    DiagramDropObjectUtils
                            .selectObjectOrder(hostControl.getShell(),
                                    dropObjects,
                                    this,
                                    String.format(Messages.DropObjectUtils_SetTaskOrder_title,
                                            TaskType.REFERENCE_LITERAL
                                                    .toString()));

            if (orderedObjects != null && orderedObjects.size() > 0) {
                return createLibraryTaskReferencePopupItems(process,
                        laneId,
                        orderedObjects,
                        location,
                        destPkg,
                        true);
            }
            return null;
        }

        @Override
        public Image getImage(Object element) {
            return DiagramDropObjectUtils
                    .getImageForTaskType(TaskType.REFERENCE_LITERAL);
        }

        @Override
        public String getText(Object element) {
            return getCallProcessName((NamedElement) element);
        }

        @Override
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {
        }

    }

    private static final String REFLECTION_OM_CORE_PLUGIN_ID =
            "com.tibco.xpd.om.core"; //$NON-NLS-1$

    private static final String REFLECTION_OM_CORE_NAMEDELEMENT_CLASS =
            "com.tibco.xpd.om.core.om.NamedElement"; //$NON-NLS-1$

    public static Bundle omCoreBundle = null;

    public static Class namedElementCls = null;

    /**
     * Add the data items in list to the actual parameters in given message (if
     * they are not already present).
     * <p>
     * If we wish to perform activity level check while creating associated
     * parameters then we should use the overloaded version of this method i.e.
     * {@link LocalDropObjectUtils#addDataItemsToAssocParams(AssociatedParameters, List, ModeType, Activity)}
     * 
     * @param assocParams
     * @param dropDataItems
     * @param mode
     */
    public static void addDataItemsToAssocParams(
            AssociatedParameters assocParams,
            List<ProcessRelevantData> dropDataItems, ModeType mode) {
        addDataItemsToAssocParams(assocParams, dropDataItems, mode, null);
    }

    /**
     * Adds process data in the list to the associated parameters with the
     * appropriate additional info(Mode Type, mandatory etc) depending upon the
     * Activity to which the parameters are to be associated.
     * 
     * @param assocParams
     *            Associated Parameters
     * @param dropDataItems
     *            Process Data
     * @param mode
     *            Mode Type(IN/OUT/IN-OUT)
     * @param activity
     *            Activity with which the parameters should be associated.
     */
    public static void addDataItemsToAssocParams(
            AssociatedParameters assocParams,
            List<ProcessRelevantData> dropDataItems, ModeType mode,
            Activity activity) {

        Map<String, AssociatedParameter> currentItems =
                new HashMap<String, AssociatedParameter>();

        boolean isUserTask = false;

        /* check if activity is an user task */
        if (activity != null) {
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                isUserTask = true;
            }
        }

        for (Iterator iterator =
                assocParams.getAssociatedParameter().iterator(); iterator
                .hasNext();) {
            AssociatedParameter assocParam =
                    (AssociatedParameter) iterator.next();

            currentItems.put(assocParam.getFormalParam(), assocParam);
        }

        for (ProcessRelevantData data : dropDataItems) {
            // Only add if not already there.
            String dataName = data.getName();

            AssociatedParameter assocParam = currentItems.get(dataName);

            if (assocParam != null) {
                // SID MR 38078 - Associated Param should NOT inherit mode from
                // formal param any more.

                // If this data item is already associated with this user
                // task then simply ADD the mode to that requested.
                /*
                 * Read-only field/param cannot be set to OUT or INOUT mode cos
                 * it can't be assigned.
                 */
                if (data.isReadOnly()) {
                    assocParam.setMode(ModeType.IN_LITERAL);

                } else {
                    ModeType assocParamMode =
                            ProcessInterfaceUtil
                                    .getAssocParamModeType(assocParam);

                    if (ModeType.IN_LITERAL.equals(mode)
                            && ModeType.OUT_LITERAL.equals(assocParamMode)) {
                        assocParam.setMode(ModeType.INOUT_LITERAL);

                    } else if (ModeType.OUT_LITERAL.equals(mode)
                            && ModeType.IN_LITERAL.equals(assocParamMode)) {

                        assocParam.setMode(ModeType.INOUT_LITERAL);

                    } else if (ModeType.INOUT_LITERAL.equals(mode)) {
                        assocParam.setMode(ModeType.INOUT_LITERAL);
                    }
                }

            } else {
                // Not currently associated, add one.
                assocParam =
                        XpdExtensionFactory.eINSTANCE
                                .createAssociatedParameter();
                assocParam.setFormalParam(dataName);

                /*
                 * Read-only field/param cannot be set to OUT mode cos it can't
                 * be assigned. So force to IN.
                 */
                if (data.isReadOnly()) {
                    assocParam.setMode(ModeType.IN_LITERAL);
                } else {
                    // SID MR 38078 - Associated Param should NOT inherit mode
                    // from formal param any more.
                    assocParam.setMode(mode);
                }

                /*
                 * XPD-5435: If the activity is a user task and the data
                 * references a case class then the associated param should by
                 * default be 'not mandatory'
                 */
                if (!(data instanceof FormalParameter)
                        || (isUserTask && data.getDataType() instanceof RecordType)) {
                    assocParam.setMandatory(false);
                } else {
                    // assoc Formal parameter mandatory flag defaults to formal
                    // params mandatory flag.
                    assocParam.setMandatory(ProcessInterfaceUtil
                            .isFormalParamMandatory((FormalParameter) data));
                }

                assocParams.getAssociatedParameter().add(assocParam);
            }
        }

        return;
    }

    /**
     * Check that the given list contains only Process objects.
     * 
     * @param dropObjects
     * @return
     */
    public static boolean checkAllAreValidProcesses(Process process,
            List<Object> dropObjects) {

        boolean isServiceProcess = Xpdl2ModelUtil.isServiceProcess(process);

        if (dropObjects.size() < 1 || !(dropObjects.get(0) instanceof EObject)) {
            return false;
        }

        for (Iterator<Object> iterator = dropObjects.iterator(); iterator
                .hasNext();) {
            Object o = iterator.next();

            if (o instanceof Process) {
                Process dropProcess = (Process) o;
                /*
                 * ABPM-897: Saket: We now allow drag-drop of business process
                 * on a pageflow and it's derivatives.
                 */

                /*
                 * SDA-59 Don't drop decision flow onto business / pageflow
                 * process as a reusable sub-proc task. Allow other
                 * contributions to create decision service task instead.
                 */
                /*
                 * XPD-7481: Saket: A pageflow process shouldn't be allowed to
                 * be dragged-dropped on to a service process.
                 */
                if (DecisionFlowUtil.isDecisionFlow(dropProcess)
                        || (Xpdl2ModelUtil.isPageflowOrSubType(dropProcess) && isServiceProcess)) {
                    return false;
                }

            } else if (o instanceof ProcessInterface) {

                /*
                 * Handle Process Interface and Service process Interface...
                 * 
                 * XPD-7516: Saket: The only combination that we don't allow is
                 * a process interface being dragged-dropped on a service
                 * process.
                 */

                if (Xpdl2ModelUtil.isServiceProcess(process)) {

                    if (Xpdl2ModelUtil.isProcessInterface((ProcessInterface) o)) {

                        return false;

                    }
                }
            } else {
                return false; // not a process or interfaceat all.
            }
        }
        return true;

    }

    /**
     * @param dropObjects
     * @param process
     * 
     * @return True if all the objects are tasks in a task library.
     */
    public static boolean checkAllAreLibraryTasks(List<Object> dropObjects,
            Process process) {
        Process taskLibrary = null;

        for (Object o : dropObjects) {
            if (!(o instanceof Activity)
                    || !XpdlFileContentPropertyTester
                            .isTasksFileContent((Activity) o)) {
                return false;

            }

            //
            // Must all be from same library.
            if (taskLibrary == null) {
                taskLibrary = ((Activity) o).getProcess();
            } else if (taskLibrary != ((Activity) o).getProcess()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param dropObjects
     * @return
     */
    public static boolean checkAllAreValidOMNamedElements(
            List<Object> dropObjects) {
        if (dropObjects.size() < 1 || !(dropObjects.get(0) instanceof EObject)) {
            return false;
        }

        try {
            omCoreBundle = Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                namedElementCls =
                        omCoreBundle
                                .loadClass(REFLECTION_OM_CORE_NAMEDELEMENT_CLASS);
            }
        } catch (Exception e) {
            return false;
        }

        for (Iterator iterator = dropObjects.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (namedElementCls == null || !namedElementCls.isInstance(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check that the given list contains only data fields / formal parameters
     * of type Performer.
     * 
     * @param dropObjects
     * @return
     */
    public static boolean checkAllAreProcessRelevantData(
            List<Object> dropObjects, Process processForTargetTask) {
        if (dropObjects.size() < 1 || !(dropObjects.get(0) instanceof EObject)) {
            return false;
        }

        Package parentPkg = Xpdl2ModelUtil.getPackage(processForTargetTask);

        for (Iterator iterator = dropObjects.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (!(o instanceof ProcessRelevantData)) {
                return false;
            }

            ProcessRelevantData data = (ProcessRelevantData) o;

            Process proc = Xpdl2ModelUtil.getProcess((ProcessRelevantData) o);
            if (proc != null) {
                if (proc != processForTargetTask) {
                    // If this is a field/param in a process then it MUST be
                    // same process.
                    return false;
                }

            } else {
                // Not a field / param in a process - chekc process interface
                // params...
                // If its owned by a process interface then it must be the one
                // that target process implements.
                if (data.eContainer() instanceof ProcessInterface) {
                    ProcessInterface procIfc =
                            (ProcessInterface) data.eContainer();

                    String implementedIfc =
                            ProcessInterfaceUtil
                                    .getImplementedProcessInterfaceId(processForTargetTask);

                    if (!procIfc.getId().equals(implementedIfc)) {
                        return false;
                    }
                } else {
                    // If it's not a process field or a process interface param
                    // Then check if its a package field then must be in same
                    // package.
                    Package pkg =
                            Xpdl2ModelUtil.getPackage((ProcessRelevantData) o);
                    if (pkg != parentPkg) {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * Check that the given list contains only participants or data fields /
     * formal parameters of type Performer.
     * 
     * @param dropObjects
     * @return
     */
    public static boolean checkAllAreValidTaskParticipants(Process process,
            List<Object> dropObjects) {
        if (dropObjects.size() < 1 || !(dropObjects.get(0) instanceof EObject)) {
            return false;
        }

        String implementedIfc =
                ProcessInterfaceUtil.getImplementedProcessInterfaceId(process);

        Package pkg = Xpdl2ModelUtil.getPackage((EObject) dropObjects.get(0));

        // Check that all fields/params are performer type.
        for (Iterator iterator = dropObjects.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (o instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) o;

                BasicType basicType =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(data);

                if (basicType == null
                        || !BasicTypeType.PERFORMER_LITERAL.equals(basicType
                                .getType())) {
                    // Only performer data fields allowed.
                    return false;

                } else {
                    // If it's a proc interface performer param then make sure
                    // we implement the interface.
                    // If its owned by a process interface then it must be on
                    // that target process implements.
                    if (data.eContainer() instanceof ProcessInterface) {
                        ProcessInterface procIfc =
                                (ProcessInterface) data.eContainer();

                        if (!procIfc.getId().equals(implementedIfc)) {
                            return false;
                        }
                    }

                }

            } else if (!(o instanceof Participant)) {
                return false;
            }

            // Get parent process/package
            EObject parent = ((EObject) o).eContainer();
            while (parent != null && !(parent instanceof Process)
                    && !(parent instanceof Package)) {
                parent = parent.eContainer();
            }

            // If participant is package level then must be same package as
            // process.
            // If participant is process level then must be same process.
            if (parent == null) {
                return false;
            } else if (parent instanceof Package) {
                if (process.getPackage() != parent) {
                    return false;
                }
            } else if (parent instanceof Process) {
                if (process != parent) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Get default name of a Task that will call a sub-process.
     * 
     * @param process
     * @return
     */
    public static String getCallProcessName(NamedElement procOrIfc) {
        return procOrIfc.getName();
    }

    /**
     * Get drop-popup menu items appropriate for creating User Task with
     * parameters taken from data field / formal parameter dropObjects.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param dropObjects
     * @param location
     * @return
     */
    public static List<DropObjectPopupItem> getCreateTaskFromFieldsDropPopupItems(
            Process process, String laneId, List<Object> dropObjects,
            Point location) {

        List<DropObjectPopupItem> finalPopupItems =
                new ArrayList<DropObjectPopupItem>();

        List<ProcessRelevantData> dropDataItems =
                new ArrayList<ProcessRelevantData>();
        for (Object o : dropObjects) {
            if (o instanceof ProcessRelevantData) {
                dropDataItems.add((ProcessRelevantData) o);
            }
        }

        finalPopupItems.add(createTaskPopupItemForFieldParams(process,
                laneId,
                dropDataItems,
                location,
                ModeType.INOUT_LITERAL));

        finalPopupItems.add(createTaskPopupItemForFieldParams(process,
                laneId,
                dropDataItems,
                location,
                ModeType.IN_LITERAL));

        finalPopupItems.add(createTaskPopupItemForFieldParams(process,
                laneId,
                dropDataItems,
                location,
                ModeType.OUT_LITERAL));

        return finalPopupItems;
    }

    /**
     * Get the drop popup menu items for dropping participant on lane/embedded
     * sub-proc (i.e. items that will create tasks of various kinds).
     * <p>
     * There are several possible popup items for dropping participant(s) to
     * create tasks, here is the hierarchy...
     * 
     * <pre>
     *     - Create Task Sequence For Participants... (If there is more than one participant)
     *         - Create User Task Sequence For Participants
     *         - Create Service Task Sequence For Participants
     *         - Create Manual Task Sequence For Participants
     *         - Other...
     *             - Entry for each of the other task types
     *         
     *     - Create Unsequenced Tasks For Participants... (If there is more than one participant)
     *         - Create User Task For Each Participant
     *         - Create Service Task For Each Participant
     *         - Create Manual Task For Each Participant
     *         - Other...
     *             - Entry for each of the other task types
     *         
     *     - Create Single Task For Participant(s)... 
     *         - Create User Task For Participant(s)
     *         - Create Service Task For Participant(s)
     *         - Create Manual Task For Participant(s)
     *         - Other...
     *             - Entry for each of the other task types;
     * 
     * </pre>
     * 
     * <p>
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param dropObjects
     * @param location
     * @param actualTarget
     * 
     * @return
     */
    public static List<DropObjectPopupItem> getCreateTaskFromParticDropPopupItems(
            String laneId, List<Object> dropObjects, Point location,
            Object actualTarget) {

        List<DropObjectPopupItem> finalPopupItems =
                new ArrayList<DropObjectPopupItem>();

        Process process = getProcess(actualTarget);

        //
        // Create a list of popup items for creating task with multiple
        // participants...
        List<DropObjectPopupItem> multiParticSingleTaskItems =
                new ArrayList<DropObjectPopupItem>();

        Collection<String> performerIds = getPerformerIds(dropObjects);

        // Task type User, Manual and Service are most likely
        // options so put them at top.
        multiParticSingleTaskItems
                .add(createTaskPopupItemForParticipant(process,
                        laneId,
                        performerIds,
                        location,
                        TaskType.USER_LITERAL,
                        dropObjects));
        multiParticSingleTaskItems
                .add(createTaskPopupItemForParticipant(process,
                        laneId,
                        performerIds,
                        location,
                        TaskType.SERVICE_LITERAL,
                        dropObjects));
        multiParticSingleTaskItems
                .add(createTaskPopupItemForParticipant(process,
                        laneId,
                        performerIds,
                        location,
                        TaskType.MANUAL_LITERAL,
                        dropObjects));

        multiParticSingleTaskItems.add(DropObjectPopupItem
                .createSeparatorItem());

        // Put rest in a sub-menu
        List<DropObjectPopupItem> subItems =
                new ArrayList<DropObjectPopupItem>();

        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.NONE_LITERAL,
                dropObjects));
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.SEND_LITERAL,
                dropObjects));
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.RECEIVE_LITERAL,
                dropObjects));
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.SCRIPT_LITERAL,
                dropObjects));
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.SUBPROCESS_LITERAL,
                dropObjects));
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                dropObjects));
        /*
         * ABPM-911: Saket: Introducing a new type (event subprocess).
         */
        subItems.add(createTaskPopupItemForParticipant(process,
                laneId,
                performerIds,
                location,
                TaskType.EVENT_SUBPROCESS_LITERAL,
                dropObjects));

        multiParticSingleTaskItems.add(DropObjectPopupItem
                .createSubMenu(subItems,
                        Messages.DropObjectUtils_OtherTaskTypes_menu));

        //
        // Create popup items for creating sequence of tasks with each
        // participant in turn.
        if (performerIds.size() > 1) {

            //
            // So we have multiple participants and will have three sub-menus
            // Create Sequence of Tasks, Create unsequence task set and Create
            // Single Task with multi participants.

            // Create a sub-menu for creating sequence of given task type
            // These have to wrapped in a custom command that allows user to
            // select final sequence order of tasks.
            List<DropObjectPopupItem> taskSeqItems =
                    new ArrayList<DropObjectPopupItem>();

            taskSeqItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.USER_LITERAL));
            taskSeqItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.SERVICE_LITERAL));
            taskSeqItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.MANUAL_LITERAL));

            subItems = new ArrayList<DropObjectPopupItem>();
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.NONE_LITERAL));
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.SEND_LITERAL));
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.RECEIVE_LITERAL));
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.SCRIPT_LITERAL));
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.SUBPROCESS_LITERAL));
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.EMBEDDED_SUBPROCESS_LITERAL));

            /*
             * ABPM-911: Saket: Introducing a new type (event subprocess).
             */
            subItems.add(getTaskSeqFromParticDropCommand(process,
                    laneId,
                    dropObjects,
                    location,
                    TaskType.EVENT_SUBPROCESS_LITERAL));

            taskSeqItems.add(DropObjectPopupItem.createSubMenu(subItems,
                    Messages.DropObjectUtils_OtherTaskTypes_menu));

            finalPopupItems.add(DropObjectPopupItem.createSubMenu(taskSeqItems,
                    Messages.LocalDropObjectUtils_CreateTaskSeqForPartic_menu,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_TASKSEQUENCE)));

            //
            // Create a sub-menu for creating unsequenced set of given task
            // type (unless real target is sequence flow)
            if (!(actualTarget instanceof Transition)) {
                List<DropObjectPopupItem> taskUnseqItems =
                        new ArrayList<DropObjectPopupItem>();

                taskUnseqItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.USER_LITERAL,
                        false));
                taskUnseqItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.SERVICE_LITERAL,
                        false));
                taskUnseqItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.MANUAL_LITERAL,
                        false));

                subItems = new ArrayList<DropObjectPopupItem>();
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.NONE_LITERAL,
                        false));
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.SEND_LITERAL,
                        false));
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.RECEIVE_LITERAL,
                        false));
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.SCRIPT_LITERAL,
                        false));
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.SUBPROCESS_LITERAL,
                        false));
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                        false));

                /*
                 * ABPM-911: Saket: Introducing a new type (event subprocess).
                 */
                subItems.add(createMultiTasksForParticipants(process,
                        laneId,
                        dropObjects,
                        location,
                        TaskType.EVENT_SUBPROCESS_LITERAL,
                        false));

                taskUnseqItems.add(DropObjectPopupItem.createSubMenu(subItems,
                        Messages.DropObjectUtils_OtherTaskTypes_menu));

                finalPopupItems
                        .add(DropObjectPopupItem
                                .createSubMenu(taskUnseqItems,
                                        Messages.LocalDropObjectUtils_CreateUnseqTaskForPartics_menu,
                                        Xpdl2ProcessEditorPlugin
                                                .getDefault()
                                                .getImageRegistry()
                                                .get(ProcessEditorConstants.IMG_TASKUNSEQUENCED)));
            }

            // Create sub-menu for creating single task with multiple
            // participants.
            finalPopupItems
                    .add(DropObjectPopupItem
                            .createSubMenu(multiParticSingleTaskItems,
                                    Messages.LocalDropObjectUtils_CreateSingleTaskForPartic_menu,
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.IMG_MULTIPARTICIPANT)));

        } else {
            //
            // It there is only one participant selected then we don't need the
            // extra level of sub-menu.
            finalPopupItems.addAll(multiParticSingleTaskItems);
        }

        return finalPopupItems;
    }

    /**
     * @param actualTarget
     * @return
     */
    private static Process getProcess(Object actualTarget) {
        Process process = null;
        if (actualTarget instanceof EObject) {
            EObject eo = (EObject) actualTarget;
            process = Xpdl2ModelUtil.getProcess(eo);
        }
        return process;
    }

    /**
     * Get appropriate drop-popup menu items for dropping process on a
     * lane/embedded sub-process.
     * 
     * @param laneId
     *            Lane id or null for embedded sub-process.
     * @param dropObjects
     * @param location
     * @param destPkg
     * @param actualTarget
     * 
     * @return
     */
    public static List<DropObjectPopupItem> getCreateTaskFromProcessDropPopupItems(
            Process process, String laneId, List<Object> dropObjects,
            Point location, Package destPkg, Object actualTarget) {

        //
        // Ask user to confirm for 'need to create a project reference' if any
        // are dragged from different process.
        List<EObject> dropEObjs = new ArrayList<EObject>();
        for (Object o : dropObjects) {
            if (o instanceof EObject) {
                dropEObjs.add((EObject) o);
            }
        }

        if (!ProcessUIUtil.checkAndAddProjectReferences(Display.getDefault()
                .getActiveShell(), process, dropEObjs)) {
            return Collections.emptyList();
        }

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        if (dropObjects.size() == 1) {
            // If there's only one then we don't need a set order dialog.
            popupItems.add(createTaskFromProcessDropPopupItems(process,
                    laneId,
                    dropObjects,
                    location,
                    destPkg,
                    false));

        } else {

            // If there is more than one process we need to allow user to select
            // the order.
            popupItems
                    .add(DropObjectPopupItem
                            .createCustomCommand(new CreateSubProcSequencePopupCommand(
                                    process, laneId, dropObjects, location,
                                    destPkg),
                                    Messages.DropObjectUtils_CreateSubProcSeq_menu2,
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.IMG_TASKSEQUENCE)));

            // OR create unsequenced tasks - but not if actual target is a
            // sequence flow.
            if (!(actualTarget instanceof Transition)) {
                popupItems.add(createTaskFromProcessDropPopupItems(process,
                        laneId,
                        dropObjects,
                        location,
                        destPkg,
                        false));
            }
        }

        return popupItems;
    }

    /**
     * Get appropriate drop-popup menu items for dropping task library tasks
     * oonto a lane / embedded sub-process.
     * 
     * @param laneId
     *            Lane id or null for embedded sub-process.
     * @param dropObjects
     * @param location
     * @param destPkg
     * @param actualTarget
     * 
     * @return appropriate drop-popup menu items
     */
    public static List<DropObjectPopupItem> getCreateLibraryTaskReferenceDropPopupItems(
            Process process, String laneId, List<Object> dropObjects,
            Point location, Package destPkg, Object actualTarget) {

        //
        // Ask user to confirm for 'need to create a project reference' if any
        // are dragged from different process.
        List<EObject> dropEObjs = new ArrayList<EObject>();
        for (Object o : dropObjects) {
            if (o instanceof EObject) {
                dropEObjs.add((EObject) o);
            }
        }

        if (!ProcessUIUtil.checkAndAddProjectReferences(Display.getDefault()
                .getActiveShell(), process, dropEObjs)) {
            return Collections.emptyList();
        }

        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        if (dropObjects.size() == 1) {
            // If there's only one then we don't need a set order dialog.
            popupItems.add(createLibraryTaskReferencePopupItems(process,
                    laneId,
                    dropObjects,
                    location,
                    destPkg,
                    false));

        } else {
            // If there is more than one task we need to allow user to select
            // the order.
            popupItems
                    .add(DropObjectPopupItem
                            .createCustomCommand(new CreateRefTaskSequencePopupCommand(
                                    process, laneId, dropObjects, location,
                                    destPkg),
                                    Messages.LocalDropObjectUtils_CreateRefTaskSequence_title,
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.IMG_TASKSEQUENCE)));

            // OR create unsequenced tasks - but not if actual target is a
            // sequence flow.
            if (!(actualTarget instanceof Transition)) {
                popupItems.add(createLibraryTaskReferencePopupItems(process,
                        laneId,
                        dropObjects,
                        location,
                        destPkg,
                        false));
            }
        }

        return popupItems;
    }

    /**
     * Create a single DropPopupItem that represents a create diagram objects
     * item to create a set of tasks (sequenced or unsequenced) - one for each
     * participant.
     * 
     * @param laneId
     * @param dropObjects
     * @param location
     * @param taskType
     * @param createSequence
     * @return
     */
    private static DropObjectPopupItem createMultiTasksForParticipants(
            Process process, String laneId, List<Object> dropObjects,
            Point location, TaskType taskType, boolean createSequence) {

        //
        // Create a list of activities of given task type, each in turn with an
        // assigned participant.
        List<Activity> activities = new ArrayList<Activity>();

        Collection<String> performerIds = getPerformerIds(dropObjects);

        Point taskLoc = location.getCopy();

        int idx = 0;

        for (String perfId : performerIds) {

            List<String> thisTaskPerf = new ArrayList<String>();
            thisTaskPerf.add(perfId);
            List<Object> thisTaskDropPerfs = new ArrayList<Object>();
            thisTaskDropPerfs.add(dropObjects.get(idx));

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {
                taskLoc.x +=
                        ProcessWidgetConstants.EMBSUBFLOW_WIDTH_SIZE
                                + (ProcessWidgetConstants.TASK_WIDTH_SIZE / 2);
            } else {
                taskLoc.x +=
                        (int) (ProcessWidgetConstants.TASK_WIDTH_SIZE * 1.5f);
            }

            Activity act =
                    createTaskForParticipants(process,
                            laneId,
                            thisTaskPerf,
                            taskLoc,
                            taskType,
                            thisTaskDropPerfs);

            String token = getParticTaskName(dropObjects.get(idx));
            String name = getParticTaskDisplayName(dropObjects.get(idx));
            Xpdl2ModelUtil
                    .setOtherAttribute(act, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);
            act.setName(NameUtil.getInternalName(token, false));

            activities.add(act);

            idx++;
        }

        List<Object> createObjs = new ArrayList<Object>();
        createObjs.addAll(activities);

        if (createSequence) {
            //
            // Join activities in given sequence.
            List<Transition> transitions =
                    DiagramDropObjectUtils
                            .joinActivitiesWithTransitions(activities);

            createObjs.addAll(transitions);

            return DropObjectPopupItem
                    .createCreateDiagramObjectsItem(createObjs,
                            getParticSeqItemPopupItemLabel(taskType),
                            DiagramDropObjectUtils
                                    .getImageForTaskType(taskType));

        }

        // Unsequence list of activities.
        return DropObjectPopupItem.createCreateDiagramObjectsItem(createObjs,
                getParticUnSeqPopupItemLabel(taskType),
                DiagramDropObjectUtils.getImageForTaskType(taskType));
    }

    /**
     * Create a task of given type and assign given participants.
     * 
     * @param laneId
     * @param performerIds
     * @param location
     * @param taskType
     * @return
     */
    private static Activity createTaskForParticipants(Process process,
            String laneId, Collection<String> performerIds, Point location,
            TaskType taskType, List<Object> dropPerformers) {
        Activity act =
                DiagramDropObjectUtils.createTaskObject(process,
                        laneId,
                        location,
                        taskType);

        // Add the performers.
        Performers performers = Xpdl2Factory.eINSTANCE.createPerformers();

        for (String perfId : performerIds) {
            Performer performer = Xpdl2Factory.eINSTANCE.createPerformer();
            performer.setValue(perfId);
            performers.getPerformers().add(performer);

        }

        int i = 0;
        String taskName = ""; //$NON-NLS-1$
        String tokenName = ""; //$NON-NLS-1$
        for (Object perf : dropPerformers) {
            if (perf instanceof NamedElement) {
                NamedElement named = (NamedElement) perf;
                if (i > 0) {
                    taskName += ", "; //$NON-NLS-1$
                }
                String token = named.getName();
                String display =
                        (String) Xpdl2ModelUtil.getOtherAttribute(named,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                if (display == null || display.length() == 0) {
                    display = token;
                }
                taskName += display;
                tokenName += token;

                i++;
            }
        }

        act.setPerformers(performers);
        act.setName(NameUtil.getInternalName(tokenName, false));
        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                taskName);
        return act;
    }

    /**
     * Get the drop popup menu items for dropping process on lane/embedded
     * sub-proc (i.e. items that will create independent sub-proc tasks).
     * <p>
     * If there is more than one process then a string of tasks is created.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param dropObjects
     * @param location
     * @param createSequence
     * @return
     */
    private static DropObjectPopupItem createTaskFromProcessDropPopupItems(
            Process process, String laneId, List<Object> dropObjects,
            Point location, Package destPkg, boolean createSequence) {

        // For drop process we create an independent sub-process task (for each
        // selected process). And also append a command to add any extra
        // external package references to Working Copy and Package.
        CompoundCommand extraStuffCommand = new CompoundCommand();

        Xpdl2WorkingCopyImpl thisPkgWorkingCopy =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                        .getWorkingCopyFor(destPkg);

        List<Activity> activities = new ArrayList<Activity>();

        Point taskLoc = location.getCopy();

        boolean isTargetPageflow = Xpdl2ModelUtil.isPageflow(process);

        for (Object obj : dropObjects) {
            boolean isProcessInterface = obj instanceof ProcessInterface;
            boolean isBusinessProcess = false;
            boolean isPageflowProcess = false;
            if (obj instanceof Process) {
                if (Xpdl2ModelUtil.isPageflow((Process) obj)) {
                    isPageflowProcess = true;
                } else {
                    isBusinessProcess = true;
                }
            }
            if (isBusinessProcess || isProcessInterface || isPageflowProcess) {
                NamedElement procOrIfc = (NamedElement) obj;

                taskLoc.x +=
                        (int) (ProcessWidgetConstants.TASK_WIDTH_SIZE * 1.5f);

                if (isPageflowProcess && !isTargetPageflow) {
                    Activity act =
                            DiagramDropObjectUtils.createTaskObject(process,
                                    laneId,
                                    taskLoc,
                                    TaskType.USER_LITERAL);
                    activities.add(act);

                    act.setName(NameUtil
                            .getInternalName(getCallProcessName(procOrIfc),
                                    false));
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    Xpdl2ModelUtil.setOtherAttribute(act,
                            ea,
                            Xpdl2ModelUtil.getOtherAttribute(procOrIfc, ea));

                    // Set reference to pageflow
                    FormImplementation formImpl =
                            XpdExtensionFactory.eINSTANCE
                                    .createFormImplementation();
                    formImpl.setFormType(FormImplementationType.PAGEFLOW);
                    formImpl.setFormURI(TaskObjectUtil
                            .getUserTaskFormURIFromPageflowProcess((Process) procOrIfc));
                    Xpdl2ModelUtil.setOtherElement(((Task) act
                            .getImplementation()).getTaskUser(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_FormImplementation(),
                            formImpl);

                    // Bit of a bodge but we have to be able to re-locate the
                    // COPIED AND PASTED version of act so that we can add extra
                    // stuff to final activity. So add something we can use to
                    // identify the activity
                    String addedActId = XpdEcoreUtil.generateUUID();
                    ExtendedAttribute addedActExtAttr =
                            Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                    addedActExtAttr.setName(addedActId);
                    act.getExtendedAttributes().add(addedActExtAttr);

                    Command addExtraDataCmd =
                            new AbstractLateExecuteCommand(
                                    thisPkgWorkingCopy.getEditingDomain(),
                                    new Object[] { addedActId, procOrIfc,
                                            process }) {

                                @Override
                                protected Command createCommand(
                                        EditingDomain editingDomain,
                                        Object contextObject) {
                                    String addedActId =
                                            (String) ((Object[]) contextObject)[0];
                                    Process pageflowProcess =
                                            (Process) ((Object[]) contextObject)[1];
                                    Process targetProcess =
                                            (Process) ((Object[]) contextObject)[2];

                                    // Locate the activity
                                    Activity userTaskActivity = null;

                                    CompoundCommand cmd = new CompoundCommand();

                                    Collection<Activity> allActs =
                                            Xpdl2ModelUtil
                                                    .getAllActivitiesInProc(targetProcess);
                                    for (Activity a : allActs) {
                                        ExtendedAttribute addedActExtAttr =
                                                TaskObjectUtil
                                                        .getExtendedAttributeByName(a,
                                                                addedActId);
                                        if (addedActExtAttr != null) {
                                            userTaskActivity = a;
                                            // We can ditch the added ext attr
                                            // now
                                            cmd.append(RemoveCommand
                                                    .create(editingDomain,
                                                            addedActExtAttr));
                                        }
                                    }

                                    if (userTaskActivity != null) {
                                        Command c =
                                                PageflowUtil
                                                        .getCreateUserTaskDataForPageflowCommand(editingDomain,
                                                                userTaskActivity,
                                                                targetProcess,
                                                                pageflowProcess,
                                                                true);
                                        if (c != null) {
                                            cmd.append(c);
                                        }
                                        return c;
                                    }

                                    return UnexecutableCommand.INSTANCE;
                                }

                            };

                    extraStuffCommand.append(addExtraDataCmd);

                } else if (((isPageflowProcess || isBusinessProcess || isProcessInterface) && isTargetPageflow)
                        || ((isBusinessProcess || isProcessInterface) && !isTargetPageflow)) {

                    /*
                     * XPD-7516: Saket: Need to allow drag-n-drop of 'service
                     * process interface' on pageflow processes.
                     */

                    Activity act =
                            DiagramDropObjectUtils.createTaskObject(process,
                                    laneId,
                                    taskLoc,
                                    TaskType.SUBPROCESS_LITERAL);
                    activities.add(act);

                    act.setName(NameUtil
                            .getInternalName(getCallProcessName(procOrIfc),
                                    false));
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    Xpdl2ModelUtil.setOtherAttribute(act,
                            ea,
                            Xpdl2ModelUtil.getOtherAttribute(procOrIfc, ea));

                    SubFlow subFlow = (SubFlow) act.getImplementation();

                    // Get the working copy for the process's package and check
                    // whether it's the same as ours.
                    WorkingCopy externalPkgWorkingCopy =
                            WorkingCopyUtil.getWorkingCopyFor(procOrIfc);

                    if (thisPkgWorkingCopy == externalPkgWorkingCopy) {
                        // No need for extra package refs.
                        subFlow.setProcessId(procOrIfc.getId());
                    } else {
                        // Create external package ref and tell our working copy
                        // about it.
                        String refId =
                                thisPkgWorkingCopy
                                        .appendCreateReferenceCommand(externalPkgWorkingCopy,
                                                extraStuffCommand);
                        subFlow.setProcessId(procOrIfc.getId());
                        subFlow.setPackageRefId(refId);
                    }
                }
            }
        } // Next process.

        // Now join multiple tasks with transitions.
        List<Object> createObjects = new ArrayList<Object>();
        createObjects.addAll(activities);

        String label;
        Image img;

        if (createSequence && activities.size() > 1) {
            List<Transition> transitions =
                    DiagramDropObjectUtils
                            .joinActivitiesWithTransitions(activities);

            createObjects.addAll(transitions);

            label = Messages.DropObjectUtils_CreateSubProcSeq_menu2;
            img =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_TASKSEQUENCE);

        } else {
            if (activities.size() == 1) {
                label = Messages.DropObjectUtils_CreateSubProcTask_menu2;
                img =
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskType.SUBPROCESS_LITERAL);
            } else {
                label = Messages.DropObjectUtils_CreateUnseqSubProc_menu2;
                img =
                        Xpdl2ProcessEditorPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessEditorConstants.IMG_TASKUNSEQUENCED);
            }
        }

        // Create the drop popup menu item
        DropObjectPopupItem popupItem;

        if (!extraStuffCommand.isEmpty()) {
            // We have external refs to add also.
            popupItem =
                    DropObjectPopupItem
                            .createCreateDiagramObjectsItem(createObjects,
                                    extraStuffCommand,
                                    label,
                                    img);
        } else {
            popupItem =
                    DropObjectPopupItem
                            .createCreateDiagramObjectsItem(createObjects,
                                    label,
                                    img);
        }

        return popupItem;
    }

    /**
     * Get the drop popup menu items for dropping task library tasks on
     * lane/embedded sub-proc (i.e. items that will create reference tasks to
     * the library tasks)
     * <p>
     * If there is more than one task then a string of tasks is created.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param dropObjects
     * @param location
     * @param createSequence
     * 
     * @return The drop popup items.
     */
    private static DropObjectPopupItem createLibraryTaskReferencePopupItems(
            Process process, String laneId, List<Object> dropObjects,
            Point location, Package destPkg, boolean createSequence) {
        //
        // For drop task we create a reference task for each library task).
        //
        // Create a list of activities of given task type, each in turn with an
        // assigned participant.
        List<Activity> activitiesToAdd = new ArrayList<Activity>();

        Point taskLoc = location.getCopy();

        //
        // Command to add package reference / copies of task library data if
        // necessary.
        CompoundCommand extraCommands = new CompoundCommand();
        // And Empty compound can't be executed so make sure it's got a dummy
        // command in in case we don't have to add any.
        extraCommands.append(new AbstractCommand() {

            @Override
            public void execute() {
            }

            @Override
            public boolean canExecute() {
                return true;
            }

            @Override
            public void undo() {
            }

            @Override
            public void redo() {
            }
        });

        //
        // Go thru dropped library tasks creating a reference task for each one.
        String pkgRefId = null;
        Set<ProcessRelevantData> libraryDataToCopy =
                new HashSet<ProcessRelevantData>();

        EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(process);

        for (Object o : dropObjects) {
            Activity libraryTask = (Activity) o;

            //
            // First time thru get the package reference for the task library
            // and add an external reference element for it (if one doesn't
            // already exit in the target process pkg.
            if (pkgRefId == null) {
                WorkingCopy externalWc =
                        WorkingCopyUtil.getWorkingCopyFor(libraryTask
                                .getProcess());
                Xpdl2WorkingCopyImpl wc =
                        (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                                .getWorkingCopyFor(process);
                if (wc != externalWc) {
                    pkgRefId =
                            wc.appendCreateReferenceCommand(externalWc,
                                    extraCommands);
                }
            }

            //
            // Create the new reference task activity.
            taskLoc.x += (int) (ProcessWidgetConstants.TASK_WIDTH_SIZE * 1.5f);

            Activity newReferenceTask =
                    DiagramDropObjectUtils.createTaskObject(process,
                            laneId,
                            taskLoc,
                            TaskType.REFERENCE_LITERAL);

            activitiesToAdd.add(newReferenceTask);

            //
            // Setup the reference task.

            // Reference
            Reference ref =
                    ReferenceTaskUtil.createLibraryTaskReference(libraryTask
                            .getId(), libraryTask.getProcess(), pkgRefId);
            newReferenceTask.setImplementation(ref);

            // Name
            String displayName =
                    ReferenceTaskUtil.getDefaultReferenceTaskName(libraryTask);
            Xpdl2ModelUtil
                    .setOtherAttribute(newReferenceTask,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            displayName);
            newReferenceTask.setName(NameUtil.getInternalName(displayName,
                    false));

            // Collect the library data to add (multiple library tasks may ref
            // same library data so gather the unique set we need to check if
            // needs adding).
            List<ProcessRelevantData> refdLibraryData =
                    ReferenceTaskUtil.getLibraryTaskReferencedData(libraryTask);
            if (refdLibraryData != null && !refdLibraryData.isEmpty()) {
                libraryDataToCopy.addAll(refdLibraryData);
            }

        }

        //
        // Add command to add task library data ref'd by the library tasks (if
        // there is any to copy)
        if (!libraryDataToCopy.isEmpty()) {
            Command copyTaskLibraryDataCmd =
                    ReferenceTaskUtil
                            .getAddTaskLibraryDataToProcessCommand(editingDomain,
                                    process,
                                    libraryDataToCopy);
            if (copyTaskLibraryDataCmd != null) {
                extraCommands.append(copyTaskLibraryDataCmd);
            }
        }

        //
        // Create the drop object popup items to create sequence / unsequenced
        // tasks.

        List<Object> createObjs = new ArrayList<Object>();
        createObjs.addAll(activitiesToAdd);

        if (createSequence) {
            //
            // Join activities in given sequence.
            List<Transition> transitions =
                    DiagramDropObjectUtils
                            .joinActivitiesWithTransitions(activitiesToAdd);

            createObjs.addAll(transitions);

            return DropObjectPopupItem
                    .createCreateDiagramObjectsItem(createObjs,
                            extraCommands,
                            Messages.LocalDropObjectUtils_CreateRefTaskSequence_menu,
                            DiagramDropObjectUtils
                                    .getImageForTaskType(TaskType.REFERENCE_LITERAL));
        }

        // Unsequence list of activities.
        return DropObjectPopupItem.createCreateDiagramObjectsItem(createObjs,
                extraCommands,
                Messages.LocalDropObjectUtils_CreateRefTasks_menu,
                DiagramDropObjectUtils
                        .getImageForTaskType(TaskType.REFERENCE_LITERAL));
    }

    /**
     * Create a drop creation object for creation of a user task with parameters
     * of given mode type for given data fields/formal parameters.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param performerIds
     * @param dropDataItems
     * @param paramType
     * @return
     */
    public static DropObjectPopupItem createTaskPopupItemForFieldParams(
            Process process, String laneId,
            List<ProcessRelevantData> dropDataItems, Point location,
            ModeType paramType) {

        Activity act =
                DiagramDropObjectUtils.createTaskObject(process,
                        laneId,
                        location,
                        TaskType.USER_LITERAL);

        /*
         * XPD-5030: Saket: DND of Parameters/DataFields/BOM
         * entity/CorrelationField on process Lane should create User tasks
         * having names in increments.
         */
        setUniqueNameForActivity(act, process);

        /* TaskUser taskUser = ((Task) act.getImplementation()).getTaskUser(); */

        AssociatedParameters assocParams =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameters();
        addDataItemsToAssocParams(assocParams, dropDataItems, paramType, act);
        Xpdl2ModelUtil.setOtherElement(act, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_AssociatedParameters(), assocParams);

        String label;
        Image img;
        if (ModeType.IN_LITERAL.equals(paramType)) {
            label =
                    String.format(Messages.LocalDropObjectUtils_CreateTaskToViewData_menu,
                            TaskType.USER_LITERAL.toString());
            img =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_USERTASK_DATA_IN);

        } else if (ModeType.OUT_LITERAL.equals(paramType)) {
            label =
                    String.format(Messages.LocalDropObjectUtils_CreateTaskToAssignData_menu,
                            TaskType.USER_LITERAL.toString());
            img =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_USERTASK_DATA_OUT);

        } else {
            label =
                    String.format(Messages.LocalDropObjectUtils_CreateTaskToViewAssignData_menu,
                            TaskType.USER_LITERAL.toString());
            img =
                    Xpdl2ProcessEditorPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessEditorConstants.IMG_USERTASK_DATA_INOUT);

        }

        List<Object> createObjs = new ArrayList<Object>();
        createObjs.add(act);

        // add any new params/data fields to process
        for (ProcessRelevantData data : dropDataItems) {
            if (data.eContainer() == null) {
                createObjs.add(data);
            }
        }
        return DropObjectPopupItem.createCreateDiagramObjectsItem(createObjs,
                label,
                img);
    }

    /**
     * 
     * XPD-5030: Saket: Sets a unique name for the activity created after DND of
     * Parameters/DataFields/BOM entity/CorrelationField/Participant on process
     * Lane.
     * 
     * @param act
     * @param process
     */
    public static void setUniqueNameForActivity(Activity act, Process process) {
        String uniqueLabelInSet =
                Xpdl2ModelUtil.getUniqueLabelInSet(Xpdl2ModelUtil
                        .getDisplayNameOrName(act), Xpdl2ModelUtil
                        .getAllActivitiesInProc(process));

        Xpdl2ModelUtil.setOtherAttribute(act,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                uniqueLabelInSet);

        act.setName(NameUtil.getInternalName(uniqueLabelInSet, false));
    }

    /**
     * Create a drop creation object for a task of given type from this
     * participant.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param performerIds
     * @param location
     * @param taskType
     * @return
     */
    private static DropObjectPopupItem createTaskPopupItemForParticipant(
            Process process, String laneId, Collection<String> performerIds,
            Point location, TaskType taskType, List<Object> dropPerformers) {

        Activity act =
                createTaskForParticipants(process,
                        laneId,
                        performerIds,
                        location,
                        taskType,
                        dropPerformers);

        /*
         * XPD-5030: Saket: DND of Participants on process Lane should create
         * activities having names in increments.
         */
        setUniqueNameForActivity(act, process);

        String label =
                String.format(Messages.Xpdl2LaneAdapter_CreateTaskForPartics_menu,
                        taskType.toString()).toString();

        List<Object> createObjs = new ArrayList<Object>();
        createObjs.add(act);

        Image img = DiagramDropObjectUtils.getImageForTaskType(taskType);

        return DropObjectPopupItem.createCreateDiagramObjectsItem(createObjs,
                label,
                img);
    }

    /**
     * Returns a copy of the {@link AssociatedParameters} if
     * 'currentAssocParams' is not <code>null</code> else creates and returns a
     * new instance of {@link AssociatedParameters}.
     * 
     * @param editingDomain
     * @param currentAssocParams
     *            the AssociatedParameters whose copy needs to be returned.
     * 
     * @return {@link AssociatedParameters}
     */
    public static AssociatedParameters copyOrCreateAssocParams(
            EditingDomain editingDomain, AssociatedParameters currentAssocParams) {
        AssociatedParameters newAssocParams = null;

        if (currentAssocParams != null) {
            List<EObject> msgs = new ArrayList<EObject>();
            msgs.add(currentAssocParams);

            CopyCommand cpyCmd =
                    (CopyCommand) CopyCommand.create(editingDomain, msgs);
            if (cpyCmd.canExecute()) {
                cpyCmd.execute();

                newAssocParams =
                        (AssociatedParameters) cpyCmd.getResult().iterator()
                                .next();
            }
        } else {
            /*
             * if currentAssocParams == null then create a new instance of
             * Associated Parameters.
             */
            newAssocParams =
                    XpdExtensionFactory.eINSTANCE.createAssociatedParameters();
        }
        return newAssocParams;
    }

    /**
     * @param editingDomain
     * @param dropObjects
     * @return
     */
    public static List<DropObjectPopupItem> createAddTaskParamsDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            Activity activity) {
        List<DropObjectPopupItem> popupItems =
                new ArrayList<DropObjectPopupItem>();

        TaskUser taskUser = ((Task) activity.getImplementation()).getTaskUser();

        // We'll keep, things simple and just re-create the msgin/out
        // completely.

        // This is the new set of assoc params if the user chooses the Assign IN
        // PArams to task.
        AssociatedParameters newInAssociatedParams =
                copyOrCreateAssocParams(editingDomain,
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters()));

        // This is the new set of assoc params if the user chooses the Assign
        // INOUT PArams to task.
        AssociatedParameters newInOutAssociatedParams =
                copyOrCreateAssocParams(editingDomain,
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters()));

        // This is the new set of assoc params if the user chooses the Assign
        // OUT PArams to task.
        AssociatedParameters newOutAssociatedParams =
                copyOrCreateAssocParams(editingDomain,
                        (AssociatedParameters) Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters()));

        if (newInAssociatedParams != null && newInOutAssociatedParams != null
                && newOutAssociatedParams != null) {
            //
            // Provide options to add fields to inout, in and out
            //
            List<ProcessRelevantData> dropDataItems =
                    new ArrayList<ProcessRelevantData>();
            for (Object o : dropObjects) {
                if (o instanceof ProcessRelevantData) {
                    dropDataItems.add((ProcessRelevantData) o);
                }
            }

            LocalDropObjectUtils
                    .addDataItemsToAssocParams(newInAssociatedParams,
                            dropDataItems,
                            ModeType.IN_LITERAL,
                            activity);

            LocalDropObjectUtils
                    .addDataItemsToAssocParams(newInOutAssociatedParams,
                            dropDataItems,
                            ModeType.INOUT_LITERAL,
                            activity);

            LocalDropObjectUtils
                    .addDataItemsToAssocParams(newOutAssociatedParams,
                            dropDataItems,
                            ModeType.OUT_LITERAL,
                            activity);

            // Option: Add fields to in and out parameters
            Command inOutCmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            newInOutAssociatedParams);

            popupItems.add(DropObjectPopupItem.createCommandItem(inOutCmd,
                    Messages.Xpdl2TaskAdapter_AddDataToInOutParams_menu,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_DATA_INOUT)));

            // Option: Add fields to in parameters.
            Command inCmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            newInAssociatedParams);

            popupItems.add(DropObjectPopupItem.createCommandItem(inCmd,
                    Messages.Xpdl2TaskAdapter_AddDataToInParams_menu,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_DATA_IN)));

            // Option: Add fields to out parameters.
            Command outCmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            newOutAssociatedParams);

            popupItems.add(DropObjectPopupItem.createCommandItem(outCmd,
                    Messages.Xpdl2TaskAdapter_AddDataToOutParams_menu,
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_DATA_OUT)));
        }
        return popupItems;
    }

    private static String getParticSeqItemPopupItemLabel(TaskType taskType) {
        return String
                .format(Messages.LocalDropObjectUtils_CreateTaskSeqForPartics_menu,
                        taskType.toString());
    }

    /**
     * Get task name that should be set when task created / partic set from
     * given participant/performer field.
     * 
     * @param element
     * @return
     */
    private static String getParticTaskName(Object element) {
        String name = ""; //$NON-NLS-1$

        if (element instanceof Participant) {
            name = ((Participant) element).getName();
        } else if (element instanceof ProcessRelevantData) {
            name = ((ProcessRelevantData) element).getName();
        }
        return name;
    }

    /**
     * Get task name that should be set when task created / partic set from
     * given participant/performer field.
     * 
     * @param element
     * @return
     */
    private static String getParticTaskDisplayName(Object element) {
        String name = ""; //$NON-NLS-1$

        if (element instanceof NamedElement) {
            NamedElement named = (NamedElement) element;
            name =
                    (String) Xpdl2ModelUtil.getOtherAttribute(named,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            if (name == null || name.length() == 0) {
                name = named.getName();
            }
        }
        return name;
    }

    private static String getParticUnSeqPopupItemLabel(TaskType taskType) {
        return String
                .format(Messages.LocalDropObjectUtils_CreateTaskForEachPartic_menu,
                        taskType.toString());
    }

    /**
     * Get a list of task performer ids from the list of
     * participants/datafields/formal params.
     * 
     * @param dropObjects
     * @return
     */
    private static Collection<String> getPerformerIds(List<Object> dropObjects) {
        List<String> ids = new ArrayList<String>();

        for (Object o : dropObjects) {
            if (o instanceof Participant) {
                ids.add(((Participant) o).getId());

            } else if (o instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) o;

                BasicType basicType =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(data);

                if (basicType != null
                        && BasicTypeType.PERFORMER_LITERAL.equals(basicType
                                .getType())) {
                    ids.add(data.getId());
                }
            }

        }
        return ids;
    }

    /**
     * Get the drop popup item that displays a select sequence for tasks dialog
     * and then creates the tasks in that sequence.
     * 
     * @param laneId
     * @param dropObjects
     * @param location
     * @param taskType
     * @return
     */
    private static DropObjectPopupItem getTaskSeqFromParticDropCommand(
            Process process, String laneId, List<Object> dropObjects,
            Point location, TaskType taskType) {
        return DropObjectPopupItem
                .createCustomCommand(new CreateParticTaskSequencePopupCommand(
                        process, laneId, dropObjects, location, taskType),
                        getParticSeqItemPopupItemLabel(taskType),
                        DiagramDropObjectUtils.getImageForTaskType(taskType));
    }

    /**
     * Create a {@link DropObjectPopupItem} for creating an object and a
     * connection to it according to the creiteria in cacPair.
     * 
     * @param editingDomain
     * @param cacPair
     *            Defines the new object type and connection type.
     * @param adjustableRelativeLocation
     *            Passed as initial drop location relative to target container,
     *            can be used to adjust the position to execute the drop (for
     *            centering object etc).
     * @param process
     *            Parent process.
     * @param actualDropTarget
     *            The model object that was actuall dropped upon (could be
     *            sequence flow or same as actaulDropTargetContainer).
     * @param actualDropTargetContainer
     *            The container of the new object.
     * @param connectionSourceObject
     *            The object that should be the source of the new connection (if
     *            null then no connection is created - this should normally be
     *            when actualDropTarget is a sequence flow to insert on.
     * 
     * @return popup item or null if cannot be handled.
     */
    public static DropObjectPopupItem getCreateAndConnectDropPopupItem(
            EditingDomain editingDomain, CreateAndConnectObjectPair cacPair,
            Point adjustableRelativeLocation, Process process,
            Object actualDropTarget, Object actualDropTargetContainer,
            Object connectionSourceObject) {

        /*
         * Create new object of required type...
         */
        NamedElement newObject = null;
        Image menuIcon = null;
        String menuLabel = null;
        Dimension newObjectSize = new Dimension(0, 0);

        String laneId = null;
        if (actualDropTargetContainer instanceof Lane) {
            laneId = ((Lane) actualDropTargetContainer).getId();
        }

        if (CreateAndConnectObjectType.TASK.equals(cacPair.getObjectType())) {
            newObjectSize.width = ProcessWidgetConstants.TASK_WIDTH_SIZE;
            newObjectSize.height = ProcessWidgetConstants.TASK_HEIGHT_SIZE;

            TaskType taskType =
                    DecisionFlowUtil.isDecisionFlow(process) ? TaskType.DTABLE_LITERAL
                            : TaskType.NONE_LITERAL;
            newObject =
                    DiagramDropObjectUtils.createTaskObject(process,
                            laneId,
                            adjustableRelativeLocation,
                            taskType);

            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            String name =
                    Xpdl2ModelUtil.getUniqueDisplayNameInSet(Xpdl2ModelUtil
                            .getDisplayNameOrName(newObject), acts, true);
            Xpdl2ModelUtil
                    .setOtherAttribute(newObject, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);

            newObject.setName(NameUtil.getInternalName(name, false));

            menuLabel = Messages.LocalDropObjectUtils_CreateAndConnectTask_menu;
            menuIcon = DiagramDropObjectUtils.getImageForTaskType(taskType);

        } else if (CreateAndConnectObjectType.GATEWAY.equals(cacPair
                .getObjectType())) {
            newObjectSize.width = ProcessWidgetConstants.GATEWAY_WIDTH_SIZE;
            newObjectSize.height = ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE;

            newObject =
                    DiagramDropObjectUtils.createGatewayObject(process,
                            laneId,
                            adjustableRelativeLocation,
                            GatewayType.EXCLUSIVE_DATA_LITERAL);

            menuLabel =
                    Messages.LocalDropObjectUtils_CreateAndConnectGateway_menu;
            menuIcon =
                    DiagramDropObjectUtils
                            .getImageForGatewayType(GatewayType.EXCLUSIVE_DATA_LITERAL);

        } else if (CreateAndConnectObjectType.START_EVENT.equals(cacPair
                .getObjectType())) {
            newObjectSize.width = ProcessWidgetConstants.START_EVENT_SIZE;
            newObjectSize.height = ProcessWidgetConstants.START_EVENT_SIZE;

            newObject =
                    DiagramDropObjectUtils.createEventObject(process,
                            laneId,
                            adjustableRelativeLocation,
                            EventFlowType.FLOW_START_LITERAL,
                            EventTriggerType.EVENT_NONE_LITERAL);

            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            String name =
                    Xpdl2ModelUtil.getUniqueDisplayNameInSet(Xpdl2ModelUtil
                            .getDisplayNameOrName(newObject), acts, true);
            Xpdl2ModelUtil
                    .setOtherAttribute(newObject, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);

            newObject.setName(NameUtil.getInternalName(name, false));

            menuLabel = Messages.LocalDropObjectUtils_CreateStartEvent_menu;
            menuIcon =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_START_LITERAL,
                                    EventTriggerType.EVENT_NONE_LITERAL);

        } else if (CreateAndConnectObjectType.INTERMEDIATE_EVENT.equals(cacPair
                .getObjectType())) {
            newObjectSize.width =
                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE;
            newObjectSize.height =
                    ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE;

            newObject =
                    DiagramDropObjectUtils.createEventObject(process,
                            laneId,
                            adjustableRelativeLocation,
                            EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                            EventTriggerType.EVENT_NONE_LITERAL);

            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            String name =
                    Xpdl2ModelUtil.getUniqueDisplayNameInSet(Xpdl2ModelUtil
                            .getDisplayNameOrName(newObject), acts, true);
            Xpdl2ModelUtil
                    .setOtherAttribute(newObject, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);

            newObject.setName(NameUtil.getInternalName(name, false));

            menuLabel =
                    Messages.LocalDropObjectUtils_CreateAndConnectEvent_menu;
            menuIcon =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                    EventTriggerType.EVENT_NONE_LITERAL);

        } else if (CreateAndConnectObjectType.END_EVENT.equals(cacPair
                .getObjectType())) {
            newObjectSize.width = ProcessWidgetConstants.END_EVENT_SIZE;
            newObjectSize.height = ProcessWidgetConstants.END_EVENT_SIZE;

            newObject =
                    DiagramDropObjectUtils.createEventObject(process,
                            laneId,
                            adjustableRelativeLocation,
                            EventFlowType.FLOW_END_LITERAL,
                            EventTriggerType.EVENT_NONE_LITERAL);

            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            String name =
                    Xpdl2ModelUtil.getUniqueDisplayNameInSet(Xpdl2ModelUtil
                            .getDisplayNameOrName(newObject), acts, true);
            Xpdl2ModelUtil
                    .setOtherAttribute(newObject, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);

            newObject.setName(NameUtil.getInternalName(name, false));

            menuLabel =
                    Messages.LocalDropObjectUtils_CreateAndConnectEndEvent_menu;
            menuIcon =
                    DiagramDropObjectUtils
                            .getImageForEventType(EventFlowType.FLOW_END_LITERAL,
                                    EventTriggerType.EVENT_NONE_LITERAL);

        } else if (CreateAndConnectObjectType.ANNOTATION.equals(cacPair
                .getObjectType())) {
            newObjectSize.height = ProcessWidgetConstants.NOTE_HEIGHT;

            newObject =
                    DiagramDropObjectUtils.createAnnotationObject(process,
                            laneId,
                            adjustableRelativeLocation);

            menuLabel =
                    Messages.LocalDropObjectUtils_CreateAndConnectAnnotation_menu;
            menuIcon =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_TOOL_NOTE_16);

        } else if (CreateAndConnectObjectType.DATAOBJECT.equals(cacPair
                .getObjectType())) {
            newObjectSize.width = ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE;
            newObjectSize.height =
                    ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE;

            newObject =
                    DiagramDropObjectUtils.createDataObject(process,
                            laneId,
                            adjustableRelativeLocation);

            menuLabel =
                    Messages.LocalDropObjectUtils_CreateAndConnectDataObject_menu;
            menuIcon =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_TOOL_DATA_OBJECT_16);

        }

        /*
         * It is all a bit hideously intertwined but basically, the drop
         * location we adjust to here will be used to position the new object
         * BUT the process widget's
         * DropObjectPopupItemUtil.executeDropPopupItem() is designed to say "if
         * you drop the object in container then use drop location as top left,
         * otherwise if dropping on a connection then center new object on drop
         * point".
         * 
         * For the purposes of create and connect gadget we ALWAYS want to
         * centre. So adjust the location IF not to a connection so that we
         * don't end up adjusting here AND in executeDropPopupItem) in the
         * widget.
         */
        if (!(actualDropTarget instanceof Transition)) {
            adjustableRelativeLocation.x -= newObjectSize.width / 2;
            adjustableRelativeLocation.y -= newObjectSize.height / 2;
        }

        /* Add the new object to list of objects to 'paste' into process. */
        List<Object> newObjects = new ArrayList<Object>();
        newObjects.add(newObject);

        /*
         * Create command to connect the source and new target in appropriate
         * way.
         * 
         * Unless the user is inserting a new object onto an existing flow AND
         * that flow is outgoing from the source object.
         */
        boolean isInsertOnExistingToOrFromFlow = false;
        if (actualDropTarget instanceof Transition
                && connectionSourceObject instanceof UniqueIdElement) {
            Transition t = (Transition) actualDropTarget;

            String id = ((UniqueIdElement) connectionSourceObject).getId();
            if (id != null && id.length() > 0) {
                if (id.equals(t.getFrom()) || id.equals(t.getTo())) {
                    isInsertOnExistingToOrFromFlow = true;
                }
            }
        }

        if (!isInsertOnExistingToOrFromFlow && connectionSourceObject != null) {
            /*
             * If not inserting onto sequence flow then create appropriate
             * connection type.
             */
            if (CreateAndConnectConnectionType.SEQUENCE_FLOW.equals(cacPair
                    .getConnectionType())
                    && connectionSourceObject instanceof Activity) {

                WidgetRGB lineColor =
                        ProcessWidgetColors
                                .getInstance(TaskObjectUtil.getProcessType(process))
                                .getGraphicalNodeColor(null,
                                        ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

                Activity srcActivity = (Activity) connectionSourceObject;

                Transition trans =
                        ElementsFactory.createTransition(srcActivity,
                                newObject,
                                SequenceFlowType.UNCONTROLLED_LITERAL,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                lineColor.toString());
                newObjects.add(trans);

            } else if (CreateAndConnectConnectionType.MESSAGE_FLOW
                    .equals(cacPair.getConnectionType())
                    && connectionSourceObject instanceof Activity) {

                WidgetRGB lineColor =
                        ProcessWidgetColors
                                .getInstance(TaskObjectUtil.getProcessType(process))
                                .getGraphicalNodeColor(null,
                                        ProcessWidgetColors.MESSAGE_FLOW_LINE);

                Activity srcActivity = (Activity) connectionSourceObject;

                MessageFlow msgFlow =
                        ElementsFactory.createMessageFlow(srcActivity,
                                newObject,
                                null,
                                null,
                                null,
                                lineColor.toString());

                newObjects.add(msgFlow);

            } else if (CreateAndConnectConnectionType.ASSOCIATION
                    .equals(cacPair.getConnectionType())
                    && connectionSourceObject instanceof NamedElement) {

                WidgetRGB lineColor =
                        ProcessWidgetColors
                                .getInstance(TaskObjectUtil.getProcessType(process))
                                .getGraphicalNodeColor(null,
                                        ProcessWidgetColors.ASSOCIATION_LINE);

                NamedElement srcObject = (NamedElement) connectionSourceObject;

                Association ass =
                        ElementsFactory.createAssociation(srcObject,
                                newObject,
                                null,
                                null,
                                null,
                                lineColor.toString());

                newObjects.add(ass);

            }

        }

        //
        // Create drop popup menu item.
        DropObjectPopupItem popupItem =
                DropObjectPopupItem.createCreateDiagramObjectsItem(newObjects,
                        null,
                        menuLabel,
                        menuIcon);

        return popupItem;
    }
}
