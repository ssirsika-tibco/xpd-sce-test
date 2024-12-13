/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * This abstract adds 'editing' capabilities to the underlying
 * {@link IScriptDataMapperProvider} that provides the various data mapper
 * framework components access to the xpdExt:ScriptDataMapper element.
 * <p>
 * This is required in the generic mapping-scenario-agnostic underlying
 * framework because many aspects (like mapping exclusions, array inflation mode
 * etc) are stored generically in the ScriptDataMapper element.
 * <p>
 * However the location of the ScriptDatamapper element is scenario specific so
 * must be supplied into the various framework components.
 * 
 * @author aallway
 * @since 7 May 2015
 */
public abstract class AbstractScriptDataMapperEditorProvider implements
        IScriptDataMapperProvider {

    private String mapperContext;

    private DirectionType mappingDirection;

    /**
     * @param mapperContext
     *            The mapping context to use for this data mapper
     * @param mappingDirection
     *            The mappign direction to use for this mapper.
     */
    public AbstractScriptDataMapperEditorProvider(String mapperContext,
            DirectionType mappingDirection) {
        super();
        this.mapperContext = mapperContext;
        this.mappingDirection = mappingDirection;
    }

    /**
     * Get the element that should contain the xpdExt:ScriptDataMapper element
     * for the given context object, if it does not exist the append a commend
     * to the optionalCreationCommand to create it.
     * 
     * @param contextInputObject
     * @param optionalCreationCommand
     * 
     * @return The ScriptDataMapper element
     */
    public final ScriptDataMapper getOrCreateScriptDataMapper(
            Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {

        ScriptDataMapper scriptDataMapper =
                getScriptDataMapper(contextInputObject);

		// ACE - 8268 Recreate the ScriptDataMapper with the correct context type
		if (scriptDataMapper == null || !(scriptDataMapper.getMapperContext().equals(mapperContext)))
		{
            scriptDataMapper =
                    createScriptDataMapper(contextInputObject,
                            editingDomain,
                            optionalCreationCommand);
        }

        /*
         * Sid XPD-7078 - half way thru dev I added new MapperContext and
         * MappingDirection attributes :: rather than forcing a re-develop on
         * all test resources we'll add them here if not already there
         * (obviosuly for all custome rcases they'll be there on initial create.
         */
        if (scriptDataMapper != null) {
            if (!scriptDataMapper.isSetMapperContext()) {
                /*
                 * XPD-7820: If command available then append to command, if
                 * scriptDataMapper is not attached to model as yet then set the
                 * attribute directly.
                 */
                if (optionalCreationCommand != null) {
                    optionalCreationCommand
                            .append(SetCommand
                                    .create(editingDomain,
                                            scriptDataMapper,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getScriptDataMapper_MapperContext(),
                                            mapperContext));
                } else {

                    scriptDataMapper.setMapperContext(mapperContext);
                }
            }

            if (!scriptDataMapper.isSetMappingDirection()) {
                /*
                 * XPD-7820: If command available then append to command, if
                 * scriptDataMapper is not attached to model as yet then set the
                 * attribute directly.
                 */
                if (optionalCreationCommand != null) {
                    optionalCreationCommand
                            .append(SetCommand
                                    .create(editingDomain,
                                            scriptDataMapper,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getScriptDataMapper_MappingDirection(),
                                            mappingDirection));
                } else {
                    scriptDataMapper.setMappingDirection(mappingDirection);
                }
            }
        }

        return scriptDataMapper;
    }

    /**
     * Create the element that should contain the xpdExt:ScriptDataMapper
     * element for the given context object, if it does not exist the append a
     * commend to the optionalCreationCommand to create it.
     * <p>
     * This method will only be called to create teh {@link ScriptDataMapper}
     * element if {@link #getScriptDataMapper(Object)} returns <code>null</code>.
     * 
     * @param contextInputObject
     * @param editingDomain
     * @param optionalCreationCommand
     * 
     * @return The ScriptDataMapper element
     */
    protected abstract ScriptDataMapper createScriptDataMapper(
            Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand);

    /**
     * @return the mapperContext
     */
    public String getMapperContext() {
        return mapperContext;
    }

    /**
     * @return the mappingDirection
     */
    public DirectionType getMappingDirectionType() {
        return mappingDirection;
    }

    /**
     * In circumstances where mapping grammar is selectable by user in a
     * datamapper screen, it is usually necessary to remove the ScriptDataMapper
     * element completely (in order that other parts of the system like
     * ScriptGrammarFactory do not think that the mapping scenario is still
     * using datamapper grammar.
     * <p>
     * This method allows the data mapper provider to clean up any model that is
     * no longer needed when datamapper grammar is deselected.
     * <p>
     * <b>Note: If the mapping scenario does not allow grammar selection, then
     * there is no need to implement this functionality and you can return
     * null</b>
     * 
     * 
     * @param editingDomain
     * @param contextInputObject
     * 
     * @return The command to clean up any model that is no longer needed when
     *         datamapper grammar is deselected (or <code>null</code> if the
     *         mapping scenarios that this provider is used for does not allow
     *         grammar selection).
     */
    public abstract Command getDataMapperDeselectedCommand(
            EditingDomain editingDomain, Object contextInputObject);

}
