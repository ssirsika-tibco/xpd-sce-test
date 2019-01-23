/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.taskfromtext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.taskfromtext.TaskFromTextItem.TaskFromTextItemType;
import com.tibco.xpd.xpdl2.Process;

/**
 * Paste text as tasks / task sets wizard configuration page .
 * 
 * @author aallway
 * @since 3.2
 */
public class PasteTasksFromTextConfigPage extends AbstractXpdWizardPage implements
        ICheckStateListener {

    private Process taskLibrary;

    private List<TaskFromTextItem> taskFromTextItems;

    private CheckboxTreeViewer checkTree;

    private Button changeToTaskSet;

    private Button changeToTask;

    private Button moveTaskUp;

    private Button moveTaskDown;

    private Button allowDuplicateTasks;

    private boolean allowDuplicateTaskCreation = false;

    private CCombo activityTypeCombo;

    private CLabel curTaskTypeImage;

    private Button collapseAll;

    private Button deselectAll;

    //
    // Keep the grayed and checked state in tree cached (tree viewer can be
    // quite slow so do not bother it unnecessarily).
    private Map<TaskFromTextItem, Boolean> checkStateCache =
            new HashMap<TaskFromTextItem, Boolean>();

    private Map<TaskFromTextItem, Boolean> greyStateCache =
            new HashMap<TaskFromTextItem, Boolean>();

    public PasteTasksFromTextConfigPage(List<TaskFromTextItem> items,
            Process taskLibrary) {
        super(
                "Configure Tasks", //$NON-NLS-1$
                "Configure Tasks &&  Task Sets", //$NON-NLS-1$
                TaskLibraryEditorPlugin
                        .getImageDescriptor(TaskLibraryEditorContstants.IMG_TASK_WIZARD));
        setDescription("Choose text entries to create as Tasks and/or Task Sets."); //$NON-NLS-1$
        this.taskLibrary = taskLibrary;
        taskFromTextItems = items;

    }

    public void createControl(Composite parent) {

        Composite cmp = new Composite(parent, SWT.NONE);
        cmp.setLayout(new GridLayout(2, false));
        setControl(cmp);

        //
        // Collapse all and deselect all buttons.
        Composite bCmp = new Composite(cmp, SWT.NONE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        bCmp.setLayoutData(gd);

        GridLayout gl = new GridLayout(3, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        bCmp.setLayout(gl);

        Composite filler = new Composite(bCmp, SWT.NONE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 1;
        filler.setLayoutData(gd);

        deselectAll = new Button(bCmp, SWT.PUSH);
        gd = new GridData();
        deselectAll.setLayoutData(gd);
        deselectAll.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry()
                .get(TaskLibraryEditorContstants.ICON_DESELECTALL));
        deselectAll.setToolTipText(Messages.PasteTasksFromTextConfigPage_DeselectAll_tooltip);
        deselectAll.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                deselectAll();
            }
        });

        collapseAll = new Button(bCmp, SWT.PUSH);
        gd = new GridData();
        collapseAll.setLayoutData(gd);
        collapseAll.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry()
                .get(TaskLibraryEditorContstants.ICON_COLLAPSEALL));
        collapseAll.setToolTipText(Messages.PasteTasksFromTextConfigPage_CollapseAll_tooltip);
        collapseAll.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                checkTree.getControl().setRedraw(false);
                checkTree.collapseAll();
                checkTree.getControl().setRedraw(true);
            }
        });

        filler = new Composite(cmp, SWT.NONE);
        gd = new GridData();
        gd.heightHint = 1;
        gd.widthHint = 1;
        filler.setLayoutData(gd);

        //
        // Task from text selection tree.
        checkTree = new CheckboxTreeViewer(cmp, SWT.BORDER | SWT.MULTI);
        checkTree.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

        checkTree.addCheckStateListener(this);

        checkTree.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                if (event.getSelection() instanceof TreeSelection) {
                    TreeSelection sel = (TreeSelection) event.getSelection();

                    updateControls(sel.toList());
                }
                return;
            }
        });

        checkTree.setContentProvider(new TaskFromTextItemsContentProvider());

        checkTree.setLabelProvider(new TaskFromTextItemsLabelProvider());
        checkTree.setAutoExpandLevel(CheckboxTreeViewer.ALL_LEVELS);
        checkTree.setInput(taskFromTextItems);

        //
        // buttons
        Composite buttonComp = new Composite(cmp, SWT.NONE);
        buttonComp
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        buttonComp.setLayout(new GridLayout(1, false));

        changeToTaskSet = new Button(buttonComp, SWT.PUSH);
        changeToTaskSet.setLayoutData(new GridData());
        changeToTaskSet.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry()
                .get(TaskLibraryEditorContstants.IMG_TASKSET));

        changeToTaskSet
                .setToolTipText(Messages.PasteTasksFromTextConfigPage_ChangeToTaskSet_tooltip);
        changeToTaskSet.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                changeToTaskSet();
            }
        });

        changeToTask = new Button(buttonComp, SWT.PUSH);
        changeToTask.setLayoutData(new GridData());
        changeToTask.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry()
                .get(TaskLibraryEditorContstants.ICON_TASKPLAIN));

        changeToTask
                .setToolTipText(Messages.PasteTasksFromTextConfigPage_ChangeToTask_tooltip);
        changeToTask.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                changeToTask();
            }
        });

        moveTaskUp = new Button(buttonComp, SWT.PUSH);
        moveTaskUp.setLayoutData(new GridData());
        moveTaskUp.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry().get(TaskLibraryEditorContstants.ICON_UP));

        moveTaskUp
                .setToolTipText(Messages.PasteTasksFromTextConfigPage_MoveTaskUp_tooltip);
        moveTaskUp.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                moveTaskUp();
            }
        });

        moveTaskDown = new Button(buttonComp, SWT.PUSH);
        moveTaskDown.setLayoutData(new GridData());
        moveTaskDown.setImage(TaskLibraryEditorPlugin.getDefault()
                .getImageRegistry().get(TaskLibraryEditorContstants.ICON_DOWN));

        moveTaskDown
                .setToolTipText(Messages.PasteTasksFromTextConfigPage_MoveTaskDown_tooltip);
        moveTaskDown.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                moveTaskDown();
            }
        });

        Composite atComp = new Composite(cmp, SWT.NONE);
        gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        atComp.setLayout(gl);

        atComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Filler under tool buttons
        filler = new Composite(cmp, SWT.NONE);
        gd = new GridData();
        gd.heightHint = 1;
        gd.widthHint = 1;
        filler.setLayoutData(gd);

        Label l = new Label(atComp, SWT.NONE);
        l.setText(Messages.PasteTasksFromTextConfigPage_TaskType_label);

        activityTypeCombo = new CCombo(atComp, SWT.READ_ONLY | SWT.BORDER);
        activityTypeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        activityTypeCombo.setBackground(ColorConstants.listBackground);

        activityTypeCombo.add(TaskType.NONE_LITERAL.toString());
        activityTypeCombo.add(TaskType.USER_LITERAL.toString());
        activityTypeCombo.add(TaskType.MANUAL_LITERAL.toString());
        activityTypeCombo.add(TaskType.SERVICE_LITERAL.toString());
        activityTypeCombo.add(TaskType.SEND_LITERAL.toString());
        activityTypeCombo.add(TaskType.SCRIPT_LITERAL.toString());
        activityTypeCombo.add(TaskType.SUBPROCESS_LITERAL.toString());

        activityTypeCombo.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                String sel =
                        activityTypeCombo.getItem(activityTypeCombo
                                .getSelectionIndex());

                TaskType selectedTaskType = null;

                for (int i = 0; i < TaskType.types.length; i++) {
                    TaskType type = TaskType.types[i];

                    if (type.toString().equals(sel)) {
                        selectedTaskType = type;
                        break;
                    }
                }

                if (selectedTaskType != null) {
                    List selList =
                            ((TreeSelection) checkTree.getSelection()).toList();

                    for (Iterator iterator = selList.iterator(); iterator
                            .hasNext();) {
                        TaskFromTextItem item =
                                (TaskFromTextItem) iterator.next();

                        item.setCreateTaskType(selectedTaskType);
                    }

                    checkTree.refresh();
                    updateControls(selList);
                }

            }
        });

        allowDuplicateTasks = new Button(cmp, SWT.CHECK);
        allowDuplicateTasks.setSelection(allowDuplicateTaskCreation);
        allowDuplicateTasks
                .setText(Messages.PasteTasksFromTextConfigPage_CreateIfDuplicateName_label);
        allowDuplicateTasks
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        allowDuplicateTasks.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                allowDuplicateTaskCreation = allowDuplicateTasks.getSelection();

                // Select or deselect all the task items that have duplicates
                // already in task library.
                boolean curSetSelected = true;

                for (TaskFromTextItem item : taskFromTextItems) {
                    if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                        curSetSelected = item.isSelected();

                    } else {
                        if (item.getExistingObject() != null) {
                            // This is an item with a duplicate in task library,
                            // select it if user as check the allow duplicates
                            // button AND the parent task set is not selected.
                            if (allowDuplicateTaskCreation && curSetSelected) {
                                item.setSelected(true);
                            } else {
                                item.setSelected(false);
                            }
                        }
                    }
                }

                synchroniseCheckState();
                checkTree.refresh();
                updateControls(((TreeSelection) checkTree.getSelection())
                        .toList());
            }
        });

        // filler
        filler = new Composite(cmp, SWT.NONE);
        gd = new GridData();
        gd.heightHint = 1;
        gd.widthHint = 1;
        filler.setLayoutData(gd);

        //
        // Synch everything up.
        synchroniseCheckState();

        checkTree.getControl().setFocus();
        if (taskFromTextItems.size() > 0) {
            checkTree.setSelection(new StructuredSelection(taskFromTextItems
                    .get(0)));
        }

        updateControls(((TreeSelection) checkTree.getSelection()).toList());

        // debugComps(cmp);
        return;
    }

    void debugComps(Composite c) {
        Color col =
                new Color(null, (int) (Math.random() * 255), (int) (Math
                        .random() * 255), (int) (Math.random() * 255));
        c.setBackground(col);

        Control[] ch = c.getChildren();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] instanceof Composite) {
                debugComps((Composite) ch[i]);
            }
        }
    }

    /**
     * @return the allowDuplicateTaskCreation
     */
    public boolean isAllowDuplicateTaskCreation() {
        return allowDuplicateTaskCreation;
    }

    private Image getImageForTaskType(TaskType taskType) {
        ImageRegistry ir =
                TaskLibraryEditorPlugin.getDefault().getImageRegistry();

        if (TaskType.USER_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKUSER);
        } else if (TaskType.MANUAL_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKMANUAL);
        } else if (TaskType.SERVICE_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKSERVICE);
        } else if (TaskType.SEND_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKSEND);
        } else if (TaskType.SCRIPT_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKSCRIPT);
        } else if (TaskType.SUBPROCESS_LITERAL.equals(taskType)) {
            return ir.get(TaskLibraryEditorContstants.ICON_TASKSUBPROC);
        }

        return ir.get(TaskLibraryEditorContstants.ICON_TASKPLAIN);
    }

    private void deselectAll() {
        for (TaskFromTextItem item : taskFromTextItems) {
            item.setSelected(false);
        }

        refreshControls(((TreeSelection) checkTree.getSelection()).toList());
    }

    private void moveTaskDown() {
        List selList = ((TreeSelection) checkTree.getSelection()).toList();

        if (selList != null && selList.size() > 0) {
            // Find the bottom item in select list (we'll group all the selected
            // into one bunch and move them all below the one below last item
            // in list.
            int botIdx = -1;

            for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                TaskFromTextItem item = (TaskFromTextItem) iterator.next();

                int idx = taskFromTextItems.indexOf(item);
                if (idx > botIdx) {
                    botIdx = idx;
                }
            }

            if (botIdx != -1 && (botIdx + 1) < taskFromTextItems.size()) {
                int insertAfter = botIdx + 2;

                if (insertAfter == (taskFromTextItems.size())) {
                    taskFromTextItems.removeAll(selList);
                    taskFromTextItems.addAll(selList);
                } else {
                    taskFromTextItems.removeAll(selList);

                    insertAfter -= selList.size();

                    taskFromTextItems.addAll(insertAfter, selList);
                }

                refreshControls(selList);

            }
        }

    }

    /**
     * @param selList
     */
    private void refreshControls(List selList) {
        checkTree.getControl().setRedraw(false);
        checkTree.refresh();
        synchroniseCheckState();

        updateControls(selList);
        checkTree.getControl().setRedraw(true);
    }

    private void moveTaskUp() {
        List selList = ((TreeSelection) checkTree.getSelection()).toList();

        if (selList != null && selList.size() > 0) {
            // Find the top item in list (we'll group all the selected into one
            // bunch and move them all above the one above forst item in list.
            int topIdx = -1;

            for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                TaskFromTextItem item = (TaskFromTextItem) iterator.next();

                int idx = taskFromTextItems.indexOf(item);
                if (idx != -1) {
                    if (topIdx == -1 || idx < topIdx) {
                        topIdx = idx;
                    }
                }
            }

            if (topIdx >= 1) {
                int insertBefore = topIdx - 1;

                taskFromTextItems.removeAll(selList);

                taskFromTextItems.addAll(insertBefore, selList);

                refreshControls(selList);
            }
        }

        return;
    }

    private void changeToTask() {
        List selList = ((TreeSelection) checkTree.getSelection()).toList();

        if (selList != null && selList.size() > 0) {
            for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                TaskFromTextItem item = (TaskFromTextItem) iterator.next();

                item.setType(TaskFromTextItemType.TASK);
            }

            refreshControls(selList);
        }
    }

    private void changeToTaskSet() {
        List selList = ((TreeSelection) checkTree.getSelection()).toList();

        if (selList != null && selList.size() > 0) {
            for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                TaskFromTextItem item = (TaskFromTextItem) iterator.next();

                item.setType(TaskFromTextItemType.TASKSET);
            }

            checkTree.getControl().setRedraw(false);

            checkTree.refresh();

            for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                TaskFromTextItem item = (TaskFromTextItem) iterator.next();
                checkTree.expandToLevel(item, checkTree.ALL_LEVELS);

                // When the task was changed to task set then all it's children
                // states will have been reinitialised to unchecked.
                // 
                // So reset our cache to match this state (which will force them
                // to be re-synch'd with the data.
                Object[] children =
                        ((TaskFromTextItemsContentProvider) checkTree
                                .getContentProvider()).getChildren(item);
                for (int i = 0; i < children.length; i++) {
                    TaskFromTextItem childItem = (TaskFromTextItem) children[i];

                    checkStateCache.put(childItem, Boolean.FALSE);
                }
            }

            synchroniseCheckState();
            updateControls(selList);
            checkTree.getControl().setRedraw(true);
        }
    }

    /**
     * Synch the tree viewer item check states with the task from text items
     * list
     */
    private void synchroniseCheckState() {
        for (TaskFromTextItem item : taskFromTextItems) {
            boolean grey = false;

            if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                if (!item.isSelected()) {
                    // Any task set that has selected children must be selected.
                    Object[] children =
                            ((TaskFromTextItemsContentProvider) checkTree
                                    .getContentProvider()).getChildren(item);

                    for (int i = 0; i < children.length; i++) {
                        TaskFromTextItem childItem =
                                (TaskFromTextItem) children[i];

                        if (childItem.isSelected()) {
                            item.setSelected(true);
                            break;
                        }
                    }
                } else {
                    // Any task set that has all un selected children can be
                    // deselected.
                    Object[] children =
                            ((TaskFromTextItemsContentProvider) checkTree
                                    .getContentProvider()).getChildren(item);
                    if (children.length > 0) {
                        boolean haveSelChild = false;
                        for (int i = 0; i < children.length; i++) {
                            TaskFromTextItem childItem =
                                    (TaskFromTextItem) children[i];

                            if (childItem.isSelected()) {
                                haveSelChild = true;
                                break;
                            }
                        }

                        if (!haveSelChild) {
                            item.setSelected(false);
                        }
                    }

                }

            } else {
                if (item.getExistingObject() != null
                        && !allowDuplicateTaskCreation) {
                    grey = true;
                }
            }

            Boolean cachedCheckState = checkStateCache.get(item);
            if (cachedCheckState == null) {
                // First time in we have to set the state.
                checkTree.setChecked(item, item.isSelected());
                checkStateCache.put(item, item.isSelected());
            } else {
                // Only set the state is it has changed.
                if (cachedCheckState.booleanValue() != item.isSelected()) {
                    checkTree.setChecked(item, item.isSelected());
                    checkStateCache.put(item, item.isSelected());
                }
            }

            Boolean cachedGreyState = greyStateCache.get(item);
            boolean changedGreyState = false;
            if (cachedGreyState == null) {
                // First time in we have to set the state if its the non-default
                // greyed.
                if (grey) {
                    changedGreyState = true;
                }
                greyStateCache.put(item, grey);
            } else {
                // Only set the state is it has changed.
                if (cachedGreyState.booleanValue() != grey) {
                    changedGreyState = true;
                    greyStateCache.put(item, grey);
                }
            }

            if (changedGreyState) {
                TreeItem treeItem =
                        findConfigTreeItem(checkTree.getTree().getItems(), item);
                if (treeItem != null) {
                    if (grey) {
                        treeItem.setForeground(ColorConstants.lightGray);
                    } else {
                        treeItem.setForeground(ColorConstants.listForeground);
                    }
                }
            }
        }
    }

    protected void updateControls(List selList) {
        boolean changeToSetEnabled = false;
        boolean changeToTaskEnabled = false;
        boolean moveUpEnabled = false;
        boolean moveDownEnabled = false;

        boolean duplicateExistingItemSelected = false;
        boolean taskSetSelected = false;
        boolean taskSelected = false;

        if (selList == null) {
            selList = Collections.EMPTY_LIST;
        }

        for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
            TaskFromTextItem item = (TaskFromTextItem) iterator.next();

            if (item.getExistingObject() != null) {
                if (!TaskFromTextItemType.TASK.equals(item.getType())
                        || !allowDuplicateTaskCreation) {
                    duplicateExistingItemSelected = true;
                }
            }

            if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                taskSetSelected = true;
            } else {
                taskSelected = true;
            }
        }

        // Nothing is enabled when an item that duplicates and existing task /
        // task set is selected.
        if (!duplicateExistingItemSelected && selList.size() > 0) {
            if (taskSetSelected && !taskSelected) {
                changeToTaskEnabled = true;
                changeToSetEnabled = false;
            } else if (taskSelected && !taskSetSelected) {
                changeToTaskEnabled = false;
                changeToSetEnabled = true;
            }

            // Can only move tasks up and down (not task sets).
            if (!taskSetSelected) {
                moveUpEnabled = true;
                moveDownEnabled = true;

                for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
                    TaskFromTextItem item = (TaskFromTextItem) iterator.next();

                    int idx = taskFromTextItems.indexOf(item);
                    if (idx < 1) {
                        // Can't move up if top item already.
                        moveUpEnabled = false;
                    } else if (idx == 1
                            && TaskFromTextItemType.TASKSET
                                    .equals(taskFromTextItems.get(0).getType())) {
                        // Can't move up to very top of top item is a task set.
                        moveUpEnabled = false;
                    }

                    if (idx >= (taskFromTextItems.size() - 1)) {
                        moveDownEnabled = false;
                    }

                }
            }
        }

        changeToTask.setEnabled(changeToTaskEnabled);
        changeToTaskSet.setEnabled(changeToSetEnabled);
        moveTaskUp.setEnabled(moveUpEnabled);
        moveTaskDown.setEnabled(moveDownEnabled);

        TaskType selTaskType = null;

        for (Iterator iterator = selList.iterator(); iterator.hasNext();) {
            TaskFromTextItem item = (TaskFromTextItem) iterator.next();

            if (TaskFromTextItemType.TASK.equals(item.getType())) {
                if (selTaskType == null) {
                    selTaskType = item.getCreateTaskType();
                } else {
                    if (selTaskType != item.getCreateTaskType()) {
                        // Mixture of types selected.
                        selTaskType = null;
                        break;
                    }
                }
            }
        }

        if (selTaskType == null) {
            activityTypeCombo.select(-1);
            activityTypeCombo.setText(""); //$NON-NLS-1$
        } else {

            String[] typeItems = activityTypeCombo.getItems();
            for (int i = 0; i < typeItems.length; i++) {
                String typeName = typeItems[i];

                if (typeName.equals(selTaskType.toString())) {
                    activityTypeCombo.select(i);
                    break;
                }
            }

        }

        return;
    }

    public void checkStateChanged(CheckStateChangedEvent event) {
        TaskFromTextItem item = (TaskFromTextItem) event.getElement();

        boolean checked = event.getChecked();

        // When doing a 'user changed check state' then ensure we update our
        // check state cache.
        checkStateCache.put(item, checked);

        // Don't allow check state change on objects that duplicate existing
        // tasks / sets.
        if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
            item.setSelected(checked);

            // If it's a task set the set state of children too.
            Object[] children =
                    ((TaskFromTextItemsContentProvider) checkTree
                            .getContentProvider()).getChildren(item);

            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    TaskFromTextItem child = (TaskFromTextItem) children[i];

                    // Only change selection state of non duplicates.
                    if (child.getExistingObject() == null
                            || allowDuplicateTaskCreation) {
                        child.setSelected(checked);
                    }
                }
            }

        } else {
            // Only allow set check of non-duplicate task items.
            if (item.getExistingObject() == null || allowDuplicateTaskCreation) {
                TaskFromTextItem parent =
                        (TaskFromTextItem) ((TaskFromTextItemsContentProvider) checkTree
                                .getContentProvider()).getParent(item);

                item.setSelected(checked);

                // If checking an item in an unchecked task set then check the
                // task set too.
                if (checked && parent != null && !parent.isSelected()) {
                    // parent.setSelected(true);
                }
            }
        }

        synchroniseCheckState();

        checkTree.setSelection(new StructuredSelection(item));

        return;
    }

    private TreeItem findConfigTreeItem(TreeItem[] treeItems, Object item) {
        if (treeItems != null) {
            for (int i = 0; i < treeItems.length; i++) {
                TreeItem treeItem = treeItems[i];

                if (treeItem.getData() == item) {
                    return treeItem;
                } else {
                    TreeItem subItem =
                            findConfigTreeItem(treeItem.getItems(), item);
                    if (subItem != null) {
                        return subItem;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Content provider converts from flat list of task sets and tasks to a 2
     * level tree structure. Each item goes at root level UNTIL first task set
     * is found, then all items below UP TO the next task set are children of
     * the task set and so on.
     * <p>
     * This is done this way to make building content simpler when TaskSet and
     * be changed to task and visa versa.
     * 
     * @author aallway
     * @since 3.2
     */
    private static class TaskFromTextItemsContentProvider implements
            ITreeContentProvider {
        List<TaskFromTextItem> taskFromTextItems = Collections.emptyList();

        public Object[] getChildren(Object parentElement) {
            TaskFromTextItem item = (TaskFromTextItem) parentElement;

            List<TaskFromTextItem> children = new ArrayList<TaskFromTextItem>();

            if (TaskFromTextItemType.TASKSET.equals(item.getType())) {

                // The children are all subsequent items up to the next task
                // set.

                int idx = taskFromTextItems.indexOf(item);
                if (idx >= 0) {
                    while (++idx < taskFromTextItems.size()) {
                        TaskFromTextItem nextItem = taskFromTextItems.get(idx);

                        if (TaskFromTextItemType.TASKSET.equals(nextItem
                                .getType())) {
                            break;
                        }

                        children.add(nextItem);
                    }
                }
            }

            return children.toArray();
        }

        public Object getParent(Object element) {
            TaskFromTextItem item = (TaskFromTextItem) element;

            // If this is a task item then the parent is the first task set
            // above us (if there is one).
            if (TaskFromTextItemType.TASK.equals(item.getType())) {
                int idx = taskFromTextItems.indexOf(item);
                if (idx > 0) {
                    while (--idx >= 0) {
                        TaskFromTextItem aboveItem = taskFromTextItems.get(idx);

                        if (TaskFromTextItemType.TASKSET.equals(aboveItem
                                .getType())) {
                            return aboveItem;
                        }
                    }
                }
            }

            return null;
        }

        public boolean hasChildren(Object element) {
            TaskFromTextItem item = (TaskFromTextItem) element;

            // If this is a task set item and the next item is a task then we
            // have children.
            if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                int idx = taskFromTextItems.indexOf(item);
                if (idx >= 0 && (idx + 1) < taskFromTextItems.size()) {
                    TaskFromTextItem nextItem = taskFromTextItems.get(idx + 1);
                    if (TaskFromTextItemType.TASK.equals(nextItem.getType())) {
                        return true;
                    }
                }
            }

            return false;
        }

        public Object[] getElements(Object inputElement) {
            List<TaskFromTextItem> items =
                    (List<TaskFromTextItem>) inputElement;

            // Top level items are all the task sets and tasks that aren't in a
            // task set.
            List<TaskFromTextItem> topItems = new ArrayList<TaskFromTextItem>();

            boolean foundFirstTaskSet = false;
            for (TaskFromTextItem item : items) {
                if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                    foundFirstTaskSet = true;
                    topItems.add(item);
                } else if (TaskFromTextItemType.TASK.equals(item.getType())) {
                    if (!foundFirstTaskSet) {
                        topItems.add(item);
                    }
                }
            }

            return topItems.toArray();
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            taskFromTextItems = (List<TaskFromTextItem>) newInput;
        }

    }

    private class TaskFromTextItemsLabelProvider implements ILabelProvider {

        TaskFromTextItemsLabelProvider() {

        }

        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof TaskFromTextItem) {
                TaskFromTextItem item = (TaskFromTextItem) element;

                if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                    img =
                            TaskLibraryEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(TaskLibraryEditorContstants.IMG_TASKSET);

                } else if (TaskFromTextItemType.TASK.equals(item.getType())) {
                    img = getImageForTaskType(item.getCreateTaskType());
                }
            }
            return img;
        }

        public String getText(Object element) {
            String text = ""; //$NON-NLS-1$

            if (element instanceof TaskFromTextItem) {
                TaskFromTextItem item = (TaskFromTextItem) element;

                text = item.getName();

                if (item.getExistingObject() != null) {
                    if (TaskFromTextItemType.TASKSET.equals(item.getType())) {
                        text =
                                text
                                        + " " + Messages.TasksFromTextUtil_ExistingTaskSet_label; //$NON-NLS-1$
                    } else {
                        text =
                                text
                                        + " " + Messages.TasksFromTextUtil_ExistingTask_label; //$NON-NLS-1$
                    }
                }
            }

            return text;
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }

    }
}
