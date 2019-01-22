/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper provider for Catch Error.
 * 
 * @author sajain
 * @since Feb 26, 2016
 */
public class CatchErrorScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {

    String mapperContext;

    public CatchErrorScriptDataMapperProvider(String mapperContext) {
        super(mapperContext, DirectionType.OUT_LITERAL);

        this.mapperContext = mapperContext;
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

            OtherElementsContainer oec =
                    getScriptDataMapperContainer(activity, null, null);
            if (oec != null) {
                Object other = null;

                other =
                        Xpdl2ModelUtil.getOtherElement(oec,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings());

                if (other instanceof ScriptDataMapper) {
                    sdm = (ScriptDataMapper) other;
                }
            }
        }
        return sdm;
    }

    /**
     * @param contextInputObject
     * @param optionalCreationCommand
     * @param editingDomain
     * 
     * @return the Correct container for the xpdExt:ScriptDataMapper element
     *         (depending on mapping direction we were constructed with.
     */
    public OtherElementsContainer getScriptDataMapperContainer(
            Object contextInputObject, CompoundCommand optionalCreationCommand,
            EditingDomain editingDomain) {
        OtherElementsContainer oec = null;

        if (contextInputObject instanceof Activity) {
            Activity activity = (Activity) contextInputObject;

            Event event = activity.getEvent();

            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;

                ResultError resErr = intermediateEvent.getResultError();

                if (resErr != null) {
                    Object catchErrMappingsObj =
                            Xpdl2ModelUtil
                                    .getOtherElement(resErr,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CatchErrorMappings());

                    if (editingDomain != null
                            && optionalCreationCommand != null
                            && catchErrMappingsObj == null) {
                        CatchErrorMappings cem =
                                XpdExtensionFactory.eINSTANCE
                                        .createCatchErrorMappings();

                        cem.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

                        optionalCreationCommand
                                .append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(editingDomain,
                                                resErr,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_CatchErrorMappings(),
                                                cem));

                        catchErrMappingsObj = cem;
                    }

                    if (catchErrMappingsObj instanceof CatchErrorMappings) {
                        CatchErrorMappings cem =
                                (CatchErrorMappings) catchErrMappingsObj;

                        Message msg = cem.getMessage();

                        if (editingDomain != null
                                && optionalCreationCommand != null
                                && msg == null) {
                            msg = Xpdl2Factory.eINSTANCE.createMessage();

                            optionalCreationCommand
                                    .append(SetCommand
                                            .create(editingDomain,
                                                    cem,
                                                    Xpdl2Package.eINSTANCE
                                                            .getTriggerResultMessage_Message(),
                                                    msg));
                        }

                        if (msg instanceof OtherElementsContainer) {
                            oec = msg;
                        }
                    }
                }
            }
        }
        return oec;
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

        OtherElementsContainer scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject,
                        optionalCreationCommand,
                        editingDomain);

        if (scriptDataMapperContainer != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {

                optionalCreationCommand.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                scriptDataMapperContainer,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings(),
                                scriptDataMapper));
            } else {

                Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_OutputMappings(),
                        scriptDataMapper);
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

            return ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                    .getGrammarToUse((Activity) contextInputObject,
                            DirectionType.OUT_LITERAL));

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
         * Remove the ScriptDatamapper (OutputMappings) element completely.
         */
        OtherElementsContainer scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject, null, null);

        if (scriptDataMapperContainer != null) {
            ScriptDataMapper scriptDataMapper =
                    getScriptDataMapper(contextInputObject);

            if (scriptDataMapper != null) {

                return Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(editingDomain,
                                scriptDataMapperContainer,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings(),
                                scriptDataMapper);
            }
        }
        return null;
    }
}
