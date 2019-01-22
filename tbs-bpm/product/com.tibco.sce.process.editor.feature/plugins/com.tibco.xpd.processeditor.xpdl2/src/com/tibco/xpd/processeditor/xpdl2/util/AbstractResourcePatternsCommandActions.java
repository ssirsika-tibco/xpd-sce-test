/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author bharge
 * 
 */
public abstract class AbstractResourcePatternsCommandActions {

    public abstract CompoundCommand addTasksToGroup(ISelection selection,
            CompoundCommand cCmd);

    public abstract CompoundCommand deleteTasksOrGroup(ISelection selection,
            CompoundCommand cCmd);

    public void renameTaskGroup(ISelection selection, Control control) {

        TreeEditor editor = null;

        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            if (structured.size() == 1) {
                final Object item = structured.getFirstElement();
                if (control instanceof Tree) {
                    Tree tree = (Tree) control;
                    if (editor != null) {
                        Control old = editor.getEditor();
                        if (old != null) {
                            old.dispose();
                        }
                    }
                    editor = new TreeEditor(tree);
                    editor.horizontalAlignment = SWT.LEFT;
                    editor.grabHorizontal = true;
                    editor.minimumWidth = 50;
                    final Text text = new Text(tree, SWT.NONE);
                    TreeItem[] items = tree.getSelection();
                    if (items.length == 1) {
                        final String oldValue = items[0].getText();
                        text.setText(oldValue);
                        text.selectAll();
                        text.setFocus();
                        text.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                updateName(item, oldValue, text.getText());
                                text.dispose();
                            }
                        });
                        text.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                if (e.character == '\r') {
                                    updateName(item, oldValue, text.getText());
                                    text.dispose();
                                }
                            }
                        });
                        editor.setEditor(text, items[0]);
                    }
                }
            }
        }
    }

    public abstract void updateName(Object object, String oldValue,
            String newValue);

    protected static List<ActivityRef> removeExistingActivityRefsBeforeAdd(
            EObject object, List<Activity> activityList, Object[] pickedTasks) {

        EList<ActivityRef> activityRef = null;
        List<ActivityRef> activityRefsToRemove = new ArrayList<ActivityRef>();

        if (object instanceof SeparationOfDutiesActivities) {
            SeparationOfDutiesActivities sepOfDutiesActivities =
                    (SeparationOfDutiesActivities) object;
            activityRef = sepOfDutiesActivities.getActivityRef();
        } else if (object instanceof RetainFamiliarActivities) {
            RetainFamiliarActivities retainFamiliarActivities =
                    (RetainFamiliarActivities) object;
            activityRef = retainFamiliarActivities.getActivityRef();
        }
        for (Activity activity : activityList) {
            for (ActivityRef activityRef2 : activityRef) {
                if (activityRef2.getIdRef().equals(activity.getId())) {
                    activityRefsToRemove.add(activityRef2);
                }
            }
        }

        return activityRefsToRemove;
    }

    protected static boolean findForChanges(Object[] pickedTasks,
            List<Activity> activityList) {
        List<String> pickedActIds = new ArrayList<String>();
        List<String> existingActIds = new ArrayList<String>();
        boolean noChangesOccured = false;

        for (Object object : pickedTasks) {
            if (object instanceof Activity) {
                Activity act = (Activity) object;
                pickedActIds.add(act.getId());
            }
        }

        for (Activity activity : activityList) {
            existingActIds.add(activity.getId());
        }

        // if no changes return true
        if (pickedActIds.equals(existingActIds)) {
            noChangesOccured = true;
        }
        return noChangesOccured;
    }

    protected static class ResourcePatternsCommand extends CompoundCommand {

        private EditingDomain ed;

        private Object object;

        private List<ActivityRef> activityRefsToRemove;

        private List<ActivityRef> newActivityRefsToCreate;

        /**
         * 
         */
        protected ResourcePatternsCommand(EditingDomain ed, EObject object,
                List<ActivityRef> activityRefsToRemove,
                List<ActivityRef> newActivityRefsToCreate) {

            super();
            this.ed = ed;
            if (object instanceof SeparationOfDutiesActivities) {
                this.object = (SeparationOfDutiesActivities) object;
            } else if (object instanceof RetainFamiliarActivities) {
                this.object = (RetainFamiliarActivities) object;
            }
            this.activityRefsToRemove = activityRefsToRemove;
            this.newActivityRefsToCreate = newActivityRefsToCreate;
        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         * 
         * @return
         */
        @Override
        public boolean canExecute() {
            return true;
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         * 
         */
        @Override
        public void execute() {
            if (null != activityRefsToRemove) {
                if (object instanceof SeparationOfDutiesActivities) {
                    super
                            .appendAndExecute(RemoveCommand
                                    .create(ed,
                                            object,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getSeparationOfDutiesActivities_ActivityRef(),
                                            activityRefsToRemove));
                }
                if (object instanceof RetainFamiliarActivities) {
                    super.appendAndExecute(RemoveCommand.create(ed,
                            object,
                            XpdExtensionPackage.eINSTANCE
                                    .getRetainFamiliarActivities_ActivityRef(),
                            activityRefsToRemove));
                }
            }

            if (null != newActivityRefsToCreate) {
                if (object instanceof SeparationOfDutiesActivities) {
                    super
                            .appendAndExecute(AddCommand
                                    .create(ed,
                                            object,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getSeparationOfDutiesActivities_ActivityRef(),
                                            newActivityRefsToCreate));
                } else if (object instanceof RetainFamiliarActivities) {
                    super.appendAndExecute(AddCommand.create(ed,
                            object,
                            XpdExtensionPackage.eINSTANCE
                                    .getRetainFamiliarActivities_ActivityRef(),
                            newActivityRefsToCreate));
                }
            }
        }
    }

}
