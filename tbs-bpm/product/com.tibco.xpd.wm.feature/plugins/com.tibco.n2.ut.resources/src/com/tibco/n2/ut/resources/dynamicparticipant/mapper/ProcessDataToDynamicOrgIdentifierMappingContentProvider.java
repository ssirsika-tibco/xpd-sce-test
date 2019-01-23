/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Content provider for mappings between the Process data and the Dynamic Org
 * Identifiers.
 * 
 * @author kthombar
 * @since 26-Sep-2013
 */
public class ProcessDataToDynamicOrgIdentifierMappingContentProvider implements
        IStructuredContentProvider {

    public ProcessDataToDynamicOrgIdentifierMappingContentProvider() {

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    public void dispose() {

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }

    /**
     * Create mappings between process data and dynamic org identifiers.
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    public Object[] getElements(Object inputElement) {

        if (inputElement instanceof Process) {

            List<Mapping> result = new ArrayList<Mapping>();
            Process process = (Process) inputElement;
            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());
            if (otherElement != null) {
                DynamicOrganizationMappings dynamicOrganizationMappings =
                        (DynamicOrganizationMappings) otherElement;

                EList<DynamicOrganizationMapping> dynamicparticipantMapping =
                        dynamicOrganizationMappings
                                .getDynamicOrganizationMapping();

                for (DynamicOrganizationMapping eachDynamicparticipantMapping : dynamicparticipantMapping) {
                    /*
                     * Create ConceptPath from the source path
                     */
                    ConceptPath sourceConceptPath =
                            ConceptUtil.resolveConceptPath(process,
                                    eachDynamicparticipantMapping
                                            .getSourcePath());

                    DynamicOrgIdentifierRef dynamicOrgIdentifierRef =
                            eachDynamicparticipantMapping
                                    .getDynamicOrgIdentifierRef();
                    /*
                     * Create DynamicOrgIdentiferPath from the target
                     * DynamicOrgIdentifierRef
                     */
                    DynamicOrgIdentiferPath dynOrgIdPath =
                            new DynamicOrgIdentiferPath(dynamicOrgIdentifierRef);

                    result.add(new Mapping(sourceConceptPath, dynOrgIdPath,
                            eachDynamicparticipantMapping));

                }
                return result.toArray();
            }
        }

        return new Object[0];
    }

}
