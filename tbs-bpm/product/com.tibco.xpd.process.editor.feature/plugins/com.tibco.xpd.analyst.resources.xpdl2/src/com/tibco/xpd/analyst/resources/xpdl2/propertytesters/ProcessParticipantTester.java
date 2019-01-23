/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author mtorres
 * 
 * 
 */
public class ProcessParticipantTester extends PropertyTester {

	public ProcessParticipantTester() {
	}

	public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue3) {

        if (receiver instanceof Participant) {
            Participant participant = (Participant) receiver;
            if (participant.eContainer() != null
                    && participant.eContainer() instanceof Process) {
                return true;
            }
        }
        return false;
    }

}
