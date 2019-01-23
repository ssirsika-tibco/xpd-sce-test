/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TasksGroup;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TaskPropertyTableSection
 * 
 * 
 * @author bharge
 * @since 3.3 (5 Nov 2009)
 */
public class TaskPropertySection extends AbstractTransactionalSection {
    TaskPropertyTable taskPropertyTable;

    Process inputTaskLibrary = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;

        taskPropertyTable =
                new TaskPropertyTable(root, toolkit, getEditingDomain());
        taskPropertyTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (taskPropertyTable != null && taskPropertyTable.getViewer() != null) {
            taskPropertyTable.getViewer().cancelEditing();
            taskPropertyTable.getViewer().setInput(input);
            // if (inputTaskLibrary != null ) {
            // taskPropertyTable.setComboItems();
            // taskPropertyTable.setInputTaskLibrary(inputTaskLibrary);
            // }
        }
    }

    @Override
    public void setInput(Collection<?> items) {

        // 
        // Input can be Tasks navigator group or Task Set (xpdl Lane)
        List<EObject> inputList = new ArrayList<EObject>();
        if (items.size() == 1) {
            Object obj = items.iterator().next();
            if (obj instanceof TasksGroup) {
                TasksGroup tasksGroup = (TasksGroup) obj;
                if (tasksGroup.getParent() instanceof Process) {
                    inputTaskLibrary = (Process) tasksGroup.getParent();
                    if (inputTaskLibrary != null) {
                        inputList.add(inputTaskLibrary);
                    }
                }
            } else {
                EObject eo = getBaseSelectObject(obj);
                if (eo instanceof Lane) {
                    inputTaskLibrary = Xpdl2ModelUtil.getProcess(eo);
                    if (inputTaskLibrary != null) {
                        inputList.add(eo);
                    }
                } else if (eo instanceof Process) {
                    inputTaskLibrary = (Process) eo;
                    inputList.add(inputTaskLibrary);
                }
            }
        }

        super.setInput(inputList);

        return;
    }

    public static EObject getBaseSelectObject(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        return eo;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;
        if (getInput() instanceof Lane) {
            Process inputProcess = Xpdl2ModelUtil.getProcess(getInput());
            if (inputProcess != null && notifications != null
                    && !notifications.isEmpty()) {
                for (Notification notification : notifications) {
                    if (!notification.isTouch()) {
                        Object notifier = notification.getNotifier();

                        if (notifier instanceof EObject) {
                            if (notifier == inputProcess
                                    || EcoreUtil.isAncestor(inputProcess,
                                            (EObject) notifier)) {
                                refresh = true;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            refresh = super.shouldRefresh(notifications);
        }
        return refresh;
    }

}
