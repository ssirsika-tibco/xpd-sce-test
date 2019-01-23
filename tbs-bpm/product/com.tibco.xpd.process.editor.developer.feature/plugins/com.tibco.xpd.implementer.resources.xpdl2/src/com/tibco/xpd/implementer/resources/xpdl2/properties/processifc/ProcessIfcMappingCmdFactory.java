/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;

/**
 * 
 * @author rsomayaj
 * @deprecated This mapping command factory was never required to actually do
 *             anything because process interface message event mappings are not
 *             editable by the user.
 */
@Deprecated
public class ProcessIfcMappingCmdFactory implements IMappingCommandFactory {

    private MappingDirection createDirection;

    public ProcessIfcMappingCmdFactory(MappingDirection mappingDirection) {
        this.createDirection = mappingDirection;

    }

    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        /*
         * This mapping command factory was never required to actually do
         * anything because process interface message event mappings are not
         * editable by the user.
         */
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param input
     *            The input object.
     * @param delta
     *            The mapping delta.
     * @return The command to make the changes.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory#getChangeMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.MappingDelta)
     * @deprecated This method is never called via the Mapper which always does
     *             remove and re-add
     */
    @Override
    @Deprecated
    public Command getChangeMappingCommand(EditingDomain ed, EObject owner,
            Collection<MappingDelta> changes) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        /*
         * This mapping command factory was never required to actually do
         * anything because process interface message event mappings are not
         * editable by the user.
         */
        return UnexecutableCommand.INSTANCE;
    }

}
