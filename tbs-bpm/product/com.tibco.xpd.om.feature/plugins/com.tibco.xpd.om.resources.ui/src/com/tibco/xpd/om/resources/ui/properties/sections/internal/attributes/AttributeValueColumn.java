/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.DateUtil;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateTimeCellEditor;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * {@link AbstractTableControl Table} column for {@link Attribute} values. This
 * will display the appropriate cell editor for each attribute type.
 * 
 * @author njpatel
 * 
 */
public abstract class AttributeValueColumn extends AbstractColumn {

    private final TextCellEditor txtEditor;

    private final BooleanCellEditor boolEditor;

    private final DateTimeCellEditor dateEditor;

    private final Composite root;

    /**
     * <code>Attribute</code> value column that displayed the appropriate cell
     * editor depending on the <code>Attribute</code> type.
     * 
     * @param editingDomain
     * @param viewer
     *            column viewer
     * @param label
     *            column name
     * @param width
     *            column width
     */
    public AttributeValueColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String label, int width) {
        super(editingDomain, viewer, SWT.LEFT, label, width);

        root = (Composite) viewer.getControl();
        txtEditor = new TextCellEditor(root);
        boolEditor = new BooleanCellEditor(root);
        dateEditor = new DateTimeCellEditor(root);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        CellEditor editor = null;
        Attribute attr = getAttribute(element);
        if (attr != null) {
            switch (attr.getType()) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
                editor = txtEditor;
                break;
            case BOOLEAN:
                editor = boolEditor;
                break;
            case DATE:
                dateEditor.setType(DateType.DATE);
                editor = dateEditor;
                break;
            case DATE_TIME:
                dateEditor.setType(DateType.DATETIME);
                editor = dateEditor;
                break;
            case TIME:
                dateEditor.setType(DateType.TIME);
                editor = dateEditor;
                break;
            }
        }
        return editor;
    }

    @Override
    protected void setValueFromEditor(Object element, Object value) {
        /*
         * Only update the value if the editor is dirty
         */
        CellEditor editor = getCellEditor(element);
        if (editor != null && editor.isDirty()) {
            super.setValueFromEditor(element, value);
        }
    }

    @Override
    protected final Command getSetValueCommand(Object element, Object value) {
        Attribute attr = getAttribute(element);

        // If value is a blank string then set it to null
        if ("".equals(value)) { //$NON-NLS-1$
            value = null;
        }

        if (attr != null) {
            if (value != null) {
                switch (attr.getType()) {
                case TEXT:
                case BOOLEAN:
                    return getSetCommand(element, value);
                case DECIMAL:
                    Number num = new Double(0);
                    try {
                        if (value != null) {
                            num =
                                    NumberFormat.getInstance()
                                            .parse(value.toString());
                        }
                    } catch (ParseException e1) {
                        // Don't set command and retain previous value as the
                        // current value is invalid
                        return null;
                    }

                    return getSetCommand(element, num.toString());
                case INTEGER:
                    Number intNum = new Long(0);
                    try {
                        if (value != null) {
                            intNum =
                                    NumberFormat.getIntegerInstance()
                                            .parse(value.toString());
                        }
                    } catch (ParseException e) {
                        // Don't set command and retain previous value as the
                        // current value is invalid
                        return null;
                    }

                    return getSetCommand(element, intNum.toString());
                case DATE:
                case DATE_TIME:
                case TIME:
                    String str = null;
                    if (value instanceof Date) {
                        str = dateEditor.format((Date) value);
                    }
                    return getSetCommand(element, str);
                }
            }
            return getSetCommand(element, value);
        }
        return null;
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
    protected abstract Command getSetCommand(Object element, Object value);

    /**
     * Get the value of the given {@link Attribute}.
     * 
     * @param element
     * @return for attribute of type Enum and EnumSet a collection of
     *         {@link EnumValue}s is expected and for all other types a
     *         {@link String} is expected (date/time should be ISO 8601 format
     *         string).
     */
    protected abstract Object getValue(Object element);

    @Override
    protected Object getValueForEditor(Object element) {
        Attribute attr = getAttribute(element);
        if (attr != null) {
            switch (attr.getType()) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
                return getText(element);

            case DATE:
            case DATE_TIME:
            case TIME:
                Object value = getValue(element);
                if (value == null) {
                    // Get the default value
                    value = getAttributeDefaultValue(attr);
                }
                if (value instanceof String && !("".equals(value))) { //$NON-NLS-1$
                    try {
                        return DateUtil
                                .parse((String) value,
                                        attr.getType() == AttributeType.DATE ? DateType.DATE
                                                : attr.getType() == AttributeType.DATE_TIME ? DateType.DATETIME
                                                        : DateType.TIME);
                    } catch (ParseException e) {
                        OMResourcesUIActivator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format(Messages.AttributeValueColumn_unrecognisedDateTime_error_shortdesc,
                                                value.toString()));
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return getText(element);
    }

    @Override
    protected String getText(Object element) {
        Attribute attr = getAttribute(element);

        if (attr != null && attr.getType() != null) {
            Object value = getValue(element);
            if (value == null) {
                // Get the default value of this attribute
                value = getAttributeDefaultValue(attr);
            }

            if (value != null) {
                switch (attr.getType()) {
                case DECIMAL:
                    try {
                        Double num = Double.parseDouble(value.toString());
                        return NumberFormat.getInstance().format(num);
                    } catch (Exception e) {
                        return value.toString();
                    }
                case INTEGER:
                    try {
                        Long intNum = Long.parseLong(value.toString());
                        return NumberFormat.getIntegerInstance().format(intNum);
                    } catch (Exception e) {
                        return value.toString();
                    }

                case ENUM:
                case ENUM_SET:
                case SET:
                    if (value instanceof Collection<?>) {
                        StringBuffer buffer = new StringBuffer();
                        for (Object item : (Collection<?>) value) {
                            if (buffer.length() > 0) {
                                buffer.append(", "); //$NON-NLS-1$
                            }
                            if (item instanceof String) {
                                buffer.append((String) item);
                            } else if (item instanceof EnumValue) {
                                buffer.append(((EnumValue) item).getValue());
                            }
                        }
                        return buffer.toString();
                    }
                    break;
                case DATE:
                case DATE_TIME:
                case TIME:
                    if (value instanceof String && !("".equals(value))) { //$NON-NLS-1$
                        try {
                            return DateUtil
                                    .getLocalizedStringFromISO8601String((String) value,
                                            attr.getType() == AttributeType.DATE ? DateType.DATE
                                                    : attr.getType() == AttributeType.DATE_TIME ? DateType.DATETIME
                                                            : DateType.TIME);
                        } catch (ParseException e) {
                            OMResourcesUIActivator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            String.format(Messages.AttributeValueColumn_unrecognisedDateTime_error_shortdesc,
                                                    value.toString()));
                        }
                    }
                    break;
                default:
                    return value.toString();
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Get the default value of this attribute if set
     * 
     * @param attr
     * @return
     */
    protected Object getAttributeDefaultValue(Attribute attr) {
        if (attr != null) {
            if (attr.getType() == AttributeType.ENUM
                    || attr.getType() == AttributeType.ENUM_SET) {
                return attr.getDefaultEnumSetValues();
            }
            return attr.getDefaultValue();
        }
        return null;
    }

    /**
     * Get the {@link Attribute} from the given input. Default implementation
     * returns the object if it is an <code>Attribute</code>. Subclasses should
     * override if the input is not going to be an <code>Attribute</code>.
     * 
     * @param input
     * @return input if it is an <code>Attribute</code>, <code>null</code>
     *         otherwise.
     */
    protected Attribute getAttribute(Object input) {
        return (Attribute) (input instanceof Attribute ? input : null);
    }
}
