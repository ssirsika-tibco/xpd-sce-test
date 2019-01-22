/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping command provider for Intermediate Catch Error Events which catch
 * events that business analyst mode can handle... (Catch All, Catch By Name
 * Only or Catch Specific Thrown by End Error Event).
 * <p>
 * This is for the business analyst capability only (there is an alternative
 * script editor with script editor for solution desginer capability in the
 * implementer plugins)
 * <p>
 * It provides a left hand side of a in-built special param called
 * "[Error Code]" (that is represented by the static concept path
 * {@link CatchErrorMapperErrCodeSourceParam#CATCH_ERRORCODE_CONCEPTPATH}) AND,
 * when the error from a specific end error event is caught, the formal
 * parameter data associated with that end event.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchErrorMapperCommandFactory extends
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
        return true;
    }

    @Override
    public boolean isValidTransfer(Object source, Object target) {
        // Not a valid transfer if either the source or the target is a
        // ChoiceConceptPath
        if (source instanceof ChoiceConceptPath
                || target instanceof ChoiceConceptPath) {
            return false;
        }
        if ((source instanceof ConceptPath || source instanceof ScriptInformation)
                && target instanceof ConceptPath) {
            return true;
        }
        return false;
    }

    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.CatchErrorMapperCommandFactory_AddErrorMapping_menu);

        // Owner is always a catch error event.
        Activity catchErrorEvent = (Activity) owner;

        // Get or create the message (the data mappings container).
        Message msg = getOrCreateCatchErrorMessage(ed, catchErrorEvent, cmd);

        //
        Command addMappingCmd =
                StandardMappingUtil
                        .createMapFromScriptOrProcDataToProcDataCommand(ed,
                                source,
                                target,
                                catchErrorEvent,
                                msg,
                                Xpdl2Package.eINSTANCE
                                        .getMessage_DataMappings(),
                                msg.getDataMappings(),
                                MappingDirection.OUT);

        if (addMappingCmd != null) {
            cmd.append(addMappingCmd);
        } else {
            cmd.append(UnexecutableCommand.INSTANCE);
        }

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

    /**
     * Get the xpdl2 Message element for the given catch error event. If it does
     * not already exist, create it (by appending commands to cmd).
     * <p>
     * Message element is found in
     * Activity/IntermediateEvent/ResultError/xpdExt:CatchErrorMappings
     * 
     * @param ed
     * @param catchErrorEvent
     * @param cmd
     * 
     * @return Message
     */
    private Message getOrCreateCatchErrorMessage(EditingDomain ed,
            Activity catchErrorEvent, CompoundCommand cmd) {
        if (catchErrorEvent.getEvent() == null
                || !(catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError)) {
            throw new RuntimeException(
                    "Unexpected (non Catch error) activity supplied!" + catchErrorEvent); //$NON-NLS-1$
        }

        ResultError resError =
                (ResultError) catchErrorEvent.getEvent()
                        .getEventTriggerTypeNode();

        CatchErrorMappings catchErrorMappings =
                (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resError,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchErrorMappings());

        if (catchErrorMappings == null) {
            catchErrorMappings =
                    XpdExtensionFactory.eINSTANCE.createCatchErrorMappings();

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    resError,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CatchErrorMappings(),
                    catchErrorMappings));
        }

        Message msg = catchErrorMappings.getMessage();

        if (msg == null) {
            msg = Xpdl2Factory.eINSTANCE.createMessage();
            cmd.append(SetCommand.create(ed,
                    catchErrorMappings,
                    XpdExtensionPackage.eINSTANCE
                            .getCatchErrorMappings_Message(),
                    msg));
        }

        return msg;
    }

}