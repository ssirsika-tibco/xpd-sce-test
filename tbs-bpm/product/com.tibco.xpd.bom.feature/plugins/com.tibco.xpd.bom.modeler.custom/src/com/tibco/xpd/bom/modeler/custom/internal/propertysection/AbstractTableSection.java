/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Extension to the {@link AbstractTransactionalSection} to be used by property
 * sections that may have an {@link EditPart}, or any object that adapts to an
 * {@link EObject}, as the input. This section displays a table with the given
 * columns.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractTableSection extends AbstractTransactionalSection
        implements IFilter {

    private final String title;

    private BaseTableControl table;

    private final EStructuralFeature feature;

    private XpdFormToolkit toolkit;

    public AbstractTableSection(String title, EStructuralFeature feature) {
        this.title = title;
        this.feature = feature;
    }

    /**
     * Add table columns.
     * 
     * @see AbstractColumn
     * 
     * @param viewer
     */
    protected abstract void addColumns(ColumnViewer viewer);

    /**
     * Add the "Add" action to the table.
     * 
     * @param viewer
     * @return action or <code>null</code> not to have the action.
     */
    protected abstract TableAddAction getAddAction(ColumnViewer viewer);

    /**
     * Add the "Remove" action to the table.
     * 
     * @param viewer
     * @return action or <code>null</code> not to have the action.
     */
    protected abstract TableDeleteAction getDeleteAction(ColumnViewer viewer);

    /**
     * Add additional actions to the action toolbar. Default implementation
     * returns <code>null</code>, subclasses may extend to add custom actions.
     * 
     * @param viewer
     * @return
     */
    protected Collection<ViewerAction> getAdditionalActions(ColumnViewer viewer) {
        return null;
    }

    /**
     * Should the move up and down options be available in the table.
     * 
     * @return
     */
    protected boolean canMove() {
        return true;
    }

    /**
     * Allows for the Move Down action to be overridden
     * 
     * @param viewer
     * @return
     */
    protected ViewerMoveDownAction getMoveDownAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * Allows for the Move Up action to be overridden
     * 
     * @param viewer
     * @return
     */
    protected ViewerMoveUpAction getMoveUpAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * Get the toolkit used for creating the controls.
     * 
     * @return toolkit or <code>null</code> if one is not yet available.
     */
    public XpdFormToolkit getToolkit() {
        return toolkit;
    }

    /**
     * Get the section table.
     * 
     * @return
     */
    public BaseTableControl getTable() {
        return table;
    }

    /**
     * Get the table's content provider. By default this will return a
     * {@link IStructuredContentProvider} which will get the list of the
     * "feature" from the input.
     * 
     * @return
     */
    protected IContentProvider getContentProvider() {
        return new IStructuredContentProvider() {

            @Override
            public Object[] getElements(Object inputElement) {
                Object[] items = null;

                if (inputElement instanceof EObject && feature != null) {
                    Object value = ((EObject) inputElement).eGet(feature);

                    if (value instanceof Collection<?>) {
                        items = ((Collection<?>) value).toArray();
                    }
                }

                return items != null ? items : new Object[0];
            }

            @Override
            public void dispose() {
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }
        };
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        this.toolkit = toolkit;
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());
        createHeading(root, toolkit, title);

        table = new SectionTable(root, toolkit);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Table will handle this
        return null;
    }

    @Override
    protected void doRefresh() {
        setTableInput(getInput());
    }

    /**
     * Set the visibility of the given column.
     * 
     * @param column
     * @param visible
     */
    protected void setVisible(AbstractColumn column, boolean visible) {
        if (column != null && column.getColumn() instanceof TableViewerColumn) {
            TableColumn theColumn =
                    ((TableViewerColumn) column.getColumn()).getColumn();

            if (theColumn != null) {
                if (visible) {
                    theColumn.setWidth(column.getInitialWidth());
                    theColumn.setResizable(true);
                } else {
                    theColumn.setWidth(0);
                    theColumn.setResizable(false);
                }
            }
        }
    }

    /**
     * Set the table's input.
     * 
     * @param input
     */
    protected void setTableInput(Object input) {
        if (input != null && table != null && table.getViewer() != null) {
            table.getViewer().cancelEditing();
            table.getViewer().setInput(input);
        }
    }

    /**
     * Create a heading in this section. This assumes that the parent composite
     * uses <code>GridLayout</code>.
     * 
     * @param parent
     * @param toolkit
     * @param heading
     * @return
     */
    private Label createHeading(Composite parent, XpdFormToolkit toolkit,
            String heading) {
        Label label = toolkit.createLabel(parent, heading);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);

        Font tableHeaderFont =
                JFaceResources.getResources()
                        .createFont(FontDescriptor.createFrom("Arial", //$NON-NLS-1$
                                10,
                                SWT.BOLD));
        label.setFont(tableHeaderFont);
        return label;
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof IAdaptable) {
            EObject adapter =
                    (EObject) ((IAdaptable) object).getAdapter(EObject.class);
            if (adapter != null) {
                return adapter;
            }
        }
        return super.resollveInput(object);
    }

    /**
     * The section table that will list all "feature" elements of the input.
     * 
     * @author njpatel
     * 
     */
    private class SectionTable extends BaseTableControl {

        private Collection<ViewerAction> additionalActions;

        public SectionTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        @Override
        protected void addColumns(ColumnViewer viewer) {
            AbstractTableSection.this.addColumns(viewer);
        }

        @Override
        protected IContentProvider getViewerContentProvider() {
            return AbstractTableSection.this.getContentProvider();
        }

        @Override
        protected void createActions(ColumnViewer viewer) {
            super.createActions(viewer);
            additionalActions = getAdditionalActions(viewer);

            if (additionalActions != null) {
                for (ViewerAction action : additionalActions) {
                    viewer.addSelectionChangedListener(action);
                    registerActionAccelerator(action);
                }
            }
        }

        @Override
        protected void fillViewerButtonsBar(IContributionManager manager,
                ColumnViewer viewer) {
            super.fillViewerButtonsBar(manager, viewer);
            if (additionalActions != null) {
                for (ViewerAction action : additionalActions) {
                    manager.add(action);
                }
            }

        }

        @Override
        protected ViewerAddAction createAddAction(ColumnViewer viewer) {
            return getAddAction(viewer);
        }

        @Override
        protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
            return getDeleteAction(viewer);
        }

        @Override
        protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
            if (canMove()) {
                ViewerMoveDownAction moveDownAction = getMoveDownAction(viewer);
                if (moveDownAction == null) {
                    moveDownAction = super.createMoveDownAction(viewer);
                }
                return moveDownAction;
            }
            return null;
        }

        @Override
        protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
            if (canMove()) {
                ViewerMoveUpAction moveUpAction = getMoveUpAction(viewer);
                if (moveUpAction == null) {
                    moveUpAction = super.createMoveUpAction(viewer);
                }
                return moveUpAction;
            }
            return null;
        }

        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            if (feature != null && canMove()) {
                features.add(feature);
            }
            return features;
        }
    }
}
