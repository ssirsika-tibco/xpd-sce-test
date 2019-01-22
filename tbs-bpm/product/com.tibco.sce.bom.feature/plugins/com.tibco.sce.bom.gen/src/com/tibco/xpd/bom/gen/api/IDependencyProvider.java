/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.api;

import java.util.List;

import org.eclipse.core.resources.IFile;

/**
 * Implemented by a class that will provide dependency information to the
 * {@link BOMGenerator2}.
 * 
 * @author njpatel
 * @since 3.3
 */
public interface IDependencyProvider {

    /**
     * Get all direct BOM resources that the given BOM resource file depends on.
     * 
     * @param file
     * @return
     */
    List<IFile> getDependencies(IFile file);

    /**
     * Get all direct and indirect BOM resources that the given BOM resource
     * file depends on.
     * 
     * @param file
     * @return
     */
    List<IFile> getAllDependencies(IFile file);
}
