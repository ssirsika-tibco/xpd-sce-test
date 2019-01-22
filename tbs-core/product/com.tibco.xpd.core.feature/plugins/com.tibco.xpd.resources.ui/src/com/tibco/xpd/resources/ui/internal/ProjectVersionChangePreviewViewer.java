/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.resources.refactoring.ProjectVersionRefactorChange;

/**
 * Viewer for the project detail refactor change preview.
 * 
 * @author njpatel
 * 
 */
public class ProjectVersionChangePreviewViewer implements IChangePreviewViewer {
    private Composite root;
    private TextMergeViewer viewer;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#createControl(org
     * .eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        CompareConfiguration conf = new CompareConfiguration();
        conf.setLeftEditable(false);
        conf.setLeftLabel(Messages.ProjectVersionChangePreviewViewer_currentValue_column_title);
        conf.setRightEditable(false);
        conf.setRightLabel(Messages.ProjectVersionChangePreviewViewer_newValue_column_title);
        viewer = new TextMergeViewer(root, conf);
        viewer.getControl().setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true));

        Label lbl = new Label(root, SWT.WRAP);
        lbl.setText(Messages.ProjectVersionChangePreviewViewer_changeCannotBeUndone_message);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.heightHint = 20;
        data.widthHint = 150;
        data.horizontalIndent = 10;
        lbl.setLayoutData(data);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#getControl()
     */
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
    public void setInput(ChangePreviewViewerInput input) {
        if (input != null && viewer != null
                && !viewer.getControl().isDisposed()) {
            Change change = input.getChange();
            if (change instanceof ProjectVersionRefactorChange) {
                ProjectVersionRefactorChange refactorChange = (ProjectVersionRefactorChange) change;

                viewer.setInput(new DiffNode(refactorChange.getCurrentValue(),
                        refactorChange.getRefactoredValue()));
            }
        }
    }
}
