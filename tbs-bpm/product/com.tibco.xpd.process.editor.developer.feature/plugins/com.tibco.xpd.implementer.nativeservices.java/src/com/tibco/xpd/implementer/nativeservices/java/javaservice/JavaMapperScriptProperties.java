/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * @author nwilson
 */
public class JavaMapperScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public JavaMapperScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    public String getScriptContext() {
        return JavaServiceMappingUtil.SCRIPT_CONTEXT;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new JavaServiceScriptProvider(scriptGrammar,
                getMappingDirection());
    }

    @Override
    public boolean isLoadProcessData() {
        MappingDirection mappingDirection = getMappingDirection();
        if (mappingDirection != null && mappingDirection.name() != null) {
            if (mappingDirection.name().equals(MappingDirection.OUT.name())) {
                return false;
            }
        }
        return true;
    }

}
