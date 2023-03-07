/*
 * (c) 2004-2023 Cloud Software Group, Inc.
 */

package com.tibco.xpd.rest.datamapper.properties;

import com.tibco.xpd.datamapper.properties.AdvancedRestServiceInputMappingProperties.AbstractExcludeEmptyOptionalArraysProperty;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * REST Input mapping, advanced properties for Target Mapping configuration options...
 * 
 * Do not include empty arrays for optional target data - If the target data is an array, then it will not be included
 * in the parent object/array at all if after performing all mapping assignments then it has no content.
 *
 * @author aallway
 * @since Feb 2023
 */
public class RestInputExcludeEmptyOptionalArraysProperty extends AbstractExcludeEmptyOptionalArraysProperty {

    /**
     * Super class does everything except decide the location of the {@link ScriptDataMapper} model is relative to the
     * selected input.
     * 
     * All we have to do is provide the {@link AbstractScriptDataMapperEditorProvider} that knows the location / how to
     * create {@link ScriptDataMapper} for REST input mapping scenario.
     * 
     * @param dataMapperInfoProvider
     */
    public RestInputExcludeEmptyOptionalArraysProperty() {
        super(new RestScriptDataMapperProvider(MappingDirection.IN));
    }

}
