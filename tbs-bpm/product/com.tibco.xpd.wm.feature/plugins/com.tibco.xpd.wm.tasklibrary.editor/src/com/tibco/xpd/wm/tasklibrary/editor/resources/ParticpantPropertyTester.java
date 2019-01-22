/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;

/**
 * Plugin xml roperty Tester for participant entries.
 * 
 * @author aallway
 * @since 3.2
 */
public class ParticpantPropertyTester extends PropertyTester {

    private static final String IS_SYSTEM_PARTICIPANT = "isSystemParticipant"; //$NON-NLS-1$

    
    
    public ParticpantPropertyTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof Participant) {
            Participant partic = (Participant) receiver;

            if (IS_SYSTEM_PARTICIPANT.equals(property)) {
                if (ParticipantType.SYSTEM_LITERAL.equals(partic
                        .getParticipantType().getType())) {
                    return true;
                }
            }

        }
        return false;
    }
}
