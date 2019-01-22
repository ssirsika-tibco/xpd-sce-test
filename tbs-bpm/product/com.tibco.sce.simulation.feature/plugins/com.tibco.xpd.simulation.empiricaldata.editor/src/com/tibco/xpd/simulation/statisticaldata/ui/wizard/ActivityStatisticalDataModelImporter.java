/* 
 ** 
 **  MODULE:             $RCSfile: ModelImporter.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-21 $ 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.realdata.Cases;
import com.tibco.xpd.simulation.realdata.DocumentRoot;
import com.tibco.xpd.simulation.realdata.RealDataFactory;
import com.tibco.xpd.simulation.realdata.RealDataPackage;
import com.tibco.xpd.simulation.realdata.provider.RealDataItemProviderAdapterFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2Switch;

/**
 * Model for importer.
 * 
 * @author jarciuch
 */
public class ActivityStatisticalDataModelImporter {

    public static final Integer IGNORED_VALUE = new Integer(0);

    public static final String NOT_APLICABLE =
            Messages.ActivityStatisticalDataModelImporter_NotApplicable;

    /** Tabular data about case start events */
    private DataTable dataTable = null;

    /** Emf model rootObject */
    private DocumentRoot rootObject;

    /**
     * This caches an instance of the model package.
     */
    protected RealDataPackage modelPackage = RealDataPackage.eINSTANCE;

    /**
     * This caches an instance of the model factory.
     */
    protected RealDataFactory modelFactory = modelPackage.getRealDataFactory();

    private ComposedAdapterFactory adapterFactory;

    private AdapterFactoryEditingDomain editingDomain;

    private Map activityNamesMap;

    private final List processActivities = new ArrayList();

    private String[] processActivitiesNames;

    private List processParameters = new ArrayList();

    private String[] processParametersIds;

    private Map parametersIdsMap;

    private boolean parametersReloadNeeded;

    private boolean activityReloadNeeded;

    private Map activityParameterMap;

    private List selectedParameters = new ArrayList();

    private String[] selectedParametersIds;

    private Map parametersValuesMap = new HashMap();

    private TimeDisplayUnitType importTimeUnit;

    public ActivityStatisticalDataModelImporter() {
        super();
        createEditingDomain();
    }

    /**
     * Create a new model.
     */
    protected DocumentRoot createInitialModel() {
        EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot(modelPackage);
        EStructuralFeature eStructuralFeature =
                eClass.getEStructuralFeature(getInitialObjectName());
        EObject rootObject = modelFactory.create(eClass);
        rootObject.eSet(eStructuralFeature, EcoreUtil
                .create((EClass) eStructuralFeature.getEType()));
        Cases casesElement = modelFactory.createCases();
        ((DocumentRoot) rootObject).setCases(casesElement);
        return (DocumentRoot) rootObject;
    }

