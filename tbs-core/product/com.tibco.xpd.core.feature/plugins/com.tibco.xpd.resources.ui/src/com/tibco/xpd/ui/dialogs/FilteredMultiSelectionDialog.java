/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.dialogs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Filtered item selection dialog that supports multiple selection using a split
 * dialog view.
 * 
 * @author njpatel
 */
public abstract class FilteredMultiSelectionDialog extends
        FilteredItemsSelectionDialog implements SelectionListener {

    private static final Logger LOG = XpdResourcesUIActivator.getDefault()
            .getLogger();

    private final List<Object> selectedItems;

    private Button addBtn;

    private Button removeBtn;

    private Button removeAllBtn;

    private TableViewer selectionViewer;

    private CLabel detailsLabel;

    private ILabelProvider listLabelProvider;

    private ILabelDecorator listSelectionLabelDecorator;

    private ILabelProvider detailsLabelProvider;

    private final boolean multi;

    public FilteredMultiSelectionDialog(Shell shell) {
        this(shell, true);
    }

    /**
     * Filtered multiple item selection dialog.
     * 
     * @param shell
     *            parent shell.
     * @param multi
     *            <code>true</code> for the extended multiple selection dialog
     *            and <code>false</code> for the standard single selection
     *            dialog.
     */
    public FilteredMultiSelectionDialog(Shell shell, boolean multi) {
        super(shell, multi);
        this.multi = multi;

        selectedItems = new ArrayList<Object>();
    }

    @Override
    public void setListLabelProvider(ILabelProvider listLabelProvider) {
        this.listLabelProvider = listLabelProvider;
        super.setListLabelProvider(listLabelProvider);
    }

    @Override
    public void setListSelectionLabelDecorator(
            ILabelDecorator listSelectionLabelDecorator) {
        this.listSelectionLabelDecorator = listSelectionLabelDecorator;
        super.setListSelectionLabelDecorator(listSelectionLabelDecorator);
    }

    @Override
    public void setDetailsLabelProvider(ILabelProvider detailsLabelProvider) {
        this.detailsLabelProvider = detailsLabelProvider;
        super.setDetailsLabelProvider(detailsLabelProvider);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // If multiple selection dialog then add "Selection" custom list
        if (multi) {
            SashForm root = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
            root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            Control dlgArea = super.createDialogArea(root);

            if (dlgArea != null) {
                GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true);
                gdata.minimumWidth = 200;
                dlgArea.setLayoutData(gdata);
            }

            Composite custPart = new Composite(root, SWT.NONE);
            GridLayout layout = new GridLayout(2, false);
            layout.marginWidth = 0;
            layout.marginWidth = 0;
            custPart.setLayout(layout);

            Composite rightSide = createSelectionSection(custPart);

            if (rightSide != null) {
                GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true);
                gdata.minimumWidth = 200;
                rightSide.setLayoutData(gdata);
            }

            return root;
        }

        return super.createDialogArea(parent);
    }

	/**
	 * Force reselection of last set initial selection.
	 */
    private void applyInitalElementSection() {
        Set<Object> initialSel = new LinkedHashSet<Object>();
        updateInitialSelection(initialSel);
        Field listField;
        try {
			/*
			 * Sid ACE-7602 Use recently introduced set initial elements method rather than old hacky way of using Java
			 * introspection.
			 */
            if (initialSel != null && !initialSel.isEmpty()) {
				this.setInitialElementSelections(new ArrayList<Object>(initialSel));
            } else {
                /*
                 * Ensure that there is no selection set in the list until a
                 * selection is made by user so that the OK button would be
                 * disabled
                 */
				this.setInitialElementSelections(Collections.emptyList());
            }
        } catch (Exception e) {
            // Ignore, the error will only be logged.
            LOG.error(e);
        }
    }

    protected boolean initialized = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        if (!initialized && !multi) {
            applyInitalElementSection();
            initialized = true;
        }
		/*
		 * Sid ACE-7602 Don't refresh until after we have set the initial selection (as 1st refresh sets the initial
		 * selection.
		 */
		super.refresh();
    }

    @Override
    protected void updateButtonsEnableState(IStatus status) {
        // Do nothing as the OK button should always be enabled if multi dialog
        if (!multi) {
            super.updateButtonsEnableState(status);
        }
    }

    @Override
    protected void okPressed() {
        if (multi) {
            /*
             * Force OK to follow through if multi selection - the "standard"
             * filtered selection dialog will not do anything when OK button is
             * clicked without selection in the main list
             */
            updateStatus(Status.OK_STATUS);
        }
        super.okPressed();
    }

    /**
     * Create the buttons section that appears between the filtered list and the
     * current selection viewers.
     * 
     * @param parent
     *            parent <code>Composite</code>.
     * @return <code>Composite</code> containing the buttons.
     */
    private Composite createButtonsSection(Composite parent) {
        Composite btnComposite = new Composite(parent, SWT.NONE);
        btnComposite.setLayout(new FillLayout(SWT.VERTICAL));
        // Add button
        addBtn = new Button(btnComposite, SWT.NONE);
        addBtn.setText(Messages.FilteredMultiSelectionDialog_add_button);
        addBtn.setToolTipText(Messages.FilteredMultiSelectionDialog_add_button_tooltip);
        addBtn.addSelectionListener(this);

        // Remove button
        removeBtn = new Button(btnComposite, SWT.NONE);
        removeBtn.setText(Messages.FilteredMultiSelectionDialog_remove_button);
        removeBtn
                .setToolTipText(Messages.FilteredMultiSelectionDialog_remove_button_tooltip);
        removeBtn.addSelectionListener(this);

        // Remove all button
        removeAllBtn = new Button(btnComposite, SWT.NONE);
        removeAllBtn
                .setText(Messages.FilteredMultiSelectionDialog_clear_button);
        removeAllBtn
                .setToolTipText(Messages.FilteredMultiSelectionDialog_clear_button_tooltip);
        removeAllBtn.addSelectionListener(this);

        return btnComposite;
    }

    /**
     * Create the selection section. This contains the viewer that will show the
     * current selection.
     * 
     * @param parent
     *            parent <code>Composite</code>.
     * @return <code>Composite</code> containing the selection section.
     */
    protected Composite createSelectionSection(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginLeft = 0;
        root.setLayout(layout);

        Composite buttonsSection = createButtonsSection(root);

        if (buttonsSection != null) {
            buttonsSection.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
                    false, false));
        }

        // Create selection viewer
        Composite selectionComposite = new Composite(root, SWT.NONE);
        selectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        layout = new GridLayout();
        layout.marginLeft = 5;
        layout.marginBottom = -2;
        layout.marginTop = 5;
        selectionComposite.setLayout(layout);
        Label lbl = new Label(selectionComposite, SWT.NONE);
        lbl.setText(Messages.FilteredMultiSelectionDialog_selection_label);
        selectionViewer = new TableViewer(selectionComposite);
        selectionViewer.getTable().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        selectionViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                String text =
                        listLabelProvider != null ? listLabelProvider
                                .getText(element) : null;

                if (text != null && listSelectionLabelDecorator != null) {
                    text =
                            listSelectionLabelDecorator.decorateText(text,
                                    element);
                }
                return text != null ? text : super.getText(element);
            }

            @Override
            public Image getImage(Object element) {
                return listLabelProvider != null ? listLabelProvider
                        .getImage(element) : super.getImage(element);
            }
        });
        selectionViewer.setContentProvider(new IStructuredContentProvider() {

            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement == selectedItems) {
                    return selectedItems.toArray();
                }

                return new Object[0];
            }

            @Override
            public void dispose() {
                // Nothing to do
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // Nothing to do
            }

        });
        selectionViewer.setInput(selectedItems);

        selectionViewer
                .addPostSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ISelection selection = event.getSelection();

                        if (selection instanceof IStructuredSelection) {
                            Object element =
                                    ((IStructuredSelection) selection)
                                            .getFirstElement();

                            String text =
                                    element != null
                                            && detailsLabelProvider != null ? detailsLabelProvider
                                            .getText(element) : null;
                            if (detailsLabel != null
                                    && !detailsLabel.isDisposed()) {
                                detailsLabel.setText(text != null ? text : ""); //$NON-NLS-1$

                                if (element != null
                                        && detailsLabelProvider != null)
                                    detailsLabel.setImage(detailsLabelProvider
                                            .getImage(element));
                            }
                        }
                    }

                });

        // Add initial selection
        getShell().getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                Set<Object> objs = new HashSet<Object>();
                updateInitialSelection(objs);
                selectedItems.addAll(objs);

                if (!selectedItems.isEmpty() && selectionViewer != null
                        && !selectionViewer.getControl().isDisposed()) {
                    // Update selection viewer
                    selectionViewer.refresh();
                    // Select first item in viewer
                    selectionViewer.setSelection(new StructuredSelection(
                            selectedItems.iterator().next()));
                }
            }
        });

        ViewForm viewForm =
                new ViewForm(selectionComposite, SWT.BORDER | SWT.FLAT);
        viewForm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        detailsLabel = new CLabel(viewForm, SWT.LEFT | SWT.SHADOW_NONE);
        viewForm.setContent(detailsLabel);
        return root;
    }

    /**
     * Update the initial selection. The default implementation will add all
     * items in the {@link #getInitialElementSelections() initial selection} to
     * the provided <i>selectedItems</i> list.
     * <p>
     * Subclasses may override.
     * </p>
     */
    protected void updateInitialSelection(Set<Object> selectedItems) {
        List<?> selections = getInitialElementSelections();
        if (selections != null) {
            selectedItems.addAll(selections);
        }
    }

    @Override
    protected Control createExtendedContentArea(Composite parent) {
        return null;
    }

    @Override
    public Object[] getResult() {
        // If multi selection then return objects from the "Selection" list
        if (multi) {
            return selectedItems.toArray();
        }

        return super.getResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();

        if (source == addBtn) {
            handleDoubleClick();
        } else if (source == removeBtn) {
            // Remove the selection
            IStructuredSelection selection =
                    (IStructuredSelection) selectionViewer.getSelection();

            if (selection != null) {
                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    selectedItems.remove(iter.next());
                }
            }
            // Update the selection viewer
            selectionViewer.refresh();
        } else if (source == removeAllBtn) {
            selectedItems.clear();
            selectionViewer.refresh();
        }
    }

    @Override
    protected void handleDoubleClick() {

        if (multi) {
            // Add to selection
            StructuredSelection items = getSelectedItems();
            boolean update = false;
            if (items != null) {
                for (Object item : items.toArray()) {
                    if (!selectedItems.contains(item)) {
                        selectedItems.add(item);
                        update = true;
                    }
                }
            }

            if (update) {
                selectionViewer.refresh();

                // Select first item in list if no selection made
                if (!selectedItems.isEmpty()
                        && selectionViewer.getSelection().isEmpty()) {
                    selectionViewer.setSelection(new StructuredSelection(
                            selectedItems.iterator().next()));
                }
            }
        } else {
            super.handleDoubleClick();
        }
    }

    /**
     * The class will create items filter that will show everything when
     * filtering pattern is empty.
     * <p>
     * <i>Created: 22 Apr 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    protected abstract class ShowAllWhenEmptyItemsFilter extends ItemsFilter {
        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.dialogs.FilteredItemsSelectionDialog.ItemsFilter#
         * getPattern()
         */
        @Override
        public String getPattern() {
            if ("".equals(patternMatcher.getPattern())) { //$NON-NLS-1$
                return "?"; //$NON-NLS-1$
            } else {
                return super.getPattern();
            }
        }
    };

}
