/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Provides mapping items for REST service mappings.
 * 
 * @author jarciuch
 */
public class RestJsMappingItemProvider extends
        BaseJavaScriptMappingItemProvider {

    public RestJsMappingItemProvider(MappingDirection direction) {
        super(direction);
    }

    @Override
    public String getScriptContext() {
        return ScriptMappingCompositorFactory.DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolveParameter(Activity activity,
            MappingDirection direction, String formalName) {
        return RestMapperTreeItemFactory.getInstance()
                .resolveParameter(activity, direction, formalName);
    }

}
