/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.taskfromtext;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.util.TaskLibraryTaskUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * COmmand to create a task of given name and type in given lane.
 *
 * @author aallway
 * @since
 */
public class CreateTaskCommand extends CompoundCommand {
    private EditingDomain editingDomain;

    private Process taskLibrary;

    private Lane taskSet;

    private String taskName;

    private TaskType taskType;

    public CreateTaskCommand(EditingDomain editingDomain,
            Process taskLibrary, Lane taskSet, String taskName,
            TaskType taskType) {
        super();
        this.editingDomain = editingDomain;
        this.taskLibrary = taskLibrary;
        this.taskSet = taskSet;
        this.taskName = taskName;
        this.taskType = taskType;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        Activity task = createTaskFromText(taskLibrary, taskName, taskType);

        this.appendAndExecute(AddCommand.create(editingDomain,
                taskLibrary,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                task));
        return;
    }

    /**
     * @param taskLibrary
     * @param fromText
     * @param tt 
     * @return
     */
    private Activity createTaskFromText(Process taskLibrary, String fromText, TaskType tt) {
        Activity task =
                TaskLibraryTaskUtil.createDefaultTask(tt);

        String name =
                TaskLibraryTaskUtil.getUniqueTaskName(taskLibrary, fromText);
        Xpdl2ModelUtil.setOtherAttribute(task, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_DisplayName(), name);
        task.setName(NameUtil.getInternalName(name, false));

        NodeGraphicsInfo ngi = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(task);
        Coordinates newCoords = Xpdl2Factory.eINSTANCE.createCoordinates();

        TaskLibraryTaskUtil.placeTaskInLane(task,
                ProcessWidgetConstants.TASK_WIDTH_SIZE,
                ProcessWidgetConstants.TASK_HEIGHT_SIZE,
                taskSet,
                newCoords);

        ngi.setLaneId(taskSet.getId());
        ngi.setCoordinates(newCoords);

        return task;
    }

}