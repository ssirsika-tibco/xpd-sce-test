/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author bharge
 * 
 */
public class Xpdl2ParticipantReplacer {
    Map<String, String> idOrNameMap = null;

    boolean mapIsIdMap = true;

    /**
     * Create a field replacer for the given map of data fields
     * 
     * @param idOrNameMap
     *            key = old field id OR name, value = new field id OR name
     * @param mapIsIdMap
     *            If true the 2 strings in map are old and new id. If false then
     *            they are old and new name.
     * 
     */
    public Xpdl2ParticipantReplacer(Map<String, String> idOrNameMap,
            boolean mapIsIdMap) {
        this.idOrNameMap = idOrNameMap;
        this.mapIsIdMap = mapIsIdMap;
    }

    /**
     * Return EMF command to replace any references to participant in the given
     * process relevant data
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param processRelevantData
     * @return EMF command to replace references or null if nothing to do.
     */
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain, ProcessRelevantData processRelevantData) {
        CompoundCommand resultCmd = null;
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;
            if (resolver instanceof IFieldResolverExtension2) {
                IFieldResolverExtension2 resolver2 =
                        (IFieldResolverExtension2) resolver;
                if (mapIsIdMap) {
                    extCmd =
                            resolver2
                                    .getSwapProcessRelevantDataIdReferencesCommand(editingDomain,
                                            processRelevantData,
                                            idOrNameMap);
                } else {
                    extCmd =
                            resolver2
                                    .getSwapProcessRelevantDataNameReferencesCommand(editingDomain,
                                            processRelevantData,
                                            idOrNameMap);
                }
                if (extCmd != null) {
                    if (resultCmd == null) {
                        resultCmd = new CompoundCommand();
                    }
                    resultCmd.append(extCmd);
                }
            }
        }

        return resultCmd;

    }

}
