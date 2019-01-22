/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Interface required in the internals of Data Mapper framework that provides
 * the various framework components access to the xpdExt:ScriptDataMapper
 * element.
 * <p>
 * This is required in the generic mapping-scenario-agnostic underlying
 * framework because many aspects (like mapping exclusions, array inflation mode
 * etc) are stored generically in the ScriptDataMapper element.
 * <p>
 * However the location of the ScriptDatamapper element is scenario specific so
 * must be supplied into the various framework components.
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public interface IScriptDataMapperProvider {
    /**
     * @param contextInputObject
     *            The context item to get the SDM for.
     * @return The xpdExt:ScriptDataMapper element for the current data mapper
     *         context.
     */
    ScriptDataMapper getScriptDataMapper(Object contextInputObject);

    /**
     * @param contextInputObject
     *            The context item to check the script grammer for.
     * @return true if the DataMapper grammer is set.
     */
    boolean usesDataMapperGrammer(Object contextInputObject);
}
