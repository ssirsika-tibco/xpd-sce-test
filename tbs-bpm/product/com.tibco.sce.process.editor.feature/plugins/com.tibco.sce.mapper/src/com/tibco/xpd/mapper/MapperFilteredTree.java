/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * This sub-class of Eclipse's FilteredTree simply adds a separator line between
 * the filter text box and the tree control.
 * <p>
 * The purpose of this is to match the line that is added across the top of the
 * mapping lines control (which is necessary to stop the mapping lines appearing
 * to be cut off in mid air).
 * 
 * @author aallway
 * @since 3.4.2 (30 Jul 2010)
 */
public class MapperFilteredTree extends FilteredTree {

    public Label separator;

    private List<ITextChangedListener> textChangedListeners =
            new ArrayList<ITextChangedListener>();

    /**
     * @param parent
     * @param treeStyle
     * @param filter
     */
    public MapperFilteredTree(Composite parent, int treeStyle,
            PatternFilter filter) {
        super(parent, treeStyle, filter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.dialogs.FilteredTree#createControl(org.eclipse.swt.widgets
     * .Composite, int)
     * 
     * This is the equivalent of FilterTree.createControl()@v3.2 Eclipse with
     * additions marked as Studio_Specific.
     */
    @Override
    protected void createControl(Composite parent, int treeStyle) {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0; // Studio_Specific
        setLayout(layout);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        if (showFilterControls) {
            filterComposite = new Composite(this, SWT.NONE);
            GridLayout filterLayout = new GridLayout(2, false);
            filterLayout.marginHeight = 0;
            filterLayout.marginWidth = 0;
            filterComposite.setLayout(filterLayout);
            filterComposite.setFont(parent.getFont());

            createFilterControls(filterComposite);
            GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
            gd.verticalIndent = 2;
            gd.horizontalIndent = 2;
            filterComposite.setLayoutData(gd);

            // Studio_Specific - add a line above tree control.
            separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
            separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            // Studio_Specific
        }

        treeComposite = new Composite(this, SWT.NONE);
        GridLayout treeCompositeLayout = new GridLayout();
        treeCompositeLayout.marginHeight = 0;
        treeCompositeLayout.marginWidth = 0;
        treeComposite.setLayout(treeCompositeLayout);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        treeComposite.setLayoutData(data);
        createTreeControl(treeComposite, treeStyle);
    }

    /**
     * 
     * Overriding this method so that the controls using this Tree control have
     * an opportunity to do things when the text in the filter text box changes.
     * 
     * @see org.eclipse.ui.dialogs.FilteredTree#doCreateRefreshJob()
     * 
     * @return
     */
    @Override
    protected WorkbenchJob doCreateRefreshJob() {
        WorkbenchJob job = super.doCreateRefreshJob();

        /*
         * TODO - Add Comment
         */
        job.addJobChangeListener(new JobChangeAdapter() {
            @Override
            public void done(IJobChangeEvent event) {
                if (!MapperFilteredTree.this.isDisposed() && filterText != null
                        && !filterText.isDisposed()) {
                    if (textChangedListeners != null) {
                        for (ITextChangedListener txtChangedListener : textChangedListeners) {
                            txtChangedListener.update(getFilterString());
                        }
                    }
                }
            }
        });
        return job;
    }

    @Override
    protected void textChanged() {
        super.textChanged();
    }

    /**
     * Adds filter text change listeners. These listeners will get a chance to
     * update their respective viewers/controls when the text in the filter text
     * box is modified.
     * 
     * @param textChangedListener
     */
    public void addFilterTextChangeListener(
            ITextChangedListener textChangedListener) {
        textChangedListeners.add(textChangedListener);
    }
}
