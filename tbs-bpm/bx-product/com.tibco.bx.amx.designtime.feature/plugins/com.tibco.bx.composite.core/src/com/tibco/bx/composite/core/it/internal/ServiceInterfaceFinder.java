package com.tibco.bx.composite.core.it.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.RepeatUntil;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.While;
import org.eclipse.core.resources.IFile;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.w3c.dom.Element;

import com.tibco.bx.bpelExtension.extensions.OnReceiveEvent;

public class ServiceInterfaceFinder {

	private final ComponentInterfacesModel interfacesModel;
	private boolean needsRecordXpdlId = false;

	public ServiceInterfaceFinder(ComponentInterfacesModel interfacesModel,boolean hasXpdlId) {
		this.interfacesModel = interfacesModel;
		this.needsRecordXpdlId  = hasXpdlId;
	}

	/**
	 * used to visit all elements of bpel. may be "To" and "From" need more
	 * discussions.
	 * 
	 * according to specification,a partner link,it is considered a service,if:
	 * 
	 * 1.<receive> activity 2.<onMessage> element of a <pick> activity
	 * 3.<onEvent> element of an event handler 4.a partner link who can send the
	 * first message
	 * 
	 * else it is considered as a reference.
	 * 
	 * condition 4 is ignored here.
	 * 
	 * @param bpelFile
	 * @param activity
	 */
	public void findInterfaces(IFile bpelFile, ExtensibleElement activity) {
		if (activity instanceof Process) {
			/*
			 * process <onEvent> elements of event handler
			 */
			Process process = (Process) activity;
			searchInEventHandlers(bpelFile, process, process.getEventHandlers());
			/*
			 * visit child of process
			 */
			findInterfaces(bpelFile, process.getActivity());
		} else if (activity instanceof Receive) {
			/*
			 * process <receive> activity
			 */
			String xpdlId = null;
			if(needsRecordXpdlId){
				xpdlId = getInvokeXpdlId(activity.getElement());
			}
			interfacesModel.addServiceForReceive(bpelFile, (Receive) activity,xpdlId);
		} else if (activity instanceof Pick) {
			/*
			 * process <onMessage> elements of <pick> activity
			 */
			Pick pick = (Pick) activity;
			Iterator<OnMessage> iterator = pick.getMessages().iterator();
			while (iterator.hasNext()) {
				OnMessage onMessage = iterator.next();
				String xpdlId = null;
				if(needsRecordXpdlId){
					xpdlId = getInvokeXpdlId(onMessage.getActivity().getElement());
				}
				interfacesModel.addServiceForOnMessage(bpelFile, onMessage,xpdlId);
//				findInterfaces(bpelFile, onMessage.getActivity());
			}
		} else if (activity instanceof Flow) {
			/*
			 * if <flow> activity,then visit its children
			 */
			Iterator<Activity> iterator = ((Flow) activity).getActivities()
					.iterator();
			while (iterator.hasNext()) {
				Activity ac = iterator.next();
				findInterfaces(bpelFile, ac);
			}
		} else if (activity instanceof Scope) {
			searchInEventHandlers(bpelFile, activity, ((Scope)activity).getEventHandlers());
			/*
			 * if <scope> activity,visit its child
			 */
			Activity sc = ((Scope) activity).getActivity();
			findInterfaces(bpelFile, sc);
		} else if (activity instanceof Sequence) {
			/*
			 * if <sequence> activity,then visit its children
			 */
			Iterator<Activity> iterator = ((Sequence) activity).getActivities()
					.iterator();
			while (iterator.hasNext()) {
				Activity ac = iterator.next();
				findInterfaces(bpelFile, ac);
			}
		} else if (activity instanceof ForEach) {
			findInterfaces(bpelFile, ((ForEach) activity).getActivity());
		} else if (activity instanceof While) {
			findInterfaces(bpelFile, ((While) activity).getActivity());
		} else

		/*
		 * the following activity is considered as a reference
		 */
		if (activity instanceof From) {
		} else if (activity instanceof To) {
		} else if (activity instanceof Invoke) {
			String xpdlId = null;
			if(needsRecordXpdlId){
				xpdlId = getInvokeXpdlId(activity.getElement());
			}
			interfacesModel.addReference(bpelFile, (Invoke) activity,xpdlId);
		} else if (activity instanceof Reply) {
		} else if (activity instanceof ExtensionActivity) {
		} else if (activity instanceof RepeatUntil){
			findInterfaces(bpelFile, ((RepeatUntil)activity).getActivity());
		} 
//		else if (activity instanceof Then){
//			findInterfaces(bpelFile, ((Then)activity).getActivity());
//		} else if (activity instanceof TerminationHandler){
//			findInterfaces(bpelFile, ((TerminationHandler)activity).getActivity());
//		}
		return;
	}

	private void searchInEventHandlers(IFile bpelFile,
			ExtensibleElement activity, EventHandler eventHandlers) {
		if (eventHandlers == null) {
			return;
		}

		Iterator<OnEvent> iterator = eventHandlers.getEvents().iterator();
		while (iterator.hasNext()) {
			OnEvent onEvent = iterator.next();
			String xpdlId = null;
			if(needsRecordXpdlId){
				xpdlId = getInvokeXpdlId(onEvent.getElement());
			}
			interfacesModel.addServiceForOnEvent(bpelFile, onEvent,xpdlId);

			findInterfaces(bpelFile, onEvent.getActivity());
		}
		
		List<ExtensibilityElement> extensibilityElements = eventHandlers.getExtensibilityElements();
		if (extensibilityElements != null) {
			for (ExtensibilityElement ee : extensibilityElements) {
				if (ee instanceof OnReceiveEvent) {
					OnReceiveEvent onReceive = (OnReceiveEvent) ee;
					findInterfaces(bpelFile, onReceive.getActivity());
				}
			}
		}
	}
	
	private static final String TIBEX_NS = "http://www.tibco.com/bpel/2007/extensions";
	private static final String XPDLID_LN = "xpdlId";
	
	private String getInvokeXpdlId(Element element) {
		String xpdlId = element.getAttributeNS(TIBEX_NS, XPDLID_LN);
		if(xpdlId==null||xpdlId.length() == 0){
			return getInvokeXpdlId((Element) element.getParentNode());
		}
		return xpdlId;
	}
}
