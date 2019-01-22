/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class RemoveIncomingTransitions extends AbstractWorkingCopyResolution {

    /** Remove activity command name. */
    private static final String COMMAND = "removeIncomingTransitionsCommand"; //$NON-NLS-1$

    /**
     * @param ed
     *            The editing domain.
     * @param target
     *            The target object.
     * @return The resolution command.
     * @throws ResolutionException
     *             If there was a problem.
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target, IMarker marker)
            throws ResolutionException {
        CompoundCommand command = new CompoundCommand(ResolutionMessages
                .getText(COMMAND));
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            List<?> incoming = activity.getIncomingTransitions();
            FlowContainer container = activity.getFlowContainer();
            command.append(RemoveCommand.create(ed, container,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Transitions(),
                    incoming));
        }
        return command;
    }

}
