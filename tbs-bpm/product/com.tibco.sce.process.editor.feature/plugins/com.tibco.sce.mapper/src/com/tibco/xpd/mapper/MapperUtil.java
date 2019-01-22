/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.UniqueIdElement;


/** 
 * @author Miguel Torres
 *
 */
public class MapperUtil {

    public static String getMapperId(MappingDirection mappingDirection,
            EObject eObject) {
        String mapperId = "propertySectionMapper"; //$NON-NLS-1$
        if (mappingDirection != null) {
            mapperId += mappingDirection.toString();
        }
        if (eObject instanceof UniqueIdElement) {
            mapperId += ((UniqueIdElement) eObject).getId();
        }
        return mapperId;
    }
}
