package com.tibco.bx.bpelExtension.converter;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.bpelExtension.extensions.Signal;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Participant;

public class ConvertSignal implements IActivityConfigurationModelBuilder {
	
	public EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {
		//System.out.println("***in ConvertSignal ***");
		Event event = xpdlActivity.getEvent();
		IntermediateEvent ev = (IntermediateEvent)event;
		String errorCode = ev.getResultError().getErrorCode();
		//todo what is error code versus event name versus variable name
		Signal signal = ExtensionsFactory.eINSTANCE.createSignal();
		signal.setEvent(errorCode);
//		signal.setVariable(text);  //todo there is no variable?
		return signal;
	}

}
