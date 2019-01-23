/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.descriptor;

import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

/**
 * A property type mapper implementation to be used with the properties
 * contributor extension.
 * 
 * @author wzurek
 * 
 */
public class PropertyTypeMapper implements ITypeMapper {

    public Class mapType(Object object) {
        return object.getClass();
    }
}
