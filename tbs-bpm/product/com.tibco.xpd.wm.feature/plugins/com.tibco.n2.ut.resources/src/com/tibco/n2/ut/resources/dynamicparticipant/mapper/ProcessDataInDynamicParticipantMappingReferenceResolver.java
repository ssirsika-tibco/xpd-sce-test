/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Checks for Process data used in Dynamic Participant mappings and updates the
 * references in case any changes(name changed/deletion) are been made to the
 * Process data.
 * 
 * @author kthombar
 * @since 13 Oct 2013
 */
public class ProcessDataInDynamicParticipantMappingReferenceResolver implements
        IDataReferenceResolver {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getDataReferences(org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param dataScopeObject
     * @param data
     * @return
     */
    public Map<EObject, ProcessDataReferenceAndContexts> getDataReferences(
            EObject dataScopeObject, ProcessRelevantData data) {

        Map<EObject, ProcessDataReferenceAndContexts> dataReferences = null;

        /*
         * Only interested in data changes in scope of whoel process as that is
         * all that can be referenced in dynamic org identifier mappings.
         * 
         * In which case we'll look for the data name in our mapping source's
         */
        if (dataScopeObject instanceof Process && data.getName() != null) {
            Process process = (Process) dataScopeObject;

            DynamicOrganizationMappings dynOrgMappings =
                    (DynamicOrganizationMappings) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());

            if (dynOrgMappings != null
                    && dynOrgMappings.getDynamicOrganizationMapping() != null) {

                String dataName = data.getName();

                for (DynamicOrganizationMapping dynOrgMapping : dynOrgMappings
                        .getDynamicOrganizationMapping()) {

                    if (dataName.equals(dynOrgMapping.getSourcePath())) {
                        /*
                         * Found a reference to the field, just need to return
                         * this one reference to say
                         * "process referece data for dynamic org mapping)"
                         */
                        dataReferences =
                                new HashMap<EObject, ProcessDataReferenceAndContexts>();

                        ProcessDataReferenceAndContexts dataRef =
                                new ProcessDataReferenceAndContexts(
                                        data,
                                        new DataReferenceContext(
                                                "DynOrgIdentifierMapping", //$NON-NLS-1$
                                                Messages.ProcessDataInDynamicParticipantMappingReferenceResolver_DataRefContext_label));

                        dataReferences.put(dynOrgMappings, dataRef);
                        break;
                    }
                }

            }

        }

        return dataReferences;
    }

    /**
     * Checks if the Process data used in mapping is deleted, if yes then delete
     * all the mappings from that process data.
     * 
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getDeleteDataReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param dataScopeObject
     * @param data
     * @return
     */
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            EObject dataScopeObject, ProcessRelevantData data) {

        if (dataScopeObject instanceof Process) {

            DynamicOrganizationMappings dynOrgMappings =
                    (DynamicOrganizationMappings) Xpdl2ModelUtil
                            .getOtherElement((Process) dataScopeObject,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());

            if (dynOrgMappings != null
                    && dynOrgMappings.getDynamicOrganizationMapping() != null) {

                CompoundCommand cmd = new CompoundCommand();

                for (DynamicOrganizationMapping dynOrgMapping : dynOrgMappings
                        .getDynamicOrganizationMapping()) {

                    String value = dynOrgMapping.getSourcePath();

                    if (value != null && value.equals(data.getName())) {
                        /*
                         * If the Process data which is referenced is deleted in
                         * that case delete the mappings from that process data.
                         */
                        cmd.append(LateExecuteRemoveCommand
                                .create(editingDomain, dynOrgMapping));
                    }
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }
        }

        return null;
    }

    /**
     * Checks if the name of the process data used in mappings is changed, if
     * yes then update the model file with new names so that references do not
     * break.
     * 
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getSwapDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param editingDomain
     * @param dataScopeObject
     * @param oldName
     * @param newName
     * @return
     */
    public Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, EObject dataScopeObject,
            String oldName, String newName) {
        if (dataScopeObject instanceof Process) {

            DynamicOrganizationMappings dynOrgMappings =
                    (DynamicOrganizationMappings) Xpdl2ModelUtil
                            .getOtherElement((Process) dataScopeObject,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());

            if (dynOrgMappings != null
                    && dynOrgMappings.getDynamicOrganizationMapping() != null) {

                CompoundCommand cmd = new CompoundCommand();

                for (DynamicOrganizationMapping dynOrgMapping : dynOrgMappings
                        .getDynamicOrganizationMapping()) {

                    String value = dynOrgMapping.getSourcePath();

                    if (value != null && value.equals(oldName)) {

                        /*
                         * Update the name of the source path
                         */
                        cmd.append(SetCommand
                                .create(editingDomain,
                                        dynOrgMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDynamicOrganizationMapping_SourcePath(),
                                        newName));

                    }
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }
        }

        return null;
    }

}
