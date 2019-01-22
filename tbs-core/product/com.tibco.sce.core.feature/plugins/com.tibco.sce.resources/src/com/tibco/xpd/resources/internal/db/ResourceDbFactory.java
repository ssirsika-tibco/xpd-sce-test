/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

import com.tibco.xpd.resources.internal.db.impl.derby.ResourceDbDerby;

/**
 * This factory provides the implementation database. At the moment only
 * derby is supported. Later versions will create instances by the class name.
 * 
 * @author ramin
 *
 */
/**
 * @author ramin
 *
 */
/**
 * @author ramin
 *
 */
/**
 * @author ramin
 * 
 */
public class ResourceDbFactory {

    /**
     * Exception message
     */
    public final static String DB_NOT_IMPLEMENTED = "not implemented"; //$NON-NLS-1$

    /**
     * the apache derby implementation
     */
    public final static String DB_HASHMAP = "hashmap"; //$NON-NLS-1$

    /**
     * the apache derby implementation
     */
    public final static String DB_DERBY = "derby"; //$NON-NLS-1$

    /**
     * create a new ResourceDb provider. At the moment only one implementation
     * is provided (Apache derby).
     * 
     * @param name
     * @return
     */
    public static IResourceDb createDb(String name) {
        if (name.equals(DB_DERBY)) {
            return new ResourceDbDerby();
        }
        // else if(name.equals(DB_HASHMAP)){
        // return new ResourceDbHashmap();
        // }
        return null;
    }

    /**
     * Returns a new Resource db provider by a class name.
     * 
     * @param name
     * @return
     * @throws ResourceDbException
     */
    public static IResourceDb createDbByClassName(String name)
            throws ResourceDbException {
        throw new ResourceDbException(DB_NOT_IMPLEMENTED);
    }

}
