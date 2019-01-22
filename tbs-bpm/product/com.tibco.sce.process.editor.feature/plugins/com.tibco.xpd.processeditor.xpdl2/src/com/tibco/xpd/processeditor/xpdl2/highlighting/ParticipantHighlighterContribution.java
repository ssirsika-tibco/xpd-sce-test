/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.highlighting;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to process diagram referencing object highlighter for
 * participants.
 * 
 * @author aallway
 * @since 20 Jan 2011
 */
public class ParticipantHighlighterContribution extends
        AbstractReferenceHighlighterContribution {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getInterestingReferencedObjectClass()
     * 
     * @return
     */
    @Override
    public Class<? extends Object> getInterestingReferencedObjectClass() {
        return Participant.class;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#isInScope(java.lang.Object,
     *      com.tibco.xpd.xpdl2.Process)
     * 
     * @param interestingReferencedObject
     * @param process
     * @return
     */
    @Override
    public boolean isInScope(Object interestingReferencedObject, Process process) {
        Participant participant = (Participant) interestingReferencedObject;

        /*
         * We are only interested in objects that are visible to our process.
         */
        boolean inScope = false;

        /* If has process ancestor then must be our process. */
        EObject ancProc =
                Xpdl2ModelUtil.getAncestor(participant, Process.class);
        if (ancProc != null) {
            /* If it's under a process it has be our process! */
            if (ancProc.equals(process)) {
                inScope = true;
            }
        } else {
            /* If it's in a process, it must be in same package! */
            EObject ancPkg =
                    Xpdl2ModelUtil.getAncestor(participant, Package.class);
            if (ancPkg != null) {
                if (ancPkg.equals(process.getPackage())) {
                    inScope = true;
                }
            }
        }

        return inScope;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getReferencingDiagramModelObjects(java.lang.Object,
     *      com.tibco.xpd.xpdl2.Process)
     * 
     * @param referencedObject
     * @param diagramProcess
     * @return
     */
    @Override
    public Set<? extends EObject> getReferencingDiagramModelObjects(
            Object interestingReferencedObject, Process diagramProcess) {
        Participant participant = (Participant) interestingReferencedObject;

        return Xpdl2ParticipantReferenceResolver
                .getReferencingObjects(participant);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getInterestingObjectLabel(java.lang.Object)
     * 
     * @param interestingReferencedObject
     * @return
     */
    @Override
    public String getInterestingObjectLabel(Object interestingReferencedObject) {
        Participant participant = (Participant) interestingReferencedObject;

        return String
                .format(Messages.ParticipantHighlighterContribution_ParticipantReferenceLeader_tooltip,
                        Xpdl2ModelUtil.getDisplayNameOrName(participant));
    }

}
