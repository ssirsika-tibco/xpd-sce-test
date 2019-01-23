/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;

/**
 * {@link EListCompareNode} with alternative name label and icon.
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public class WrappedEListCompareNode extends EListCompareNode {

    private String label;

    private Image image;

    /**
     * @param list
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public WrappedEListCompareNode(EList list, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory,
            String label, Image image) {
        super(list, feature, parentCompareNode, compareNodeFactory);

        this.label = label;
        this.image = image;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        if (image != null) {
            return image;
        }
        return super.getImage();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        if (label != null) {
            return label;
        }
        return super.getName();
    }
}
