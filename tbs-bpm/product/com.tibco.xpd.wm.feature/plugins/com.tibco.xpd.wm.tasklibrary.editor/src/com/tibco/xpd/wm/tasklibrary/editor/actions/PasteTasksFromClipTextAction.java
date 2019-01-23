/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryPropertyTester;
import com.tibco.xpd.wm.tasklibrary.editor.taskfromtext.PasteTasksFromTextConfigurationWizard;
import com.tibco.xpd.wm.tasklibrary.editor.taskfromtext.TaskFromTextItem;
import com.tibco.xpd.wm.tasklibrary.editor.taskfromtext.TasksFromTextUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class PasteTasksFromClipTextAction extends BaseSelectionListenerAction
        implements IActionDelegate {

    private Process taskLibrary;

    public PasteTasksFromClipTextAction() {
        super(Messages.PasteTasksFromClipTextAction_PasteTasksFromText_menu);
        setId("com.tibco.xpd.wm.taskLibrary.editor.PasteTasksFromClipTextAction"); //$NON-NLS-1$
    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        taskLibrary = null;

        if (selection != null && selection.size() == 1) {
            Object sel = selection.getFirstElement();
            
            taskLibrary = getTaskLibraryFromSelection(sel);

            if (taskLibrary != null) {
                Clipboard clp =
                        new Clipboard(PlatformUI.getWorkbench().getDisplay());

                Object cbContent = clp.getContents(TextTransfer.getInstance());
                if (cbContent instanceof String) {
                    return true;
                }
            }
        }

        return false;
    }


    public static Process getTaskLibraryFromSelection(Object sel) {

        Process taskLib = null;

        if (sel instanceof INavigatorGroup) {
            sel = ((INavigatorGroup) sel).getParent();
        }

        if (!(sel instanceof EObject)) {
            if (sel instanceof IAdaptable) {
                Object eo = ((IAdaptable) sel).getAdapter(EObject.class);
                if (eo instanceof EObject) {
                    sel = eo;
                }
            }
        }

        if (sel instanceof EObject) {
            if (TaskLibraryPropertyTester
                    .isTaskLibraryContent((EObject) sel)) {
                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopyFor((EObject) sel);
                if (wc != null) {
                    taskLib =
                            TaskLibraryFactory.INSTANCE.getTaskLibrary(wc);
                }
            }
        } else if (sel instanceof IFile) {
            if (TaskLibraryPropertyTester.isTaskLibraryFile((IFile) sel)) {
                taskLib =
                        TaskLibraryFactory.INSTANCE
                                .getTaskLibrary((IFile) sel);
            }
        }

        return taskLib;
    }

    @Override
    public void run() {
        if (taskLibrary != null) {
            Clipboard clp =
                    new Clipboard(PlatformUI.getWorkbench().getDisplay());

            Object cbContent = clp.getContents(TextTransfer.getInstance());
            if (cbContent instanceof String
                    && ((String) cbContent).length() > 0) {
                String clipText = (String) cbContent;

                EditingDomain editingDomain =
                        WorkingCopyUtil.getEditingDomain(taskLibrary);

                List<TaskFromTextItem> taskFromTextItems =
                        TasksFromTextUtil.getTaskFromTextItems(clipText,
                                taskLibrary);

                PasteTasksFromTextConfigurationWizard wiz =
                        new PasteTasksFromTextConfigurationWizard(
                                taskFromTextItems, taskLibrary);
                WizardDialog wizDig =
                        new WizardDialog(Display.getCurrent().getActiveShell(),
                                wiz);
                if (wizDig.open() == WizardDialog.OK) {
                    Command cmd =
                            TasksFromTextUtil
                                    .getCreateTasksFromTextCommand(editingDomain,
                                            taskFromTextItems,
                                            taskLibrary,
                                            wiz.isAllowDuplicateTaskCreation());

                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
            }
        }
    }

    public void run(IAction action) {
        run();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        boolean ret;
        if (selection instanceof StructuredSelection) {
            ret = updateSelection((StructuredSelection) selection);
        } else {
            ret = updateSelection(new StructuredSelection());
        }
        
        if (ret) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
        }
    }

    
}