    /**
     * Returns the names of the features representing global elements.
     */
    protected String getInitialObjectName() {
        List initialObjectNames = new ArrayList();
        for (Iterator elements =
                ExtendedMetaData.INSTANCE
                        .getAllElements(ExtendedMetaData.INSTANCE
                                .getDocumentRoot(modelPackage)).iterator(); elements
                .hasNext();) {
            EStructuralFeature eStructuralFeature =
                    (EStructuralFeature) elements.next();
            EClassifier eClassifier = eStructuralFeature.getEType();
            if (eClassifier instanceof EClass) {
                EClass eClass = (EClass) eClassifier;
                if (!eClass.isAbstract()) {
                    initialObjectNames.add(eStructuralFeature.getName());
                }
            }
        }
        Collections.sort(initialObjectNames, java.text.Collator.getInstance());
        // get first object so make sure that there is only one
        return (String) initialObjectNames.iterator().next();
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public EObject getRootObject() {
        return rootObject;
    }

    public Cases getCases() {
        return rootObject.getCases();
    }

    private void createEditingDomain() {
        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new RealDataItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        adapterFactory = new ComposedAdapterFactory(factories);

        // Create the command stack that will notify this editor as commands are
        // executed.
        //
        BasicCommandStack commandStack = new BasicCommandStack();

        // Create the editing domain with a special command stack.
        //
        editingDomain =
                new AdapterFactoryEditingDomain(adapterFactory, commandStack,
                        new HashMap());
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    public void createCasesFromTableData() {
        if (rootObject == null) {
            rootObject = createInitialModel();
        }
        Cases cases = modelFactory.createCases();

        rootObject.setCases(cases);
    }

    public IStatus importFromExcelFile(DataTable dataTable, File f,
            IProgressMonitor progressMonitor) {
        POIFSFileSystem fs;
        try {
            fs = new POIFSFileSystem(new FileInputStream(f));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            int numOfSheets = wb.getNumberOfSheets();
            HSSFSheet sheet = wb.getSheetAt(0);
            int firstRowNumber = sheet.getFirstRowNum();
            int lastRowNumber = sheet.getLastRowNum();
            int noOfRows = sheet.getPhysicalNumberOfRows();
            progressMonitor
                    .beginTask(Messages.ActivityStatisticalDataModelImporter_ReadingFile,
                            noOfRows);
            int worked = 0;
            if (firstRowNumber < lastRowNumber) {// process first row-col.
                // header
                HSSFRow row = sheet.getRow(firstRowNumber);
                HSSFRow row2 = sheet.getRow(firstRowNumber + 1);
                if (row != null) {
                    progressMonitor.worked(1);
                    // process row
                    short firstColNo = row.getFirstCellNum();
                    short lastColNo = row.getLastCellNum();
                    String columnName = null;
                    Class columnType = Object.class;
                    int columnNo = 0;
                    for (int j = firstColNo; j < lastColNo; j++) {
                        HSSFCell cell = row.getCell((short) j);
                        columnName = getCellValue(cell).toString().trim();
                        if (row2 != null) {
                            HSSFCell cell2 = row2.getCell((short) j);
                            columnType = getCellType(cell2);
                        } else {
                            columnType = Object.class;
                        }
                        dataTable.addColumn(columnNo, columnName, columnType);
                        columnNo++;
                    }
                }
            }
            int rowNo = 0;
            for (int i = firstRowNumber + 1; i <= lastRowNumber; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null)
                    continue;
                // process row
                Object[] tableDataRow =
                        new Object[dataTable.getNumberOfColumns()];
                short firstColNo = row.getFirstCellNum();
                short lastColNo = row.getLastCellNum();
                short tableLength = (short) tableDataRow.length;
                if ((lastColNo - firstColNo) > tableLength) {
                    // lastColNo = firstColNo + tableLength;
                }
                int columnNo = 0;
                for (int j = firstColNo; j < lastColNo; j++) {
                    HSSFCell cell = row.getCell((short) j);
                    tableDataRow[columnNo] = getCellValue(cell);
                    columnNo++;
                }
                dataTable.addRow(tableDataRow);
                worked++;
                progressMonitor.worked(worked);
                rowNo++;
            }
        } catch (FileNotFoundException e) {
            return new Status(IStatus.ERROR, EmpircalDataEditorPlugin.ID, 0,
                    Messages.ActivityStatisticalDataModelImporter_FileNotExist,
                    e);
        } catch (IOException e) {
            return new Status(
                    IStatus.ERROR,
                    EmpircalDataEditorPlugin.ID,
                    0,
                    Messages.ActivityStatisticalDataModelImporter_ProblemReading,
                    e);
        }
        setParametersReloadNeeded(true);
        setActivityReloadNeeded(true);
        return Status.OK_STATUS;
    }

    private Class getCellType(HSSFCell cell) {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                return String.class;
            case HSSFCell.CELL_TYPE_NUMERIC:
                short formatId = cell.getCellStyle().getDataFormat();
                String format = HSSFDataFormat.getBuiltinFormat(formatId);
                if (isDateTimeFormat(format)) {
                    return Date.class;
                } else {
                    return Double.class;
                }
            case HSSFCell.CELL_TYPE_BLANK:
                return Object.class;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.class;
            case HSSFCell.CELL_TYPE_ERROR:
                return Object.class;
            case HSSFCell.CELL_TYPE_FORMULA:
                return Object.class;
            default:
                return Object.class;
            }
        } else {
            return Object.class;
        }
    }

    private Object getCellValue(HSSFCell cell) {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_NUMERIC:
                short formatId = cell.getCellStyle().getDataFormat();
                String format = HSSFDataFormat.getBuiltinFormat(formatId);
                if (isDateTimeFormat(format)) {
                    return cell.getDateCellValue();
                } else {
                    return new Double(cell.getNumericCellValue());
                }
            case HSSFCell.CELL_TYPE_BLANK:
                return ""; //$NON-NLS-1$
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.valueOf(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_ERROR:
                return "!error!"; //$NON-NLS-1$
            case HSSFCell.CELL_TYPE_FORMULA:
                return "Formula: " + cell.getCellFormula(); //$NON-NLS-1$
            default:
                return ""; //$NON-NLS-1$
            }
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    private boolean isDateTimeFormat(String format) {
        boolean containsDataChars =
                (format.indexOf('y') != -1) && (format.indexOf('m') != -1)
                        && (format.indexOf('d') != -1)
                        && (format.indexOf('h') != -1);
        return containsDataChars;
    }

    public void createProcessActivitiesList(Process process) {
        processActivities.clear();
        Xpdl2Switch xpdlSwitch = new Xpdl2Switch() {
            public Object caseActivity(Activity object) {
                return object;
            }
        };
        TreeIterator ti =
                EcoreUtil.getAllContents(Collections.singleton(process));
        while (ti.hasNext()) {
            Object eobj = xpdlSwitch.doSwitch((EObject) ti.next());
            if (eobj instanceof Activity) {
                Activity activity = (Activity) eobj;
                Implementation implementation = activity.getImplementation();
                if (implementation instanceof Task
                        || implementation instanceof SubFlow) {
                    processActivities.add(eobj);
                }
            }
        }
        Collections.sort(processActivities, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                return ((Activity) arg0).getName().compareTo(((Activity) arg1)
                        .getName());
            }

        });

        processActivitiesNames = new String[processActivities.size() + 1];
        processActivitiesNames[0] =
                Messages.ActivityStatisticalDataModelImporter_Ignored;
        for (int i = 1; i < processActivitiesNames.length; i++) {
            processActivitiesNames[i] =
                    ((Activity) processActivities.get(i - 1)).getName();
        }
        processActivities.add(0, null);
    }

    public List getProcessActivityList() {
        return processActivities;
    }

    public String[] getProcessActivityNames() {
        return processActivitiesNames;
    }

    public void createProcessParameterList(Process process) {
        processParameters.clear();
        for (Iterator iter =
                ProcessInterfaceUtil.getAllFormalParameters(process).iterator(); iter
                .hasNext();) {
            processParameters.add(iter.next());
        }
        Collections.sort(processParameters, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                return ((FormalParameter) arg0).getName()
                        .compareTo(((FormalParameter) arg1).getName());
            }

        });

        processParametersIds = new String[processParameters.size() + 1];
        processParametersIds[0] =
                Messages.ActivityStatisticalDataModelImporter_Ignored;
        for (int i = 1; i < processParametersIds.length; i++) {
            processParametersIds[i] =
                    ((FormalParameter) processParameters.get(i - 1)).getName();
        }
        processParameters.add(0, null);
    }

    public List getProcessParameterList() {
        return processParameters;
    }

    public String[] getProcessParameterIds() {
        return processParametersIds;
    }

    public void createActivityNamesMap() {
        activityNamesMap = new TreeMap();
        int actNameColIndex = dataTable.getActivityNameColumnIndex();
        for (Iterator iter = dataTable.getData().iterator(); iter.hasNext();) {
            Object actName = ((Object[]) iter.next())[actNameColIndex];
            activityNamesMap.put(actName, IGNORED_VALUE);
        }
        if (processActivities != null) {
            // TODO Try to map automatically
        }
        setParametersReloadNeeded(true);
        setActivityReloadNeeded(false);
    }

    public Map getActivityNamesMap() {
        return activityNamesMap;
    }

    public void createParameterIdsMap() {
        parametersIdsMap = new TreeMap();
        Set filteredParams = new HashSet();
        filteredParams.add(dataTable.getActivityNameColumn());
        filteredParams.add(dataTable.getDurationColumn());
        for (Iterator iter = dataTable.getColumns().iterator(); iter.hasNext();) {
            DataTable.Column column = ((DataTable.Column) iter.next());
            if (!filteredParams.contains(column)) {
                parametersIdsMap.put(column.getLabel(), IGNORED_VALUE);
            }
        }
        if (processParameters != null) {
            // TODO Try to map automatically
        }
        setParametersReloadNeeded(false);
    }

    public Map getParametersIdsMap() {
        return parametersIdsMap;
    }

    /**
     * Create map with ActivityDataEntry and selected parameter index (Integer)
     * as a value. Integer("0") is the special (default) ignored parameter
     * value.
     * 
     */
    public void createActivityParameterMap() {
        activityParameterMap = new TreeMap();

        for (Iterator iter = activityNamesMap.entrySet().iterator(); iter
                .hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer activityIndex = (Integer) entry.getValue();
            if (activityIndex != null && !activityIndex.equals(IGNORED_VALUE)) {
                Activity activity =
                        (Activity) processActivities.get(activityIndex
                                .intValue());
                String importedActivityName = (String) entry.getKey();
                activityParameterMap.put(new ActivityDataEntry(activity,
                        importedActivityName), IGNORED_VALUE);
            }
        }
        if (processParameters != null) {
            // TODO Try to map automatically
        }
    }

    public Map getActivityParameterMap() {
        return activityParameterMap;
    }

    public void createSelectedParameterList() {
        selectedParameters.clear();
        for (Iterator iter = parametersIdsMap.entrySet().iterator(); iter
                .hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer parameterIndex = (Integer) entry.getValue();
            if (parameterIndex != null && !parameterIndex.equals(IGNORED_VALUE)) {
                FormalParameter selParam =
                        (FormalParameter) processParameters.get(parameterIndex
                                .intValue());
                selectedParameters.add(new ParameterDataEntry(selParam,
                        (String) entry.getKey()));
            }
        }
        Collections.sort(selectedParameters, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                return ((ParameterDataEntry) arg0).getLabel()
                        .compareTo(((ParameterDataEntry) arg1).getLabel());
            }
        });

        selectedParametersIds = new String[selectedParameters.size() + 1];
        selectedParametersIds[0] =
                Messages.ActivityStatisticalDataModelImporter_Ignored;
        for (int i = 1; i < selectedParametersIds.length; i++) {
            selectedParametersIds[i] =
                    ((ParameterDataEntry) selectedParameters.get(i - 1))
                            .getLabel();
        }
        selectedParameters.add(0, null);
    }

    public List getSelectedParameterList() {
        return selectedParameters;
    }

    public String[] getSelectedParameterIds() {
        return selectedParametersIds;
    }

    public boolean isParameterReloadNeeded() {
        return parametersReloadNeeded;
    }

    class ActivityDataEntry implements Comparable {
        private final Activity activity;

        private final String importedActivityName;

        private boolean selected;

        private Map distributionMap;

        ActivityDataEntry(Activity activity, String importedActivityName) {
            this.activity = activity;
            this.importedActivityName = importedActivityName;
        }

        public boolean equals(Object arg) {
            if (!(arg instanceof ActivityDataEntry)) {
                return false;
            }
            if (activity == null || importedActivityName == null) {
                return false;
            }
            ActivityDataEntry ade = ((ActivityDataEntry) arg);
            return activity.equals(ade.getActivity())
                    && importedActivityName.equals(ade
                            .getImportedActivityName());
        }

        public int hashCode() {
            return activity.hashCode() * importedActivityName.hashCode();
        }

        public String toString() {
            return activity.getName();
        }

        public String getLabel() {
            return activity.getName();
        }

        public Activity getActivity() {
            return activity;
        }

        public String getImportedActivityName() {
            return importedActivityName;
        }

        public int compareTo(Object arg) {
            return getLabel().compareTo(((ActivityDataEntry) arg).getLabel());
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Map getDistributionMap() {
            return distributionMap;
        }

        public void setDistributionMap(Map distributionMap) {
            this.distributionMap = distributionMap;
        }
    }

    class ParameterDataEntry implements Comparable {

        private final FormalParameter parameter;

        private final String parameterColumnName;

        public ParameterDataEntry(FormalParameter parameter,
                String parameterColumnName) {
            this.parameter = parameter;
            this.parameterColumnName = parameterColumnName;
        }

        public boolean equals(Object arg) {
            if (!(arg instanceof ParameterDataEntry)) {
                return false;
            }
            if (parameter == null || parameterColumnName == null) {
                return false;
            }
            ParameterDataEntry pde = (ParameterDataEntry) arg;
            return parameter.equals(pde.getParameter())
                    && parameterColumnName.equals(pde.getParameterColumnName());
        }

        public int hashCode() {
            return super.hashCode();
        }

        public String toString() {
            return parameter.getName();
        }

        public String getLabel() {
            return parameter.getName();
        }

        public FormalParameter getParameter() {
            return parameter;
        }

        public String getParameterColumnName() {
            return parameterColumnName;
        }

        public int compareTo(Object arg) {
            return getLabel().compareTo(((ParameterDataEntry) arg).getLabel());
        }
    }

    public Map createEnumerationValuesStaticticalMap(String activityName,
            String parameterColumnName, String processParameterId) {
        if (parameterColumnName == null) {
            return createValuesStaticticalMap(activityName);
        } else {
            return createValuesStaticticalMapForParam(activityName,
                    parameterColumnName,
                    processParameterId);
        }
    }

    private Map createValuesStaticticalMap(String activityName) {
        Map resultMap = new TreeMap();
        if (dataTable == null) {
            return resultMap;
        }

        int actColIdx = dataTable.getActivityNameColumnIndex();
        int durationColIndex = dataTable.getDurationColumnIndex();
        List data = dataTable.getData();
        String notApplicable = NOT_APLICABLE;
        ValueStatisticalDataImpl vsd =
                new ValueStatisticalDataImpl(notApplicable, notApplicable,
                        true, false);
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                double doubleValue =
                        ((Double) row[durationColIndex]).doubleValue();
                vsd.addToAverage(DisplayTimeUnitConverter
                        .convert(importTimeUnit,
                                TimeDisplayUnitType.MINUTE_LITERAL,
                                doubleValue));
            }
        }
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                double doubleValue =
                        ((Double) row[durationColIndex]).doubleValue();
                vsd.addToDiffSquereTotal(DisplayTimeUnitConverter
                        .convert(importTimeUnit,
                                TimeDisplayUnitType.MINUTE_LITERAL,
                                doubleValue));
            }
        }
        resultMap.put(notApplicable, vsd);
        return resultMap;
    }

    private Map createValuesStaticticalMapForParam(String activityName,
            String parameterColumnName, String processParameterId) {
        Map resultMap = new TreeMap();
        if (dataTable == null) {
            return resultMap;
        }

        int actColIdx = dataTable.getActivityNameColumnIndex();
        int durationColIndex = dataTable.getDurationColumnIndex();
        int parameterColumnIndex =
                dataTable.getColumnIndexByName(parameterColumnName);
        List data = dataTable.getData();
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                String paramValue = row[parameterColumnIndex].toString();
                double doubleValue =
                        ((Double) row[durationColIndex]).doubleValue();
                if (resultMap.keySet().contains(paramValue)) {
                    ValueStatisticalDataImpl vsd =
                            (ValueStatisticalDataImpl) resultMap
                                    .get(paramValue);
                    vsd.addToAverage(DisplayTimeUnitConverter
                            .convert(importTimeUnit,
                                    TimeDisplayUnitType.MINUTE_LITERAL,
                                    doubleValue));
                } else {
                    ValueStatisticalDataImpl vsd =
                            new ValueStatisticalDataImpl(paramValue.toString(),
                                    processParameterId, false, false);
                    vsd.addToAverage(DisplayTimeUnitConverter
                            .convert(importTimeUnit,
                                    TimeDisplayUnitType.MINUTE_LITERAL,
                                    doubleValue));
                    resultMap.put(paramValue, vsd);
                }
            }
        }
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                String paramValue = row[parameterColumnIndex].toString();
                if (resultMap.keySet().contains(paramValue)) {
                    ValueStatisticalDataImpl vsd =
                            (ValueStatisticalDataImpl) resultMap
                                    .get(paramValue);
                    double doubleValue =
                            ((Double) row[durationColIndex]).doubleValue();

                    vsd.addToDiffSquereTotal(DisplayTimeUnitConverter
                            .convert(importTimeUnit,
                                    TimeDisplayUnitType.MINUTE_LITERAL,
                                    doubleValue));
                } else {
                    throw new IllegalStateException(String.format(
                            Messages.ActivityStatisticalDataModelImporter_CannotFind, paramValue));                                    
                                    
                }
            }
        }
        String defaultValue = "Default"; //$NON-NLS-1$
        ValueStatisticalDataImpl vsd =
                new ValueStatisticalDataImpl(defaultValue, NOT_APLICABLE,
                        false, true);
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                double doubleValue =
                        ((Double) row[durationColIndex]).doubleValue();
                vsd.addToAverage(DisplayTimeUnitConverter
                        .convert(importTimeUnit,
                                TimeDisplayUnitType.MINUTE_LITERAL,
                                doubleValue));
            }
        }
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            if (activityName.equals(row[actColIdx])) {
                double doubleValue =
                        ((Double) row[durationColIndex]).doubleValue();
                vsd.addToDiffSquereTotal(DisplayTimeUnitConverter
                        .convert(importTimeUnit,
                                TimeDisplayUnitType.MINUTE_LITERAL,
                                doubleValue));
            }
        }
        resultMap.put(defaultValue, vsd);
        return resultMap;
    }

    public interface ValueStatisticalData {
        int getOccurences();

        double getTotal();

        String getValue();

        double getAverage();

        double getStdDeviation();

        boolean isDefaultDistribution();

        boolean isPatameterIndependent();

        public Integer getProcessParameterValueIndex();

        public void setProcessParameterValueIndex(
                Integer processParameterValueIndex);

        public String getParameterId();
    }

    private static class ValueStatisticalDataImpl implements
            ValueStatisticalData {
        private String value;

        private int occurences;

        private double total;

        private double diffSquareTotal;

        private boolean patameterIndependent;

        private boolean defaultDistribution;

        private Integer processParameterValueIndex = IGNORED_VALUE;

        private String parameterId;

        public ValueStatisticalDataImpl(String value, String processParameter) {
            this.value = value;
            this.parameterId = processParameter;
            this.patameterIndependent = false;
            this.defaultDistribution = false;
        }

        public ValueStatisticalDataImpl(String value, String parameter,
                boolean patameterIndependent, boolean defaultDistribution) {
            this(value, parameter);
            this.patameterIndependent = patameterIndependent;
            this.defaultDistribution = defaultDistribution;
        }

        public void addToAverage(double value) {
            total += value;
            occurences++;
        }

        public void addToDiffSquereTotal(double value) {
            diffSquareTotal += Math.pow((value - getAverage()), 2);
        }

        /**
         * @see com.tibco.xpd.simulation.statisticaldata.ui.wizard.ValueStatisticalDataInterf#getOccurences()
         */
        public int getOccurences() {
            return occurences;
        }

        /**
         * @see com.tibco.xpd.simulation.statisticaldata.ui.wizard.ValueStatisticalDataInterf#getTotal()
         */
        public double getTotal() {
            return total;
        }

        /**
         * @see com.tibco.xpd.simulation.statisticaldata.ui.wizard.ValueStatisticalDataInterf#getValue()
         */
        public String getValue() {
            return value;
        }

        /**
         * @see com.tibco.xpd.simulation.statisticaldata.ui.wizard.ValueStatisticalDataInterf#getAverage()
         */
        public double getAverage() {
            return total / occurences;
        }

        /**
         * @see com.tibco.xpd.simulation.statisticaldata.ui.wizard.ValueStatisticalDataInterf#getStdDeviation()
         */
        public double getStdDeviation() {
            return Math.sqrt(diffSquareTotal / occurences);
        }

        public String toString() {
            return "Value = '" + value + "', Avg = " + getAverage() //$NON-NLS-1$ //$NON-NLS-2$
                    + ",  StdDev = " + getStdDeviation(); //$NON-NLS-1$
        }

        public boolean isDefaultDistribution() {
            return defaultDistribution;
        }

        public boolean isPatameterIndependent() {
            return patameterIndependent;
        }

        public Integer getProcessParameterValueIndex() {
            return processParameterValueIndex;
        }

        public void setProcessParameterValueIndex(
                Integer processParameterValueIndex) {
            this.processParameterValueIndex = processParameterValueIndex;
        }

        public String getParameterId() {
            return parameterId;
        }

    }

    public ParameterBasedDistribution createParameterBasedDistribution(
            String parameterId, Map valuesStaticticalMap) {
        SimulationFactory factory =
                SimulationPackage.eINSTANCE.getSimulationFactory();
        ParameterBasedDistribution pbd =
                factory.createParameterBasedDistribution();
        for (Iterator iter = valuesStaticticalMap.values().iterator(); iter
                .hasNext();) {
            ValueStatisticalData vsd = (ValueStatisticalData) iter.next();
            ParameterDependentDistribution paramValueDist =
                    factory.createParameterDependentDistribution();
            NormalRealDistribution normalDist =
                    factory.createNormalRealDistribution();
            normalDist.setMean(vsd.getAverage());
            normalDist.setStandardDeviation(vsd.getStdDeviation());
            paramValueDist.setBasicDistribution(normalDist);

            ExpressionType expression = factory.createExpressionType();

            if (vsd.isDefaultDistribution()) {
                expression.setDefault(new EObjectImpl() {
                });
            } else {
                EnumBasedExpressionType enumBasedExpression =
                        factory.createEnumBasedExpressionType();
                enumBasedExpression.setParamName(parameterId);
                String enumValue;
                if (vsd.getProcessParameterValueIndex().equals(IGNORED_VALUE)) {
                    enumValue = vsd.getValue();
                } else {
                    List processParamValuesList =
                            getProcessParametersValues(vsd.getParameterId());
                    enumValue =
                            (String) processParamValuesList
                                    .get(vsd.getProcessParameterValueIndex()
                                            .intValue());
                }
                enumBasedExpression.setEnumValue(enumValue);
                expression.setEnumBasedExpression(enumBasedExpression);
            }
            paramValueDist.setExpression(expression);

            pbd.getParameterDependentDistributions().add(paramValueDist);
        }
        return pbd;
    }

    public NormalRealDistribution createNormalDistribution(
            Map valuesStaticticalMap) {
        SimulationFactory factory =
                SimulationPackage.eINSTANCE.getSimulationFactory();
        ValueStatisticalData vsd =
                (ValueStatisticalData) valuesStaticticalMap.get(NOT_APLICABLE);
        NormalRealDistribution normalDist =
                factory.createNormalRealDistribution();
        normalDist.setMean(vsd.getAverage());
        normalDist.setStandardDeviation(vsd.getStdDeviation());
        return normalDist;
    }

    /**
     * Creates Map of process formal parameters to list of values. Indexes in
     * the value lists are important.
     * 
     */
    public void createProcessParametersValuesMap(Process process) {
        parametersValuesMap.clear();
        List simulationParams =
                SimulationXpdlUtils.getWorkflowSimulationParameters(process);
        for (Iterator iter = simulationParams.iterator(); iter.hasNext();) {
            ParameterDistribution paramDist =
                    (ParameterDistribution) iter.next();
            ArrayList parameterValusList = new ArrayList();
            for (Iterator iterator = paramDist.getEnumerationValue().iterator(); iterator
                    .hasNext();) {
                EnumerationValueType enumValue =
                        (EnumerationValueType) iterator.next();
                parameterValusList.add(enumValue.getValue());
            }
            Collections.sort(parameterValusList);
            parameterValusList.add(0,
                    Messages.ActivityStatisticalDataModelImporter_Ignored);
            parametersValuesMap.put(paramDist.getParameterId(),
                    parameterValusList);
        }
    }

    public List getProcessParametersValues(String parameterId) {
        return (List) parametersValuesMap.get(parameterId);
    }

    public void setParametersReloadNeeded(boolean parametersReloadNeeded) {
        this.parametersReloadNeeded = parametersReloadNeeded;
    }

    public void setImportTimeUnit(TimeDisplayUnitType unit) {
        importTimeUnit = unit;

    }

    public TimeDisplayUnitType getImportTimeUnit() {
        return importTimeUnit;
    }

    public boolean isActivityReloadNeeded() {
        return activityReloadNeeded;
    }

    public void setActivityReloadNeeded(boolean activityReloadNeeded) {
        this.activityReloadNeeded = activityReloadNeeded;
    }
}