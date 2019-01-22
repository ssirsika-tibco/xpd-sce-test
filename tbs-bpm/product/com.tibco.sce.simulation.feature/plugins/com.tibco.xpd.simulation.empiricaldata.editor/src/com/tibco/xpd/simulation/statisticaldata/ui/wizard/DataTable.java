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
package com.tibco.xpd.simulation.statisticaldata.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.empiricaldata.Messages;
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
            case 1:
            	return ((Column) element).getParameterName();
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

    static class Column {
        private String label;
        private Class type;
        private boolean selected;
        private String parameterName;

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

		public String getParameterName() {
			return parameterName;
		}

		public void setParameterName(String parameterName) {
			this.parameterName = parameterName;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
    }

    private List data = null;

    private List columns = null;

	private Column activityNameColumn;

    private Column durationColumn;

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
        Column column = new Column(columnLabel, Object.class);
        column.setParameterName(getColumnParameterName(columnLabel));
        column.setSelected(false);
		columns.add(index, column);
        
    }

    public void addColumn(int index, String columnLabel, Class columntype) {
        Column column = new Column(columnLabel, columntype);
        column.setParameterName(getColumnParameterName(columnLabel));
        column.setSelected(false);
		columns.add(index, column);
    }

    Column getColumnByName(String name){
    	for (Iterator iter = columns.iterator(); iter.hasNext();) {
			Column column = (Column) iter.next();
			if (name.equals(column.getLabel())) {
				return column;
			}
		}
    	return null;
    }
    
    int getColumnIndexByName(String name){
        int noOfCol = getNumberOfColumns();
        for (int i = 0; i < noOfCol; i++) {
            Column column = (Column) columns.get(i);
            if (name.equals(column.getLabel())) {
                return i;
            }
        }
        return -1;
    }
    
    public String getColumnLabel(int index) {
        return ((DataTable.Column) columns.get(index)).getLabel();
    }

    public Class getColumnType(int index) {
        return ((DataTable.Column) columns.get(index)).getType();
    }
    
    public Column getColumn(int index) {
    	return (Column) columns.get(index);
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
        activityNameColumn = null;
    }

    public String toString() {
        char newLine = '\n';
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(newLine);
        sb.append(Messages.StatisticalDataTable_Columns + getNumberOfColumns()).append(newLine);
        sb.append(Messages.StatisticalDataTable_Rows + getNumberOfRows()).append(newLine);
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
    
    List getSelectedColumnsIndexes() {
    	List selectedColumnsIndexes = new ArrayList();
    	for (int i = 0; i < getNumberOfColumns(); i++) {
			Column column = (Column) columns.get(i);
			if (column.isSelected()) {
				selectedColumnsIndexes.add(new Integer(i));
			}
		}
    	return selectedColumnsIndexes;
    }
    
    private static String getColumnParameterName(String colName) {
    	String pattern = " +|\\t+|\\r+|\\n+"; //$NON-NLS-1$
    	return colName.replaceAll(pattern, ""); //$NON-NLS-1$
    }
    
    
    void setActivityNameColumn(Column column) {
    	activityNameColumn = column;
    }
    
	Column getActivityNameColumn() {
		return activityNameColumn;
	}
	
	int getActivityNameColumnIndex() {
		for (int i = 0; i < getNumberOfColumns(); i++) {
			if (activityNameColumn.equals(columns.get(i))) {
				return i;
			}
		}
		return -1;
	}
    
	void setDurationColumn(Column column) {
	    durationColumn = column;
	}
	
	Column getDurationColumn() {
	    return durationColumn;
	}
	
	int getDurationColumnIndex() {
	    for (int i = 0; i < getNumberOfColumns(); i++) {
	        if (durationColumn.equals(columns.get(i))) {
	            return i;
	        }
	    }
	    return -1;
	}
    

}
