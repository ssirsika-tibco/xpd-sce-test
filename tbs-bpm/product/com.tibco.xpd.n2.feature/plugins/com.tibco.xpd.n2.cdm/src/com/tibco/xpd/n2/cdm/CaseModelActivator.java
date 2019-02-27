package com.tibco.xpd.n2.cdm;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CaseModelActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		CaseModelActivator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		CaseModelActivator.context = null;
	}

}
