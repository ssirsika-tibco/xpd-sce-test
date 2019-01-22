/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process Data Resolver for Process Data-Physical Work Item Attribute mapping.
 * 
 * @author aprasad
 * @since 15-Nov-2013
 */
public class WorkItemAttributeMappingProcessDataResolver implements
        IDataReferenceResolver {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getDataReferences(org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param dataScopeObject
     * @param data
     * @return, Map <ProcessDataWorkItemAttributeMappings
     *          ,ProcessDataReferenceAndContexts> data reference locations and
     *          referencing context
     */
    @Override
    public Map<EObject, ProcessDataReferenceAndContexts> getDataReferences(
            EObject dataScopeObject, ProcessRelevantData data) {

        Map<EObject, ProcessDataReferenceAndContexts> dataReferences = null;

        String dataName = data.getName();
        if (dataScopeObject instanceof Process && dataName != null) {
            Process process = (Process) dataScopeObject;

            ProcessDataWorkItemAttributeMappings procDataPhysicalAttribMappings =
                    (ProcessDataWorkItemAttributeMappings) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings());

            if (procDataPhysicalAttribMappings != null
                    && procDataPhysicalAttribMappings
                            .getDataWorkItemAttributeMapping() != null) {

                // Process each FacadeMapping which contains mappings to
                // attribute aliases of a given facade file

                for (DataWorkItemAttributeMapping dataAliasMapping : procDataPhysicalAttribMappings
                        .getDataWorkItemAttributeMapping()) {

                    if (dataName.equals(dataAliasMapping.getProcessData())) {
                        /*
                         * Found a reference to the field, just need to return
                         * this one reference to say
                         * "process reference data for DataAliasMapping)"
                         */
                        dataReferences =
                                new HashMap<EObject, ProcessDataReferenceAndContexts>();

                        ProcessDataReferenceAndContexts dataRef =
                                new ProcessDataReferenceAndContexts(
                                        data,
                                        new DataReferenceContext(
                                                "DataPhysicalAttributeMapping", //$NON-NLS-1$
                                                Messages.WorkItemAliasMappingProcessDataResolver_DataContextReferenceMessage));

                        dataReferences.put(procDataPhysicalAttribMappings,
                                dataRef);
                        break;// exit this loop when data reference is
                              // found.
                    }

                }
            }

        }
        return dataReferences;
    }

    /**
     * Update {@link DataWorkItemAttributeMapping} for the renamed Process Data.
     * 
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getSwapDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param editingDomain
     * @param dataScopeObject
     *            , owner Process.
     * @param oldName
     *            , Old name of the process data.
     * @param newName
     *            , new name of the process data.
     * @return Command to update the {@link DataWorkItemAttributeMapping} for
     *         the renamed process Data.
     */
    @Override
    public Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, EObject dataScopeObject,
            String oldName, String newName) {
        if (dataScopeObject instanceof Process) {

            ProcessDataWorkItemAttributeMappings procDataWLFMappings =
                    (ProcessDataWorkItemAttributeMappings) Xpdl2ModelUtil
                            .getOtherElement((Process) dataScopeObject,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings());

            if (procDataWLFMappings != null
                    && procDataWLFMappings.getDataWorkItemAttributeMapping() != null) {

                CompoundCommand cmd = new CompoundCommand();

                EList<DataWorkItemAttributeMapping> dataWorkItemAttributeMapping =
                        procDataWLFMappings.getDataWorkItemAttributeMapping();

                if (dataWorkItemAttributeMapping != null
                        && !dataWorkItemAttributeMapping.isEmpty()) {

                    for (DataWorkItemAttributeMapping dataAliasMapping : dataWorkItemAttributeMapping) {

                        if (oldName.equals(dataAliasMapping.getProcessData())) {
                            /*
                             * Update the name of the process data
                             */
                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            dataAliasMapping,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDataWorkItemAttributeMapping_ProcessData(),
                                            newName));
                        }

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
     * Does not do anything, leaves broken mappings and does not delete them
     * when a process data is deleted.
     * 
     * @see com.tibco.xpd.xpdl2.resolvers.IDataReferenceResolver#getDeleteDataReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param dataScopeObject
     * @param data
     * @return null.
     */
    @Override
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            EObject dataScopeObject, ProcessRelevantData data) {
        // DO NOT Delete mappings on delete of process data. Show mappings as
        // broken.
        return null;
    }

}
