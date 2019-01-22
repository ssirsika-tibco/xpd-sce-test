/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process Data To Physical Work Item Attribute Mapping Content Provider.
 * 
 * @author aprasad
 * @since 30-Oct-2013
 */
public class ProcessDataToWIAttributeMappingContentProvider implements
        IStructuredContentProvider {

    /**
     * 
     */
    public ProcessDataToWIAttributeMappingContentProvider() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
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
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     *            , {@link Process}
     * @return, Array of {@link Mapping} representing Process Data to Physical
     *          Work Item Attribute Mappings for the {@link Process}.
     */
    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof Process) {

            List<Mapping> result = new ArrayList<Mapping>();
            Process process = (Process) inputElement;

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings());

            if (otherElement != null) {
                // Mappings Exist
                ProcessDataWorkItemAttributeMappings processDataWIAttribMappings =
                        (ProcessDataWorkItemAttributeMappings) otherElement;

                // get mappings list for facade files
                EList<DataWorkItemAttributeMapping> dataWIAttribMappings =
                        processDataWIAttribMappings
                                .getDataWorkItemAttributeMapping();

                for (DataWorkItemAttributeMapping dataWIAttribMapping : dataWIAttribMappings) {
                    /*
                     * Create ConceptPath from the source path
                     */
                    ConceptPath sourceConceptPath =
                            ConceptUtil.resolveConceptPath(process,
                                    dataWIAttribMapping.getProcessData());
                    Property attribute = getAttribute(dataWIAttribMapping);
                    // also use its corresponding Display Entry from the Work
                    // List Facade file if exists
                    WorkItemAttributeConceptPath targetConceptPath =
                            new WorkItemAttributeConceptPath(
                                    attribute,
                                    WorkListFacadeMapperUtil
                                            .getDisplayAttributeFromFacade(attribute));

                    result.add(new Mapping(sourceConceptPath,
                            targetConceptPath, dataWIAttribMapping));
                }

                return result.toArray();
            }
        }

        return new Object[0];
    }

    /**
     * Gets Physical Work Item Attribute associated with this mapping.Uses
     * {@link PhysicalWorkItemAttributesProvider} to get the {@link Property}
     * representing the Physical Work Item Attribute.
     * 
     * @param dataAttributeMapping
     *            , Process Data to Physical Attribute Mapping object.
     * @return Property, Physical Attribute for the
     *         {@link DataWorkItemAttributeMapping}.
     */
    private Property getAttribute(
            DataWorkItemAttributeMapping dataAttributeMapping) {
        if (dataAttributeMapping != null) {
            /** Deference the attribute Property from target object in mapping. */

            // get Physical Attributes.
            Collection<Property> attributes =
                    PhysicalWorkItemAttributesProvider.INSTANCE
                            .getWorkItemAttributes();

            for (Property property : attributes) {
                if (property.getName() != null
                        && property.getName()
                                .equals(dataAttributeMapping.getAttribute())) {
                    return property;
                }
            }
        }

        return null;
    }
}
