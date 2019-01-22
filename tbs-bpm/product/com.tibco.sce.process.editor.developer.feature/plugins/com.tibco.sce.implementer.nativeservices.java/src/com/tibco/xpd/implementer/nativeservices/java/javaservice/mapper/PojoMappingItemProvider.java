/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * @author rsomayaj
 * 
 */
public class PojoMappingItemProvider extends BaseJavaScriptMappingItemProvider {

    /**
     * @param direction
     */
    public PojoMappingItemProvider(MappingDirection direction) {
        super(direction);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#getScriptContext()
     * 
     * @return
     */
    @Override
    public String getScriptContext() {
        return JavaServiceMappingUtil.SCRIPT_CONTEXT;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.BaseJavaScriptMappingItemProvider#resolveParameter(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.mapper.MappingDirection, java.lang.String)
     * 
     * @param activity
     * @param direction2
     * @param formalName
     * @return
     */
    @Override
    public Object resolveParameter(Activity activity,
            MappingDirection direction2, String formalName) {
        return JavaServiceMappingUtil.resolveParameter(activity,
                direction2,
                formalName);
    }

}
