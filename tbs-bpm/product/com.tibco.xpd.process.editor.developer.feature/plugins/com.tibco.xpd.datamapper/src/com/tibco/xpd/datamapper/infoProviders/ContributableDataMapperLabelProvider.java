/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.datamapper.DataMapperConstants;
import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Label provider for the Data Mapper. It asks the actual contributor to provide
 * the text and images. As its methods will expect WrappedContributedContent
 * object, it uses the contributed object and contributor inside the wrapped
 * object to provide the required information).
 * 
 * @author Ali
 * @since 20 Feb 2015
 */
class ContributableDataMapperLabelProvider extends LabelProvider implements
        IColorProvider {

    private boolean isRightHandSide;

    /**
     * @param isRightHandSide
     *            <code>true</code> if the label provider is for RHS content
     */
    public ContributableDataMapperLabelProvider(boolean isRightHandSide) {
        this.isRightHandSide = isRightHandSide;
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        if (element instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    (WrappedContributedContent) element;

            if (wrappedElement.getContributor().getInfoProvider() != null
                    && wrappedElement.getContributor().getInfoProvider()
                            .getLabelProvider() != null) {

                return wrappedElement.getContributor().getInfoProvider()
                        .getLabelProvider()
                        .getText(wrappedElement.getContributedObject());
            }
        }
        return super.getText(element);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        if (element instanceof WrappedContributedContent) {

            WrappedContributedContent wrappedElement =
                    (WrappedContributedContent) element;

            AbstractDataMapperInfoProvider infoProvider =
                    wrappedElement.getContributor().getInfoProvider();

            if (infoProvider != null) {
                /*
                 * Check if there is data mapper specified icon to use in place
                 * of content contributor's default.
                 */
                Image standardIconOverride =
                        getStandardIconOverride(wrappedElement, infoProvider);

                if (standardIconOverride != null) {
                    return standardIconOverride;
                }

                /*
                 * Get icon from same content contributer that provided this
                 * content.
                 */
                if (infoProvider.getLabelProvider() != null) {
                    return infoProvider.getLabelProvider()
                            .getImage(wrappedElement.getContributedObject());
                }

            }
        }
        return super.getImage(element);
    }

    /**
     * Checks for the need to override the content contributers normal icon for
     * an overriding generic data mapper purposed icon (such as array inflation
     * type.
     * 
     * @param wrappedElement
     * @param infoProvider
     * 
     * @return Override icon to use in preference to contributor's icon or
     *         <code>null</code> if there is none.
     */
    private Image getStandardIconOverride(
            WrappedContributedContent wrappedElement,
            AbstractDataMapperInfoProvider infoProvider) {
        Image standardIconOverride = null;

        /*
         * FOr right hand side elements, if it's multi instance type then check
         * array-inflation type and use alternate icon.
         */
        if (isRightHandSide
                && infoProvider.isMultiInstance(wrappedElement
                        .getContributedObject())) {

            String objectPath =
                    infoProvider.getObjectPath(wrappedElement
                            .getContributedObject());

            if (objectPath != null) {
                ScriptDataMapper scriptDataMapper =
                        wrappedElement.getScriptDataMapperModel();

                if (scriptDataMapper != null) {
                    /*
                     * Search for object in array inflation types list.
                     */
                    EList<DataMapperArrayInflation> arrayInflations =
                            scriptDataMapper.getArrayInflationType();

                    for (DataMapperArrayInflation arrayInflation : arrayInflations) {
                        if (objectPath.equals(arrayInflation.getPath())) {
                            DataMapperArrayInflationType mappingType =
                                    arrayInflation.getMappingType();

                            /*
                             * Override icon for non default types (append and
                             * merge)
                             */
                            if (DataMapperArrayInflationType.APPEND_LIST
                                    .equals(mappingType)) {
                                return DataMapperPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(DataMapperConstants.IMG_APPEND_LIST);

                            } else if (DataMapperArrayInflationType.MERGE_LIST
                                    .equals(mappingType)) {
                                return DataMapperPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(DataMapperConstants.IMG_MERGE_LIST);
                            }
                        }
                    }

                }
            }
        }
        return standardIconOverride;
    }

    /**
     * Overrides RHS tree item color to grey for RHS read only items.
     * 
     * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Color getForeground(Object element) {
        if (isRightHandSide) {
            if (element instanceof WrappedContributedContent) {

                WrappedContributedContent wrappedElement =
                        (WrappedContributedContent) element;

                if (wrappedElement.getContributor().getInfoProvider() != null) {
                    Object contributedObject =
                            wrappedElement.getContributedObject();

                    /* Is contributed object itself read only? */
                    boolean isReadOnly =
                            wrappedElement.getContributor().getInfoProvider()
                                    .isReadOnlyTarget(contributedObject);

                    if (!isReadOnly) {
                        /* Else is it contained within a read only ancestor. */
                        Object parent =
                                wrappedElement.getContributor()
                                        .getInfoProvider().getContentProvider()
                                        .getParent(contributedObject);

                        while (parent != null) {
                            if (wrappedElement.getContributor()
                                    .getInfoProvider().isReadOnlyTarget(parent)) {
                                isReadOnly = true;
                                break;
                            }

                            parent =
                                    wrappedElement.getContributor()
                                            .getInfoProvider()
                                            .getContentProvider()
                                            .getParent(parent);
                        }
                    }

                    if (isReadOnly) {
                        return ColorConstants.gray;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Color getBackground(Object element) {
        /* We currently do not override default background tree item color. */
        return null;
    }
}
