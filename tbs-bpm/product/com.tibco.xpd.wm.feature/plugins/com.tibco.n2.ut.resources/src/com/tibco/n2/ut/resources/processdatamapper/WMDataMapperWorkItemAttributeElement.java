/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.n2.ut.resources.processdatamapper;

import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.datamapper.staticcontent.AbstractStaticContentDataMapperElement;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeLabelProvider;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Specialisation of {@link AbstractStaticContentDataMapperElement} for elements
 * representing Work Manager Data Mapper Script
 * "Work Item -> Work Item Attributes -> Attributes"
 * 
 * @author aallway
 * @since 15 May 2015
 */
public class WMDataMapperWorkItemAttributeElement extends
        AbstractStaticContentDataMapperElement {

    private Property workItemAttrProperty;

    private WorkListFacadeLabelProvider workListFacadeLabelProvider;

    /**
     * @param workItemAttrProperty
     * @param parent
     */
    public WMDataMapperWorkItemAttributeElement(Property workItemAttrProperty,
            ConceptPath parent) {
        super(getAttributePathModelName(workItemAttrProperty),
                "WorkManagerFactory.getWorkItem().workItemAttributes." //$NON-NLS-1$
                        + workItemAttrProperty.getName(),
                getDataType(workItemAttrProperty), false, parent);

        this.workItemAttrProperty = workItemAttrProperty;
    }

    /**
     * @param workItemAttrProperty
     * 
     * @return The string to use for the path model for this work item attribute
     */
    private static String getAttributePathModelName(
            Property workItemAttrProperty) {
        return "WorkItem_Attributes_" + workItemAttrProperty.getName() + "$"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @param property
     * @return The proces data equivalent {@link BasicTypeType} of the property
     */
    private static BasicTypeType getDataType(Property property) {
        BasicType dataType =
                BasicTypeConverterFactory.INSTANCE.getBasicType(property
                        .getType());

        if (dataType != null) {
            return dataType.getType();
        }
        return null;
    }

    /**
     * @return the workItemAttrProperty (the UML property that defines the
     *         attribute in WorkmanagerFactory JavaScript class descriptor)
     */
    public Property getWorkItemAttrProperty() {
        return workItemAttrProperty;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        /*
         * XPD-7846: return the label
         */
        if (workListFacadeLabelProvider == null) {
            workListFacadeLabelProvider = new WorkListFacadeLabelProvider();
        }
        String lbl =
                workItemAttrProperty != null ? workListFacadeLabelProvider
                        .getDisplayLabelFromFacade(workItemAttrProperty
                                .getName()) : null;

        return lbl != null ? lbl : super.toString();
    }
}
