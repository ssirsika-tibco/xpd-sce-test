/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Data Mapper Provider for Sub-process invocations and mapping
 * directions.
 * 
 * @author sajain
 * @since Oct 20, 2015
 */
public class SubProcessScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {

    private MappingDirection direction;

    public SubProcessScriptDataMapperProvider(MappingDirection direction) {
        super(
                MappingDirection.IN.equals(direction) ? SubProcessDataMapperConstants.PROCESS_TO_SUBPROCESS
                        : SubProcessDataMapperConstants.SUBPROCESS_TO_PROCESS,
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

            OtherElementsContainer oec = getScriptDataMapperContainer(activity);
            if (oec != null) {
                Object other = null;

                if (MappingDirection.IN.equals(direction)) {
                    other =
                            Xpdl2ModelUtil.getOtherElement(oec,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings());
                } else if (MappingDirection.OUT.equals(direction)) {
                    other =
                            Xpdl2ModelUtil.getOtherElement(oec,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings());
                }

                if (other instanceof ScriptDataMapper) {
                    sdm = (ScriptDataMapper) other;
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
    public OtherElementsContainer getScriptDataMapperContainer(
            Object contextInputObject) {
        OtherElementsContainer oec = null;

        if (contextInputObject instanceof Activity) {
            Activity activity = (Activity) contextInputObject;
            Implementation impl = activity.getImplementation();
            if (impl instanceof SubFlow) {

                SubFlow subFlow = (SubFlow) impl;
                if (MappingDirection.IN.equals(direction)) {

                    Object obj = subFlow;

                    if (obj instanceof OtherElementsContainer) {
                        oec = (OtherElementsContainer) obj;
                    }

                } else if (MappingDirection.OUT.equals(direction)) {

                    Object obj = subFlow;

                    if (obj instanceof OtherElementsContainer) {
                        oec = (OtherElementsContainer) obj;
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
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            if (optionalCreationCommand != null) {

                if (MappingDirection.IN.equals(direction)) {

                    optionalCreationCommand.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings(),
                                    scriptDataMapper));

                } else if (MappingDirection.OUT.equals(direction)) {

                    optionalCreationCommand.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings(),
                                    scriptDataMapper));

                }
            } else {

                if (MappingDirection.IN.equals(direction)) {

                    Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InputMappings(),
                            scriptDataMapper);

                } else if (MappingDirection.OUT.equals(direction)) {

                    Xpdl2ModelUtil.setOtherElement(scriptDataMapperContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OutputMappings(),
                            scriptDataMapper);

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
        OtherElementsContainer scriptDataMapperContainer =
                getScriptDataMapperContainer(contextInputObject);

        if (scriptDataMapperContainer != null) {
            ScriptDataMapper scriptDataMapper =
                    getScriptDataMapper(contextInputObject);

            if (scriptDataMapper != null) {
                if (MappingDirection.IN.equals(direction)) {
                    return Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InputMappings(),
                                    scriptDataMapper);

                } else if (MappingDirection.OUT.equals(direction)) {
                    return Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    scriptDataMapperContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_OutputMappings(),
                                    scriptDataMapper);
                }
            }
        }
        return null;
    }
}
