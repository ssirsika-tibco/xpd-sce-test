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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractSeparationOfDutiesSection;
import com.tibco.xpd.processeditor.xpdl2.util.SeparationOfDutiesCommandActions;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * @author bharge
 * 
 */
public class DeleteSeparationOfDutiesAction extends Action {

    private AbstractSeparationOfDutiesSection section;

    public DeleteSeparationOfDutiesAction(
            AbstractSeparationOfDutiesSection section) {
        this.section = section;
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
        image = ir.get(Xpdl2UiPlugin.IMG_DELETE);
        return ImageDescriptor.createFromImage(image);
    }

    /**
     * @see org.eclipse.jface.action.Action#getText()
     * 
     * @return
     */
    @Override
    public String getText() {
        ISelection selection =
                section.resourcePatternsTreeViewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) selection;
            Object firstElement = structuredSelection.getFirstElement();
            if (firstElement instanceof SeparationOfDutiesActivities) {
                return Messages.ResourcePatterns_DeleteGroup;
            } else if (firstElement instanceof ActivityRef) {
                return Messages.ResourcePatterns_DeleteActivity;
            }
        }
        return super.getText();
    }

    /**
     * @see org.eclipse.jface.action.Action#isEnabled()
     * 
     * @return
     */
    @Override
    public boolean isEnabled() {
        ISelection selection =
                section.resourcePatternsTreeViewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) selection;
            Object firstElement = structuredSelection.getFirstElement();
            if (firstElement instanceof ActivityRef) {
                ActivityRef activityRef = (ActivityRef) firstElement;
                EObject input = section.getInput();
                if (input instanceof Activity) {
                    Activity activity = (Activity) input;
                    if (activityRef.getIdRef().equals(activity.getId())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    public void run() {
        CompoundCommand cCmd =
                new CompoundCommand(
                        Messages.SeparationOfDutiesSashSection_RemoveSeparationOfDutiesCommand);

        EditingDomain ed = null;

        ISelection selection =
            section.resourcePatternsTreeViewer.getSelection();
        if (selection instanceof StructuredSelection) {
            Object object = ((StructuredSelection) selection).getFirstElement();
            if (object instanceof EObject) {
                ed = WorkingCopyUtil.getEditingDomain((EObject) object);
            }

            SeparationOfDutiesCommandActions commandActions =
                    new SeparationOfDutiesCommandActions(section.getInput());

            cCmd = commandActions.deleteTasksOrGroup(selection, cCmd);
        }
        if (cCmd.canExecute()) {
            ed.getCommandStack().execute(cCmd);
        }
    }

}
