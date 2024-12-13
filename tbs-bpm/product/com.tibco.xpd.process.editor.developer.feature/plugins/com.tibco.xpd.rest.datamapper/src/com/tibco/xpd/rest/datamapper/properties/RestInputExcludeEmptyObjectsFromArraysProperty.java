/*
 * (c) 2004-2023 Cloud Software Group, Inc.
 */

package com.tibco.xpd.rest.datamapper.properties;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.rest.datamapper.AdvancedRestServiceInputMappingProperties.AbstractExcludeEmptyObjectsFromArraysProperty;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * REST Input mapping, advanced properties for Target Mapping configuration options...
 * 
 * Do not include empty objects from arrays data - If the target data is an object in an array, then it will not be
 * included in the array at all if after performing all mapping assignments then it has no content.
 *
 * @author aallway
 * @since Feb 2023
 */
public class RestInputExcludeEmptyObjectsFromArraysProperty extends AbstractExcludeEmptyObjectsFromArraysProperty {

    /**
     * Super class does everything except decide the location of the {@link ScriptDataMapper} model is relative to the
     * selected input.
     * 
     * All we have to do is provide the {@link AbstractScriptDataMapperEditorProvider} that knows the location / how to
     * create {@link ScriptDataMapper} for REST input mapping scenario.
     * 
     * @param dataMapperInfoProvider
     */
    public RestInputExcludeEmptyObjectsFromArraysProperty() {
		/*
		 * Sid ACE-8864 Updated underlying classes to figure out correct context for itself (otherwise the separate
		 * contributions for RSD and Swagger fought against each other)
		 */
		super();
    }

}
