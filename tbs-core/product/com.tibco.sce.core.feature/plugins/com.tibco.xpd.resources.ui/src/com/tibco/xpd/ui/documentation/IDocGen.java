/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * This interface is used for the generation of documentation
 * for the passed input {@link IResource}
 * 
 * The documentation is generated in the outputPath
 * 
 * @author mtorres
 */
public interface IDocGen {
    /**
     * Generation of the documentation for the given input {@link IResource} resources
     * in the given output path
     * 
     * @param inputs
     * @param outputPath
     * @return List<IDocGenInfo>
     */
    List<IDocGenInfo> generate(List<IResource> inputs, IPath outputPath);
    
}
