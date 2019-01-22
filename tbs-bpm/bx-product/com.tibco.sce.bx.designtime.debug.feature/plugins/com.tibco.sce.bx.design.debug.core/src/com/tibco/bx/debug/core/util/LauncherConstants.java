package com.tibco.bx.debug.core.util;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public interface LauncherConstants {

	public static final String BX_LAUNCH_PROJECT_NAME = ".com.tibco.bx.launch"; //$NON-NLS-1$

	public static final String BPM_RAD_RUNTIME_FILENAME_DESCRIPTOR = "BPM-RAD-Runtime.launch"; //$NON-NLS-1$
	public static final IPath BPM_RAD_RUNTIME_PATH = new Path(BPM_RAD_RUNTIME_FILENAME_DESCRIPTOR);

	public static final String REMOTE_BPM_RAD_RUNTIME_FILE_URL = "platform:/plugin/com.tibco.bx.design.debug.core/launch/BPM-RAD-Runtime.launch"; //$NON-NLS-1$

	public static final String BPM_RAD_RUNTIME_LAUNCH_EXT = "launch"; //$NON-NLS-1$
	
	public static final String BPM_RAD_LAUNCH_TYPE = "com.tibco.bx.debug.launchType"; //$NON-NLS-1$
	
}
