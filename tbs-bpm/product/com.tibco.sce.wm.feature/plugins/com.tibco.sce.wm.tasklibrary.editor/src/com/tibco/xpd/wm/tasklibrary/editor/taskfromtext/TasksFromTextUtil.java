/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.taskfromtext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.taskfromtext.TaskFromTextItem.TaskFromTextItemType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class TasksFromTextUtil {

    /**
     * Given a text string create a default set of task from text items.
     * <p>
     * The text is analysed and various assumptions are made. To begin with,
     * task items are created for each line of text (that has text values). If a
     * subsequent line is further indented than the first then this method
     * assumes that these are tasks within a task set defined by the preceding
     * line.
     * <p>
     * In other words, text formatted as follws will create task from text items
     * as described....
     * 
     * <pre>
     * Task1
     * Task2
     * TaskSet1
     *   Task3
     * </pre>
     * 
     * @param text
     * @return Task from text items.
     */
    public static List<TaskFromTextItem> getTaskFromTextItems(String text,
            Process destinationTaskLibrary) {
        List<TaskFromTextItem> items = new ArrayList<TaskFromTextItem>();

        if (text != null) {
            String[] lines = text.split("\n"); //$NON-NLS-1$

            TaskFromTextItem curTaskSet = null;
            TaskFromTextItem prevItem = null;

            boolean doneFirstTaskSet = false;

            int baseLineIndent = -1;

            //
            // Go thru lines - assume all are tasks to start with, then if we
            // find one more indented than the first, change the previous one to
            // a task set.

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];

                // Trim white space.
                String name = line.trim();

                if (name.length() > 0) {
                    TaskFromTextItem newItem;

                    // Set the baseLineIndent from the first line with some text
                    // actually on it.
                    if (baseLineIndent == -1) {
                        baseLineIndent = indentSize(line);
                    }

                    int lineIndent = indentSize(line);

                    // Anything with an indent > than base indent is a task in a
                    // task set.
                    if (lineIndent > baseLineIndent) {
                        // If this is first item in a task set then set previous
                        // item to be a task set.
                        if (!doneFirstTaskSet) {
                            prevItem.setType(TaskFromTextItemType.TASKSET);
                        }

                        // And this indented iem has to be a task.
                        newItem =
                                new TaskFromTextItem(name,
                                        TaskFromTextItemType.TASK, true);

                        doneFirstTaskSet = true;

                    } else {
                        // This line is at base indent level or less...
                        if (doneFirstTaskSet) {
                            // We've previously created a task set then always
                            // create tasksets for unindented items.
                            newItem =
                                    new TaskFromTextItem(name,
                                            TaskFromTextItemType.TASKSET, true);
                        } else {
                            // Otherwise create tasks until we have found
                            // something more indented and created the first
                            // task set.
                            newItem =
                                    new TaskFromTextItem(name,
                                            TaskFromTextItemType.TASK, true);
                        }
                    }

                    items.add(newItem);
                    prevItem = newItem;
                }
            }
        }

        // Reconfigure list to add existing lanes (so new tasks can be added to
        // them) and to check for existing item with same name.
        TasksFromTextUtil.configureToMergeWithExistingLibrary(items,
                destinationTaskLibrary);

        return items;
    }

    /**
     * Return a command that will create the task sets and tasks according to
     * the given tasks from text items.
     * 
     * @param editingDomain
     * @param taskFromTextItems
     * @param taskLibrary
     * 
     * @return Command
     */
    public static Command getCreateTasksFromTextCommand(
            EditingDomain editingDomain,
            List<TaskFromTextItem> taskFromTextItems, Process taskLibrary,
            boolean allowDuplicateTaskCreation) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.PasteTasksFromClipTextAction_PasteTasksFromText_menu);

        Collection<Pool> pools = Xpdl2ModelUtil.getProcessPools(taskLibrary);
        if (pools.size() > 0) {
            Pool pool = pools.iterator().next();

            Lane currentLane = null;

            EList<Lane> lanes = pool.getLanes();

            if (lanes.size() > 0) {
                currentLane = lanes.get(lanes.size() - 1);
            }

            boolean ignoringChildren = false;

            for (TaskFromTextItem item : taskFromTextItems) {
                if (item.isSelected()) {
                    if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                        if (!(item.getExistingObject() instanceof Lane)) {
                            CreateLaneCommand clc =
                                    new CreateLaneCommand(editingDomain, pool,
                                            item.getName());
                            cmd.append(clc);
                            currentLane = clc.getLane();
                        } else {
                            currentLane = (Lane) item.getExistingObject();
                        }

                        ignoringChildren = false;

                    } else if (!ignoringChildren) {
                        // Create task unless it's a duplicate.
                        if (allowDuplicateTaskCreation
                                || !(item.getExistingObject() instanceof Activity)) {
                            if (currentLane == null) {
                                // HAve tasks in list before first task set to
                                // create so if process never had a lane then
                                // add one now.
                                CreateLaneCommand clc =
                                        new CreateLaneCommand(
                                                editingDomain,
                                                pool,
                                                Messages.TasksPropertySection_TaskSetColumnTitle_label);
                                cmd.append(clc);
                                currentLane = clc.getLane();
                            }
                            cmd.append(new CreateTaskCommand(editingDomain,
                                    taskLibrary, currentLane, item.getName(),
                                    item.getCreateTaskType()));
                        }
                    }
                } else {
                    // unselected
                    if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                        ignoringChildren = true;
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * Reconfigure the list to merge it with existing task library.
     * 
     * @param taskFromTextItems
     * @param taskLibrary
     * 
     * @return Reconfigured list.
     */
    public static void configureToMergeWithExistingLibrary(
            List<TaskFromTextItem> taskFromTextItems, Process taskLibrary) {

        List<TaskFromTextItem> newItems = new ArrayList<TaskFromTextItem>();
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(taskLibrary);

        // Insert any task sets in process that aren't in list.
        List<Lane> lanes = Xpdl2ModelUtil.getProcessLanes(taskLibrary);
        for (Lane lane : lanes) {
            if (!configureExistingTaskSetItem(taskFromTextItems, lane)) {
                TaskFromTextItem item =
                        new TaskFromTextItem(Xpdl2ModelUtil
                                .getDisplayNameOrName(lane),
                                TaskFromTextItemType.TASKSET, true);
                item.setExistingObject(lane);
                newItems.add(item);
            }
        }

        for (TaskFromTextItem item : taskFromTextItems) {
            if (TaskFromTextItemType.TASK.equals(item.getType())) {
                configureExistingTaskItem(item, activities);
            }

            newItems.add(item);
        }

        taskFromTextItems.clear();
        taskFromTextItems.addAll(newItems);
        return;
    }

    private static void configureExistingTaskItem(TaskFromTextItem taskItem,
            Collection<Activity> activities) {

        for (Activity act : activities) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(act);
            if (name.equals(taskItem.getName())) {
                taskItem.setExistingObject(act);
                taskItem.setSelected(false);

                return;
            }
        }
        return;
    }

    /**
     * If there is a taskset from text item in the list then configure it
     * appropriately.
     * 
     * @param taskFromTextItems
     * @param existingTaskSet
     * @return true if the task set was found in list.
     */
    private static boolean configureExistingTaskSetItem(
            List<TaskFromTextItem> taskFromTextItems, Lane existingTaskSet) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(existingTaskSet);

        if (name != null && name.length() > 0) {
            for (TaskFromTextItem item : taskFromTextItems) {
                if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                    if (name.equals(item.getName())) {
                        item.setName(item.getName());
                        item.setExistingObject(existingTaskSet);
                        item.setSelected(true);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param line
     * @return number of leading whitespace chars.
     */
    private static int indentSize(String line) {
        int lineIndent;
        lineIndent = 0;
        char[] chars = line.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] == ' ') {
                lineIndent++;
            } else if (chars[j] == '\t') {
                lineIndent += 4;
            } else {
                break;
            }
        }
        return lineIndent;
    }

    /**
     * Create a lane with the of the given name.
     * <p>
     * The lane that will be created is made available via getLane().
     * 
     * @author aallway
     * @since 3.2
     */
    public static class CreateLaneCommand extends CompoundCommand {
        Lane lane;

        public CreateLaneCommand(EditingDomain editingDomain, Pool pool,
                String name) {
            lane = Xpdl2Factory.eINSTANCE.createLane();

            Xpdl2ModelUtil
                    .setOtherAttribute(lane, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);
            lane.setName(NameUtil.getInternalName(name, false));

            NodeGraphicsInfo nodeGraphicsInfo =
                    Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);

            nodeGraphicsInfo.setIsVisible(true);

            nodeGraphicsInfo.setHeight(50);

            this.append(AddCommand.create(editingDomain,
                    pool,
                    Xpdl2Package.eINSTANCE.getPool_Lanes(),
                    lane));
        }

        public Lane getLane() {
            return lane;
        }

    }

}
