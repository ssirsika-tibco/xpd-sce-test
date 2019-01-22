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

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.statisticaldata.ui.wizard.ActivityStatisticalDataModelImporter.ActivityDataEntry;
import com.tibco.xpd.simulation.statisticaldata.ui.wizard.ActivityStatisticalDataModelImporter.ParameterDataEntry;
import com.tibco.xpd.simulation.statisticaldata.ui.wizard.ActivityStatisticalDataModelImporter.ValueStatisticalData;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class DataPreviewPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private static final String ACTIVITY_COLUMN = "ActivityColumn"; //$NON-NLS-1$

    private static final String PARAMETER_COLUMN = "ParametersColumn"; //$NON-NLS-1$

    private static final String VALUE_COLUMN = "ValueColumn"; //$NON-NLS-1$

    private static final String PROCESS_VALUE_COLUMN = "ProcessValueColumn"; //$NON-NLS-1$

    private static final String AVG_DURATION_COLUMN = "AverageDurationColumn"; //$NON-NLS-1$

    private static final String STD_DEVIATION_COLUMN = "StandardDeviationColumn"; //$NON-NLS-1$

    private final ActivityStatisticalDataModelImporter modelImporter;

    private final String DECIMAL_FORMAT = DisplayTimeUnitConverter.DEFAULT_SHORT_FORMAT;

    DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat
            .getInstance(DisplayTimeUnitConverter.DEFAULT_LOCALE);

    public DataPreviewPage(ActivityStatisticalDataModelImporter modelImporter,
            String pageName) {
        super(pageName);
        this.modelImporter = modelImporter;
        decimalFormat.applyPattern(DECIMAL_FORMAT);
    }

    private Composite composite = null;

    private CheckboxTableViewer activityParamViewer;

    private ComboBoxCellEditor selectedParametersEditor;

    private ComboBoxCellEditor parametersValuesMappingEditor;

    private TableViewer valuesViever;

    private String previousSelectedImportedActivity;

    private int previousSelectedParamIndex = -1;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        final Group dataPreviewGroup = new Group(composite, SWT.NULL);
        dataPreviewGroup.setText(Messages.DataPreviewPage_Preview);
        dataPreviewGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

        GridLayout dataPreviewGroupGridLayout = new GridLayout();
        dataPreviewGroup.setLayout(dataPreviewGroupGridLayout);

        SashForm sashForm = new SashForm(dataPreviewGroup, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

        // first table
        Table activityParamViewerTable = new Table(sashForm, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);

        activityParamViewerTable.setHeaderVisible(true);
        activityParamViewerTable.setLinesVisible(true);

        TableColumn sourceParametersColumn = new TableColumn(
                activityParamViewerTable, SWT.NONE);
        sourceParametersColumn.setText(Messages.DataPreviewPage_Activity);
        sourceParametersColumn.setWidth(100);

        TableColumn processParametersColumn = new TableColumn(
                activityParamViewerTable, SWT.NONE);
        processParametersColumn.setText(Messages.DataPreviewPage_Parameter);
        processParametersColumn.setWidth(100);

        activityParamViewer = new CheckboxTableViewer(activityParamViewerTable);
        activityParamViewer.setColumnProperties(new String[] { ACTIVITY_COLUMN,
                PARAMETER_COLUMN });
        selectedParametersEditor = new ComboBoxCellEditor(
                activityParamViewerTable, new String[] {}, SWT.READ_ONLY);
        CellEditor[] cellEditors = new CellEditor[] { null,
                selectedParametersEditor };
        activityParamViewer.setCellEditors(cellEditors);
        activityParamViewer
                .setCellModifier(new SelectedParametersCellModifier());
        activityParamViewer.setContentProvider(new MapContentProvider());
        activityParamViewer
                .setLabelProvider(new ActivityParametersMapLabelProvider());
        activityParamViewer.addSelectionChangedListener(this);
        activityParamViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                Map.Entry entry = (Map.Entry) event.getElement();
                ((ActivityStatisticalDataModelImporter.ActivityDataEntry) entry
                        .getKey()).setSelected(event.getChecked());
                updatePageComplite();
            }
        });

        // second table
        Composite distributionComposite = new Composite(sashForm, SWT.NONE);
        distributionComposite.setLayout(new GridLayout(1, true));
        Label distributionLablel = new Label(distributionComposite, SWT.NONE);
        distributionLablel.setText(Messages.DataPreviewPage_DurationDistribution);

        Table valuesViewerTable = new Table(distributionComposite, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        valuesViewerTable.setLayoutData(new GridData(GridData.FILL_BOTH));
        valuesViewerTable.setHeaderVisible(true);
        valuesViewerTable.setLinesVisible(true);

        TableColumn valueParametersColumn = new TableColumn(valuesViewerTable,
                SWT.NONE);
        valueParametersColumn.setText(Messages.DataPreviewPage_Value);
        valueParametersColumn.setWidth(100);

        TableColumn processValueParametersColumn = new TableColumn(
                valuesViewerTable, SWT.NONE);
        processValueParametersColumn.setText(Messages.DataPreviewPage_ProcessValue);
        processValueParametersColumn.setWidth(100);

        TableColumn averageDurationColumn = new TableColumn(valuesViewerTable,
                SWT.NONE);
        averageDurationColumn.setText(Messages.DataPreviewPage_AverageDuration);
        averageDurationColumn.setWidth(120);

        TableColumn standardDeviationColumn = new TableColumn(
                valuesViewerTable, SWT.NONE);
        standardDeviationColumn.setText(Messages.DataPreviewPage_StandardDeviation);
        standardDeviationColumn.setWidth(120);

        valuesViever = new TableViewer(valuesViewerTable);
        valuesViever
                .setColumnProperties(new String[] { VALUE_COLUMN,
                        PROCESS_VALUE_COLUMN, AVG_DURATION_COLUMN,
                        STD_DEVIATION_COLUMN });

        parametersValuesMappingEditor = new ComboBoxCellEditor(
                valuesViewerTable, new String[] {}, SWT.READ_ONLY);
        CellEditor[] valuesViewerCellEditors = new CellEditor[] { null,
                parametersValuesMappingEditor, null, null };
        valuesViever.setCellEditors(valuesViewerCellEditors);
        valuesViever.setCellModifier(new ParametersValuesMappingCellModifier());

        valuesViever.setContentProvider(new MapContentProvider());
        valuesViever
                .setLabelProvider(new ValuStatisticalDataMapLabelProvider());

        setPageComplete(false);
        setControl(composite);
    }

    private void updatePageComplite() {
        // errors
        /*
         * if (dataTableViewer.getSelection() == null) { setMessage(null);
         * setErrorMessage("Select one of the types!"); return; }
         */
        boolean oneOfActivitiesSelected = false;
        Set activities = modelImporter.getActivityParameterMap().keySet();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ActivityDataEntry actData = (ActivityDataEntry) iter.next();
            if (actData.isSelected()) {
                oneOfActivitiesSelected = true;
                break;
            }
        }
        if (!oneOfActivitiesSelected) {
            setMessage(null);
            setErrorMessage(Messages.DataPreviewPage_Select);
            setPageComplete(false);
            return;
        }

        setPageComplete(true);
        // warnings
        setMessage(null);
        setErrorMessage(null);
    }

    public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSource() == activityParamViewer) {
            IStructuredSelection selection = (IStructuredSelection) event
                    .getSelection();
            if (!selection.isEmpty()) {
                showValueStatisticTable(selection);
            }
        }
    }

    private void showValueStatisticTable(IStructuredSelection selection) {
        Map.Entry entry = (Map.Entry) selection.getFirstElement();
        ActivityDataEntry activityDataEntry = ((ActivityStatisticalDataModelImporter.ActivityDataEntry) entry
                .getKey());
        String selectedImportedActivity = activityDataEntry
                .getImportedActivityName();
        int selectedParamIndex = ((Integer) entry.getValue()).intValue();
        // selected parameters have changed.
        if (!selectedImportedActivity.equals(previousSelectedImportedActivity)
                || selectedParamIndex != previousSelectedParamIndex) {
            previousSelectedImportedActivity = selectedImportedActivity;
            previousSelectedParamIndex = selectedParamIndex;
            ParameterDataEntry parameterDataEntry = ((ActivityStatisticalDataModelImporter.ParameterDataEntry) modelImporter
                    .getSelectedParameterList().get(selectedParamIndex));
            if (selectedParamIndex != 0) {
                List processParametersValues = modelImporter
                        .getProcessParametersValues(parameterDataEntry
                                .getParameter().getName());
                if (processParametersValues != null) {
                    parametersValuesMappingEditor
                            .setItems((String[]) processParametersValues
                                    .toArray(new String[processParametersValues
                                            .size()]));
                } else {
                    parametersValuesMappingEditor.setItems(new String[] {});
                }
            } else {

            }
            if (activityDataEntry.getDistributionMap() == null) {
                Map valuesMap = null;
                if (selectedParamIndex == 0) {
                    valuesMap = modelImporter
                            .createEnumerationValuesStaticticalMap(
                                    selectedImportedActivity, null, null);
                } else {
                    String paramColumnName = parameterDataEntry
                            .getParameterColumnName();
                    String processParamId = parameterDataEntry.getParameter()
                            .getName();
                    valuesMap = modelImporter
                            .createEnumerationValuesStaticticalMap(
                                    selectedImportedActivity, paramColumnName,
                                    processParamId);
                }
                activityDataEntry.setDistributionMap(valuesMap);
            }
            valuesViever.setInput(activityDataEntry.getDistributionMap());
            valuesViever.refresh();
        }
    }

    public void setVisible(boolean visible) {
        if (visible && modelImporter.getDataTable() != null) {
            modelImporter
                    .createProcessParametersValuesMap(((ActivityStatisticalDataWizard) getWizard())
                            .getSelectedProcess());
            modelImporter.createSelectedParameterList();
            selectedParametersEditor.setItems(modelImporter
                    .getSelectedParameterIds());
            modelImporter.createActivityParameterMap();
            activityParamViewer.setInput(modelImporter
                    .getActivityParameterMap());
            activityParamViewer.refresh();
        }
        super.setVisible(visible);
    }

    public class MapContentProvider implements IStructuredContentProvider {

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

    public class ActivityParametersMapLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((ActivityStatisticalDataModelImporter.ActivityDataEntry) ((Map.Entry) element)
                        .getKey()).getLabel();
            case 1:
                Object value = ((Map.Entry) element).getValue();
                if (value instanceof Integer) {
                    int index = ((Integer) value).intValue();
                    return modelImporter.getSelectedParameterIds()[index];
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

    public class ValuStatisticalDataMapLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            ValueStatisticalData valueStatData = ((ActivityStatisticalDataModelImporter.ValueStatisticalData) ((Map.Entry) element)
                    .getValue());
            switch (columnIndex) {
            case 0:
                return valueStatData.getValue();
            case 1:
                Object processParametersValues = modelImporter
                        .getProcessParametersValues(valueStatData
                                .getParameterId());
                if (processParametersValues instanceof List) {
                    return (String) ((List) processParametersValues)
                            .get(valueStatData.getProcessParameterValueIndex()
                                    .intValue());
                } else {
                    return modelImporter.NOT_APLICABLE;
                }
            case 2:
                return decimalFormat.format(valueStatData.getAverage());
            case 3:
                return decimalFormat.format(valueStatData.getStdDeviation());
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

    }

    private final class SelectedParametersCellModifier implements ICellModifier {

        public boolean canModify(Object element, String property) {
            return PARAMETER_COLUMN.equals(property);
        }

        public Object getValue(Object element, String property) {
            if (element instanceof Map.Entry) {
                if (PARAMETER_COLUMN.equals(property)) {
                    Integer value = (Integer) ((Map.Entry) element).getValue();
                    if (value != null) {
                        return value;
                    }
                    return ActivityStatisticalDataModelImporter.IGNORED_VALUE;
                }
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            if (PARAMETER_COLUMN.equals(property)) {
                if (element instanceof TableItem) {
                    Object data = ((TableItem) element).getData();
                    if (data instanceof Map.Entry) {
                        Entry entry = ((Map.Entry) data);
                        if (!entry.getValue().equals(value)) {
                            // reset current associated map
                            ActivityDataEntry activityDataEntry = ((ActivityStatisticalDataModelImporter.ActivityDataEntry) entry
                                    .getKey());
                            activityDataEntry.setDistributionMap(null);
                            entry.setValue(value);
                            if (!ActivityStatisticalDataModelImporter.IGNORED_VALUE
                                    .equals(value)) {
                                activityDataEntry.setSelected(true);
                                activityParamViewer.setChecked(data, true);
                                updatePageComplite();
                            }
                            activityParamViewer.refresh();
                            IStructuredSelection selection = (IStructuredSelection) activityParamViewer
                                    .getSelection();
                            showValueStatisticTable(selection);
                        }
                    }
                }
            }
        }
    }

    private final class ParametersValuesMappingCellModifier implements
            ICellModifier {

        public boolean canModify(Object element, String property) {
            if (PROCESS_VALUE_COLUMN.equals(property)) {
                if (element instanceof Map.Entry) {
                    ValueStatisticalData valueStatData = ((ActivityStatisticalDataModelImporter.ValueStatisticalData) ((Map.Entry) element)
                            .getValue());
                    return !valueStatData.isPatameterIndependent()
                            && !valueStatData.isDefaultDistribution();
                }
            }
            return false;
        }

        public Object getValue(Object element, String property) {
            if (element instanceof Map.Entry) {
                if (PROCESS_VALUE_COLUMN.equals(property)) {
                    ValueStatisticalData valueStatData = ((ActivityStatisticalDataModelImporter.ValueStatisticalData) ((Map.Entry) element)
                            .getValue());
                    String processParamId = valueStatData.getParameterId();
                    Integer value = valueStatData
                            .getProcessParameterValueIndex();
                    if (processParamId != null && value != null) {
                        return value;
                    }
                    return Integer.valueOf("0"); //$NON-NLS-1$
                }
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            if (PROCESS_VALUE_COLUMN.equals(property)) {
                if (element instanceof TableItem) {
                    Object data = ((TableItem) element).getData();
                    if (data instanceof Map.Entry) {
                        Entry entry = ((Map.Entry) data);
                        if (!entry.getValue().equals(value)) {
                            ValueStatisticalData valueStatData = ((ActivityStatisticalDataModelImporter.ValueStatisticalData) entry
                                    .getValue());
                            valueStatData
                                    .setProcessParameterValueIndex((Integer) value);
                            valuesViever.refresh();
                        }
                    }
                }
            }
        }
    }

}
