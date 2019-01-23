package com.tibco.bx.debug.http.runtime;



public interface HTTPNotificationBroadcaster {

	 public void addNotificationListener(HTTPNotificationListener listener);
	 
	 public void removeNotificationListener(HTTPNotificationListener listener);
}
