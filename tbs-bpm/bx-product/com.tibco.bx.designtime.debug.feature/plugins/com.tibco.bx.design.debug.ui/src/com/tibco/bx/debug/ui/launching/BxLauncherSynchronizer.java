package com.tibco.bx.debug.ui.launching;

import org.eclipse.ui.IStartup;

import com.tibco.bx.debug.core.util.LauncherSyncHelper;

public class BxLauncherSynchronizer implements IStartup {

	@Override
	public void earlyStartup() {
		LauncherSyncHelper.initiateProject();
	}

}
