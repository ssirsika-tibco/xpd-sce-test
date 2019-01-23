/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Abstract class representing a column in a ColumnViewer. This column is
 * self-contained in that it has it's own label provider and editing support.
 * 
 * @see ViewerColumn
 * @see TableViewerColumn
 * @see TreeViewerColumn
 * @see EditingSupport
 * @see AbstractTableControl
 * 
 * @author njpatel, Jan Arciuchiewicz
 * 
 */
public abstract class AbstractColumn {

    private final ViewerColumn viewerColumn;

    private final TransactionalAdapterFactoryLabelProvider modelLabelProvider;

    private boolean showImage = false;

    private final EditingDomain editingDomain;

    private final int initialWidth;

    /**
     * Abstract column class to be extended to create a column in a group
     * {@link AbstractTableControl table}.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param header
     *            column heading
     * @param width
     *            initial column width
     */
    public AbstractColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String header, int width) {
        this(editingDomain, viewer, SWT.LEFT, header, width);
    }

    /**
     * Abstract column class to be extended to create a column in a group
     * {@link AbstractTableControl table}.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param heading
     *            column heading
     * @param width
     *            initial column width
     */
    public AbstractColumn(EditingDomain editingDomain, ColumnViewer viewer,
            int style, String heading, int width) {
        this(editingDomain, viewer, style, heading, width, -1);
    }

    /**
     * Abstract column class to be extended to create a column in a group
     * {@link AbstractTableControl table}.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param style
     *            column {@link SWT style}
     * @param heading
     *            column heading
     * @param width
     *            initial column width
     * @param index
     *            initial column index.
     */
    public AbstractColumn(EditingDomain editingDomain, ColumnViewer viewer,
            int style, String heading, int width, int index) {
        this.initialWidth = width;
        Assert.isNotNull(editingDomain,
                String.format("No editing domain set for table column '%s'.", heading)); //$NON-NLS-1$
        Assert.isNotNull(viewer, String.format("Viewer cannot be null.")); //$NON-NLS-1$
        this.editingDomain = editingDomain;
        modelLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());

        if (viewer instanceof TreeViewer) {
            TreeViewerColumn column =
                    new TreeViewerColumn((TreeViewer) viewer, style, index);
            column.getColumn().setText(heading);
            column.getColumn().setToolTipText(heading);
            column.getColumn().setWidth(width);
            column.setLabelProvider(getLabelProvider());
            column.getColumn().addDisposeListener(new DisposeListener() {
                @Override
                public void widgetDisposed(DisposeEvent e) {
                    dispose();
                }
            });
            viewerColumn = column;
        } else if (viewer instanceof TableViewer) {
            TableViewerColumn column =
                    new TableViewerColumn((TableViewer) viewer, style, index);
            column.getColumn().setText(heading);
            column.getColumn().setToolTipText(heading);
            column.getColumn().setWidth(width);
            column.setLabelProvider(getLabelProvider());
            column.getColumn().addDisposeListener(new DisposeListener() {
                @Override
                public void widgetDisposed(DisposeEvent e) {
                    dispose();
                }
            });
            viewerColumn = column;
        } else {
            throw new IllegalArgumentException(
                    "Editing support reference unsupported ColumnViewer."); //$NON-NLS-1$
        }
        viewerColumn.setEditingSupport(getEditingSupport(viewer));
    }

    /**
     * Get the default width that was set on this column.
     * 
     * @return initial width.
     */
    public int getInitialWidth() {
        return initialWidth;
    }

    public ColumnViewer getViewer() {
        return viewerColumn.getViewer();
    }

    /**
     * Set whether the image should be shown in this column
     * 
     * @param showImage
     *            <code>true</code> to show image, <code>false</code> otherwise.
     */
    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
    }

    /**
     * Get the transactional editing domain.
     * 
     * @return {@link EditingDomain}
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Check if the image will be shown in this column
     * 
     * @return <code>true</code> if images will be shown in this column.
     */
    protected boolean getShowImage() {
        return showImage;
    }

    /**
     * Get the default adapter factory label provider of this model.
     * 
     * @return {@link TransactionalAdapterFactoryLabelProvider}
     */
    public TransactionalAdapterFactoryLabelProvider getModelLabelProvider() {
        return modelLabelProvider;
    }

    /**
     * Get the underlying {@link TableViewerColumn column}.
     * 
     * @return <code>TableViewerColumn</code>
     */
    public ViewerColumn getColumn() {
        return viewerColumn;
    }

    /**
     * Get the text for this column for the given element.
     * 
     * @param element
     *            table row element.
     * @return text
     */
    protected abstract String getText(Object element);

    /**
     * Override to provide tool tip text for individual cells.
     * <p>
     * You will also need to configure the column viewer itself to enable
     * cell-specific tooltip handling. When using
     * {@link BaseColumnViewerControl} then this can be done simply by calling
     * {@link BaseColumnViewerControl#enableCellSpecificTooltips()}.
     * <p>
     * If not the the SWT method is to use
     * {@link ColumnViewerToolTipSupport#enableFor(ColumnViewer, int)}
     * 
     * @param element
     *            Content element for row.
     * 
     * @return Tooltip for cell
     * 
     * @since 3.5.10
     */
    protected String getToolTipText(Object element) {
        return null;
    }

    /**
     * Get the foreground {@link Color} for the column cell. Default
     * implementation returns <code>null</code>. Subclasses may override to
     * change the color.
     * 
     * @param element
     *            table row element
     * @return <code>Color</code> or <code>null</code> for default color.
     */
    protected Color getForeground(Object element) {
        return null;
    }

    /**
     * Show the image in this column (if {@link #setShowImage(boolean) show
     * image} is set to true). By default this will use the adapter factory
     * {@link #getModelLabelProvider() label provider} to get the image of the
     * element that is the input of this row. Subclasses may override if they
     * wish to show another image or image of the item actually represented in
     * this cell.
     * 
     * @param element
     * @return {@link Image} or <code>null</code> if no image required.
     */
    protected Image getImage(Object element) {
        if (element != null) {
            return getModelLabelProvider().getImage(element);
        }
        return null;
    }

    /**
     * @param element
     * @return
     */
    protected boolean canEdit(Object element) {
        return getCellEditor(element) != null;
    }

    /**
     * Get the cell editor of this column.
     * 
     * @param element
     *            element being edited (table row)
     * @return cell editor if editing is required, <code>null</code> otherwise.
     */
    protected abstract CellEditor getCellEditor(Object element);

    /**
     * Get this column's value of the element in the editor.
     * 
     * @param element
     *            table row
     * @return editor's value
     */
    protected abstract Object getValueForEditor(Object element);

    /**
     * @param element
     * @param value
     */
    protected void setValueFromEditor(Object element, Object value) {
        // Update value if it has changed
        Object currentVal = getValueForEditor(element);
        if (currentVal != value) {
            Command cmd = getSetValueCommand(element, value);

            if (cmd != null && cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Get the command to set the value entered in the editor.
     * 
     * @param element
     *            element being edited
     * @param value
     *            value in the editor
     * @return {@link Command}
     */
    protected abstract Command getSetValueCommand(Object element, Object value);

    /**
     * Get the label provider for this column. This label provider will delegate
     * calls to {@link #getText(Object) getText} and {@link #getImage(Object)
     * getImage}.
     * 
     * @return {@link ColumnLabelProvider}
     */
    private ColumnLabelProvider getLabelProvider() {
        return new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return AbstractColumn.this.getText(element);
            }

            @Override
            public Image getImage(Object element) {
                if (getShowImage()) {
                    return AbstractColumn.this.getImage(element);
                }
                return null;
            }

            /**
             * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
             * 
             * @param element
             * @return
             */
            @Override
            public String getToolTipText(Object element) {
                return AbstractColumn.this.getToolTipText(element);
            }

            @Override
            public Color getForeground(Object element) {
                Color color = AbstractColumn.this.getForeground(element);
                return color != null ? color : super.getForeground(element);
            }
        };
    }

    /**
     * Get the editing support for this column.
     * 
     * @param viewer
     * @return
     */
    private EditingSupport getEditingSupport(ColumnViewer viewer) {
        return new EditingSupport(viewer) {

            @Override
            protected boolean canEdit(Object element) {
                return AbstractColumn.this.canEdit(element);
            }

            @Override
            protected CellEditor getCellEditor(Object element) {
                return AbstractColumn.this.getCellEditor(element);
            }

            @Override
            protected Object getValue(Object element) {
                return getValueForEditor(element);
            }

            @Override
            protected void setValue(Object element, Object value) {
                setValueFromEditor(element, value);
            }
        };
    }

    /**
     * Dispose any resources used in this column.
     */
    protected void dispose() {
        modelLabelProvider.dispose();
    }

}
