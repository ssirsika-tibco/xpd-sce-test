/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * Base class for composite encapsulating tree column viewer with an
 * accompanying button bar.
 * <p>
 * <i>Created: 15 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class BaseTreeControl extends BaseColumnViewerControl {

    protected Tree tree;

    /**
     * @param parent
     * @param toolkit
     */
    public BaseTreeControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     */
    public BaseTreeControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {
        super(parent, toolkit, viewerInput);
    }

    /**
     * 
     * @param parent
     * @param toolkit
     * @param viewerInput
     * @param createContents
     */
    public BaseTreeControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput, boolean createContents) {
        super(parent, toolkit, viewerInput, createContents);
    }

    /** {@inheritDoc} */
    @Override
    protected ColumnViewer createColumnViewer(Composite parent,
            XpdToolkit toolkit) {
        /*
         * Sid XPD-958: Should not have created tree with parent of "this" - the
         * parent is passed an should not be ignored (and is now different from
         * "this" anyway.
         */
        tree =
                toolkit.createTree(parent, SWT.MULTI | SWT.V_SCROLL
                        | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.BORDER, this
                        .getClass().getName());
        return new TreeViewer(tree);
    }

    public TreeViewer getTreeViewer() {
        return (TreeViewer) getViewer();
    }

    /**
     * Default implementation for tree control doesn't add any column.
     * {@inheritDoc}
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * setColumnProportions(float[])
     */
    @Override
    public void setColumnProportions(float[] columnProportions) {
        TreeColumn[] columns = tree.getColumns();

        if (columns.length != columnProportions.length) {
            throw new IllegalArgumentException(
                    "Must provide proportions for every column in tree!"); //$NON-NLS-1$
        }

        TreeColumnLayout treeColumnLayout = new TreeColumnLayout();

        for (int i = 0; i < columns.length; i++) {
            TreeColumn column = columns[i];

            ColumnLayoutData cld =
                    new ColumnWeightData((int) (columnProportions[i] * 100),
                            10, true);

            treeColumnLayout.setColumnData(column, cld);
        }

        Composite treeContainer = getColumnViewerContainer();
        treeContainer.setLayout(treeColumnLayout);
        treeContainer.layout(true, true);
        return;
    }

}
