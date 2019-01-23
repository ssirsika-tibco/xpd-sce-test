/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.themes.ColorUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Type picker column for the BOM tables.
 * 
 * @author njpatel
 * 
 */
public abstract class TypeColumn extends AbstractColumn {

    private final TypeEditor editor;

    private Color disabledColor;

    /**
     * Type column with the specified title. This will provide a dialog cell
     * without a clear option.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param title
     *            title of this column
     * @param width
     *            initial width of this column
     */
    public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, int width) {
        this(editingDomain, viewer, title, width, false);
    }

    /**
     * Type column with the specified title.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param title
     *            title of this column
     * @param width
     *            initial width of this column
     * @param allowClear
     *            <code>true</code> if a clear button should be available in the
     *            cell editor.
     */
    public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, int width, boolean allowClear) {
        super(editingDomain, viewer, SWT.NONE, title, width);
        editor = new TypeEditor((Composite) viewer.getControl(), allowClear);
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        editor
                .setInput((EObject) (element instanceof EObject ? element
                        : null));
        return editor;
    }

    @Override
    protected String getText(Object element) {
        String txt = null;
        Type type = getType(element);
        if (type != null) {
            if (!type.eIsProxy()) {
                txt = UML2ModelUtil.getQualifiedLabel(type);
                if (txt == null) {
                    txt = WorkingCopyUtil.getText(type);
                }
            } else {
                txt = AbstractGeneralSection.UNRESOLVED_REFERENCE;
            }
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    @Override
    protected Color getForeground(Object element) {
        Type type = getType(element);
        if (type != null && type.eIsProxy()) {
            if (disabledColor == null) {
                Color foreground =
                        getColumn().getViewer().getControl().getForeground();
                disabledColor =
                        new Color(foreground.getDevice(), ColorUtil
                                .blend(foreground.getRGB(),
                                        ColorConstants.white.getRGB(),
                                        40));
            }
            return disabledColor;
        }
        return super.getForeground(element);
    }

    @Override
    protected Image getImage(Object element) {
        Type type = getType(element);
        if (type != null) {
            return WorkingCopyUtil.getImage(type);
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        Type type = getType(element);
        if (type != null && type.eIsProxy()) {
            return AbstractGeneralSection.UNRESOLVED_REFERENCE;
        }
        return type;
    }

    @Override
    protected void dispose() {
        if (disabledColor != null) {
            disabledColor.dispose();
            disabledColor = null;
        }
        super.dispose();
    }

    /**
     * Get the {@link Type} of the given element.
     * 
     * @param element
     * @return
     */
    protected abstract Type getType(Object element);

    /**
     * Dialog cell editor that shows a type picker.
     * 
     * @author njpatel
     * 
     */
    private class TypeEditor extends DialogCellWithClearEditor {

        private EObject input;

        public TypeEditor(Composite parent, boolean showCancel) {
            super(parent, SWT.NONE, showCancel);
        }

        public void setInput(EObject input) {
            this.input = input;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            if (input != null) {
                return BomUIUtil.getTypeFromPicker(cellEditorWindow.getShell(),
                        input);
            }
            return null;
        }

        @Override
        protected void updateContents(Object value) {
            if (value instanceof NamedElement) {
                value = UML2ModelUtil.getQualifiedLabel((NamedElement) value);
            }
            super.updateContents(value);
        }

    }
}