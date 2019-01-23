/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.ExtendedAttribute;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;

/**
 * {@link AbstractTableControl Table} column for {@link ExtendedAttribute}
 * values. This will display the appropriate cell editor for each extended
 * attribute type.
 * 
 * @author glewis
 * 
 */
public class ExtendedAttributeNameColumn extends AbstractColumn {

    private final TextCellEditor txtEditor;

    private final Composite root;

    private final Image errImg;

    /**
     * <code>Attribute</code> value column that displayed the appropriate cell
     * editor depending on the <code>Attribute</code> type.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param label
     *            column name
     * @param width
     *            column width
     */
    public ExtendedAttributeNameColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String label, int width) {
        super(editingDomain, viewer, SWT.LEFT, label, width);

        root = (Composite) viewer.getControl();
        txtEditor = new TextCellEditor(root);
        errImg =
                PlatformUI.getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return txtEditor;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    /**
     * Get the {@link Command} to set the value of the given
     * <code>Attribute</code>.
     * 
     * @param element
     *            element being edited
     * @param value
     *            the attribute value
     * @return {@link Command} or <code>null</code> if error.
     */
    protected Command getSetCommand(Object element, Object value) {
        Command cmd = null;
        Object input = getViewer().getInput();
        if (input instanceof TypeAssociation
                && element instanceof ExtendedAttribute) {
            TypeAssociation typeAssociation = (TypeAssociation) input;
            ExtendedAttribute extAttr = (ExtendedAttribute) element;
            String extAttrName = extAttr.getName();
            if (extAttrName != null) {
                cmd =
                        SetCommand.create(getEditingDomain(),
                                extAttr,
                                ChannelsPackage.eINSTANCE
                                        .getExtendedAttribute_Name(),
                                value);
            }
        }

        return cmd;
    }

    @Override
    protected String getText(Object element) {
        String text = null;
        if (element instanceof ExtendedAttribute) {
            ExtendedAttribute extAttr = (ExtendedAttribute) element;
            String name = extAttr.getName();
            if (name != null) {
                text = name;
            }
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

    /**
     * Get the {@link Attribute} from the given element.
     * 
     * @param element
     *            context element.
     * @return attribute element if it is an <code>Attribute</code>,
     *         <code>null</code> otherwise.
     */
    protected ExtendedAttribute getExtendedAttribute(Object element) {
        return (ExtendedAttribute) (element instanceof ExtendedAttribute ? element
                : null);
    }

    /** {@inheritDoc} */
    @Override
    protected void setValueFromEditor(Object element, Object value) {
        super.setValueFromEditor(element, value);

        // TODO workaround - remove when proper
        // adapter->factory->content/label provider->viewer will be
        // working.
        getViewer().refresh();
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        ExtendedAttribute extAttr = getExtendedAttribute(element);
        if (extAttr != null) {
            return getSetCommand(element, value);
        }
        return null;
    }
}
