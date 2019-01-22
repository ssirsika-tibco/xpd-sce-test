package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * Action to Expand or collapse all content of right/left/ancestor viewers
 * 
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class ExpandAllCompareNodesAction extends Action {

    private boolean expand;

    private XpdCompareMergeViewer mergeViewer;

    public ExpandAllCompareNodesAction(
            XpdCompareMergeViewer mergeViewer, boolean expand) {
        super(
                "", //$NON-NLS-1$
                expand ? XpdResourcesUIActivator
                        .getImageDescriptor(XpdResourcesUIConstants.ICON_COMPARE_EXPAND_ALL)
                        : XpdResourcesUIActivator
                                .getImageDescriptor(XpdResourcesUIConstants.ICON_COMPARE_COLLAPSE_ALL));

        this.mergeViewer = mergeViewer;

        this.expand = expand;

        if (expand) {
            setText("Expand All");
            setToolTipText("Expand all content");
        } else {
            setText("Collapse All");
            setToolTipText("Collapse all content");
        }
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        try {
            /* Set all the listeners to ignore the upcoming events. */
            mergeViewer.getSynchSelectionListener().setIgnoreEvents(true);
            mergeViewer.getSynchExpandListener().setIgnoreEvents(true);
            mergeViewer.getSynchScrollPosListener().setIgnoreEvents(true);

            /* Stop redrawing trees */
            mergeViewer.getLeftViewer().getTree().setRedraw(false);
            mergeViewer.getRightViewer().getTree().setRedraw(false);
            mergeViewer.getAncestorViewer().getTree().setRedraw(false);

            expandCollapseViewer(mergeViewer.getLeftViewer());
            expandCollapseViewer(mergeViewer.getRightViewer());
            expandCollapseViewer(mergeViewer.getAncestorViewer());

        } finally {
            /*
             * Put everything back how it should be.
             */
            mergeViewer.getLeftViewer().getTree().setRedraw(true);
            mergeViewer.getRightViewer().getTree().setRedraw(true);
            mergeViewer.getAncestorViewer().getTree().setRedraw(true);

            mergeViewer.getSynchSelectionListener().setIgnoreEvents(false);
            mergeViewer.getSynchExpandListener().setIgnoreEvents(false);
            mergeViewer.getSynchScrollPosListener().setIgnoreEvents(false);
        }
        return;
    }

    /**
     * @param viewer
     */
    private void expandCollapseViewer(MergeContentTreeViewer viewer) {
        // viewer.setSelection(new StructuredSelection());
        if (expand) {
            viewer.expandAll();
        } else {
            viewer.collapseAll();
        }
        TreeItem[] items = viewer.getTree().getItems();
        if (items != null && items.length > 0) {
            viewer.getTree().setTopItem(items[0]);
        }
    }
}