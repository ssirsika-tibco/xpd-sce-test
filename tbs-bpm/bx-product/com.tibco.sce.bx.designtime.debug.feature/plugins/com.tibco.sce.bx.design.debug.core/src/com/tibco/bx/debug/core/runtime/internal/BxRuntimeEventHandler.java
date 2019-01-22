package com.tibco.bx.debug.core.runtime.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.IRuntimeEventListener;
import com.tibco.bx.debug.core.runtime.eventhandlers.ActivityEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.BreakPointEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.EndEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.IRuntimeEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.LinkEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.RegisterEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.ResumeEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.StartEventHandler;
import com.tibco.bx.debug.core.runtime.eventhandlers.SuspendEventHandler;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;
import com.tibco.bx.debug.core.runtime.events.IBxRuntimeEventFactory;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent.EventType;

public class BxRuntimeEventHandler implements IRuntimeEventListener {
	
	private BlockingQueue<Object> eventQueue = new LinkedBlockingQueue<Object>();
	private Thread eventDispatcher;
	
	private final IBxRuntimeEventFactory runtimeEventFactory;

	private Map<EventType, IRuntimeEventHandler> eventHandlerMap;
	
	private StartEventHandler startEventHandler;
	private EndEventHandler endEventHandler;
	private SuspendEventHandler suspendEventHandler;
	private ResumeEventHandler resumeEventHandler;
	private BreakPointEventHandler breakpointEventHandler;
	private LinkEventHandler linkEventHandler;
	private ActivityEventHandler activityEventHandler;
	private RegisterEventHandler registerEventHandler;
	
	public BxRuntimeEventHandler(IBxDebugTarget debugTarget, IBxRuntimeEventFactory runtimeEventFactory) {
		this.runtimeEventFactory = runtimeEventFactory;
		
		eventHandlerMap = new HashMap<EventType, IRuntimeEventHandler>();
		
		startEventHandler = new StartEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ProcessCreation, startEventHandler);
		
		endEventHandler = new EndEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ProcessTermination, endEventHandler);
		
		suspendEventHandler = new SuspendEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ProcessSuspension, suspendEventHandler);
		
		resumeEventHandler = new ResumeEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ProcessResumption, resumeEventHandler);
		
		breakpointEventHandler = new BreakPointEventHandler(debugTarget);
		eventHandlerMap.put(EventType.BreakpointHit, breakpointEventHandler);
		
		linkEventHandler = new LinkEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ProcessLink, linkEventHandler);
		
		activityEventHandler = new ActivityEventHandler(debugTarget);
		eventHandlerMap.put(EventType.ActivityCreation, activityEventHandler);		
		eventHandlerMap.put(EventType.ActivityTermination, activityEventHandler);	
		
		registerEventHandler = new RegisterEventHandler(debugTarget);
		eventHandlerMap.put(EventType.TemplateRegistration, registerEventHandler);
	}

	@Override
	public void notifyEvent(Object event) throws CoreException {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			DebugCoreActivator.log(e);
		}
	}

	protected void dispatchEvent(Object receivedEvent) throws CoreException {
		BxRuntimeEvent runtimeEvent = runtimeEventFactory.createRuntimeEvent(receivedEvent);
		if (runtimeEvent != null) {
			
			IRuntimeEventHandler runtimeEventHandler = eventHandlerMap.get(runtimeEvent.getEventType());
			
			if (runtimeEventHandler != null) {
				runtimeEventHandler.handleRuntimeEvent(runtimeEvent);
			}
		}
	}


	public void start() {
		eventDispatcher = new Thread(new Runnable() {
			public void run() {
			     try {
					while (true) {
						Object event = eventQueue.take();
						try {
							BxRuntimeEventHandler.this.dispatchEvent(event);
						} catch (CoreException e) {
							DebugCoreActivator.log(e.getStatus());
						}
					}
				} catch (InterruptedException e) {
				}
			}
		});
		eventDispatcher.start();
	}
	
	public void stop() {
		if (eventDispatcher != null) {
			eventDispatcher.interrupt();
		}
	}

}
