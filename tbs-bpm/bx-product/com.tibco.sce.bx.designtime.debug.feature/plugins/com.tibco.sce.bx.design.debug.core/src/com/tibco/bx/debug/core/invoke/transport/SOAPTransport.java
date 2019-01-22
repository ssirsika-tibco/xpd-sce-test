package com.tibco.bx.debug.core.invoke.transport;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class SOAPTransport  {

	static final String PROP_READ_ONLY = "prop_read_only"; //$NON-NLS-1$
	static final String PROP_RAW_BYTES = "prop_raw_bytes"; //$NON-NLS-1$
	
	static final MutexRule mutexRule = new MutexRule();
	
	private transient ListenerList listeners = null;
	
    	public SOAPTransport() {
    	    super();
    	    listeners = new ListenerList();
    	}

	public SendSOAPMessageJob send(String url , String requestSoapMessage , String operationName ) {
	    fireBeforeLaunchEvent(requestSoapMessage);
	    SendSOAPMessageJob sender = new SendSOAPMessageJob(Messages.getString("SOAPTransport.jobMessage"), url , requestSoapMessage , operationName); //$NON-NLS-1$
	    //sender.setRule(mutexRule);
	    runSendJob(sender );
	    return sender;
	}
	
	public void runSendJob(SendSOAPMessageJob sender) {
		
		sender.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				try {
					fireResponseBackEvent(((SendSOAPMessageJob)event.getJob()).getResponse());
				} catch (CoreException e) {
					DebugCoreActivator.log(e.getStatus());
				}
			}
			
		});
		sender.schedule();
	}
	
	public void send(String url,  ISOAPMessage message  ){
	    
	        fireBeforeLaunchEvent(message.toXML());
			SendSOAPMessageJob sender = new SendSOAPMessageJob("Send SOAP Message", url ,message); //$NON-NLS-1$
			runSendJob(sender );
		}


	private void fireBeforeLaunchEvent(String request) {
	    for(int i = 0 ; i < listeners.getListeners().length; i++) {
            if(listeners.getListeners()[i] instanceof ILauncherEventHandler) {
               ((ILauncherEventHandler)listeners.getListeners()[i]).beforeLauncher(request);
            }
        }
	}

	private void fireResponseBackEvent(String response){
	    for(int i = 0 ; i < listeners.getListeners().length; i++) {
	        if(listeners.getListeners()[i] instanceof ILauncherEventHandler) {
	          ((ILauncherEventHandler)listeners.getListeners()[i]).afterLauncher(response);
	        }
	    }
	}	
	
	 public void addSelectionChangedListener(Object listener) {
 	    listeners.add(listener);
	 }

	public void removeSoapTransportListern(Object listener){
		listeners.remove(listener);	
	}

		
}

