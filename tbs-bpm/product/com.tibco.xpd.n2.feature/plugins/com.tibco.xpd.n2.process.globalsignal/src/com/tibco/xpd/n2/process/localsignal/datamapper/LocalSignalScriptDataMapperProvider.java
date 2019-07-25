/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper Provider for Local Signal mappings.
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalScriptDataMapperProvider extends AbstractScriptDataMapperEditorProvider {

    /**
     * Mapping direction for Local Signal Data Mapper.
     */
    private MappingDirection direction;

    /**
     * Script Data Mapper Provider for Local Signal mappings.
     * 
     * @param direction
     */
    public LocalSignalScriptDataMapperProvider(MappingDirection direction) {
        super(SignalDataMapperConstants.LOCAL_SIGNAL_CATCH, DirectionType.OUT_LITERAL);
        this.direction = direction;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#getScriptDataMapper(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    public ScriptDataMapper getScriptDataMapper(Object inputObject) {
        ScriptDataMapper sdm = null;
        if (inputObject instanceof Activity) {
            Activity activity = (Activity) inputObject;

            SignalData sigData = getScriptDataMapperContainer(activity, false, null, null);
            if (sigData != null) {
                Object sdmObject = null;

                if (MappingDirection.IN.equals(direction)) {
                    sdmObject = sigData.getInputScriptDataMapper();
                } else if (MappingDirection.OUT.equals(direction)) {
                    sdmObject = sigData.getOutputScriptDataMapper();
                }

                if (sdmObject instanceof ScriptDataMapper) {
                    sdm = (ScriptDataMapper) sdmObject;
                }
            }
        }
        return sdm;
    }

    /**
     * Remove any pre-existing JavaScript based mappings.
     * 
     * @param sigData
     * @param editingDomain
     * @param optionalCreationCommand
     */
    private void clearJavaScriptMappings(SignalData sigData, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {
        if (null != sigData.getDataMappings() && !sigData.getDataMappings().isEmpty()) {
            optionalCreationCommand.append(RemoveCommand.create(editingDomain, sigData.getDataMappings()));
        }

        if (null != sigData.getCorrelationMappings()) {
            optionalCreationCommand.append(SetCommand.create(editingDomain,
                    sigData,
                    XpdExtensionPackage.eINSTANCE.getSignalData_CorrelationMappings(),
                    null));
        }
    }

    /**
     * @param contextInputObject
     * @param createNewContainerIfNull
     *            <code>true</code> if we want to create a new container in case
     *            it is null, <code>false</code> otherwise.
     * @param optionalCreationCommand
     * @param editingDomain
     * 
     * @return the Correct container for the xpdExt:ScriptDataMapper element
     *         (depending on mapping direction we were constructed with.
     */
    public SignalData getScriptDataMapperContainer(Object contextInputObject, boolean createNewContainerIfNull,
            EditingDomain editingDomain, CompoundCommand optionalCreationCommand) {
        SignalData sigData = null;

        if (contextInputObject instanceof Activity) {

            Activity activity = (Activity) contextInputObject;
            Event event = activity.getEvent();

            if (null != event) {

                if (event instanceof IntermediateEvent) {

                    /*
                     * Intermediate events
                     */

                    IntermediateEvent intermediateEvent = (IntermediateEvent) event;

                    TriggerResultSignal trs = intermediateEvent.getTriggerResultSignal();

                    if (null != trs) {

                        Object signalDataObj = Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

                        if (CatchThrow.CATCH.equals(trs.getCatchThrow())
                                && MappingDirection.OUT.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

                            } else if (createNewContainerIfNull) {
                                sigData = XpdExtensionFactory.eINSTANCE.createSignalData();
                                optionalCreationCommand.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                                        trs,
                                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData(),
                                        sigData));
                            }
                        }
                    }

                } else if (event instanceof StartEvent) {

                    /*
                     * Start events
                     */

                    StartEvent startEvent = (StartEvent) event;

                    TriggerResultSignal trs = startEvent.getTriggerResultSignal();

                    if (null != trs) {

                        Object signalDataObj = Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

                        if (CatchThrow.CATCH.equals(trs.getCatchThrow()) && MappingDirection.OUT.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

                            } else if (createNewContainerIfNull) {
                                sigData = XpdExtensionFactory.eINSTANCE.createSignalData();
                                optionalCreationCommand.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                                        trs,
                                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData(),
                                        sigData));
                            }
                        }
                    }
                }
            }
        }
        return sigData;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#createScriptDataMapper(java.lang.Object,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param contextInputObject
     * @param editingDomain
     * @param optionalCreationCommand
     * @return
     */
    @Override
    protected ScriptDataMapper createScriptDataMapper(Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {
        ScriptDataMapper scriptDataMapper = null;

        SignalData scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject, true, editingDomain, optionalCreationCommand);

        if (scriptDataMapperContainer != null) {
            scriptDataMapper = XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {

                /*
                 * Remove old JavaScript based mappings.
                 */
                clearJavaScriptMappings(scriptDataMapperContainer, editingDomain, optionalCreationCommand);

                if (MappingDirection.IN.equals(direction)) {

                    optionalCreationCommand.append(SetCommand.create(editingDomain,
                            scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE.getSignalData_InputScriptDataMapper(),
                            scriptDataMapper));

                } else if (MappingDirection.OUT.equals(direction)) {

                    optionalCreationCommand.append(SetCommand.create(editingDomain,
                            scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE.getSignalData_OutputScriptDataMapper(),
                            scriptDataMapper));

                }
            }
        }

        return scriptDataMapper;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#usesDataMapperGrammer(java.lang.Object)
     * 
     * @param contextInputObject
     * @return
     */
    @Override
    public boolean usesDataMapperGrammer(Object contextInputObject) {

        if (contextInputObject instanceof Activity) {

            return ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory.getGrammarToUse(
                    (Activity) contextInputObject,
                    MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL : DirectionType.OUT_LITERAL));

        }

        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#getDataMapperDeselectedCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     * @param contextInputObject
     * @return
     */
    @Override
    public Command getDataMapperDeselectedCommand(EditingDomain editingDomain, Object contextInputObject) {
        /*
         * Remove the ScriptDatamapper (SubFlow/InputMappings | OutputMappings)
         * element completely.
         */
        SignalData scriptDataMapperContainer = getScriptDataMapperContainer(contextInputObject, false, null, null);

        if (scriptDataMapperContainer != null) {
            ScriptDataMapper scriptDataMapper = getScriptDataMapper(contextInputObject);

            if (scriptDataMapper != null) {
                if (MappingDirection.OUT.equals(direction)) {
                    return RemoveCommand.create(editingDomain,
                            scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE.getSignalData_OutputScriptDataMapper(),
                            scriptDataMapper);
                }
            }
        }
        return null;
    }
}
