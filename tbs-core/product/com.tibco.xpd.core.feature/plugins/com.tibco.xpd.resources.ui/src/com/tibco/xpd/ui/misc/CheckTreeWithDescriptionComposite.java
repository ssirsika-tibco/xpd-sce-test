/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.misc;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledFormText;

import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * 
 * <p>
 * <i>Created: 14 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class CheckTreeWithDescriptionComposite extends Composite implements
        ISelectionChangedListener {
    /**
     * Provider for the <code>{@link TableWithDescriptionComposite}</code>
     * control.
     * 
     * @see TableWithDescriptionComposite
     * 
     * @author njpatel
     */
    public interface ViewerServicesProvider {

        /**
         * Get the content provider for the tree viewer
         * 
         * @return <code>IStructuredContentProvider</code>
         */
        public IStructuredContentProvider getContentProvider();

        /**
         * Get the label provider for the tree viewer
         * 
         * @return <code>ILabelProvider</code>
         */
        public ILabelProvider getLabelProvider();

        /**
         * Get the input for the tree viewer.
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
         * Get the description associated with the given element from tree
         * 
         * @param element
         *            Element selected in the tree table
         * @return Description to display in the description viewer
         */
        public String getDescription(Object element);

        /**
         * @return list of actions displayed as a button in toolbar.
         */
        public List<IAction> getToolBarActions();

        /**
         * Returns the text that will be displayed above main control.
         * 
         * @return label for main control.
         */
        public String getMainControlLabelText();
    }

    private TreeViewer treeViewer;

    private ScrolledFormText descriptionViewer;

    private final ViewerServicesProvider provider;

    private boolean isCheckboxViewer = false;

    private ToolBarManager mainControlToolBar;

    private Font headerFont;

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
    public CheckTreeWithDescriptionComposite(Composite parent,
            ViewerServicesProvider provider, int style) {
        super(parent, style);
        this.provider = provider;

        isCheckboxViewer = (style & SWT.CHECK) == SWT.CHECK;

        if (provider != null) {
            {
                GridLayout layout = new GridLayout();
                layout.marginHeight = 0;
                layout.marginWidth = 0;
                super.setLayout(layout);
            }

            FontDescriptor descriptor = JFaceResources
                    .getDialogFontDescriptor();
            descriptor = descriptor.increaseHeight(2);
            headerFont = descriptor.createFont(getDisplay());

            // Create the main part of the page - the wizard selection
            SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
            GridData gridData = new GridData(GridData.FILL_BOTH);
            // limit the width of the sash form to avoid the wizard opening very
            // wide.
            gridData.widthHint = 300;
            sashForm.setLayoutData(gridData);

            FormData formData;

            // Create the left hand side composite
            Composite leftPane = new Composite(sashForm, SWT.NONE);
            GridLayout lpLayout = new GridLayout(1, false);
            lpLayout.marginHeight = 0;
            lpLayout.marginWidth = 0;
            leftPane.setLayout(lpLayout);

            GridDataFactory topCompositeGDFactory = GridDataFactory
                    .swtDefaults().align(SWT.FILL, SWT.CENTER)
                    .grab(true, false).hint(SWT.DEFAULT, 25);

            Composite leftTopPane = new Composite(leftPane, SWT.NONE);
            topCompositeGDFactory.applyTo(leftTopPane);
            leftTopPane.setLayout(new FormLayout());

            Label mainControlLabel = new Label(leftTopPane, SWT.NONE);
            mainControlLabel.setText(provider.getMainControlLabelText());
            formData = new FormData();
            formData.left = new FormAttachment(0, 1);
            formData.bottom = new FormAttachment(100, 0);
            mainControlLabel.setLayoutData(formData);

            mainControlToolBar = createMainControlToolBar(leftTopPane);
            formData = new FormData();
            formData.right = new FormAttachment(100, -1);
            formData.bottom = new FormAttachment(100, 0);
            mainControlToolBar.getControl().setLayoutData(formData);

            treeViewer = createTreeViewer(leftPane);
            GridDataFactory mainControlGDFactory = GridDataFactory
                    .fillDefaults().grab(true, true);
            mainControlGDFactory.applyTo(treeViewer.getControl());
            treeViewer.getControl().setFont(getFont());

            // Create the right hand side composite
            Composite rightPane = new Composite(sashForm, SWT.NONE);
            GridLayout rpLayout = new GridLayout(1, false);
            rpLayout.marginHeight = 0;
            rpLayout.marginWidth = 0;

            rightPane.setLayout(rpLayout);

            Composite rightTopPane = new Composite(rightPane, SWT.NONE);
            topCompositeGDFactory.applyTo(rightTopPane);
            rightTopPane.setLayout(new FormLayout());

            Label descLabel = new Label(rightTopPane, SWT.NONE);
            descLabel
                    .setText(Messages.CheckTreeWithDescriptionComposite_description_label);
            formData = new FormData();
            formData.left = new FormAttachment(0, 1);
            formData.bottom = new FormAttachment(100, 0);
            descLabel.setLayoutData(formData);

            descriptionViewer = createDescriptionSection(rightPane);
            mainControlGDFactory.applyTo(descriptionViewer.getParent());
            descriptionViewer.getContent().setFont(getFont());
            // Set the background and foreground color of the description
            // viewer
            // the same as the table viewer
            descriptionViewer.getContent().setBackground(
                    treeViewer.getControl().getBackground());
            descriptionViewer.getContent().setForeground(
                    treeViewer.getControl().getForeground());

            /*
             * Select the first item in the table by default, unless an initial
             * selection has been provided
             */
            Object initialSelection = provider.getInitialSelection();
            if (initialSelection != null) {
                treeViewer.setSelection(new StructuredSelection(
                        initialSelection));
            } else {
                // tableViewer.getTree().select(0);
            }

            ISelection selection = treeViewer.getSelection();
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
        treeViewer.getTree().setEnabled(enabled);
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
     *             If this call is made to a non-checkbox tree viewer.
     */
    public void setAllChecked(boolean state) {
        if (isCheckboxViewer) {
            // fix to ungray select all grayed elements
            ((CheckboxTreeViewer) treeViewer).setGrayedElements(new Object[0]);
            ((CheckboxTreeViewer) treeViewer).setAllChecked(state);
        } else {
            throw new IllegalArgumentException("Viewer is not checkbox viewer."); //$NON-NLS-1$
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
    public void setCheckedElements(Object[] elements) {
        if (isCheckboxViewer) {
            ((CheckboxTreeViewer) treeViewer).setCheckedElements(elements);
        } else {
            throw new IllegalArgumentException("Viewer is not checkbox viewer."); //$NON-NLS-1$
        }
    }

    /**
     * Expands elements in the tree.
     * 
     * @param elements
     *            elements to be expanded.
     */
    public void setExpandedElements(Object[] elements) {
        for (Object element : elements) {
            ((TreeViewer) treeViewer).reveal(element);
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

        if (isCheckboxViewer) {
            elements = ((CheckboxTreeViewer) treeViewer).getCheckedElements();
        } else {
            throw new IllegalArgumentException("Viewer is not checkbox viewer."); //$NON-NLS-1$
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
        if (isCheckboxViewer) {
            return ((CheckboxTreeViewer) treeViewer).getChecked(element);
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
        if (treeViewer != null && isCheckboxViewer) {
            ((CheckboxTreeViewer) treeViewer).addCheckStateListener(listener);
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
        if (treeViewer != null && isCheckboxViewer) {
            ((CheckboxTreeViewer) treeViewer)
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
        if (treeViewer != null) {
            treeViewer.addSelectionChangedListener(listener);
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
        if (treeViewer != null) {
            treeViewer.removeSelectionChangedListener(listener);
        }
    }

    /**
     * Get the selection from the table viewer.
     * 
     * @return <code>ISelection</code> if items are selected in the table, null
     *         if not or table not accessible.
     */
    public ISelection getSelection() {
        ISelection selection = null;

        if (treeViewer != null) {
            selection = treeViewer.getSelection();
        }

        return selection;
    }

    @Override
    public void setLayout(Layout layout) {
        // Do nothing as this class will control the layout
    }

    @Override
    public void dispose() {

        if (headerFont != null) {
            headerFont.dispose();
            headerFont = null;
        }

        removeSelectionChangedListener(this);
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
     * org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        // Update the description
        if (event.getSelection() instanceof IStructuredSelection) {
            updateDescription((IStructuredSelection) event.getSelection());
        }
    }

    /**
     * Update the description view with description of the given selected
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
        ftext.setColor("gray", parent.getShell().getDisplay().getSystemColor( //$NON-NLS-1$
                SWT.COLOR_DARK_GRAY));

        ftext.setFont("header", headerFont); //$NON-NLS-1$
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
     * Create the tree viewer.
     * 
     * @param parent
     * @return <code>CheckboxTableViewer</code>
     */
    private TreeViewer createTreeViewer(Composite parent) {
        TreeViewer viewer;

        if (isCheckboxViewer) {
            viewer = new ContainerCheckedTreeViewer(parent, SWT.BORDER);
        } else {
            viewer = new TreeViewer(parent);
        }

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

    public StructuredViewer getViewer() {
        return treeViewer;
    }

    /**
     * Create the button that toggles categories.
     * 
     * @param parent
     *            parent <code>Composite</code> of toolbar button
     */
    private ToolBarManager createMainControlToolBar(Composite parent) {
        ToolBarManager mainControlToolBar = new ToolBarManager(SWT.FLAT
                | SWT.HORIZONTAL);
        mainControlToolBar.createControl(parent);
        for (IAction action : provider.getToolBarActions()) {
            mainControlToolBar.add(action);
        }
        mainControlToolBar.update(false);
        return mainControlToolBar;
    }
}
