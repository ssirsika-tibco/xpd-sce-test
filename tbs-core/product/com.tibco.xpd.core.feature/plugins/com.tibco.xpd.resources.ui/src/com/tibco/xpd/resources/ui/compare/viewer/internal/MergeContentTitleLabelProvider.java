package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * The label provider for the area above left/right viewer for XpdCompareNode
 * input.
 * 
 * 
 * @author aallway
 * @since 13 Oct 2010
 */
public class MergeContentTitleLabelProvider implements
        ICompareInputLabelProvider {

    private CompareConfiguration compareConfiguration;

    private XpdCompareMergeViewer mergeViewer;

    private String leftResourceLabel = null;

    private String rightResourceLabel = null;

    private String ancestorResourceLabel = null;

    /**
     * @param compareConfiguration
     * @param xpdCompareNodeContentMergeViewer
     */
    public MergeContentTitleLabelProvider(
            CompareConfiguration compareConfiguration,
            XpdCompareMergeViewer mergeViewer) {
        super();

        this.mergeViewer = mergeViewer;

        this.compareConfiguration = compareConfiguration;

    }

    /**
     * Get the object that is the subject of teh difference we are merging.
     * <p>
     * This is the any of the elements that input {@link ICompareInput}
     * references in any of left/right/ancestor. Effectively these are all
     * 'equivalent' in terms of location in the tree BUT we will give preference
     * to the 'current value in the model merge viewer for the side in question
     * (in case user has already performed a copy).
     * 
     * @param input
     * @return object
     */
    private Object getLeftElement(Object input) {
        Object o = mergeViewer.getLeftContent(input);

        if (o == null) {
            if (input instanceof ICompareInput) {
                ICompareInput compareInput = (ICompareInput) input;

                o = compareInput.getAncestor();
                if (o == null) {
                    o = compareInput.getLeft();
                    if (o == null) {
                        o = compareInput.getRight();
                    }
                }
            }
        }
        return o;
    }

    /**
     * Get the object that is the subject of teh difference we are merging.
     * <p>
     * This is the any of the elements that input {@link ICompareInput}
     * references in any of left/right/ancestor. Effectively these are all
     * 'equivalent' in terms of location in the tree BUT we will give preference
     * to the 'current value in the model merge viewer for the side in question
     * (in case user has already performed a copy).
     * 
     * @param input
     * @return object
     */
    private Object getRightElement(Object input) {
        Object o = mergeViewer.getRightContent(input);

        if (o == null) {
            if (input instanceof ICompareInput) {
                ICompareInput compareInput = (ICompareInput) input;

                o = compareInput.getAncestor();
                if (o == null) {
                    o = compareInput.getLeft();
                    if (o == null) {
                        o = compareInput.getRight();
                    }
                }
            }
        }
        return o;
    }

    /**
     * Get the object that is the subject of teh difference we are merging.
     * <p>
     * This is the any of the elements that input {@link ICompareInput}
     * references in any of left/right/ancestor. Effectively these are all
     * 'equivalent' in terms of location in the tree BUT we will give preference
     * to the 'current value in the model merge viewer for the side in question
     * (in case user has already performed a copy).
     * 
     * @param input
     * @return object
     */
    private Object getAncestorElement(Object input) {
        Object o = mergeViewer.getAncestorContent(input);

        if (o == null) {
            if (input instanceof ICompareInput) {
                ICompareInput compareInput = (ICompareInput) input;

                o = compareInput.getAncestor();
                if (o == null) {
                    o = compareInput.getLeft();
                    if (o == null) {
                        o = compareInput.getRight();
                    }
                }
            }
        }
        return o;
    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getAncestorImage(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public Image getAncestorImage(Object input) {
        Image img = null;

        if (input instanceof ICompareInput) {
            Object element = ((ICompareInput) input).getAncestor();
            if (element != null) {
                img = MergeContentLabelProvider.getBaseImage(element);
            }
        }

        /*
         * Have to always return image else compare editor retains previous for
         * the ancestor view title.
         */
        return img != null ? img : XpdResourcesUIActivator.getDefault()
                .getImageRegistry().get(XpdResourcesUIConstants.ICON_BLANK16);
    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getAncestorLabel(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public String getAncestorLabel(Object input) {
        String name = ""; //$NON-NLS-1$

        if (input instanceof ICompareInput) {
            Object ancestor = ((ICompareInput) input).getAncestor();
            if (ancestor != null) {
                name = MergeContentLabelProvider.getBaseText(ancestor);

                while (ancestor instanceof IChildCompareNode) {
                    ancestor = ((IChildCompareNode) ancestor).getParent();
                    if (ancestor instanceof ITypedElement) {
                        name =
                                MergeContentLabelProvider.getBaseText(ancestor)
                                        + " / " + name; //$NON-NLS-1$
                    }
                }

                return "Common Ancestor:" + " " + name; //$NON-NLS-2$
            }
        }

        return "No Common Ancestor";

    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getLeftImage(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public Image getLeftImage(Object input) {
        Image img = null;

        /*
         * Get object from input (one side or other must have it!
         */
        Object element = getLeftElement(input);

        if (element != null) {
            boolean showImage = false;

            boolean decorate = false;

            int diffKind = 0;

            if (input instanceof IDiffElement) {
                IDiffElement diffElement = (IDiffElement) input;

                diffKind = diffElement.getKind();
            }

            int changeDirection = (diffKind & Differencer.DIRECTION_MASK);
            if (changeDirection != 0) { // Three-way merge!
                /*
                 * On a three way merge decorate image ONLY on the side on which
                 * change was made.
                 */
                if (changeDirection == Differencer.LEFT
                        || changeDirection == Differencer.CONFLICTING) {
                    decorate = true;

                    /*
                     * The COmpareConfiguration decoration uses direction from
                     * point of view of direction of arrow (it interprets
                     * Differencer.RIGHT as 'arrow point right' not 'change on
                     * right' so we need to switch the contenxt
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.RIGHT;

                    /* Always show the image when decorating it. */
                    showImage = true;

                }

                if (mergeViewer.getLeftContent(input) != null) {
                    /*
                     * Always show at least the base image when content exists
                     * on that side regardless of whether decorating it or not.
                     */
                    showImage = true;
                }

            } else {
                /*
                 * TWO WAY Merge - All changes are from the perspective of the
                 * RIGHT HAND SIDE side - therefore when an ADD is done in local
                 * copy (left hand side) compare with local history then
                 * diffKind will == "DIfferencer.DELETION". When REMOVE is done
                 * in local copy (left hand side) then diffKind will ==
                 * Differencer.ADD
                 */

                /*
                 * So we always show icon and decorate on the left (because
                 * object was added/removed/changed in left copy as far as
                 * compare is concerned.
                 */
                decorate = true;
                showImage = true;
            }

            if (showImage) {
                img = MergeContentLabelProvider.getBaseImage(element);

                if (img != null) {
                    if (decorate) {
                        img = compareConfiguration.getImage(img, diffKind);
                    }
                }
            }
        }

        /*
         * Always return at least a blank image (if return null then title is
         * left as with previous image
         */
        return img != null ? img : XpdResourcesUIActivator.getDefault()
                .getImageRegistry().get(XpdResourcesUIConstants.ICON_BLANK16);
    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getLeftLabel(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public String getLeftLabel(Object input) {
        String text = null;

        /*
         * Get object from input (one side or other must have it!
         */
        Object element = getLeftElement(input);

        if (element != null) {
            boolean showText = false;

            /* In case we want to append diff kind later. */
            boolean decorate = false;

            int diffKind = 0;

            if (input instanceof IDiffElement) {
                IDiffElement diffElement = (IDiffElement) input;

                diffKind = diffElement.getKind();
            }

            int changeDirection = (diffKind & Differencer.DIRECTION_MASK);
            if (changeDirection != 0) { // Three-way merge!
                /*
                 * On a three way merge decorate image on side which change was
                 * made.
                 */
                if (changeDirection == Differencer.LEFT
                        || changeDirection == Differencer.CONFLICTING) {
                    decorate = true;

                    /*
                     * The COmpareConfiguration decoration uses direction from
                     * point of view of direction of arrow (it interprets
                     * Differencer.RIGHT as 'arrow point right' not 'change on
                     * right' so we need to switch the contenxt
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.RIGHT;

                } else if (changeDirection == Differencer.RIGHT) {
                    /*
                     * If the object was deleted on right then decorate on right
                     * because we won't be showing label on the right.
                     */
                    if ((diffKind & Differencer.CHANGE_TYPE_MASK) == Differencer.DELETION) {
                        decorate = true;

                        /*
                         * The COmpareConfiguration decoration uses direction
                         * from point of view of direction of arrow (it
                         * interprets Differencer.RIGHT as 'arrow point right'
                         * not 'change on right' so we need to switch the
                         * contenxt
                         */
                        diffKind =
                                (diffKind & ~Differencer.DIRECTION_MASK)
                                        | Differencer.LEFT;
                    }
                }

            } else {
                /*
                 * TWO WAY Merge - All changes are from the perspective of the
                 * RIGHT HAND SIDE side - therefore when an ADD is done in local
                 * copy (left hand side) compare with local history then
                 * diffKind will == "DIfferencer.DELETION". When REMOVE is done
                 * in local copy (left hand side) then diffKind will ==
                 * Differencer.ADD
                 */

                /*
                 * The only time we don't want to decorate label with change is
                 * when we won't be showing the label anyway (i.e. when object
                 * removed on left then the decoration is shown on right hand
                 * side) - otherwise we show a decoration if there is one.
                 */
                decorate = true;
            }

            /*
             * Only/always show the label if the object exists in that content
             * (it may not have been in original content on left side according
             * to DiffNode but may be now in the merged content of left side
             * because of a merge - or visa versa.
             */
            if (mergeViewer.getLeftContent(input) != null) {
                showText = true;
            }

            if (showText) {
                text = MergeContentLabelProvider.getBaseText(element);

                if (decorate) {
                    // TODO May wish to textually decorate label to reinforce
                    // icon.
                }
            }
        }

        if (text == null) {
            text = "";
        }

        if (leftResourceLabel != null) {
            if (text.length() == 0
                    && getLeftElement(input) instanceof IStreamContentAccessor) {
                text = leftResourceLabel;
            } else {
                text = text + "  [" + leftResourceLabel + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        return text != null ? text : ""; //$NON-NLS-1$;
    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getRightImage(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public Image getRightImage(Object input) {
        Image img = null;

        /*
         * Get object from input (one side or other must have it!
         */
        Object element = getRightElement(input);

        if (element != null) {
            boolean showImage = false;

            boolean decorate = false;

            int diffKind = 0;

            if (input instanceof IDiffElement) {
                IDiffElement diffElement = (IDiffElement) input;

                diffKind = diffElement.getKind();
            }

            int changeDirection = (diffKind & Differencer.DIRECTION_MASK);
            if (changeDirection != 0) { // Three-way merge!
                /*
                 * On a three way merge decorate image on side which change was
                 * made.
                 */
                if (changeDirection == Differencer.RIGHT
                        || changeDirection == Differencer.CONFLICTING) {
                    decorate = true;

                    /*
                     * The COmpareConfiguration decoration uses direction from
                     * point of view of direction of arrow (it interprets
                     * Differencer.RIGHT as 'arrow point right' not 'change on
                     * right' so we need to switch the contenxt
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.LEFT;

                    /* Always show the image when decorating it. */
                    showImage = true;

                }

                if (mergeViewer.getRightContent(input) != null) {
                    /*
                     * Always show at least the base image when content exists
                     * on that side regardless of whether decorating it or not.
                     */
                    showImage = true;
                }

            } else {
                /*
                 * TWO WAY Merge - All changes are from the perspective of the
                 * RIGHT HAND SIDE side - therefore when an ADD is done in local
                 * copy (left hand side) compare with local history then
                 * diffKind will == "DIfferencer.DELETION". When REMOVE is done
                 * in local copy (left hand side) then diffKind will ==
                 * Differencer.ADD
                 */

                /*
                 * The change is always considsered to have been made to left
                 * side in two way merge so we never decorate it and we only
                 * show the base iconb if the object exists on the right hand
                 * side.
                 */
                if (mergeViewer.getRightContent(input) != null) {
                    showImage = true;
                }
            }

            if (showImage) {
                img = MergeContentLabelProvider.getBaseImage(element);

                if (img != null) {
                    if (decorate) {
                        img = compareConfiguration.getImage(img, diffKind);
                    }
                }
            }
        }

        /*
         * Always return at least a blank image (if return null then title is
         * left as with previous image
         */
        return img != null ? img : XpdResourcesUIActivator.getDefault()
                .getImageRegistry().get(XpdResourcesUIConstants.ICON_BLANK16);
    }

    /**
     * @see org.eclipse.compare.ICompareInputLabelProvider#getRightLabel(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public String getRightLabel(Object input) {
        String text = null;

        /*
         * Get object from input (one side or other must have it!
         */
        Object element = getRightElement(input);

        if (element != null) {
            boolean showText = false;

            /* In case we want to append diff kind later. */
            boolean decorate = false;

            int diffKind = 0;

            if (input instanceof IDiffElement) {
                IDiffElement diffElement = (IDiffElement) input;

                diffKind = diffElement.getKind();
            }

            int changeDirection = (diffKind & Differencer.DIRECTION_MASK);
            if (changeDirection != 0) { // Three-way merge!
                /*
                 * On a three way merge decorate image on side which change was
                 * made.
                 */
                if (changeDirection == Differencer.RIGHT
                        || changeDirection == Differencer.CONFLICTING) {
                    decorate = true;

                    /*
                     * The COmpareConfiguration decoration uses direction from
                     * point of view of direction of arrow (it interprets
                     * Differencer.RIGHT as 'arrow point right' not 'change on
                     * right' so we need to switch the contenxt
                     */
                    diffKind =
                            (diffKind & ~Differencer.DIRECTION_MASK)
                                    | Differencer.LEFT;

                } else if (changeDirection == Differencer.LEFT) {
                    /*
                     * If the object was deleted on left then decorate on right
                     * because we won't be showing label on the left.
                     */
                    if ((diffKind & Differencer.CHANGE_TYPE_MASK) == Differencer.DELETION) {
                        decorate = true;

                        /*
                         * The COmpareConfiguration decoration uses direction
                         * from point of view of direction of arrow (it
                         * interprets Differencer.RIGHT as 'arrow point right'
                         * not 'change on right' so we need to switch the
                         * contenxt
                         */
                        diffKind =
                                (diffKind & ~Differencer.DIRECTION_MASK)
                                        | Differencer.RIGHT;
                    }
                }

            } else {
                /*
                 * TWO WAY Merge - All changes are from the perspective of the
                 * RIGHT HAND SIDE side - therefore when an ADD is done in local
                 * copy (left hand side) compare with local history then
                 * diffKind will == "DIfferencer.DELETION". When REMOVE is done
                 * in local copy (left hand side) then diffKind will ==
                 * Differencer.ADD
                 */

                /*
                 * Therefore the element is an addition on RIGHT (removed on
                 * left) there won't be any label to decorate on left side
                 * becuase teh object won't exist on left - so decorate on
                 * right.
                 */
                if ((diffKind & Differencer.CHANGE_TYPE_MASK) == Differencer.ADDITION) {
                    decorate = true;
                }

            }

            /*
             * Only/always show the label if the object exists in that content
             * (it may not have been in original content on right side according
             * to DiffNode but may be now in the merged content of rightside
             * because of a merge - or visa versa.
             */
            if (mergeViewer.getRightContent(input) != null) {
                showText = true;
            }

            if (showText) {
                text = MergeContentLabelProvider.getBaseText(element);

                if (decorate) {
                    // TODO May wish to textually decorate label to reinforce
                    // icon.
                }
            }
        }

        if (text == null) {
            text = "";
        }

        if (rightResourceLabel != null) {
            if (text.length() == 0
                    && getRightElement(input) instanceof IStreamContentAccessor) {
                text = rightResourceLabel;
            } else {
                text = text + "  [" + rightResourceLabel + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        /*
         * Always return at least a blank image (if return null then title is
         * left as with previous image
         */
        return text != null ? text : ""; //$NON-NLS-1$;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        return MergeContentLabelProvider.getBaseImage(element);
    }

    /**
     * @return the leftResourceLabel
     */
    public String getLeftResourceLabel() {
        return leftResourceLabel;
    }

    /**
     * @param leftResourceLabel
     *            the leftResourceLabel to set
     */
    public void setLeftResourceLabel(String leftResourceLabel) {
        this.leftResourceLabel = leftResourceLabel;
    }

    /**
     * @return the rightResourceLabel
     */
    public String getRightResourceLabel() {
        return rightResourceLabel;
    }

    /**
     * @param rightResourceLabel
     *            the rightResourceLabel to set
     */
    public void setRightResourceLabel(String rightResourceLabel) {
        this.rightResourceLabel = rightResourceLabel;
    }

    /**
     * @return the ancestorResourceLabel
     */
    public String getAncestorResourceLabel() {
        return ancestorResourceLabel;
    }

    /**
     * @param ancestorResourceLabel
     *            the ancestorResourceLabel to set
     */
    public void setAncestorResourceLabel(String ancestorResourceLabel) {
        this.ancestorResourceLabel = ancestorResourceLabel;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        return MergeContentLabelProvider.getBaseText(element);
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }
}