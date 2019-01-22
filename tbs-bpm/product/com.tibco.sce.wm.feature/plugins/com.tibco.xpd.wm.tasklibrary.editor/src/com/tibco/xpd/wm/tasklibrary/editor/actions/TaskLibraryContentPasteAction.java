/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.PasteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.wm.tasklibrary.editor.util.TaskLibraryTaskUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project explorer paste action for task library content.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryContentPasteAction extends PasteAction {

    @Override
    protected CommandContainer createPasteCommand(Object target,
            EObject destEObject, EditingDomain editingDomain,
            Collection copyObjects, boolean handleProjectReferences) {
        CommandContainer cmdCont = null;

        Process taskLibrary = getDestinationTaskLibrary(destEObject);

        if (taskLibrary != null) {
            //
            // Get the list of objects to paste converted to correct type for
            // task library if necessary.
            //
            if (copyObjects != null && !copyObjects.isEmpty()) {
                List<Activity> pasteTasks = new ArrayList<Activity>();

                Collection baseCopyObjects =
                        convertNonTaskClipboardObjects(editingDomain,
                                destEObject,
                                copyObjects,
                                pasteTasks);
                if (baseCopyObjects != null && !baseCopyObjects.isEmpty()) {
                    if (pasteTasks.isEmpty()) {
                        //
                        // This is a normal non-tasks paste.
                        // So we can use the super's paste.
                        cmdCont =
                                super.createPasteCommand(target,
                                        destEObject,
                                        editingDomain,
                                        baseCopyObjects,
                                        true);
                    } else {
                        //
                        // Get paste command more suitable to pasting diagram
                        // objects.
                        // This will treat fields etc in the paste objects
                        // differently bacuase we don't want to copy them, we
                        // just want to create them if they're not already in
                        // destination process.
                        //

                        //
                        // Setup the tasks (position them in lane etc).
                        LaneAndReqdHeight laneAndHgt =
                                setupPasteTasks(taskLibrary, pasteTasks);

                        CompoundCommand cmd = new CompoundCommand();

                        ProcessPasteCommand pasteCmd =
                                Xpdl2ProcessDiagramUtils
                                        .getAddDiagramObjectsCommand(editingDomain,
                                                taskLibrary,
                                                laneAndHgt.lane,
                                                new Point(0, 0),
                                                baseCopyObjects,
                                                null,
                                                true,// We do the positioning
                                                handleProjectReferences);

                        if (pasteCmd != null) {
                            cmd.setLabel(pasteCmd.getLabel());
                            cmd.append(pasteCmd);

                            //
                            // Increase the size of lane if necessary.
                            NodeGraphicsInfo laneNgi =
                                    Xpdl2ModelUtil
                                            .getOrCreateNodeGraphicsInfo(laneAndHgt.lane,
                                                    editingDomain,
                                                    cmd);
                            if (laneAndHgt.reqdHeight > laneNgi.getHeight()) {
                                Command setc =
                                        SetCommand
                                                .create(editingDomain,
                                                        laneNgi,
                                                        Xpdl2Package.eINSTANCE
                                                                .getNodeGraphicsInfo_Height(),
                                                        new Double(
                                                                laneAndHgt.reqdHeight));
                                cmd.append(setc);
                            }

                            cmdCont = new CommandContainer(editingDomain, cmd);
                        }
                    }
                }
            }
        }

        return cmdCont;

    }

    /**
     * Setup the tasks that are about to be pasted.
     * 
     * @param taskLibrary
     * @param pasteTasks
     * @return The TaskSet (lane) that the objects are being pasted into and the
     *         height it needs to be to accommodate them.
     */
    private LaneAndReqdHeight setupPasteTasks(Process taskLibrary,
            List<Activity> pasteTasks) {

        Collection<Activity> exitingActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(taskLibrary);

        //
        // Choose the first task set to do the paste.
        List<Lane> taskSets = Xpdl2ModelUtil.getProcessLanes(taskLibrary);
        if (taskSets != null && taskSets.size() > 0) {
            Lane taskSet = taskSets.get(0);
            String taskSetId = taskSet.getId();

            //
            // List of bounds of objects already in the lane.
            List<Rectangle> siblingsBounds =
                    TaskLibraryTaskUtil.getLaneChildrenBounds(taskSet);

            int reqdLaneHeight = 0;

            for (Activity task : pasteTasks) {
                //
                // Rename incoming task if it duplicates existing.
                String name =
                        ActionUtil.getCopyOfPasteDisplayName(Xpdl2ModelUtil
                                .getDisplayNameOrName(task), exitingActivities);
                Xpdl2ModelUtil.setOtherAttribute(task,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        name);
                task.setName(NameUtil.getInternalName(name, false));

                //
                // Calculate the best position to add the task to (same as
                // for new task wiz)
                NodeGraphicsInfo ngi =
                        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(task);

                int width = (int) ngi.getWidth();
                int height = (int) ngi.getHeight();

                Coordinates newCoords =
                        Xpdl2Factory.eINSTANCE.createCoordinates();

                reqdLaneHeight =
                        TaskLibraryTaskUtil
                                .getDefaultNewTaskPositionInLane(task,
                                        width,
                                        height,
                                        taskSet,
                                        newCoords,
                                        siblingsBounds);

                //
                // Set the new position
                ngi.setCoordinates(newCoords);

                //
                // Add the newl added task bounds to list of child bounds.
                siblingsBounds.add(TaskLibraryTaskUtil.getNodeBounds(task));

            }

            return new LaneAndReqdHeight(taskSet, reqdLaneHeight);
        }

        return null;
    }

    /**
     * Get and return the non-activities objects from the copied clipboard
     * objects.
     * <p>
     * Whilst at it, convert formal param and correlation data field to data
     * field.
     * <p>
     * And add all the activities to returnPasteTasks for later processing.
     * 
     * @param editingDomain
     * @param explorerSelection
     * @param copyObjects
     * @param returnPasteTasks
     * 
     * @return Collection of non-activity objects to paste or null if there are
     *         invalid objects.
     */
    private Collection convertNonTaskClipboardObjects(
            EditingDomain editingDomain, EObject explorerSelection,
            Collection copyObjects, List<Activity> returnPasteTasks) {

        //
        // Check for things we know we can handle - convert those that
        // need converting (parameter -> datafield and correlation data
        // -> data field)
        List<Object> finalCopy = new ArrayList<Object>();

        for (Object o : copyObjects) {
            if (o instanceof Package || o instanceof Process) {
                // Definitely do not allow these.
                return null;
            }

            Object convertedObj = convertTypeIfNecessary(o);

            if (convertedObj == null) {
                // Ignore Unhandled paste object type.s
                continue;
            }

            if (convertedObj instanceof Activity) {
                returnPasteTasks.add((Activity) convertedObj);
            }
            finalCopy.add(convertedObj);

        }

        //
        // If there a tasks then the other things like fields are only there in
        // support of them so remove them if there are ones with same name here
        // already!
        if (!returnPasteTasks.isEmpty()) {
        }

        return finalCopy;

    }

    /**
     * @param explorerSelection
     * @return The target task library for the given selection.
     */
    private Process getDestinationTaskLibrary(EObject explorerSelection) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(explorerSelection);

        if (wc != null) {
            Process tl = TaskLibraryFactory.INSTANCE.getTaskLibrary(wc);
            if (tl != null) {
                return tl;
            }
        }

        return null;
    }

    /**
     * If ncessary convert the given object to one that's appropriate for task
     * library.
     * <p>
     * i.e. (parameter -> datafield and correlation data -> data field
     * 
     * @param o
     * 
     * @return Original object, new object converted from original, null if
     *         unhandled object type.
     */
    private Object convertTypeIfNecessary(Object o) {
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            if (act.getEvent() != null || act.getRoute() != null) {
                return null;
            }

            TaskType tt = TaskObjectUtil.getTaskType(act);

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (TaskType.RECEIVE_LITERAL.equals(tt)
                    || TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(tt)
                    || TaskType.REFERENCE_LITERAL.equals(tt)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(tt)) {
                return null;
            }
            return o;

        } else if (o instanceof TypeDeclaration) {
            return o;
        } else if (o instanceof Participant) {
            return o;
        } else if (o instanceof ProcessRelevantData) {
            if (o instanceof DataField) {
                //
                // Convert correlation data to normal data.
                DataField d = (DataField) o;

                if (d.isCorrelation()) {
                    d.setCorrelation(false);
                }
                return o;

            } else if (o instanceof FormalParameter) {
                FormalParameter f = (FormalParameter) o;

                DataField d = Xpdl2Factory.eINSTANCE.createDataField();

                d.setName(f.getName());
                Xpdl2ModelUtil.setOtherAttribute(d,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        Xpdl2ModelUtil.getDisplayName(f));

                d.setDataType(f.getDataType());
                f.setDataType(null);

                d.setIsArray(f.isIsArray());
                d.setReadOnly(f.isReadOnly());

                d.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                        f.getId());
                return d;

            } else {
                return o;
            }
        }

        return null;
    }

    private class LaneAndReqdHeight {
        Lane lane;

        public LaneAndReqdHeight(Lane lane, int reqdHeight) {
            super();
            this.lane = lane;
            this.reqdHeight = reqdHeight;
        }

        int reqdHeight;
    }
}
