/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

/**
 * AbstractProjectExplorerQuickSearchContribution
 * <p>
 * Abstract <code>com.tibco.xpd.resources.ui.quickSearchContribution</code>
 * extension point contribution specifically for contribution of additional
 * project explorer searchable content.
 * </p>
 * <p>
 * Whilst the subclass must still create their own content and label provider,
 * this abstract class provides standard selectAndReveal() facitlity common to
 * all project explorer content.
 * </p>
 * 
 * @author aallway
 * @since 3.1
 */
public abstract class AbstractProjectExplorerQuickSearchContribution extends
        AbstractQuickSearchPopupContribution {

    public static final String PROJEXPLORER_VIEWID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * Just in case the elements contributed to the project explorer quick
     * search are <i>not actually elements within the explorer content</i> (for
     * instance the searchable content may be reference from an indexing service
     * to objects in the explorer) this method allows the sub-class to exchange
     * the given searchable content element to a project explorer content
     * element for selection.
     * 
     * @param searchableContentElement
     */
    protected abstract Object getProjectExplorerSelectionElement(
            Object searchableContentElement);

    @Override
    public Rectangle selectAndReveal(IWorkbenchPartReference partRef,
            Object element) {
        Rectangle revealRect = null;

        element = getProjectExplorerSelectionElement(element);
        if (element != null) {
            IWorkbenchPart viewPart = partRef.getPart(false);

            if (viewPart instanceof CommonNavigator) {
                CommonViewer commonViewer =
                        ((CommonNavigator) viewPart).getCommonViewer();
                if (commonViewer != null) {
                    // Set the selection and reveal it.
                    commonViewer.setSelection(new StructuredSelection(element),
                            true);

                    // Caclulate where the revealed item is and return it so
                    // popup
                    // can move out of way if it's covering it.
                    Tree tree = commonViewer.getTree();
                    if (tree != null && !tree.isDisposed()) {
                        TreeItem[] selarr = tree.getSelection();
                        if (selarr != null && selarr.length == 1
                                && selarr[0] != null) {
                            revealRect = selarr[0].getBounds();
                            if (revealRect != null) {
                                Rectangle treeBnds = tree.getBounds();
                                Point tp =
                                        tree.toDisplay(treeBnds.x, treeBnds.y);
                                treeBnds.x = tp.x;
                                treeBnds.y = tp.y;

                                Point p =
                                        tree.toDisplay(revealRect.x,
                                                revealRect.y);
                                revealRect.x = p.x;
                                revealRect.y = p.y;

                                if (treeBnds.intersects(revealRect)) {
                                    revealRect =
                                            treeBnds.intersection(revealRect);
                                }
                            }
                        }
                    }
                }
            }
        }
        return revealRect;
    }

}
