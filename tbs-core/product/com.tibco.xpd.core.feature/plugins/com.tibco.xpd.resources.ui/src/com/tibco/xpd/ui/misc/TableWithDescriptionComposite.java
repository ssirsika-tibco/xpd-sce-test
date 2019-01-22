/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.misc;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledFormText;

/**
 * A <code>Composite</code> control that provides with a sash-separated table
 * viewer and a description viewer. The description viewer will display
 * description (that can contain HTML) of the selected item in the table viewer.
 * <p>
 * If the style is set to <code>{@link SWT#NONE}</code> then a standard table
 * will be used. If the style is set to <code>{@link SWT#CHECK}</code> then a
 * checkbox table will be provided.
 * </p>
 * <p>
 * Do not set the layout of this composite - this is set to
 * <code>GridLayout</code>.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class TableWithDescriptionComposite extends Composite implements
        ISelectionChangedListener {

    /**
     * Provider for the <code>{@link TableWithDescriptionComposite}</code>
     * control.
     * 
     * @see TableWithDescriptionComposite
     * 
     * @author njpatel
     */
    public interface ICheckTableWithDescriptionInputProvider {

        /**
         * Get the content provider for the checkbox table viewer
         * 
         * @return <code>IStructuredContentProvider</code>
         */
        public IStructuredContentProvider getContentProvider();

        /**
         * Get the label provider for the checkbox table viewer
         * 
         * @return <code>ILabelProvider</code>
         */
        public ILabelProvider getLabelProvider();

        /**
         * Get the input for the checkbox table viewer.
         * 
         * @return Input for the table viewer
         */
        public Object getInput();

        /**
         * Get the initial selection to be made in the table
         * 
         * @return the object to select in the table
         */
        public Object getInitialSelection();

        /**
         * Get the description associated with the given element from the
         * checkbox table
         * 
         * @param element
         *            Element selected in the checkbox table
         * @return Description to display in the description viewer
         */
        public String getDescription(Object element);
    }

    private TableViewer tableViewer;

    private ScrolledFormText descriptionViewer;

    private final ICheckTableWithDescriptionInputProvider provider;

    private boolean checkTableViewer = false;

    /**
     * Constructor for the table with description composite.
     * 
     * @param parent
     *            The parent <code>Composite</code>
     * @param provider
     *            The input provider
     * @param style
     *            <code>{@link SWT#NONE}</code> for a table,
     *            <code>{@link SWT#CHECK}</code> for a checkbox table.
     */
    public TableWithDescriptionComposite(Composite parent,
            ICheckTableWithDescriptionInputProvider provider, int style) {
        super(parent, style);
        this.provider = provider;

        checkTableViewer = (style & SWT.CHECK) == SWT.CHECK;

        if (provider != null) {
            GridLayout layout = new GridLayout();
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            super.setLayout(layout);

            // Create the main part of the page - the wizard selection
            SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
            GridData gridData = new GridData(GridData.FILL_BOTH);
            // limit the width of the sash form to avoid the wizard opening very
            // wide.
            gridData.widthHint = 300;
            sashForm.setLayoutData(gridData);

            // Create the left hand side checkbox table viewer
            tableViewer = createTableViewer(sashForm);
            tableViewer.getControl().setFont(getFont());

            descriptionViewer = createDescriptionSection(sashForm);
            descriptionViewer.getContent().setFont(getFont());
            // Set the background and foreground colour of the description
            // viewer
            // the same as the table viewer
            descriptionViewer.getContent().setBackground(
                    tableViewer.getControl().getBackground());
            descriptionViewer.getContent().setForeground(
                    tableViewer.getControl().getForeground());

            /*
             * Select the first item in the table by default, unless an initial
             * selection has been provided
             */
            Object initialSelection = provider.getInitialSelection();
            if (initialSelection != null) {
                tableViewer.setSelection(new StructuredSelection(
                        initialSelection));
            } else {
                tableViewer.getTable().select(0);
            }

            ISelection selection = tableViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                updateDescription((IStructuredSelection) selection);
            }
            // Add this class as the selection listener of the table viewer
            addSelectionChangedListener(this);

        } else {
            // No provider available
            throw new NullPointerException("provider is null."); //$NON-NLS-1$
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        tableViewer.getTable().setEnabled(enabled);
        descriptionViewer.setEnabled(enabled);
        // Need to redraw to refresh the description viewer - otherwise it will
        // not grey out when disabled
        descriptionViewer.getContent().redraw();
        super.setEnabled(enabled);
    }

    /**
     * Set all items checked if using the checkbox table viewer. NOTE: This
     * should be only used with a checkbox table viewer.
     * 
     * @param state
     *            <code>true</code> if the element should be checked, and
     *            <code>false</code> if it should be unchecked
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public void setAllChecked(boolean state) {
        if (checkTableViewer) {
            ((CheckboxTableViewer) tableViewer).setAllChecked(state);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Set the given objects as checked in the check box table viewer. All other
     * elements will be unchecked. NOTE: This should be only used with a
     * checkbox table viewer.
     * 
     * @param elements
     *            Elements to check in the checkbox table viewer
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public void SetCheckedElements(Object[] elements) {
        if (checkTableViewer) {
            ((CheckboxTableViewer) tableViewer).setCheckedElements(elements);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Get the checked elements in the table. NOTE: This should only be used for
     * a checkbox table viewer.
     * 
     * @return Checked elements.
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public Object[] getCheckedElements() {
        Object[] elements = new Object[0];

        if (checkTableViewer) {
            elements = ((CheckboxTableViewer) tableViewer).getCheckedElements();
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }

        return elements;
    }

    /**
     * Check if the given element is checked. NOTE: This should be only used
     * with a checkbox table viewer.
     * 
     * @param element
     * @return
     */
    public boolean getChecked(Object element) {
        if (checkTableViewer) {
            return ((CheckboxTableViewer) tableViewer).getChecked(element);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Add a check state listener to the table viewer. This will only apply if
     * the table viewer is set with the {@link SWT#CHECK} style.
     * 
     * @see CheckboxTableViewer#addCheckStateListener(ICheckStateListener)
     * 
     * @param listener
     */
    public void addCheckStateListener(ICheckStateListener listener) {
        if (tableViewer != null && checkTableViewer) {
            ((CheckboxTableViewer) tableViewer).addCheckStateListener(listener);
        }
    }

    /**
     * Remove a check state listener to the table viewer. This will only apply
     * if the table viewer is set with the {@link SWT#CHECK} style.
     * 
     * @see CheckboxTableViewer#removeCheckStateListener(ICheckStateListener)
     * 
     * @param listener
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        if (tableViewer != null && checkTableViewer) {
            ((CheckboxTableViewer) tableViewer)
                    .removeCheckStateListener(listener);
        }
    }

    /**
     * Add a selection change listener to the table viewer.
     * 
     * @see TableViewer#addSelectionChangedListener(ISelectionChangedListener)
     * 
     * @param listener
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        if (tableViewer != null) {
            tableViewer.addSelectionChangedListener(listener);
        }
    }

    /**
     * Remove a selection change listener to the table viewer.
     * 
     * @see TableViewer#removeSelectionChangedListener(ISelectionChangedListener)
     * 
     * @param listener
     */
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        if (tableViewer != null) {
            tableViewer.removeSelectionChangedListener(listener);
        }
    }

    /**
     * Get the selection from the table viewer.
     * 
     * @return <code>ISelection</code> if items are selected in the table,
     *         null if not or table not accessible.
     */
    public ISelection getSelection() {
        ISelection selection = null;

        if (tableViewer != null) {
            selection = tableViewer.getSelection();
        }

        return selection;
    }

    @Override
    public void setLayout(Layout layout) {
        // Do nothing as this class will control the layout
    }

    @Override
    public void dispose() {

        removeSelectionChangedListener(this);
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        // Update the description
        if (event.getSelection() instanceof IStructuredSelection) {
            updateDescription((IStructuredSelection) event.getSelection());
        }
    }

    /**
     * Update the description view with description of the given selectde
     * element in the table
     * 
     * @param selection
     */
    private void updateDescription(IStructuredSelection selection) {
        Object element = selection.getFirstElement();

        if (element != null) {
            String description = provider.getDescription(element);

            descriptionViewer.setText(description != null ? description : ""); //$NON-NLS-1$
        }
    }

    /**
     * Create the description section
     * 
     * @param parent
     * @return <code>ScrolledFormText</code>
     */
    private ScrolledFormText createDescriptionSection(Composite parent) {
        Composite container = new Composite(parent, SWT.BORDER);
        FillLayout fLayout = new FillLayout();
        fLayout.marginHeight = 1;
        fLayout.marginWidth = 1;
        container.setLayout(fLayout);
        ScrolledFormText scrolledForm = new ScrolledFormText(container,
                SWT.V_SCROLL | SWT.H_SCROLL, false);
        FormText ftext = new FormText(scrolledForm, SWT.NONE);
        scrolledForm.setFormText(ftext);
        scrolledForm.setExpandHorizontal(true);
        scrolledForm.setExpandVertical(true);

        ftext.marginWidth = 2;
        ftext.marginHeight = 2;
        ftext
                .setHyperlinkSettings(new HyperlinkSettings(container
                        .getDisplay()));
        scrolledForm.setText(""); //$NON-NLS-1$

        return scrolledForm;
    }

    /**
     * Create the checkbox table viewer
     * 
     * @param parent
     * @return <code>CheckboxTableViewer</code>
     */
    private TableViewer createTableViewer(Composite parent) {
        TableViewer viewer;

        if (checkTableViewer) {
            viewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
        } else {
            viewer = new TableViewer(parent);
        }

        viewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

        IStructuredContentProvider contentProvider = provider
                .getContentProvider();

        if (contentProvider != null) {
            viewer.setContentProvider(contentProvider);
        } else {
            throw new NullPointerException("content provider is null."); //$NON-NLS-1$
        }

        ILabelProvider labelProvider = provider.getLabelProvider();

        if (labelProvider != null) {
            viewer.setLabelProvider(labelProvider);
        } else {
            throw new NullPointerException("label provider is null."); //$NON-NLS-1$
        }

        Object input = provider.getInput();

        if (input != null) {
            viewer.setInput(input);
        } else {
            throw new NullPointerException("input is null."); //$NON-NLS-1$
        }

        return viewer;
    }

}
