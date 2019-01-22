/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.process.datamapper.common;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperElement;
import com.tibco.xpd.datamapper.staticcontent.VirtualDataMapperFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Info provider that helps to represent the use case where a static JavaScript
 * class is presented in a data mapper tree content.
 * <p>
 * The content provider used in conjunction with this info provider (via the
 * {@link AbstractDataMapperContentContributor} for this contribution) should
 * use objects of the only following types (unless it sub-classes info provider
 * to deal wityh alternative content object types...
 * <li>{@link VirtualDataMapperFolder} for content container folders that do not
 * appear in object path.</li>
 * <li>{@link StaticContentDataMapperElement}</li>
 * <li>Other derivatives of
 * {@link AbstractJavaScriptStaticClassDataMapperElement}</li>
 */
public class StaticContentDataMapperInfoProvider extends
        ProcessDataMapperInfoProvider {

    /**
     * @param contentProvider
     * @param labelProvider
     */
    public StaticContentDataMapperInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        super(contentProvider, labelProvider);
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#getObjectForPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param objectPath
     * @param mapperInput
     * @return
     */
    @Override
    public Object getObjectForPath(String objectPath, Object mapperInput) {
        return recursiveFindObjectForPath(objectPath, getContentProvider()
                .getElements(mapperInput));
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#getObjectPathDescription(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {

        if (objectFromMappingOrContent instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) objectFromMappingOrContent;
            /*
             * XPD-7846: we should not use the mapper label provider getText()
             * method because that is specific to the mapper, rather the
             * toString method should be provided by the resp element provider.
             */
            /* Build path to object thru parents */
            StringBuilder sb = new StringBuilder(cp.toString());

            ConceptPath parent = cp.getParent();
            while (parent != null) {
                sb.insert(0, parent.toString() + "."); //$NON-NLS-1$

                parent = parent.getParent();
            }

            return sb.toString();

        }

        return objectFromMappingOrContent.toString();
    }

    /**
     * Recursive search the content provider's content looking for the object
     * with the given path
     * 
     * @param objectPath
     * @param elements
     * @return Object mathcing the given path.
     */
    private Object recursiveFindObjectForPath(String objectPath,
            Object[] elements) {
        for (Object object : elements) {
            if (object instanceof ConceptPath) {
                ConceptPath cp = (ConceptPath) object;

                if (objectPath.equals(cp.getPath())) {
                    return cp;

                } else {
                    // recurs
                    Object found =
                            recursiveFindObjectForPath(objectPath,
                                    getContentProvider().getChildren(cp));
                    if (found != null) {
                        return found;
                    }

                }
            }
        }

        return null;
    }

}