package com.tibco.xpd.bpm.om;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.newparticipant.NewBPMParticipantWizard;
import com.tibco.xpd.processeditor.xpdl2.properties.ParticipantNameSection;
import com.tibco.xpd.xpdl2.Participant;

public class BPMParticipantNameSection extends ParticipantNameSection {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        Object item = getBaseSelectObject(toTest);
        if (!(item instanceof Participant)) {
            return false;
        }
        Participant participant = (Participant) item;

        if (XpdlFileContentPropertyTester.isXpdlFileContent(participant)
                || NewBPMParticipantWizard.NEW_BPM_PARTICIPANT_ID
                        .equals(participant.getId())) {
            return true;
        }
        return false;
    }
}
