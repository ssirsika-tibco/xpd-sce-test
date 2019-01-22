/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.n2.process.globalsignal.util.GlobalSignalMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;

/**
 * Command factory for Catch Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public class CatchGlobalSignalMappingCommandFactory extends
        AbstractDataMappingCommandFactory {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2#getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     * 
     * @param ed
     * @param owner
     * @param source
     * @param target
     * @return
     */
    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            final Object source, final Object target) {

        final Activity throwSignalEvent = (Activity) owner;

        AbstractLateExecuteCommand cmd =
                new AbstractLateExecuteCommand(ed, null) {

                    @Override
                    protected Command createCommand(
                            EditingDomain editingDomain, Object contextObject) {

                        Command addMappingCmd =
                                GlobalSignalMappingUtil
                                        .getCreateCatchGlobalSignalMappingCommand(editingDomain,
                                                source,
                                                target,
                                                throwSignalEvent);
                        return addMappingCmd;
                    }
                };

        cmd.setLabel(Messages.CatchGlobalSignalMappingCommandFactory_AddSignalMappingCommand_label);

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory#getUnmappedScriptContainerObject(org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.DataMapping)
     * 
     * @param owner
     * @param dataMapping
     * @return
     */
    @Override
    protected OtherElementsContainer getUnmappedScriptContainerObject(
            EObject owner, DataMapping dataMapping) {
        if (owner instanceof Activity) {
            return (Activity) owner;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory#getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.Mapping)
     * 
     * @param ed
     * @param owner
     * @param mapping
     * @return
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {

        CompoundCommand cmd = new CompoundCommand(getRemoveCommandLabel());
        /*
         * let the super class handle all the remove mappings computations.
         */
        cmd.append(super.getRemoveMappingCommand(ed, owner, mapping));

        if (mapping.getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) mapping.getMappingModel();

            if (dataMapping.eContainer() instanceof CorrelationDataMappings) {
                CorrelationDataMappings correlationMappings =
                        (CorrelationDataMappings) dataMapping.eContainer();

                if (correlationMappings.getDataMappings().size() == 1) {
                    /*
                     * If the last corremation mapping is removed then remove
                     * the empty container as well.
                     */
                    cmd.append(LateExecuteRemoveCommand.create(ed,
                            correlationMappings));
                }
            }
        }
        return cmd;
    }
}
