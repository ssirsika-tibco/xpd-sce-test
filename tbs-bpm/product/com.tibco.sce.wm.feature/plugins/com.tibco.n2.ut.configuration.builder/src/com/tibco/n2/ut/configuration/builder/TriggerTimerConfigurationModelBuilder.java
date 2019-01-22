/*******************************************************************************
 * Copyright 2017 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.n2.ut.configuration.builder;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.n2.ut.model.UserTaskDynamicOrgAttributesType;
import com.tibco.n2.ut.model.UsertaskFactory;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;

/**
 * Contribution class to create the dynamic org model attributes to attach to the BPEL onAlarm.
 * 
 * @author nwilson
 */
public class TriggerTimerConfigurationModelBuilder implements IActivityConfigurationModelBuilder {

	/**
	 * @see com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder#transformConfigModel(com.tibco.xpd.xpdl2.Activity, java.util.Map)
	 */
	@Override
	public EObject transformConfigModel(Activity activity, Map<String, Participant> participantMap)
			throws ConversionException {
		UsertaskFactory factory = UsertaskFactory.eINSTANCE;
		Performers performersList = activity.getPerformers();
		if (performersList != null) {
			List<Performer> performers = performersList.getPerformers();
			for (Performer performer : performers) {
				// Get this participant from the process based on the performer
				// value
				Participant participant = activity.getProcess().getParticipant(performer.getValue());

				if (participant != null) {
					ExternalReference externalReference = participant.getExternalReference();

					if (externalReference != null) {
						// Get the Model Element from the Organisational Model
						ModelElement omModelElement = UserActivityParticipantUtils.getOMModelElement(externalReference);
						if (omModelElement != null) {
							UserTaskDynamicOrgAttributesType attrs = UserActivityParticipantUtils
									.getDynamicOrgAttrs(factory, activity.getProcess(), participant, omModelElement);
							return attrs;
						}
					}
				}
			}
		}
		return null;
	}

}
