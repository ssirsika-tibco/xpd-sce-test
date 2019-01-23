/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractRetainFamiliarSection;
import com.tibco.xpd.processeditor.xpdl2.util.RetainFamiliarCommandActions;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * @author bharge
 * 
 */
public class SelectTasksForRFGroupAction extends Action {

    private AbstractRetainFamiliarSection section;

    /**
     * 
     */
    public SelectTasksForRFGroupAction(AbstractRetainFamiliarSection section) {
        this.section = section;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    public void run() {
        EditingDomain ed = null;
        CompoundCommand cCmd = new LateExecuteCompoundCommand();
        cCmd.setLabel(Messages.ResourcePatterns_AddTaskGroup);
        ISelection selection =
                section.resourcePatternsTreeViewer.getSelection();
        if (selection instanceof StructuredSelection) {
            Object object = ((StructuredSelection) selection).getFirstElement();
            if (object instanceof EObject) {
                ed = WorkingCopyUtil.getEditingDomain((EObject) object);
            }
            RetainFamiliarCommandActions commandActions =
                    new RetainFamiliarCommandActions(section.getInput());
            cCmd = commandActions.addTasksToGroup(selection, cCmd);
        }

        if (cCmd != null && cCmd.canExecute()) {
            ed.getCommandStack().execute(cCmd);
        }
    }

    /**
     * @see org.eclipse.jface.action.Action#getImageDescriptor()
     *
     * @return
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        Image image = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
        image = ir.get(Xpdl2UiPlugin.IMG_ADD_TASK_TO_GROUP);
        return ImageDescriptor.createFromImage(image);
    }

    /**
     * @see org.eclipse.jface.action.Action#getText()
     *
     * @return
     */
    @Override
    public String getText() {
        return Messages.ResourcePatterns_AddTaskGroup;
    }
}
