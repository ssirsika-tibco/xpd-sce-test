package com.tibco.xpd.resources.ui.compare.viewer.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectInternalPropertiesNode;
import com.tibco.xpd.resources.ui.compare.viewer.Messages;
import com.tibco.xpd.resources.ui.compare.viewer.XpdCompareMergeViewer;

/**
 * Action to toggle show internal properties.
 * 
 * 
 * @author aallway
 * @since 21 Oct 2010
 */
public class ShowInternalPropertiesAction extends Action {

    /**
     * 
     */
    private final XpdCompareMergeViewer mergeViewer;

    public ShowInternalPropertiesAction(
            XpdCompareMergeViewer xpdCompareNodeContentMergeViewer) {
        super(
                "", //$NON-NLS-1$
                XpdResourcesUIActivator
                        .getImageDescriptor(XpdResourcesUIConstants.ICON_INTERNAL_PROPS_COMPARENODE));
        mergeViewer = xpdCompareNodeContentMergeViewer;

        setText(Messages.XpdCompareNodeContentMergeViewer_ShowInternalProperties_button);
        setToolTipText(Messages.XpdCompareNodeContentMergeViewer_ShowInternalProperties_tooltip);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        MergeContentTreeContentProvider contentProvider =
                (MergeContentTreeContentProvider) mergeViewer.getLeftViewer()
                        .getContentProvider();

        boolean newState = !contentProvider.isExpandInternalProperties();

        contentProvider.setExpandInternalProperties(newState);

        contentProvider =
                (MergeContentTreeContentProvider) mergeViewer.getRightViewer()
                        .getContentProvider();

        contentProvider.setExpandInternalProperties(newState);

        contentProvider =
                (MergeContentTreeContentProvider) mergeViewer
                        .getAncestorViewer().getContentProvider();

        contentProvider.setExpandInternalProperties(newState);

        mergeViewer.getLeftViewer().refresh();
        mergeViewer.getRightViewer().refresh();
        mergeViewer.getAncestorViewer().refresh();

        /* Expand any InternalProperties nodes. */
        if (newState) {
            TreeItem[] items = mergeViewer.getLeftViewer().getTree().getItems();
            if (items != null) {
                for (TreeItem treeItem : items) {
                    if (treeItem.getData() instanceof EObjectInternalPropertiesNode) {
                        mergeViewer.getLeftViewer().expandToLevel(treeItem
                                .getData(),
                                1);
                    }
                }
            }

            items = mergeViewer.getRightViewer().getTree().getItems();
            if (items != null) {
                for (TreeItem treeItem : items) {
                    if (treeItem.getData() instanceof EObjectInternalPropertiesNode) {
                        mergeViewer.getRightViewer().expandToLevel(treeItem
                                .getData(),
                                1);
                    }
                }
            }

            items = mergeViewer.getAncestorViewer().getTree().getItems();
            if (items != null) {
                for (TreeItem treeItem : items) {
                    if (treeItem.getData() instanceof EObjectInternalPropertiesNode) {
                        mergeViewer.getAncestorViewer().expandToLevel(treeItem
                                .getData(),
                                1);
                    }
                }
            }
        }

        return;
    }
}