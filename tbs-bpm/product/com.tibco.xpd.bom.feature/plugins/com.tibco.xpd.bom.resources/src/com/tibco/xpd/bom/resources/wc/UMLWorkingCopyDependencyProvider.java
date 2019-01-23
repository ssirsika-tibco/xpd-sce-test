/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.wc;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Dependency provider for the <code>UMLWorkingCopy</code>.
 * 
 * @see BOMWorkingCopyDependencyProvider
 * 
 * @author njpatel
 * 
 */
public class UMLWorkingCopyDependencyProvider extends
        BOMWorkingCopyDependencyProvider {

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return UMLWorkingCopy.class;
    }
}
