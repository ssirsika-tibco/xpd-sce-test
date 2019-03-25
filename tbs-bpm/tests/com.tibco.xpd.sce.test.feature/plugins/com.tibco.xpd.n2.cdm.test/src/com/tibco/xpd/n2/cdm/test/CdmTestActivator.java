/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * N2 CDM test plugin activator.
 *
 * @author jarciuch
 * @since 7 Mar 2019
 */
public class CdmTestActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
    public void start(BundleContext bundleContext) throws Exception {
		CdmTestActivator.context = bundleContext;
	}

	@Override
    public void stop(BundleContext bundleContext) throws Exception {
		CdmTestActivator.context = null;
	}

}
