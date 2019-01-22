/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Factory for the PackageScopeEnumCache.
 * 
 * <p>
 * <i>Created: 26 Mar 2008</i>
 * </p>
 * 
 * @author aprasad
 * 
 */
public class PackageScopeEnumCacheToolFactory implements IPreProcessorFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
		IPreProcessor processor = null;
        if (input instanceof EObject) {
            Package pckg = Xpdl2ModelUtil.getPackage((EObject) input);
            if (pckg != null) {
                processor = new PackageScopeEnumCache(pckg);
			}
		}
		return processor;
	}

	public Class<? extends IPreProcessor> getToolClass() {
        return PackageScopeEnumCache.class;
	}

}
