/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;

/**
 * Fragments view tree label provider and decorator.
 * 
 * @author njpatel
 * 
 */
public class FragmentsViewLabelProvider extends LabelProvider implements
        ILabelDecorator {

    private final Image categoryImg;
    private final Image fragmentsImg;

    // Locked category images
    private final Map<Image, Image> lockedCategoryImageMap;

    /**
     * Fragments view label provider and decorator.
     */
    public FragmentsViewLabelProvider() {
        categoryImg = FragmentsActivator.getDefault().getImageRegistry().get(
                FragmentConsts.IMG_CATEGORY);
        fragmentsImg = FragmentsActivator.getDefault().getImageRegistry().get(
                FragmentConsts.IMG_FRAGMENT);

        lockedCategoryImageMap = new HashMap<Image, Image>();

    }

    @Override
    public String getText(Object element) {
        String name = null;

        if (element instanceof IFragmentElement) {
            name = ((IFragmentElement) element).getNameLabel();
        }

        return name != null ? name : super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        Image img = null;

        if (element instanceof FragmentElementImpl) {
            FragmentsProvider provider = ((FragmentElementImpl) element)
                    .getProvider();

            if (provider != null) {
                try {
                    FragmentsContributor providerClass = provider
                            .getProviderClass();

                    if (providerClass != null) {
                        img = providerClass.getIcon((IFragmentElement) element);
                    }
                } catch (CoreException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                }
            }
        }

        if (img == null) {
            if (element instanceof IFragmentCategory) {
                img = categoryImg;
            } else if (element instanceof IFragment) {
                img = fragmentsImg;
            }
        }
        return img != null ? img : super.getImage(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt
     * .graphics.Image, java.lang.Object)
     */
    public Image decorateImage(Image image, Object element) {
        if (element instanceof IFragmentCategory
                && ((IFragmentCategory) element).isSystem() && image != null) {

            // Check the map for an image that may already have been created
            Image lockedImg = lockedCategoryImageMap.get(image);

            if (lockedImg == null) {
                // Create a locked image for this category and store in local
                // cache
                DecorationOverlayIcon overlay = new DecorationOverlayIcon(
                        image, FragmentsActivator.imageDescriptorFromPlugin(
                                FragmentsActivator.PLUGIN_ID,
                                FragmentConsts.IMG_LOCK_OVR),
                        IDecoration.BOTTOM_RIGHT);

                lockedImg = overlay.createImage();
                lockedCategoryImageMap.put(image, lockedImg);
            }
            return lockedImg;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String,
     * java.lang.Object)
     */
    public String decorateText(String text, Object element) {
        return null;
    }

    @Override
    public void dispose() {
        // Dispose off all the overlay images created
        for (Image img : lockedCategoryImageMap.values()) {
            img.dispose();
        }
        lockedCategoryImageMap.clear();

        super.dispose();
    }
}
