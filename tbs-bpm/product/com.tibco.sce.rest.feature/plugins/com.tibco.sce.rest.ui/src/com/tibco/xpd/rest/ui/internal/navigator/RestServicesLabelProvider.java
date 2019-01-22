/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.navigator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.RestImage;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;

/**
 * Navigator label provider for REST Service resources.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public class RestServicesLabelProvider extends LabelProvider {

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     *            The element to provide an image for.
     * @return The image, ot null.
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof SpecialFolder) {
            SpecialFolder sf = (SpecialFolder) element;
            if (RestServicesUtil.REST_SPECIAL_FOLDER_KIND.equals(sf.getKind())) {
                RestServicesActivator.getDefault()
                        .getImage(RestImage.REST_SPECIAL_FOLDER);
            }
        }
        return image;
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     * 
     * @param element
     *            The element to provide text for.
     * @return The navigator display label.
     */
    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof SpecialFolder) {
            SpecialFolder sf = (SpecialFolder) element;
            if (RestServicesUtil.REST_SPECIAL_FOLDER_KIND.equals(sf.getKind())) {
                text = sf.getFolder().getName();
            }
        }
        return text;
    }

}
