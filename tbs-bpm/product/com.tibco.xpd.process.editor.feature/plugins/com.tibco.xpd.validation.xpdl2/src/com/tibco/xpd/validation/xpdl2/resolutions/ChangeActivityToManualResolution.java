/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class ChangeActivityToManualResolution implements IResolution {

    /** Command id. */
    private static final String COMMAND = "changeActivityToManualCommand"; //$NON-NLS-1$

    /**
     * @param marker The problem marker.
     * @throws ResolutionException If there was a problem during the resolution.
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(
     *      org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        try {
            XpdProjectResourceFactory factory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory(
                                    marker.getResource().getProject());
            WorkingCopy workingCopy =
                    factory.getWorkingCopy(marker.getResource());
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            Package xpdlPackage = (Package) workingCopy.getRootElement();
            Resource resource = xpdlPackage.eResource();
            if (resource != null) {
                EObject target = resource.getEObject(location);
                if (target instanceof Activity) {
                    Activity activity = (Activity) target;
                    CompoundCommand command =
                            new CompoundCommand(ResolutionMessages
                                    .getText(COMMAND));
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(activity);
                    Implementation implementation =
                            activity.getImplementation();
                    if (!(implementation instanceof Task)) {
                        implementation = Xpdl2Factory.eINSTANCE.createTask();
                        EStructuralFeature feature =
                                Xpdl2Package.eINSTANCE
                                        .getActivity_Implementation();
                        command.append(SetCommand.create(ed, activity, feature,
                                implementation));
                    }
                    Task task = (Task) implementation;
                    TaskManual manual = task.getTaskManual();
                    if (manual == null) {
                        ensureRemoved(ed, command, task, task
                                .getTaskApplication());
                        ensureRemoved(ed, command, task, task.getTaskUser());
                        ensureRemoved(ed, command, task, task.getTaskReceive());
                        ensureRemoved(ed, command, task, task
                                .getTaskReference());
                        ensureRemoved(ed, command, task, task.getTaskScript());
                        ensureRemoved(ed, command, task, task.getTaskSend());
                        ensureRemoved(ed, command, task, task.getTaskService());
                        manual = Xpdl2Factory.eINSTANCE.createTaskManual();
                        EStructuralFeature feature =
                                Xpdl2Package.eINSTANCE.getTask_TaskManual();
                        command.append(SetCommand.create(ed, task, feature,
                                manual));
                    }
                    if (command.canExecute()) {
                        ed.getCommandStack().execute(command);
                    }
                }
            }
        } catch (CoreException e) {
            throw new ResolutionException(e);
        }
    }

    /**
     * @param ed The editing domain.
     * @param command The command to add to.
     * @param task The task to remove the type from.
     * @param taskType The type to remove.
     */
    private void ensureRemoved(EditingDomain ed, CompoundCommand command,
            Task task, EObject taskType) {
        if (taskType != null) {
            EStructuralFeature feature = taskType.eContainingFeature();
            command.append(SetCommand.create(ed, task, feature, task
                    .getTaskApplication()));
        }
    }

}
