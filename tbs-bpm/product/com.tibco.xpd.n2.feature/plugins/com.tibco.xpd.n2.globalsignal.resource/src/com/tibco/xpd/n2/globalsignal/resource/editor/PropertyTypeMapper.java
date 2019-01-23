/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

/**
 * A property type mapper implementation to be used with the properties
 * contributor extension.
 * 
 * @author sajain
 * @since Feb 12, 2015
 */

public class PropertyTypeMapper implements ITypeMapper {

    @Override
    public Class mapType(Object object) {

        return object.getClass();
    }
}
