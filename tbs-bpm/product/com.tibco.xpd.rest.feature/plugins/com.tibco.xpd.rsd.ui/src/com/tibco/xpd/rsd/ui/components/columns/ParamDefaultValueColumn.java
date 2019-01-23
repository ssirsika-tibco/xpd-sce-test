/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components.columns;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.resources.ui.components.calendar.DateUtil;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * This column will display the appropriate cell editor for each parameter type.
 * 
 * @author jarciuch
 * @since 6 Mar 2015
 */
public class ParamDefaultValueColumn extends AbstractColumn {
    /*
     * TODO Improve and refactor ParamDefaultValueColumn: 2. Unset value for all
     * types and use -not set- label when default value is set.
     */

    private static Logger LOG = RsdUIPlugin.getLogger();

    private final TextCellEditor txtEditor;

    private final BooleanCellEditor boolEditor;

    private final DateTimeCellEditor dateEditor;

    private final Composite root;

    public ParamDefaultValueColumn(EditingDomain editingDomain,
            ColumnViewer viewer) {
        this(editingDomain, viewer, Messages.ParamDefaultValueColumn_DefaultValueColumn_label, 150);
    }

    /**
     * <code>Parameter</code> value column that displayed the appropriate cell
     * editor depending on the <code>Parameter</code> type.
     * 
     * @param editingDomain
     * @param viewer
     *            column viewer
     * @param label
     *            column name
     * @param width
     *            column width
     */
    public ParamDefaultValueColumn(EditingDomain editingDomain,
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
        Parameter param = getParameter(element);
        if (param != null) {
            switch (param.getDataType()) {
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
        Parameter param = getParameter(element);

        // If value is a blank string then set it to null
        if ("".equals(value)) { //$NON-NLS-1$
            value = null;
        }

        if (param != null) {
            if (value != null) {
                switch (param.getDataType()) {
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
     * <code>Parameter</code>.
     * 
     * @param element
     *            element being edited
     * @param value
     *            the attribute value
     * @return {@link Command} or <code>null</code> if error.
     */
    protected Command getSetCommand(Object element, Object value) {
        Command cmd = null;
        if (element instanceof Parameter) {
            Parameter param = (Parameter) element;
            if (param != null && param.getDataType() != null) {
                cmd =
                        SetCommand.create(getEditingDomain(),
                                param,
                                RsdPackage.eINSTANCE
                                        .getParameter_DefaultValue(),
                                value != null ? value : SetCommand.UNSET_VALUE);
            }
        }
        return cmd;
    }

    /**
     * Get the value of the given {@link Parameter}.
     * 
     * @param element
     * @return for all types a {@link String} is expected (date/time should be
     *         ISO 8601 format string).
     */
    protected Object getValue(Object element) {
        Parameter param = getParameter(element);
        if (param != null && param.getDataType() != null) {
            return param.getDefaultValue();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueForEditor(Object element) {
        Parameter param = getParameter(element);
        if (param != null) {
            DataType dataType = param.getDataType();
            switch (dataType) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
            case BOOLEAN:
                return getText(element);

            case DATE:
            case TIME:
            case DATE_TIME:
                Object value = getValue(element);
                if (value == null) {
                    // Get the default value
                    value = getParameterDefaultValue(param);
                }
                if (value instanceof String
                        && !((String) value).trim().isEmpty()) {
                    try {
                        return DateUtil.parse((String) value,
                                getDateType(dataType));
                    } catch (ParseException e) {
                        LOG.error(e,
                                String.format("Problem with date parsing [%s]", //$NON-NLS-1$
                                        value));
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return getText(element);
    }

    /**
     * Converts RSD type to the core calendar DateType.
     */
    private static DateType getDateType(DataType dataType) {
        switch (dataType) {
        case DATE:
            return DateType.DATE;
        case TIME:
            return DateType.TIME;
        case DATE_TIME:
            return DateType.DATETIME;
        default:
            String message =
                    String.format("Invalid type: [%s], expected one of: (DATE, TIME, DATE_TIME)", //$NON-NLS-1$
                            dataType);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getText(Object element) {
        Parameter param = getParameter(element);

        DataType dataType = param.getDataType();
        if (param != null && dataType != null) {
            Object value = getValue(element);
            if (value == null) {
                // Get the default value of this attribute
                value = getParameterDefaultValue(param);
            }

            if (value != null) {
                switch (dataType) {
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
                case DATE:
                case DATE_TIME:
                case TIME:
                    if (value instanceof String && !("".equals(value))) { //$NON-NLS-1$
                        try {
                            return DateUtil
                                    .getLocalizedStringFromISO8601String((String) value,
                                            getDateType(dataType));
                        } catch (ParseException e) {
                            LOG.error(e, "Problem with date/time parsing."); //$NON-NLS-1$
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
     * Get the default value of this parameter if set
     * 
     * @param param
     * @return
     */
    protected Object getParameterDefaultValue(Parameter param) {
        if (param != null) {
            return param.getDefaultValue();
        }
        return null;
    }

    /**
     * Get the {@link Parameter} from the given input. Default implementation
     * returns the object if it is an <code>Parameter</code>. Subclasses should
     * override if the input is not going to be an <code>Parameter</code>.
     * 
     * @param input
     * @return input if it is an <code>Parameter</code>, <code>null</code>
     *         otherwise.
     */
    protected Parameter getParameter(Object input) {
        return (Parameter) (input instanceof Parameter ? input : null);
    }
}