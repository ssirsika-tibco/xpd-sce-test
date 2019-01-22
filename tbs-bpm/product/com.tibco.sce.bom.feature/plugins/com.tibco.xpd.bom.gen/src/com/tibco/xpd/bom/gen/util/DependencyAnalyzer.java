/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.util;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.bom.gen.api.IDependencyProvider;

/**
 * Dependency analyzer that will build a dependency list from a given BOM file.
 * 
 * @author njpatel
 * 
 */
public class DependencyAnalyzer extends com.tibco.xpd.bom.resources.utils.DependencyAnalyzer implements IDependencyProvider {
	public DependencyAnalyzer(Collection<IFile> files) {
		super(files);		
	}    
}