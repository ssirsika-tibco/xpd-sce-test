/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.calendar.DateTimePopup;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * DateTimeArrayTable
 * 
 * 
 * @author bharge
 * @since 3.3 (26 Nov 2009)
 */
public class DateTimeInitArrayTable extends AbstractInitArrayTable {

    private EditingDomain editingDomain;

    private String dateColumnLabel;

    /**
     * @param parent
     * @param toolkit
     */
    public DateTimeInitArrayTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain, String columnLabel) {
        super(parent, toolkit, editingDomain, false);
        this.editingDomain = editingDomain;
        this.dateColumnLabel = columnLabel;
        createContents(parent, toolkit, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new DateTimeColumn(editingDomain, viewer, dateColumnLabel);
    }

    private class DateTimeColumn extends AbstractInitColumn {
        private final DateTimeCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public DateTimeColumn(EditingDomain editingDomain, ColumnViewer viewer,
                String columnLabel) {
            super(editingDomain, viewer, columnLabel);
            editor = new DateTimeCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof String) {
                setDateType();
                return editor;
            }
            return null;
        }

        private void setDateType() {
            ProcessRelevantData data =
                    (ProcessRelevantData) getViewer().getInput();
            if (data != null) {
                BasicType basicType = ProcessDataUtil.getModelBasicType(data);
                if (basicType != null
                        && basicType.getType()
                                .equals(BasicTypeType.DATETIME_LITERAL)) {
                    editor.setType(DateType.DATETIME);
                } else if (basicType != null
                        && basicType.getType()
                                .equals(BasicTypeType.DATE_LITERAL)) {
                    editor.setType(DateType.DATE);
                } else if (basicType != null
                        && basicType.getType()
                                .equals(BasicTypeType.TIME_LITERAL)) {
                    editor.setType(DateType.TIME);
                }
            }
        }

        private class DateTimeCellEditor extends DialogCellEditor {
            /**
             * @return the type
             */
            public DateType getType() {
                return type;
            }

            /**
             * @param type
             *            the type to set
             */
            public void setType(DateType type) {
                this.type = type != null ? type : DateType.DATE;
            }

            private DateType type;

            /**
             * @param control
             */
            public DateTimeCellEditor(Composite parent) {
                this(parent, null);
            }

            /**
             * Cell editor to input date/time using a calendar pop-up.
             * 
             * @param parent
             * @param type
             */
            public DateTimeCellEditor(Composite parent, DateType type) {
                super(parent);
                setType(type);
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
             * eclipse.swt.widgets.Control)
             */
            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                Date date = null;
                DateType dateType = getType();
                DateTimePopup popup =
                        new DateTimePopup(cellEditorWindow, dateType);
                popup.setCalendar(DateTimePopup.createCalendar((Date) null));
                if (popup.open() == DateTimePopup.OK) {
                    date = DateTimePopup.getDate(popup.getSelection());
                }
                String dateStr = null;
                if (date != null) {
                    switch (dateType) {
                    case DATE:
                        dateStr =
                                SimpleDateFormat
                                        .getDateInstance(DateFormat.SHORT)
                                        .format(date);
                        break;
                    case DATETIME:
                        dateStr =
                                SimpleDateFormat
                                        .getDateTimeInstance(DateFormat.SHORT,
                                                DateFormat.SHORT).format(date);
                        break;
                    case TIME:
                        dateStr =
                                SimpleDateFormat
                                        .getTimeInstance(DateFormat.SHORT)
                                        .format(date);
                        break;
                    }
                }
                return dateStr;
            }

        }

    }
}
