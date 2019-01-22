/**
 * FragmentThumbnailList.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.fragments.internal.thumbnail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.Messages;

/**
 * FragmentThumbnailList
 * <p>
 * Use {@link #setDataContent(IFragmentElement)} to set the input of this list.
 * </p>
 * 
 */
public class FragmentThumbnailList extends ScrolledComposite implements
        ISelectionProvider {

    /**
     * NavigationKeyListener
     * 
     */
    private final class NavigationKeyListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            if (fragmentThumbnails.size() > 0) {

                FragmentThumbnail currentThumb = getThumbnailForFragment(currSelection);
                if (currentThumb == null) {
                    currentThumb = fragmentThumbnails.get(0);
                }

                int currIdx = fragmentThumbnails.indexOf(currentThumb);

                int numCols = imageContainerLayout.getNumberOfColumns();
                int numRows = imageContainerLayout.getNumberOfRows();

                FragmentThumbnail selThumb = currentThumb;

                switch (e.keyCode) {
                case SWT.HOME:
                    selThumb = fragmentThumbnails.get(0);
                    break;

                case SWT.END:
                    selThumb = fragmentThumbnails
                            .get(fragmentThumbnails.size() - 1);
                    break;

                case SWT.ARROW_RIGHT:
                    if (currIdx >= (fragmentThumbnails.size() - 1)) {
                        selThumb = fragmentThumbnails.get(0);
                    } else {
                        selThumb = fragmentThumbnails.get(currIdx + 1);
                    }
                    break;

                case SWT.ARROW_LEFT:
                    if (currIdx == 0) {
                        selThumb = fragmentThumbnails.get(fragmentThumbnails
                                .size() - 1);
                    } else {
                        selThumb = fragmentThumbnails.get(currIdx - 1);
                    }
                    break;

                case SWT.ARROW_DOWN:
                    if (numCols > 0 && numRows > 0) {
                        if ((currIdx + numCols) < (fragmentThumbnails.size() - 1)) {
                            // There's another item directly below.
                            selThumb = fragmentThumbnails
                                    .get(currIdx + numCols);

                        } else {
                            int curRow = (currIdx / numCols); // zero based
                            // row num

                            if (curRow >= (numRows - 1)) {
                                // On last row already - move to start of next
                                // row.
                                selThumb = fragmentThumbnails
                                        .get((currIdx % numCols));
                            } else {
                                // Not on last row, must be less columns on it
                                // so select last.
                                selThumb = fragmentThumbnails
                                        .get(fragmentThumbnails.size() - 1);
                            }
                        }
                    }
                    break;

                case SWT.ARROW_UP:
                    if (numCols > 0 && numRows > 0) {
                        if ((currIdx - numCols) >= 0) {
                            // There's another directly above us.
                            selThumb = fragmentThumbnails
                                    .get(currIdx - numCols);

                        } else {
                            // On top row , move to last row.
                            int nextIdx = ((numRows - 1) * numCols) + currIdx;

                            if (nextIdx > (fragmentThumbnails.size() - 1)) {
                                nextIdx = fragmentThumbnails.size() - 1;
                            }

                            selThumb = fragmentThumbnails.get(nextIdx);
                        }
                    }
                    break;

                case SWT.TAB:
                    if ((e.stateMask & SWT.SHIFT) != 0) {
                        imagesContainer.traverse(SWT.TRAVERSE_TAB_PREVIOUS);
                    } else {
                        imagesContainer.traverse(SWT.TRAVERSE_TAB_NEXT);
                    }
                    return;

                default:

                    return;

                }

                if (selThumb != currentThumb) {
                    if (selThumb != null) {
                        setSelection(new StructuredSelection(selThumb
                                .getFragment()));
                    } else {
                        setSelection(new StructuredSelection());
                    }
                }
            }

            return;
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    private static final int IMG_THUMB_WIDTH = 150;

    private static final int IMG_THUMB_HEIGHT = 100;

    private FragmentsViewPart fragmentViewPart;

    private FragmentThumbnailListLayout imageContainerLayout;

    private final List<FragmentThumbnail> fragmentThumbnails = new ArrayList<FragmentThumbnail>();

    private Object currentSetContent = null;

    // Either Fragment (if fragment thumb is selected content is a single
    // fragment) OR category if no fragments in category or user clicks outside
    // of frags
    private Object currSelection = null;

    private List<ISelectionChangedListener> selectionListeners = new ArrayList<ISelectionChangedListener>();

    Composite imagesContainer = null;

    private MouseListener mouseSelectionListener;

    private FocusListener allCtrlFocusListener = null;

    private KeyListener navigationKeyListener = null;

    private boolean haveFocus = false; // true of this any child is focused.

    /**
     * Construct the frgament thumbnail list viewer
     * 
     * @param parent
     *            parent control
     * @param style
     *            display style
     */
    public FragmentThumbnailList(Composite parent, int style) {
        this(parent, style, null);
    }

    /**
     * Construct the frgament thumbnail list viewer
     * 
     * @param parent
     * @param style
     * @param viewPart
     *            fragments view part to add context menu and drag support,
     *            <code>null</code> if these functionality is not required.
     */
    public FragmentThumbnailList(Composite parent, int style,
            FragmentsViewPart viewPart) {
        super(parent, style | SWT.V_SCROLL | SWT.H_SCROLL);

        this.fragmentViewPart = viewPart;

        setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        imagesContainer = new Composite(this, SWT.NONE);

        imagesContainer.setBackground(getDisplay().getSystemColor(
                SWT.COLOR_LIST_BACKGROUND));

        imageContainerLayout = new FragmentThumbnailListLayout(this);
        imageContainerLayout.imageItemWidth = 100;
        imageContainerLayout.imageItemHeight = 100;
        imagesContainer.setLayout(imageContainerLayout);
        setContent(imagesContainer);

        // Listener for click-select's on thumbnails and container
        mouseSelectionListener = new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                if (e.widget instanceof FragmentThumbnail) {
                    // Select fragment for the tgiven thumbnail.
                    FragmentThumbnail fragThumb = (FragmentThumbnail) e.widget;

                    setSelection(new StructuredSelection(fragThumb
                            .getFragment()));

                } else if (currentSetContent != null) {
                    setSelection(new StructuredSelection(currentSetContent));

                } else {
                    setSelection(new StructuredSelection());

                }
                imagesContainer.forceFocus();
            }
        };

        //
        // Listener for focus events on child controls
        allCtrlFocusListener = new FocusListener() {
            public void focusGained(FocusEvent e) {
                // Update selection state
                haveFocus = true;

                imagesContainer.forceFocus();
                updateSelectionStates();

                // Force re-selection of current selection on gain focus so that
                // menu handlers get updated correctly.
                setSelection(getSelection());

            }

            public void focusLost(FocusEvent e) {
                haveFocus = false;
                updateSelectionStates();
            }
        };

        //
        // Listener for navigation key presses.
        navigationKeyListener = new NavigationKeyListener();

        //
        // Add listeners to main ctrls.
        this.addFocusListener(allCtrlFocusListener);
        imagesContainer.addFocusListener(allCtrlFocusListener);

        this.addMouseListener(mouseSelectionListener);
        imagesContainer.addMouseListener(mouseSelectionListener);

        imagesContainer.addKeyListener(navigationKeyListener);

        return;
    }

    @Override
    public void setMenu(Menu menu) {
        super.setMenu(menu);
        if (imagesContainer != null && !imagesContainer.isDisposed()) {
            imagesContainer.setMenu(menu);
        }
    }

    /**
     * @return the fragmentThumbnails
     */
    public List<FragmentThumbnail> getFragmentThumbnails() {
        return fragmentThumbnails;
    }

    @Override
    public void dispose() {
        if (fragmentThumbnails != null) {
            for (FragmentThumbnail thumbnail : fragmentThumbnails) {
                thumbnail.dispose();
            }
            fragmentThumbnails.clear();
        }

        if (imagesContainer != null) {
            imagesContainer.dispose();
            imagesContainer = null;
        }

        super.dispose();
    }

    /**
     * Reset content to the given fragment element
     * 
     * @param fragment
     *            element
     */
    public void setDataContent(IFragmentElement fragmentElem) {
        resetContent(fragmentElem);
    }

    /**
     * Remove the content from the container.
     * 
     */
    public void unsetDataContent() {
        resetContent(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
     * (org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
     */
    public ISelection getSelection() {
        if (currSelection != null) {
            return new StructuredSelection(currSelection);
        }

        return new StructuredSelection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener
     * (org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        selectionListeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
     * .jface.viewers.ISelection)
     */
    public void setSelection(ISelection selection) {
        currSelection = null;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            if (sel.getFirstElement() instanceof IFragment) {
                currSelection = (IFragment) sel.getFirstElement();

            } else if (sel.getFirstElement() instanceof IFragmentCategory) {
                currSelection = (IFragmentCategory) sel.getFirstElement();
            }
        }

        final SelectionChangedEvent e = new SelectionChangedEvent(this,
                selection);
        Object[] listenersArray = selectionListeners.toArray();

        for (int i = 0; i < listenersArray.length; i++) {
            final ISelectionChangedListener l = (ISelectionChangedListener) listenersArray[i];
            SafeRunner.run(new SafeRunnable() {
                public void run() {
                    l.selectionChanged(e);
                }
            });
        }

        updateSelectionStates();

        if (currSelection != null) {
            FragmentThumbnail fragThumb = getThumbnailForFragment(currSelection);

            if (fragThumb != null) {
                revealThumb(fragThumb);
            }
        }

        return;
    }

    /**
     * @param fragThumb
     */
    private void revealThumb(FragmentThumbnail thumb) {
        Rectangle sectBounds = this.getBounds();
        Point sectLoc = new Point(sectBounds.x, sectBounds.y);
        sectLoc = getParent().toDisplay(sectLoc);

        Rectangle absBounds = new Rectangle(sectLoc.x, sectLoc.y,
                sectBounds.width, sectBounds.height);

        Rectangle thumbBounds = thumb.getBounds();
        Point thumbLoc = new Point(thumbBounds.x, thumbBounds.y);
        thumbLoc = thumb.getParent().toDisplay(thumbLoc);

        if (!absBounds.contains(thumbLoc)
                || !absBounds.contains(thumbLoc.x + thumbBounds.width,
                        thumbLoc.y + thumbBounds.height)) {
            // System.out.println("Thumb is partially obscured.");

            Point origin = getOrigin();

            int numcols = imageContainerLayout.getNumberOfColumns();
            int idx = fragmentThumbnails.indexOf(thumb);

            if (idx < numcols) {
                // If a top row thumb is selected then position at top so that
                // description label is visible
                origin.y = 0;

            } else if (thumbLoc.y > (sectLoc.y + sectBounds.height)) {
                origin.y = (thumbBounds.y + thumbBounds.height)
                        - sectBounds.height;

            } else if ((thumbLoc.y + thumbBounds.height) < sectLoc.y) {
                origin.y = thumbBounds.y;
            } else {
                origin.y = thumbBounds.y;
            }

            setOrigin(origin);
        }

    }

    private FragmentThumbnail getThumbnailForFragment(Object fragment) {
        FragmentThumbnail retThumb = null;

        if (fragment instanceof IFragment) {
            for (FragmentThumbnail thumb : fragmentThumbnails) {
                if (thumb.getFragment() == (IFragment) fragment) {
                    retThumb = thumb;
                    break;
                }
            }
        }

        return retThumb;
    }

    /**
     * Refresh the images section according to the given section.
     * 
     * @param newContent
     */
    private void resetContent(Object newContent) {

        Object originalSelection = null;

        if (newContent == currentSetContent) {
            originalSelection = currSelection;
        }

        currSelection = null;

        // Remove current thumbnail children
        Control[] children = imagesContainer.getChildren();

        if (children != null) {
            for (Control child : children) {
                if (child instanceof FragmentThumbnail) {
                    child.setMenu(null);
                    child.dispose();
                }
            }
        }

        fragmentThumbnails.clear();

        currentSetContent = null;

        imagesContainer.setLayoutDeferred(true);

        if (newContent instanceof IFragmentCategory
                || newContent instanceof IFragment) {

            currentSetContent = newContent;

            // Get a list of images that pertinent to given selection.
            List<IFragment> fragments;

            if (newContent instanceof IFragment) {
                fragments = new ArrayList<IFragment>();
                fragments.add((IFragment) newContent);
            } else {
                IFragmentCategory category = ((IFragmentCategory) newContent);

                fragments = getSortedFragments(category.getChildren());
            }

            int maxLabelHeight = 0;

            //
            // Create a thumbnail for each fragment
            for (IFragment fragment : fragments) {
                // Setup tool tip.
                String toolTip = ""; //$NON-NLS-1$
                String desc = fragment.getDescriptionLabel();
                if (desc != null && desc.length() > 0) {
                    toolTip = desc + "\n\n"; //$NON-NLS-1$
                }
                toolTip += Messages.FragmentThumbnailList_dragOntoDiagram_message;

                // Create the thumbnail.
                FragmentThumbnail fragThumbnail = new FragmentThumbnail(
                        imagesContainer, SWT.NONE, fragmentViewPart, fragment,
                        newContent instanceof IFragmentCategory);

                // Need to calc the preferred width to get the max-wrapped
                // height of label.
                int prefWidth;

                if (fragments.size() == 1) {
                    ImageData imgData = fragment.getLocalizedImageData();
                    prefWidth = imgData != null ? imgData.width : SWT.DEFAULT;

                } else {
                    prefWidth = IMG_THUMB_WIDTH;
                }

                int height = fragThumbnail.computeLabelHeight(prefWidth);
                if (height > maxLabelHeight) {
                    maxLabelHeight = height;
                }

                fragThumbnail.setToolTipText(toolTip);
                fragThumbnail.setMenu(getMenu());
                fragmentThumbnails.add(fragThumbnail);

            } // Next fragment.

            if (newContent instanceof IFragment && fragments.size() != 0) {
                // For single image then show full size.
                ImageData imgData = fragments.get(0).getLocalizedImageData();

                imageContainerLayout.imageItemWidth = (imgData != null ? imgData.width
                        : 0)
                        + 10 + FragmentThumbnail.IMAGE_BORDER_MARGIN;
                imageContainerLayout.imageItemHeight = (imgData != null ? imgData.height
                        : 0)
                        + 10 + FragmentThumbnail.IMAGE_BORDER_MARGIN;

            } else {
                // For category selection show thumbnails
                imageContainerLayout.imageItemWidth = IMG_THUMB_WIDTH;
                imageContainerLayout.imageItemHeight = IMG_THUMB_HEIGHT;
            }

            // Add preferred height info to labels.
            // And add selection listener.
            imageContainerLayout.extraForLabel = maxLabelHeight;

            for (FragmentThumbnail fragThumbnail : fragmentThumbnails) {
                fragThumbnail.setLabelHeightHint(maxLabelHeight);

                fragThumbnail.addMouseListener(mouseSelectionListener);
                fragThumbnail.addFocusListener(allCtrlFocusListener);
                fragThumbnail.getFragmentCanvas().addFocusListener(
                        allCtrlFocusListener);

            }

        } else {
            imagesContainer.update();
        }

        imagesContainer.setLayoutDeferred(false);
        refreshLayout();

        StructuredSelection sel = null;

        // If content set was same as before then re-set selection to original
        if (originalSelection != null) {
            for (FragmentThumbnail fragmentThumbnail : fragmentThumbnails) {
                if (fragmentThumbnail.getFragment() == originalSelection) {
                    sel = new StructuredSelection(originalSelection);
                    break;
                }
            }
        }

        if (sel == null) {
            if (fragmentThumbnails.size() > 0) {
                sel = new StructuredSelection(fragmentThumbnails.get(0)
                        .getFragment());

            } else if (currentSetContent != null) {
                sel = new StructuredSelection(currentSetContent);

            } else {
                sel = new StructuredSelection();

            }
        }

        setSelection(sel);

    }

    /**
     * Get a copy of the source fragment list sorted by name.
     * 
     * @param fragments
     * @return
     */
    private List<IFragment> getSortedFragments(
            Collection<IFragmentElement> fragmentElements) {

        List<IFragment> ret = new ArrayList<IFragment>();

        if (fragmentElements != null) {
            for (IFragmentElement frag : fragmentElements) {
                if (frag instanceof IFragment) {
                    ret.add((IFragment) frag);
                }
            }

            Collections.sort(ret, new Comparator<IFragment>() {
                public int compare(IFragment o1, IFragment o2) {
                    return o1.getNameLabel().compareToIgnoreCase(
                            o2.getNameLabel());
                }
            });
        }

        return ret;
    }

    public void refreshLayout() {
        // Re-do the layout (call computeSize before in order to update the
        // stored layout info).
        Point sz = getSize();

        imageContainerLayout.computeSize(imagesContainer, sz.x, sz.y, true);

        imagesContainer.layout(true);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Composite#layout()
     */
    @Override
    public void layout() {
        super.layout();
        refreshLayout();
    }

    /**
     * Set the selection state of the thumbnails according to currentSelection
     * 
     */
    private void updateSelectionStates() {

        for (FragmentThumbnail fragThumb : fragmentThumbnails) {

            if (fragThumb.getFragment() == currSelection) {
                fragThumb.setSelected(true, haveFocus);
            } else {
                fragThumb.setSelected(false, haveFocus);
            }
        }

        return;
    }

}
