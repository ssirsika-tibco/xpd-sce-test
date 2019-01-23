/* 
 ** 
 **  MODULE:             $RCSfile: DataTable.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-20 $ 
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
package com.tibco.xpd.simulation.empiricaldata.ui.wizard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType;

public class DataTable {

    public static class DataTableContentProvider implements IStructuredContentProvider{
        
        public Object[] getElements(Object inputElement) {
            return ((DataTable) inputElement).getData().toArray();
        }

        public void dispose() {
            // do nothing
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // do nothing
        }
        
    }
    
    public static class ParametersContentProvider implements IStructuredContentProvider {

        public Object[] getElements(Object inputElement) {
            return ((DataTable) inputElement).getColumns().toArray();
        }

        public void dispose() {
            // no implementation
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // no implementation
        }
        
    }
    
    public static class ParametersLabelProvider extends LabelProvider implements ITableLabelProvider {
        
        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((Column) element).getLabel();
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        
    }
    
    public static class DataTableLabelProvider extends LabelProvider implements ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            return ((Object[]) element)[columnIndex].toString();
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
        
    }

    private static class Column {
        private String label;

        private Class type;

        public Column(String label, Class type) {
            this.label = label;
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public Class getType() {
            return type;
        }

    }

    private List data = null;

    private List columns = null;

    public DataTable() {
        data = new ArrayList();
        columns = new ArrayList();
    }

    public int getNumberOfRows() {
        return data.size();
    }

    public int getNumberOfColumns() {
        return columns.size();
    }

    public void addColumn(int index, String columnLabel) {
        columns.add(index, new Column(columnLabel, Object.class));
    }

    public void addColumn(int index, String columnLabel, Class columntype) {
        columns.add(index, new Column(columnLabel, columntype));
    }

    public String getColumnLabel(int index) {
        return ((DataTable.Column) columns.get(index)).getLabel();
    }

    public Class getColumnType(int index) {
        return ((DataTable.Column) columns.get(index)).getType();
    }

    public void addRow(Object[] row) {
        if (row == null || row.length != columns.size()) {
            throw new IllegalArgumentException(
                    "No of columns should match number elements in row!"); //$NON-NLS-1$
        }
        data.add(row);
    }

    public Object[] getRow(int index) {
        return (Object[]) data.get(index);
    }

    public Object getElement(int rowNo, int columnNo) {
        if (rowNo < getNumberOfRows() && rowNo >= 0) {
            Object row = (Object) data.get(rowNo);
            if (row != null ) {
                if (columnNo < getNumberOfColumns() && columnNo >= 0) {
                    return ((Object[]) row)[columnNo];
                }    
            }
        }
        return null;
    }
    
    public void clearDataTable() {
        data = new ArrayList();
        columns = new ArrayList();
    }

    public String toString() {
        char newLine = '\n';
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(newLine);
        sb.append("No of columns: " + getNumberOfColumns()).append(newLine); //$NON-NLS-1$
        sb.append("No of rows: " + getNumberOfRows()).append(newLine); //$NON-NLS-1$
        //columns
        for (int i = 0; i < columns.size(); i++) {
            sb.append(getColumnLabel(i)).append('\t');
        } 
        sb.append(newLine);
        //rows
        int rowNo = 0;
        for (Iterator iter = data.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i].toString()).append('\t');
            }
            sb.append(newLine);
            rowNo++;
            if (rowNo >= 100) {
                break;
            }
        }
        return sb.toString();
    }
    
    List getData() {
        return data;
    }
    
    List getColumns() {
        return columns;
    }
    ////////////////////////////
    class PeriodValue {
        String periodValue;
        int numberOfHits;
        List periodDates;
        PeriodValue(String periodValue, int numberOfHits, Date date){
            this.periodValue = periodValue;
            this.numberOfHits = numberOfHits;
        }
        public int getNumberOfHits() {
            return numberOfHits;
        }
        public void setNumberOfHits(int numberOfHits) {
            this.numberOfHits = numberOfHits;
        }
        public String getPeriodValue() {
            return periodValue;
        }
        public void setPeriodValue(String periodValue) {
            this.periodValue = periodValue;
        }
        public void addDate(Date date){
            periodDates.add(date);  
        }
    }
    
    class Period {
        SimpleDateFormat periodValueFormat = new SimpleDateFormat("M"); //$NON-NLS-1$
        SimpleDateFormat periodIdFormat = new SimpleDateFormat("yyyyMM"); //$NON-NLS-1$
        //hash map [perioidId(String) -> periodValue]
        Map periodValues = new HashMap();
        
        void getWaightingForPeriod(List dates) {
            for (Iterator iter = dates.iterator(); iter.hasNext();) {
                Date date = (Date) iter.next();
                String periodId = getPeriodId(date);
                String periodValue = getPeriodValue(date);
                PeriodValue pv = (PeriodValue) periodValues.get(periodId);
                if (pv != null) { 
                    //add new instance to periodValue
                    pv.setNumberOfHits(pv.getNumberOfHits() + 1);
                    pv.addDate(date);
                } else {                    
                    periodValues.put(periodId, new PeriodValue(periodValue, 1, date));
                }
            }
            //agregate values
            
        }
        
        String getPeriodId(Date date){
            return periodIdFormat.format(date);
        }
        String getPeriodValue(Date date){
            int periodId = Integer.parseInt(periodValueFormat.format(date)); 
            return MonthOfYearPeriodType.get(periodId).getName();
        }
        
    }
    
}
