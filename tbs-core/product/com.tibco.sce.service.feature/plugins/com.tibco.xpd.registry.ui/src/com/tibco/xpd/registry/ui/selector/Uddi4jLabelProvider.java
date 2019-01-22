/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.selector;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.uddi4j.response.BusinessInfo;
import org.uddi4j.response.ServiceInfo;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;

/**
 * @author nwilson
 */
public class Uddi4jLabelProvider extends LabelProvider implements
        ILabelProvider {
    /** Image to use for registries in the tree. */
    private final Image registry;

    /** Image to use for organisations in the tree. */
    private final Image organization;

    /** Image to use for services in the tree. */
    private final Image service;

    /** Image to use for services in the tree. */
    private final Image search;

    /**
     * Constructor.
     */
    public Uddi4jLabelProvider() {
        ImageCache cache = Activator.getDefault().getImageCache();
        registry = cache.getImage(ImageCache.REGISTRY);
        organization = cache.getImage(ImageCache.ORGANIZATION);
        service = cache.getImage(ImageCache.SERVICE);
        search = cache.getImage(ImageCache.SEARCH);
    }

    /**
     * @param element
     *            The element to provide an image for.
     * @return The image for that element, or null if one isn't defined.
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof Registry) {
            image = registry;
        } else if (element instanceof RegistrySearch) {
            image = search;
        } else if (element instanceof BusinessInfo) {
            image = organization;
        } else if (element instanceof ServiceInfo) {
            image = service;
        }
        return image;
    }

    /**
     * @param element
     *            The element to provide the text label for.
     * @return The label for the element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
        String string = null;
        if (element instanceof Registry) {
            string = ((Registry) element).getName();
        } else if (element instanceof RegistrySearch) {
            string = ((RegistrySearch) element).getSearch().getName();
        } else if (element instanceof BusinessInfo) {
            string = ((BusinessInfo) element).getDefaultNameString();
        } else if (element instanceof ServiceInfo) {
            string = ((ServiceInfo) element).getDefaultNameString();
        }
        return string;
    }

}
