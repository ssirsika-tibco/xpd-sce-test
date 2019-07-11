/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.Activator;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper Provider for Global Signal Catch/Throw.
 * 
 * @author sajain
 * @since Apr 26, 2016
 */
public class GlobalSignalScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {

    private MappingDirection direction;

    public GlobalSignalScriptDataMapperProvider(MappingDirection direction) {
        super(
                MappingDirection.IN.equals(direction) ? GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_THROW
                        : GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_CATCH,
                MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL);
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

            SignalData sigData = getScriptDataMapperContainer(activity);
            if (sigData != null) {
                Object sdmObject = null;

                if (MappingDirection.IN.equals(direction)) {
                    sdmObject =
                            sigData.getInputScriptDataMapper();
                } else if (MappingDirection.OUT.equals(direction)) {
                    sdmObject =
                            sigData.getOutputScriptDataMapper();
                }

                if (sdmObject instanceof ScriptDataMapper) {
                    sdm = (ScriptDataMapper) sdmObject;
                }
            }
        }
        return sdm;
    }

    /**
     * @param contextInputObject
     * 
     * @return the Correct container for the xpdExt:ScriptDataMapper element
     *         (depending on mapping direction we were constructed with.
     */
    public SignalData getScriptDataMapperContainer(
            Object contextInputObject) {
        SignalData sigData = null;

        if (contextInputObject instanceof Activity) {

            Activity activity = (Activity) contextInputObject;
            Event event = activity.getEvent();

            if (null != event) {

                if (event instanceof EndEvent) {

                    /*
                     * End events
                     */

                    EndEvent endEvent = (EndEvent) event;

                    TriggerResultSignal trs = endEvent.getTriggerResultSignal();
                    if (null != trs) {

                        Object signalDataObj =
                                Xpdl2ModelUtil.getOtherElement(trs,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SignalData());

                        if (CatchThrow.THROW.equals(trs.getCatchThrow())
                                && MappingDirection.IN.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

                            }
                        }
                    }

                } else if (event instanceof IntermediateEvent) {

                    /*
                     * Intermediate events
                     */

                    IntermediateEvent intermediateEvent =
                            (IntermediateEvent) event;

                    TriggerResultSignal trs =
                            intermediateEvent.getTriggerResultSignal();

                    if (null != trs) {

                        Object signalDataObj =
                                Xpdl2ModelUtil.getOtherElement(trs,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SignalData());

                        if (CatchThrow.THROW.equals(trs.getCatchThrow())
                                && MappingDirection.IN.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

                            }

                        } else if (CatchThrow.CATCH.equals(trs.getCatchThrow())
                                && MappingDirection.OUT.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

                            }
                        }
                    }

                } else if (event instanceof StartEvent) {

                    /*
                     * Start events
                     */

                    StartEvent startEvent = (StartEvent) event;

                    TriggerResultSignal trs =
                            startEvent.getTriggerResultSignal();

                    if (null != trs) {

                        Object signalDataObj =
                                Xpdl2ModelUtil.getOtherElement(trs,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_SignalData());

                        if (CatchThrow.CATCH.equals(trs.getCatchThrow())
                                && MappingDirection.OUT.equals(direction)) {

                            if (signalDataObj instanceof SignalData) {

                                sigData = (SignalData) signalDataObj;

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
    protected ScriptDataMapper createScriptDataMapper(
            Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {
        ScriptDataMapper scriptDataMapper = null;

        SignalData scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {

                if (MappingDirection.IN.equals(direction)) {

                    optionalCreationCommand.append(SetCommand.create(editingDomain, scriptDataMapperContainer, XpdExtensionPackage.eINSTANCE
                                    .getSignalData_InputScriptDataMapper(), scriptDataMapper));
                   

                } else if (MappingDirection.OUT.equals(direction)) {

                    optionalCreationCommand.append(SetCommand.create(editingDomain, scriptDataMapperContainer, XpdExtensionPackage.eINSTANCE
                            .getSignalData_OutputScriptDataMapper(), scriptDataMapper));

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

            return ScriptGrammarFactory.DATAMAPPER
                    .equals(ScriptGrammarFactory
                            .getGrammarToUse((Activity) contextInputObject,
                                    MappingDirection.IN.equals(direction) ? DirectionType.IN_LITERAL
                                            : DirectionType.OUT_LITERAL));

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
    public Command getDataMapperDeselectedCommand(EditingDomain editingDomain,
            Object contextInputObject) {
        /*
         * Remove the ScriptDatamapper (SubFlow/InputMappings | OutputMappings)
         * element completely.
         */
        SignalData scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            ScriptDataMapper scriptDataMapper =
                    getScriptDataMapper(contextInputObject);

            if (scriptDataMapper != null) {
                if (MappingDirection.IN.equals(direction)) {
                    return RemoveCommand.create(editingDomain, scriptDataMapperContainer, XpdExtensionPackage.eINSTANCE
                                            .getSignalData_InputScriptDataMapper(), scriptDataMapper);

                } else if (MappingDirection.OUT.equals(direction)) {
                    return RemoveCommand.create(editingDomain, scriptDataMapperContainer, XpdExtensionPackage.eINSTANCE
                            .getSignalData_OutputScriptDataMapper(), scriptDataMapper);
                }
            }
        }
        return null;
    }
}
