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
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class ParametersMappingPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private static final String SOURCE_PARAMETERS_COLUMN = "ProcessParametersColumn"; //$NON-NLS-1$

    private static final String PROCESS_PARAMETERS_COLUMN = "ProcessParametersColumn"; //$NON-NLS-1$

    private final ActivityStatisticalDataModelImporter modelImporter;

    public ParametersMappingPage(
            ActivityStatisticalDataModelImporter modelImporter, String pageName) {
        super(pageName);
        this.modelImporter = modelImporter;
    }

    private Composite composite = null;

    private TableViewer dataTableViewer = null;

    private TableViewer paramsViewer;

    private ComboBoxCellEditor processParametersEditor;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group mapParametersGroup = new Group(composite, SWT.NULL);
        mapParametersGroup.setText(Messages.ParametersMappingPage_MapParameters);
        mapParametersGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout mapParametersGroupLayout = new GridLayout();
        mapParametersGroupLayout.numColumns = 2;
        mapParametersGroup.setLayout(mapParametersGroupLayout);

        Table paramViewerTable = new Table(mapParametersGroup, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        GridData paramViewerTableGridData = new GridData(GridData.FILL_BOTH);
        paramViewerTableGridData.horizontalSpan = 2;
        paramViewerTable.setLayoutData(paramViewerTableGridData);
        paramViewerTable.setHeaderVisible(true);
        paramViewerTable.setLinesVisible(true);

        TableColumn sourceParametersColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        sourceParametersColumn.setText(Messages.ParametersMappingPage_SourceParametersTableColumn_label);
        sourceParametersColumn.setWidth(150);

        TableColumn processParametersColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        processParametersColumn.setText(Messages.ParametersMappingPage_ProcessParametersTableColumn_label);
        processParametersColumn.setWidth(150);

        paramsViewer = new TableViewer(paramViewerTable);
        paramsViewer.setColumnProperties(new String[] {
                PROCESS_PARAMETERS_COLUMN, SOURCE_PARAMETERS_COLUMN });
        processParametersEditor = new ComboBoxCellEditor(paramViewerTable,
                new String[] {}, SWT.READ_ONLY);
        CellEditor[] cellEditors = new CellEditor[] { null,
                processParametersEditor };
        paramsViewer.setCellEditors(cellEditors);
        paramsViewer.setCellModifier(new ProcessParametersCellModifier());
        paramsViewer
                .setContentProvider(new ProcessParametersMapContentProvider());
        paramsViewer.setLabelProvider(new ProcessParametersMapLabelProvider());

        setControl(composite);
    }

    private void updatePageComplite() {
        // errors
        /*
         * if (dataTableViewer.getSelection() == null) { setMessage(null);
         * setErrorMessage("Select one of the types!"); return; }
         */

        // Check if parameter is mapped only once.
        Set tempSet = new HashSet();
        Integer repeatedParameterIndex = null; 
        for (Iterator iter = modelImporter.getParametersIdsMap().values().iterator(); iter.hasNext();) {
            Integer paramIndex = (Integer) iter.next();
            if(!paramIndex.equals(modelImporter.IGNORED_VALUE)) {
                if(tempSet.contains(paramIndex)) {                    
                    repeatedParameterIndex = paramIndex;
                    break;
                } else {
                    tempSet.add(paramIndex);
                }
            }
        }
        if (repeatedParameterIndex != null) {
            setMessage(null);
            String paramName = modelImporter.getProcessParameterIds()[repeatedParameterIndex.intValue()];
            setErrorMessage(String.format(Messages.ParametersMappingPage_ParameterError,paramName)); 
            setPageComplete(false);
            return;
        }
        
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
            if (modelImporter.isParameterReloadNeeded()) {
                modelImporter
                        .createProcessParameterList(((ActivityStatisticalDataWizard) getWizard())
                                .getSelectedProcess());
                processParametersEditor.setItems(modelImporter
                        .getProcessParameterIds());
                modelImporter.createParameterIdsMap();
                paramsViewer.setInput(modelImporter.getParametersIdsMap());
                paramsViewer.refresh();
            }
        }
        super.setVisible(visible);
    }

    public class ProcessParametersMapContentProvider implements
            IStructuredContentProvider {

        public Object[] getElements(Object inputElement) {
            return ((Map) inputElement).entrySet().toArray();
        }

        public void dispose() {
            // no implementation
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // no implementation
        }

    }

    public class ProcessParametersMapLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((Map.Entry) element).getKey().toString();
            case 1:
                Object value = ((Map.Entry) element).getValue();
                if (value instanceof Integer) {
                    int index = ((Integer) value).intValue();
                    return modelImporter.getProcessParameterIds()[index];
                }
                return ""; //$NON-NLS-1$
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

    }

    private final class ProcessParametersCellModifier implements ICellModifier {

        public boolean canModify(Object element, String property) {
            return PROCESS_PARAMETERS_COLUMN.equals(property);
        }

        public Object getValue(Object element, String property) {
            if (element instanceof Map.Entry) {
                if (PROCESS_PARAMETERS_COLUMN.equals(property)) {
                    Integer value = (Integer) ((Map.Entry) element).getValue();
                    if (value != null) {
                        return value;
                    }
                    return Integer.valueOf("0"); //$NON-NLS-1$
                }
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            if (PROCESS_PARAMETERS_COLUMN.equals(property)) {
                if (element instanceof TableItem) {
                    Object data = ((TableItem) element).getData();
                    if (data instanceof Map.Entry) {
                        ((Map.Entry) data).setValue(value);
                        paramsViewer.refresh();
                        updatePageComplite();
                    }
                }
            }
        }
    }

}
