/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author bharge
 * 
 */
public interface IFieldResolverExtension2 {
    /**
     * Given a set of data fields / formal parameters return the set of those
     * data fields / formal parameters that are referenced by parts of
     * ProcessRelevantData model that the extension knows about.
     * 
     * @param ProcessRelevantData
     * @return Set of data fields / formal parameters from given set that are
     *         referenced.
     */
    Set<ProcessRelevantData> getProcessRelevantDataReferences(
            ProcessRelevantData processRelevantData,
            Set<ProcessRelevantData> dataSet);

    /**
     * Given a set of data fields / formal parameters return the set of those
     * participants that are referenced by parts of ProcessRelevantData model
     * that the extension knows about.
     * 
     * @param ProcessRelevantData
     * @return Set of data fields / formal parameters from given set that are
     *         referenced.
     */
    Set<Participant> getParticipantDataReferences(
            ProcessRelevantData processRelevantData,
            Set<Participant> participantSet);

    /**
     * Given a map of old data field / formal Parameter / participant names to
     * new names; return the command that will swap any references by name to
     * those fields made in the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param processRelevantData
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapProcessRelevantDataNameReferencesCommand(
            EditingDomain editingDomain,
            ProcessRelevantData processRelevantData, Map<String, String> nameMap);

    /**
     * Given a map of old data field / formal Parameter / participant names to
     * new names; return the command that will swap any references by id to
     * those fields made in the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param processRelevantData
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapProcessRelevantDataIdReferencesCommand(
            EditingDomain editingDomain,
            ProcessRelevantData processRelevantData, Map<String, String> nameMap);

}
