package com.tibco.bx.emulation.core;

public interface IEmulationConstants {

	/**
	 * Constants for emulationData add/remove/change updates
	 */
	public final static int ADDED = 0;
	public final static int REMOVED = 1;
	public final static int CHANGED = 2;
	public final static int SAVED = 4;
	
	public static String LINE_SEPERATOR = "\r\n"; //System.getProperty("line.separator"); //$NON-NLS-1$
	public static String JSCRIPT_LANGUAGE = "urn:tibco:wsbpel:2.0:sublang:javascript";//$NON-NLS-1$
}
