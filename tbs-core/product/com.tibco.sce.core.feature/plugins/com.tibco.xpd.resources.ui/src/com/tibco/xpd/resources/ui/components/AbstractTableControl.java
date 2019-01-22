/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;

/**
 * A composite that shows a table with action buttons. This uses the new 3.3
 * JFACE table creation method using {@link EditingSupport} for each columns.
 * 
 * @author njpatel
 * @deprecated Use either {@link BaseTableControl} or {@link BaseTreeControl}
 *             instead.
 */
@Deprecated
public abstract class AbstractTableControl extends Composite {

    private TableViewer tableViewer;

    private final TransactionalAdapterFactoryLabelProvider modelLabelProvider;

    private final Map<ImageDescriptor, Image> imageMap;

    private final List<ToolItemAction> toolActions;

    // private TableWithButtons tabWithButtons;

    /**
     * A table control with buttons.
     * 
     * @param parent
     *            parent control
     * @param toolkit
     *            form toolkit
     * @param style
     *            {@link SWT} style
     */
    public AbstractTableControl(Composite parent, XpdToolkit toolkit, int style) {
        this(parent, toolkit, style, true);
    }

    /**
     * A table control with buttons.
     * 
     * @param parent
     *            parent control
     * @param toolkit
     *            form toolkit
     * @param style
     *            {@link SWT} style
     * @param createContent
     *            <code>true</code>if the content should be created immediately,
     *            <code>false</code> if the client is responsible for calling
     *            {@link #createContent(Composite, int, XpdToolkit)}.
     */
    protected AbstractTableControl(Composite parent, XpdToolkit toolkit,
            int style, boolean createContent) {
        super(parent, style);

        modelLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
        imageMap = new HashMap<ImageDescriptor, Image>();
        toolActions = new ArrayList<ToolItemAction>();

        setBackground(parent.getBackground());
        if (createContent) {
            createContent(this, style, toolkit);
        }
    }

    /**
     * Get the model adapter factory label provider.
     * 
     * @return
     */
    protected TransactionalAdapterFactoryLabelProvider getModelLabelProvider() {
        return modelLabelProvider;
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        // if (tabWithButtons != null) {
        // tabWithButtons.setBackground(color);
        // }
    }

