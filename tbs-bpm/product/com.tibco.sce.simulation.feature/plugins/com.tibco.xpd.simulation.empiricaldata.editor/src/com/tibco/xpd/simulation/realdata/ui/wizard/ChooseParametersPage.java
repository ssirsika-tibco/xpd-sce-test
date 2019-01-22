/* 
 ** 
 **  MODULE:             $RCSfile: ChooseParametersPage.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-23 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.realdata.ui.wizard;

import java.util.Date;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.realdata.ui.wizard.DataTable.Column;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class ChooseParametersPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private static final String PARAMETER_NAME_COLUMN = "ParameterNameColumn"; //$NON-NLS-1$

    private static final String COLUMN_NAME_COLUMN = "ColumnNameColumn"; //$NON-NLS-1$

    private final class ParamNameCellModifier implements ICellModifier {

        public boolean canModify(Object element, String property) {
            return PARAMETER_NAME_COLUMN.equals(property);
        }

        public Object getValue(Object element, String property) {
            if (PARAMETER_NAME_COLUMN.equals(property)) {
                if (element instanceof Column) {
                    return ((Column) element).getParameterName();
                }
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            if (PARAMETER_NAME_COLUMN.equals(property)) {
                if (element instanceof Column) {
                    ((Column) element).setParameterName((String) value);
                } else if (element instanceof TableItem) {
                    Object data = ((TableItem) element).getData();
                    if (data instanceof Column) {
                        ((Column) data).setParameterName((String) value);
                        paramsViewer.refresh();
                    }
                }
            }

        }
    }

    private final RealDataModelImporter modelImporter;

    public ChooseParametersPage(RealDataModelImporter modelImporter,
            String pageName) {
        super(pageName);
        // TODO Auto-generated constructor stub
        this.modelImporter = modelImporter;
    }

    private Composite composite = null;

    private Label importersLabel = null;

    private TableViewer dataTableViewer = null;

    private CheckboxTableViewer paramsViewer;

    private Combo timeParameterCombo;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        importersLabel = new Label(composite, SWT.NONE);
        importersLabel.setText(Messages.ChooseParametersPage_Imported);

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        dataTableViewer = new TableViewer(composite, SWT.BORDER);
        dataTableViewer.getTable().setLayoutData(gridData);
        dataTableViewer.getTable().setHeaderVisible(true);
        dataTableViewer.getTable().setLinesVisible(true);
        dataTableViewer
                .setContentProvider(new DataTable.DataTableContentProvider());
        dataTableViewer
                .setLabelProvider(new DataTable.DataTableLabelProvider());
        dataTableViewer.addSelectionChangedListener(this);

        Group paramGroup = new Group(composite, SWT.NULL);
        paramGroup.setText(Messages.ChooseParametersPage_Chosen);
        GridData gridData2 = new GridData();
        gridData2.grabExcessHorizontalSpace = true;
        gridData2.grabExcessVerticalSpace = true;
        gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        paramGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        paramGroup.setLayout(paramGroupLayout);

        Label timeParameterLabel = new Label(paramGroup, SWT.NONE);
        timeParameterLabel.setText(Messages.ChooseParametersPage_Time);

        timeParameterCombo = new Combo(paramGroup, SWT.BORDER);
        GridData gridData4 = new GridData();
        gridData4.grabExcessHorizontalSpace = true;
        gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        timeParameterCombo.setLayoutData(gridData4);
        timeParameterCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Combo combo = ((Combo) e.getSource());
                if (combo.getItemCount() > 0) {
                    String selItem = combo.getItem(combo.getSelectionIndex());
                    DataTable dataTable = modelImporter.getDataTable();
                    if (dataTable != null) {
                        DataTable.Column timeCol = dataTable
                                .getColumnByName(selItem);
                        if (timeCol != null) {
                            dataTable.setTimeColum(timeCol);
                        }
                    }
                }
            }
        });

        Table paramViewerTable = new Table(paramGroup, SWT.CHECK | SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        GridData paramViewerTableGridData = new GridData();
        paramViewerTableGridData.horizontalSpan = 2;
        paramViewerTableGridData.grabExcessHorizontalSpace = true;
        paramViewerTableGridData.grabExcessVerticalSpace = true;
        paramViewerTableGridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        paramViewerTableGridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        paramViewerTable.setLayoutData(paramViewerTableGridData);
        paramViewerTable.setHeaderVisible(true);
        paramViewerTable.setLinesVisible(true);

        TableColumn columnNameColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        columnNameColumn.setText("Column Name"); //$NON-NLS-1$
        columnNameColumn.setWidth(100);

        TableColumn parameterNameColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        parameterNameColumn.setText("Parameter Name"); //$NON-NLS-1$
        parameterNameColumn.setWidth(100);

        paramsViewer = new CheckboxTableViewer(paramViewerTable);
        paramsViewer.setColumnProperties(new String[] { COLUMN_NAME_COLUMN,
                PARAMETER_NAME_COLUMN });
        TextCellEditor paramNameEditor = new TextCellEditor(paramViewerTable);
        CellEditor[] cellEditors = new CellEditor[] { null, paramNameEditor };
        paramsViewer.setCellEditors(cellEditors);
        paramsViewer.setCellModifier(new ParamNameCellModifier());
        paramsViewer
                .setContentProvider(new DataTable.ParametersContentProvider());
        paramsViewer.setLabelProvider(new DataTable.ParametersLabelProvider());
        paramsViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                DataTable.Column col = (DataTable.Column) event.getElement();
                col.setSelected(event.getChecked());
            }
        });

        updatePageComplite();
        setControl(composite);
    }

    private void updatePageComplite() {
        // errors
        /*
         * if (dataTableViewer.getSelection() == null) { setMessage(null);
         * setErrorMessage("Select one of the types!"); return; }
         */

        getWizard().getNextPage(this);
        getWizard().getPreviousPage(this);
        setPageComplete(true);

        // warnings
        setMessage(null);
        setErrorMessage(null);
    }

    public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSource() == dataTableViewer) {
        }
    }

    public void setVisible(boolean visible) {
        if (visible && modelImporter.getDataTable() != null) {
            TableColumn[] tableCols = dataTableViewer.getTable().getColumns();
            for (int i = 0; i < tableCols.length; i++) {
                tableCols[i].dispose();
                tableCols[i] = null;
            }
            // dataTableViewer.getTable().pack(true);
            // setup columns
            DataTable dataTable = modelImporter.getDataTable();
            for (int i = 0; i < dataTable.getNumberOfColumns(); i++) {
                TableColumn tCol = new TableColumn(dataTableViewer.getTable(),
                        SWT.LEFT, i);
                tCol.setText(dataTable.getColumnLabel(i));
                tCol.pack();
                // tCol.setWidth(30);
            }
            dataTableViewer.setInput(dataTable);
            dataTableViewer.refresh();
            paramsViewer.setInput(dataTable);
            dataTableViewer.refresh();
            setTimeParameterCombo(dataTable);
        }
        super.setVisible(visible);
    }

    private void setTimeParameterCombo(DataTable dataTable) {
        timeParameterCombo.removeAll();
        for (int i = 0; i < dataTable.getNumberOfColumns(); i++) {
            if (dataTable.getColumnType(i).isAssignableFrom(Date.class)) {
                timeParameterCombo.add(dataTable.getColumnLabel(i));
            }
        }
        if (timeParameterCombo.getItemCount() > 0) {
            // select first
            timeParameterCombo.select(0);
            DataTable.Column timeCol = dataTable
                    .getColumnByName(timeParameterCombo
                            .getItem(timeParameterCombo.getSelectionIndex()));
            if (timeCol != null) {
                dataTable.setTimeColum(timeCol);
            }
        }
    }

}
