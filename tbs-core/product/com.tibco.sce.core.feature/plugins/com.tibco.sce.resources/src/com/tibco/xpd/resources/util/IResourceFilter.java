/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import org.eclipse.core.resources.IResource;

/**
 * 
 * 
 * @author bharge
 * @since 13 May 2013
 */
public interface IResourceFilter {

    boolean includeResource(IResource resource);

}
