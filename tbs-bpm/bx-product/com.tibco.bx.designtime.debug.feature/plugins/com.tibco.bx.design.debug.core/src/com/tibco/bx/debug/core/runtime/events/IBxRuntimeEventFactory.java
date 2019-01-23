package com.tibco.bx.debug.core.runtime.events;

public interface IBxRuntimeEventFactory {
	
	BxRuntimeEvent createRuntimeEvent(Object receivedEvent);

}
