/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.n2.ut.resources.processdatamapper;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeLabelProvider;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;

/**
 * Label provider for the Data mapper Content Contributor that supports the use
 * of the content of the JavaScript class "WorkManagerFactory" within User Task
 * Work manager Scripts.
 * <p>
 * This handles labelling of all content provided by
 * {@link WMDataMapperContentProvider} (which states that all content is derived
 * from {@link ConceptPath}).
 */
class WMDataMapperLabelProvider extends StaticContentDataMapperLabelProvider {

    private ParameterLabelProvider conceptPathLabelProvider = null;

    private WorkListFacadeLabelProvider workListFacadeLabelProvider = null;

    /**
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {

    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {

        if (element instanceof WMDataMapperWorkItemAttributeElement) {
            WMDataMapperWorkItemAttributeElement attribute =
                    (WMDataMapperWorkItemAttributeElement) element;

            return PhysicalWorkItemAttributesProvider.INSTANCE
                    .getImage(attribute.getWorkItemAttrProperty());
        }

        return super.getImage(element);
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof WMDataMapperWorkItemAttributeElement) {
            /*
             * Get display label from the in-play WLF and use to override
             * property name if available.
             */
            Property workItemAttrProperty =
                    ((WMDataMapperWorkItemAttributeElement) objectFromMappingOrContent)
                            .getWorkItemAttrProperty();

            if (workItemAttrProperty != null) {

                if (workListFacadeLabelProvider == null) {
                    workListFacadeLabelProvider =
                            new WorkListFacadeLabelProvider();
                }
                /*
                 * XPD-7846: Using workListFacadeLabelProvider
                 * .getAttributeLabelForMapper as it returns the
                 * "displayName : type"
                 */
                return workListFacadeLabelProvider
                        .getAttributeLabelForMapper(workItemAttrProperty, false);
            }
        }

        return super.getText(objectFromMappingOrContent);
    }

}