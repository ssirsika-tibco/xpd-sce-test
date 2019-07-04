/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * Label Provider for WorkListFacade Mapper View, i.e. the Right Hand Side of
 * the Physical Work Item Attribute Mapping Section. Shows Physical Work Item
 * Attribute Label as " <Physical Attribute Name> : <Type name>", If there is
 * not a WLF in workspace .
 * "<Display Label> (<Physical Attribute Name>) : <Type name>", if there is a
 * WLF in workspace.
 * 
 * @author aprasad
 * @since 30-Oct-2013
 */
public class WorkListFacadeLabelProvider implements ILabelProvider {
    /**
     * 
     */
    public WorkListFacadeLabelProvider() {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
        // Do Nothing

    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        // Do Nothing
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     * 
     * @param element
     * @param property
     * @return boolean, false , this is not a label property.
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        // No Property affects Label.
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
        // No Listener to remove
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return Image, image to be displayed for this element.Returns image based
     *         on the Physical Work Item Attribute Type.
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof WorkItemAttributeConceptPath) {
            image =
                    PhysicalWorkItemAttributesProvider.INSTANCE
                            .getImage(((WorkItemAttributeConceptPath) element)
                                    .getPhysicalAttribute());

        } else if (element instanceof String) {
            image =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_INFO_ICON);
        }
        return image;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return String, text to be displayed for this element.
     */
    @Override
    public String getText(Object element) {
        if (element instanceof WorkItemAttributeConceptPath) {
            return getAttributeLabelForMapper(((WorkItemAttributeConceptPath) element)
                    .getPhysicalAttribute());
        } else if (element instanceof String) {
            return (String) element;
        }
        return Messages.WLFAttributeAliasConceptPath_UnresolvedReference;
    }

    /**
     * Returns Label for Physical Work Item Attribute to be displayed in Mapper.
     * 
     * @param property
     * @return String, Label for {@link WorkItemAttribute}, as
     *         "Physical Work Item Attribute Name : Attribute Type" OR
     *         "Attribute Display Label (Physical Worm Item Attribute Name) : Attribute Type"
     */
    public String getAttributeLabelForMapper(Property property) {

        return getAttributeLabelForMapper(property, true);
    }

    /**
     * Returns Label for Physical Work Item Attribute to be displayed in Mapper.
     * 
     * @param property
     * @param shouldHaveNameInBrackets
     *            decides if the Label should have the name of the attribute in
     *            brackets '( )'
     * @return String, Label for {@link WorkItemAttribute}, as
     *         "Physical Work Item Attribute Name : Attribute Type" OR
     *         "Attribute Display Label (Physical Worm Item Attribute Name) : Attribute Type"
     *         , Optionally adds the name of the attribute in brackets '( )'
     *         based upon the value of the passed param
     *         'shouldHaveNameInBrackets'
     */
    public String getAttributeLabelForMapper(Property property,
            boolean shouldHaveNameInBrackets) {

        String displayLabelFromFacade =
                getDisplayLabelFromFacade(property.getName());

        StringBuffer label = new StringBuffer();

        /*
         * Sid ACE-1720 Use display label for type name not the internal type
         * name (so that Number and Date Time and Timezone appear correctly in
         * work item attribute mappings and datamapper
         */
        String typeLabel = PrimitivesUtil.getDisplayLabel(property.getType(), true);

        if (displayLabelFromFacade != null) {

            if (shouldHaveNameInBrackets) {
                label.append(String.format("%s (%s) : %s", //$NON-NLS-1$
                        displayLabelFromFacade,
                        property.getName(),
                        typeLabel));
            } else {
                label.append(String.format("%s : %s", //$NON-NLS-1$
                        displayLabelFromFacade,
                        typeLabel));
            }
        } else {
            label.append(String.format("%s : %s", //$NON-NLS-1$
                    property.getName(),
                    typeLabel));
        }
        return label.toString();
    }

    /**
     * Returns the Display Label from the Work Item Attribute Facade File if
     * exists. Returns null if there is no facade file in Workspace, or the
     * Display label for given Physical Work Item Attribute is not defined.
     * 
     * @param name
     * @return String, Display Label for the Physical Attribute from the Work
     *         List Facade if exists, null otherwise.
     */
    public String getDisplayLabelFromFacade(String name) {
        if (name != null && !name.isEmpty()) {
            // get facade file available in workspace
            IResource facadeFileInWorkspace =
                    WorkListFacadeMapperUtil
                            .getFirstWorkListFacadeFileInWorkspace();

            // when file is found check and return the DisplayLabel for
            // attribute with given name
            if (facadeFileInWorkspace != null) {
                // facade working Copy
                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(facadeFileInWorkspace);

                if (workingCopy instanceof WorkListFacadeWorkingCopy) {

                    WorkListFacadeWorkingCopy wlfWCopy =
                            (WorkListFacadeWorkingCopy) workingCopy;
                    // Get Attributes
                    WorkItemAttributes workItemAttributes =
                            wlfWCopy.getWorkListFacade()
                                    .getWorkItemAttributes();

                    if (workItemAttributes != null) {
                        // Check if this attribute has a Display Label Defined.
                        for (WorkItemAttribute workItemAttrib : workItemAttributes
                                .getWorkItemAttribute()) {

                            if (name.equals(workItemAttrib.getName())) {
                                // return Defined Display Label.
                                return workItemAttrib.getDisplayLabel();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
