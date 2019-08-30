/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.actions.TreeViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction;

/**
 * Base class for composite encapsulating structural column viewer with an
 * accompanying button bar. The button bar usually contains the common set of
 * actions for the viewer (like: add, edit, remove, move down, move up) which
 * can be modified or extended
 * {@link #fillViewerButtonsBar(IContributionManager, ColumnViewer)}. Actions
 * could also be added to the viewer's context menu
 * {@link #fillViewerContextMenu(IMenuManager, ColumnViewer)}. Viewer can use
 * the set of reusable columns.
 * <p/>
 * There are two known direct base implementations:
 * <li> {@link BaseTableControl} - for table viewers.</li>
 * <li>
 * {@link BaseTreeControl} - for tree viewers.</li>
 * 
 * <p>
 * <i>Created: 15 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class BaseColumnViewerControl extends Composite {

    /** Marker for inserting actions before 'Add'. */
    public static final String ADD_ACTIONS_START_MARKER = "AddActionsStart"; //$NON-NLS-1$

    /** Marker for inserting actions after 'Add'. */
    public static final String ADD_ACTIONS_END_MARKER = "AddActionsEnd"; //$NON-NLS-1$

    /** Marker for inserting actions before 'Edit'. */
    public static final String EDIT_ACTIONS_START_MARKER = "EditActionsStart"; //$NON-NLS-1$

    /** Marker for inserting actions after 'Edit'. */
    public static final String EDIT_ACTIONS_END_MARKER = "EditActionsEnd"; //$NON-NLS-1$

    /** Marker for inserting actions before 'Move'. */
    public static final String MOVE_ACTIONS_START_MARKER = "MoveActionsStart"; //$NON-NLS-1$

    /** Marker for inserting actions after 'Move'. */
    public static final String MOVE_ACTIONS_END_MARKER = "MoveActionsEnd"; //$NON-NLS-1$

    /** Marker for inserting additional actions. */
    public static final String ADDITIONS_MARKER = "Additions"; //$NON-NLS-1$

    private ColumnViewer columnViewer;

    private ToolBarManager buttonsManager;

    protected ViewerAddAction addAction;

    protected ViewerEditAction editAction;

    protected ViewerDeleteAction deleteAction;

    private ViewerMoveUpAction upAction;

    private ViewerMoveDownAction downAction;

    protected ILabelProvider viewerLabelProvider;

    protected IContentProvider viewerContentProvider;

    private final Set<IAction> acceleratedActions = Collections
            .synchronizedSet(new LinkedHashSet<IAction>());

    protected Set<EStructuralFeature> movableFeatures;

    protected Set<EStructuralFeature> deletableFeatures;

    private boolean actionsEnabled = true;

    private MenuManager menuManager;

    private Composite columnViewerContainer;

    private ViewerCellSpecificTooltipHandler cellTooltipHandler;

    private boolean isReadOnly;

    /**
     * @param parent
     * @param toolkit
     */
    public BaseColumnViewerControl(Composite parent, XpdToolkit toolkit) {
        super(parent, SWT.NONE);
        createContents(parent, toolkit, null);
    }

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     *            input for this viewer
     */
    public BaseColumnViewerControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {
        this(parent, toolkit, viewerInput, true);
    }

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     *            input for this viewer
     * @param createContent
     *            <code>true</code> if the content should be created,
     *            <code>false</code> otherwise. If <code>false</code> the user
     *            is responsible for creating the content by calling
     *            {@link #createContents(Composite, XpdToolkit, Object)
     *            createContent}. This is useful if the subclass needs to
     *            initialize some data before creating content.
     */
    public BaseColumnViewerControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput, boolean createContent) {
        super(parent, SWT.NONE);

        if (createContent) {
            createContents(parent, toolkit, viewerInput);
        }
    }

    protected void createContents(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {

        // Set the background colour of this control the same as the parent's
        setBackground(parent.getBackground());

        // create viewer
        /*
         * SID create a container that _only_ contains the table control. Then
         * if the setColumnProportions method is called, the sub-class can
         * switch to a an AbstractColumnLayout if required
         * (AbstractColumnLayout's have to be set on a parent composite of a
         * ColumnViwerControl, but we don't want that to interfere with our own
         * table+buttons layout).
         */
        columnViewerContainer = toolkit.createComposite(this);

        /* Fill the viwer container with the viwer control. */
        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        fl.spacing = 0;
        columnViewerContainer.setLayout(fl);

        columnViewer = createColumnViewer(columnViewerContainer, toolkit);
        columnViewer.setContentProvider(getViewerContentProvider());
        columnViewer.setLabelProvider(getViewerLabelProvider());
        columnViewer.setInput(viewerInput);

        createActions(columnViewer);

        /*
         * If no actions have been added then don't bother with buttonsManager
         */
        boolean wantToolbar = true;
        if (addAction == null && deleteAction == null && downAction == null
                && upAction == null && editAction == null) {
            wantToolbar = false;
        }

        /*
         * Use out own custom layout (see class header for details and
         * explanation why GruidLayout is not appropriate.
         */
        this.setLayout(new BaseColumnViewerLayout());

        if (wantToolbar) {
            // create buttons panel
            buttonsManager = new ToolBarManager(SWT.VERTICAL);
            buttonsManager.createControl(this);
            ToolBar toolbar = buttonsManager.getControl();
            if (toolbar != null && !toolbar.isDisposed()) {
                toolkit.adapt(toolbar);
                toolbar.setBackground(getBackground());

            }
        }

        addColumns(columnViewer);

        columnViewer.getControl().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                /*
                 * If actions is disabled then skip
                 */
                if (actionsEnabled) {
                    for (IAction action : acceleratedActions) {
                        if (action.isEnabled()
                                && action.getAccelerator() == (e.keyCode | e.stateMask)) {
                            action.run();
                            e.doit = false;
                            break;
                        }
                    }
                }
            }
        });

        /*
         * If the Cell specific tooltip was enabled prior to content creation
         * then we ned to activate it.
         */
        if (cellTooltipHandler != null) {
            cellTooltipHandler.activate();
        }

        if (buttonsManager != null) {
            fillViewerButtonsBar(buttonsManager, columnViewer);
            buttonsManager.update(true);
        }

        // create context menu
        menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        Menu menu = menuManager.createContextMenu(columnViewer.getControl());
        columnViewer.getControl().setMenu(menu);

        menuManager.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                fillViewerContextMenu(manager, columnViewer);
            }

        });

        enableTraversal();
    }

    /**
     * Add tooltip handling which will ask each ViewerColumn's label provider
     * for tooltips for individual cells (if using {@link AbstractColumn} then
     * you can simply override {@link AbstractColumn#getToolTipText(Object)}.
     * <p>
     * Although similar functionality is available in SWT already in
     * ColumnViewerTooltipSupport, this is not all that it could be (see
     * ViewerCellSpecificTooltipHandler) for more details.
     * 
     * @since v3.5.10
     */
    public void enableCellSpecificTooltips() {
        if (cellTooltipHandler == null) {
            cellTooltipHandler =
                    new ViewerCellSpecificTooltipHandler(columnViewer);
        }

        if (columnViewerContainer != null) {
            /*
             * Can enable right now. Else will have to wait till content is
             * created.
             */
            cellTooltipHandler.activate();
        }
    }

    /**
     * @return the columnViewerContainer
     */
    public Composite getColumnViewerContainer() {
        return columnViewerContainer;
    }

    protected void fillViewerContextMenu(IMenuManager manager,
            ColumnViewer columnViewer) {
        // add actions to the context menu in subclasses if needed.
    }

    /**
     *  
     */
    protected void enableTraversal() {
        // traversal is disabled by default
    }

    protected abstract ColumnViewer createColumnViewer(Composite parent,
            XpdToolkit toolkit);

    /**
     * Add columns to the viewer.
     * 
     * @param viewer
     *            the viewer.
     */
    protected abstract void addColumns(ColumnViewer viewer);

    protected void registerActionAccelerator(IAction action) {
        if (action.getAccelerator() != SWT.NONE) {
            acceleratedActions.add(action);
        }
    }

    /**
     * @return
     */
    protected IContentProvider getViewerContentProvider() {
        if (viewerContentProvider == null) {
            viewerContentProvider =
                    new TransactionalAdapterFactoryContentProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory());
        }
        return viewerContentProvider;
    }

    /**
     * @return
     */
    protected ILabelProvider getViewerLabelProvider() {
        if (viewerLabelProvider == null) {
            viewerLabelProvider =
                    new TransactionalAdapterFactoryLabelProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory());

        }
        return viewerLabelProvider;
    }

    /**
     * attach actions as viewer's listeners
     * 
     * @param viewer
     *            the managed viewer.
     */
    protected void createActions(ColumnViewer viewer) {
        addAction = createAddAction(viewer);
        if (addAction != null) {
            viewer.addSelectionChangedListener(addAction);
            registerActionAccelerator(addAction);
        }
        editAction = createEditAction(viewer);
        if (editAction != null) {
            viewer.addSelectionChangedListener(editAction);
            registerActionAccelerator(editAction);
        }
        deleteAction = createDeleteAction(viewer);
        if (deleteAction != null) {
            viewer.addSelectionChangedListener(deleteAction);
            registerActionAccelerator(deleteAction);
        }
        upAction = createMoveUpAction(viewer);
        if (upAction != null) {
            viewer.addSelectionChangedListener(upAction);
            registerActionAccelerator(upAction);
        }
        downAction = createMoveDownAction(viewer);
        if (downAction != null) {
            viewer.addSelectionChangedListener(downAction);
            registerActionAccelerator(downAction);
        }
    }

    /**
     * Enable/Disable the actions available in the toolbar.
     * 
     * @param enabled
     *            <code>true</code> to enable the actions, <code>false</code> to
     *            disable.
     */
    public void setEnableActions(boolean enabled) {
        actionsEnabled = enabled;
        if (buttonsManager != null) {
            ToolBar bar = buttonsManager.getControl();
            if (bar != null && !bar.isDisposed()) {
                bar.setEnabled(enabled);
            }
        }
    }

    /**
     * @return
     */
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * @param viewer
     * @return
     */
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * Creates default delete action that for selected viewer elements. The returned {@link TreeViewerDeleteEMFAction}
     * will use {@link #getDeletableFeatures()} to establish EMF features that can be deleted. This method can be
     * overridden by subclasses if needed.
     * 
     * @param viewer
     *            the viewer.
     * @return the delete action.
     */
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TreeViewerDeleteEMFAction(viewer, getDeletableFeatures()) {
            @Override
            protected boolean canDelete(IStructuredSelection selection) {
                return super.canDelete(selection) && !isReadOnly();
            }
        };
    }

    /**
     * Creates default move down action that for a selected viewer element. The returned {@link ViewerMoveDownEMFAction}
     * will use {@link #getMovableFeatures()} to establish EMF features that can be moved.This method can be overridden
     * by subclasses if needed.
     * 
     * @param viewer
     *            the viewer.
     * @return the move action.
     */
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new ViewerMoveDownEMFAction(viewer, getMovableFeatures()) {
            @Override
            protected boolean canMoveDown(IStructuredSelection selection, StructuredViewer viewer) {
                return super.canMoveDown(selection, viewer) && !isReadOnly();
            }
        };
    }

    /**
     * Creates default move up action that for a selected viewer element. The returned {@link ViewerMoveDownEMFAction}
     * will use {@link #getMovableFeatures()} to establish EMF features that can be moved.This method can be overridden
     * by subclasses if needed.
     * 
     * @param viewer
     *            the viewer.
     * @return the move action.
     */
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        return new ViewerMoveUpEMFAction(viewer, getMovableFeatures()) {
            @Override
            protected boolean canMoveUp(IStructuredSelection selection, StructuredViewer viewer) {
                return super.canMoveUp(selection, viewer) && !isReadOnly();
            }
        };
    }

    /**
     * Fills the buttons bar with actions and or other contribution elements.
     * 
     * @param manager
     *            the manager for adding actions (or other IContributions like
     *            GroupMaker or Separator).
     * @param viewer
     *            the viewer.
     */
    protected void fillViewerButtonsBar(IContributionManager manager,
            ColumnViewer viewer) {
        manager.add(new GroupMarker(ADD_ACTIONS_START_MARKER));
        if (addAction != null) {
            manager.add(addAction);
        }
        manager.add(new GroupMarker(ADD_ACTIONS_END_MARKER));
        manager.add(new GroupMarker(EDIT_ACTIONS_START_MARKER));
        if (editAction != null) {
            manager.add(editAction);
        }
        if (deleteAction != null) {
            manager.add(deleteAction);
        }
        manager.add(new GroupMarker(EDIT_ACTIONS_END_MARKER));
        manager.add(new Separator(MOVE_ACTIONS_START_MARKER));
        if (upAction != null) {
            manager.add(upAction);
        }
        if (downAction != null) {
            manager.add(downAction);
        }
        manager.add(new Separator(MOVE_ACTIONS_END_MARKER));
        manager.add(new Separator(ADDITIONS_MARKER));
    }

    public ColumnViewer getViewer() {
        return columnViewer;
    }

    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = new HashSet<EStructuralFeature>();
        }
        return movableFeatures;
    }

    protected Set<EStructuralFeature> getDeletableFeatures() {
        if (deletableFeatures == null) {
            deletableFeatures = new HashSet<EStructuralFeature>();
        }
        return deletableFeatures;
    }

    /**
     * Set the column proportions in relation to the overall width of the
     * control. Columns are automatically resized to their respective
     * proportional
     * <p>
     * Each columnProportion is given as a figure between 0.0 and 1.0 (0% and
     * 100%). Therefore if you have 3 columns and pass 0.5f, 0.3f, 0.2f then the
     * columns will be sized to full width of table with each column taking up
     * 50%, 30% and 20% respectively.
     * 
     * @param columnProportions
     */
    public abstract void setColumnProportions(float[] columnProportions);

    /**
     * Sets read-only state for the control.
     * 
     * @param readOnly
     *            the read-only state
     */
    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;
        refreshSelectionInViewer();
    }

    /**
     * Gets current read-only state of the control.
     * 
     * @return read-only state of the control.
     */
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    /**
     * Refreshes selection in the viewer. Usually used to refresh state of actions which depends on the viewer's
     * selection.
     */
    public void refreshSelectionInViewer() {
        StructuredViewer viewer = getViewer();
        if (viewer != null) {
            ISelection selection = viewer.getSelection();
            viewer.setSelection(StructuredSelection.EMPTY);
            viewer.setSelection(selection);
        }
    }

    /**
     * This is the custom layout <b>specifically</b> for {@link BaseColumnViewerControl}'s.
     * <p>
     * This is necessary because when a column viewer has a proportional layout ({@link AbstractColumnLayout}) it does
     * not interact nicely with it's parent container (the {@link BaseColumnViewerControl}) having a {@link GridLayout}.
     * <p>
     * It seems that {@link AbstractColumnLayout} does not constrain itself very well in that when it's parent layout
     * asks it how wide it wants to be it will return 'n' _but_ when the layout asks it how wide it wants to be given
     * 'n' width to fit into it _always_ returns 'n + a_bit_extra'.
     * <p>
     * The practical upshot of this is that when the controls parent is resized the control just gets bigger and bigger
     * and bigger (this may also be due in part to having a scrollable container somewhere up the stack.
     * 
     * @author aallway
     * @since 3.4.2 (27 Jul 2010)
     */
    private class BaseColumnViewerLayout extends Layout {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets
         * .Composite, int, int, boolean)
         */
        @Override
        protected Point computeSize(Composite columnViewerControl, int wHint,
                int hHint, boolean flushCache) {

            /*
             * SID: XPD-919. If, like grid layout, we were to ask the column
             * viewer it's preferred size, add the toolbar size to it and return
             * that then the grid layout will assume that the control has to be
             * 'at least' that size.
             * 
             * When using proportional layout this can be a problem because the
             * because the table will want to size its columns according to the
             * width it is set to _not_ set it's size from the width of it's
             * columns (rather than setting it's size to the sum of fixed width
             * columns).
             * 
             * But even when using fixed column widths, returning the sum of
             * columns (as is when you get default computeSize for table) then
             * it means that the column viwer is forced to sum of columns which
             * will cause the parent to be resized and (eventually) some
             * scrollable parent to have a scroll bar.
             * 
             * On the other hand, if we return a minimal width and assume that
             * the parent layout will be such that the column viewer will be the
             * control that grabs most horizontal space (which I think is fine),
             * then the control will be sized according to the space available.
             * 
             * In the case of proportional columns this means the control is
             * squeezed to available width (according to parent's layout) and
             * columns will be proportionally sized within that.
             * 
             * In the case of fixed width columns, then when the sum of column
             * width's is greater than space available the scroll bar will
             * appear <i>in the column viewer itself</i>. This is much better
             * because it means that the actions buttons won't be pushed off the
             * edge of parent scrollable.
             */
            Point size = new Point(100, 30); // effectively this is minimum size

            return size; // new Point(1, 1);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite
         * , boolean)
         */
        @Override
        protected void layout(Composite columnViewerControl, boolean flushCache) {
            Point calculatedToolbarSize = new Point(0, 0);
            if (buttonsManager != null && buttonsManager.getControl() != null
                    && !buttonsManager.getControl().isDisposed()) {
                /* How big would toolbar like to be if possible. */
                calculatedToolbarSize =
                        buttonsManager.getControl().computeSize(SWT.DEFAULT,
                                SWT.DEFAULT,
                                true);
            }

            /*
             * Calculate table size from actual size of thi
             * BaseColumnViewerControl (the button bar size is always the same).
             */
            Point calculatedTableSize =
                    calculateSizeTableForProportionalLayout(columnViewerControl,
                            calculatedToolbarSize);

            /*
             * Height is the tallest of table or toolbar.
             */
            int height =
                    Math.max(calculatedTableSize.y, calculatedToolbarSize.y);

            /*
             * Set the sizes.
             */
            if (columnViewerContainer != null
                    && !columnViewerContainer.isDisposed()) {
                columnViewerContainer.setBounds(0,
                        0,
                        calculatedTableSize.x,
                        height);
            }

            if (buttonsManager != null && buttonsManager.getControl() != null
                    && !buttonsManager.getControl().isDisposed()) {
                buttonsManager.getControl()
                        .setBounds(calculatedTableSize.x + 1,
                                0,
                                calculatedToolbarSize.x,
                                height);
            }
            return;
        }

        /**
         * @param columnViewerControl
         * @param toolbarSize
         * @return The table size for the given BaseColumnViewerControl
         */
        private Point calculateSizeTableForProportionalLayout(
                Composite columnViewerControl, Point toolbarSize) {
            Point parentSize = columnViewerControl.getSize();
            Point tableSize =
                    new Point(parentSize.x - toolbarSize.x - 1, parentSize.y);
            if (tableSize.x < 10) {
                /* Make sure there's alway something that the user can see. */
                tableSize.x = 10;
            }

            return tableSize;
        }

    }

}