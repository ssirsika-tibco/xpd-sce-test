/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import java.io.InputStream;

import org.eclipse.core.resources.IStorage;

/**
 * An extensiont of the IStorage interface that allows the content to be set.
 * 
 * @author NWilson
 */
public interface ISaveableStorage extends IStorage {

    void setContents(InputStream input);

}
