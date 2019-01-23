/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.MappingDelta;

/**
 * A factory for commands to maintain mappings between formal and actual
 * parameters on various base objects.
 * 
 * @author scrossle
 * @deprecated This interface is deprecated, the functionality will be supported
 *             by the new interface IMappingCommandFactory2.refer to
 *             IMappingCommandFactory2 for further details.
 */
@Deprecated
public interface IMappingCommandFactory {

    Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target);

    Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target);

    /**
     * @param ed
     *            The editing domain.
     * @param input
     *            The input object.
     * @param changes
     *            The mapping changes.
     * @return The command to make the change.
     * 
     * @deprecated This method is never called via the Mapper which always does
     *             remove and re-add (hence only
     *             {@link #getAddMappingCommand(EditingDomain, EObject, Object, Object)}
     *             and
     *             {@link #getRemoveMappingCommand(EditingDomain, EObject, Object, Object)}
     *             are called in this interface by
     *             {@link AbstractMappingSection}
     */
    @Deprecated
    Command getChangeMappingCommand(EditingDomain ed, EObject input,
            Collection<MappingDelta> changes);

}
