package com.tibco.bx.debug.core.runtime;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

public interface IConnection extends IAdaptable {

	public static String SESSION_ZERO = "0";
	
	void connect() throws CoreException;
	
	void disconnect() throws CoreException ;
	
	boolean isValid() throws CoreException;
	
	void addConnectionListener(IConnectionListener connectionListener);
	
	void removeConnectionListener(IConnectionListener connectionListener);

	void addEventListener(IRuntimeEventListener eventListener);
	
	void removeEventListener(IRuntimeEventListener eventListener);
    
    String getProtocol();
    String getHost();
    int getPort();
    String getModelType();
    String getEndpointURI();
    
    String getSessionId();
    
    public String getUsername();
    
    public String getPassword();
    
    public String getSoapVersion();
    
}
