/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Preview viewer to display the potential changes in the references to the
 * element being refactored.
 * 
 * @author mtorres
 */
public abstract class AbstractReferenceChangePreviewViewer implements
        IChangePreviewViewer {

    private Composite root;

    private TextMergeViewer viewer;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#createControl(org
     * .eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        CompareConfiguration conf = new CompareConfiguration();
        conf.setLeftEditable(false);
        conf.setLeftLabel(Messages.ReferenceChangePreviewViewer_CurrentValue);
        conf.setRightEditable(false);
        conf.setRightLabel(Messages.ReferenceChangePreviewViewer_NewValue);
        viewer = new TextMergeViewer(root, conf);
        viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#getControl()
     */
    @Override
    public Control getControl() {
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#setInput(org.eclipse
     * .ltk.ui.refactoring.ChangePreviewViewerInput)
     */
    @Override
    public abstract void setInput(ChangePreviewViewerInput input);

    protected TextMergeViewer getViewer() {
        return viewer;
    }
}
