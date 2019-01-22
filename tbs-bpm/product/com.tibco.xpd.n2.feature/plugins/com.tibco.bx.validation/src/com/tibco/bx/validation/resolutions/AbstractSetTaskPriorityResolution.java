/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Set the task priority of target activity.
 * 
 * @author aallway
 * @since 26 Oct 2011
 */
public abstract class AbstractSetTaskPriorityResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.TaskTypeIndependentSubProcSection_SetSubProcTaskPriority_menu);

            Priority priority = Xpdl2Factory.eINSTANCE.createPriority();

            priority = Xpdl2Factory.eINSTANCE.createPriority();
            priority.setValue(getPriorityValue());

            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Priority(),
                    priority));

            return cmd;

        }

        return null;
    }

    /**
     * @return the priority value to set
     */
    protected abstract String getPriorityValue();

    public static class SetTaskPriority300Resolution extends
            AbstractSetTaskPriorityResolution {

        @Override
        protected String getPriorityValue() {
            return "300"; //$NON-NLS-1$
        }
    }

    public static class SetTaskPriorityInheritFromParentResolution extends
            AbstractSetTaskPriorityResolution {

        @Override
        protected String getPriorityValue() {
            return ""; //$NON-NLS-1$
        }
    }

}