    /**
     * Create the content of the <code>Composite</code>.
     * 
     * @param parent
     *            parent control
     * @param style
     *            style
     * @param toolkit
     *            form toolkit
     */
    protected void createContent(Composite parent, int style, XpdToolkit toolkit) {
        // tabWithButtons = new TableWithButtons(toolkit, parent, style);
        // tabWithButtons.createControl();
        // tableViewer = tabWithButtons.getViewer();
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        setLayout(layout);

        if ((style & SWT.BORDER) == 0) {
            style |= SWT.BORDER;
        }
        tableViewer = new TableViewer(this, style);
        tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));
        toolkit.adapt(tableViewer.getTable());
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);
        // Add columns
        addColumns(tableViewer);
        // Add actions
        // addActions(tabWithButtons);
        // tabWithButtons.getActionsManager().update(true);

        addSelectionChangeListener(tableViewer);

        /*
         * SCF-231: Dispose the table control to ensure any SWT resources are
         * released.
         */
        parent.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent e) {
                /*
                 * Don't call AbstractTableControl#dispose() method from here as
                 * it crashes studio on linux. (Also see: SCF-248)
                 */
                if (!isDisposed()) {
                    modelLabelProvider.dispose();

                    for (Image img : imageMap.values()) {
                        img.dispose();
                    }
                    imageMap.clear();
                }
            }
        });

        tableViewer.getControl().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                for (ToolItemAction action : toolActions) {
                    if (action.isEnabled()
                            && action.getAccelerator() == e.keyCode
                                    + e.stateMask) {
                        action.getAction().run();
                        e.doit = false;
                        break;
                    }
                }
            }
        });

        ToolBar bar = addToolbar(this);
        if (bar != null) {
            toolkit.adapt(bar);
            bar.setLayoutData(new GridData(SWT.TOP, SWT.TOP, false, true));
        }

        enableTraversal();
    }

    /**
     * Enable table traversal.
     */
    private void enableTraversal() {
        if (tableViewer != null && !tableViewer.getControl().isDisposed()) {
            TableViewerEditor.create(tableViewer,
                    new TableViewerFocusCellManager(tableViewer,
                            new FocusCellAndRowHighlighter(tableViewer)),
                    new ColumnActivationStrategy(tableViewer),
                    ColumnViewerEditor.KEYBOARD_ACTIVATION
                            | ColumnViewerEditor.TABBING_HORIZONTAL
                            | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
                            | ColumnViewerEditor.TABBING_VERTICAL);
        }
    }

    /**
     * Add columns to the table.
     * 
     * @see #createColumn(TableViewer, int, String, int)
     * @param viewer
     */
    protected abstract void addColumns(TableViewer viewer);

    /**
     * Get table {@link ViewerAction}s. These actions will be displayed on the
     * right hand side of the table. The default implementation of this calls on
     * the following methods to provide the standard expected actions.
     * Subclasses may override if they wish to provide their own set of actions:
     * <ul>
     * <li>{@link #getAddAction(TableViewer)} - to add a row to the table</li>
     * <li>{@link #getDeleteAction(TableViewer)} - to delete a row from the
     * table</li>
     * <li>{@link #getMoveUpAction(TableViewer)} - to move a row up the table</li>
     * <li>{@link #getMoveDownAction(TableViewer)} - to move a row down the
     * table</li>
     * </ul>
     * 
     * @param viewer
     *            table viewer
     * @return collection of <code>ViewerAction</code>s, <code>null</code> or
     *         empty if no actions required.
     */
    protected Collection<ViewerAction> getActions(TableViewer viewer) {
        List<ViewerAction> actions = new ArrayList<ViewerAction>();

        // Get ADD action
        ViewerAction action = getAddAction(viewer);
        if (action != null) {
            actions.add(action);
        }

        // Get DELETE action
        action = getDeleteAction(viewer);
        if (action != null) {
            actions.add(action);
        }

        // Get MOVE UP action
        action = getMoveUpAction(viewer);
        if (action != null) {
            actions.add(action);
        }

        // Get MOVE DOWN action
        action = getMoveDownAction(viewer);
        if (action != null) {
            actions.add(action);
        }

        return actions;
    }

    /**
     * Get the ADD column action for this table. Default implementation returns
     * <code>null</code>, subclasses may extend to provide the action.
     * 
     * @param viewer
     *            table viewer
     * @return {@link TableAddAction} or <code>null</code> if no ADD action is
     *         required.
     */
    protected TableAddAction getAddAction(TableViewer viewer) {
        return null;
    }

    /**
     * Get the DELETE column action for this table. Default implementation
     * returns <code>null</code>, subclasses may extend to provide the action.
     * 
     * @param viewer
     *            table viewer
     * @return {@link TableDeleteAction} or <code>null</code> if no DELETE
     *         action is required.
     */
    protected TableDeleteAction getDeleteAction(TableViewer viewer) {
        return null;
    }

    /**
     * Get the MOVE UP column action for this table. Default implementation
     * returns <code>null</code>, subclasses may extend to provide the action.
     * 
     * @param viewer
     *            table viewer
     * @return {@link TableMoveUpAction} or <code>null</code> if no MOVE UP
     *         action is required.
     */
    protected TableMoveUpAction getMoveUpAction(TableViewer viewer) {
        return null;
    }

    /**
     * Get the MOVE DOWN column action for this table. Default implementation
     * returns <code>null</code>, subclasses may extend to provide the action.
     * 
     * @param viewer
     *            table viewer
     * @return {@link TableMoveDownAction} or <code>null</code> if no MOVE DOWN
     *         action is required.
     */
    protected TableMoveDownAction getMoveDownAction(TableViewer viewer) {
        return null;
    }

    public static TableViewerColumn createColumn(TableViewer viewer, int style,
            String label, int width) {
        TableViewerColumn column = new TableViewerColumn(viewer, style);
        column.getColumn().setText(label);
        column.getColumn().setWidth(width);
        return column;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Set the elements to display in the table.
     * 
     * @param elements
     */
    public void setElements(Collection<?> elements) {
        tableViewer.setInput(elements);
    }

    /**
     * Get the table viewer being managed by this {@link Composite}.
     * 
     * @return TableViewer.
     */
    public TableViewer getTableViewer() {
        return tableViewer;
    }

    /**
     * Add a tool bar. This will contain a tool item for each registered action.
     * 
     * @param viewer
     * @param bar
     */
    private ToolBar addToolbar(Composite parent) {
        ToolBar bar = null;
        if (parent != null) {
            /*
             * Add actions
             */
            Collection<ViewerAction> actions = getActions(tableViewer);

            if (actions != null && !actions.isEmpty()) {
                bar = new ToolBar(parent, SWT.VERTICAL);
                for (ViewerAction action : actions) {
                    // Add selection change listner
                    tableViewer.addSelectionChangedListener(action);
                    ToolItemAction itemAction = new ToolItemAction(action);
                    itemAction.createToolItem(bar);
                    toolActions.add(itemAction);
                }
            }
        }
        return bar;
    }

    /**
     * Adds the selection change listener to the viewer that will update all
     * actions with the selection.
     * 
     * @param viewer
     */
    private void addSelectionChangeListener(TableViewer viewer) {
        if (viewer != null) {
            viewer.addSelectionChangedListener(new ISelectionChangedListener() {
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    ISelection selection = event.getSelection();
                    if (selection instanceof IStructuredSelection) {
                        for (ToolItemAction action : toolActions) {
                            action.updateSelection((IStructuredSelection) selection);
                        }
                    }
                }
            });
        }
    }

    private class ToolItemAction implements IPropertyChangeListener,
            SelectionListener {
        private final ViewerAction action;

        private ToolItem toolItem;

        public ToolItemAction(ViewerAction action) {
            this.action = action;
            action.addPropertyChangeListener(this);
        }

        public ViewerAction getAction() {
            return action;
        }

        public boolean isEnabled() {
            return action != null && action.isEnabled();
        }

        public void updateSelection(IStructuredSelection selection) {
            action.selectionChanged(selection);
        }

        public int getAccelerator() {
            return action != null ? action.getAccelerator() : 0;
        }

        public ToolItem createToolItem(ToolBar bar) {
            toolItem = new ToolItem(bar, SWT.PUSH);
            toolItem.setEnabled(action.isEnabled());
            String label = action.getText();
            ImageDescriptor descriptor = action.getImageDescriptor();

            // If there is an image then add image, otherwise just add
            // text
            if (descriptor != null) {
                Image img = imageMap.get(descriptor);
                if (img == null) {
                    img = descriptor.createImage();
                    imageMap.put(descriptor, img);
                }
                toolItem.setImage(img);
            } else if (label != null) {
                toolItem.setText(label);
            }
            if (action.getToolTipText() != null) {
                toolItem.setToolTipText(action.getToolTipText());
            }

            toolItem.addSelectionListener(this);

            return toolItem;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (toolItem != null) {
                if (event.getProperty() == Action.ENABLED) {
                    toolItem.setEnabled((Boolean) (event.getNewValue() instanceof Boolean ? event
                            .getNewValue() : false));
                }
            }
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            // Do nothing
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (action != null) {
                action.run();
            }
        }
    }

    private class ColumnActivationStrategy extends
            ColumnViewerEditorActivationStrategy {

        public ColumnActivationStrategy(ColumnViewer viewer) {
            super(viewer);
        }

        @Override
        protected boolean isEditorActivationEvent(
                ColumnViewerEditorActivationEvent event) {

            if (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED) {
                if (event.keyCode == SWT.CR || event.keyCode == ' ') {
                    return true;
                }
            }
            return super.isEditorActivationEvent(event);
        }

    }
}
