/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;

/**
 * Mapping command provider for Intermediate Catch Signal Events
 * <p>
 * This is for the business analyst capability only (there is an alternative
 * script editor with script editor for solution designer capability in the
 * implementer plugins)
 * <p>
 * It provides a left hand side of data associated with teh event(s) that throw
 * the signal.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchSignalMapperCommandFactory extends
        AbstractDataMappingCommandFactory implements IMappingCommandFactory,
        IMappingTransferValidator {

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

    @Override
    public boolean canAcceptMultipleInputs(Object target) {
        /* Prevent concatenation */
        return false;
    }

    @Override
    public boolean isValidTransfer(Object source, Object target) {
        // Not a valid transfer if either the source or the target is a
        // ChoiceConceptPath
        if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }

        /* Ignore special dummy data added for complaints about problems. */
        if (source instanceof ConceptPath) {
            Object item = ((ConceptPath) source).getItem();
            if (item instanceof ProcessRelevantData) {
                if (CatchSignalMapperSourceContentProvider.CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID
                        .equals(((ProcessRelevantData) item).getId())) {
                    return false;
                }
            }
        }

        if ((source instanceof ConceptPath || source instanceof ScriptInformation)
                && target instanceof ConceptPath) {
            return true;
        }
        return false;
    }

    @Override
    public Command getAddMappingCommand(EditingDomain ed, final EObject owner,
            final Object source, final Object target) {

        // Owner is always a catch signal event.
        final Activity catchSignalEvent = (Activity) owner;

        if (!EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(catchSignalEvent))) {
            throw new RuntimeException(
                    "Unexpected (non Catch error) activity supplied!" + catchSignalEvent); //$NON-NLS-1$
        }

        AbstractLateExecuteCommand cmd =
                new AbstractLateExecuteCommand(ed, null) {

                    @Override
                    protected Command createCommand(
                            EditingDomain editingDomain, Object contextObject) {
                        Command addMappingCmd =
                                CatchSignalMappingUtil
                                        .getCreateMappingCommand(editingDomain,
                                                source,
                                                target,
                                                catchSignalEvent);
                        return addMappingCmd;
                    }
                };

        cmd.setLabel(Messages.CatchSignalMapperCommandFactory_AddSignalDataMapping_menu);

        return cmd;
    }

    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        // XPD-4275:Interface ImappingCommandFactory is deprecated and the new
        // interface IMappingCommandFactory2 and its methods will be used, hence
        // this method is no more required to do anything.
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @deprecated This method is never called via the Mapper which always does
     *             remove and re-add
     */
    @Override
    @Deprecated
    public Command getChangeMappingCommand(EditingDomain ed, EObject owner,
            Collection<MappingDelta> changes) {
        return UnexecutableCommand.INSTANCE;
    }

}