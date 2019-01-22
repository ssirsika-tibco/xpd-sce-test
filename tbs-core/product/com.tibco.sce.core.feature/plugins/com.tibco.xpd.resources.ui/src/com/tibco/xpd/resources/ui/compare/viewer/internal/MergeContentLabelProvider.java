/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * Label provider for left/right/ancestor views of
 * {@link XpdCompareMergeViewer}
 * 
 * @author aallway
 * @since 1 Oct 2010
 */
class MergeContentLabelProvider extends LabelProvider {

    private MergeContentViewerType compareSide;

    private CompareConfiguration compareConfiguration;

    /**
     * @param compareConfiguration
     * @param viewSide
     *            Controls how the
     */
    public MergeContentLabelProvider(CompareConfiguration compareConfiguration,
            MergeContentViewerType compareSide) {
        super();
        this.compareConfiguration = compareConfiguration;
        this.compareSide = compareSide;
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        String text = getBaseText(element);
        return text;
    }

    /**
     * @param element
     * @return
     */
    public static String getBaseText(Object element) {
        String text = null;

        if (element instanceof ITypedElement) {
            text = ((ITypedElement) element).getName();
        } else if (element instanceof MergeContentInfoNodesSeparatorLine) {
            /*
             * MergeTreeChangeDecorator will draw a line in for the separator
             * for us.
             */
            text = ""; //$NON-NLS-1$

        }
        // TODO conditionalise this out before final release
        else if (false && (element instanceof MissingMergeContentPlaceHolder)) {
            text =
                    "    ** missing ** "
                            + getBaseText(((MissingMergeContentPlaceHolder) element)
                                    .getWrappedContentObject());

        } else {
            text = ""; //$NON-NLS-1$
        }

        if (text != null) {
            text = text.replaceAll("\r", "\\\\r");
            text = text.replaceAll("\n", "\\\\n");
            text = text.replaceAll("\t", "\\\\t");
        }
        return text;
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        Integer diffKind = null;

        if (element instanceof XpdCompareNode) {
            diffKind =
                    (Integer) ((XpdCompareNode) element)
                            .getProperty(XpdCompareMergeViewer.DIFF_NODE_KIND_PROPERTY);
        }

        /**
         * Now that we are putting the label on the connector line we dont need
         * it on the content tree's themselves.
         */
        if (true) {
            return getBaseImage(element);
        } else {
            /*
             * Just in case we want to return to decorating the left/right tree
             * content with difference icons
             */
            return getImage(element, diffKind);
        }
    }

    public Image getImage(Object element, Integer diffKind) {
        if (diffKind == null) {
            diffKind = Differencer.NO_CHANGE;
        }

        Image img = getBaseImage(element);

        if (img == null) {
            img = super.getImage(element);
        }

        /*
         * For 2 way merge, never decorate the right hand window. (Direction is
         * only set for 3-way comparison)
         */
        boolean decorateImage = false;

        boolean isThreeWay = (diffKind & Differencer.DIRECTION_MASK) != 0;

        if (isThreeWay) {
            /*
             * Always decorate left and right side icons for threeway but not
             * the ancestor)
             */
            if (!MergeContentViewerType.ANCESTOR.equals(compareSide)) {

                if ((diffKind & Differencer.DIRECTION_MASK) == Differencer.RIGHT) {
                    /*
                     * Decorate images that happened on the side we are
                     * interested in.
                     */
                    if (MergeContentViewerType.RIGHT.equals(compareSide)) {
                        decorateImage = true;
                    }
                    /*
                     * However, for deletion's we need to show the decoration on
                     * the opposite side (as we will not have the object on the
                     * side it was deleted).
                     */
                    else if (MergeContentViewerType.LEFT.equals(compareSide)) {
                        if ((diffKind & Differencer.CHANGE_TYPE_MASK) == Differencer.DELETION) {
                            decorateImage = true;
                        }
                    }

                    /*
                     * For some reason the image decorator in
                     * CompareConfiguration seem to cache return the OPPOSITE
                     * image for side indicated in diffKind for three-way
                     * compare. i.e. by default if diffKind=right+add then it
                     * returns the grey arrow with a + in (which in the top
                     * window indicates a addition to the right) - I think it is
                     * caching them in terms of diffKind direction = direction
                     * of arrow NOT direction of change!! :o(
                     * 
                     * So we have to swap the diff direction kind.
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.LEFT;

                } else if ((diffKind & Differencer.DIRECTION_MASK) == Differencer.LEFT) {
                    /*
                     * Decorate images that happened on the side we are
                     * interested in.
                     */
                    if (MergeContentViewerType.LEFT.equals(compareSide)) {
                        decorateImage = true;
                    }
                    /*
                     * However, for deletion's we need to show the decoration on
                     * the opposite side (as we will not have the object on the
                     * side it was deleted).
                     */
                    else if (MergeContentViewerType.RIGHT.equals(compareSide)) {
                        if ((diffKind & Differencer.CHANGE_TYPE_MASK) == Differencer.DELETION) {
                            decorateImage = true;
                        }
                    }

                    /*
                     * For some reason the image decorator in
                     * CompareConfiguration seem to cache return the OPPOSITE
                     * image for side indicated in diffKind for three-way
                     * compare. i.e. by default if diffKind=right+add then it
                     * returns the grey arrow with a + in (which in the top
                     * window indicates a addition to the right) - I think it is
                     * caching them in terms of diffKind direction = direction
                     * of arrow NOT direction of change!! :o(
                     * 
                     * So we have to swap the diff direction kind.
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.RIGHT;

                } else {
                    /* For conflicting change always decorate both sides */
                    decorateImage = true;
                }
            }

        } else {
            /*
             * Two-way merge, always decorate
             */
            decorateImage = true;
        }

        if (!decorateImage) {
            /*
             * Still want to run thru the decorator (so that all icons are sized
             * as if decorated I think)
             */
            diffKind = Differencer.NO_CHANGE;
        }

        if (img != null) {
            img = compareConfiguration.getImage(img, diffKind);

        } else if ((diffKind & (Differencer.CHANGE_TYPE_MASK | Differencer.DIRECTION_MASK)) != 0) {
            img = compareConfiguration.getImage(diffKind);
        }

        return img;
    }

    /**
     * @param element
     * @return
     */
    public static Image getBaseImage(Object element) {
        Image img = null;
        if (element instanceof ITypedElement) {
            img = ((ITypedElement) element).getImage();

        } else if (element instanceof MergeContentInfoNodesSeparatorLine
                || element instanceof MissingMergeContentPlaceHolder) {
            img =
                    XpdResourcesUIActivator.getDefault().getImageRegistry()
                            .get(XpdResourcesUIConstants.ICON_BLANK16);
        }

        if (img == null) {
            img =
                    XpdResourcesUIActivator
                            .getDefault()
                            .getImageRegistry()
                            .get(XpdResourcesUIConstants.ICON_DEFAULT_COMPARATOR_NODE);
        }
        return img;
    }
}