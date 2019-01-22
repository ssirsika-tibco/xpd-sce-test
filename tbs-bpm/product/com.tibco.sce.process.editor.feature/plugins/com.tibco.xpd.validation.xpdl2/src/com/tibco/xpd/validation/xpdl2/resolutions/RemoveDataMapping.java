/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * Resolution to remove Mappings between content of source and target tree.
 * 
 * @author nwilson
 */
public class RemoveDataMapping extends AbstractWorkingCopyResolution {

    /** Remove DataMapping command name. */
    private static final String COMMAND = "removeDataMappingCommand"; //$NON-NLS-1$

    /**
     * @param ed
     *            The editing domain.
     * @param target
     *            The target object.
     * @param marker
     *            The problem marker.
     * @return The resolution command.
     * @throws ResolutionException
     *             If there was a problem.
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand command =
                new CompoundCommand(ResolutionMessages.getText(COMMAND));
        if (target instanceof DataMapping) {
            DataMapping mapping = (DataMapping) target;
            EObject container = mapping.eContainer();

            if (container != null) {
                command.append(RemoveCommand.create(ed, mapping));
            }
        } else if (target instanceof DynamicOrganizationMapping
                || target instanceof DataWorkItemAttributeMapping) {
            /*
             * If we are trying to remove a Dynamic Org Identifier mapping OR
             * Work Item Attribute Alias Mappings, then , the target itself is
             * an instance of DynamicOrganizationMapping/DataAliasMapping
             */
            command.append(RemoveCommand.create(ed, target));
        }
        return command;
    }
}
