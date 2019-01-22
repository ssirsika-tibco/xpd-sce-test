/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;

/**
 * Constants for task library images.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorContstants {

    public static final int DEFAULT_TASKSET_EXTENT_WIDTH = 700;

    public static final int HORIZONTAL_LAYOUT_MARGIN =
            ProcessWidgetConstants.TASK_WIDTH_SIZE / 2;

    public static final int VERTICAL_LAYOUT_MARGIN =
            ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2;

    //
    // Icons.
    //

    public static final String ICON_TASKGROUP = "icons/TaskGroup.gif"; //$NON-NLS-1$

    public static final String ICON_USERTASKGROUP = "icons/UserTaskGroup.gif"; //$NON-NLS-1$

    public static final String ICON_TASKLIBRARY = "icons/TaskLibrary.png"; //$NON-NLS-1$

    public static final String ICON_TASKLIBRARY_FILE =
            "icons/TaskLibraryFile.png"; //$NON-NLS-1$

    public static final String IMG_TASKLIBRARY_WIZARD =
            "icons/TaskLibraryWizard.png"; //$NON-NLS-1$

    public static final String IMG_TASK_WIZARD = "icons/TaskWizard.png"; //$NON-NLS-1$

    public static final String IMG_TASKSET = "icons/taskset_16.png"; //$NON-NLS-1$

    public static final String ICON_TASKPLAIN = "icons/taskPlain.png"; //$NON-NLS-1$

    public static final String ICON_TASKUSER = "icons/TaskUser.png"; //$NON-NLS-1$

    public static final String ICON_TASKMANUAL = "icons/TaskManual.png"; //$NON-NLS-1$

    public static final String ICON_TASKSERVICE = "icons/TaskService.png"; //$NON-NLS-1$

    public static final String ICON_TASKSEND = "icons/TaskSend.png"; //$NON-NLS-1$

    public static final String ICON_TASKSCRIPT = "icons/TaskScript.png"; //$NON-NLS-1$

    public static final String ICON_TASKSUBPROC = "icons/SubFlow.png"; //$NON-NLS-1$

    public static final String ICON_UP = "icons/up.gif"; //$NON-NLS-1$

    public static final String ICON_DOWN = "icons/down.gif"; //$NON-NLS-1$

    public static final String ICON_COLLAPSEALL = "icons/collapseall.gif"; //$NON-NLS-1$

    public static final String ICON_DESELECTALL = "icons/deselectall.png"; //$NON-NLS-1$

    public static final String ICON_DATAOBJECT = "icons/data_object_16.png"; //$NON-NLS-1$

    public static final String ICON_DATAFIELD = "icons/DataField.gif"; //$NON-NLS-1$

    public static final String ICON_PARTICIPANT = "icons/Participant.gif"; //$NON-NLS-1$

    public static final String[] IMAGES =
            new String[] { ICON_TASKGROUP, ICON_USERTASKGROUP,
                    ICON_TASKLIBRARY, ICON_TASKLIBRARY_FILE,
                    IMG_TASKLIBRARY_WIZARD, IMG_TASK_WIZARD, IMG_TASKSET,
                    ICON_TASKMANUAL, ICON_TASKPLAIN, ICON_TASKUSER,
                    ICON_TASKSCRIPT, ICON_TASKSEND, ICON_TASKSERVICE,
                    ICON_TASKSUBPROC, ICON_UP, ICON_DOWN, ICON_COLLAPSEALL,
                    ICON_DESELECTALL, ICON_DATAOBJECT, ICON_DATAFIELD,
                    ICON_PARTICIPANT };
}
