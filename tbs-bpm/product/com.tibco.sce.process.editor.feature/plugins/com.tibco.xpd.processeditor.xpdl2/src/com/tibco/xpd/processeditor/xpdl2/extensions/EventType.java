/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

/**
 * Possible values of the event type for event implementations.
 * 
 * @author scrossle
 *
 */
public enum EventType {

	START,
	INTERMEDIATE,
	END,
	START_AND_INTERMEDIATE,
	STARTMETHOD,
	INTERMEDIATEMETHOD,
	ANY;
}
