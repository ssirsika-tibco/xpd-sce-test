package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import com.tibco.xpd.xpdl2.Participant;

/**
 * RefactorReferencedParticipantInfo
 * 
 * Refactoring information about a referenced participant.
 */
public class RefactorReferencedParticipantInfo {
    public Participant participant = null;

    // If not ref'd elsewhere then give user choice to move or duplicate.
    public Boolean referencedElseWhere = false;

    // If referenced ONLY in selected objects then given choice to Move to
    // new process or duplicate in new process.
    public Boolean moveParticipant = false;

}
