package com.tibco.xpd.n2.cdm;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CaseDataModelActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		CaseDataModelActivator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		CaseDataModelActivator.context = null;
	}

}
