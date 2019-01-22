/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;

/**
 * Content Provider for right hand side WorkListFacade Mapper Viewer, which
 * provides data for {@link Property} representing the Physical Work Item
 * Attribute. The content is provided as list of
 * {@link WorkItemAttributeConceptPath} representing/wrapping the Physical Work
 * Item Attribute and its Display Attribute {@link WorkItemAttribute} from Work
 * List Facade file.
 * 
 * @author aprasad
 * @since 30-Oct-2013
 */
public class WorkListFacadeMapperContentProvider implements
        ITreeContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * 
     */
    public WorkListFacadeMapperContentProvider() {
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
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return Array of WorkItemAttributeConceptPath representing the Physical
     *         Work Item Attributes.
     */
    @Override
    public Object[] getElements(Object inputElement) {

        if (inputElement instanceof com.tibco.xpd.xpdl2.Process) {

            List<WorkItemAttributeConceptPath> elements =
                    new ArrayList<WorkItemAttributeConceptPath>();

            // get WRM Work Item Attributes Provider
            PhysicalWorkItemAttributesProvider provider =
                    PhysicalWorkItemAttributesProvider.INSTANCE;

            // for each Attribute create WorkItemAttributeConceptPath to return
            for (Property property : provider.getWorkItemAttributes()) {
                elements.add(new WorkItemAttributeConceptPath(property,
                        WorkListFacadeMapperUtil
                                .getDisplayAttributeFromFacade(property)));
            }
            Collections.sort(elements,
                    new WorkItemAttributeComparatorForMapper());
            return elements.toArray();
        }

        // If no contents are found , return defined No Data To map
        // element.

        return new String[] { Messages.WorkItemAttributeAliasContentProvider_No_Facade_To_Map_Message };
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return Empty array, WorkListFacade Mapper View is single level,
     *         displaying only Physical Attributes.
     */
    @Override
    public Object[] getChildren(Object parentElement) {

        return new Object[0];
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return null, WorkListFacade Mapper View is single level, displaying only
     *         Physical Attributes.
     */
    @Override
    public Object getParent(Object element) {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return false, WorkListFacade Mapper View is single level, displaying
     *         only Physical Attributes.
     */
    @Override
    public boolean hasChildren(Object element) {
        return false;
    }

    class WorkItemAttributeComparatorForMapper implements
            Comparator<WorkItemAttributeConceptPath> {
        @Override
        public int compare(WorkItemAttributeConceptPath consPath1,
                WorkItemAttributeConceptPath consPath2) {

            if (consPath1.getDisplayAttribute() == null) {
                return consPath2.getDisplayAttribute() == null ? 0 : 1;
            } else if (consPath2.getDisplayAttribute() == null) {
                return -1;
            } else {
                String displayLabel1 =
                        consPath1.getDisplayAttribute().getDisplayLabel();
                if (displayLabel1 != null) {
                    return displayLabel1.compareTo(consPath2
                            .getDisplayAttribute().getDisplayLabel());
                }
                return 0;
            }

        }
    }
}
