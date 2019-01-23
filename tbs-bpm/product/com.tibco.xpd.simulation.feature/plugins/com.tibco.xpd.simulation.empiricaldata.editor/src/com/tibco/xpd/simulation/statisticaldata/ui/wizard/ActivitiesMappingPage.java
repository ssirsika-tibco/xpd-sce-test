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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.provider.SimulationExtensionsEditPlugin;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class ActivitiesMappingPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private static final String PROCESS_ACTIVITIES_COLUMN = "ProcessActivitiesColumn"; //$NON-NLS-1$

    private static final String SOURCE_ACTIVITIES_COLUMN = "SourceActivitiesColumn"; //$NON-NLS-1$

    private final ActivityStatisticalDataModelImporter modelImporter;

    public ActivitiesMappingPage(
            ActivityStatisticalDataModelImporter modelImporter, String pageName) {
        super(pageName);
        // TODO Auto-generated constructor stub
        this.modelImporter = modelImporter;
    }

    private Composite composite = null;

    private TableViewer dataTableViewer = null;

    private TableViewer paramsViewer;

    private Combo activityColumnCombo;

    private Combo durationColumnCombo;

    private Combo durationUnitCombo;

    private ComboBoxCellEditor processActivityEditor;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group paramGroup = new Group(composite, SWT.NULL);
        paramGroup.setText(Messages.ActivitiesMappingPage_Select);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
        paramGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        paramGroup.setLayout(paramGroupLayout);

        Label timeParameterLabel = new Label(paramGroup, SWT.NONE);
        timeParameterLabel.setText(Messages.ActivitiesMappingPage_ActivityNameColumn);
        activityColumnCombo = new Combo(paramGroup, SWT.BORDER | SWT.READ_ONLY);
        activityColumnCombo
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        activityColumnCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Combo combo = ((Combo) e.getSource());
                if (combo.getItemCount() > 0) {
                    String selItem = combo.getItem(combo.getSelectionIndex());
                    DataTable dataTable = modelImporter.getDataTable();
                    if (dataTable != null) {
                        DataTable.Column activityCol = dataTable
                                .getColumnByName(selItem);
                        if (activityCol != null) {
                            dataTable.setActivityNameColumn(activityCol);
                            modelImporter.createActivityNamesMap();
                            paramsViewer.setInput(modelImporter
                                    .getActivityNamesMap());
                            paramsViewer.refresh();
                            updatePageComplite();
                        }
                    }
                }
            }
        });

        Label durationParameterLabel = new Label(paramGroup, SWT.NONE);
        durationParameterLabel.setText(Messages.ActivitiesMappingPage_DurationColumn);
        durationColumnCombo = new Combo(paramGroup, SWT.BORDER | SWT.READ_ONLY);
        durationColumnCombo
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        durationColumnCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Combo combo = ((Combo) e.getSource());
                if (combo.getItemCount() > 0) {
                    String selItem = combo.getItem(combo.getSelectionIndex());
                    DataTable dataTable = modelImporter.getDataTable();
                    if (dataTable != null) {
                        DataTable.Column durationCol = dataTable
                                .getColumnByName(selItem);
                        if (durationCol != modelImporter.getDataTable()
                                .getDurationColumn()) {
                            if (durationCol != null) {
                                dataTable.setDurationColumn(durationCol);
                                modelImporter.setParametersReloadNeeded(true);
                                updatePageComplite();
                            }
                        }
                    }
                }
            }
        });
        Label durationUnitLabel = new Label(paramGroup, SWT.NONE);
        durationUnitLabel.setText(Messages.ActivitiesMappingPage_DurationUnit);
        durationUnitCombo = new Combo(paramGroup, SWT.BORDER | SWT.READ_ONLY);
        durationUnitCombo
        .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        for (Iterator iter = TimeDisplayUnitType.VALUES.iterator(); iter.hasNext();) {
            TimeDisplayUnitType unit = (TimeDisplayUnitType) iter.next();
            String unitName = SimulationExtensionsEditPlugin.INSTANCE.getString("_UI_TimeDisplayUnitType_" + unit.getName()+ "_literal"); //$NON-NLS-1$ //$NON-NLS-2$
            durationUnitCombo.add(unitName);
        }
        modelImporter.setImportTimeUnit(TimeDisplayUnitType.MINUTE_LITERAL);
        
        durationUnitCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Combo combo = ((Combo) e.getSource());
                if (combo.getItemCount() > 0) {
                    int index = combo.getSelectionIndex();
                    modelImporter.setImportTimeUnit((TimeDisplayUnitType) TimeDisplayUnitType.VALUES.get(index));
                }
            }
        });

        Group mapActivityGroup = new Group(composite, SWT.NULL);
        mapActivityGroup.setText(Messages.ActivitiesMappingPage_MapNames);
        mapActivityGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout mapActivityGroupLayout = new GridLayout();
        mapActivityGroupLayout.numColumns = 2;
        mapActivityGroup.setLayout(mapActivityGroupLayout);

        Table paramViewerTable = new Table(mapActivityGroup, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        GridData paramViewerTableGridData = new GridData(GridData.FILL_BOTH);
        paramViewerTableGridData.horizontalSpan = 2;
        paramViewerTable.setLayoutData(paramViewerTableGridData);
        paramViewerTable.setHeaderVisible(true);
        paramViewerTable.setLinesVisible(true);

        TableColumn columnNameColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        columnNameColumn.setText(Messages.ActivitiesMappingPage_SourceActivitiesTableColumn_label);
        columnNameColumn.setWidth(100);

        TableColumn parameterNameColumn = new TableColumn(paramViewerTable,
                SWT.NONE);
        parameterNameColumn.setText(Messages.ActivitiesMappingPage_ProcessActivitiesTableColumn_label); 
        parameterNameColumn.setWidth(100);

        paramsViewer = new TableViewer(paramViewerTable);
        paramsViewer.setColumnProperties(new String[] {
                SOURCE_ACTIVITIES_COLUMN, PROCESS_ACTIVITIES_COLUMN });
        processActivityEditor = new ComboBoxCellEditor(paramViewerTable,
                new String[] {}, SWT.READ_ONLY);
        CellEditor[] cellEditors = new CellEditor[] { null,
                processActivityEditor };
        paramsViewer.setCellEditors(cellEditors);
        paramsViewer.setCellModifier(new ParamNameCellModifier());
        paramsViewer.setContentProvider(new ActivityNamesMapContentProvider());
        paramsViewer.setLabelProvider(new ActivityNamesLabelProvider());

        setPageComplete(false);
        setControl(composite);
    }

    private void updatePageComplite() {
        setMessage(null);
        setErrorMessage(null);
        // errors
        if (modelImporter.getDataTable().getActivityNameColumn() == null ||
                activityColumnCombo.getText().trim().length() == 0) {
            setMessage(null);
            setErrorMessage(Messages.ActivitiesMappingPage_SelectActivity);
            activityColumnCombo.setFocus();
            setPageComplete(false);
            return;
        }
        if (modelImporter.getDataTable().getDurationColumn() == null ||
                durationColumnCombo.getText().trim().length() == 0) {
            setMessage(null);
            setErrorMessage(Messages.ActivitiesMappingPage_SelectDuration);
            durationColumnCombo.setFocus();
            setPageComplete(false);
            return;
        }
        
        //Check if at least one activity is mapped.
        boolean oneOfActivitiesMapped = false;
        for (Iterator iter = modelImporter.getActivityNamesMap().values().iterator(); iter.hasNext();) {
            Integer actIndex = (Integer) iter.next();
            if(!actIndex.equals(modelImporter.IGNORED_VALUE)) {
                oneOfActivitiesMapped = true;
                break;
            }
        }
        if (!oneOfActivitiesMapped) {
            setMessage(null);
            setErrorMessage(Messages.ActivitiesMappingPage_AtLeastOne);
            setPageComplete(false);
            return;            
        }
        
        //Check if activity is mapped only once.
        Set tempSet = new HashSet();
        Integer repeatedActivityIndex = null; 
        for (Iterator iter = modelImporter.getActivityNamesMap().values().iterator(); iter.hasNext();) {
            Integer actIndex = (Integer) iter.next();
            if(!actIndex.equals(modelImporter.IGNORED_VALUE)) {
                if(tempSet.contains(actIndex)) {                    
                    repeatedActivityIndex = actIndex;
                    break;
                } else {
                    tempSet.add(actIndex);
                }
            }
        }
        if (repeatedActivityIndex != null) {
            setMessage(null);
            String actName = modelImporter.getProcessActivityNames()[repeatedActivityIndex.intValue()];
            setErrorMessage(String.format(Messages.ActivitiesMappingPage_ActivityError, actName));  
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
        if (visible && modelImporter.getDataTable() != null && modelImporter.isActivityReloadNeeded()) {
            DataTable dataTable = modelImporter.getDataTable();
            modelImporter
                    .createProcessActivitiesList(((ActivityStatisticalDataWizard) getWizard())
                            .getSelectedProcess());
            processActivityEditor.setItems(modelImporter
                    .getProcessActivityNames());
            setActivityColumnCombo(dataTable);
            setDurationColumnCombo(dataTable);
            setDurationUnitCombo();
            paramsViewer.setInput(null);
            paramsViewer.refresh();
        }
        super.setVisible(visible);
    }

    private void setActivityColumnCombo(DataTable dataTable) {
        activityColumnCombo.removeAll();
        for (int i = 0; i < dataTable.getNumberOfColumns(); i++) {
            activityColumnCombo.add(dataTable.getColumnLabel(i));
        }
        DataTable.Column selectedColumn = dataTable.getActivityNameColumn();
        if (selectedColumn != null) {
            int index = activityColumnCombo.indexOf(selectedColumn.getLabel());
            if (index != -1) {
                activityColumnCombo.select(index);
                updatePageComplite();
            }
        }
    }

    private void setDurationColumnCombo(DataTable dataTable) {
        durationColumnCombo.removeAll();
        for (int i = 0; i < dataTable.getNumberOfColumns(); i++) {
            if (dataTable.getColumnType(i).isAssignableFrom(Double.class)) {
                durationColumnCombo.add(dataTable.getColumnLabel(i));
            }
        }
        DataTable.Column selectedColumn = dataTable.getDurationColumn();
        if (selectedColumn != null) {
            int index = durationColumnCombo.indexOf(selectedColumn.getLabel());
            if (index != -1) {
                durationColumnCombo.select(index);
            }
        }
    }
    private void setDurationUnitCombo() {
        TimeDisplayUnitType curentImportUnit = modelImporter.getImportTimeUnit();
        int selected = -1;
        for (int i = 0; i < TimeDisplayUnitType.VALUES.size(); i++) {
            if(((TimeDisplayUnitType) TimeDisplayUnitType.VALUES.get(i)).getValue() == curentImportUnit.getValue()) {
                selected = i;
                break;
            }
        }
        if(selected >= 0) {
            durationUnitCombo.select(selected);
        }
    }

    public class ActivityNamesMapContentProvider implements
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

    public class ActivityNamesLabelProvider extends LabelProvider implements
            ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((Map.Entry) element).getKey().toString();
            case 1:
                Object value = ((Map.Entry) element).getValue();
                if (value instanceof Integer) {
                    int index = ((Integer) value).intValue();
                    return modelImporter.getProcessActivityNames()[index];
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

    private final class ParamNameCellModifier implements ICellModifier {

        public boolean canModify(Object element, String property) {
            return PROCESS_ACTIVITIES_COLUMN.equals(property);
        }

        public Object getValue(Object element, String property) {
            if (element instanceof Map.Entry) {
                if (PROCESS_ACTIVITIES_COLUMN.equals(property)) {
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
            if (PROCESS_ACTIVITIES_COLUMN.equals(property)) {
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
