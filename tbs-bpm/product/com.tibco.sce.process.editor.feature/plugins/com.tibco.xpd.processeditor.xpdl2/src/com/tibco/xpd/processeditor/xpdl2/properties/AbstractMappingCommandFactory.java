/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;

/**
 * A factory for commands to maintain mappings between formal and actual
 * parameters on various base objects.
 * <p>
 * This class is independent of the model objects used for both teh data-mapping
 * and the owner.
 * 
 * @author aprasad
 * @since 22 Feb 2013
 */
public abstract class AbstractMappingCommandFactory implements
        IMappingCommandFactory2 {

    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {
        Object dataMapping = mapping.getMappingModel();

        if (dataMapping instanceof EObject) {
            CompoundCommand cmd = new CompoundCommand(getRemoveCommandLabel());

            appendRemoveMappingCommand(ed, cmd, (EObject) dataMapping);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @return The remove command label.
     */
    protected String getRemoveCommandLabel() {
        return Messages.MappingCommandFactory_RemoveMapping_menu;
    }

    /**
     * Append command to remove the given data mapping.
     * 
     * @param ed
     * @param cmd
     * @param dataMappingsContainer
     * @param dataMapping
     */
    private void appendRemoveMappingCommand(EditingDomain ed,
            CompoundCommand cmd, EObject dataMappingModel) {
        cmd.append(LateExecuteRemoveCommand.create(ed, dataMappingModel));
    }

}
