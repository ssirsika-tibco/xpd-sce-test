/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class RemoveActivityResolution implements IResolution {

    /** Remove activity command name. */
    private static final String COMMAND = "removeActivityCommand"; //$NON-NLS-1$

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
                    CompoundCommand command =
                            new CompoundCommand(ResolutionMessages
                                    .getText(COMMAND));
                    Activity activity = (Activity) target;
                    FlowContainer container = activity.getFlowContainer();
                    List incoming = activity.getIncomingTransitions();
                    List outgoing = activity.getOutgoingTransitions();
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(container);
                    EStructuralFeature activities =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities();
                    EStructuralFeature transitions =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions();
                    command.append(new RemoveCommand(ed, container,
                            transitions, incoming));
                    command.append(new RemoveCommand(ed, container,
                            transitions, outgoing));
                    command.append(new RemoveCommand(ed, container, activities,
                            activity));
                    if (command.canExecute()) {
                        ed.getCommandStack().execute(command);
                    }
                }
            }
        } catch (CoreException e) {
            throw new ResolutionException(e);
        }
    }
}
