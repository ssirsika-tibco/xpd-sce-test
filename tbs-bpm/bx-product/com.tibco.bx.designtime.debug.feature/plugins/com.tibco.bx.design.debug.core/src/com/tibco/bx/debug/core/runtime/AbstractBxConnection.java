package com.tibco.bx.debug.core.runtime;

import java.util.LinkedList;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.runtime.IConnectionListener;

public abstract class AbstractBxConnection implements IConnection {

	protected boolean connected;
	private LinkedList<IConnectionListener> connectionListeners;
	private LinkedList<IRuntimeEventListener> eventListeners;

	protected int port = -1;
	protected String host;
	protected String protocol;
	protected String modelType;
	protected String sessionId;
	protected String endpointURI;
	@Override
	public final void connect() throws CoreException {
		if (!connected) {
			doConnect();
		}
		connected = true;
	}

	@Override
	public final void disconnect() throws CoreException {
		if (connected) {
			connected = false;
			try{
				doDisconnect();
			}catch(CoreException e){
				//throw e;
			}
		}
	}
	
	public void fireConnectionClosed(){
		if (connectionListeners != null) {
			for (IConnectionListener connectionListener : connectionListeners) {
				connectionListener.connectionClosed(this);
			}
		}
	}
	
	@Override
	public final void addConnectionListener(IConnectionListener connectionListener) {
		if (connectionListeners == null) {
			connectionListeners = new LinkedList<IConnectionListener>();
		}
		connectionListeners.add(connectionListener);
	}

	@Override
	public final void removeConnectionListener(
			IConnectionListener connectionListener) {
		if (connectionListeners != null) {
			connectionListeners.remove(connectionListener);
		}
	}
	
	@Override
	public void addEventListener(IRuntimeEventListener eventListener) {
		if (eventListeners == null) {
			eventListeners = new LinkedList<IRuntimeEventListener>();
		}
		eventListeners.add(eventListener);
	}
	
	@Override
	public void removeEventListener(IRuntimeEventListener eventListener) {
		if (eventListeners != null) {
			eventListeners.remove(eventListener);
		}
	}
	
	protected void notifyEvent(Object event) {
		if (eventListeners != null) {
			for (IRuntimeEventListener eventListener : eventListeners) {
				try {
					eventListener.notifyEvent(event);
				} catch (CoreException e) {
					DebugCoreActivator.log(e.getStatus());
				}
			}
		}
	}
	
	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getHost() {
		return host;
	}
	
	@Override
    public String getProtocol() {
        return protocol;
    }
	
	@Override
	public String getEndpointURI(){
		return endpointURI;
	}
	
	@Override
	public String getModelType() {
		return modelType;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}
	
	abstract protected void doConnect() throws CoreException;

	abstract protected void doDisconnect() throws CoreException;

}
