package com.tibco.n2.pfe.services.pageflow;

import java.util.Map;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.http.Messages;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.n2.pfe.services.pageflowType.PageResponse;

public class PageflowTransport {
	
	private transient ListenerList listeners = null;
	private IConnection connection;

	public PageflowTransport(IConnection connection) {
		this.connection = connection;
		listeners = new ListenerList();
	}

	public SendPageflowMessageJob send(ProcessTemplate processTemplate, Map<String, String> parametersMap, ILauncherEventHandler handler, PageFlowManagement pfManager) {
		SendPageflowMessageJob sender = new SendPageflowMessageJob(Messages.getString("HTTPProcessListing.handlingPageflow_message"), processTemplate, parametersMap, handler, pfManager, getConnection()); //$NON-NLS-1$
		runSendJob(sender);
		return sender;
	}

	public void runSendJob(SendPageflowMessageJob sender) {
		sender.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				PageResponse resp = ((SendPageflowMessageJob) event.getJob()).getResponse();
			}

		});
		sender.schedule();
	}
	
	 public void addSelectionChangedListener(Object listener) {
 	    listeners.add(listener);
	 }

	public void removeSoapTransportListern(Object listener){
		listeners.remove(listener);	
	}
	
	public IConnection getConnection() {
		return connection;
	}

}
