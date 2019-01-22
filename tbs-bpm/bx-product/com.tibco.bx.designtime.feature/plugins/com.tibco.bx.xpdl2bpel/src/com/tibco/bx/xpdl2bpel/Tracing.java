package com.tibco.bx.xpdl2bpel;

import java.text.MessageFormat;
import java.util.Date;

import org.eclipse.core.runtime.Platform;

public class Tracing {

	private static final String DEBUG_TAG = ConverterActivator.PLUGIN_ID + "/debug"; //$NON-NLS-1$
	public static final boolean ENABLED = "true".equals(Platform.getDebugOption(DEBUG_TAG)); //$NON-NLS-1$


	public static void trace(String msg) {
		String str = MessageFormat.format(
				"[{0,date,yyyy-MM-dd} {0,time,HH:mm:ss}] {1}", //$NON-NLS-1$
				new Object[] { new Date(), msg });
		System.out.println(str);
	}
}
