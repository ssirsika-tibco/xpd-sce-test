package com.tibco.bx.debug.api;

public interface IDebuggerEvents {
	//for breakpoint
	String EVENT_ONENTRY = "com.tibco.bx.eventEntry"; //$NON-NLS-1$
	String EVENT_ONEXIT = "com.tibco.bx.eventExit"; //$NON-NLS-1$
	//for life cycle
    String EVENT_COMPLETE = "com.tibco.bx.eventComplete";
    String EVENT_TERMINATE = "com.tibco.bx.eventTerminate";
    String EVENT_RESUME = "com.tibco.bx.eventResume";
    String EVENT_SUSPEND = "com.tibco.bx.eventSuspend";
    String EVENT_START = "com.tibco.bx.eventStart";
    String EVENT_FAULT = "com.tibco.bx.eventFault";
    // for link
    String EVENT_LINK = "com.tibco.bx.linkExecuted";
    // for activity
    String EVENT_ACTIVITY = "com.tibco.bx.eventActivity";
    // for template
    String EVENT_TEMPLATE = "com.tibco.bx.eventTemplate";
}
