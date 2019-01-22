/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import com.tibco.xpd.script.model.client.ITypeResolver;


/**
 * This interface provides the type resolver  
 * 
 * @author mtorres
 * 
 */
public interface ITypeResolverProvider {

    /**
     * @return the type resolver
     * 
     **/
    ITypeResolver getTypeResolver();
    
}
